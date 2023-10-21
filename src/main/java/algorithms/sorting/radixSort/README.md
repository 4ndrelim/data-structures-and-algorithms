# Radix Sort

Radix Sort is a non-comparison based, stable sorting algorithm. 

Radix Sort continuously sorts based on the least-significant segment of a element 
to the most-significant value of a element. 

The definition of a 'segment' is user defined and defers from implementation to implementation. 
Within our implementation, we define each segment as a bit chunk. 

For example, if we aim to sort integers, we can sort each element 
from the least to most significant digit, with the digits being our 'segments'. 

While Radix Sort is non-comparison based, 
the that total ordering of elements is still required. 
This is maintained in how the states are stored after sorting is conducted with respect to each digit.

This total ordering is needed because once we assigned a element to a order based on a segment, 
the order *cannot* change unless deemed by a segment with a higher significance. 
Hence a stable sort is required to maintain the order as 
the sorting is done with respect to each of the segments.

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
we get 10 queues.

*Source: Level Up Coding* 

## Complexity Analysis
**Time**:
Let *w* be the number of segments we sort through, *n* be the number of elements 
and *k* be the number of queues.

- Worst case: O(w * (n + k))
- Average case: O(w * (n + k))
- Best case (sorted array): O(w * (n + k))

**Space**: O(n + k) 

## Notes
- Radix sort's time complexity is dependent on the maximum number of digits in each element,
hence it is ideal to use it on integers with a large range and with little digits.
- This could mean that Radix Sort might end up performing worst on small sets of data 
if any one given element has a in-proportionate amount of digits.
- It is interesting to note that counting sort is used as a sub-routine within the 
Radix Sort process.

### Common Misconception
- While not immediately obvious, we can see that radix sort is a stable sorting algorithm as
  they are enqueued in a manner where the first observed element remains at the head of the queue.