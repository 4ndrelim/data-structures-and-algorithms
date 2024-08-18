# LRU Cache

## Background

Assuming that software engineers develop their applications using well-structured design patterns, programs tend to reuse data and instructions they've recently accessed (temporal locality) or access data elements that are close together in memory (spatial locality).

### Temporal Locality

The Least Recently Used (LRU) Cache operates on the principle that the data most recently accessed is likely to be accessed again in the near future (temporal locality). By evicting the least recently accessed items first, LRU cache ensures that the most relevant data remains available in the cache.

### Applications

<ol>
	<li>Operating systems: Operating systems use LRU cache for memory management in page replacement algorithms. When a program requires more memory pages than are available in physical memory, the operating system decides which pages to evict to disc based on LRU caching, ensuring that the most recently accessed pages remain in memory.</li>
	<li>Web browsers: Web browsers use LRU cache to store frequently accessed web pages. This allows users to quickly revisit pages without the need to fetch the entire content from the server.</li>
	<li>Databases: Databases use LRU cache to store frequent query results. This reduces the need to access the underlying storage system for repeated queries.</li>
</ol>

### Data Structures

Implementing an LRU cache typically involves using a combination of data structures. A common approach is to use a doubly-linked list to maintain the order of items based on access recency and a hash map to achieve constant-time access to any item in the cache. This combination effectively creates a data structure that supports the operations required for LRU cache. As nodes are connected in a doubly-linked list fashion, updating neighbours when rearranging recently cached items is as simple as redirecting the next and previous pointers of affected nodes.

<img src = "https://cdn.hashnode.com/res/hashnode/image/upload/v1655812960691/pqAZ20NyS.png?auto=compress,format&format=webp" alt = "Hash Map">

### Cache Key

The hash map values are accessed through cache keys, which are unique references to the cached items in a LRU cache. Moreover, storing key-value pairs of hash keys and their corresponding nodes, which encapsulate cached items in a hash map and allows us to avoid O(n) sequential access of cached items.

### Eviction

When the cache is full and a new item needs to be added, the eviction process is triggered. The item at the back of the list, which represents the least recently used data, is removed from both the list and the hash map. The new item is then added to the front of the list, and the cache key is stored in the hash map along with its corresponding cache value. 

However, if a cached item is accessed through a read-only operation, we still move the cached item to the front of the list without any eviction. Therefore, any form of interaction with a key will move its corresponding node to the front of the doubly-linked list without evection being triggered. Eviction is only applicable to write operations when a cache is considered full.

## Complexity Analysis

**Time**: **expected** O(1) complexity

As we rely on basic hash map operations to insert, access, and delete cache nodes, the get and put operations supported by LRU cache are influenced by the time complexity of these hash map operations. Insertion, lookup, and deletion operations in a well-designed hash map take O(1) time on average. Therefore, the hash map provides expected O(1) time on operations, and the doubly-linked list provides insertion and removal of nodes in O(1) time.

**Space**: O(cache capacity)

## Notes

<ol>
	<li>Cache hit/miss ratio: A simple metric for measuring the effectiveness of the cache is the cache hit ratio. It is represented by the percentage of requests that are served from the cache without needing to access the original data store. Generally speaking, for most applications, a hit ratio of 95 - 99% is ideal.</li>
	<li>Outdated cached data: A cached item that is constantly accessed and remains in cache for too long may become outdated.</li>
	<li>Thread safety: When working with parallel computation, careful considerations have to be made when multiple threads try to access the cache at the same time. Thread-safe caching mechanisms may involve the proper use of mutex locks.</li>
	<li>Other caching algorithms: First-In-First-Out (FIFO) cache, Least Frequently Used (LFU) cache, Most Recently Used (MRU) cache, and Random Replacement (RR) cache. The performance of different caching algorithms depends entirely on the application. LRU caching provides a good balance between performance and memory usage, making it suitable for a wide range of applications as most applications obey recency of data access (we often do reuse the same data in many applications). However, in the event that access patterns are random or even anti-recent, random replacement may perform better as it has less overhead when compared to LRU due to lack of bookkeeping.</li>
</ol>
