# HashSet (Chaining)

## Background

**Chaining** resolves hash collisions by storing all elements that hash to the same bucket in a linked list.

```
hash(key) → bucket index → append to list at bucket

Bucket 0: → [Alice] → [Dave] → null     (both hash to 0)
Bucket 1: → [Bob] → null
Bucket 2: → [Carol] → [Eve] → null
Bucket 3: → null (empty)
```

See [parent README](../README.md) for comparison with open addressing.

## How It Works

### Add
1. Compute `bucket = hash(key) % m`
2. Search linked list at `buckets[bucket]` for duplicates
3. If not found, insert at front of list

### Contains
1. Compute bucket index
2. Linear search through linked list
3. Return true if found

### Remove
1. Compute bucket index
2. Search list for element
3. Remove node from linked list

## Complexity Analysis

| Operation | Expected | Worst |
|-----------|----------|-------|
| `add()` | `O(1)` | `O(n)` |
| `contains()` | `O(1)` | `O(n)` |
| `remove()` | `O(1)` | `O(n)` |

**Expected time**: `O(1 + α)` where `α = n/m` is the load factor.

Under SUHA, each bucket has `α` elements on average, so list traversal is `O(α)`. With `m = Θ(n)`, we get `α = O(1)`.

**Worst case**: All n elements hash to the same bucket → `O(n)` list traversal.

## Notes

1. **No resizing required** (unlike open addressing): Lists can grow indefinitely. However, performance degrades as lists get longer, so resizing is still recommended.

2. **Our implementation**: Uses fixed 256 buckets without resizing. For production use, resize when `α > 1`.

3. **Java's approach**: Java HashMap starts with linked lists, then converts to Red-Black Trees when a bucket exceeds 8 elements. This bounds worst-case to `O(log n)`.

4. **Memory overhead**: Each node requires extra pointer(s) for the linked list structure. This also hurts cache locality compared to open addressing.

5. **Deletion is simple**: Just remove the node from the linked list. No tombstones needed (unlike open addressing).

**Interview tip:** Chaining is simpler to implement correctly than open addressing. When asked to implement a hash table from scratch in an interview, chaining is often the safer choice.
