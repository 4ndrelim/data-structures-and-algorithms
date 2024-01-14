# B-Trees

## Background
Is the fastest way to search for data to store them in an array, sort them and perform binary search? No. <br>

We have seen binary search trees (BSTs), which always maintains data in sorted order. This allows us to avoid the 
overhead of sorting before we search. However, we also learnt that unbalanced BSTs can be incredibly inefficient for 
insertion, deletion and search operations, which are O(h) in time complexity (i.e. can go up to O(n) for unbalanced 
BSTs). <br>

Then, we learnt about self-balancing BSTs such as AVL Trees, that will help us cap the time complexity of insertion, 
deletion and search operations to O(h) ~= O(logn). <br>

B-tree is another of self-balancing search tree data structure that maintains sorted data and allows for efficient
insertion, deletion and search operations. 

### (a,b) trees

Before we talk about B-trees, we first introduce its family (generalized form) - (a,b) trees. <br> 

- In an (a,b) tree, a nd b refer to the minimum and maximum number of children of an internal node in the tree. <br>
- a and b are parameters where 2 <= a <= (b+1)/2. 

Note that unlike binary trees, in (a,b) trees, each node can have more than 2 children and each node can store multiple 
keys.

Here is a (2,4) tree to aid visualisation as we go through the (a,b) tree rules/invariants. 
![(2,4) tree](../../../../../docs/assets/images/(2,4)tree.jpg)


### Implementation Invariant/(a,b) Tree Rules
Rule #1: (a,b)-child Policy
The min and max of keys and children each node can have are bounded as follows:
![(a,b) child policy](../../../../../docs/assets/images/(a,b)childpolicy.jpg)

Note: With the exception of leaves, realize that the number of children is always one more than the number of keys. 
(See rule 2)

The min height of an (a,b) tree will be O(logb(n)) and the max height of an (a,b) tree will be O(loga(n)). <br>

How do we pick the values of a and b? b is dependent on the hardware, and we want to maximise a to make the tree fatter
and shorter. 

Rule #2: Key ranges

A non-leaf node (i.e. root or internal) must have one more child than its number of keys. This is to ensure that all 
value ranges due to its keys are covered in its subtrees. 

The permitted range of keys within a subtree is referred to be its key range. 

Specifically, for a non-leaf node with k keys and (k+1) children:
- its keys in sorted order are v1, v2, ..., vk
- the subtrees due to its keys are t1, t2, ..., tk+1

Then: 
- first child t1 has key range <= v1
- final child tk+1 has key range > vk
- all other children ti have key range (vi-1, vi)

Rule #3: Leaf depth

All leaf nodes must be at the same depth from root. 

## Complexity Analysis
Search:

**Time**:  O(bloga(n)) = O(logn)

- The max height of an (a,b) tree is O(loga(n)). 
- Linear search takes maximally b nodes per level. 

**Space**: O(n)

where n is the number of elements (whatever the structure, it must store at least n nodes)

## How do B Trees relate to (a,b) trees?
A B-Tree is an (a,b) tree with a = ceil(b/2).

There are varying definitions of B-trees but we will be following the CLRS definition: a B tree is parameterized by
a value t >= 2, known as its minimum degree. 
- Every internal node other than the root has at least t children. 
- Following this definition, t = a in the naming convention of (a,b) trees. 

## Split Child Method
![split child](../../../../../docs/assets/images/btreesplitchild.jpeg)
Image Source: https://www.geeksforgeeks.org/insert-operation-in-b-tree/

## References
This description heavily references CS2040S Recitation Sheet 4. 