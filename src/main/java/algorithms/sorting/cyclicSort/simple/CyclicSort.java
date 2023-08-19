package algorithms.sorting.cyclicSort.simple;

/**
 * Implementation of cyclic sort in the simple case where the n elements of the given array are contiguous,
 * but not in sorted order. Below illustrates the idea using integers from 0 to n-1. <p></p>
 *
 * Analysis: <br>
 *          Time:
 *              Best: O(n) since the array has to be traversed <br>
 *              Worst: O(n) since each element is at most seen twice <br>
 *              Average: O(n), it's bounded by the above two <br>
 *          Space: O(1) auxiliary space, this is an in-place algorithm <p></p>
 *
 * Invariant: <br>
 *          At the end of the ith iteration, the ith element is correctly positioned
 *          (smallest or largest depending on implementation). <br>
 *
 *          This is exactly the same invariant as Selection sort. But the algorithm for cyclic sort actually does a bit
 *          more. In the process of trying to find the correct element for the ith position, whatever elements that were
 *          encountered will be correctly placed in their positions as well. This leads to the O(n) complexity as
 *          opposed to Selection sort's O(n^2). <p></p>
 *
 * NOTE: <br>
 *      In this implementation, the algorithm is not comparison-based! (unlike the general case) <br>
 *      It makes use of the known inherent ordering of the numbers. <br>
 *
 *      It may seem quite trivial to sort integers from 0 to n-1 when one could simply generate such a sequence. But
 *      this algorithm proves its use in cases where the integers to be sorted are keys to associated values and sorting
 *      to be done in O(1) auxiliary space.
 */
 public class CyclicSort {
    /**
     * Sorts the given array.
     * @param arr the array to be sorted.
     */
    public static void sort(int[] arr) {
        int curr = 0; // can be easily modified to work on n numbers starting at some other number
        while (curr < arr.length) { // iterate until the end of the array
            int ele = arr[curr]; // encounter an element that may not be in its correct position
            assert ele >= 0 && ele < arr.length : "Input array should only have integers from 0 to n-1 (inclusive)";
            if (ele != curr) { // verified that it is indeed not the correct element to be placed at this curr position
                int tmp = arr[ele]; // go to the correct position of ele
                arr[ele] = ele; // do a swap
                arr[curr] = tmp; // note that curr isn't incremented because we haven't yet placed the correct element
            } else {
                curr += 1; // we found the correct element to be placed at pos curr, which in this example, is itself
            }
        }
    }
 }
