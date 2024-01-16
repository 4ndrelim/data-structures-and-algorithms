# Radix Sort

## Background

Radix Sort is a non-comparison based, stable sorting algorithm that conventionally uses counting sort as a subroutine.

Radix Sort performs counting sort several times on the numbers. It sorts starting with the least-significant segment 
to the most-significant segment.

### Segments
The definition of a 'segment' is user defined and defers from implementation to implementation.
It is most commonly defined as a bit chunk.

For example, if we aim to sort integers, we can sort each element
from the least to most significant digit, with the digits being our 'segments'.

Within our implementation, we take the binary representation of the elements and
partition it into 8-bit segments. An integer is represented in 32 bits,
this gives us 4 total segments to sort through.

Note that the number of segments is flexible and can range up to the number of digits in the binary representation.
(In this case, sub-routine sort is done on every digit from right to left)

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
Let b-bit words be broken into r-bit pieces. Let n be the number of elements to sort.

*b/r* represents the number of segments and hence the number of counting sort passes. Note that each pass
of counting sort takes *(2^r + n)* (O(k+n) where k is the range which is 2^r here).

**Time**: *O((b/r) * (2^r + n))*

**Space**: *O(n + 2^r)*

### Choosing r
Previously we said the number of segments is flexible. Indeed, it is but for more optimised performance, r needs to be
carefully chosen. The optimal choice of r is slightly smaller than logn which can be justified with differentiation.

Briefly, r=lgn --> Time complexity can be simplified to (b/lgn)(2n). <br>
For numbers in the range of 0 - n^m, b = mlgn and so the expression can be further simplified to *O(mn)*.

## Notes
- Radix sort's time complexity is dependent on the maximum number of digits in each element,
  hence it is ideal to use it on integers with a large range and with little digits.
- This could mean that Radix Sort might end up performing worst on small sets of data
  if any one given element has a in-proportionate amount of digits.
