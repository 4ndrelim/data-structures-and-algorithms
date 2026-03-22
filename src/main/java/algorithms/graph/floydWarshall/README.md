# Floyd-Warshall Algorithm

## Background

Floyd-Warshall computes **shortest paths between all pairs** of vertices in a weighted graph. Unlike Dijkstra or Bellman-Ford (single-source), it finds the shortest path from **every** vertex to **every** other vertex in one run.

**Intuition:** For each possible intermediate vertex `k`, ask: "Can I get from `i` to `j` faster by going through `k`?"

### When to Use Floyd-Warshall

| Use Case | Why Floyd-Warshall |
|----------|-------------------|
| All-pairs shortest paths | Designed for this exact problem |
| Dense graphs | O(V³) is competitive with V × Dijkstra on dense graphs |
| Small graphs (V ≤ 400) | Simple implementation, O(V³) is acceptable |
| Negative edge weights | Handles them correctly (unlike Dijkstra) |
| Transitive closure | Minor modification computes reachability |

## Core Idea

The algorithm uses **dynamic programming** with a clever insight:

> Define `dist[k][i][j]` = shortest path from `i` to `j` using only vertices `{0, 1, ..., k-1}` as intermediates.

The recurrence is:
```
dist[k][i][j] = min(
    dist[k-1][i][j],           // Don't use k
    dist[k-1][i][k] + dist[k-1][k][j]  // Use k as intermediate
)
```

**Space optimization:** Since `dist[k]` only depends on `dist[k-1]`, we can use a single 2D array and update in-place.

### The Key Question

For each triple `(i, k, j)`, ask:

> "Is the path `i → k → j` shorter than the current best path `i → j`?"

If yes, update: `dist[i][j] = dist[i][k] + dist[k][j]`

## Algorithm

```
FloydWarshall(graph):
    n = number of vertices
    dist[][] = copy of graph adjacency matrix
    next[][] = initialized for path reconstruction

    // Consider each vertex as intermediate
    for k = 0 to n-1:
        for i = 0 to n-1:
            for j = 0 to n-1:
                if dist[i][k] + dist[k][j] < dist[i][j]:
                    dist[i][j] = dist[i][k] + dist[k][j]
                    next[i][j] = next[i][k]

    // Check for negative cycles
    for i = 0 to n-1:
        if dist[i][i] < 0:
            return "Negative cycle detected"

    return dist, next
```

### Why This Order (k, i, j)?

The order of loops matters. `k` must be the **outermost** loop because:

1. When computing paths using vertices `{0..k}`, we need all paths using `{0..k-1}` to be finalized
2. If `i` or `j` were outer, we'd be using incomplete information

**Interview tip:** If asked "why k is outer loop?", explain that each k-iteration builds on the previous, requiring all (i,j) pairs to be updated before moving to the next k.

## Visual Walkthrough

```
Graph:
    0 --3--> 1
    |        |
    8        -4
    |        |
    v        v
    2 <--2-- 3

Adjacency Matrix (INF = ∞):
      0    1    2    3
  0 [ 0,   3,   8,   ∞ ]
  1 [ ∞,   0,   ∞,  -4 ]
  2 [ ∞,   ∞,   0,   ∞ ]
  3 [ ∞,   ∞,   2,   0 ]

After k=0 (using vertex 0 as intermediate):
  No improvements (0 doesn't help reach anywhere faster)

After k=1 (using vertices {0,1} as intermediates):
  dist[0][3] = min(∞, dist[0][1] + dist[1][3]) = min(∞, 3 + (-4)) = -1
      0    1    2    3
  0 [ 0,   3,   8,  -1 ]  ← Updated!
  ...

After k=2 (using vertices {0,1,2}):
  No improvements

After k=3 (using vertices {0,1,2,3}):
  dist[0][2] = min(8, dist[0][3] + dist[3][2]) = min(8, -1 + 2) = 1
  dist[1][2] = min(∞, dist[1][3] + dist[3][2]) = min(∞, -4 + 2) = -2

Final:
      0    1    2    3
  0 [ 0,   3,   1,  -1 ]
  1 [ ∞,   0,  -2,  -4 ]
  2 [ ∞,   ∞,   0,   ∞ ]
  3 [ ∞,   ∞,   2,   0 ]

Shortest path 0→2: 0 → 1 → 3 → 2 with distance 1 (not direct edge 8!)
```

## Complexity Analysis

| Aspect | Complexity |
|--------|------------|
| Time | `O(V³)` |
| Space | `O(V²)` |

**Why O(V³)?** Three nested loops, each iterating V times.

**Space:** We store a V×V distance matrix (and optionally a V×V next matrix for path reconstruction).

### Comparison with Alternatives

| Approach | Time | Best For |
|----------|------|----------|
| Floyd-Warshall | `O(V³)` | Dense graphs, small V |
| V × Dijkstra | `O(V · (V + E) log V)` | Sparse graphs, non-negative weights |
| V × Bellman-Ford | `O(V² · E)` | Negative weights, sparse graphs |

**Interview tip:** For sparse graphs with non-negative weights, running Dijkstra V times is often faster than Floyd-Warshall.

## Floyd-Warshall vs Dijkstra

| Aspect | Floyd-Warshall | Dijkstra |
|--------|----------------|----------|
| Problem | All-pairs shortest path | Single-source shortest path |
| Time (all-pairs) | `O(V³)` | `O(V · (V + E) log V)` with heap |
| Negative weights | Yes | No (greedy fails) |
| Negative cycles | Detects them | Cannot handle |
| Graph representation | Adjacency matrix | Adjacency list preferred |
| Best for | Dense graphs, small V | Sparse graphs, non-negative |
| Implementation | Simple (3 nested loops) | More complex (priority queue) |

### When to Choose Which

**Use Floyd-Warshall when:**
- You need **all-pairs** shortest paths
- Graph is **dense** (E ≈ V²)
- Graph has **negative edge weights** (but no negative cycles)
- V is small (≤ 400-500)

**Use Dijkstra (V times) when:**
- You need **all-pairs** but graph is **sparse** (E << V²)
- All edge weights are **non-negative**
- V is large and graph is sparse

**Use single Dijkstra when:**
- You only need paths from **one source**

### Complexity Crossover

For all-pairs shortest paths:
- Floyd-Warshall: `O(V³)`
- V × Dijkstra: `O(V·(V + E) log V)`

On a **sparse graph** (E = O(V)):
- V × Dijkstra ≈ `O(V² log V)` — faster than `O(V³)`

On a **dense graph** (E = O(V²)):
- V × Dijkstra ≈ `O(V³ log V)` — slower than `O(V³)`

**Interview tip:** When asked "Dijkstra vs Floyd-Warshall?", clarify: (1) single-source or all-pairs? (2) negative weights? (3) dense or sparse?

## Negative Cycle Detection

A **negative cycle** exists if any vertex can reach itself with negative total weight. After running Floyd-Warshall:

```java
for (int i = 0; i < n; i++) {
    if (dist[i][i] < 0) {
        // Negative cycle exists!
    }
}
```

**Why?** If `dist[i][i] < 0`, there's a path from `i` back to `i` with negative weight—a negative cycle.

## Path Reconstruction

To reconstruct paths, maintain a `next[][]` matrix:
- `next[i][j]` = next vertex after `i` on the shortest path to `j`

```java
// Initialize
for each edge (i, j):
    next[i][j] = j

// Update during algorithm
if dist[i][k] + dist[k][j] < dist[i][j]:
    dist[i][j] = dist[i][k] + dist[k][j]
    next[i][j] = next[i][k]  // Go toward k first

// Reconstruct path from u to v
path = [u]
while u != v:
    u = next[u][v]
    path.append(u)
```

## Variants

### Transitive Closure (Warshall's Algorithm)

Find if there's **any** path from `i` to `j` (not shortest, just reachability):

```java
// Use boolean matrix instead of distances
for (int k = 0; k < n; k++)
    for (int i = 0; i < n; i++)
        for (int j = 0; j < n; j++)
            reach[i][j] = reach[i][j] || (reach[i][k] && reach[k][j]);
```

### Minimax / Maximin Path

Find path where the **maximum edge weight is minimized** (useful for capacity problems):

```java
dist[i][j] = min(dist[i][j], max(dist[i][k], dist[k][j]));
```

## Applications

| Application | Why Floyd-Warshall? |
|-------------|---------------------|
| Network routing tables | Precompute all shortest paths |
| Graph diameter | max(dist[i][j]) after running |
| Transitive closure | Reachability between all pairs |
| Finding graph center | Vertex minimizing max distance to others |
| Arbitrage detection | Negative cycles in currency graph |

## Implementation Notes

### Avoiding Overflow

When using `Integer.MAX_VALUE` for infinity:

```java
// BAD: Can overflow!
if (dist[i][k] + dist[k][j] < dist[i][j])

// GOOD: Check for infinity first
if (dist[i][k] != INF && dist[k][j] != INF
    && dist[i][k] + dist[k][j] < dist[i][j])

// OR: Use INF = Integer.MAX_VALUE / 2
```

### Adjacency Matrix Format

```java
// Initialize matrix
int[][] dist = new int[n][n];
for (int i = 0; i < n; i++) {
    Arrays.fill(dist[i], INF);
    dist[i][i] = 0;  // Distance to self is 0
}

// Add edges
dist[from][to] = weight;
```

## LeetCode Problems

| Problem | Description |
|---------|-------------|
| [1334. Find the City With the Smallest Number of Neighbors](https://leetcode.com/problems/find-the-city-with-the-smallest-number-of-neighbors-at-a-threshold-distance/) | Floyd-Warshall + count neighbors within threshold |
| [1462. Course Schedule IV](https://leetcode.com/problems/course-schedule-iv/) | Transitive closure (is course A prerequisite of B?) |
| [743. Network Delay Time](https://leetcode.com/problems/network-delay-time/) | Can use Floyd-Warshall (though Dijkstra is simpler) |

## Notes

- Floyd-Warshall is elegant but `O(V³)` limits it to small graphs (typically V ≤ 400-500)
- The algorithm is inherently sequential (unlike BFS/Dijkstra which can be parallelized)
- For sparse graphs, consider Johnson's algorithm: reweight edges + V × Dijkstra in `O(V² log V + VE)`
- Self-loops with positive weight don't affect results (dist[i][i] stays 0)
