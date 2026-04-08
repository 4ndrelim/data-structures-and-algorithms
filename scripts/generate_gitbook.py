#!/usr/bin/env python3
"""
Generate gitbook-docs/ from the repo's READMEs.

Re-runnable: wipes gitbook-docs/ and regenerates from scratch on every run, so
new READMEs added under src/main/java/ are picked up automatically.

Usage:
    python3 scripts/generate_gitbook.py

What it does:
    1. Mirrors every README.md under src/main/java/ into gitbook-docs/<same path
       minus the src/main/java/ prefix>. Mirroring (instead of flattening) keeps
       all existing relative cross-README links working untouched.
    2. Copies docs/assets/images/ -> gitbook-docs/.gitbook/assets/, and rewrites
       every image reference (markdown ![]() and HTML <img src=...>) so the new
       relative depth is correct. External URLs are left alone.
    3. Copies the root README into gitbook-docs/README.md as the home page,
       rewriting its folder-style links to point at the mirrored README files
       and stripping its trailing meta sections (Getting Started / Usage /
       Disclaimer / Contributors), which are project meta — not educational
       content — and reference files we don't include in the GitBook output.
    4. Generates gitbook-docs/SUMMARY.md (the GitBook sidebar / TOC) from the
       resulting folder tree, using each README's H1 as the entry title.
"""

import os
import re
import shutil
import sys
from pathlib import Path


REPO_ROOT = Path(__file__).resolve().parents[1]
SRC_JAVA = REPO_ROOT / "src" / "main" / "java"
DOCS_IMAGES = REPO_ROOT / "docs" / "assets" / "images"
OUT_ROOT = REPO_ROOT / "gitbook-docs"
ASSETS_DST = OUT_ROOT / ".gitbook" / "assets"

# Extra files (outside src/main/java/) to include in the GitBook output.
# We deliberately only include the root README — DEVELOPER.md, team/profiles.md,
# and scripts/README.md are project meta, not educational content.
EXTRA_FILES = {
    REPO_ROOT / "README.md": OUT_ROOT / "README.md",
}

# Top-level sections shown in SUMMARY.md, in this order.
SECTIONS = [
    ("Data Structures", "dataStructures"),
    ("Algorithms", "algorithms"),
]


# ---- regexes ----
# Markdown image: ![alt](url) or ![alt](url "title"). URL has no spaces.
MD_IMAGE_RE = re.compile(r'(!\[[^\]]*\]\()([^)\s]+)([^)]*\))')
# HTML <img ... src="..." ...> — capture only the src value.
HTML_IMG_SRC_RE = re.compile(r'(<img\b[^>]*?\bsrc=)(["\'])([^"\']+)\2', re.IGNORECASE)
# Markdown link: [text](url). Negative lookbehind on '!' to skip images.
MD_LINK_RE = re.compile(r'(?<!!)(\[[^\]]+\]\()([^)\s]+)([^)]*\))')


warnings: list[str] = []


def warn(msg: str) -> None:
    warnings.append(msg)
    print(f"  WARN: {msg}", file=sys.stderr)


def is_external(url: str) -> bool:
    return url.startswith(("http://", "https://", "data:", "mailto:", "#"))


# ---------- path mapping ----------

def find_source_readmes() -> list[Path]:
    """All README.md files under src/main/java/."""
    return sorted(SRC_JAVA.rglob("README.md"))


def src_to_dst(src: Path) -> Path:
    """src/main/java/<rest> -> gitbook-docs/<rest>"""
    return OUT_ROOT / src.relative_to(SRC_JAVA)


# ---------- image rewriting ----------

def rewrite_image_url(url: str, src_file: Path, dst_file: Path) -> str:
    """If `url` resolves into docs/assets/images/, return a new path relative to dst_file."""
    if is_external(url):
        return url
    clean = url.split("#", 1)[0]
    if not clean:
        return url
    abs_src = (src_file.parent / clean).resolve()
    try:
        rel_under_images = abs_src.relative_to(DOCS_IMAGES)
    except ValueError:
        # Not under docs/assets/images/ — leave alone.
        return url
    target = ASSETS_DST / rel_under_images
    if not target.exists():
        warn(f"image not found after copy: {target} (referenced by {src_file})")
    return os.path.relpath(target, dst_file.parent)


def rewrite_images(content: str, src_file: Path, dst_file: Path) -> str:
    def md_sub(m: re.Match) -> str:
        prefix, url, suffix = m.group(1), m.group(2), m.group(3)
        return f"{prefix}{rewrite_image_url(url, src_file, dst_file)}{suffix}"

    def html_sub(m: re.Match) -> str:
        before, quote, url = m.group(1), m.group(2), m.group(3)
        return f"{before}{quote}{rewrite_image_url(url, src_file, dst_file)}{quote}"

    content = MD_IMAGE_RE.sub(md_sub, content)
    content = HTML_IMG_SRC_RE.sub(html_sub, content)
    return content


# ---------- link rewriting for the special files ----------

def strip_root_readme_meta(content: str) -> str:
    """Drop trailing meta sections from the root README.

    Everything from the "## Getting Started" heading onward (and the preceding
    `---` separator if present) is project meta — install instructions, usage
    notes, disclaimer, contributor list — not educational content. The GitBook
    home page should end after the CS2040S Syllabus Reference section.
    """
    marker = re.search(r"\n(?:---\s*\n+)?## Getting Started\b", content)
    if marker:
        content = content[:marker.start()].rstrip() + "\n"
    return content


def rewrite_root_readme_links(content: str) -> str:
    """Rewrite the folder-style links in the root README to point at mirrored README files."""

    def sub(m: re.Match) -> str:
        prefix, url, suffix = m.group(1), m.group(2), m.group(3)
        if is_external(url):
            return m.group(0)
        new_url = url
        if url.startswith("src/main/java/"):
            stripped = url[len("src/main/java/"):].rstrip("/")
            target_dir = SRC_JAVA / stripped
            target_readme = target_dir / "README.md"
            if target_readme.exists():
                new_url = f"{stripped}/README.md"
            elif target_dir.exists():
                # Missing parent README — point at the first child README we can find.
                children = sorted(target_dir.rglob("README.md"))
                if children:
                    new_url = str(children[0].relative_to(SRC_JAVA))
                    warn(
                        f"root README link {url!r} has no README at the target; "
                        f"redirecting to {new_url}"
                    )
                else:
                    warn(f"root README link target has no README anywhere: {url}")
            else:
                warn(f"root README link target does not exist: {url}")
        return f"{prefix}{new_url}{suffix}"

    return MD_LINK_RE.sub(sub, content)


# ---------- SUMMARY.md generation ----------

def extract_title(readme_path: Path) -> str | None:
    """Return the text of the first level-1 markdown heading, or None."""
    try:
        with open(readme_path, encoding="utf-8") as f:
            for line in f:
                stripped = line.strip()
                if stripped.startswith("# "):
                    return stripped[2:].strip()
    except OSError:
        pass
    return None


def folder_to_title(name: str) -> str:
    """camelCase / PascalCase folder name -> Title Case."""
    spaced = re.sub(r"(?<=[a-z])(?=[A-Z])", " ", name)
    return spaced[:1].upper() + spaced[1:]


def get_title(folder: Path) -> str:
    """Prefer the README's H1, otherwise convert the folder name."""
    readme = folder / "README.md"
    if readme.exists():
        title = extract_title(readme)
        if title:
            return title
    return folder_to_title(folder.name)


def walk_summary(folder: Path, depth: int) -> list[str]:
    """Recursively emit SUMMARY lines for `folder` and its sub-folders."""
    indent = "  " * depth
    title = get_title(folder)
    readme = folder / "README.md"
    if readme.exists():
        rel = readme.relative_to(OUT_ROOT).as_posix()
        lines = [f"{indent}* [{title}]({rel})"]
    else:
        lines = [f"{indent}* {title}"]
    for child in sorted(folder.iterdir()):
        if child.is_dir() and any(child.rglob("README.md")):
            lines.extend(walk_summary(child, depth + 1))
    return lines


def write_summary() -> None:
    lines = ["# Table of contents", "", "* [Introduction](README.md)", ""]

    for section_title, section_dir in SECTIONS:
        section_root = OUT_ROOT / section_dir
        if not section_root.exists():
            continue
        lines.append(f"## {section_title}")
        lines.append("")
        for child in sorted(section_root.iterdir()):
            if child.is_dir() and any(child.rglob("README.md")):
                lines.extend(walk_summary(child, depth=0))
        lines.append("")

    (OUT_ROOT / "SUMMARY.md").write_text("\n".join(lines).rstrip() + "\n", encoding="utf-8")


# ---------- main ----------

def main() -> None:
    print(f"Repo root: {REPO_ROOT}")
    print(f"Output:    {OUT_ROOT}")

    if not SRC_JAVA.exists():
        sys.exit(f"ERROR: {SRC_JAVA} does not exist")
    if not DOCS_IMAGES.exists():
        sys.exit(f"ERROR: {DOCS_IMAGES} does not exist")

    if OUT_ROOT.exists():
        shutil.rmtree(OUT_ROOT)
    OUT_ROOT.mkdir(parents=True)

    print(f"Copying images: {DOCS_IMAGES} -> {ASSETS_DST}")
    shutil.copytree(DOCS_IMAGES, ASSETS_DST)
    image_count = sum(1 for _ in ASSETS_DST.iterdir() if _.is_file())
    print(f"  copied {image_count} images")

    src_readmes = find_source_readmes()
    print(f"Processing {len(src_readmes)} source READMEs from src/main/java/...")
    for src in src_readmes:
        dst = src_to_dst(src)
        dst.parent.mkdir(parents=True, exist_ok=True)
        content = src.read_text(encoding="utf-8")
        content = rewrite_images(content, src, dst)
        dst.write_text(content, encoding="utf-8")

    print(f"Processing {len(EXTRA_FILES)} extra files...")
    for src, dst in EXTRA_FILES.items():
        if not src.exists():
            warn(f"extra file missing: {src}")
            continue
        dst.parent.mkdir(parents=True, exist_ok=True)
        content = src.read_text(encoding="utf-8")
        content = rewrite_images(content, src, dst)
        if src == REPO_ROOT / "README.md":
            content = strip_root_readme_meta(content)
            content = rewrite_root_readme_links(content)
        dst.write_text(content, encoding="utf-8")

    print("Generating SUMMARY.md...")
    write_summary()

    out_readmes = list(OUT_ROOT.rglob("README.md"))
    print(f"\nDone. {len(out_readmes)} README files written under {OUT_ROOT.name}/")
    if warnings:
        print(f"\n{len(warnings)} warning(s):")
        for w in warnings:
            print(f"  - {w}")
    else:
        print("\nNo warnings.")


if __name__ == "__main__":
    main()
