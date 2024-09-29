# B-Trees

## Background
Is the fastest way to search for data to store them in an array, sort them and perform binary search? No. This will
incur minimally O(nlogn) sorting cost, and O(n) cost per insertion to maintain sorted order. <br>

We have seen binary search trees (BSTs), which always maintains data in sorted order. This allows us to avoid the 
overhead of sorting before we search. However, we also learnt that unbalanced BSTs can be incredibly inefficient for 
insertion, deletion and search operations, which are O(h) in time complexity (i.e. can go up to O(n) for unbalanced 
BSTs). <br>

Then, we learnt about self-balancing BSTs such as AVL Trees, that will help us cap the time complexity of insertion, 
deletion and search operations to O(h) ~= O(logn). <br>

B-tree is another of self-balancing search tree data structure that maintains sorted data and allows for efficient
insertion, deletion and search operations. 

## (a,b) trees

Before we talk about B-trees, we first introduce its family (generalized form) - (a,b) trees. <br> 

- In an (a,b) tree, a and b refer to the minimum and maximum number of children of an internal node in the tree. <br>
- a and b are parameters where 2 <= a <= (b+1)/2. 

Note that unlike binary trees, in (a,b) trees, each node can have more than 2 children and each node can store multiple 
keys.

Here is a (2,4) tree to aid visualisation as we go through the (a,b) tree rules/invariants. 
![(2,4) tree](../../../../../docs/assets/images/(2,4)tree.jpg)


### Implementation Invariants/(a,b) Tree Rules
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
- all other children ti have key range (vi-1, vi]

Rule #3: Leaf depth

All leaf nodes must be at the same depth from root. 
- This property forces the tree to be balanced. 

## Complexity Analysis

**Search, Insertion, Deletion Time**:  O(bloga(n)) = O(logn)

- The max height of an (a,b) tree is O(loga(n)).
- Linear search takes maximally b nodes per level.

**Space**: O(n)

where n is the number of elements (whatever the structure, it must store at least n nodes)

## How do B Trees relate to (a,b) trees?
A B-Tree is an (a,b) tree with a = ceil(b/2).

There are varying definitions of B-trees, but we will be following the CLRS definition: a B tree is parameterized by
a value t >= 2, known as its minimum degree. 
- Every internal node other than the root has at least t children. 
- Following this definition, t = a in the naming convention of (a,b) trees. 

## Operations

### Search Operation 
Here is an outline of the search operation:
1. Begin the search at the root of the B tree. 
2. If the key being searched for is in the current node, return true (i.e. found). 
3. Else, determine the child node where the key might be located based on comparison with the keys in the current node. 
4. Recursively perform the search operation in the determined child node. 
5. If the search reaches a leaf node, and the key is not found, return false (i.e. not found). 

### Insert Operation
You can read more about how the insert operation works 
[here](https://www.geeksforgeeks.org/insert-operation-in-b-tree/).

### Split Child Method
![split child](../../../../../docs/assets/images/btreesplitchild.jpeg)
Image Source: https://www.geeksforgeeks.org/insert-operation-in-b-tree/

## Delete Operation
The delete operation has a similar idea as the insert operation, but involves a lot more edge cases. If you are
interested to learn about it, you can read more [here](https://www.geeksforgeeks.org/delete-operation-in-b-tree/).

## Application: Index Structure
B+ trees tend to be used in practice over vanilla B-trees. 
The B+ tree is a specific variant of the B-tree that is optimized for efficient data retrieval from disk 
and range queries.

We will discuss two common applications of B+ trees: **database indexing** and **file system indexing**.

---

### Indexing Structure

B+ trees are often used to efficiently manage large amounts of data stored on disk. 
They do not store the actual data itself but instead store **pointers** (or references) 
to where the data is located on the disk.

#### Pointer / Reference
A pointer in the context of a B+ tree refers to some piece of information that can be used to 
retrieve actual data from the disk. Some common examples include:
- **Disk address/block number**
- **Filename with offset**
- **Database page and record ID**
- **Primary key ID**

<details>
<summary> <b>File System Indexing</b> </summary>

### B+ Trees for File System Indexing

File system indexing refers to the process by which an operating system organizes and manages files on 
storage media (such as hard drives, SSDs) to enable efficient file retrieval, searching, and management. 
It involves creating and maintaining indexes (similar to those in a database) that help quickly locate files, 
directories, and their metadata (like file names, attributes, permissions, and timestamps).

#### Workflow:
- The **root node** of a B+ tree is typically stored in **RAM** to speed up access.
- **Nodes** in the tree contain keys and child pointers to other nodes.
- **Intermediate nodes** do not store actual data but guide the search process toward the leaf nodes.
- **Leaf nodes** either contain the actual data or pointers to the data stored on disk. 
This is where the data retrieval process ends.

#### Optimized Disk I/O:
B+ trees are optimized for disk I/O, especially for **range queries**. 
The tree nodes are designed to fit into disk pages, meaning a single disk read operation can bring in multiple keys 
and pointers. This reduces the overall number of disk accesses required and efficiently utilizes disk pages.

#### Range Queries:
B+ trees are particularly effective for **range queries**. Since the leaf nodes in a B+ tree are linked together 
(typically via a **doubly linked list**), this makes sequential access for range queries efficient. 
For example, in a file system, this allows fetching multiple adjacent keys (like file names in a directory) 
without requiring additional disk I/O.

</details>

<details>
<summary> <b>SQL Engines</b> </summary>

### B+ Trees in SQL Engines

In **MySQL**, B+ trees are extensively used in the **InnoDB** storage engine 
(the default storage engine for MySQL databases).

#### Primary Key Index (Clustered Index):
In **InnoDB**, the primary key is always stored in a **clustered index**. 
This means the leaf nodes of the B+ tree store the actual rows of the table. 
In a clustered index, the rows are physically stored in the order of the primary key, 
making retrieval by primary key highly efficient.

#### Secondary Indexes:
For secondary indexes in MySQL (specifically in InnoDB), 
once the B+ tree for the secondary index is navigated to the leaf node, the following process occurs:

1. **Secondary Index B+ Tree**: The leaf nodes store the indexed column value (e.g., `last_name`) 
along with a reference to the primary key (e.g., `emp_id`).
2. **Reference to Primary Key**: This reference (the primary key value) is used to look up the actual data 
in the **clustered index** (which is also a B+ tree). The clustered index stores the entire row data in its leaf nodes.

#### Detailed Process:
- **Step 1**: MySQL navigates the secondary index tree based on the query condition (e.g. a range query on `last_name`)
    - The internal nodes guide the search, and the leaf node contains the `last_name` 
  value and the corresponding primary key (`emp_id`).

- **Step 2**: Once MySQL reaches the leaf node of the secondary index B+ tree, it retrieves the primary key (`emp_id`).

- **Step 3**: MySQL uses this primary key to directly access the **clustered index** (the B+ tree for the primary key).
    - It navigates the primary key B+ tree to locate the row in its leaf nodes, where the full row data 
  (e.g., `emp_id`, `last_name`, `first_name`, `salary`) is stored.

> **Note**: If multiple results match a query on the secondary index, 
the leaf nodes of the secondary index B+ tree will store multiple primary keys corresponding to the matching rows.

</details>

## References
CS2040S Recitation Sheet 4. 
