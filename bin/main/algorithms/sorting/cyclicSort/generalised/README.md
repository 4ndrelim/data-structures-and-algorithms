# Generalized Case

## More Details
Implementation of cyclic sort in the generalised case where the input can contain any integer and duplicates.

This is a comparison-based algorithm. In short, for the element encountered at the ith position of the original array, 
the algorithm does a O(n) traversal to search for its rightful position and does a swap. It repeats this until a swap 
placing the correct element at the ith position, before moving onto the next (i+1)th.

### Illustration
Result:  6  10  6  5  7  4  2  1  <br> &nbsp;
  Read:  ^ 6 should be placed at index 4, so we do a swap with 6 and 7,  <br><br>
Result:  7  10  6  5  6  4  2  1  <br> &nbsp;
  Read:  ^ 7 should be placed at index 6, so we do a swap. Note that index 5 should be taken up by dup 6 <br><br>
Result:  2  10  6  5  6  4  7  1  <br> &nbsp;
  Read:  ^ 2 should be placed at index 1, so swap. <br><br>
Result:  10  2  6  5  6  4  7  1  <br> &nbsp;
  Read:  ^ 10 is the largest, should be placed at last index, so swap. <br><br>
Result:   1  2  6  5  6  4  7  10  <br> &nbsp;
  Read:   ^ Correctly placed, so move on. Same for 2.  <br> &nbsp;
  Read: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                ^ 6 should be placed at index 4. But index 4 already has a 6. So place at index 5 and so on.  <br><br>
Result:   1  2  4  5  6  6  7  10  <br> &nbsp;
  Read: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                   ^  ^  ^  ^  ^ Continue with O(n) verification of correct position at each iteration


## Complexity Analysis
**Time**:
- Best: O(n^2) even if the ith element is encountered in the ith position, a O(n) traversal validation check is needed
- Worst: O(n^2) since we need O(n) time to find / validate the correct position of an element and 
the total number of O(n) traversals is bounded by O(n).
- Average: O(n^2), it's bounded by the above two

**Space**: O(1) auxiliary space, this is an in-place algorithm
