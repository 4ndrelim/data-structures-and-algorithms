# Data Structures & Algorithms
This repository contains implementation of some fundamental data structures and algorithms in Computer Science. It is primarily used as a teaching resource and is currently being developed by ex-2040s students.

The project uses Gradle and the structure is optimised for IntelliJ IDEA since implementation are mostly written in Java.

**Note**: This is still being developed. Those below with links mean that they are complete (alongside testing). We project to complete CS2040s course content by November and along the way, add interesting algorithms/problems. We are hopeful that the subsequent batches of students (from AY23/24 S2) will benefit greatly from this. 

If you wish to contribute, do drop an email at andre_lin@u.nus.edu.

## Full List of Implementation (in alphabetical order):
## Structures
- Adelson-Velskii and Landis (AVL) Binary Search Tree
- Disjoint Set / Union Find
    * Quick Find
    * Weighted Union 
    * Path compression
- [Hashing](src/main/java/dataStructures/hashSet)
    * [Chaining](src/main/java/dataStructures/hashSet/chaining)
    * [Open Addressing](src/main/java/dataStructures/hashSet/openAddressing)
- [Heap](src/main/java/dataStructures/heap)
    * Max heap implementation
- [Linked List](src/main/java/dataStructures/linkedList)
- LRU Cache
- Minimum Spanning Tree
- [Queue](src/main/java/dataStructures/queue)
- Segment Tree
    * Array implementation
    * TreeNode implementation 
- [Stack](src/main/java/dataStructures/stack)
- Trie


## Algorithms
- [Counting Sort](src/main/java/algorithms/sorting/countingSort)
- [Cyclic Sort](src/main/java/algorithms/sorting/cyclicSort)
    * [Special case](src/main/java/algorithms/sorting/cyclicSort/simple) of O(n) time complexity
    * [Generalized case](src/main/java/algorithms/sorting/cyclicSort/generalised) of O(n^2) time complexity
- [Knuth-Morris-Pratt](src/main/java/algorithms/patternFinding) aka KMP algorithm
- [Bubble Sort](src/main/java/algorithms/sorting/bubbleSort)
- [Insertion Sort](src/main/java/algorithms/sorting/insertionSort)
- [Selection Sort](src/main/java/algorithms/sorting/selectionSort)
- Merge Sort
    * [Recursive](src/main/java/algorithms/sorting/mergeSort/recursive)
    * [Bottom-up iterative](src/main/java/algorithms/sorting/mergeSort/iterative)
- Quick Sort
    * [Hoare's](src/main/java/algorithms/sorting/quickSort/hoares)
    * [Lomuto's](src/main/java/algorithms/sorting/quickSort/lomuto)
    * [Paranoid](src/main/java/algorithms/sorting/quickSort/paranoid)
    * [3-way Partitioning](src/main/java/algorithms/sorting/quickSort/threeWayPartitioning)
- Radix Sort


## Short-cut to CS2040S Material
1. Basic structures
    * [Linked List](src/main/java/dataStructures/linkedList)
    * [Stack](src/main/java/dataStructures/stack)
    * [Queue](src/main/java/dataStructures/queue)
2. Binary Search
    * Peak Finding
    * Template
3. Sorting
    * [Bubble](src/main/java/algorithms/sorting/bubbleSort)
    * [Insertion](src/main/java/algorithms/sorting/insertionSort)
    * [Selection](src/main/java/algorithms/sorting/selectionSort)
    * [Merge](src/main/java/algorithms/sorting/mergeSort)
    * [Quick](src/main/java/algorithms/sorting/quickSort)
4. Trees
    * Binary search tree
    * AVL-tree
    * Kd-tree
    * Interval tree
    * Augmented tree for orthogonal range searching
    * Red-Black Tree
    * ab-Tree
5. [Binary Heap](src/main/java/dataStructures/heap)
    * Max heap implementation
6. Disjoint Set / Union Find
    * Quick Find
    * Weighted Union
    * Path compression
7. [Hashing](src/main/java/dataStructures/hashSet)
    * [Chaining](src/main/java/dataStructures/hashSet/chaining)
    * [Open Addressing](src/main/java/dataStructures/hashSet/openAddressing)
    * Double Hashing
    * Bloom filter
8. Basic graphs
    * Depth-first search
    * Breadth-first search
9. Graphs
    * Bellman-ford
    * Dijkstra
    * Directed acyclic graphs
10. Minimum spanning tree
    * Prim's 
    * Kruskal's


## Running Custom Inputs
See [here](scripts/)

## Contributors
See the [team]()!
