# Quick Find

## Background
Every object will be assigned a component identity. The implementation of Quick Find often involves
an underlying array or hash map that tracks the component identity of each object. 
Our implementation uses a hash map (to easily handle the case when objects aren't integers).

<div align="center">
    <img src="../../../../../../docs/assets/images/QuickFind.png" width="50%">
    <br>
    Credits: CS2040s Lecture Slides
</div>

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