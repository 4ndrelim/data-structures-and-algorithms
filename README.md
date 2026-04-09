# Data Structures & Algorithms

A complementary resource for learning fundamental data structures and algorithms. Aligned with [CS2040S](https://nusmods.com/courses/CS2040S/data-structures-and-algorithms) taught by [Prof Seth Gilbert](https://www.comp.nus.edu.sg/cs/people/gilbert/) at NUS.

Each topic includes implementation, intuition, complexity analysis, and practical considerations. Useful for:
- **CS2040S students** - Supplement lecture content with working implementations
- **Interview prep** - Review fundamental DSA topics with clear explanations
- **CS students** - Quick refresher on core concepts

Developed by CS2040S Teaching Assistants and alumni under Prof Seth's guidance.

📖 Also available as a [GitBook](https://andreas-3.gitbook.io/data-structures-and-algorithms) for a cleaner reading experience.

If you find this resource helpful, consider giving the [GitHub repo](https://github.com/4ndrelim/data-structures-and-algorithms) a ⭐ — it helps others discover it!

---

## Data Structures

| Structure | Description |
|-----------|-------------|
| [AVL Tree](src/main/java/dataStructures/avlTree) | Self-balancing BST with height-balance property |
| [B-Tree](src/main/java/dataStructures/bTree) | Self-balancing tree optimized for disk access |
| [Binary Search Tree](src/main/java/dataStructures/binarySearchTree) | Ordered tree structure |
| [Disjoint Set / Union Find](src/main/java/dataStructures/disjointSet) | [Quick Find](src/main/java/dataStructures/disjointSet/quickFind), [Weighted Union](src/main/java/dataStructures/disjointSet/weightedUnion) with path compression |
| [Fenwick-Tree](src/main/java/dataStructures/fenwickTree) | AKA Binary Indexed Tree for prefix sum queries |
| [Hash Set](src/main/java/dataStructures/hashSet) | [Chaining](src/main/java/dataStructures/hashSet/chaining), [Open Addressing](src/main/java/dataStructures/hashSet/openAddressing) |
| [Heap](src/main/java/dataStructures/heap) | Binary max heap |
| [Linked List](src/main/java/dataStructures/linkedList) | Singly linked list with sorting |
| [LRU Cache](src/main/java/dataStructures/lruCache) | Hash map + doubly linked list |
| [Queue](src/main/java/dataStructures/queue) | [Deque](src/main/java/dataStructures/queue/Deque), [Monotonic Queue](src/main/java/dataStructures/queue/monotonicQueue) |
| [Red-Black Tree](src/main/java/dataStructures/rbTree) | Self-balancing BST with color invariants |
| [Segment Tree](src/main/java/dataStructures/segmentTree) | Range queries with point updates |
| [Stack](src/main/java/dataStructures/stack) | LIFO with monotonic stack discussion |
| [Trie](src/main/java/dataStructures/trie) | Prefix tree for string operations |

## Algorithms

### Sorting

| Algorithm | Variants |
|-----------|----------|
| [Bubble Sort](src/main/java/algorithms/sorting/bubbleSort) | Basic comparison sort |
| [Insertion Sort](src/main/java/algorithms/sorting/insertionSort) | Efficient for small/nearly sorted arrays |
| [Selection Sort](src/main/java/algorithms/sorting/selectionSort) | Simple O(n²) sort |
| [Merge Sort](src/main/java/algorithms/sorting/mergeSort) | [Recursive](src/main/java/algorithms/sorting/mergeSort/recursive), [Iterative](src/main/java/algorithms/sorting/mergeSort/iterative) |
| [Quick Sort](src/main/java/algorithms/sorting/quickSort) | [Hoare's](src/main/java/algorithms/sorting/quickSort/hoares), [Lomuto's](src/main/java/algorithms/sorting/quickSort/lomuto), [Paranoid](src/main/java/algorithms/sorting/quickSort/paranoid), [3-way](src/main/java/algorithms/sorting/quickSort/threeWayPartitioning) |
| [Counting Sort](src/main/java/algorithms/sorting/countingSort) | Integer sorting in O(n + k) |
| [Radix Sort](src/main/java/algorithms/sorting/radixSort) | Digit-by-digit sorting |
| [Cyclic Sort](src/main/java/algorithms/sorting/cyclicSort) | [Simple](src/main/java/algorithms/sorting/cyclicSort/simple), [Generalized](src/main/java/algorithms/sorting/cyclicSort/generalised) |

### Searching

| Algorithm | Description |
|-----------|-------------|
| [Binary Search](src/main/java/algorithms/binarySearch) | Standard and [templated](src/main/java/algorithms/binarySearch/binarySearchTemplated) versions |
| [Orthogonal Range Searching](src/main/java/algorithms/orthogonalRangeSearching) | Range trees for multi-dimensional queries |

### Graph Algorithms

| Algorithm | Description |
|-----------|-------------|
| [Graph Traversals](src/main/java/algorithms/graph) | [BFS](src/main/java/algorithms/graph/bfs), [DFS](src/main/java/algorithms/graph/dfs) (recursive & iterative) |
| [Dijkstra](src/main/java/algorithms/graph/dijkstra) | Single-source shortest path (non-negative weights) |
| [Bellman-Ford](src/main/java/algorithms/graph/bellmanFord) | Single-source shortest path (handles negative weights) |
| [Floyd-Warshall](src/main/java/algorithms/graph/floydWarshall) | All-pairs shortest path |
| [Topological Sort](src/main/java/algorithms/graph/topologicalSort) | Linear ordering of DAG vertices (Kahn's algorithm) |
| [Minimum Spanning Tree](src/main/java/algorithms/minimumSpanningTree) | [Prim's](src/main/java/algorithms/minimumSpanningTree/prim), [Kruskal's](src/main/java/algorithms/minimumSpanningTree/kruskal) |

### String Algorithms

| Algorithm | Description |
|-----------|-------------|
| [KMP](src/main/java/algorithms/patternFinding) | Knuth-Morris-Pratt pattern matching |

---

## CS2040S Syllabus Reference

<details>
<summary>Click to expand syllabus mapping</summary>

1. **Basic Structures**: [Linked List](src/main/java/dataStructures/linkedList), [Stack](src/main/java/dataStructures/stack), [Queue](src/main/java/dataStructures/queue)
2. **[Binary Search](src/main/java/algorithms/binarySearch)**: Peak finding, [templated search](src/main/java/algorithms/binarySearch/binarySearchTemplated)
3. **Sorting**: [Bubble](src/main/java/algorithms/sorting/bubbleSort), [Insertion](src/main/java/algorithms/sorting/insertionSort), [Selection](src/main/java/algorithms/sorting/selectionSort), [Merge](src/main/java/algorithms/sorting/mergeSort), [Quick](src/main/java/algorithms/sorting/quickSort), [Counting](src/main/java/algorithms/sorting/countingSort), [Radix](src/main/java/algorithms/sorting/radixSort)
4. **Trees**: [BST](src/main/java/dataStructures/binarySearchTree), [AVL](src/main/java/dataStructures/avlTree), [Trie](src/main/java/dataStructures/trie), [B-Tree](src/main/java/dataStructures/bTree), [Segment Tree](src/main/java/dataStructures/segmentTree), [Red-Black Tree](src/main/java/dataStructures/rbTree), [Orthogonal Range Searching](src/main/java/algorithms/orthogonalRangeSearching)
5. **[Binary Heap](src/main/java/dataStructures/heap)**
6. **[Disjoint Set / Union Find](src/main/java/dataStructures/disjointSet)**
7. **[Hashing](src/main/java/dataStructures/hashSet)**
8. **[Graphs](src/main/java/algorithms/graph)**: [BFS](src/main/java/algorithms/graph/bfs), [DFS](src/main/java/algorithms/graph/dfs), [Dijkstra](src/main/java/algorithms/graph/dijkstra), [Bellman-Ford](src/main/java/algorithms/graph/bellmanFord), [Floyd-Warshall](src/main/java/algorithms/graph/floydWarshall), [Topological Sort](src/main/java/algorithms/graph/topologicalSort)
9. **[Minimum Spanning Tree](src/main/java/algorithms/minimumSpanningTree)**: [Prim](src/main/java/algorithms/minimumSpanningTree/prim), [Kruskal](src/main/java/algorithms/minimumSpanningTree/kruskal)

</details>

---

## Getting Started

```bash
git clone https://github.com/4ndrelim/data-structures-and-algorithms.git
cd data-structures-and-algorithms
./gradlew clean test
```

For detailed setup with IntelliJ IDEA and Java SDK configuration, see the [Developer Guide](docs/DEVELOPER.md).

## Usage

Browse implementations directly on GitHub, or clone locally to experiment. See the [scripts folder](scripts/README.md) for running algorithms with custom inputs.

## Disclaimer

While we have verified correctness, there may be discrepancies with current lecture content. Please raise issues or consult your TA if you spot any concerns.

## Contributors

See the [team](docs/team/profiles.md)!
