# Union Find / Disjoint Set

## Background

A disjoint-set structure also known as a union-find or merge-find set, is a data structure
keeps track of a partition of a set into disjoint (non-overlapping) subsets. In CS2040s, this
is introduced in the context of checking for dynamic connectivity. For instance, Kruskal's algorithm
in graph theory to find minimum spanning tree of the graph utilizes disjoint set to efficiently
query if there exists a path between 2 nodes. <br>
It supports 2 main operations:

1. Union: Join two subsets into a single subset
2. Find: Determine which subset a particular element is in. In practice, this is often done to check
   if two elements are in the same subset or component.

The Disjoint Set structure is often introduced in 3 parts, with each iteration being better than the
previous either in time or space complexity (or both). More details can be found in the respective folders. 
Below is a brief overview:

1. Quick Find - Elements are assigned a component identity. 
Querying for connectivity and updating usually tracked with an internal array.

2. Quick Union - Component an element belongs to is now tracked with a tree structure. Nothing to enforce
a balanced tree and hence complexity does not necessarily improve
   - Note, this is not implemented but details can be found under weighted union folder.

3. Weighted Union - Same idea of using a tree, but constructed in a way that the tree is balanced, leading to improved
complexities. Can be further augmented with path compression.
