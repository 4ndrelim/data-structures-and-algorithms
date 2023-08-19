# Selection Sort

Selection sort is another intuitive comparison-based sorting algorithm. It works similarly to other sorting algorithms 
like bubble and insertion in the sense that it maintains a sorted and unsorted region. It does so by repeatedly finding
smallest (or largest) element in the unsorted region, and places the element in the correct and final position as it 
would be in the sorted array.

![SelectionSort](../../../../assets/SelectionSort.png)

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