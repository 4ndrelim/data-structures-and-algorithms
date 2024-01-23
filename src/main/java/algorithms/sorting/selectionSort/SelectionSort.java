package algorithms.sorting.selectionSort;

/**
 * Here, we are implementing SelectionSort where we sort the array in increasing (or more precisely, non-decreasing)
 * order.
 */

public class SelectionSort {
    /**
     * Sorts the given array in-place in non-decreasing order.
     *
     * @param arr array to be sorted.
     * @return the same array arr that is sorted.
     */
    public static int[] sort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) { //loop which supports the invariant
            int minElemIdx = minElemIdx(i, n, arr);
            int temp = arr[i];
            arr[i] = arr[minElemIdx];
            arr[minElemIdx] = temp;
        }
        return arr;
    }

    /**
     * Finds the index of the minimum element within the specified range of the array.
     * The range is from 'start' (inclusive) to 'end' (exclusive).
     *
     * @param start the starting index (inclusive) of the range to be considered.
     * @param end   the ending index (exclusive) of the range to be considered.
     * @param arr   the array to be sorted.
     * @return the index of the minimum element within the range.
     *     <p>
     *     We can easily modify this method to find maxElemIdx instead to sort the array in non-increasing order.
     */
    private static int minElemIdx(int start, int end, int[] arr) {
        int min = Integer.MAX_VALUE;
        int idx = -1;
        for (int i = start; i < end; i++) {
            if (arr[i] < min) {
                min = arr[i];
                idx = i;
            }
        }
        return idx;
    }
}
