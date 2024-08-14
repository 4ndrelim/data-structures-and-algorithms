# LRU Cache

## Background

Assuming that software engineers develop their applications using well-structured design patterns, programs tend to reuse data and instructions they've recently accessed (temporal locality) or access data elements that are close together in memory (spatial locality).

### Temporal Locality

The Least Recently Used (LRU) Cache operates on the principle that the data most recently accessed is likely to be accessed again in the near future (temporal locality). By evicting the least recently accessed items first, LRU cache ensures that the most relevant data remains available in the cache.

### Data Structures

Implementing an LRU cache typically involves using a combination of data structures. A common approach is to use a doubly-linked list to maintain the order of items based on access recency, and a hash map to achieve constant-time access to any item in the cache. This combination effectively creates a data structure that supports the operations required for LRU cache.

### Cache Key

The hash map values are accessed through a cache key, which are unique references to the cached items in a LRU cache.

### Eviction

When the cache is full and a new item needs to be added, the eviction process is triggered. The item at the back of the list, which represents the least recently used data, is removed from both the list and the hash map. The new item is then added to the front of the list and the cache key is stored in the hash map along with its corresponding cache value. If a cached item is accessed through a read-only operation, we still move the cached item to the front of the list without any eviction.

## Complexity Analysis

**Time**: O(1) **average** complexity

**Space**: O(cache capacity)

## Notes

<ol>
	<li>Cache hit/miss ratio: A simple metric for measuring the effectiveness of the cache is the cache hit ratio. It is represented by the percentage of requests that are served from the cache without needing to access the original data store. Generally speaking, for most applications, a hit ratio of 95 - 99% is ideal.</li>
	<li>Outdated cached data: A cached item which is constantly accessed and remains in cache for too long may become outdated.</li>
	<li>Thread safety: When working with parallel computation, careful considerations have to be made when multiple threads try to access the cache at the same time. Thread-safe caching mechanisms may involve the proper use of mutex locks.</li>
	<li>Other caching algorithms: First-In-First-Out (FIFO) cache, Least Frequently Used (LFU) cache, Most Recently Used (MRU) cache, and Random Replacement (RR) cache.</li>
</ol>
