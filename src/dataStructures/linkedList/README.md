# Linked Lists
Linked lists are a linear structure used to store data elements. 
It consists of a collection of objects, used to store our data elements, known as nodes.

![Linked list image](https://media.geeksforgeeks.org/wp-content/cdn-uploads/20230726162542/Linked-List-Data-Structure.png)
*Source: GeeksForGeeks*

## Linked Lists vs Arrays
Linked lists are similar to arrays in 
terms of used and purpose, but there are considerations when deciding which structure to use. 

Unlike arrays, which are stored in contiguous locations in memory,
linked lists are stored across memory and are connected to each other via pointers.

![Array image](https://beginnersbook.com/wp-content/uploads/2018/10/array.jpg)
*Source: BeginnersBook*

### Search

Due to the contiguous nature of an array, we only need the memory address for the first element
to calculate the address for any subsequent element, resulting in a *O(1)* lookup time.

However, to look for a element in a linked list of size *n*, we would have to traverse through each node
to get the pointer to the next node until we reached our required node. This creates a *O(n)* lookup time.

There are also some unique methods to speed up your linked lists searches, which will be discussed in a later section.

**Generally, searching a index in a array is faster than a linked list.**

### Insert

#### Front & End Insertion
When we try to insert a element from the beginning and the end of array, to keep the contiguity in memory,
we need to shift all other elements to the left (in the case of a end insertion)
or right (in the case of a front insertion). This incurs a *O(n)* time complexity.

However, we just need to update the pointer address in our new node (for a front insertion), or a 
pointer address in our last node (for a end insertion) in a linked list, incurring a *O(1)* time complexity.

#### Inserting into the *i-th* index
To insert a element into the *i-th* position of an array, we need to navigate to that position and shift all element
that have a index of more than *i + 1* back.

For a linked list, we have to navigate to the *i - 1 th* node and change the pointer to reference our new node. 
We can then reference the rest of the list by updating the pointer in our newly added node.

In both cases, a *O(n)* time complexity is incurred.

**Generally, inserting a element into a linked list is faster than an array.**

### Memory Requirements & Flexibility
As a contiguous block of memory must be allocated for an array, its size is fixed.
If we declare a array of size *n*, but only allocate *x* elements, where *x < n*, 
we will be left with unused, wasted memory.

This waste does not occur with linked lists, which only take up memory as new elements are added.
However, additional space will be used to store the pointers. 

As the declared size of our array is static (done at compile time), we are also given less flexibility if 
we end up needing to store more elements at run time.

However, linked list gives us the option of adding new nodes at run time based on our requirements, 
allowing us to allocate memory to store items dynamically, giving us more flexibility.

**Generally, linked list is more memory efficient and flexible than arrays.**

### Conclusion
You should aim to use linked list in unpredictable scenarios or if you require constant time insertions to the list.

However, arrays would be preferred if you already know the amount of elements you need to store ahead of time, 
preventing memory wastage. It would also be preferred if you are conducting a lot of look up operations.

## Linked List Variants
The lookup time within a linked list is its biggest issue.
However, there are variants of linked lists designed to speed up lookup time.

### Skip list

![Skip List](https://upload.wikimedia.org/wikipedia/commons/thumb/8/86/Skip_list.svg/800px-Skip_list.svg.png)
*Source: Brilliant*

This is a variant of a linked list with additional pointer paths that does not lead to the next node 
but allow you to skip through nodes as you wish. 
If we know which nodes each pointer path stops at, we would be able to conduct faster lookup.

This could also be ideal in situations where we want to store a large amount
of data which we do not need to access regularly that we are not willing to delete.

### Unrolled Linked Lists

![Unrolled Linked List](https://ds055uzetaobb.cloudfront.net/brioche/uploads/5LFjevVjNy-ull-new-page.png?width=2400)
*Source: Brilliant*

Unrolled linked lists stores multiple consecutive elements into a single bucket node. 
This allows us to avoid constantly travelling down nodes to get to the element we need.