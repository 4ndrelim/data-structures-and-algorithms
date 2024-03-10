# Red-Black Tree

The red-black tree is another form of self-balancing binary tree with a looser constraint
as compared to an AVL Tree.

It achieves balance with the following properties:

1) Every node is colored either red or black. (In our implementation we denote this property with the tag field)
2) The root is black.
3) The leaf is black. 
4) A red node can only have black children.
5) For any node, a path from itself to any of its descendant leaf nodes must contain the same amount of nodes that are colored black.

![rb-image](https://miro.medium.com/v2/resize:fit:1400/format:webp/1*xGjCx645d9RPwOm5mIpthA.jpeg)

Much like an AVL tree, red black properties are maintained using a series of 
left and right rotations after an insert or delete operation is conducted. There are 5 different 
cases to consider during an insert operation and 6 different cases to consider during a delete operation.
[This article](https://www.happycoders.eu/algorithms/red-black-tree-java/) explains the cases.

## Operation Orders

Much like other balanced BSTs, RB Trees have *O(logN)* operations.

## Notes

The AVL balance property is stronger than the red-black property, this means it requires more 
rotations for the tree to balance after operations. This makes RB Trees 
a more ideal data structure for use cases that require a lot of insert and delete operations.

However, RB Trees take up more space as each node now needs to track what color it is.

Interestingly, Java's [TreeMap](https://docs.oracle.com/javase/8/docs/api/java/util/TreeMap.html) 
and [TreeSet](https://docs.oracle.com/javase/8/docs/api/java/util/TreeSet.html) 
is a RB Tree instead of an AVL Tree. It is recommended to use these instead of creating your own 
RB Tree as unlike BSTs, an RB Tree is a lot more complex to implement.