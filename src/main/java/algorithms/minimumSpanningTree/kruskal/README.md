# Kruskal's Algorithm

## Background
Kruskal's Algorithm is a greedy algorithm used to find the minimum spanning tree (MST) of a connected, weighted graph.
It works by sorting all the edges in the graph by their weight in non-decreasing order and then adding the smallest edge
to the MST, provided it does not form a cycle with the already included edges. This is repeated until all vertices are
included in the MST.

## Implementation Details
Kruskal's Algorithm uses a simple `ArrayList` to sort the edges by weight. 

A [`DisjointSet`](/dataStructures/disjointSet/weightedUnion) data structure is also used to keep track of the
connectivity of vertices and detect cycles.

## Complexity Analysis

**Time Complexity:**
Sorting the edges by weight: O(E log E) = O(E log V), where V and E is the number of vertices and edges respectively.
Union-Find operations: O(E α(V)), where α is the inverse Ackermann function.
Overall complexity: O(E log V)

**Space Complexity:**
O(V + E) for the storage of vertices in the disjoint set and edges in the priority queue.