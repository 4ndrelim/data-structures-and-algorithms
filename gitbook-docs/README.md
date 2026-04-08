# Data Structures & Algorithms

A complementary resource for learning fundamental data structures and algorithms. Aligned with [CS2040S](https://nusmods.com/courses/CS2040S/data-structures-and-algorithms) taught by [Prof Seth Gilbert](https://www.comp.nus.edu.sg/cs/people/gilbert/) at NUS.

Each topic includes implementation, intuition, complexity analysis, and practical considerations. Useful for:
- **CS2040S students** - Supplement lecture content with working implementations
- **Interview prep** - Review fundamental DSA topics with clear explanations
- **CS students** - Quick refresher on core concepts

Developed by CS2040S Teaching Assistants and alumni under Prof Seth's guidance.

---

## Data Structures

| Structure | Description |
|-----------|-------------|
| [AVL Tree](dataStructures/avlTree/README.md) | Self-balancing BST with height-balance property |
| [B-Tree](dataStructures/bTree/README.md) | Self-balancing tree optimized for disk access |
| [Binary Search Tree](dataStructures/binarySearchTree/README.md) | Ordered tree structure |
| [Disjoint Set / Union Find](dataStructures/disjointSet/README.md) | [Quick Find](dataStructures/disjointSet/quickFind/README.md), [Weighted Union](dataStructures/disjointSet/weightedUnion/README.md) with path compression |
| [Fenwick-Tree](dataStructures/fenwickTree/README.md) | AKA Binary Indexed Tree for prefix sum queries |
| [Hash Set](dataStructures/hashSet/README.md) | [Chaining](dataStructures/hashSet/chaining/README.md), [Open Addressing](dataStructures/hashSet/openAddressing/README.md) |
| [Heap](dataStructures/heap/README.md) | Binary max heap |
| [Linked List](dataStructures/linkedList/README.md) | Singly linked list with sorting |
| [LRU Cache](dataStructures/lruCache/README.md) | Hash map + doubly linked list |
| [Queue](dataStructures/queue/README.md) | [Deque](dataStructures/queue/Deque/README.md), [Monotonic Queue](dataStructures/queue/monotonicQueue/README.md) |
| [Red-Black Tree](dataStructures/rbTree/README.md) | Self-balancing BST with color invariants |
| [Segment Tree](dataStructures/segmentTree/README.md) | Range queries with point updates |
| [Stack](dataStructures/stack/README.md) | LIFO with monotonic stack discussion |
| [Trie](dataStructures/trie/README.md) | Prefix tree for string operations |

## Algorithms

### Sorting

| Algorithm | Variants |
|-----------|----------|
| [Bubble Sort](algorithms/sorting/bubbleSort/README.md) | Basic comparison sort |
| [Insertion Sort](algorithms/sorting/insertionSort/README.md) | Efficient for small/nearly sorted arrays |
| [Selection Sort](algorithms/sorting/selectionSort/README.md) | Simple O(n²) sort |
| [Merge Sort](algorithms/sorting/mergeSort/iterative/README.md) | [Recursive](algorithms/sorting/mergeSort/recursive/README.md), [Iterative](algorithms/sorting/mergeSort/iterative/README.md) |
| [Quick Sort](algorithms/sorting/quickSort/README.md) | [Hoare's](algorithms/sorting/quickSort/hoares/README.md), [Lomuto's](algorithms/sorting/quickSort/lomuto/README.md), [Paranoid](algorithms/sorting/quickSort/paranoid/README.md), [3-way](algorithms/sorting/quickSort/threeWayPartitioning/README.md) |
| [Counting Sort](algorithms/sorting/countingSort/README.md) | Integer sorting in O(n + k) |
| [Radix Sort](algorithms/sorting/radixSort/README.md) | Digit-by-digit sorting |
| [Cyclic Sort](algorithms/sorting/cyclicSort/README.md) | [Simple](algorithms/sorting/cyclicSort/simple/README.md), [Generalized](algorithms/sorting/cyclicSort/generalised/README.md) |

### Searching

| Algorithm | Description |
|-----------|-------------|
| [Binary Search](algorithms/binarySearch/README.md) | Standard and [templated](algorithms/binarySearch/binarySearchTemplated/README.md) versions |
| [Orthogonal Range Searching](algorithms/orthogonalRangeSearching/oneDim/README.md) | Range trees for multi-dimensional queries |

### Graph Algorithms

| Algorithm | Description |
|-----------|-------------|
| [Graph Traversals](algorithms/graph/README.md) | [BFS](algorithms/graph/bfs/README.md), [DFS](algorithms/graph/dfs/README.md) (recursive & iterative) |
| [Dijkstra](algorithms/graph/dijkstra/README.md) | Single-source shortest path (non-negative weights) |
| [Bellman-Ford](algorithms/graph/bellmanFord/README.md) | Single-source shortest path (handles negative weights) |
| [Floyd-Warshall](algorithms/graph/floydWarshall/README.md) | All-pairs shortest path |
| [Topological Sort](algorithms/graph/topologicalSort/README.md) | Linear ordering of DAG vertices (Kahn's algorithm) |
| [Minimum Spanning Tree](algorithms/minimumSpanningTree/README.md) | [Prim's](algorithms/minimumSpanningTree/prim/README.md), [Kruskal's](algorithms/minimumSpanningTree/kruskal/README.md) |

### String Algorithms

| Algorithm | Description |
|-----------|-------------|
| [KMP](algorithms/patternFinding/README.md) | Knuth-Morris-Pratt pattern matching |

---

## CS2040S Syllabus Reference

<details>
<summary>Click to expand syllabus mapping</summary>

1. **Basic Structures**: [Linked List](dataStructures/linkedList/README.md), [Stack](dataStructures/stack/README.md), [Queue](dataStructures/queue/README.md)
2. **[Binary Search](algorithms/binarySearch/README.md)**: Peak finding, [templated search](algorithms/binarySearch/binarySearchTemplated/README.md)
3. **Sorting**: [Bubble](algorithms/sorting/bubbleSort/README.md), [Insertion](algorithms/sorting/insertionSort/README.md), [Selection](algorithms/sorting/selectionSort/README.md), [Merge](algorithms/sorting/mergeSort/iterative/README.md), [Quick](algorithms/sorting/quickSort/README.md), [Counting](algorithms/sorting/countingSort/README.md), [Radix](algorithms/sorting/radixSort/README.md)
4. **Trees**: [BST](dataStructures/binarySearchTree/README.md), [AVL](dataStructures/avlTree/README.md), [Trie](dataStructures/trie/README.md), [B-Tree](dataStructures/bTree/README.md), [Segment Tree](dataStructures/segmentTree/README.md), [Red-Black Tree](dataStructures/rbTree/README.md), [Orthogonal Range Searching](algorithms/orthogonalRangeSearching/oneDim/README.md)
5. **[Binary Heap](dataStructures/heap/README.md)**
6. **[Disjoint Set / Union Find](dataStructures/disjointSet/README.md)**
7. **[Hashing](dataStructures/hashSet/README.md)**
8. **[Graphs](algorithms/graph/README.md)**: [BFS](algorithms/graph/bfs/README.md), [DFS](algorithms/graph/dfs/README.md), [Dijkstra](algorithms/graph/dijkstra/README.md), [Bellman-Ford](algorithms/graph/bellmanFord/README.md), [Floyd-Warshall](algorithms/graph/floydWarshall/README.md), [Topological Sort](algorithms/graph/topologicalSort/README.md)
9. **[Minimum Spanning Tree](algorithms/minimumSpanningTree/README.md)**: [Prim](algorithms/minimumSpanningTree/prim/README.md), [Kruskal](algorithms/minimumSpanningTree/kruskal/README.md)

</details>
