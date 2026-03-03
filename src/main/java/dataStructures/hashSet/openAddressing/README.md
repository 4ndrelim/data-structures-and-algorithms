# HashSet (Open Addressing)

## Background

**Open addressing** stores all elements directly in the hash table array. When a collision occurs, we **probe** for the next available slot.

```
hash(key) → index → if occupied, probe next slot

[Alice] [_] [Bob] [Carol] [Dave] [_] [Eve] [_]
                    ↑
          collision → probed here
```

See [parent README](../README.md) for comparison with chaining.

## How It Works

### Probe Sequence

The hash function for open addressing takes an additional parameter - the number of collisions (probe attempts):

`h(k, i) = (h'(k) + f(i)) mod m`

Where `h'(k)` is the base hash and `f(i)` varies by probing strategy.

### The Tombstone Problem

**Invariant**: The probe sequence for any element must be unbroken.

Consider inserting A, B, C that all hash to slot 0:

```
Insert A, B, C: [A] [B] [C] [_] ...     (B, C probed to slots 1, 2)
```

Now remove B:

```
WRONG:   [A] [null] [C] [_] ...   contains(C) stops at null → returns false!
CORRECT: [A] [TOMB] [C] [_] ...   tombstone signals "keep probing"
```

**Why tombstones?** If we set deleted slots to `null`, we break the probe sequence. Elements inserted via probing become unreachable. Tombstones mark "deleted but continue probing".

| Operation | `null` slot | Tombstone | Matching element |
|-----------|-------------|-----------|------------------|
| `add()` | Insert here | Insert here | Duplicate, skip |
| `contains()` | Return false | Keep probing | Return true |
| `remove()` | Return false | Keep probing | Replace with tombstone |

## Probing Strategies

### Linear Probing (Our Implementation)

`h(k, i) = (h'(k) + i) mod m`

Step size is always 1. Simple and cache-friendly.

**Problem: Primary clustering** - long runs of occupied slots build up, degrading performance.

```
[X] [X] [X] [X] [X] [_] [_]    ← cluster slows insertions
```

### Quadratic Probing

`h(k, i) = (h'(k) + c₁*i + c₂*i²) mod m`

Step size grows quadratically, reducing primary clustering.

**Problem: Secondary clustering** - keys with same initial hash follow same probe sequence.

### Double Hashing

`h(k, i) = (h₁(k) + i * h₂(k)) mod m`

Second hash function determines step size. Approaches Uniform Hashing Assumption (UHA).

**Requirement**: `h₂(k)` must be relatively prime to `m` to ensure all slots are probed.

### Expected Number of Probes

All three strategies have `O(1)` expected time, but **expected probes differ significantly** due to clustering:

| Strategy | Clustering | Expected Probes (α=0.9) | Cache Efficiency |
|----------|------------|-------------------------|------------------|
| Linear | Primary (worst) | ~50 | Best |
| Quadratic | Secondary | ~10 | Good |
| Double Hashing | None | ~2.6 | Worst |

Double hashing approaches the theoretical optimal under UHA (`1/(1-α)` ≈ 10 probes at α=0.9), while
linear probing degrades much faster due to clustering. The tradeoff: double hashing has worse cache
locality since probes are non-sequential.

**In practice**: Linear probing often wins for low-to-medium load factors due to cache efficiency.
Double hashing shines at high load factors where clustering dominates.

## Complexity Analysis

| Operation | Expected | Worst |
|-----------|----------|-------|
| `add()` | `O(1)` | `O(n)` |
| `contains()` | `O(1)` | `O(n)` |
| `remove()` | `O(1)` | `O(n)` |

Expected probes under uniform hashing: `1/(1-α)` where `α = n/m`.

| Load Factor α | Expected Probes |
|---------------|-----------------|
| 50% | 2 |
| 75% | 4 |
| 90% | 10 |
| 99% | 100 |

**This is why resizing is mandatory** - as `α → 1`, performance degrades sharply.

## Notes

1. **Two properties of good hash functions**:
   - **Coverage**: `h(key, i)` must enumerate all buckets (be a permutation of {0..m-1})
   - **Uniform Hashing Assumption (UHA)**: Every probe sequence permutation equally likely

   Linear probing satisfies coverage but NOT UHA. Double hashing approaches UHA.

2. **Resizing**: Our implementation resizes up at 75% load, down at 25% load. Rehashing eliminates tombstones as a side benefit.

3. **Cache efficiency**: Sequential probing is cache-friendly - better practical performance than chaining for small/medium tables.

4. **When to prefer open addressing**:
   - Memory-constrained (no pointer overhead)
   - Small to medium datasets
   - Cache efficiency matters

**Interview tip:** The tombstone mechanism is the trickiest part. Be ready to explain why simply setting deleted slots to `null` breaks `contains()`. Draw out an example with colliding elements.

*Reference: [MIT 6.006 Lecture Notes](https://courses.csail.mit.edu/6.006/fall11/lectures/lecture10.pdf)*
