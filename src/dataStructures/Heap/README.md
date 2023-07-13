# Heap

## Binary Heap
A binary heap is often used to introduce the concept of heaps. It is a tree-based data structure that satisfies the 
following properties:
1. Complete binary tree - every level, except possibly the last, is completely filled
2. Max (min) heap property - the value of every vertex in the binary tree is >= (<=) than that of its child vertices

This makes it a powerful data structure that provides efficient access to the highest (or lowest) priority element, 
making it suitable as an underlying implementation of the ADT, priority queue.

![max heap](../../../assets/max_heap.png)

## Array-based Heap
The complete binary tree property actually allows the heap to be implemented as a contiguous array (since no gaps!).
The parent-child relationships are derived based on the indices of the elements.

Theoretically, there isn't any fundamental difference in order of growth for either implementation.
Both implementation provide the same asymptotic time complexity, and supports most operations in O(log(n)).

That said, in practice, the array-based implementation of a heap often provides better performance as opposed to the 
former, in cache efficiency and memory locality. This is due to its contiguous memory layout. As such, 
the implementation shown here is a 0-indexed array-based heap.


## Notes
1. Heaps are often presented as max-heaps (eg. in textbooks), hence the implementation follows a max-heap structure
    - Still, it is not too difficult to convert a max heap to a min heap, simply negate the values of the nodes
2. The heap implemented here is actually augmented with a Map data type. This allows identification of nodes by key. 
   - Java's PriorityQueue and Python's heap actually support the removal of a node identified by its value / key. 
      Note that this is not a typical operation introduced alongside the concept heap simply because the time complexity 
      would now be O(n), no longer log(n). And indeed, both Java's and Python's version have time complexities 
      of O(n) for this remove operation since their underlying implementation is not augmented.
3. Rather than using Java arrays, where size mus tbe declared upon initializing, we use list here in the implementation.
