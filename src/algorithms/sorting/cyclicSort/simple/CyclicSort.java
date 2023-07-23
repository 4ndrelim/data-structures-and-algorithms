package src.algorithms.sorting.cyclicSort.simple;

/**
 * Implementation of cyclic sort in the simple case where the n elements of the given array are contiguous,
 * but not in sorted order. Below illustrates the idea using integers from 0 to n-1. <p></p>
 *
 * Analysis: <br>
 *          Time:
 *              Best: O(n) since the array has to be traversed <br>
 *              Worst: O(n) since each element is at most seen twice <br>
 *              Average: O(n), it's bounded by the above two <br>
 *          Space: O(1), this is an in-place algorithm <p></p>
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
 */
 public class CyclicSort {
    /**
     * Sorts the given array.
     * @param arr the array to be sorted.
     */
    public static void sort(int[] arr) {
        int start = 0; // can be easily modified to work on n numbers starting at some other number
        while (start < arr.length) { // iterate until the end of the array
            int curr = arr[start]; // encounter an element that may not be in its correct position
            assert curr >= 0 && curr < arr.length : "Input array should only have integers from 0 to n-1 (inclusive)";
            if (curr != start) { // verified that it is indeed not the correct element to be placed at this ith position
                int tmp = arr[curr]; // go to the correct position of curr
                arr[curr] = arr[start]; // do a swap
                arr[start] = tmp; // note that start isn't incremented because we haven't yet place the correct element
            } else {
                start += 1; // we found the correct element to be placed pos start, which in this example, is itself
            }
        }
    }
 }
