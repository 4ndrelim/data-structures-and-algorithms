# Hash Table (HashSet / HashMap)

## Background

A **hash table** maps keys to values using a **hash function** that converts keys into array indices. This enables `O(1)` expected-time operations for insert, lookup, and delete.

```
key → hash(key) → index → bucket
```

The core challenge is **collision handling** - when two different keys hash to the same index.

### Hash Function Requirements

A good hash function should:
1. **Deterministic**: Same key always produces same hash
2. **Uniform distribution**: Keys spread evenly across buckets
3. **Fast to compute**: `O(1)` time

Common approach: **division method** `h(k) = k mod m` where `m` is the number of buckets.

## Collision Resolution Strategies

| Strategy | How it works | Collision handling |
|----------|--------------|-------------------|
| **[Chaining](chaining/)** | Each bucket stores a linked list | Append to list at bucket |
| **[Open Addressing](openAddressing/)** | All elements stored in array | Probe for next empty slot |

### Chaining

Each bucket contains a linked list of elements that hash to that index.

```
Bucket 0: → [A] → [D] → null
Bucket 1: → [B] → null
Bucket 2: → [C] → [E] → [F] → null
```

**Pros**: Simple, never "full", degrades gracefully
**Cons**: Extra memory for pointers, cache-unfriendly

### Open Addressing

All elements stored directly in the array. On collision, probe for next available slot.

```
[A] [B] [_] [C] [D] [_] [E] [_]
     ↑
     collision with A → probe to here
```

**Pros**: Cache-friendly, no extra pointers
**Cons**: Clustering, must resize when full, deletion is tricky (tombstones)

**Interview tip:** Favor **chaining** when load factor is unpredictable or high (chains grow linearly; OA probe sequences explode), deletions are frequent (no tombstone pollution), hash function quality is uncertain, or you need worst-case guarantees (treeification). Favor **open addressing** when you want to minimize per-element memory (no pointers/node wrappers), cache performance matters, or the load factor stays low (short probe sequences).

## Complexity Analysis

| Operation | Expected | Worst (Chaining) | Worst (OA) |
|-----------|----------|------------------|------------|
| `add()` | `O(1)` | `O(n)` | `O(n)` |
| `contains()` | `O(1)` | `O(n)` | `O(n)` |
| `remove()` | `O(1)` | `O(n)` | `O(n)` |

**Space**: `O(n)` for n elements

Expected `O(1)` assumes **Simple Uniform Hashing Assumption (SUHA)**: each key is equally likely to hash to any bucket, independent of other keys.

Worst case occurs when all keys hash to the same bucket (degenerate case).

## Load Factor and Resizing

The **load factor** `α = n/m` (elements/buckets) measures how full the table is.

| Strategy | Typical threshold | Why resize? |
|----------|------------------|-------------|
| **Chaining** | α > 0.75 (recommended) | Performance optimization - more buckets → fewer collisions |
| **Open Addressing** | α > 0.75 (mandatory) | **Must resize** - table fills up, probing degrades |

**Key insight**: Resizing is **mandatory** for open addressing (table becomes full), but merely an **optimization** for chaining (can always append to lists).

## Real-World Implementations

### Java's HashMap (Chaining with Treeification)

Java uses **chaining** with a clever optimization:
1. Initially: linked list per bucket
2. When bucket exceeds 8 elements: convert to **Red-Black Tree**
3. When bucket shrinks below 6: convert back to linked list

```
Bucket with few elements:    → [A] → [B] → [C]           O(n) search
Bucket with many elements:   Red-Black Tree              O(log n) search
```

This bounds worst-case lookup to `O(log n)` instead of `O(n)`.

### Python's dict (Open Addressing with Perturbation)

Python uses **open addressing** with a sophisticated probing strategy:
1. Primary hash determines initial slot
2. On collision: **perturbed probing** using bits from full hash
3. Probe sequence: `j = ((5*j) + 1 + perturb) mod 2^n`

This achieves better distribution than linear/quadratic probing while maintaining cache efficiency.

| Language | Strategy | Collision in bucket | Load factor |
|----------|----------|---------------------|-------------|
| **Java** | Chaining | LinkedList → RB-Tree | 0.75 |
| **Python** | Open Addressing | Perturbed probing | 0.67 |

### Why Different Choices?

**Java chose chaining**: Java is already fast (JIT-compiled), so it can **afford chaining's overhead**. This lets it prioritize **security** - treeification guarantees `O(log n)` worst-case, protecting against hash-flooding DoS attacks that matter for enterprise/server systems. (Hash-flooding: attacker crafts keys that all hash to the same bucket, turning `O(1)` into `O(n)` per lookup. Treeification bounds damage to `O(log n)`.) 
<details>
<summary><b>Don't confuse load factor with collision handling</b></summary>

- **Load factor** = proportion of buckets occupied --> determines when to resize (add more buckets)
- **Chaining/treeification** = what happens _within_ a bucket when multiple keys hash there

Resizing reduces collision _probability_ by spreading keys across more buckets, but doesn't prevent collisions. Even at low load factor, an attacker can force all keys into one bucket. That's why treeification matters — it handles the within-bucket worst case.
</details>

<br/>

**Python chose open addressing**: Python is already slow (interpreted), so it **can't afford extra overhead**. Dicts aren't just a data structure you use, they're how the language _works internally_ (`obj.attr` looks up `__dict__`, variables are namespace dicts, imports use `sys.modules` dict). Cache-efficient OA on these constant dict operations compounds into significant real-world speedups.

## HashMap vs HashSet

The **hash table** (HashMap) is the core data structure. Everything discussed above - hashing, collision resolution, load factors - describes how to build a HashMap.

A **HashSet** is typically just a thin wrapper around HashMap:

```java
class HashSet<T> {
    private HashMap<T, Object> map = new HashMap<>();
    private static final Object PRESENT = new Object();  // dummy value

    public boolean add(T key) {
        return map.put(key, PRESENT) == null;
    }

    public boolean contains(T key) {
        return map.containsKey(key);
    }
}
```

So when implementing a "HashSet" from scratch, you're really implementing a HashMap that ignores values:

| HashSet | HashMap |
|---------|---------|
| `bucket[i] = key` | `bucket[i] = Entry(key, value)` |
| `add(key)` | `put(key, value)` |
| `contains(key)` | `containsKey(key)` |

**Interview tip:** If asked to implement HashSet, you can mention it's typically backed by HashMap with a dummy value. The interesting work is in the hash table mechanics (collision resolution, resizing), not the Set vs Map distinction.

## Notes

1. **Hash function quality matters**: A poor hash function causes clustering, degrading `O(1)` to `O(n)`. Java's `String.hashCode()` uses: `s[0]*31^(n-1) + s[1]*31^(n-2) + ... + s[n-1]`.

2. **Prime table sizes**: Using a prime number of buckets helps distribute keys more uniformly with the division method.

3. **Immutable keys**: Keys should not change after insertion. If `hashCode()` changes, the element becomes "lost" - it's in the wrong bucket.

4. **equals/hashCode contract**: If `a.equals(b)` then `a.hashCode() == b.hashCode()`. Violating this breaks hash tables.

5. **Elastic Hashing (2021)**: Andrew Yao conjectured in 1985 that for open-addressing hash tables, you can't do better than uniform probing - the fuller the table, the worse performance gets (exponentially so). [Elastic hashing](https://joshtuddenham.dev/blog/hashmaps/) challenges this: it can fill a table to `(1-δ)` capacity (e.g., 99% full with `δ=0.01`) while achieving amortized `O(1)` expected probes and worst-case `O(log δ⁻¹)` expected probes. This is a significant theoretical advancement for open addressing.

## Applications

| Use Case | Why Hash Table? |
|----------|-----------------|
| Database indexing | `O(1)` lookup by key |
| Caching (LRU, etc.) | Fast key-based retrieval |
| Counting frequencies | `O(1)` increment per element |
| Detecting duplicates | `O(1)` membership test |
| Symbol tables (compilers) | Fast variable/function lookup |

**Interview tip:** When you need `O(1)` lookup/insert/delete by key, hash table is usually the answer. Common patterns: two-sum (complement lookup), anagram grouping (canonical key), frequency counting.
