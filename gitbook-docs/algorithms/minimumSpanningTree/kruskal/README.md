# Kruskal's Algorithm

## Background

Kruskal's algorithm finds the Minimum Spanning Tree by processing edges in order of weight, greedily adding edges that don't form cycles.

**Intuition:** Sort edges by weight. Greedily add the lightest edge that doesn't create a cycle. This works because of the **cycle property**: the heaviest edge in any cycle is never in the MST.

## Algorithm

```
Kruskal(vertices, edges):
    sort edges by weight (ascending)
    initialize each vertex as its own component (Union-Find)
    mst = []

    for each edge (u, v, weight) in sorted order:
        if u and v are in different components:
            add edge to mst
            union(u, v)  // merge components

        if mst has V-1 edges:
            break  // MST complete

    return mst
```

### Why Disjoint Set (Union-Find)?

The key operation is: "Does adding edge (u, v) create a cycle?"

This is equivalent to: "Are u and v already connected?"

[**Disjoint Set**](../../../dataStructures/disjointSet/weightedUnion) answers this in nearly `O(1)` time:
- `find(u, v)`: Are u and v in the same component?
- `union(u, v)`: Merge their components

Without Union-Find, we'd need `O(V)` traversal to check connectivity.

## Visual Walkthrough

```
Graph edges (sorted by weight):
  (0,1,1), (1,2,2), (0,2,3), (2,3,4), (1,3,5)

     0 ---1--- 1
     |         |
     3         2
     |         |
     +----+----+
          |
          4
          |
          3

Step 1: Process (0,1,1)
  - 0 and 1 in different components → ADD
  - MST: [(0,1,1)], weight = 1

Step 2: Process (1,2,2)
  - 1 and 2 in different components → ADD
  - MST: [(0,1,1), (1,2,2)], weight = 3

Step 3: Process (0,2,3)
  - 0 and 2 in SAME component (via 0-1-2) → SKIP (would create cycle)

Step 4: Process (2,3,4)
  - 2 and 3 in different components → ADD
  - MST: [(0,1,1), (1,2,2), (2,3,4)], weight = 7

Done! MST has V-1 = 3 edges.

Final MST:
  0 ---1--- 1
            |
            2
            |
            3
```

## Complexity Analysis

| Aspect | Complexity |
|--------|------------|
| Sort edges | `O(E log E)` |
| Union-Find operations | `O(E α(V))` where α is inverse Ackermann |
| **Total Time** | `O(E log E)` = `O(E log V)` since E ≤ V² |
| **Space** | `O(V)` for Union-Find |

**Note:** `α(V)` is the inverse Ackermann function, which is effectively constant (≤ 5) for any practical input size.

## Kruskal vs Prim

| Aspect | Kruskal | Prim |
|--------|---------|------|
| Strategy | Process edges globally | Grow tree locally |
| Data Structure | Disjoint Set | Priority Queue |
| Best for input | Edge list | Adjacency list/matrix |
| Best for graph | Sparse (E ≈ V) | Dense (E ≈ V²) |

**Interview tip:** Kruskal is often simpler to implement for problems where edges are given as a list.

## Implementation Notes

### Edge List Format

```java
// Edges as [from, to, weight] triples
int[][] edges = {
    {0, 1, 1},
    {1, 2, 2},
    {0, 2, 3}
};

Kruskal.Result result = Kruskal.mst(3, edges);
// result.mstEdges = edges in MST
// result.totalWeight = sum of weights
```

### From Adjacency Matrix

```java
int[][] matrix = {
    {0, 1, 3},
    {1, 0, 2},
    {3, 2, 0}
};

int[][] edges = Kruskal.matrixToEdges(matrix);
Kruskal.Result result = Kruskal.mst(3, edges);
```

## Common Pitfalls

### 1. Forgetting to Check for Cycles
```java
// WRONG: Add all edges
for (int[] edge : sortedEdges) {
    mstEdges.add(edge);
}

// CORRECT: Only add if no cycle
for (int[] edge : sortedEdges) {
    if (!ds.find(u, v)) {  // Different components
        mstEdges.add(edge);
        ds.union(u, v);
    }
}
```

### 2. Not Stopping at V-1 Edges
```java
// Optimization: MST has exactly V-1 edges
if (mstEdges.size() == numVertices - 1) {
    break;
}
```

## Applications

| Application | Why Kruskal? |
|-------------|--------------|
| Network design | Find cheapest way to connect all nodes |
| Clustering | Remove k-1 heaviest MST edges → k clusters |
| Maze generation | Random edge weights + MST = random maze |

## LeetCode Problems

| Problem | Description |
|---------|-------------|
| [1584. Min Cost to Connect All Points](https://leetcode.com/problems/min-cost-to-connect-all-points/) | MST on 2D points (Manhattan distance) |
| [1135. Connecting Cities With Minimum Cost](https://leetcode.com/problems/connecting-cities-with-minimum-cost/) | Direct Kruskal application |
| [1489. Find Critical and Pseudo-Critical Edges](https://leetcode.com/problems/find-critical-and-pseudo-critical-edges-in-minimum-spanning-tree/) | Classify MST edges |
| [1579. Remove Max Number of Edges](https://leetcode.com/problems/remove-max-number-of-edges-to-keep-graph-fully-traversable/) | Two simultaneous MSTs |
