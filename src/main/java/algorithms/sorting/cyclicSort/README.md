# Cyclic Sort
Cyclic sort is a comparison-based, in-place algorithm that performs sorting in O(n^2) time. 
It is quite similar to selection sort, with both invariants ensuring that at the end of
the ith iteration, the ith element is correctly positioned. But they differ slightly in how
they ensure this invariant is maintained, allowing cyclic sort to have a time complexity of O(n) 
for certain inputs (the relative ordering of the elements are already known).
