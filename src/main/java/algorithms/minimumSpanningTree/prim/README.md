# Prim's Algorithm

## Background

Prim's algorithm finds the Minimum Spanning Tree by growing a tree from a starting vertex, always adding the lightest edge that connects the tree to a new vertex.

**Intuition:** Start with any vertex. Repeatedly add the cheapest edge that connects the current tree to a vertex not yet in the tree. This works because of the **cut property**: the minimum weight edge crossing any cut must be in the MST.

## Algorithm

```
Prim(graph, start):
    minWeight[v] = infinity for all v
    minWeight[start] = 0
    parent[v] = -1 for all v
    pq = min-heap with (start, 0)

    while pq not empty:
        (u, weight) = pq.poll()  // Get minimum weight vertex

        if u already in MST:
            continue  // Lazy deletion

        add u to MST
        totalWeight += weight
        if parent[u] != -1:
            add edge (parent[u], u, weight) to MST

        for each edge (u, v, edgeWeight):
            if v not in MST and edgeWeight < minWeight[v]:
                minWeight[v] = edgeWeight
                parent[v] = u
                pq.add((v, edgeWeight))

    return MST edges, totalWeight
```

### Similarity to Dijkstra

Prim's and Dijkstra's algorithms have nearly identical structure:

| Aspect | Prim's | Dijkstra's |
|--------|--------|------------|
| Priority | Edge weight to vertex | Total distance from source |
| Comparison | `edgeWeight < minWeight[v]` | `dist[u] + weight < dist[v]` |
| Goal | Minimum total edge weight | Minimum path distances |

**Interview tip:** "Prim's is Dijkstra with edge weight instead of path distance."

## Visual Walkthrough

```
Graph (adjacency list):
  0: [(1,4), (2,2)]
  1: [(0,4), (2,1), (3,5)]
  2: [(0,2), (1,1), (3,3)]
  3: [(1,5), (2,3)]

     0
    /|\
   4 | 2
  /  |  \
 1---1---2
  \     /
   5   3
    \ /
     3

Start from vertex 0:

Step 1: Process (0, 0)
  - Add 0 to MST
  - Update: minWeight[1]=4, minWeight[2]=2
  - PQ: [(2,2), (1,4)]

Step 2: Process (2, 2)
  - Add 2 to MST, add edge (0,2,2)
  - Update: minWeight[1]=min(4,1)=1, minWeight[3]=3
  - PQ: [(1,1), (1,4), (3,3)]

Step 3: Process (1, 1)
  - Add 1 to MST, add edge (2,1,1)
  - 3 already has minWeight=3, edge weight 5 > 3, no update
  - PQ: [(3,3), (1,4)]

Step 4: Process (3, 3)
  - Add 3 to MST, add edge (2,3,3)
  - PQ: [(1,4)]

Step 5: Process (1, 4)
  - 1 already in MST → SKIP (lazy deletion)

Done! MST edges: (0,2,2), (2,1,1), (2,3,3)
Total weight: 6

Final MST:
     0
      \
       2
      /|
     1 |
       3
       |
       2
```

## Complexity Analysis

| Implementation | Time | Space |
|----------------|------|-------|
| Binary Heap + Adjacency List | `O(E log V)` | `O(V + E)` |
| Binary Heap + Adjacency Matrix | `O(V² log V)` | `O(V²)` |
| Array (no heap) + Matrix | `O(V²)` | `O(V²)` |

### Lazy Deletion

Like Dijkstra, we use **lazy deletion** instead of decrease-key:
- When a lighter edge is found, add a new entry to the PQ
- Skip vertices already in MST when polling
- Complexity unaffected: `O(E log V)` since `log(E) = O(log V)`

## Prim vs Kruskal

| Aspect | Prim | Kruskal |
|--------|------|---------|
| Strategy | Grow tree from start | Process edges globally |
| Data Structure | Priority Queue | Disjoint Set |
| Best for input | Adjacency list/matrix | Edge list |
| Best for graph | Dense (E ≈ V²) | Sparse (E ≈ V) |
| Property used | Cut property | Cycle property |

**Interview tip:** For dense graphs or when starting vertex matters, use Prim. For sparse graphs with edge list input, use Kruskal.

## Prim vs Dijkstra

| Aspect | Prim's | Dijkstra's |
|--------|--------|------------|
| Goal | Minimum Spanning Tree | Single-source shortest paths |
| Priority | Edge weight | Total path distance |
| Output | Tree with min total weight | Shortest distances from source |
| Negative weights | Works (no path concept) | Fails (greedy assumption) |

```java
// Prim: priority = edge weight
if (edgeWeight < minWeight[v]) {
    minWeight[v] = edgeWeight;  // Just the edge
}

// Dijkstra: priority = path distance
if (dist[u] + weight < dist[v]) {
    dist[v] = dist[u] + weight;  // Cumulative
}
```

## Implementation Notes

### Adjacency List Format

```java
// Build graph from edges
int[][] edges = {
    {0, 1, 4},
    {0, 2, 2},
    {1, 2, 1}
};
List<List<Prim.Edge>> graph = Prim.buildGraph(3, edges);

// Run Prim's
Prim.Result result = Prim.mst(3, graph);
// result.mstEdges = edges in MST
// result.totalWeight = sum of weights
```

## Common Pitfalls

### 1. Not Using Lazy Deletion
```java
// WRONG: No skip check
int[] curr = pq.poll();
// process...

// CORRECT: Skip if already in MST
int[] curr = pq.poll();
if (inMST[curr[0]]) continue;
// process...
```

### 2. Updating Instead of Adding
```java
// Java's PriorityQueue doesn't support efficient decrease-key
// WRONG: Try to update priority
pq.remove(oldEntry);  // O(V) operation!
pq.add(newEntry);

// CORRECT: Just add new entry, skip stale ones later
pq.add(newEntry);
```

## Applications

| Application | Why Prim? |
|-------------|-----------|
| Network design | Connect all nodes with minimum cable |
| Circuit design | Minimum wire length |
| Real-time MST | Can grow incrementally from any start |

## LeetCode Problems

| Problem | Description |
|---------|-------------|
| [1584. Min Cost to Connect All Points](https://leetcode.com/problems/min-cost-to-connect-all-points/) | MST on point coordinates |
| [1135. Connecting Cities With Minimum Cost](https://leetcode.com/problems/connecting-cities-with-minimum-cost/) | Direct Prim application |
| [1168. Optimize Water Distribution](https://leetcode.com/problems/optimize-water-distribution-in-a-village/) | MST with virtual node |
| [778. Swim in Rising Water](https://leetcode.com/problems/swim-in-rising-water/) | Modified Prim (minimax path) |
