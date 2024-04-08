# Prim's Algorithm

## Background

Prim's Algorithm is a greedy algorithm that finds the minimum spanning tree of a graph by starting from an
arbitrary node (vertex) and adding the edge, with the minimum weight that connects the current tree to an unexplored
node, and the unexplored node to the current tree, until all nodes are included in the tree.

### Implementation Details

A priority queue (binary heap) is utilised to keep track of the minimum weight edge that connects the current tree to an
unexplored node. In an ideal scenario, the minimum weight edge to each node in the priority queue should be updated each
time a lighter edge is found to maintain a single unique node in the priority queue. This means that a decrease key
operation is required. However, we know that the decrease key operation of a binary heap implementation of a priority
queue will take O(V) time, which will result in a larger time complexity for the entire algorithm compared to using only
O(log V) operations for each edge.

Hence, in our implementation, to avoid the use of a decrease key operation, we will simply insert duplicate nodes with
their new minimum weight edge, which will take O(log E) = O(log V) given an upper bound of E = V^2, into the queue,
while leaving the old node in the queue. Additionally, we will track if a node has already been added into the MST to
avoid adding duplicate nodes.

Note that a priority queue is an abstract data type that can be implemented using different data structures. In this
implementation, the default Java `PriorityQueue` is used, which is a binary heap. By implementing the priority queue
with an AVL tree, a decrease key operation that has a time complexity of O(log V) can also be achieved.

## Complexity Analysis

**Time Complexity:**
- O(V^2 log V) for the basic version with an adjacency matrix, where V is the number of vertices.
- O(E log V) with a binary heap and adjacency list, where V and E is the number of vertices and edges
respectively.

**Space Complexity:**
- O(V^2) for the adjacency matrix representation.
- O(V + E) for the adjacency list representation.

## Notes

### Difference between Prim's Algorithm and Dijkstra's Algorithm

|                                     | Prim's Algorithm                                                                | Dijkstra's Algorithm                                     |
|-------------------------------------|---------------------------------------------------------------------------------|----------------------------------------------------------|
| Purpose                             | Finds MST - minimum sum of edge weights that includes all vertices in the graph | Finds shortest path from a single source to all vertices |
| Property Compared in Priority Queue | Minimum weight of incoming edge to a vertex                                     | Minimum distance from source vertex to current vertex    |

