# Stack

## Background
Stack is a linear data structure that restricts
the order in which operations can be performed on its elements.

![Stack data structure](https://cdn.programiz.com/sites/tutorial2program/files/stack.png)

*Source: Programiz*

### Operation Orders

Stack follows a LIFO, last in first out order.
This means the most recent element
added to the stack is the one operations are conducted on first.

A [queue](../queue/README.md) conducts operations in the opposite order.

## Analysis

As a stack only interacts with the most recent element regardless of operation,
it keeps the pointer of the most recent element at hand, which is constantly updated as more
elements are removed / added. This allows stack operations to only incur a *O(1)* time complexity.

## Notes

### Stack vs Queues

Stack and queues only differ in terms of operation order, you should aim to use a stack when
you want the most recent elements to be operated on.
Some situations where a stack would work well include build redo / undo systems and backtracking problems.

On the other hand, a queue allows you to operate on elements that enter first. Some situations where
this would be useful include Breadth First Search algorithms and task / resource allocation systems.

### Arrays vs Linked List

It is worth noting that stacks can be implemented with either a array or with a [linked list](../linkedList/README.md).
In the context of ordered operations, the lookup is only restricted to the first element.

Hence, there is not much advantage in using a array, which only has a better lookup speed (*O(1)* time complexity)
to implement a stack. Especially when using a linked list to construct your stack
would allow you to grow or shrink the stack as you wish.
