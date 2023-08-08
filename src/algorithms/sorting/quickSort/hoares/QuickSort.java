package src.algorithms.sorting.quickSort.hoares;

import java.lang.Math;

/**
 * Here, we are implementing Hoares's QuickSort where we sort the array in increasing (or more precisely,
 * non-decreasing) order.
 *
 * Brief Description:
 * Hoare's QuickSort operates by selecting a pivot element from the input array and rearranging the elements such that
 * all elements in A[start, returnIdx] are <= pivot and all elements in A[returnIdx + 1, end] are >= pivot, where
 * returnIdx is the index returned by the sub-routine partition.
 *
 * After partitioning, the algorithm recursively applies the same process to the left and right sub-arrays, effectively
 * sorting the entire array.
 *
 * The Hoare's partition scheme works by initializing two pointers that start at two ends. The two pointers move toward
 * each other until an inversion is found. This inversion happens when the left pointer is at an element >= pivot, and
 * the right pointer is at an element <= pivot. When an inversion is found, the two values are swapped and the pointers
 * continue moving towards each other.
 *
 * Implementation Invariant:
 * All elements in A[start, returnIdx] are <= pivot and all elements in A[returnIdx + 1, end] are >= pivot.
 *
 * Note:
 * - Hoare's partition scheme does not necessarily put the pivot in its correct position. It merely partitions the
 *   array into <= pivot and >= pivot portions.
 * - This is in contrast to Lomuto's partition scheme. Hoare's uses two pointers, while Lomuto's uses one. Hoare's
 *   partition scheme is generally more efficient as it requires less swaps. See more at
 *   https://www.geeksforgeeks.org/hoares-vs-lomuto-partition-scheme-quicksort/.
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
            quickSort(arr, start, pIdx);
            quickSort(arr, pIdx + 1, end);
        }
    }

    /**
     * Partitions the sub-array from index 'start' to index 'end' around a randomly selected pivot element.
     * After this sub-routine is complete, all elements in A[start, returnIdx] are <= pivot and all elements in
     * A[returnIdx + 1, end] are >= pivot.
     *
     * Given a sub-array of length m, the time complexity of the partition subroutine is O(m) as we need to iterate
     * through every element in the sub-array once.
     *
     * @param arr   the array containing the sub-array to be partitioned.
     * @param start the starting index (inclusive) of the sub-array to be partitioned.
     * @param end   the ending index (inclusive) of the sub-array to be partitioned.
     * @return the index at which the array is partitioned at
     */
    private static int partition(int[] arr, int start, int end) {
        int pIdx = random(start, end);
        int pivot = arr[pIdx];
        int i = start - 1;
        int j = end + 1;

        while (true) {
            do {
                i++;
            } while (arr[i] < pivot);

            do {
                j--;
            } while (arr[j] > pivot);

            if (i >= j) {
                return j;
            }
            swap(arr, i, j);
        }

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
