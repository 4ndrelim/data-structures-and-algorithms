# HashSet (Open-addressing)
Open-addressing is another approach to resolving collisions in hash tables.

A hash collision is resolved by <b>probing<b>, or searching through alternative locations in 
the array (the probe sequence) until either the target element is found, or an unused array slot is found,
which indicates that there is no such key in the table.

## Probing Strategies

### Linear Probing
The probing strategy used in our implementation.

Simplest form of probing and involves linearly searching the hash table for an empty spot upon collision.

However, this method of probing can result in a phenomenon called (primary) clustering where a large run of
occupied slots builds up, which can drastically degrade the performance of add, remove and contains operations.

h(k, i) = (h'(k) + i) mod m where h'(k) is an ordinary hash function

### Quadratic Probing
This method of probing involves taking the original hash index, and adding successive values of an arbitrary quadratic 
polynomial until an open slot is found.

This helps to avoid primary clustering of entries (like in Linear Probing), but might still result in secondary
clustering where keys that hash to the same value probe the same alternative cells when a collision occurs.

h(k, i) = ( h`(k) + c1 * i + c2 * (i^2) ) mod m  where c1 and c2 are arbitrary constants

### Double Hashing
This is a method of probing where a secondary hash function is used for probing whenever a collision occurs.

If h2(k) is relatively prime to m for all k, Uniform Hashing Assumption can hold true, as all permutations of probe 
sequences occur in equal probability.

h(k, i) = (h1(k) + i * h2(k)) mod m where h1(k) and h2(k) are two ordinary hash functions

*Source: https://courses.csail.mit.edu/6.006/fall11/lectures/lecture10.pdf*

## Analysis
let α = n / m where α is the load factor of the table

For n items, in a table of size m, assuming uniform hashing, the expected cost of an operation is:

<div style="text-align: center;">1/1-α</div>

e.g. if α = 90%, then E[#probes] = 10;

