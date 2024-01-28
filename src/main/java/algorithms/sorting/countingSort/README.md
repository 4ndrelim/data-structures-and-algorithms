# Counting Sort

## Background

Counting sort is a non-comparison-based sorting algorithm and isn't bounded by the O(nlogn) lower-bound
of most sorting algorithms. <br>
It first obtains the frequency map of all elements (i.e. counting the occurrence of every element), then
computes the prefix sum for the map. This prefix map tells us which position an element should be inserted.
It is updated after each insertion to reflection the new position to insert the next time the same element is
encountered. <br>

### Implementation Invariant

**At the end of the ith iteration, the ith element (of the original array) from the back will be placed in
its correct position**.

Note: An alternative implementation from the front is easily done with minor modification.
The catch is that this implementation would not be stable.

### Common Misconception

_"Counting sort does not require total ordering of elements since it is non-comparison based."_

This is incorrect. It requires total ordering of elements to determine their relative positions in the sorted output.
In our implementation, the total ordering property is reflected by virtue of the structure of the frequency map.

## Complexity Analysis

**Time**: O(k+n)=O(max(k,n))  <br>
**Space**: O(k+n)=O(max(k,n)) <br>
where k is the value of the largest element and n is the number of elements.

Counting sort is most efficient if the range of input values do not exceed the number of input values. <br>
Counting sort is NOT AN IN-PLACE algorithm. For one, it requires additional space to store freq map. <br>

## Notes

1. Counting sort (stable version) is often used as a sub-routine for radix sort.
2. Supplementary: Here is a [video](https://www.youtube.com/watch?v=OKd534EWcdk) if you are still having troubles.
