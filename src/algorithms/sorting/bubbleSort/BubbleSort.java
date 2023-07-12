package src.algorithms.sorting.bubbleSort;

/** Here, we are implementing BubbleSort where we sort the array in increasing (or more precisely, non-decreasing)
 * order.
 *
 * Brief Description and Implementation Invariant:
 * BubbleSort relies on the outer loop variant that after the ith iteration, the biggest i items are correctly sorted
 * at the final i positions of the array. The job of the ith iteration of the outer loop is to bubble the (i + 1)th
 * largest element (because of 0-based indexing) to the ith position of the array from the right (i.e. its correct
 * position). This is done through repeatedly comparing adjacent elements and swapping them if they are in the wrong
 * order.
 *
 * At the end of the (n-1)th iteration of the outer loop, where n is the length of the array, the (n-1) elements are
 * correctly sorted at the final (n-1) positions of the array, leaving the last 1 element placed correctly in the
 * first position of the array. Therefore, (n-1) iterations of the outer loop is sufficient.
 *
 * At the ith iteration of the outer loop, we only require (n-1-i) adjacent comparisons to get the (i + 1)th largest
 * element to its correct position.
 *
 * Complexity Analysis:
 * Time:
 * - Worst case (reverse sorted array): O(n^2)
 * - Average case: O(n^2)
 * - Best case (sorted array): O(n)
 * In the worst case, during each iteration of the outer loop, the number of adjacent comparisons is upper-bounded
 * by n. Since BubbleSort requires (n-1) iterations of the outer loop to sort the entire array, the total number
 * of comparisons performed can be upper-bounded by (n-1) * n ≈ n^2.
 *
 * This implementation of BubbleSort terminates the outer loop once there are no swaps within one iteration of the
 * outer loop. This improves the best case time complexity to O(n) for an already sorted array.
 *
 * Space:
 * - O(1) since sorting is done in-place
 */

public class BubbleSort {
    /**
     * Sorts the given array in-place in non-decreasing order.
     * @param arr array to be sorted.
     * @return the same array arr that is sorted.
     */
    public static int[] sort(int[] arr) {
        int n = arr.length;
        boolean swapped; //tracks of the presence of swaps within one iteration of the outer loop to
        // facilitate early termination
        for (int i = 0; i < n - 1; i++ ) { //outer loop which supports the invariant
            swapped = false;
            for (int j = 0; j < n - 1 - i; j++) { //inner loop that does the adjacent comparisons
                if (arr[j] > arr[j + 1]) { //if we changed this to <, we will sort the array in non-increasing order
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }
            if (!swapped) {
                break;
            }
        }
        return arr;
    }
}
