# Monotonic Queue / Monotonic Stack

## Background

A **monotonic queue** (or **monotonic stack**) maintains elements in sorted order (increasing or decreasing) by removing elements that violate the monotonic property when new elements are added.

The key insight: **elements that can never be useful are discarded immediately**.

### Monotonic Queue vs Monotonic Stack

| Structure | Remove from | Use case |
|-----------|-------------|----------|
| **Monotonic Stack** | Same end as insert (LIFO) | "Next greater/smaller element" |
| **Monotonic Queue** | Both ends (deque-based) | "Sliding window max/min" |

Both maintain monotonic order; the difference is whether you need to expire old elements (queue) or just find relationships (stack).

## How It Works

### Decreasing Monotonic Queue (for sliding window maximum)

A monotonic **queue** is needed when elements must **expire** - i.e., sliding window problems. You remove from **both ends**:
- **Back**: Remove smaller elements (they can't be max while larger element exists)
- **Front**: Remove expired elements (outside the window)

```
Array: [5, 3, 4, 1, 2], k=3
Queue stores indices. Front is always the window maximum.

i=0 (5): push 0                               queue=[0]
i=1 (3): 3 < 5, push 1                        queue=[0,1]
i=2 (4): 4 > 3, pop 1; 4 < 5, push 2          queue=[0,2]       → window[0-2], max=arr[0]=5
i=3 (1): 1 < 4, push 3                        queue=[0,2,3]
         window is [1-3], idx 0 < 1 → EXPIRED, pop front!
                                              queue=[2,3]       → max=arr[2]=4
i=4 (2): 2 > 1, pop 3; 2 < 4, push 4          queue=[2,4]
         window is [2-4], idx 2 >= 2 → valid  → max=arr[2]=4

Output: [5, 4, 4] – Length of final result is `n - k + 1`.
```

**Why queue, not stack?** The front removal for expiration (see i=3). A stack can't efficiently remove from the front.

### Decreasing (non-increasing) Monotonic Stack (for "next greater element")

Stack holds indices of elements **waiting** for their next greater. When a new element is greater than the stack top, we've found the answer for that top.

```
Array: [2, 1, 2, 4, 3]
Find: next greater element for each

Process left to right, maintain decreasing stack (of indices):

i=0 (2): stack empty, push 0                          stack=[0]
i=1 (1): 1 < arr[0]=2, push 1                         stack=[0,1]
i=2 (2): 2 > arr[1]=1 → pop 1, ans[1]=2; push 2       stack=[0,2]
i=3 (4): 4 > arr[2]=2 → pop 2, ans[2]=4
         4 > arr[0]=2 → pop 0, ans[0]=4; push 3       stack=[3]
i=4 (3): 3 < arr[3]=4, push 4                         stack=[3,4]
End:     remaining indices have no next greater       ans[3]=-1, ans[4]=-1

Result: [4, 2, 4, -1, -1]
```

**Why decreasing?** Elements in the stack are waiting for something bigger. If a new element is smaller, it also needs to wait → push it. If bigger, it's the answer → pop and record.

## Complexity Analysis

| Operation | Amortized Time | Notes |
|-----------|----------------|-------|
| `push()` | `O(1)` | Each element pushed/popped at most once |
| `pop()` | `O(1)` | Remove from front |
| `max()` / `min()` | `O(1)` | Front of queue |

**Overall**: Processing n elements takes `O(n)` total, not `O(n²)`.

**Space**: `O(n)` worst case (when input is already sorted in desired order)

## When to Use: Pattern Recognition

### Use Monotonic STACK when:

| Pattern | Example Problem |
|---------|-----------------|
| "Next greater element" | Next Greater Element I, II (LC 496, 503) |
| "Next smaller element" | Daily Temperatures (LC 739) |
| "Previous greater/smaller" | Process left-to-right instead |
| Histogram problems | Largest Rectangle in Histogram (LC 84) |
| Stock span problems | Online Stock Span (LC 901) |

**Trigger phrases**: "next greater", "next smaller", "how many days until", "span of consecutive"

### Use Monotonic QUEUE when:

| Pattern | Example Problem |
|---------|-----------------|
| "Sliding window maximum" | Sliding Window Maximum (LC 239) |
| "Sliding window minimum" | Shortest Subarray with Sum ≥ K (LC 862) |
| Fixed-size window + max/min | Maximum of all subarrays of size k |
| Constrained optimization | Jump Game VI (LC 1696) |

**Trigger phrases**: "sliding window", "subarray of size k", "maximum/minimum in window"

## Classic Problems

### 1. Sliding Window Maximum (LC 239)
```
Input: nums = [1,3,-1,-3,5,3,6,7], k = 3
Output: [3,3,5,5,6,7]

Use decreasing monotonic queue:
- Push new element (remove smaller elements from back)
- Pop expired elements from front (index out of window)
- Front is always the window maximum
```

### 2. Largest Rectangle in Histogram (LC 84)
```
For each bar, find: how far left/right can it extend?
= find previous smaller & next smaller elements

Use increasing monotonic stack for both passes.
```

## Notes

1. **Increasing vs Decreasing**:
   - Decreasing queue/stack → finding **maximums** or **next greater**
   - Increasing queue/stack → finding **minimums** or **next smaller**

2. **Both directions work**: Left-to-right treats the stack as elements "waiting" for their answer. Right-to-left treats the stack as "candidates" to the right. Choose whichever mental model clicks for you.

3. **Queue vs Stack choice**:
   - Need to expire old elements? → Queue (sliding window)
   - Just need relationships? → Stack (next/previous element)

**Interview tip:** When you see `O(n²)` brute force for finding max/min or next greater/smaller, monotonic structures often give `O(n)`. The insight is that dominated elements can be discarded—if a newer element is better, older worse elements will never be chosen.

## Implementation Note

Our implementation is a **decreasing monotonic queue** (maintains maximum at front). For a monotonic stack, simply use a regular stack and apply the same comparison logic—no special data structure needed.
