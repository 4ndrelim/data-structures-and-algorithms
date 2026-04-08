# Topological Sort (Kahn's Algorithm)

## Background

Topological sort produces a **linear ordering** of vertices in a **Directed Acyclic Graph (DAG)** such that for every directed edge `u → v`, vertex `u` comes before `v` in the ordering.

**Intuition:** A vertex is ready to be processed only after all vertices pointing to it have been processed.

### When is Topological Sort Possible?

A topological ordering exists **if and only if** the graph is a DAG (no cycles). If a cycle exists, no valid ordering is possible, you can't have `u` before `v` and `v` before `u` simultaneously.

### Two Approaches

| Approach | Algorithm | Data Structure | Idea |
|----------|-----------|----------------|------|
| BFS-based | **Kahn's Algorithm** | Queue + In-degree array | Process vertices with no incoming edges |
| DFS-based | **DFS + Stack** | Stack + Visited array | Reverse post-order gives topological order |

This implementation uses **Kahn's Algorithm** (BFS-based).

## Kahn's Algorithm

### Core Idea

The algorithm is based on a simple observation: in a DAG, there must be at least one vertex with **in-degree 0** (no incoming edges). This vertex has no dependencies and can be placed first in the ordering.

### In-Degree

The **in-degree** of a vertex is the number of edges pointing **to** it:
- In-degree 0 → No prerequisites, can be processed immediately
- In-degree > 0 → Must wait for prerequisites to be processed

### Algorithm

```
KahnTopologicalSort(graph):
    inDegree[] = count incoming edges for each vertex
    queue = all vertices with inDegree[v] == 0
    result = []

    while queue not empty:
        u = queue.dequeue()
        result.add(u)

        for each neighbor v of u:
            inDegree[v]--
            if inDegree[v] == 0:
                queue.enqueue(v)

    if result.size() != numVertices:
        return "Cycle detected!"
    return result
```

### Visual Walkthrough

```
Graph (course prerequisites):
  5 → 2 → 3
  ↓   ↓
  0   1 → 4

Edges: 5→0, 5→2, 2→1, 2→3, 1→4

Initial in-degrees:
  Vertex:    0  1  2  3  4  5
  In-degree: 1  1  1  1  1  0

Step 1: Process vertex 5 (in-degree 0)
  Result: [5]
  Decrement: inDegree[0]--, inDegree[2]--
  In-degrees: [0, 1, 0, 1, 1, -]
  Queue: [0, 2]

Step 2: Process vertex 0 (in-degree 0)
  Result: [5, 0]
  No neighbors to update
  Queue: [2]

Step 3: Process vertex 2 (in-degree 0)
  Result: [5, 0, 2]
  Decrement: inDegree[1]--, inDegree[3]--
  In-degrees: [-, 0, -, 0, 1, -]
  Queue: [1, 3]

Step 4: Process vertex 1 (in-degree 0)
  Result: [5, 0, 2, 1]
  Decrement: inDegree[4]--
  Queue: [3, 4]

Step 5: Process vertices 3, 4
  Result: [5, 0, 2, 1, 3, 4]

Final: All 6 vertices processed → Valid topological order!
```

### Multiple Valid Orderings

A DAG can have **multiple valid topological orderings**. In the example above, `[5, 2, 0, 1, 3, 4]` is also valid. The specific ordering depends on which vertex with in-degree 0 is processed first.

**Interview tip:** When asked to verify a topological sort, check that for every edge `u → v`, vertex `u` appears before `v` in the ordering.

## Cycle Detection

Kahn's algorithm naturally detects cycles:

```
If result.size() < numVertices:
    // Some vertices never reached in-degree 0
    // They are part of or blocked by a cycle
    Cycle exists!
```

**Why?** In a cycle, every vertex has at least one incoming edge from another vertex in the cycle. No vertex in the cycle can ever reach in-degree 0.

```
Cycle example: A → B → C → A

In-degrees: A=1, B=1, C=1
No vertex has in-degree 0 → Queue starts empty → Result is empty
Result size (0) ≠ numVertices (3) → Cycle detected!
```

## Complexity Analysis

| Aspect | Complexity |
|--------|------------|
| Time | `O(V + E)` |
| Space | `O(V)` |

**Why O(V + E)?**
- Computing in-degrees: `O(E)` — iterate through all edges
- Processing vertices: `O(V)` — each vertex enqueued/dequeued once
- Processing edges: `O(E)` — each edge decrements in-degree once

## Kahn's vs DFS-based Topological Sort

| Aspect | Kahn's (BFS) | DFS-based |
|--------|--------------|-----------|
| Approach | Process in-degree 0 vertices | Reverse post-order |
| Cycle Detection | Natural (check result size) | Needs extra tracking (back edges) |
| Parallelization | Easier (vertices at same "level" can run in parallel) | Harder |
| Implementation | Iterative | Usually recursive |
| Output | Processes "sources" first | Processes "sinks" first (then reverses) |

**Interview tip:** Kahn's is often preferred in interviews because cycle detection is straightforward and the algorithm is intuitive.

## Applications

| Application | Why Topological Sort? |
|-------------|----------------------|
| Build systems (Make, Gradle) | Compile dependencies before dependents |
| Task scheduling | Execute prerequisites before dependent tasks |
| Course prerequisites | Determine valid course order |
| Package managers (npm, apt) | Install dependencies first |
| Spreadsheet cell evaluation | Evaluate cells in dependency order |
| Data serialization | Serialize objects before their references |

## LeetCode Problems

| Problem | Difficulty | Description |
|---------|------------|-------------|
| [207. Course Schedule](https://leetcode.com/problems/course-schedule/) | Medium | Detect if cycle exists (can finish all courses?) |
| [210. Course Schedule II](https://leetcode.com/problems/course-schedule-ii/) | Medium | Return valid course ordering |
| [269. Alien Dictionary](https://leetcode.com/problems/alien-dictionary/) | Hard | Build graph from sorted words, then topological sort |
| [310. Minimum Height Trees](https://leetcode.com/problems/minimum-height-trees/) | Medium | Iteratively remove leaves (similar to Kahn's) |
| [802. Find Eventual Safe States](https://leetcode.com/problems/find-eventual-safe-states/) | Medium | Reverse graph + topological sort |
| [1203. Sort Items by Groups](https://leetcode.com/problems/sort-items-by-groups-respecting-dependencies/) | Hard | Two-level topological sort |
| [2115. Find All Possible Recipes](https://leetcode.com/problems/find-all-possible-recipes-from-given-supplies/) | Medium | Topological sort with available supplies |

### Problem Patterns

**Pattern 1: Course Schedule (Cycle Detection)**
```
Given: numCourses, prerequisites = [[1,0], [2,1]]
       (meaning: to take course 1, you need course 0)
Question: Can you finish all courses?
Answer: Run Kahn's, return true if result.size() == numCourses
```

**Pattern 2: Course Schedule II (Find Ordering)**
```
Same input as above
Question: Return a valid course order
Answer: Return the result list from Kahn's algorithm
```

**Pattern 3: Alien Dictionary (Build Graph First)**
```
Given: Sorted words in alien language ["wrt", "wrf", "er", "ett", "rftt"]
Question: Derive character ordering
Steps:
  1. Compare adjacent words to find ordering constraints
     - "wrt" vs "wrf" → t < f
     - "wrf" vs "er" → w < e
     - "er" vs "ett" → r < t
     - "ett" vs "rftt" → e < r
  2. Build graph from constraints
  3. Topological sort to get character order
```

## Edge Cases

1. **Empty graph** (0 vertices) → Return empty list
2. **Single vertex** → Return `[0]`
3. **Disconnected DAG** → All components sorted together
4. **Self-loop** (`v → v`) → Cycle detected
5. **Multiple valid orderings** → Any valid ordering is acceptable

## Notes

- Kahn's algorithm can be modified to detect **all** vertices involved in cycles (not just detect existence)
- For **lexicographically smallest** topological order, use a min-heap instead of a queue
- The algorithm extends naturally to **parallel task scheduling**: all vertices with current in-degree 0 can be processed simultaneously
