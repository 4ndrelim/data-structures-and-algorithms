# Data Structures & Algorithms

This repository contains implementation and discussion notes (intuition, applications, analysis) 
of some fundamental data structures and algorithms in Computer Science. <br>
It is aligned with [CS2040s](https://nusmods.com/courses/CS2040S/data-structures-and-algorithms) syllabus taught by 
[Prof Seth](https://www.comp.nus.edu.sg/cs/people/gilbert/) at NUS. 

The work here is continually being developed by CS2040s Teaching Assistants(TAs) and ex-2040s students, 
under the guidance of Prof Seth.
It is still in its infant stage, mostly covering lecture content and discussion notes.
Future plans include deeper discussion into the tougher parts of tutorials and even practice problems / puzzles related
to DSA.

The project's structure is optimised for IntelliJ IDEA as per the course's preferred IDE. 
Gradle is used for development.

## Full List (in alphabetical order):

## Data Structures
- [Adelson-Velskii and Landis (AVL) Binary Search Tree](src/main/java/dataStructures/avlTree)
- [Disjoint Set / Union Find](src/main/java/dataStructures/disjointSet)
    * [Quick Find](src/main/java/dataStructures/disjointSet/quickFind)
    * [Weighted Union](src/main/java/dataStructures/disjointSet/weightedUnion)
      * Path compression
- [Hashing](src/main/java/dataStructures/hashSet)
    * [Chaining](src/main/java/dataStructures/hashSet/chaining)
    * [Open Addressing](src/main/java/dataStructures/hashSet/openAddressing)
- [Heap](src/main/java/dataStructures/heap)
    * Max heap implementation 
- [Linked List](src/main/java/dataStructures/linkedList)
- LRU Cache
- Minimum Spanning Tree 
    * Kruskal
    * Prim's
    * Boruvska
- [Queue](src/main/java/dataStructures/queue)
  - [Deque](src/main/java/dataStructures/queue/Deque)
  - [Monotonic Queue](src/main/java/dataStructures/queue/monotonicQueue)
- Segment Tree
- [Stack](src/main/java/dataStructures/stack)
- [Segment Tree](src/main/java/dataStructures/segmentTree)
- [Trie](src/main/java/dataStructures/trie)

## Algorithms
- [Bubble Sort](src/main/java/algorithms/sorting/bubbleSort)
- [Binary Search](src/main/java/algorithms/binarySearch)
    * [Template](src/main/java/algorithms/binarySearch/binarySearchTemplated)
- [Counting Sort](src/main/java/algorithms/sorting/countingSort)
- [Cyclic Sort](src/main/java/algorithms/sorting/cyclicSort)
    * [Special case](src/main/java/algorithms/sorting/cyclicSort/simple) of O(n) time complexity
    * [Generalized case](src/main/java/algorithms/sorting/cyclicSort/generalised) of O(n^2) time complexity
- [Insertion Sort](src/main/java/algorithms/sorting/insertionSort)
- [Knuth-Morris-Pratt](src/main/java/algorithms/patternFinding) aka KMP algorithm
- [Merge Sort](src/main/java/algorithms/sorting/mergeSort)
    * [Recursive](src/main/java/algorithms/sorting/mergeSort/recursive)
    * [Bottom-up iterative](src/main/java/algorithms/sorting/mergeSort/iterative)
- [Quick Sort](src/main/java/algorithms/sorting/quickSort/)
    * [Hoare's](src/main/java/algorithms/sorting/quickSort/hoares)
    * [Lomuto's](src/main/java/algorithms/sorting/quickSort/lomuto)
    * [Paranoid](src/main/java/algorithms/sorting/quickSort/paranoid)
    * [3-way Partitioning](src/main/java/algorithms/sorting/quickSort/threeWayPartitioning)
- [Radix Sort](src/main/java/algorithms/sorting/radixSort)
- [Selection Sort](src/main/java/algorithms/sorting/selectionSort)

## CS2040S Syllabus (in rough order)
1. Basic structures
    * [Linked List](src/main/java/dataStructures/linkedList)
    * [Stack](src/main/java/dataStructures/stack)
    * [Queue](src/main/java/dataStructures/queue)
2. [Binary Search](src/main/java/algorithms/binarySearch)
    * Peak Finding
    * [Template](src/main/java/algorithms/binarySearch/binarySearchTemplated)
3. Sorting
    * [Bubble](src/main/java/algorithms/sorting/bubbleSort)
    * [Insertion](src/main/java/algorithms/sorting/insertionSort)
    * [Selection](src/main/java/algorithms/sorting/selectionSort)
    * [Merge](src/main/java/algorithms/sorting/mergeSort)
    * [Quick](src/main/java/algorithms/sorting/quickSort)
      * [Hoare's](src/main/java/algorithms/sorting/quickSort/hoares)
      * [Lomuto's](src/main/java/algorithms/sorting/quickSort/lomuto) (Not discussed in CS2040s)
      * [Paranoid](src/main/java/algorithms/sorting/quickSort/paranoid)
      * [3-way Partitioning](src/main/java/algorithms/sorting/quickSort/threeWayPartitioning)
    * [Counting Sort](src/main/java/algorithms/sorting/countingSort) (found in tutorial)
    * [Radix Sort](src/main/java/algorithms/sorting/radixSort) (found in tutorial)
4. Trees
    * [Binary search tree](src/main/java/dataStructures/binarySearchTree)
    * [AVL-tree](src/main/java/dataStructures/avlTree)
    * [Trie](src/main/java/dataStructures/trie)
    * [B-Tree](src/main/java/dataStructures/bTree)
    * [Segment Tree](src/main/java/dataStructures/segmentTree) (Not covered in CS2040s but useful!)
    * Red-Black Tree (Not covered in CS2040s but useful!)
    * Orthogonal Range Searching (**WIP**)
    * Interval Trees (**WIP**)
5. [Binary Heap](src/main/java/dataStructures/heap) (Max heap)
6. [Disjoint Set / Union Find](src/main/java/dataStructures/disjointSet)
    * [Quick Find](src/main/java/dataStructures/disjointSet/quickFind)
    * [Weighted Union](src/main/java/dataStructures/disjointSet/weightedUnion) (with path compression)
7. [Hashing](src/main/java/dataStructures/hashSet)
    * [Chaining](src/main/java/dataStructures/hashSet/chaining)
    * [Open Addressing](src/main/java/dataStructures/hashSet/openAddressing)
    * Bloom filter (**WIP**)
8. Basic graphs (**WIP**)
    * Depth-first search
    * Breadth-first search
9. Graphs (**WIP**)
    * Bellman-ford
    * Dijkstra
    * Directed acyclic graphs algorithms 
      * Post-order DFS
      * Kahn's
    * Floyd Warshall 
10. Minimum spanning tree (**WIP**)
    * Prim's
    * Kruskal's

## Set-up 
If you are a CS2040s student, your IDEA configurations should already be compatible with this project structure. So, 
feel free to clone and use it as you see fit. Note, below configuration is as per CS2040s PS1 set-up guide.

1. Choose Java Version 11.0.XX for Project SDK. You can download it [here](https://www.oracle.com/java/technologies/downloads/#java11)
   - Create account and login if necessary
   - Make sure to download the correct one compatible with your hardware
2. Download IntelliJ (Community Edition) [here](https://www.jetbrains.com/idea/download/?section=mac) if you do not have it.
3. Fork the repo and clone it on your local device
4. Launch IntelliJ on your device and under the `Projects` tab, and click `open`. Navigate to where the local repo is 
cloned 
   1. Configure to Java SDK (if not done) by first heading to `File` on the top-left panel,
   2. Click on `Project Structure...`
   3. Apply the desired Java SDK in the `SDK:` dropdown. Remember to click `Apply`.
5. You can test if everything is properly set-up with the command: <br/>
`./gradlew clean test` <br/>
All files should be compiled and all testcases should pass.

## Usage
The resources here can be directly viewed from GitHub interface, but it is advisable for you to fork and clone 
it to your local desktop, especially if you wish to tweak or play with custom inputs. There is a folder where you can 
import and run the algorithms/structures here for your own input. See [here](scripts/README.md).

## Disclaimer
While our team of TAs and students have diligently verified the correctness of our code, there might still be
some discrepancies or deviation from lecture content (perhaps due to new changes). 
In which case, **you are strongly advised to raise it up to us or consult your TA** regarding any suspicions 
on the use of the information shared here.

## Contributors
See the [team](docs/team/profiles.md)!
