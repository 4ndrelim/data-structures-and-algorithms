# Radix Sort

## Background

Radix Sort is a non-comparison based, stable sorting algorithm with a counting sort subroutine.

Radix Sort continuously sorts based on the least-significant segment of a element
to the most-significant value of a element.

The definition of a 'segment' is user defined and defers from implementation to implementation.
Within our implementation, we define each segment as a bit chunk.

For example, if we aim to sort integers, we can sort each element
from the least to most significant digit, with the digits being our 'segments'.

Within our implementation, we take the binary representation of the elements and
partition it into 8-bit segments, a integer is represented in 32 bits,
this gives us 4 total segments to sort through.

Note that the binary representation is weighted positional,
where each bit's value is dependent on its overall position
within the representation (the n-th bit from the right represents *2^n*),
hence we can actually increase / decrease the number segments we wish to conduct a split from.

![Radix Sort](https://miro.medium.com/v2/resize:fit:661/1*xFnpQ4UNK0TvyxiL8r1svg.png)

We place each element into a queue based on the number of possible segments that could be generated.
Suppose the values of our segments are in base-10, (limited to a value within range *[0, 9]*),
we get 10 queues. We can also see that radix sort is stable since
they are enqueued in a manner where the first observed element remains at the head of the queue

*Source: Level Up Coding*

### Implementation Invariant

At the start of the *i-th* segment we are sorting on, the array has already been sorted on the
previous *(i - 1)-th* segments.

### Common Misconceptions

While Radix Sort is non-comparison based,
the that total ordering of elements is still required.
This total ordering is needed because once we assigned a element to a order based on a segment,
the order *cannot* change unless deemed by a segment with a higher significance.
Hence, a stable sort is required to maintain the order as
the sorting is done with respect to each of the segments.

## Complexity Analysis

**Time**:
Let *b* be the length of a single element we are sorting and *r* is the amount of bit-string
we plan to break each element into.
(Essentially, *b/r* represents the number of segments we
sort on and hence the number of passes we do during our sort).

Note that we derive *(2^r + n)* from the counting sort subroutine,
since we have *2^r* represents the range since we have *r* bits.

We get a general time complexity of *O((b/r) * (2^r + n))*

**Space**: *O(n + 2^r)*

## Notes

- Radix sort's time complexity is dependent on the maximum number of digits in each element,
  hence it is ideal to use it on integers with a large range and with little digits.
- This could mean that Radix Sort might end up performing worst on small sets of data
  if any one given element has a in-proportionate amount of digits.
