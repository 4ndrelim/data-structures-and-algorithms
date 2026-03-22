# Graph Algorithms

## Background

This module contains fundamental graph algorithms including traversals and shortest path algorithms.

### Graph Traversals

Graph traversal algorithms systematically visit all vertices in a graph. The two fundamental approaches are **Breadth-First Search (BFS)** and **Depth-First Search (DFS)**, each with distinct traversal patterns and use cases.

### Graph Representation

This implementation uses an **adjacency list** representation:
- `List<List<Integer>>` where `graph.get(i)` returns neighbors of node `i`
- Nodes are integers from `0` to `n-1`
- Works for both directed and undirected graphs

```java
// Example: Graph with edges 0->1, 0->2, 1->3
List<List<Integer>> graph = new ArrayList<>();
graph.add(List.of(1, 2));  // Node 0's neighbors
graph.add(List.of(3));      // Node 1's neighbors
graph.add(List.of());       // Node 2's neighbors
graph.add(List.of());       // Node 3's neighbors
```

## BFS vs DFS Comparison

| Aspect | BFS | DFS |
|--------|-----|-----|
| Data Structure | Queue | Stack (or recursion) |
| Traversal Pattern | Level by level | Deep before backtracking |
| Time Complexity | `O(V + E)` | `O(V + E)` |
| Space Complexity | `O(V)` | `O(V)` |
| Shortest Path (unweighted) | Yes | No |
| Memory Usage | Higher (stores entire level) | Lower (stores path) |

### Visual Comparison

```
       0
      / \
     1   2
    / \
   3   4

BFS from 0: [0, 1, 2, 3, 4]  (level by level)
DFS from 0: [0, 1, 3, 4, 2]  (deep first, then backtrack)
```

## When to Use Which

| Use Case | Algorithm | Why |
|----------|-----------|-----|
| Shortest path (unweighted) | BFS | Guarantees minimum edges |
| Shortest path (weighted, non-negative) | Dijkstra | Greedy + heap, O((V+E) log V) |
| Shortest path (negative weights) | Bellman-Ford | Handles negative, detects cycles |
| Level-order traversal | BFS | Naturally visits by distance |
| Cycle detection | DFS | Easier with recursion stack |
| Topological sort | DFS or Kahn's | DFS post-order or BFS with in-degree |
| Connected components | Either | Both work equally well |
| Path existence | Either | Both work equally well |
| Maze solving (any path) | DFS | Uses less memory |
| Maze solving (shortest) | BFS | Guarantees shortest |

## Implementations

### Traversals

| Algorithm | Description |
|-----------|-------------|
| [./bfs](./bfs) | Queue-based Breadth-First Search |
| [./dfs](./dfs) | Depth-First Search (recursive and iterative) |

### Shortest Path

| Algorithm | Description |
|-----------|-------------|
| [./dijkstra](./dijkstra) | Single-source shortest path, greedy with priority queue |
| [./bellmanFord](./bellmanFord) | Single-source shortest path with negative weights, cycle detection |
| [./floydWarshall](./floydWarshall) | All-pairs shortest path, O(V³) DP approach |

### Ordering

| Algorithm | Description |
|-----------|-------------|
| [./topologicalSort](./topologicalSort) | Linear ordering of DAG vertices using Kahn's algorithm |

## Notes

**Interview tip:** When asked about graph traversal, clarify:
1. Is the graph directed or undirected?
2. Is it weighted? (BFS only guarantees shortest path for unweighted)
3. Can there be cycles? (Need visited tracking)
4. Is it connected? (May need to handle disconnected components)
