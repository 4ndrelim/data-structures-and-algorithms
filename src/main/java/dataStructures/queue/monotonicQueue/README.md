# Monotonic Queue

This is a variant of queue where elements within the queue are either strictly increasing or decreasing.
Monotonic queues are often implemented with a deque.

Within a increasing monotonic queue, any element that is smaller than the current minimum is removed.
Within a decreasing monotonic queue, any element that is larger than the current maximum is removed.

It is worth mentioning that the most elements added to the monotonic queue would always be in a
increasing / decreasing order,
hence, we only need to compare down the monotonic queue from the back when adding new elements.

## Operation Orders
Just like a queue, a monotonic queue mainly has *O(1)* operations, 
which is unique given that it ensures a certain order, which usually incurs operations of a higher complexity.