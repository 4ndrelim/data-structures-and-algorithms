package src.algorithms.sorting.quickSort.hoares;

/**
 * Here, we are implementing Hoares's QuickSort where we sort the array in increasing (or more precisely,
 * non-decreasing) order. We will follow lecture implementation here, which differs slightly from the usual
 * implementation of Hoare's. See more under notes in README.
 *
 * Implementation Invariant:
 * The pivot is in the correct position, with elements to its left being <= it, and elements to its right being > it.
 * (We edited the psuedocode a bit to keep the duplicates to the left of the pivot.)
 *
 * This implementation picks the element at the index 0 as the pivot.
 */

public class QuickSort {
    /**
     * Sorts the given array in-place in non-decreasing order.
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
    public static void quickSort(int[] arr, int start, int end) {
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
     *
     * Given a sub-array of length m, the time complexity of the partition subroutine is O(m) as we need to iterate
     * through every element in the sub-array once.
     *
     * @param arr   the array containing the sub-array to be partitioned.
     * @param start the starting index (inclusive) of the sub-array to be partitioned.
     * @param end   the ending index (inclusive) of the sub-array to be partitioned.
     * @return the index of the pivot element in its correct position after partitioning.
     */
    private static int partition(int[] arr, int start, int end) {
        int pivot = arr[start];
        int low = start + 1;
        int high = end;

        while (low <= high) {
            while (low <= high && arr[low] <= pivot) {// we use <= as opposed to < to pack duplicates to the left side
                                                     // of the pivot
                low++;
            }

            while (low <= high && arr[high] > pivot) {
                high--;
            }

            if (low < high) {
                swap(arr, low, high);
            }
        }
        swap(arr, start, low - 1);

        return low - 1;
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

}
