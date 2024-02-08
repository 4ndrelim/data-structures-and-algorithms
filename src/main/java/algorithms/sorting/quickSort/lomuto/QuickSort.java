package algorithms.sorting.quickSort.lomuto;

/**
 * Here, we are implementing Lomuto's QuickSort where we sort the array in increasing (or more precisely,
 * non-decreasing) order.
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
            quickSort(arr, start, pIdx - 1);
            quickSort(arr, pIdx + 1, end);
        }
    }

    /**
     * Partitions the sub-array from index 'start' to index 'end' around a randomly selected pivot element.
     * The elements less than or equal to the pivot are placed on the left side, and the elements greater than
     * the pivot are placed on the right side.
     * <p>
     * Given a sub-array of length m, the time complexity of the partition subroutine is O(m) as we need to iterate
     * through every element in the sub-array once.
     *
     * @param arr   the array containing the sub-array to be partitioned.
     * @param start the starting index (inclusive) of the sub-array to be partitioned.
     * @param end   the ending index (inclusive) of the sub-array to be partitioned.
     * @return the index of the pivot element in its correct position after partitioning.
     */
    private static int partition(int[] arr, int start, int end) {
        int pIdx = random(start, end);
        int pivot = arr[pIdx];

        swap(arr, start, pIdx); // swap the pivot to the start of the array

        int idx = start + 1; // interpret: at the end, all elements at indices less than this var is <= pivot

        for (int i = start + 1; i <= end; i++) {
            if (arr[i] <= pivot) {
                swap(arr, idx, i);
                idx++;
            }
        }

        swap(arr, idx - 1, start); // swap the pivot to its correct position

        return idx - 1;
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

}
