package algorithms.sorting.insertionSort;

/**
 * Here, we are implementing InsertionSort where we sort the array in increasing (or more precisely, non-decreasing)
 * order.
 * <p>
 * Implementation Invariant:
 * The loop invariant is: at the end of kth iteration, the first (k+1) items in the array are in sorted order.
 * At the end of the (n-1)th iteration, all n items in the array will be in sorted order.
 * <p>
 * Note:
 * 1. the loop invariant here slightly differs from the lecture slides as we are using 0-based indexing
 * 2. Insertion into the sorted portion is done byb 'bubbling' elements as in bubble sort
 */

public class InsertionSort {
    /**
     * Sorts the given array in-place in non-decreasing order.
     *
     * @param arr array to be sorted.
     * @return the same array arr that is sorted.
     */
    public static int[] sort(int[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; i++) { //loop which supports the invariant
            arr = insert(arr, i, arr[i]);
        }
        return arr;
    }

    /**
     * Inserts val within the sorted portion of the array. The sorted portion of the array is arr[0, idx - 1].
     *
     * @param arr the array to be sorted (of length idx)
     * @param idx index of the element to be inserted into the sorted portion of the array
     * @param val value of the element to be inserted into the sorted portion of the array
     * @return returns arr with arr[0, idx] in sorted order
     */
    private static int[] insert(int[] arr, int idx, int val) {
        int pointer = idx - 1;

        while (pointer >= 0 && arr[pointer] > val) { //if we change this to arr[pointer] < val,
            // we can sort the array in non-increasing order
            arr[pointer + 1] = arr[pointer];
            pointer -= 1;
        }
        arr[pointer + 1] = val;

        return arr;
    }
}
