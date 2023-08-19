# Union Find / Disjoint Set

A disjoint-set structure also known as a union-find or merge-find set, is a data structure 
keeps track of a partition of a set into disjoint (non-overlapping) subsets. In CS2040s, this 
is primarily used to check for dynamic connectivity. For instance, Kruskal's algorithm 
in graph theory to find minimum spanning tree of the graph utilizes disjoint set to efficiently
query if there exists a path between 2 nodes. <br>
It supports 2 main operations:
1. Union: Join two subsets into a single subset
2. Find: Determine which subset a particular element is in. In practice, this is often done to check
if two  elements are in the same subset or component.

The Disjoint Set structure is often introduced in 3 parts, with each iteration being better than the
previous in terms of time and space complexity. Below is a brief overview:

## Quick Find
Every object will be assigned a component identity. The implementation of Quick Find often involves 
an underlying array that tracks the component identity of each object.

**Union**: Between the two components, decide on the component d, to represent the combined set. Let the other
component's identity be d'. Simply iterate over the component identifier array, and for any element with 
identity d', assign it to d.

**Find**: Simply use the component identifier array to query for the component identity of the two elements
and check if they are equal. This is why this implementation is known as "Quick Find". 

#### Analysis
Let n be the number of elements in consideration.

**Time**: O(n) for Union and O(1) for Find operations

**Space**: O(n) auxiliary space for the component identifier


## Quick Union
Here, we consider a completely different approach. We consider the use of trees. Every element can be
thought of as a tree node and starts off in its own component. Under this representation, it is likely 
that at any given point, we might have a forest of trees, and that's perfectly fine. The root node of each tree
simply represents the component / set of all elements in the same set. <br>
Note that the trees here are not necessarily binary trees. In fact, more often than not, we will have nodes
with multiple children nodes.

**Union**: Between the two components, decide on the component to represent the combined set as before.
Now, union is simply assigning the root node of one tree to be the child of the root node of another. Hence, its name. 
One thing to note is that to identify the component of the object involves traversing to the root node of the
tree.

**Find**: For each of the node, we traverse up the tree from the current node until the root. Check if the
two roots are the same

#### Analysis
**Time**: O(n) for Union and Find operations. While union-ing is indeed quick, it is possibly undermined
by O(n) traversal in the case of a degenerate tree. Note that at this stage, there is nothing to ensure the trees
are balanced.

**Space**: O(n), implementation still involves wrapping the n elements with some structure / wrapper.


## Weighted Union
Now, we augment and improve upon the Quick Union structure by ensuring trees constructed are 'balanced'. Balanced
trees have a nice property that the height of the tree will be upper-bounded by O(log(n)). This considerably speeds 
up Union operations. <br>
We additionally track the size of each tree and ensure that whenever there is a union between 2 elements, the smaller
tree will be the child of a larger tree. It can be mathematically shown the height of the tree is bounded by O(log(n)).

#### Analysis
**Time**: O(log(n)) for Union and Find operations.

**Space**: Remains at O(n)


### Path Compression
We can further improve on the time complexity of Weighted Union by introducing path compression. Specifically, during
the traversal of a node up to the root, we re-assign each node's parent to be the root (or as shown in CS2040s, 
assigning to its grandparent actually suffice and yield the same big-O upper-bound! This allows path compression to be
done in a single pass.). By doing so, we greatly reduce the height of the trees formed.

#### Analysis
The analysis is a bit trickier here and talks about the inverse-Ackermann function. Interested readers can find out more 
[here](https://dl.acm.org/doi/pdf/10.1145/321879.321884)

**Time**: O(alpha)

**Space**: O(n)
