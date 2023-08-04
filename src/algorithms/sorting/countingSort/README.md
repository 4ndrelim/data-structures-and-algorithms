# Counting Sort

Counting sort is a non-comparison-based sorting algorithm and isn't bounded by the O(nlogn) lower-bound 
of most sorting algorithms. <br>
It first obtains the frequency map of all elements (ie counting the occurrence of every element), then
computes the prefix sum for the map. This prefix map tells us which position an element should be inserted.
Ultimately, each group of elements will be placed together, and the groups in succession, in the sorted output.

## Complexity Analysis
Time: O(k+n)=O(max(k,n)) where k is the value of the largest element and n is the number of elements. <br>
Space: O(k+n)=O(max(k,n)) <br>
Counting sort is most efficient if the range of input values do not exceed the number of input values. <br>
Counting sort is NOT AN IN-PLACE algorithm. For one, it requires additional space to store freq map. <br>

## Notes
COMMON MISCONCEPTION: Counting sort does not require total ordering of elements since it is non-comparison based.
This is incorrect. It requires total ordering of elements to determine their relative positions in the sorted output.
In fact, in conventional implementation, the total ordering property is reflected by virtue of the structure
of the frequency map.

Supplementary: Here is a [video](https://www.youtube.com/watch?v=OKd534EWcdk) if you are still having troubles.
