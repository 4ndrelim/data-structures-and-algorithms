# Union Find / Disjoint Set

## Background

A disjoint-set structure also known as a union-find or merge-find set, is a data structure that tracks a set of elements
partitioned into a number of disjoint (non-overlapping) subsets. It is commonly used to check for connectivity 
(e.g. if two objects are 'grouped' together/belong to some component).

In CS2040s, this is introduced in the context of checking for dynamic connectivity. For instance, Kruskal's algorithm
in graph theory to find minimum spanning tree of a graph utilizes disjoint set to efficiently
query if there already exists a path between 2 nodes.

Generally, there are 2 main operations:

1. **Union**: Join two subsets into a single subset
2. **Find**: Determine which subset a particular element is in. In practice, this is often done to check
   if two elements are in the same subset or component.

The Disjoint Set structure is often introduced in 3 parts, with each iteration being better than the
previous either in time or space complexity (or both). More details can be found in the respective folders. 
Below is a brief overview:

1. **Quick Find** - Elements are assigned a component identity. 
Querying for connectivity and updating usually tracked with an internal array.

2. **Quick Union** - Component an element belongs to is now tracked with a tree structure. Nothing to enforce
a balanced tree and hence complexity does not necessarily improve
   - Note, this is not implemented but details can be found under weighted union folder.

3. **Weighted Union** - Same idea of using a tree, but constructed in a way that the tree is balanced, leading to
improved complexities.
   - Can be further augmented with path compression.

## Complexity Analysis

| Implementation | Union | Find | Space |
|----------------|-------|------|-------|
| Quick Find | `O(n)` | `O(1)` | `O(n)` |
| Quick Union | `O(n)` | `O(n)` | `O(n)` |
| Weighted Union | `O(log n)` | `O(log n)` | `O(n)` |
| Weighted Union + Path Compression | `O(α(n))`* | `O(α(n))`* | `O(n)` |

*`α(n)` is the inverse Ackermann function, which grows so slowly that it's effectively constant (`≤ 4`) for all practical input sizes.

**Interview tip:** When asked about Union-Find complexity with path compression, say "amortized nearly constant time" or "O(α(n)) where α is the inverse Ackermann function, practically constant."

## Operations

All Disjoint Set variants share the same two core operations. The differences lie in how each variant **represents components internally** and how that representation affects cost.

### `find(x)`

Return a **canonical identifier** for the component containing `x`. Two elements `x` and `y` belong to the same component if and only if `find(x) == find(y)`.

- **Quick Find**: the identifier is stored directly in an array slot for `x` → `O(1)`.
- **Quick Union / Weighted Union**: the identifier is the root of the tree containing `x`, found by walking parent pointers until reaching a node whose parent is itself.
- **With path compression**: while walking up to the root, re-point each visited node directly at the root, so future `find` calls on those nodes are nearly free.

### `union(x, y)`

Merge the components of `x` and `y` into a single component. If they are already in the same component, do nothing.

- **Quick Find**: rewrite every element's identifier from one of the two component IDs to the other → `O(n)`.
- **Quick Union**: make one root the parent of the other → cost dominated by the two `find` calls.
- **Weighted Union**: always attach the **smaller** tree under the **larger** root, which keeps tree height bounded by `O(log n)`. This is what makes both operations `O(log n)` (and `O(α(n))` amortized when combined with path compression).

For variant-specific implementation details, walk-throughs, and trade-offs, see [quickFind/](./quickFind/) and [weightedUnion/](./weightedUnion/).

## Applications
Because of its efficiency and simplicity in implementing, Disjoint Set structures are widely used in practice:
1. As mentioned, it is often used as a helper structure for Kruskal's MST algorithm
2. It can be used in the context of network connectivity
   - Managing a network of computers 
   - Or even analyse social networks, finding communities and determining if two users are connected through a chain
3. Can be part of clustering algorithms to group data points based on similarity - useful for ML
4. It can be used to detect cycles in dependency graphs, e.g, software dependency management systems
   1. Static analysis tools like `mypy` use this to identify circular dependencies!
5. It can be used for image processing, in labelling different connected components of an image

## Notes
Disjoint Set is a data structure designed to keep track of a set of elements partitioned into a number of 
non-overlapping subsets. **It is not suited for handling duplicates**, so our implementation ignores duplicates.
