# Dijkstra's Algorithm

## Background

Dijkstra's algorithm computes **single-source shortest paths** in a weighted graph with **non-negative edge weights**. It's the go-to algorithm for shortest path problems when all weights are non-negative.

**Intuition:** Greedily expand outward from the source, always processing the closest unvisited vertex. With non-negative weights, once a vertex is processed, its shortest distance is finalized.

## Core Idea

The algorithm maintains two sets:
- **Finalized:** Vertices whose shortest distance is known
- **Unfinalized:** Vertices still being explored

At each step, pick the unfinalized vertex with **minimum distance** and finalize it.

### Why Greedy Works (Non-Negative Weights Only)

When we pick vertex `u` with minimum distance `d`:
- All other unfinalized vertices have distance >= `d`
- Any path to `u` through them would be >= `d` (since edges are non-negative)
- Therefore, `d` is already the shortest possible distance to `u`

This greedy choice is **safe** only when all edge weights are >= 0.

### Why Greedy Fails (Negative Weights)

```
    0 --1--> 1
    |        |
    5       -10
    |        |
    v        v
    2 <------+

From source 0:
  - Process 1 first (dist=1), mark as finalized
  - Process 2 next (dist=5), mark as finalized
  - But path 0→1→2 = 1 + (-10) = -9 is shorter!
  - We already finalized 2, so we miss this shorter path.
```

## Algorithm

```
Dijkstra(graph, source):
    dist[] = infinity for all vertices
    dist[source] = 0
    parent[] = -1 for all vertices
    pq = min-heap with (source, 0)

    while pq not empty:
        (u, d) = pq.poll()  // Get minimum distance vertex

        if d > dist[u]:
            continue  // Already processed with shorter distance

        for each edge (u, v, weight):
            if dist[u] + weight < dist[v]:
                dist[v] = dist[u] + weight
                parent[v] = u
                pq.add((v, dist[v]))

    return dist, parent
```

### Key Implementation Details

1. **Priority Queue:** Use a min-heap keyed by distance
2. **Lazy Deletion:** Instead of decreasing key (complex), add duplicates and skip stale entries
3. **Skip Check:** `if d > dist[u]: continue` handles the lazy deletion

### Textbook vs Practical Implementation

Textbooks often describe Dijkstra using a **decrease-key** operation: when a shorter path to vertex `v` is found, update its priority in the queue. However, most standard priority queue implementations (like Java's `PriorityQueue`) don't support efficient decrease-key.

**Practical solution:** Use **lazy deletion** instead:
- When finding a shorter path, add a new entry `(v, new_dist)` to the queue
- Skip stale entries when polling: `if (d > dist[u]) continue`

**Why complexity is unaffected:** In the worst case (complete graph), `E = V²`. Each edge relaxation may add one entry to the queue, so the queue can have up to `O(E)` entries. Each operation is `O(log E) = O(log V²) = O(2 log V) = O(log V)`. Total: `O(E log V) = O((V + E) log V)` — same as the textbook version.

## Visual Walkthrough

```
Graph:
    0 --4--> 1 --1--> 3
    |        |
    2        2
    |        |
    v        v
    2 --3--> 4

Edges: (0,1,4), (0,2,2), (1,3,1), (1,4,2), (2,4,3)
Source: 0

Initial:
  dist = [0, ∞, ∞, ∞, ∞]
  pq = [(0, 0)]

Step 1: Poll (0, 0)
  Relax 0→1: dist[1] = 4
  Relax 0→2: dist[2] = 2
  dist = [0, 4, 2, ∞, ∞]
  pq = [(2, 2), (1, 4)]

Step 2: Poll (2, 2) - vertex 2 is closest
  Relax 2→4: dist[4] = 2+3 = 5
  dist = [0, 4, 2, ∞, 5]
  pq = [(1, 4), (4, 5)]

Step 3: Poll (1, 4)
  Relax 1→3: dist[3] = 4+1 = 5
  Relax 1→4: dist[4] = min(5, 4+2) = 5 (no change)
  dist = [0, 4, 2, 5, 5]
  pq = [(4, 5), (3, 5)]

Step 4: Poll (4, 5) - no outgoing edges
Step 5: Poll (3, 5) - no outgoing edges

Final: dist = [0, 4, 2, 5, 5]
```

## Complexity Analysis

| Implementation | Time | Space |
|----------------|------|-------|
| Binary Heap | `O((V + E) log V)` | `O(V)` |
| Fibonacci Heap | `O(V log V + E)` | `O(V)` |
| Array (no heap) | `O(V²)` | `O(V)` |

### When to Use Which

| Graph Type | Best Implementation |
|------------|---------------------|
| Sparse (E ≈ V) | Binary heap: `O(V log V)` |
| Dense (E ≈ V²) | Array: `O(V²)` or Binary heap: `O(V² log V)` |
| Very large, sparse | Fibonacci heap (theory), Binary heap (practice) |

**Interview tip:** Binary heap is the standard choice. Fibonacci heap has better theoretical complexity but is rarely used in practice due to high constants.

## Dijkstra vs Other Algorithms

| Aspect | Dijkstra | Bellman-Ford | Floyd-Warshall | BFS |
|--------|----------|--------------|----------------|-----|
| Problem | Single-source | Single-source | All-pairs | Single-source |
| Weights | Non-negative only | Any (detects neg cycles) | Any (detects neg cycles) | Unweighted only |
| Time | `O((V+E) log V)` | `O(V·E)` | `O(V³)` | `O(V+E)` |
| Approach | Greedy | Relax V-1 times | DP on intermediates | Level-order |

### When to Use Dijkstra

**Use Dijkstra when:**
- Single-source shortest path needed
- All edge weights are **non-negative**
- You want the fastest algorithm for this case

**Don't use Dijkstra when:**
- Graph has **negative edge weights** → Use Bellman-Ford
- You need **all-pairs** shortest paths → Use Floyd-Warshall (small V) or V × Dijkstra (sparse)
- Graph is **unweighted** → Use BFS (simpler, same complexity)

### Dijkstra vs BFS

| Aspect | Dijkstra | BFS |
|--------|----------|-----|
| Edge weights | Weighted (non-negative) | Unweighted (or all weight = 1) |
| Data structure | Priority Queue | Queue |
| Time | `O((V+E) log V)` | `O(V+E)` |
| When to use | Weighted graphs | Unweighted graphs |

**Interview tip:** If all edges have the same weight, BFS is simpler and faster than Dijkstra.

### Dijkstra vs Bellman-Ford

| Aspect | Dijkstra | Bellman-Ford |
|--------|----------|--------------|
| Negative weights | No | Yes |
| Negative cycles | Cannot detect | Detects them |
| Time | `O((V+E)log V)` | `O(V·E)` |
| Approach | Greedy | Relaxation |

**Interview tip:** "When would you use Bellman-Ford over Dijkstra?" → When edges can be negative, or when you need to detect negative cycles.

## Variants

### 1. Early Termination (Single Target)

If you only need the shortest path to one target:

```java
while (!pq.isEmpty()) {
    int[] curr = pq.poll();
    int u = curr[0];

    if (u == target) {
        return dist[target];  // Found it!
    }
    // ... rest of algorithm
}
```

### 2. K Shortest Paths

Find not just the shortest, but the k shortest paths. Requires modification to allow revisiting vertices.

### 3. Bidirectional Dijkstra

Run Dijkstra from both source and target simultaneously. Stop when searches meet. Can be up to 2× faster for point-to-point queries.

### 4. A* Search

Dijkstra with a heuristic: prioritize vertices that seem closer to the target.

```
priority = dist[v] + heuristic(v, target)
```

Used in pathfinding (games, maps) when you have geometric information.

## Common Pitfalls

### 1. Negative Weights
```java
// WRONG: Dijkstra with negative weights
// Use Bellman-Ford instead
```

### 2. Not Skipping Stale Entries
```java
// WRONG: Process every entry from priority queue
int[] curr = pq.poll();
// process...

// CORRECT: Skip if already processed with shorter distance
int[] curr = pq.poll();
if (curr[1] > dist[curr[0]]) continue;  // Stale entry
// process...
```

### 3. Integer Overflow
```java
// WRONG: Can overflow
if (dist[u] + weight < dist[v])

// CORRECT: Check for infinity first
if (dist[u] != Integer.MAX_VALUE && dist[u] + weight < dist[v])
```

## Applications

| Application | Why Dijkstra? |
|-------------|---------------|
| GPS Navigation | Find shortest route between locations |
| Network Routing (OSPF) | Find shortest path in network topology |
| Social Networks | Degrees of separation |
| Game AI | Pathfinding for characters |
| Flight Booking | Cheapest flight path |

## LeetCode Problems

| Problem | Description |
|---------|-------------|
| [743. Network Delay Time](https://leetcode.com/problems/network-delay-time/) | Classic single-source shortest path |
| [787. Cheapest Flights Within K Stops](https://leetcode.com/problems/cheapest-flights-within-k-stops/) | Dijkstra with constraint |
| [1514. Path with Maximum Probability](https://leetcode.com/problems/path-with-maximum-probability/) | Max-heap variant (maximize instead of minimize) |
| [1631. Path With Minimum Effort](https://leetcode.com/problems/path-with-minimum-effort/) | Dijkstra on grid |
| [778. Swim in Rising Water](https://leetcode.com/problems/swim-in-rising-water/) | Minimax path (modified Dijkstra) |
| [505. The Maze II](https://leetcode.com/problems/the-maze-ii/) | Dijkstra with special movement rules |

## Implementation Notes

### Priority Queue in Java

```java
// Min-heap by distance
PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);

// Add entries: {vertex, distance}
pq.offer(new int[]{source, 0});

// Poll minimum
int[] curr = pq.poll();
int vertex = curr[0];
int distance = curr[1];
```

### Adjacency List Representation

```java
// List of edges from each vertex
List<List<int[]>> graph = new ArrayList<>();
// graph.get(u) = list of {neighbor, weight} pairs
```

## Notes

- Dijkstra is optimal for single-source shortest path with non-negative weights
- For dense graphs, the simple `O(V²)` array-based version can outperform heap-based
- The algorithm naturally handles disconnected graphs (unreachable vertices stay at infinity)
- Dijkstra was conceived by Edsger Dijkstra in 1956 and published in 1959
