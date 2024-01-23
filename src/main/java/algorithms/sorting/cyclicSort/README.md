# Cyclic Sort

## Background

Cyclic sort is a comparison-based, in-place algorithm that performs sorting (generally) in O(n^2) time.
Though under some conditions (discussed later), the best case could be done in O(n) time.

### Implementation Invariant

At the end of the ith iteration, the ith element
(of the original array, from either the back or front depending on implementation), is correctly positioned.

### Comparison to Selection Sort

Its invariant is quite similar as selection sort's. But they differ slightly in maintaining this invariant.
The algorithm for cyclic sort does a bit more than selection sort's.
In the process of trying to find the correct element for the ith position, any element that was
encountered will be correctly placed in their positions as well.

This allows cyclic sort to have a time complexity of O(n) for certain inputs.
(where the relative ordering of the elements is already known). This is discussed in the [**simple**](./simple) case.

## Simple and Generalised Case

We discuss more implementation-specific details and complexity analysis in the respective folders. In short,

1. The [**simple**](./simple) case discusses the non-comparison based implementation of cyclic sort under
   certain conditions. This allows the best case to be better than O(n^2).
2. The [**generalised**](./generalised) case discusses cyclic sort for general inputs. This is comparison-based and is
   usually implemented in O(n^2).


