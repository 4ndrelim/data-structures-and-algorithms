# Double Ended Queue (Deque)

![Deque](https://media.geeksforgeeks.org/wp-content/uploads/anod.png)

*Source: GeeksForGeeks*

Deque is a variant of queue where elements can be removed or added from the head and tail of the queue.
Deque could come in handy when trying to solve sliding window problems. This means it neither follows a fixed FIFO
or LIFO order but rather can utilise either orders flexibly.

A deque can be implemented in multiple ways, using doubly linked lists, arrays or two stacks.

## Analysis
Much like a queue, deque operations involves the head / tail, resulting in *O(1)* complexity for most operations.

## Notes
Just like a queue, a monotonic deque could also be created to solve more specific sliding window problems.


