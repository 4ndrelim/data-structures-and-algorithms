# Queue

## Background

A queue is a linear data structure that follows **FIFO** (First In, First Out) order - the earliest element added is the first one removed.

<div align="center">
    <img src="../../.gitbook/assets/queue.png" alt="Queue" width="60%"/>
    <br/>
    <em>Source: GeeksForGeeks</em>
</div>

### Core Operations
- **enqueue** (offer/add): Add element to the back
- **dequeue** (poll/remove): Remove element from the front
- **peek**: View front element without removing

## Complexity Analysis

| Operation | Time | Notes |
|-----------|------|-------|
| `enqueue()` | `O(1)` | Add to back |
| `dequeue()` | `O(1)` | Remove from front |
| `peek()` | `O(1)` | Access front |
| `isEmpty()` | `O(1)` | Check size |

**Space**: `O(n)` for n elements

## Notes

1. **Array vs Linked List**: Our implementation uses a linked list, allowing unbounded growth. Array-based queues are faster (cache-friendly) but need resizing or circular buffer logic.

   <details>
   <summary><b>Why a circular buffer?</b></summary>

   The natural worry with an array-backed queue is: if we dequeue from index 0, don't we have to shift every remaining element left by one to keep things contiguous? That would make dequeue `O(n)`.

   The fix is to **not shift at all**. Track a `head` index and a `tail` index instead of always anchoring the queue at index 0. Enqueue writes at `tail` and advances `tail`; dequeue reads at `head` and advances `head`. Both indices wrap around modulo the array length when they hit the end — hence "circular." Now both operations are genuinely `O(1)`, and the array slots in front of `head` get reused as the queue moves forward.

   The only complication is detecting full vs empty (both states have `head == tail`), usually solved by storing the size separately or leaving one slot unused as a sentinel.
   </details>

2. **Java equivalents**: `enqueue()` → `offer()`/`add()`, `dequeue()` → `poll()`/`remove()`

3. **Queue vs Stack**:
   - Queue (FIFO): Process in arrival order → BFS, task scheduling, buffering
   - [Stack](../stack/README.md) (LIFO): Process most recent first → DFS, undo/redo, parsing

## Applications

| Use Case | Why Queue? |
|----------|-----------|
| BFS traversal | Process nodes level by level |
| Task scheduling | First-come-first-served processing |
| Buffering | Handle producer-consumer speed mismatch |
| Print spooling | Documents printed in submission order |

## Variants

- [**Deque**](./Deque/README.md) - Double-ended queue, add/remove from both ends
- [**Monotonic Queue**](./monotonicQueue/README.md) - Maintains sorted order, useful for sliding window problems
- **Priority Queue** - Elements ordered by priority, not arrival time (see [Heap](../heap/README.md))
- **Circular Queue** - Fixed-size array with wrap-around, efficient for bounded buffers
