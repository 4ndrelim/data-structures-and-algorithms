# HashSet (Open-addressing)

## Background

Open-addressing is another approach to resolving collisions in hash tables.

A hash collision is resolved by **probing** - searching through alternative locations in
the array (the probe sequence) until either the target element is found, or an unused array slot is found,
which indicates that there is no such key in the table.

## Implementation Invariant

Note that the buckets are 1-indexed in the following explanation.

Invariant: ***Probe sequence is unbroken.***

That is to say, given an element that is initially hashed to
bucket 1 (arbitrary), the probe sequence {1, 2, ..., m} generated when attempting to `add`/`remove`/`find`
the element will ***never*** contain null.

This invariant is used to help us ensure the correctness and efficiency of `add`/`remove`/`contains`.
With the above example of an element generating a probe sequence {1, 2, ...}, `add` will check each bucket
sequentially, attempting to add the element, treating buckets containing `Tombstones` (to be explained later) and
`nulls` as **empty** buckets available for insertion.

As a result, if the bucket is inserted in bucket `m`, such that the probe sequence {1, 2, ..., m} is
generated, then there must have been elements occupying buckets {1, 2, ..., m - 1}, resulting in collisions.

`remove` maintains this invariant with the help of a `Tombstone` class. As explained in the CS2040S lecture notes,
simply replacing the element to be removed with `null` will cause `contains` to **fail** to find an element, even if it
was present.

`Tombstones` allow us to mark the bucket as deleted, which allows `contains` to know that there is a
possibility that the targeted element can be found later in the probe sequence, returning false immediately upon
encountering `null`.

We could simply look into every bucket in the sequence, but that will result in `remove` and `contains` having an O(n)
runtime complexity, defeating the purpose of hashing.

TLDR: There is a need to differentiate between deleted elements, and `nulls` to ensure operations on the Set have an
O(1) time complexity.

## Probing Strategies

For Open-Addressing, the hash function differs from that of Chaining, in that the number of collisions encountered
when inserting the key into the Hash Set is taken into account into determining the hash value.

In the following probe strategies, the hash function typically looks like a variation of:
<div style="text-align: center;"><code>h(k, i) = (h'(k) + i) mod m</code></div>

`h'(k)` would be the equivalent of a typical hash function used in a HashSet that resolves collisions by Chaining,
while an additional parameter `i` indicates the number of collisions so far.

Take Linear Probing with a step size of 1 as an example.
Given an element `k` hashed to bucket 1 initially, such that:
<div style="text-align: center;"><code>h(k, 0) = 1</code></div>

then, if there was already an element in bucket 1 resulting in a collision, then the next bucket index is determined by:
<div style="text-align: center;"><code>h(k, 1) = 1 + 1 = 2</code></div>

### Linear Probing

The probing strategy used in our implementation.

Simplest form of probing and involves linearly searching the hash table for an empty spot upon collision.

However, this method of probing can result in a phenomenon called (primary) clustering where a large run of
occupied slots builds up, which can drastically degrade the performance of add, remove and contains operations.

`h(k, i) = (h'(k) + i) mod m`
where `h'(k)` is an ordinary hash function, and `i` is the number of collisions so far.

### Quadratic Probing

This method of probing involves taking the original hash index, and adding successive values of an arbitrary quadratic
polynomial until an open slot is found.

This helps to avoid primary clustering of entries (like in Linear Probing), but might still result in secondary
clustering where keys that hash to the same value probe the same alternative cells when a collision occurs.

`h(k, i) = (h'(k) + c1 * i + c2 * (i^2)) mod m` where `c1` and `c2` are arbitrary constants

### Double Hashing

This is a method of probing where a secondary hash function is used for probing whenever a collision occurs.

If `h2(k)` is relatively prime to `m` for all `k`, Uniform Hashing Assumption can hold true, as all permutations of
probe sequences occur in equal probability.

`h(k, i) = (h1(k) + i * h2(k)) mod m` where `h1(k)` and `h2(k)` are two ordinary hash functions

*Source: https://courses.csail.mit.edu/6.006/fall11/lectures/lecture10.pdf*

## Complexity Analysis

let `α = n / m` where `α` is the load factor of the table

For `n` items, in a table of size `m`, assuming uniform hashing, the expected cost of an operation is:

<div style="text-align: center;"><code>1/1-α</code></div>

e.g. if `α` = 90%, then `E[#probes]` = 10;

## Notes

### Properties of Good Hash Functions

There are two properties to measure the "goodness" of a Hash Function

1. `h(key, i)` enumerates all possible buckets.
    - For every bucket `j`, there is some `i` such that: `h(key, i) = j`
    - The hash function is a permutation of {1..m}.

Linear probing satisfies the first property, because it will probe all possible buckets in the Set. I.e. if an element
is initially hashed to bucket 1, in a Set with capacity n, linear probing generates a sequence of {1, 2, ..., n - 1, n},
enumerating every single bucket.

2. Uniform Hashing Assumption (NOT SUHA)
    - Every key is equally likely to be mapped to every ***permutation***, independent of every other key.
    - Under this assumption, the probe sequence should be randomly, and uniformly distributed among all possible
      permutations, implying `n!` permutations for a probe sequence of size `n`.
    - Linear Probing does ***NOT*** fulfil UHA. In linear probing, when a collision occurs, the HashSet handles it by
      checking the next bucket, linearly until an empty bucket is found. The next slot is always determined in a fixed
      linear manner.
    - In practicality, achieving UHA is difficult. Double hashing can come close to achieving UHA, by using another
      hash function to vary the step size (unlike linear probe where the step size is constant), resulting in a more
      uniform distribution of keys and better performance for the hash table.
