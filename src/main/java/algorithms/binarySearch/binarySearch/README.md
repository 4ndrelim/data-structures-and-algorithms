# BinarySearch

## Background

BinarySearch is a more straightforward and intuitive version of the binary search algorithm. In this approach, after the
mid-value is calculated, the high or low pointer is adjusted by just one unit.

### Illustration

![binary search img](../../../../../../docs/assets/images/BinarySearch.png)

Image Source: GeeksforGeeks

From the above example, after mid points to index 4 in the first search, the low pointer moves to index 5 (+1 from 4) 
when narrowing the search. Similarly, when mid points to index 7 in the second search, the high pointer shifts to index 
6 (-1 from 7) when narrowing the search. This prevents any possibility of infinite loops. During the search, the moment 
mid-value is equal to the target value, the search ends prematurely.

## Complexity Analysis
**Time**:
- Worst case: O(log n)
- Average case: O(log n)
- Best case: O(1)

In the worst case, the target is either in the first or last index or does not exist in the array at all.
In the best case, the target is the middle (odd number of element) or the first middle element (even number of elements)
if floor division is used to determine the middle.

**Space**: O(1) 

Since no new data structures are used and searching is only done within the array given.