# Selection Sort

## Background

Selection sort is another intuitive comparison-based sorting algorithm. It works similarly to other sorting algorithms
like bubble and insertion in the sense that it maintains a sorted and unsorted region. It does so by repeatedly finding
smallest (or largest) element in the unsorted region, and places the element in the correct and final position as it
would be in the sorted array.

![SelectionSort](../../../../../../docs/assets/images/SelectionSort.png)

### Implementation Invariant
Let the array of length n to be sorted be A.

The loop invariant is:
**At the end of the kth iteration, the smallest k items are correctly sorted in the first k positions of the array**.

So, at the end of the (n-1)th iteration of the loop, the smallest (n-1) items are correctly sorted in the first (n-1)
positions of the array, leaving the last item correctly positioned in the last index of the array. Therefore,
(n-1) iterations of the loop is sufficient.

## Complexity Analysis

**Time**:

- Worst case: O(n^2)
- Average case: O(n^2)
- Best case: O(n^2)

Regardless of how sorted the input array is, selectionSort will run the minimum element finding algorithm (n-1)
times. For an input array of length m, finding the minimum element necessarily takes O(m) time. Therefore, the
time complexity of selectionSort is n + (n-1) + (n-2) + ... + 2 = O(n^2)

**Space**: O(1) since sorting is done in-place

Image Source: https://www.hackerearth.com/practice/algorithms/sorting/selection-sort/tutorial/
