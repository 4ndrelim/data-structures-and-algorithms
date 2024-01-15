# Quick Find

## Background
Every object will be assigned a component identity. The implementation of Quick Find often involves
an underlying array or hash map that tracks the component identity of each object. Here, we use a hash map.

### Union
Between the two components, decide on the component d, to represent the combined set. Let the other
component's identity be d'. Simply iterate over the component identifier array / map, and for any element with
identity d', assign it to d.

### Find
Simply use the component identifier array to query for the component identity of the two elements
and check if they are equal. This is why this implementation is known as "Quick Find".

## Complexity Analysis
Let n be the number of elements in consideration.

**Time**: O(n) for Union and O(1) for Find operations

**Space**: O(n) auxiliary space for the component identifier

## Notes