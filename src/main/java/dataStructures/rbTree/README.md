# Red-Black Tree

## Background

A **Red-Black Tree** is a self-balancing binary search tree where each node stores an extra bit representing "color" (red or black). The coloring constraints ensure the tree remains approximately balanced, guaranteeing `O(log n)` operations.

You might have heard of RB-Trees, e.g. Java's `TreeMap` implementation uses it. But often, it often remains a black box to many. Admittedly, implementing an RB-tree from scratch is not particularly enlightening. It's tedious, complex, and riddled with edge cases - far less obvious than AVL trees, which are already tedious. **The real insight is knowing why they exist, the algorithm's guarantees, and when RB-trees are preferred over other balanced trees.** That said, we provide the intuition behind the implementation for coverage.

For intuitive visual explanations of RB-tree operations, see this [YouTube playlist](https://www.youtube.com/playlist?list=PL9xmBV_5YoZNqDI8qfOZgzbqahCUmUEin). Our implementation references the approaches illustrated there.

### Why Red-Black Trees?

Standard BSTs can degenerate to `O(n)` height when elements arrive in sorted order. AVL trees solve this with strict height balancing (left and right subtrees differ by at most 1), but this strictness requires more rotations on updates.

RB trees offer a different trade-off: **looser balance constraints that still guarantee `O(log n)` height, but with fewer rotations**. The key insight is using color constraints instead of explicit height tracking. Rather than checking "is the height difference > 1?", we enforce rules like "red nodes can't have red children" that implicitly bound the tree's height.

### The Five Properties

A valid Red-Black tree satisfies:

| # | Property | Why It Matters |
|---|----------|----------------|
| 1 | Every node is RED or BLACK | Coloring is the mechanism for tracking balance |
| 2 | Root is BLACK | Convention that simplifies fix-up logic |
| 3 | All leaves (NIL) are BLACK | NIL sentinels eliminate null checks (see note below) |
| 4 | Red nodes have only black children | **Prevents long chains** - at most half the nodes on any path can be red |
| 5 | All root-to-leaf paths have equal black count | **Guarantees balance** - no path can be more than 2x another |

<details>
<summary><b>Note on "leaves"</b></summary>

In RB-tree terminology, "leaves" refers to **NIL sentinel nodes**, not the conventional "last nodes with data." Every data node has two children - either another data node or a NIL. These NILs are conceptually the "leaves" we count when measuring black-height. In implementation, we typically use a single shared NIL node rather than creating separate NIL objects for every null pointer.
</details>

<details>
<summary><b>How do these properties guarantee O(log n) height?</b></summary>

Combining properties 4 and 5:
- Property 5: Every root-to-NIL path has exactly `b` black nodes (the "black-height")
- Property 4: Between any two black nodes, there's at most one red node

Therefore:
- **Shortest path**: All black → length `b`
- **Longest path**: Alternating red-black → length `2b`

The longest path is at most **twice** the shortest. If a tree has `n` data nodes and black-height `b`, then `n ≥ 2^b - 1`, giving `b ≤ log(n+1)`. Since the longest path is `2b`:

**Height ≤ 2 log(n+1) = O(log n)**

</details>

### Red-Black vs AVL

| Aspect | AVL Tree | Red-Black Tree |
|--------|----------|----------------|
| Balance strictness | Height diff ≤ 1 | Longest path ≤ 2x shortest |
| Height bound | ~1.44 log(n) | ~2 log(n) |
| Lookup speed | Slightly faster (shorter) | Slightly slower (taller) |
| Rotations per insert/delete | Up to O(log n) | At most 2-3 |
| Extra storage per node | Height or balance factor | 1 bit (color) |
| Best for | Read-heavy workloads | Write-heavy workloads |

In practice, the difference is marginal for most applications - both are `O(log n)`. RB-trees dominate in standard libraries because they guarantee **at most 2-3 rotations per operation**, while AVL deletion may require rotations propagating all the way up (O(log n) rotations).

<details>
<summary><b>Why does RB-tree have bounded rotations?</b></summary>

The key insight is how fix-up cases work:
- **Recoloring** (e.g., Case 1 in insertion) can propagate up the tree, but it's just flipping colors - O(1) and no structural change
- **Rotations** (Cases 2/3 for insert, Cases 3/4 for delete) **terminate the fix-up immediately** because they restore the local balance without affecting ancestors

In AVL, a rotation changes the _height_ of a subtree. This height change can unbalance the parent node, requiring another rotation, which can cascade all the way to the root. RB-tree's color-based invariants avoid this cascade.
</details>

**Why read-heavy → AVL, write-heavy → RB?**
- **AVL is shorter** (~1.44 log n height vs ~2 log n): Stricter balance = fewer node comparisons per lookup
- **RB has fewer rotations per write**: AVL deletion may trigger up to `O(log n)` rotations (one per ancestor level), while RB needs at most 2-3. Over millions of updates, this difference is significant

## Complexity Analysis

| Operation | Time | Notes |
|-----------|------|-------|
| `search()` | `O(log n)` | Standard BST search |
| `insert()` | `O(log n)` | BST insert + fix-up with at most 2 rotations |
| `delete()` | `O(log n)` | BST delete + fix-up with at most 3 rotations |
| `getMin()` / `getMax()` | `O(log n)` | Traverse to leftmost/rightmost |
| Single rotation | `O(1)` | Just pointer reassignments |

**Space:** `O(n)` for n nodes, plus 1 bit per node for color (negligible overhead)

## Operations

### Rotations

Rotations restructure the tree while preserving the BST property. They are `O(1)` operations involving only pointer reassignments.

```
Left Rotation:          Right Rotation:
    x                       y
   / \                     / \
  a   y      =>           x   c
     / \                 / \
    b   c               a   b
```

### Insertion

**Key point: Newly inserted nodes are always RED.**

Insertion has two phases:
1. **Standard BST insert**: Find correct position and insert as a **RED** node
2. **Fix-up**: Restore RB properties if violated

**Why insert as RED?** Inserting a black node would immediately violate property 5 (black-height) on that path. Inserting red might only violate property 4 (no red-red), which is easier to fix locally through recoloring and rotations.

<details>
<summary><b>Insertion Fix-Up Cases</b></summary>

**Case 0: Inserted node is the root**
- Simply recolor it to BLACK (satisfies property 2)
- No other fixes needed

After inserting node `z` (red), if `z`'s parent is also red, we have a red-red violation. There are three cases based on the uncle's color:

**Case 1: Uncle is RED**
```
      G(B)                G(R)
     /    \              /    \
   P(R)   U(R)   =>    P(B)   U(B)
   /                   /
 z(R)                z(R)
```
- Recolor: parent and uncle → BLACK, grandparent → RED
- Move `z` up to grandparent and repeat (violation may propagate up)
- No rotations needed

**Case 2: Uncle is BLACK, z is "inner" child** (e.g., z is right child of left parent)
```
      G(B)                G(B)
     /    \              /    \
   P(R)   U(B)   =>    z(R)   U(B)
     \                 /
     z(R)            P(R)
```
- Rotate `z`'s parent in opposite direction
- This transforms into Case 3

**Case 3: Uncle is BLACK, z is "outer" child** (e.g., z is left child of left parent)
```
      G(B)                P(B)
     /    \              /    \
   P(R)   U(B)   =>    z(R)   G(R)
   /                            \
 z(R)                           U(B)
```
- Rotate grandparent in opposite direction
- Recolor: parent → BLACK, old grandparent → RED
- **Done!** (terminates after at most 2 rotations)

</details>

### Deletion

Deletion is more complex because removing a BLACK node violates property 5 (black-height).

**Steps:**
1. Perform standard BST deletion (0, 1, or 2 children cases)
2. Track the color of the physically removed node
3. If removed node was BLACK, fix-up from its replacement position

<details>
<summary><b>The 4 Deletion Fix-Up Cases</b></summary>

When a BLACK node is removed, its replacement `x` has an implicit "extra black" that we need to resolve. Let `w` be `x`'s sibling:

**Case 1: Sibling w is RED**
- Rotate parent toward `x`, recolor `w` → BLACK, parent → RED
- Transforms into Case 2, 3, or 4 with a black sibling

**Case 2: Sibling w is BLACK, both of w's children are BLACK**
- Recolor `w` → RED (push the extra black up to parent)
- Move `x` up to parent and repeat

**Case 3: Sibling w is BLACK, w's far child is BLACK, near child is RED**
- Rotate `w` away from `x`, recolor appropriately
- Transforms into Case 4

**Case 4: Sibling w is BLACK, w's far child is RED**
- Rotate parent toward `x`
- Recolor: `w` → parent's color, parent → BLACK, `w`'s far child → BLACK
- **Done!** (terminates after at most 3 rotations total)

</details>

## Notes

1. **NIL sentinel nodes**: This implementation uses a single NIL node (always BLACK) as all leaves and as the root's parent. This eliminates null checks and simplifies fix-up logic.

2. **2-3-4 Tree correspondence**: Every RB-tree corresponds to a 2-3-4 tree. Red links represent "glued" nodes forming 3-nodes or 4-nodes. This mental model helps understand why the properties work.

3. **Left-Leaning RB Trees (LLRB)**: A simpler variant by Robert Sedgewick that restricts red links to lean left, reducing the number of cases. Trade-off: slightly taller trees.

4. **Space overhead**: Each node stores a color (1 bit conceptually, typically 1 byte in practice). This is similar to AVL's balance factor.

## Applications

| Use Case | Why Red-Black Tree? |
|----------|---------------------|
| Java `TreeMap` / `TreeSet` | General-purpose sorted map/set with consistent update performance |
| Linux kernel CFS scheduler | Process scheduling with frequent priority updates |
| C++ `std::map` / `std::set` | Standard library sorted containers |
| Database indexes (in-memory) | Balanced tree for fast range queries with frequent updates |
| Memory allocators (`malloc`) | Managing free blocks by size with frequent alloc/free |

### When to Choose RB-Tree

The key decision is understanding what you're optimizing for:

| If you need... | Use | Why |
|----------------|-----|-----|
| Sorted data + frequent updates | **RB-tree** | At most 2-3 rotations per operation |
| Sorted data + mostly reads | AVL tree | Tighter height bound (~1.44 log n vs ~2 log n) |
| Fast lookup only, no ordering | Hash table | `O(1)` average, but no ordering |
| Disk-based sorted data | B-tree | Minimizes disk I/O (wide nodes) |
| Simple implementation | Skip list | Probabilistic, easier to code |

**The real insight:** RB-trees are the "default choice" for in-memory sorted containers because they provide **consistent, predictable performance** on mixed workloads. They're not the absolute best at anything - AVL is faster for reads, hash tables faster for lookups - but they're **good enough at everything** while being especially efficient for writes. This is why production systems (Java, C++, Linux kernel) standardized on RB-trees.

**When NOT to use RB-trees:**
- Read-heavy with rare updates → AVL tree (faster lookups due to shorter height)
- Disk-based storage → B-tree (optimized for block I/O, minimizes seeks)
- Only need insert/lookup/delete without ordering → Hash table (`O(1)` average)
