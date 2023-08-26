package src.algorithms.sorting.quickSort.lomuto;

import java.lang.Math;

/**
 * Here, we are implementing Lomuto's QuickSort where we sort the array in increasing (or more precisely,
 * non-decreasing) order.
 *
 * Basic Description:
 * QuickSort is a divide-and-conquer sorting algorithm. The basic idea behind Quicksort is to choose a pivot element,
 * places it in its correct position in the sorted array, and then recursively sorts the sub-arrays on either side of
 * the pivot. When we introduce randomization in pivot selection, every element has equal probability of being
 * selected as the pivot. This means the chance of an extreme element getting chosen as the pivot is decreased, so we
 * reduce the probability of encountering the worst-case scenario of imbalanced partitioning.
 *
 * Implementation Invariant:
 * The pivot is in the correct position, with elements to its left being <= it, and elements to its right being > it.
 *
 * We are implementing Lomuto's partition scheme here. This is opposed to Hoare's partition scheme, see more at
 * https://www.geeksforgeeks.org/hoares-vs-lomuto-partition-scheme-quicksort/.
 *
 * Complexity Analysis:
 * Time:
 * - Expected worst case (poor choice of pivot): O(n^2)
 * - Expected average case: O(nlogn)
 * - Expected best case (balanced pivot): O(nlogn)
 *
 * In the best case of a balanced pivot, the partitioning process divides the array in half, which leads to log n
 * levels of recursion. Given a sub-array of length m, the time complexity of the partition subroutine is O(m) as we
 * need to iterate through every element in the sub-array once.
 * Therefore, the recurrence relation is: T(n) = 2T(n/2) + O(n) => O(nlogn).
 *
 * Even in the average case where the chosen pivot partitions the array by a fraction, there will still be log n levels
 * of recursion. (e.g. T(n) = T(n/10) + T(9n/10) + O(n) => O(nlogn))
 *
 * However, if there are many duplicates in the array, e.g. {1, 1, 1, 1}, the 1st pivot will be placed in the 3rd idx,
 * and 2nd pivot in 2nd idx, 3rd pivot in the 1st idx and 4th pivot in the 0th idx. As we observe, the presence of many
 * duplicates in the array leads to extremely unbalanced partitioning, leading to a O(n^2) time complexity.
 *
 * Space:
 * - O(logn) as the partitioning is done in-place so each partitioning takes O(1) space but depth of QuickSort
 *   recursion is logn, therefore logn * O(1) = O(logn)
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
        int pIdx = random(start, end);
        int pivot = arr[pIdx];

        swap(arr, start, pIdx); // swap the pivot to the start of the array

        int less = start + 1;

        for (int i = start + 1; i <= end; i++) {
            if (arr[i] <= pivot) {
                swap(arr, less, i);
                less++;
            }
        }

        swap(arr, less - 1, start); // swap the pivot to its correct position

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

}
