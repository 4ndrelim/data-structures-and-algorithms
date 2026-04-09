# Cyclic Sort

## Background

Cyclic sort is an in-place sorting algorithm with two variants: a **simple `O(n)` case** for contiguous
integer ranges, and a **generalized `O(n²)` case** for arbitrary inputs.

The simple case is non-comparison based and achieves `O(n)` by exploiting direct index mapping — each
element's value determines its target position. This is the version used in practice.

### Implementation Invariant

**At the end of the kth iteration, the smallest k items are correctly sorted in the first k positions
of the array**.

### Comparison to Selection Sort

The invariant is similar to selection sort's, but cyclic sort does more work (more swaps) per iteration. While finding
the correct element for position i, any element encountered is also placed in its correct position.

For the simple case (contiguous integers), this allows `O(n)` time since each element is moved at most once.

## Complexity Analysis

| Variant | Time | Space | Comparison-based? |
|---------|------|-------|-------------------|
| [Simple](./simple) (contiguous integers) | `O(n)` | `O(1)` | No |
| [Generalized](./generalised) (any integers) | `O(n²)` | `O(1)` | Yes |

## Simple vs Generalized Case

1. The [**simple**](./simple) case is **non-comparison based** and requires contiguous integers (e.g., 0 to n-1).
   Each element maps directly to its index, enabling `O(n)` sorting.

2. The [**generalised**](./generalised) case handles arbitrary integers with duplicates. It requires `O(n)`
   comparisons per element to find correct positions, resulting in `O(n²)` overall.

In practice, **the generalized case is rarely used**, merge sort and quicksort are more efficient for
general sorting. However, the simple case has unique applications.

**Key insight:** Cyclic sort is specially designed for situations where elements fall within a specific,
continuous range (e.g., integers from 1 to n) without gaps or duplicates.

## Applications

| Use Case | Why Cyclic Sort? |
|----------|------------------|
| Find missing number in range [0, n] | `O(n)` time, `O(1)` space |
| Find duplicate in range [1, n] | In-place detection without extra space |
| Find all missing/duplicate numbers | Single pass after sorting |
| Sorting known contiguous range | `O(n)` beats comparison-based `O(n log n)` |

**Interview tip:** Cyclic sort is the go-to technique for problems involving "find missing/duplicate in
range [0/1, n]". The key insight is: if values are in range [0, n-1], each value `v` belongs at index `v`.
This enables `O(n)` time with `O(1)` space — better than hash set approaches that need `O(n)` space.


