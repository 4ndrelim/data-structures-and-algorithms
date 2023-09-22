# Radix Sort

Radix Sort is a non-comparison based, stable sorting algorithm. 
We first treat each element as a string with *w* digits (Padding elements that have 
less than *w* digits).

From the least-significant digit to the most-significant digit, we constantly 
split them into ten queues corresponding to the number range *[0, 9]*. We then move 
through the queue and concatenate the elements back into a list at the next iteration.

This takes advantage of the concept of place value. 
(The value of a digit in a number relative to its position within the number)

![Radix Sort](https://miro.medium.com/v2/resize:fit:661/1*xFnpQ4UNK0TvyxiL8r1svg.png)

*Source: Level Up Coding* 

## Complexity Analysis
**Time**:
Note that we will always need to iterate through 10 different queues to rebuild our original array,
and we iterate through all *w* positions, this results in:

- Worst case: O(w * (n + 10))
- Average case: O(w * (n + 10))
- Best case (sorted array): O(w * (n + 10))

**Space**: O(n + 10) 

## Notes
- Radix sort's time complexity is dependent on the maximum number of digits in each element,
hence it is ideal to use it on integers with a large range and with little digits.
- This could mean that Radix Sort might end up performing worst on small sets of data 
if any one given element has a in-proportionate amount of digits.
- Counting sort is used as a sub-routine within the Radix Sort process.

### Common Misconception
- While not immediately obvious, we can see that radix sort is a stable sorting algorithm as
  they are enqueued in a manner where the first observed element will be at the head of the queue.
- While it is non-comparison based, not that total ordering of elements is still required -
  except now this property is forced upon the algorithm in the manner of the queues.