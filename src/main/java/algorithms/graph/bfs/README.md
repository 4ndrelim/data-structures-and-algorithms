# Breadth-First Search (BFS)

## Background

BFS explores a graph **level by level**, visiting all nodes at distance `d` from the source before visiting nodes at distance `d+1`. It uses a **Queue** (FIFO) to maintain the frontier of nodes to explore.

### Algorithm

```
BFS(graph, start):
    queue = [start]
    visited = {start}
    result = []

    while queue not empty:
        node = queue.poll()
        result.add(node)

        for neighbor in graph[node]:
            if neighbor not in visited:
                visited.add(neighbor)
                queue.offer(neighbor)

    return result
```

### Visual Walkthrough

```
Graph:
       0
      / \
     1   2
    / \
   3   4

BFS from 0:

Step 1: Queue=[0], Visited={0}
        Poll 0, add neighbors 1,2

Step 2: Queue=[1,2], Visited={0,1,2}
        Poll 1, add neighbors 3,4

Step 3: Queue=[2,3,4], Visited={0,1,2,3,4}
        Poll 2 (no new neighbors)

Step 4: Queue=[3,4]
        Poll 3, Poll 4

Result: [0, 1, 2, 3, 4]
```

## Complexity Analysis

| Case | Time | Space |
|------|------|-------|
| All cases | `O(V + E)` | `O(V)` |

Where:
- `V` = number of vertices
- `E` = number of edges
- Space is `O(V)` for the visited set and queue (worst case: all nodes in queue)

## Key Implementation Detail

**Mark visited when enqueueing, not when dequeueing:**

```java
// PREFERRED: Mark visited when adding to queue
if (!visited[neighbor]) {
    visited[neighbor] = true;  // Mark NOW
    queue.offer(neighbor);
}

// WORKS but inefficient: Mark visited when polling
// Same node may be added to queue multiple times via different paths
```

**Interview tip:** Both approaches produce correct traversal, but marking on enqueue is more efficient—each node enters the queue at most once. Marking on poll can cause the same node to be enqueued multiple times, wasting memory and iterations.

## Applications

| Application | Why BFS? |
|-------------|----------|
| Shortest path (unweighted) | First path found is shortest |
| Level-order tree traversal | Natural level-by-level order |
| Finding connected components | Visit all reachable nodes |
| Web crawler (limited depth) | Control exploration depth |
| Social network "degrees of separation" | Find minimum hops |

There are modified BFS approaches that allow revisits to nodes if a shorter path is found. This adaptation enables BFS to be used for finding shortest paths in certain types of weighted graphs. But doing so, we will lose the time compelxity guarantee, potentially making it exponential in the worst case.

## Notes

- BFS guarantees **shortest path** in unweighted graphs (measured by number of edges)
- For weighted graphs, use Dijkstra's algorithm instead
- BFS uses more memory than DFS when the graph is wide (many neighbors per node)
- The order of neighbors in the adjacency list affects the traversal order (but not correctness)
