# Queue

A queue is a linear data structure that restricts the order in which operations can be performed on its elements.

### Operation Orders

![Queue](https://media.geeksforgeeks.org/wp-content/cdn-uploads/20221213113312/Queue-Data-Structures.png)

*Source: GeeksForGeeks*

Queue follows a FIFO, first in first out order.
This means the earliest element
added to the stack is the one operations are conducted on first.

A [stack](../stack/README.md) is a queue with operations conducted in an opposite manner.

## Analysis

As a queue only interacts with either the first or last element regardless during its operations,
it only needs to keep the pointers of the two element at hand, which is constantly updated as more
elements are removed / added. This allows queue operations to only incur a *O(1)* time complexity.

## Notes

### Stack vs Queues

Stack and queues only differ in terms of operation order, you should aim to use a stack when
you want the most recent elements to be operated on.
Some situations where a stack would work well include build redo / undo systems and backtracking problems.

On the other hand, a queue allows you to operate on elements that enter first. Some situations where
this would be useful include Breadth First Search algorithms and task / resource allocation systems.

### Arrays vs Linked List
It is worth noting that queues can be implemented with either a array or with a [linked list](../linkedList/README.md).
In the context of ordered operations, the lookup is only restricted to the first element.

Hence, there is not much advantage in using a array, which only has a better lookup speed (*O(1)* time complexity)
to implement a queue. Especially when using a linked list to construct your queue
would allow you to grow or shrink the queue as you wish.


#### Monotonic Queue

This is a variant of queue where elements within the queue are either strictly increasing or decreasing.
Monotonic queues are often implemented with a deque.

Within a increasing monotonic queue, any element that is smaller than the current minimum is removed.
Within a decreasing monotonic queue, any element that is larger than the current maximum is removed.

It is worth mentioning that the most elements added to the monotonic queue would always be in a 
increasing / decreasing order,
hence, we only need to compare down the monotonic queue from the back when adding new elements.

