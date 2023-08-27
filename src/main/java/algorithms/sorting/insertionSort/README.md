# Insertion Sort

Insertion sort is a comparison-based sorting algorithm that builds the final sorted array one element at a
time. It works by repeatedly taking an element from the unsorted portion of the array and 
inserting it correctly (portion remains sorted) into the sorted portion. Note that the position is not final 
since subsequent elements from unsorted portion may displace previously inserted elements. What's important is 
the sorted region remains sorted. More succinctly: <br>
At the kth iteration, we take the element arr[k] and insert
it into arr[0, k-1] following sorted order, returning us arr[0, k] in sorted order.

![InsertionSort](../../../../assets/InsertionSort.png)

## Complexity Analysis
**Time**:
  - Worst case (reverse sorted array): O(n^2)
  - Average case: O(n^2)
  - Best case (sorted array): O(n)

In the worst case, inserting an element into the sorted array of length m requires us to iterate through the
entire array, requiring O(m) time. Since InsertionSort does this insertion (n - 1) times, the time complexity
of InsertionSort in the worst case is 1 + 2 + ... + (n-2) + (n-1) = O(n^2).

In the best case of an already sorted array, inserting an element into the sorted array of length m requires
O(1) time as we insert it directly behind the first position of the pointer in the sorted array. Since InsertionSort
does this insertion (n-1) times, the time complexity of InsertionSort in the best case is O(1) * (n-1) = O(n).

**Space**: O(1) since sorting is done in-place

## Notes
### Common Misconception
Its invariant is often confused with selection sort's. In selection sort, an element in the unsorted region will 
be immediately placed in its correct and final position as it would be in the sorted array. This is not the case
for insertion sort. However, it is because of this 'looser' invariant that allows for a better best case time complexity
for insertion sort.

Image Source: https://www.hackerrank.com/challenges/correctness-invariant/problem