# Selection Sort

## Background

Selection sort is an intuitive comparison-based sorting algorithm. Like bubble and insertion sort, it
maintains a sorted and unsorted region. It repeatedly finds the smallest (or largest) element in the
unsorted region and places it in its correct *final* position.

<div align="center">
    <img src="../../../../../../docs/assets/images/SelectionSort.png" alt="Selection sort" width="65%"/>
</div>

### Implementation Invariant

**At the end of the kth iteration, the smallest k items are correctly sorted in the first k positions**.

Unlike insertion sort, elements placed in the sorted region are in their *final* positions and will
not be displaced by future iterations.

At the end of the (n-1)th iteration, the smallest (n-1) items are correctly placed, leaving the last
item correctly positioned. Therefore, (n-1) iterations suffice.

## Complexity Analysis

| Case | Time | Space |
|------|------|-------|
| Worst | `O(n²)` | `O(1)` |
| Average | `O(n²)` | `O(1)` |
| Best | `O(n²)` | `O(1)` |

Regardless of how sorted the input is, selection sort runs the minimum-finding algorithm (n-1) times.
Finding the minimum in an array of length m takes `O(m)` time. Total: `n + (n-1) + ... + 2 = O(n²)`.

**Note**: Unlike insertion sort, selection sort has no `O(n)` best case - it always does `O(n²)` comparisons.

## Notes

1. **Not stable**: Selection sort is **not stable** by default. When swapping the minimum element with
   the current position, equal elements may change their relative order.

   <details>
   <summary>Example of instability</summary>

   Array: `[5a, 5b, 3]` (5a and 5b are equal, 'a' and 'b' just track original order)
   - Iteration 1: Find min (3), swap with position 0 → `[3, 5b, 5a]`
   - Result: 5a and 5b swapped relative order (was 5a before 5b, now 5b before 5a)
   </details>

2. **Minimal swaps**: Selection sort performs exactly `O(n)` swaps (at most n-1 swaps). This is optimal
   among comparison sorts and useful when writes are expensive (e.g., flash memory).

3. **Not adaptive**: Performance is the same regardless of input order - no benefit from partially
   sorted data.

4. **In-place**: Only requires `O(1)` extra space.

## Applications

| Use Case | Why Selection Sort? |
|----------|---------------------|
| Minimizing writes | Only `O(n)` swaps vs `O(n²)` for bubble/insertion |
| Memory writes are costly | Flash memory, EEPROM where writes degrade the medium |
| Small datasets | Simple implementation with predictable performance |

**When to avoid**: When stability is required, or when the data might be partially sorted (insertion
sort's `O(n)` best case would be better).

**Interview tip:** Selection sort's main advantage is its `O(n)` swaps. If asked "which sort minimizes
writes?", selection sort is the answer among simple `O(n²)` sorts. However, for most practical purposes,
insertion sort is preferred due to its adaptive behavior.

Image Source: https://www.hackerearth.com/practice/algorithms/sorting/selection-sort/tutorial/
