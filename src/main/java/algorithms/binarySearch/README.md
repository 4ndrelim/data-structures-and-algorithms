# Binary Search

## Background

Binary search is a search algorithm that finds the position of a target value within a sorted array or list. It compares
the target value to the middle element of the search range, then, based on the comparison, narrows the search to the
upper or lower half of the current search range.

## Implementation Invariant

At the end of each iteration, the target value is either within the search range or does not exist in the search space.

## BinarySearch and BinarySearchTemplate

We will discuss more implementation-specific details and complexity analysis in the respective folders. In short,
1. The [binarySearch](binarySearch) method is a more straightforward and intuitive version of the binary search 
algorithm.
2. The [binarySearchTemplate](binarySearchTemplated) method provides a template that can be used for most binary search 
problems by introducing a condition method that can be modified based on the requirements of the implementation.


