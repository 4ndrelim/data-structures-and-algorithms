# Bellman-Ford Algorithm

## Background

The Bellman-Ford algorithm computes **single-source shortest paths** in a weighted graph. Unlike Dijkstra's algorithm, it handles **negative edge weights** and can **detect negative cycles**.

### Core Idea

The algorithm is based on a key insight: in a graph with `V` vertices, any shortest path contains at most `V-1` edges (otherwise it would have a cycle). Therefore:

1. Initialize distance to source as 0, all others as infinity
2. Relax all edges `V-1` times
3. If any edge can still be relaxed, a negative cycle exists

### Why V-1 Iterations Are Sufficient

**Intuition:** Consider the shortest path from source `s` to any vertex `t`:

```
s → v₁ → v₂ → ... → vₖ → t
```

This path has at most `V-1` edges (since it visits at most `V` vertices without repeating).

**Key insight:** After iteration `i`, we have the correct shortest distance for all vertices reachable via paths of `i` or fewer edges.

- **Iteration 1:** Correct distances for vertices 1 edge from source
- **Iteration 2:** Correct distances for vertices <=2 edges from source
- **Iteration i:** Correct distances for vertices <=i edges from source
- **Iteration V-1:** Correct distances for all reachable vertices

**Why?** Each iteration, the "frontier" of correct distances expands by one edge. If `dist[u]` is correct, then relaxing `(u, v)` gives the correct contribution from the path through `u`.

### Relaxation

An edge `(u, v)` with weight `w` is **relaxed** if going through `u` gives a shorter path to `v`:

```
if dist[u] + w < dist[v]:
    dist[v] = dist[u] + w
    parent[v] = u
```

### Algorithm

```
BellmanFord(graph, source):
    dist[] = infinity for all vertices
    dist[source] = 0
    parent[] = -1 for all vertices

    // Relax all edges V-1 times
    for i = 1 to V-1:
        for each edge (u, v, w):
            if dist[u] + w < dist[v]:
                dist[v] = dist[u] + w
                parent[v] = u

    // Check for negative cycles
    for each edge (u, v, w):
        if dist[u] + w < dist[v]:
            return "Negative cycle detected"

    return dist[], parent[]
```

### Visual Walkthrough

```
Graph with negative edge:
    0 --4--> 1
    |        |
    2        -3
    |        |
    v        v
    3 <--1-- 2

Edges: (0,1,4), (0,3,2), (1,2,-3), (2,3,1)
Source: 0

Iteration 0 (init):
  dist = [0, INF, INF, INF]

Iteration 1:
  Relax (0,1,4): dist[1] = 0+4 = 4
  Relax (0,3,2): dist[3] = 0+2 = 2
  dist = [0, 4, INF, 2]

Iteration 2:
  Relax (1,2,-3): dist[2] = 4+(-3) = 1
  dist = [0, 4, 1, 2]

Iteration 3:
  Relax (2,3,1): dist[3] = min(2, 1+1) = 2 (no change)
  dist = [0, 4, 1, 2]

Final: Shortest paths from 0: [0, 4, 1, 2]
```

## Complexity Analysis

| Aspect | Complexity |
|--------|------------|
| Time | `O(V * E)` |
| Space | `O(V)` |

Where:
- `V` = number of vertices
- `E` = number of edges

**Why O(V * E)?** We iterate `V-1` times, and each iteration processes all `E` edges.

## Negative Cycle Detection

A **negative cycle** is a cycle where the sum of edge weights is negative. If such a cycle is reachable from the source, shortest paths are undefined (you can always go around the cycle again to get a shorter path).

### Detection Method

After `V-1` iterations, all shortest paths are finalized (if no negative cycle). Running one more iteration:
- If any edge can still be relaxed → negative cycle exists
- If no relaxation possible → no negative cycle

```java
// After V-1 iterations
for (Edge edge : edges) {
    if (dist[edge.from] + edge.weight < dist[edge.to]) {
        // Negative cycle detected!
        return true;
    }
}
```

### Finding the Negative Cycle

To find the actual cycle (not just detect it):
1. Run the V-th iteration and record any vertex `v` that gets relaxed
2. Follow parent pointers from `v` for `V` steps (guaranteed to be in the cycle)
3. From that vertex, follow parent pointers until you return to it

## Bellman-Ford vs Dijkstra

| Aspect | Bellman-Ford | Dijkstra |
|--------|--------------|----------|
| Time Complexity | `O(V * E)` | `O((V + E) log V)` |
| Negative Weights | Yes | No |
| Negative Cycle Detection | Yes | No |
| Greedy | No | Yes |
| Use Case | Negative weights, cycle detection | Non-negative weights, performance |

**Interview tip:** If asked "when to use Bellman-Ford over Dijkstra?", the answer is: (1) when edges can have negative weights, or (2) when you need to detect negative cycles.

## Variants and Optimizations

### 1. Early Termination

**Intuition:** Each iteration should "fix" at least one more vertex's distance. If nothing changes:
- Either we've found all shortest paths (done!)
- Or the graph has fewer edges in shortest paths than `V-1`

So, if no relaxation occurs in an iteration, the algorithm can terminate early:

```java
for (int i = 0; i < V - 1; i++) {
    boolean relaxed = false;
    for (Edge e : edges) {
        if (relax(e)) relaxed = true;
    }
    if (!relaxed) break;  // Early termination
}
```

### 2. SPFA (Shortest Path Faster Algorithm)

An optimization that uses a queue to only process vertices whose distances changed:

```
SPFA(graph, source):
    queue = [source]
    inQueue[source] = true

    while queue not empty:
        u = queue.dequeue()
        inQueue[u] = false
        for each edge (u, v, w):
            if dist[u] + w < dist[v]:
                dist[v] = dist[u] + w
                if not inQueue[v]:
                    queue.enqueue(v)
                    inQueue[v] = true
```

- **Average case:** Much faster than standard Bellman-Ford
- **Worst case:** Still `O(V * E)` (dense graphs with negative edges)
- **Cycle detection:** Track how many times each vertex is enqueued; if > V, negative cycle exists

### 3. Yen's Improvement
Yen's improvement (Jin Y. Yen, 1970) optimizes Bellman-Ford by ordering edge relaxations to propagate distance information faster. Partition edges into two sets and alternate relaxation order, reducing iterations in practice.

## Application: Distance-Vector Routing

Bellman-Ford is the theoretical foundation for **Distance-Vector Routing Protocols** used in computer networks, such as **RIP (Routing Information Protocol)**.

<details>

### How It Works in Networks

Each router maintains a **distance vector** (table of shortest distances to all destinations):

1. **Initialize:** Each router knows distance to directly connected neighbors
2. **Exchange:** Periodically, routers share their distance vectors with neighbors
3. **Update:** Upon receiving a neighbor's vector, update own distances using Bellman-Ford relaxation:
   ```
   dist[dest] = min(dist[dest], dist[neighbor] + neighbor.dist[dest])
   ```
4. **Converge:** After enough exchanges, all routers have correct shortest paths

### The Count-to-Infinity Problem

A major issue with distance-vector routing occurs when a link fails:

```
Network: A --1-- B --1-- C

Initial state: A knows dist[C] = 2 (via B)
               B knows dist[C] = 1 (direct)

Link B-C fails:
  - B updates: dist[C] = infinity
  - But B receives A's vector: dist[C] = 2
  - B thinks: "I can reach C via A with cost 1 + 2 = 3"
  - B updates: dist[C] = 3
  - A receives B's update: dist[C] = 3
  - A thinks: "I can reach C via B with cost 1 + 3 = 4"
  ...and so on, counting to infinity!
```

### Solutions to Count-to-Infinity

| Technique | Description |
|-----------|-------------|
| **Split Horizon** | Don't advertise a route back to where you learned it |
| **Poison Reverse** | Advertise infinity for routes learned from a neighbor back to that neighbor |
| **Hold-down Timer** | After a route fails, ignore updates for that route for a period |
| **Maximum Hop Count** | Define infinity as a small number (RIP uses 16) |

**Interview tip:** If asked about Bellman-Ford in networking context, mention RIP, distance-vector routing, and the count-to-infinity problem.
</details>

## More Applications

| Application | Why Bellman-Ford? |
|-------------|-------------------|
| Network routing (RIP, BGP) | Distributed computation, handles changing topologies |
| Arbitrage detection | Negative cycles = arbitrage opportunity |
| Currency exchange | Find best conversion path, detect arbitrage |
| Constraint satisfaction | Difference constraints can be modeled as shortest paths |

### Arbitrage Detection Example

In currency exchange, a negative cycle means you can profit by trading in a circle:

```
USD -> EUR -> GBP -> USD

If: 1 USD = 0.9 EUR
    1 EUR = 0.8 GBP
    1 GBP = 1.5 USD

Then: 1 USD -> 0.9 EUR -> 0.72 GBP -> 1.08 USD (8% profit!)
```

To detect this with Bellman-Ford:
1. Take negative log of exchange rates as edge weights
2. A negative cycle in this graph = arbitrage opportunity

## Notes

- Bellman-Ford is inherently sequential in its standard form, making it harder to parallelize than Dijkstra
- For dense graphs, `O(V * E)` can approach `O(V^3)`, making Floyd-Warshall competitive for all-pairs shortest paths
- The algorithm naturally handles disconnected graphs (unreachable vertices remain at infinity)
- When implementing, be careful with integer overflow when adding to `Integer.MAX_VALUE`
