# Binary Search Tree

## Background

A Binary Search Tree (BST) is a node-based tree structure where each node has at most two children. The key property (**BST invariant**):
- Left subtree contains only nodes with keys **less than** the node's key
- Right subtree contains only nodes with keys **greater than** the node's key

This ordering enables efficient search by eliminating roughly half the remaining nodes (assuming balanced) at each step. Often, the time complexity of operations is proportional to the tree's height, making it efficient in the case of balanced trees.

### Predecessor and Successor

- **Predecessor**: The largest key smaller than the given key
- **Successor**: The smallest key larger than the given key

Finding these involves two cases:
1. **In subtree**: Predecessor is the rightmost node in left subtree; successor is the leftmost node in right subtree
2. **In ancestors**: Traverse up via parent pointers until finding an ancestor that satisfies the condition

### Delete Operation

Delete has three cases based on the node's children:

| Case | Strategy |
|------|----------|
| **0 children** (leaf) | Simply remove the node |
| **1 child** | Replace node with its child |
| **2 children** | Replace node's key/value with its **successor**, then delete the successor |

<details><summary><b>Why does the 2-children case work?</b></summary>

The successor (smallest node in right subtree) maintains the BST invariant because:
1. It's larger than everything in the left subtree (since it's larger than the deleted node)
2. It's smaller than everything else in the right subtree (since it's the minimum there)

Using the predecessor (largest in left subtree) works equally well.

</details>

## Complexity Analysis

| Operation | Average | Worst Case | Notes |
|-----------|---------|------------|-------|
| `search()` | `O(log n)` | `O(n)` | Worst case: degenerate (linear) tree |
| `insert()` | `O(log n)` | `O(n)` | Same as search |
| `delete()` | `O(log n)` | `O(n)` | Involves search + potential successor lookup |
| `predecessor()` / `successor()` | `O(log n)` | `O(n)` | May traverse full height |
| `searchMin()` / `searchMax()` | `O(log n)` | `O(n)` | Traverse to leftmost/rightmost |
| Traversals | `O(n)` | `O(n)` | Must visit all nodes |

**Space**: `O(n)` for storing n nodes

**Interview tip:** The worst case `O(n)` occurs when insertions happen in sorted order, creating a "linked list" shape or also known as a "degenerate tree". This is why self-balancing trees (AVL, Red-Black) exist.

## Notes

1. **No duplicates**: This implementation throws an exception on duplicate keys. To support duplicates, you could store counts in nodes or use a list as the value.

2. **Parent pointers**: Nodes maintain parent references to enable upward traversal for predecessor/successor finding. This adds space overhead but simplifies these operations.

3. **Key-value pairs**: The implementation stores both keys (for ordering) and values (for data). Keys must be `Comparable`.

4. **Unbalanced by design**: A basic BST does not self-balance. For guaranteed `O(log n)` operations, use [AVL Tree](../avlTree) or Red-Black Tree.

## Applications

| Use Case | Why BST? |
|----------|----------|
| In-memory sorted data | In-order traversal yields sorted sequence |
| Range queries | Find all keys in `[a, b]` efficiently |
| Floor/ceiling queries | Find largest key ≤ x or smallest key ≥ x |
| Symbol tables | Key-value lookup with ordering |

**When to use BST vs alternatives:**
- Need sorted iteration? → BST (HashMap can't do this)
- Need guaranteed `O(log n)`? → Use AVL/Red-Black instead of basic BST
- Only need insert/search/delete? → HashMap is `O(1)` average, simpler
