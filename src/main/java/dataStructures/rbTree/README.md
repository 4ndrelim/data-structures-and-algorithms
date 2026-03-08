# Red-Black Tree

## Background

A **Red-Black Tree** is a self-balancing binary search tree where each node stores an extra bit representing "color" (red or black). The coloring constraints ensure the tree remains approximately balanced, guaranteeing `O(log n)` operations.

### Why Red-Black Trees?

Standard BSTs can degenerate to `O(n)` height when elements arrive in sorted order. AVL trees solve this with strict height balancing (left and right subtrees differ by at most 1), but this strictness requires more rotations on updates.

Red-Black trees offer a different trade-off: **looser balance constraints that still guarantee `O(log n)` height, but with fewer rotations**. The key insight is using color constraints instead of explicit height tracking - rather than checking "is the height difference > 1?", we enforce rules like "red nodes can't have red children" that implicitly bound the tree's height.

### The Five Properties

A valid Red-Black tree satisfies:

| # | Property | Why It Matters |
|---|----------|----------------|
| 1 | Every node is RED or BLACK | Coloring is the mechanism for tracking balance |
| 2 | Root is BLACK | Convention that simplifies fix-up logic |
| 3 | All leaves (NIL) are BLACK | NIL sentinels eliminate null checks |
| 4 | Red nodes have only black children | **Prevents long chains** - at most half the nodes on any path can be red |
| 5 | All root-to-leaf paths have equal black count | **Guarantees balance** - no path can be more than 2x another |

<details>
<summary><b>How do these properties guarantee O(log n) height?</b></summary>

Combining properties 4 and 5:
- Property 5: Every root-to-leaf path has exactly `b` black nodes (the "black-height")
- Property 4: Between any two black nodes, there's at most one red node

Therefore:
- **Shortest path**: All black → length `b`
- **Longest path**: Alternating red-black → length `2b`

The longest path is at most **twice** the shortest. If a tree has `n` nodes and black-height `b`, then `n ≥ 2^b - 1`, giving `b ≤ log(n+1)`. Since the longest path is `2b`:

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

**Interview tip:** "When would you choose RB over AVL?" → When inserts/deletes are frequent. RB-trees do at most 2-3 rotations per operation (vs potentially O(log n) for AVL), making them faster for write-heavy workloads despite slightly slower lookups.

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

Insertion has two phases:
1. **Standard BST insert**: Find correct position and insert as a **RED** node
2. **Fix-up**: Restore RB properties if violated

**Why insert as RED?** Inserting a black node would violate property 5 (black-height consistency) on one path. Inserting red might only violate property 4 (no red-red), which is easier to fix locally.

<details>
<summary><b>The 3 Insertion Fix-Up Cases</b></summary>

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

**Interview tip:** Deletion fix-up is the hardest part. Focus on understanding that we're pushing a "missing black" up the tree (Cases 1-2) or resolving it with rotations (Cases 3-4). You don't need to memorize all cases - understanding the invariant being restored is more valuable.

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

**When NOT to use RB-trees:**
- Read-heavy with rare updates → AVL tree (tighter height bound, faster lookups)
- Disk-based storage → B-tree (optimized for block I/O)
- Only need insert/lookup/delete without ordering → Hash table (`O(1)` average)

**Interview tip:** If asked "implement a sorted map", RB-tree is the production answer (Java/C++ use it), but AVL or skip-list are acceptable in interviews. Know the trade-offs, not just the implementation.
