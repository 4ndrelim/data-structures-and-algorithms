# Cyclic Sort

## Background

Cyclic sort is a comparison-based, in-place algorithm that performs sorting (generally) in O(n^2) time.
Under some special conditions (discussed later), the algorithm is non-comparison based and 
the best case could be done in O(n) time. This is the version that tends to be used in practice.

### Implementation Invariant

**At the end of the kth iteration, the smallest (largest) i items are correctly sorted 
in the first (last) i positions of the array**.

### Comparison to Selection Sort

Its invariant is quite similar as selection sort's. But they differ in maintaining this invariant.
The algorithm for cyclic sort does a bit more than selection sort's.
In the process of trying to find the correct element for the ith position, any element that was
encountered will be correctly placed in their positions as well.

This allows cyclic sort to have a time complexity of O(n) for certain inputs.
(where the relative ordering of the elements is already known). This is discussed in the [**simple**](./simple) case.

## Simple and Generalised Case

We discuss more implementation-specific details and complexity analysis in the respective folders. In short,

1. The [**simple**](./simple) case discusses the **non-comparison based** implementation of cyclic sort under
   certain conditions. This allows the best case to be better than O(n^2).
2. The [**generalised**](./generalised) case discusses cyclic sort for general inputs. This is comparison-based and is
   typically implemented in O(n^2).

Note that, in practice, the generalised case is hardly used. There are more efficient algorithms to use for sorting,
e.g. merge and quick sort. If the concern is the number of swaps, generalized cyclic sort does indeed require fewer 
swaps, but likely won't lower than selection sort's.

In other words, **cyclic sort is specially designed for situations where the elements to be sorted are 
known to fall within a specific, continuous range, such as integers from 1 to n, without any gaps or duplicates.**


