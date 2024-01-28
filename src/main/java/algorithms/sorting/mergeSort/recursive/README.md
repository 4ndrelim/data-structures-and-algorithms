# Merge Sort

### Brief Description:
MergeSort is a divide-and-conquer sorting algorithm. The recursive implementation takes a top-down approach by
recursively dividing the array into two halves, sorting each half separately, and then merging the sorted halves
to produce the final sorted output.

![MergeSort Recursive](../../../../../../../docs/assets/images/MergeSortRecursive.png)

Image Source: https://www.101computing.net/merge-sort-algorithm/

### Implementation Invariant (for the merging subroutine):
The sub-array temp[start, (k-1)] consists of the (𝑘−start) smallest elements of arr[start, mid] and
arr[mid + 1, end], in sorted order.

### Complexity Analysis:
Time:
- Worst case: O(nlogn)
- Average case: O(nlogn)
- Best case: O(nlogn)
Merging two sorted sub-arrays of size (n/2) requires O(n) time as we need to iterate through every element in both
sub-arrays in order to merge the two sorted sub-arrays into one sorted array.

Recursion expression: T(n) = 2T(n/2) + O(n) => O(nlogn)

Regardless of how sorted the input array is, MergeSort carries out the same divide-and-conquer strategy, so the
time complexity of MergeSort is O(nlogn) for all cases.

Space:
- O(n) since we require a temporary array to temporarily store the merged elements in sorted order