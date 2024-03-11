package algorithms.sorting.quickSort.paranoid;

/**
 * Here, we are implementing Paranoid QuickSort where we sort the array in increasing (or more precisely,
 * non-decreasing) order.
 *
 * We are implementing this with the Lomuto's partitioning scheme, with an additional check to guarantee a good pivot.
 * You could also implement this with the Hoare's partitioning scheme instead.
 */

public class QuickSort {
    /**
     * Sorts the given array in-place in non-decreasing order.
     *
     * @param arr array to be sorted.
     */
    public static void sort(int[] arr) {
        int n = arr.length;
        quickSort(arr, 0, n - 1);
    }

    /**
     * Recursively sorts the sub-array from index 'start' to index 'end' in non-decreasing order
     * using the QuickSort algorithm.
     *
     * @param arr   the array containing the sub-array to be sorted.
     * @param start the starting index (inclusive) of the sub-array to be sorted.
     * @param end   the ending index (inclusive) of the sub-array to be sorted.
     */
    private static void quickSort(int[] arr, int start, int end) {
        if (start < end) {
            int pIdx = partition(arr, start, end);
            if (isGoodPivot(pIdx, start, end)) { //check to guarantee good pivot
                quickSort(arr, start, pIdx - 1);
                quickSort(arr, pIdx + 1, end);
            } else {
                quickSort(arr, start, end);
            }
        }
    }

    /**
     * Partitions the sub-array from index 'start' to index 'end' around a randomly selected pivot element.
     * The elements less than or equal to the pivot are placed on the left side, and the elements greater than
     * the pivot are placed on the right side.
     *
     * @param arr   the array containing the sub-array to be partitioned.
     * @param start the starting index (inclusive) of the sub-array to be partitioned.
     * @param end   the ending index (inclusive) of the sub-array to be partitioned.
     * @return the index of the pivot element in its correct position after partitioning.
     */
    private static int partition(int[] arr, int start, int end) {
        int pIdx = random(start, end);
        int pivot = arr[pIdx];

        swap(arr, start, pIdx);

        int less = start + 1;

        for (int i = start + 1; i <= end; i++) {
            if (arr[i] <= pivot) {
                swap(arr, less, i);
                less++;
            }
        }

        swap(arr, less - 1, start);

        return less - 1;
    }

    /**
     * Swaps the elements at indices 'i' and 'j' in the given array.
     *
     * @param arr the array in which the elements should be swapped.
     * @param i   the index of the first element to be swapped.
     * @param j   the index of the second element to be swapped.
     */
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * Generates a random integer within the range [start, end].
     *
     * @param start the lower bound of the random integer (inclusive).
     * @param end   the upper bound of the random integer (inclusive).
     * @return a random integer within the specified range.
     */
    private static int random(int start, int end) {
        return (int) (Math.random() * (end - start + 1)) + start;
    }

    /**
     * Checks if the given pivot index is a good pivot for the QuickSort algorithm.
     * A good pivot helps avoid worst-case behavior in QuickSort.
     * <p>
     * For arrays of length greater than or equal to 10, a good pivot leaves at least 1/10th of the array on each side.
     * <p>
     * If n < 10, such a pivot condition would be meaningless, therefore always return true. This would cause
     * the worst case recurrence relation to be T(n) = T(n-1) + O(n) => O(n^2) for small sub-arrays, but the overall
     * asymptotic time complexity of Paranoid QuickSort is still O(nlogn).
     *
     * @param pIdx  The index to be checked for being a good pivot.
     * @param start The starting index of the current sub-array.
     * @param end   The ending index of the current sub-array.
     * @return True if the given index is a good pivot, false otherwise.
     */
    private static boolean isGoodPivot(int pIdx, int start, int end) {
        double n = end - start + 1; // express n as a double so n/10 will be calculated as a double
        if (n >= 10) {
            return pIdx - start >= n / 10 && end - pIdx >= n / 10;
        } else {
            return true;
        }
    }

}
