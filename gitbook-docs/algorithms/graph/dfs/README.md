# Depth-First Search (DFS)

## Background

DFS explores a graph by going **as deep as possible** along each branch before backtracking. It can be implemented recursively (using the call stack) or iteratively (using an explicit stack).

## Recursive vs Iterative

| Aspect | Recursive | Iterative |
|--------|-----------|-----------|
| Code simplicity | Simpler, more intuitive | More verbose |
| Stack management | Implicit (call stack) | Explicit (Stack/Deque) |
| Stack overflow risk | Yes, for deep graphs | No |
| Traversal order | Natural | Requires reverse push for same order |

## Algorithm

### Recursive

```
DFS(graph, node, visited, result):
    visited[node] = true
    result.add(node)

    for neighbor in graph[node]:
        if not visited[neighbor]:
            DFS(graph, neighbor, visited, result)
```

### Iterative

```
DFS(graph, start):
    stack = [start]
    visited = {}
    result = []

    while stack not empty:
        node = stack.pop()
        if node in visited:
            continue
        visited.add(node)
        result.add(node)

        for neighbor in reverse(graph[node]):  // Reverse for same order as recursive
            if neighbor not in visited:
                stack.push(neighbor)

    return result
```

## Visual Walkthrough

```
Graph:
       0
      / \
     1   2
    / \
   3   4

DFS from 0 (recursive):

Call DFS(0): Visit 0, recurse on 1
  Call DFS(1): Visit 1, recurse on 3
    Call DFS(3): Visit 3, no neighbors, return
  Back to DFS(1): recurse on 4
    Call DFS(4): Visit 4, no neighbors, return
  Back to DFS(1): done, return
Back to DFS(0): recurse on 2
  Call DFS(2): Visit 2, no neighbors, return
Back to DFS(0): done

Result: [0, 1, 3, 4, 2]
```

## Complexity Analysis

| Case | Time | Space |
|------|------|-------|
| All cases | `O(V + E)` | `O(V)` |

Where:
- `V` = number of vertices
- `E` = number of edges
- Space is `O(V)` for visited set and stack (worst case: linear graph)

## Key Implementation Details

### Iterative: Matching Recursive Order

To get the same traversal order as recursive DFS, push neighbors in **reverse order**:

```java
// To visit neighbors in order [1, 2, 3], push in reverse [3, 2, 1]
List<Integer> neighbors = graph.get(node);
for (int i = neighbors.size() - 1; i >= 0; i--) {
    stack.push(neighbors.get(i));
}
```

### Two Marking Strategies (Iterative)

**Strategy 1: Mark when popping** (shown in algorithm above)
- Simpler logic
- Same node may be pushed multiple times
- Works correctly, just slightly less efficient

**Strategy 2: Mark when pushing**
- More efficient (no duplicates in stack)
- Need to handle start node specially

**Interview tip:** Both strategies are correct. Strategy 1 is easier to implement under pressure.

## Applications

| Application | Why DFS? |
|-------------|----------|
| Cycle detection | Easy to track back edges |
| Topological sort | Post-order gives reverse order |
| Connected components | Find all reachable nodes |
| Path finding (any path) | Memory efficient |
| Maze generation | Creates interesting mazes |
| Finding bridges/articulation points | Uses DFS tree properties |

## Notes

- DFS does **not** guarantee shortest path
- For shortest path in unweighted graphs, use BFS instead
- Recursive DFS risks stack overflow on deep graphs (e.g., linked list of 10,000 nodes)
- Iterative DFS avoids this by using heap memory for the explicit stack
