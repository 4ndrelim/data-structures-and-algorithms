# Simple Case

## Background

Cyclic Sort can achieve O(n) time complexity in cases where the elements of the collection have a known,
continuous range, and there exists a direct, O(1) time-complexity mapping from each element to its respective index in
the sorted order.

This is typically applicable when sorting a sequence of integers that are in a consecutive range
or can be easily mapped to such a range. We illustrate the idea with n integers from 0 to n-1.

In this implementation, the algorithm is **not comparison-based**! (unlike the general case).
It makes use of the known inherent ordering of the numbers, 
bypassing the `nlogn` lower bound for most sorting algorithms.

<details>
<summary> <b>Duplicates</b> </summary>
Not designed to hande duplicates. When duplicates are present, the algorithm can run into issues, 
such as overwriting elements or getting stuck in infinite loops, 
because it assumes that each element has a unique position in the array.

If you need to handle duplicates, modifications are required, 
such as checking for duplicate values before placing elements, 
which can impact the simplicity and efficiency (possibly degrade to `O(n^2)`) of the algorithm.
</details>

<details>
<summary> <b>Inherent Ordering..?</b> </summary>
This property allows the sorting algorithm to avoid comparing elements with each other 
and instead directly place each element in its correct position. 

For example, if sorting integers from 0 to n-1, the number 0 naturally belongs at index 0, 1 at index 1, and so on. 
This inherent structure allows Cyclic Sort to achieve `O(n)` time complexity, 
bypassing the typical `O(nlogn)` time bound of comparison-based sorting algorithms 
([proof](https://tildesites.bowdoin.edu/~ltoma/teaching/cs231/fall07/Lectures/sortLB.pdf)) 
by using the known order of elements rather than making comparisons to determine their positions.
</details>

## Complexity Analysis

**Time**:

- Best: O(n) since the array has to be traversed
- Worst: O(n) since each element is at most seen twice
- Average: O(n), it's bounded by the above two

**Space**: O(1) auxiliary space, this is an in-place algorithm

## Case Study: Find First Missing Non-negative Integer

Cyclic sort algorithm can be easily modified to find first missing non-negative integer (i.e. starting from 0) in O(n).
The invariant is the same, but for numbers that are out-of-range (negative or greater than n),
simply ignore the number at the position and
move on first. It may be subject to swap later.

There are other ways of doing so, using a hash set for instance, but what makes cyclic sort stand out is that it is
able to do so in O(1) space. In other words, it is in-place and require no additional space.

The algorithm does a 2-pass iteration.

1. In the 1st iteration, it places elements at its rightful position where possible.
2. In the 2nd iteration, it will look for the first out of place element (element that is not supposed
   to be in that position). The answer will be the index of that position.

Note that the answer is necessarily between 0 and n (inclusive), where n is the length of the array,
otherwise there would be a contradiction.

## Notes

1. It may seem quite trivial to sort integers from 0 to n-1 when one could simply generate such a sequence.
   But this algorithm is useful in cases where the integers to be sorted are keys to associated values (or some mapping)
   and sorting needs to be done in O(1) auxiliary space.
2. The implementation here uses integers from 0 to n-1. This can be easily modified for n contiguous integers starting
   at some arbitrary number (simply offset by this start number).
3. This version of cyclic sort does not handle duplicates (at least, sorting might not be guaranteed to be in O(n))
