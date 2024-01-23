# Binary Search Tree

## Overview

A Binary Search Tree (BST) is a tree-based data structure in which each node has at most two children, referred to as
the left child and the right child. Each node in a BST contains a unique key and an associated value. The tree is
structured so that, for any given node:

1. The left subtree contains nodes with keys less than the node's key.
2. The right subtree contains nodes with keys greater than the node's key.

This property makes BSTs efficient for operations like searching, as the average time complexity for many operations is
proportional to the tree's height.

Note: in the following explanation a "smaller" node refers to a node with a smaller key and a "larger" node refers to a
node with a larger key.

## Implementation

### BinarySearchTree Class

The BinarySearchTree class is a generic implementation of a BST. It supports a variety of operations that allow
interaction with the tree:

- root(): Retrieve the root node of the tree.
- insert(T key, V value): Insert a key-value pair into the tree.
- delete(T key): Remove a key and its associated value from the tree.
- search(T key): Find a node with a specified key.
- predecessor(T key): Find the key of the predecessor of a specified key.
- successor(T key): Find the key of the successor of a specified key.
- searchMin(): Find the node with the minimum key in the tree.
- searchMax(): Find the node with the maximum key in the tree.
- getInorder(): Return an in-order traversal of the tree.
- getPreorder(): Return a pre-order traversal of the tree.
- getPostorder(): Return a post-order traversal of the tree.
- getLevelorder(): Return a level-order traversal of the tree.

We will expand on the delete implementation due to its relative complexity.

#### Delete Implementation Details

The delete operation is split into three different cases - when the node to be deleted has no children, one child or
two children.

**No children:** Simply delete the node.

**One child:** Reassign the parent attribute of the child to the parent of the node to be deleted. This will not violate
the binary search tree property as the right child will definitely be smaller than the parent of the deleted node.

**Two children:** Replace the deleted node with its successor. This works because the binary search tree property is
maintained:

1. the entire left subtree will definitely be smaller than the successor as the successor is larger than the deleted
   node
2. the entire right subtree will definitely be larger than the successor as the successor will be the smallest node in
   the right subtree

### Node

The Node class represents the nodes within the BinarySearchTree. Each Node instance contains:

- key: The unique key associated with the node.
- value: The value associated with the key.
- left: Reference to the left child.
- right: Reference to the right child.
- parent: Reference to the parent node.

## Complexity Analysis

**Time Complexity:** For a balanced tree, most operations (insert, delete, search) can be performed in O(log n) time,
except tree traversal operations which can be performed in O(n) time. However, in the worst case (an unbalanced tree),
these operations may degrade to O(n).

**Space Complexity:** O(n), where n is the number of elements in the tree.
