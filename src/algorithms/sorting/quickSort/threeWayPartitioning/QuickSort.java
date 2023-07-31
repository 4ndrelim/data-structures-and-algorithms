package src.algorithms.sorting.quickSort.threeWayPartitioning;

/**
 * Here, we are implementing QuickSort with three-way partitioning where we sort the array in increasing (or more
 * precisely, non-decreasing) order.
 *
 * Three-way partitioning is used in QuickSort to tackle the scenario where there are many duplicate elements in the
 * array being sorted.
 *
 * The idea behind three-way partitioning is to divide the array into three sections: elements less than the pivot,
 * elements equal to the pivot, and elements greater than the pivot. By doing so, we can avoid unnecessary comparisons
 * and swaps with duplicate elements, making the sorting process more efficient.
 *
 * Implementation Invariant:
 * The pivot and any element numerically equal to the pivot will be in the correct positions in the array. Elements
 * to their left are < them and elements to their right are > than them.
 *
 * Complexity Analysis:
 * Time:
 * - Worst case (poor choice of pivot): O(n^2)
 * - Average case: O(nlogn)
 * - Best case: O(nlogn)
 *
 * By isolating the elements equal to the pivot into their correct positions during the partitioning step, three-way
 * partitioning efficiently handles duplicates, preventing the presence of many duplicates in the array from causing
 * the time complexity of QuickSort to degrade to O(n^2).
 *
 * In the worst case where the pivot selected is consistently the smallest or biggest element in the array, the
 * partitioning of the array around the pivot will be extremely unbalanced, leading to a recurrence relation of:
 * T(n) = T(n-1) + O(n) => O(n^2). However, the likelihood of this happening is extremely low since pivot selection is
 * randomised.
 *
 * Space:
 * - O(1) since sorting is done in-place
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
    public static void quickSort(int[] arr, int start, int end) {
        if (start < end) {
            int[] newIdx = partition(arr, start, end);
            if (newIdx != null) {
                quickSort(arr, start, newIdx[0]);
                quickSort(arr, newIdx[1], end);
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
     * @return An integer array containing the indices that represent the boundaries
     * of the three partitions: [ending idx of the < portion of the array, starting idx of the > portion of the array]
     * if length of arr > 1, else null
     */
    private static int[] partition(int[] arr, int start, int end) {
        // ___<___ ___=___ ___IP___ ___>___
        //         ^     ^ ^      ^

        if (arr.length > 1) {
            int pivotIdx = random(start, end); // pick a pivot
            int pivot = arr[pivotIdx];

            swap(arr, pivotIdx, start); // swap the pivot to the start of the array, arr[start] is now our = portion
                                        // of the array

            int eqStart = start;
            int eqEnd = start;
            int ipStart = start + 1;
            int ipEnd = end;

            while (ipStart <= ipEnd) {
                int curr = arr[ipStart];
                // case 1: < pivot
                if (curr < pivot) {
                    swap(arr, ipStart, eqStart); // do a swap with eqStart

                    // increment eqStart, ipStart, and eqEnd
                    eqStart++;
                    ipStart++;
                    eqEnd++;
                    // case 2: = pivot
                } else if (curr == pivot) {
                    // simply increment eqEnd and ipStart
                    eqEnd++;
                    ipStart++;
                    // case 3: > pivot
                } else {
                    swap(arr, ipStart, ipEnd); // do a swap with ipEnd
                    // decrement ipEnd
                    ipEnd--;
                }
            }
            int[] result = {eqStart - 1, eqEnd}; //return
            return result;
        } else {
            return null;
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