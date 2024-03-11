package algorithms.sorting.quickSort.threeWayPartitioning;

/**
 * Here, we are implementing Paranoid QuickSort with three-way partitioning where we sort the array in increasing (or
 * more precisely, non-decreasing) order.
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
            int[] newIdx = partition(arr, start, end);
            if (isGoodPivot(newIdx[0], newIdx[1], start, end)) {
                if (newIdx != null) {
                    quickSort(arr, start, newIdx[0]);
                    quickSort(arr, newIdx[1], end);
                }
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
     * @return An integer array containing the indices that represent the boundaries
     *     of the three partitions: [ending idx of the < portion of the array,
     *     starting idx of the > portion of the array]
     *     if length of arr > 1, else null
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
            int[] result = {eqStart - 1, eqEnd + 1};
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

    /**
     * Checks if the pivot is a good pivot for the QuickSort algorithm.
     * A good pivot helps avoid worst-case behavior in QuickSort.
     * <p>
     * Since we have three-way partitioning, we cannot use 1/10, 9/10 split of the array as our good pivot condition.
     * Note that our goal here is to ensure the sizes of the sub-arrays QuickSort is to recurse on are roughly the same
     * to ensure that our partitioning is not too imbalanced. The pivot condition we chose is: the larger sub-array can
     * be at most 9 times the size of the smaller sub-array.
     * <p>
     * If n < 10, such a pivot condition would be meaningless, therefore always return true. This would cause
     * the worst case recurrence relation to be T(n) = T(n-1) + O(n) => O(n^2) for small sub-arrays, but the overall
     * asymptotic time complexity of Paranoid QuickSort is still O(nlogn).
     * <p>
     * For an all-duplicates array, all pivots will be considered good pivots, therefore return true.
     *
     * @param firstPIdx  The ending index of the < portion of the sub-array.
     * @param secondPIdx The starting index of the > portion of the sub-array.
     * @param start      The starting index of the current sub-array.
     * @param end        The ending index of the current sub-array.
     * @return True if the given index is a good pivot, false otherwise.
     */
    private static boolean isGoodPivot(int firstPIdx, int secondPIdx, int start, int end) {
        int n = end - start + 1;
        if (firstPIdx >= start || secondPIdx <= end) {
            if (end - secondPIdx + 1 > 0) { // avoid division by zero
                double ratio = (double) (firstPIdx - start + 1) / (end - secondPIdx + 1);
                if (n >= 10) {
                    return ratio >= 1.0 / 9.0 && ratio <= 9;
                } else {
                    return true;
                }
            } else { // ratio is infinite, imbalanced partition => bad pivot
                return false;
            }
        } else { // all duplicates array
            return true;
        }
    }

}
