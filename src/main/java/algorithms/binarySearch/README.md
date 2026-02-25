# Binary Search

## Background

Binary search is an algorithm that finds the position of a target value within a **sorted** array. It compares the target to the middle element, then narrows the search to the upper or lower half based on the comparison.

### Core Invariant

At the end of each iteration, the target is either within the search range or does not exist.

## Complexity Analysis

| Operation | Time | Space |
|-----------|------|-------|
| Search | `O(log n)` | `O(1)` |

Each iteration halves the search space → at most `log₂(n)` iterations.

## Implementations

| Version | Use Case | Loop Condition | Key Insight |
|---------|----------|----------------|-------------|
| [binarySearch](binarySearch) | Find exact match | `low <= high` | Exits early when found |
| [binarySearchTemplated](binarySearchTemplated) | Find boundary (first/last satisfying condition) | `low < high` | Always runs to completion |

## Tips and Tricks

### 1. Avoiding Integer Overflow

```java
// BAD: can overflow if low + high > Integer.MAX_VALUE
int mid = (low + high) / 2;

// GOOD: equivalent but overflow-safe
int mid = low + (high - low) / 2;
```

### 2. Loop Condition: `<` vs `<=`

| Condition | When to Use | Final State |
|-----------|-------------|-------------|
| `low <= high` | Searching for exact match | `low > high` when not found |
| `low < high` | Finding boundaries (first/last) | `low == high` at answer |

**Interview tip:** If you're off-by-one or stuck in infinite loops, check your loop condition and pointer updates together. They must be consistent.

### 3. Pointer Updates: The Critical Decision

When updating `low` and `high`, ask: **Can `mid` be the answer?**

| Scenario | Update |
|----------|--------|
| `mid` cannot be the answer | `low = mid + 1` or `high = mid - 1` |
| `mid` could still be the answer | `low = mid` or `high = mid` |

**Warning:** Using `low = mid` with floor division can cause infinite loops when `low + 1 == high`:

```
Example: low=1, high=2, condition always false
  mid = 1 + (2-1)/2 = 1
  condition false → low = mid = 1  (unchanged!)
  Next iteration: same state → infinite loop
```

Fix by either:
- Use `mid = low + (high - low + 1) / 2` (ceiling division), OR
- Ensure you use `low = mid + 1` instead

### 4. Recognizing Binary Search Problems

Binary search applies when:
1. **Sorted/monotonic property** - elements have an ordering
2. **Eliminable halves** - you can discard half the search space per iteration

Less obvious applications (search on answer space):
- "Find minimum capacity to ship packages in D days"
- "Find smallest divisor given a threshold"
- "Koko eating bananas"

### 5. Common Patterns

| Pattern | Condition | Return |
|---------|-----------|--------|
| Find exact value | `arr[mid] == target` | `mid` |
| First element ≥ target (lower bound) | `arr[mid] >= target` → `high = mid` | `low` |
| Last element ≤ target (upper bound) | `arr[mid] <= target` → `low = mid` | `low` or `low - 1` |
| First "true" in `[F,F,F,T,T,T]` | `condition(mid)` → `high = mid` | `low` |

### 6. Debugging Checklist

When your binary search doesn't work:
1. Is the array/search space actually sorted/monotonic?
2. Are `low` and `high` initialized to include all possible answers?
3. Does your condition correctly partition the space?
4. Are pointer updates consistent with loop condition?
5. Does termination handle the edge case of 1-2 elements?

### 7. Off-by-One Prevention

For boundary searches, trace through a 2-element array manually:
```
[F, T] with low=0, high=1
mid = 0 → condition false → low = 1
low == high → exit, return low=1 ✓
```

---

## Note: "Sorted List" Interview Problems

A common interview pattern involves maintaining a **sorted collection with dynamic insertions and deletions**. Examples:
- LeetCode: "Sliding Window Median" - need to add/remove elements while maintaining sorted order

**Do you need ordered traversal or rank queries?** If yes, you need a sorted structure.

These problems use binary search for lookups but **cannot be solved with a plain array** because insertion is `O(n)`. You need:

| Language | Data Structure | Insert | Search | Implementation |
|----------|---------------|--------|--------|----------------|
| Java | `TreeMap` / `TreeSet` | `O(log n)` | `O(log n)` | Red-black tree |
| C++ | `std::map` / `std::set` | `O(log n)` | `O(log n)` | Red-black tree |
| Python | `sortedcontainers.SortedList` | `O(log n)` | `O(log n)` | List of lists (not a tree) |

**Note:** C++ `std::map` is sorted by key. `std::unordered_map` is the hash table equivalent.

**Interview tip:** This pattern isn't covered in CS2040S but appears frequently in interviews. If you're stuck on a "binary search" problem because array insertions are too slow, consider `TreeMap`/`TreeSet`.

## Applications

| Problem Type | Example |
|-------------|---------|
| Finding element | Search in sorted array |
| Finding boundary | First/last occurrence of element |
| Search on answer | Minimum capacity, eating speed, etc. |
| Rotated arrays | Search in rotated sorted array |
| 2D search | Search in row-wise/column-wise sorted matrix |
