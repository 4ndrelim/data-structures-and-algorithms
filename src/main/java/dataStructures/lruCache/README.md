# LRU Cache

## Background

An **LRU (Least Recently Used) Cache** is a fixed-size cache that evicts the least recently accessed item when full. It exploits **temporal locality** - the principle that recently accessed data is likely to be accessed again soon.

### How It Works

The cache maintains items ordered by recency of access:
- **On access (`get`)**: Move item to front (most recent)
- **On insert (`put`)**: Add to front; if full, evict from back (least recent)
- **On update (`put` existing key)**: Update value and move to front

### Data Structures

LRU cache combines two structures for `O(1)` operations:

| Structure | Purpose |
|-----------|---------|
| **HashMap** | `O(1)` lookup by key |
| **Doubly-linked list** | `O(1)` insertion/removal, maintains recency order |

```
Most Recent                                  Least Recent
    ↓                                             ↓
[HEAD] ⟷ [Node A] ⟷ [Node B] ⟷ [Node C] ⟷ [TAIL]
                                    ↑
                            Evict this on overflow
```

The HashMap stores `key → node` mappings, allowing direct access to any node. The doubly-linked list allows `O(1)` move-to-front and removal without traversal.

**Implementation detail**: Our implementation uses **sentinel nodes** (dummy head and tail), which eliminates null checks when inserting/removing at boundaries.

## Complexity Analysis

| Operation | Time | Notes |
|-----------|------|-------|
| `get(key)` | `O(1)` expected | HashMap lookup + move to front |
| `put(key, value)` | `O(1)` expected | HashMap insert + list manipulation |

**Space**: `O(capacity)` - stores at most `capacity` items

**Interview tip:** LRU Cache (LeetCode 146) is a classic design question. The key insight is combining HashMap + doubly-linked list. Know how to implement the move-to-front operation cleanly.

## Notes

1. **Why doubly-linked?** When removing a node, we need to update its predecessor's `next` pointer. With a singly-linked list, finding the predecessor requires `O(n)` traversal.

2. **Cache hit ratio**: A good cache achieves 95-99% hit ratio. If hits are rare, caching overhead may not be worthwhile.

3. **Stale data**: Frequently accessed items may stay cached indefinitely, becoming outdated. Consider TTL (time-to-live) for data that changes.

4. **Thread safety**: For concurrent access, synchronization is needed (e.g., locks, concurrent data structures). Our implementation is not thread-safe.

## Applications

| Use Case | Example |
|----------|---------|
| OS page replacement | Keep recently used memory pages in RAM |
| Web browsers | Cache recently visited pages |
| Database query cache | Cache frequent query results |
| CDN edge caching | Cache popular content at edge servers |

## Other Caching Strategies

| Strategy | Eviction Rule | Best For |
|----------|---------------|----------|
| **LRU** | Least recently used | General purpose, temporal locality |
| **LFU** | Least frequently used | Popularity-based access patterns |
| **FIFO** | First in, first out | Simple, predictable eviction |
| **MRU** | Most recently used | Stack-like access (e.g., file scans) |
| **Random** | Random eviction | Low overhead, unpredictable access |

**Interview tip:** Be ready to discuss trade-offs. LRU has more bookkeeping overhead than FIFO/Random but performs better when access patterns show temporal locality.
