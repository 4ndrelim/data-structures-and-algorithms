# Data Structures & Algorithms

This repository contains implementation and discussion notes (intuition, applications, analysis) 
of some fundamental data structures and algorithms in Computer Science. <br>
It is aligned with [CS2040s](https://nusmods.com/courses/CS2040S/data-structures-and-algorithms) syllabus taught at NUS.

The work here is continually being developed by CS2040s Teaching Assistants(TAs) and ex-2040s students. 
Lecture content has been covered and future plans include deeper discussion into the tougher parts of tutorials.

The project's structure is optimised for IntelliJ IDEA as per the course's preferred IDE. 
Gradle is used for development.

## Full List (in alphabetical order):

## Data Structures
- Adelson-Velskii and Landis (AVL) Binary Search Tree
- [Disjoint Set / Union Find](src/main/java/dataStructures/disjointSet)
    * [Quick Find](src/main/java/dataStructures/disjointSet/quickFind)
    * [Weighted Union]((src/main/java/dataStructures/disjointSet)/weightedUnion)
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
  - [Deque](src/main/java/dataStructures/queue/Deque)
  - [Monotonic Queue](src/main/java/dataStructures/queue/monotonicQueue)
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
- [Radix Sort](src/main/java/algorithms/sorting/radixSort/)

## Short-cut to CS2040S Material
1. Basic structures
    * [Linked List](src/main/java/dataStructures/linkedList)
    * [Stack](src/main/java/dataStructures/stack)
    * [Queue](src/main/java/dataStructures/queue)
2. [Binary Search](src/main/java/algorithms/binarySearch)
    * Peak Finding
    * Simple Version
    * Universal Version
3. Sorting
    * [Bubble](src/main/java/algorithms/sorting/bubbleSort)
    * [Insertion](src/main/java/algorithms/sorting/insertionSort)
    * [Selection](src/main/java/algorithms/sorting/selectionSort)
    * [Merge](src/main/java/algorithms/sorting/mergeSort)
    * [Quick](src/main/java/algorithms/sorting/quickSort)
4. Trees
    * [Binary search tree](src/main/java/dataStructures/binarySearchTree)
    * AVL-tree
    * Kd-tree (**WIP**)
    * Interval tree (**WIP**)
    * Augmented tree for orthogonal range searching
    * Red-Black Tree
    * ab-Tree
5. [Binary Heap](src/main/java/dataStructures/heap)
    * Max heap implementation
6. [Disjoint Set / Union Find](src/main/java/dataStructures/disjointSet)
    * [Quick Find](src/main/java/dataStructures/disjointSet/quickFind)
    * [Weighted Union](src/main/java/dataStructures/disjointSet/weightedUnion)
      * Path compression
7. [Hashing](src/main/java/dataStructures/hashSet)
    * [Chaining](src/main/java/dataStructures/hashSet/chaining)
    * [Open Addressing](src/main/java/dataStructures/hashSet/openAddressing)
    * Double Hashing
    * Bloom filter (**WIP**)
8. Basic graphs (**WIP**)
    * Depth-first search
    * Breadth-first search
9. Graphs
    * Bellman-ford
    * Dijkstra
    * Directed acyclic graphs algorithms (**WIP**)
      * Post-order DFS
      * Kahn's
    * Floyd Warshall (**WIP**)
10. Minimum spanning tree
    * Prim's
    * Kruskal's

## Running Custom Inputs
See [here](scripts/README.md).

## Disclaimer
While our team of TAs and students have diligently verified the correctness of our code, there might still be
some discrepancies or deviation from lecture content (perhaps due to new changes). 
In which case, **you are strongly advised to raise it up to us or consult your TA** regarding any suspicions 
on the use of the information shared here.

## Contributors
See the [team](docs/team/profiles.md)!
