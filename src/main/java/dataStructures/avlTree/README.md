# AVL Trees

## Background
Is the fastest way to search for data to store them in an array, sort them and perform binary search? No. This will
incur minimally O(nlogn) sorting cost, and O(n) cost per insertion to maintain sorted order.

We have seen binary search trees (BSTs), which always maintains data in sorted order. This allows us to avoid the
overhead of sorting before we search. However, we also learnt that unbalanced BSTs can be incredibly inefficient for
insertion, deletion and search operations, which are O(h) in time complexity (in the case of degenerate trees,
operations can go up to O(n)).

Here we discuss a type of self-balancing BST, known as the AVL tree, that avoids the worst case O(n) performance 
across the operations by ensuring careful updating of the tree's structure whenever there is a change 
(e.g. insert or delete).

### Definition of Balanced Trees
Balanced trees are a special subset of trees with **height in the order of log(n)**, where n is the number of nodes. 
This choice is not an arbitrary one. It can be mathematically shown that a binary tree of n nodes has height of at least
log(n) (in the case of a complete binary tree). So, it makes intuitive sense to give trees whose heights are roughly
 in the order of log(n) the desirable 'balanced' label.

<div align="center">
    <img src="../../../../../docs/assets/images/BalancedProof.png" width="40%">
    <br>
    Credits: CS2040s Lecture 9
</div>

### Height-Balanced Property of AVL Trees
There are several ways to achieve a balanced tree. Red-black tree, B-Trees, Scapegoat and AVL trees ensure balance 
differently. Each of them relies on some underlying 'good' property to maintain balance - a careful segmenting of nodes 
in the case of RB-trees and enforcing a depth constraint for B-Trees. Go check them out in the other folders! <br>
What is important is that this **'good' property holds even after every change** (insert/update/delete).

The 'good' property in AVL Trees is the **height-balanced** property. Height-balanced on a node is defined as  
**difference in height between the left and right child node being not more than 1**. <br>
We say the tree is height-balanced if every node in the tree is height-balanced. Be careful not to conflate 
the concept of "balanced tree" and "height-balanced" property. They are not the same; the latter is used to achieve the
former.

<details>
<summary> <b>Ponder..</b> </summary>
Consider any two nodes (need not have the same immediate parent node) in the tree. Is the difference in height 
between the two nodes <= 1 too?
</details>

It can be mathematically shown that a **height-balanced tree with n nodes, has at most height <= 2log(n)**. Therefore, 
following the definition of a balanced tree, AVL trees are balanced.

<div align="center">
    <img src="../../../../../docs/assets/images/AvlTree.png" width="40%">
    <br>
    Credits: CS2040s Lecture 9
</div>

## Complexity Analysis
**Search, Insertion, Deletion, Predecessor & Successor queries Time**: O(height) = O(logn)

**Space**: O(n) <br>
where n is the number of elements (whatever the structure, it must store at least n nodes)

## Operations
Minimally, an implementation of AVL tree must support the standard **insert**, **delete**, and **search** operations. 
**Update** can be simulated by searching for the old key, deleting it, and then inserting a node with the new key. 

Naturally, with insertions and deletions, the structure of the tree will change, and it may not satisfy the 
"height-balance" property of the AVL tree. Without this property, we may lose our O(log(n)) run-time guarantee. 
Hence, we need some re-balancing operations. To do so, tree rotation operations are introduced. Below is one example.

<div align="center">
    <img src="../../../../../docs/assets/images/TreeRotation.png" width="40%">
    <br>
    Credits: CS2040s Lecture 10
</div>

Prof Seth explains it best! Go re-visit his slides (Lecture 10) for the operations :P <br>
Here is a [link](https://www.youtube.com/watch?v=dS02_IuZPes&list=PLgpwqdiEMkHA0pU_uspC6N88RwMpt9rC8&index=9) 
for prof's lecture on trees. <br>
_We may add a summary in the near future._

## Application
While AVL trees offer excellent lookup, insertion, and deletion times due to their strict balancing, 
the overhead of maintaining this balance can make them less preferred for applications 
where insertions and deletions are significantly more frequent than lookups. As a result, AVL trees often find itself
over-shadowed in practical use by other counterparts like RB-trees, 
which boast a relatively simple implementation and lower overhead, or B-trees which are ideal for optimizing disk 
accesses in databases.

That said, AVL tree is conceptually simple and often used as the base template for further augmentation to tackle 
niche problems. Orthogonal Range Searching and Interval Trees can be implemented with some minor augmentation to 
an existing AVL tree.
