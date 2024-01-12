package algorithms.sorting.cyclicSort.simple;

/**
 * Implementation of cyclic sort in the simple case where the n elements of the given array are contiguous,
 * but not in sorted order. Below illustrates the idea using integers from 0 to n-1.
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
            if (ele != curr) { // verified that it is indeed not the correct element to be placed at curr position
                int tmp = arr[ele]; // go to the correct position of ele
                arr[ele] = ele; // do a swap
                arr[curr] = tmp; // note that curr isn't incremented because we haven't yet placed the correct element
            } else {
                curr += 1; // found the correct element to be placed at curr, which in this eg, is itself, so, increment
            }
        }
    }
 }
