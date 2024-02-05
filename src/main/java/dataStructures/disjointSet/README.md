# Union Find / Disjoint Set

## Background

A disjoint-set structure also known as a union-find or merge-find set, is a data structure
keeps track of a partition of a set into disjoint (non-overlapping) subsets. 

In CS2040s, this is introduced in the context of checking for dynamic connectivity. For instance, Kruskal's algorithm
in graph theory to find minimum spanning tree of a graph utilizes disjoint set to efficiently
query if there already exists a path between 2 nodes.

Generally, there are 2 main operations:

1. **Union**: Join two subsets into a single subset
2. **Find**: Determine which subset a particular element is in. In practice, this is often done to check
   if two elements are in the same subset or component.

The Disjoint Set structure is often introduced in 3 parts, with each iteration being better than the
previous either in time or space complexity (or both). More details can be found in the respective folders. 
Below is a brief overview:

1. **Quick Find** - Elements are assigned a component identity. 
Querying for connectivity and updating usually tracked with an internal array.

2. **Quick Union** - Component an element belongs to is now tracked with a tree structure. Nothing to enforce
a balanced tree and hence complexity does not necessarily improve
   - Note, this is not implemented but details can be found under weighted union folder.

3. **Weighted Union** - Same idea of using a tree, but constructed in a way that the tree is balanced, leading to 
4. improved complexities. Can be further augmented with path compression.

## Applications
Because of its efficiency and simplicity in implementing, Disjoint Set structures are widely used in practice:
1. As mentioned, it is often used as a helper structure for Kruskal's MST algorithm
2. It can be used in the context of network connectivity
   - Managing a network of computers 
   - Or even analyse social networks, finding communities and determining if two users are connected through a chain
3. Can be part of clustering algorithms to group data points based on similarity - useful for ML
4. It can be used to detect cycles in dependency graphs, e.g, software dependency management systems
5. It can be used for image processing, in labelling different connected components of an image

## Notes
Disjoint Set is a data structure designed to keep track of a set of elements partitioned into a number of 
non-overlapping subsets. **It is not suited for handling duplicates**, so our implementation ignores duplicates.
