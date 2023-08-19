package src.algorithms.sorting.bubbleSort;

/** Here, we are implementing BubbleSort where we sort the array in increasing (or more precisely, non-decreasing)
 * order.
 *
 * Brief Description and Implementation Invariant:
 * BubbleSort relies on the outer loop variant that after the kth iteration, the biggest k items are correctly sorted
 * at the final k positions of the array. The job of the kth iteration of the outer loop is to bubble the kth
 * largest element to the kth position of the array from the right (i.e. its correct position). This is done through
 * repeatedly comparing adjacent elements and swapping them if they are in the wrong order.
 *
 * At the end of the (n-1)th iteration of the outer loop, where n is the length of the array, the largest (n-1)
 * elements are correctly sorted at the final (n-1) positions of the array, leaving the last 1 element placed correctly
 * in the first position of the array. Therefore, (n-1) iterations of the outer loop is sufficient.
 *
 * At the kth iteration of the outer loop, we only require (n-k) adjacent comparisons to get the kth largest
 * element to its correct position.
 */
public class BubbleSort {
    /**
     * Sorts the given array in-place in non-decreasing order.
     * @param arr array to be sorted.
     * @return the same array arr that is sorted.
     */
    public static int[] sort(int[] arr) {
        int n = arr.length;
        boolean swapped; // tracks of the presence of swaps within one iteration of the outer loop to
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
