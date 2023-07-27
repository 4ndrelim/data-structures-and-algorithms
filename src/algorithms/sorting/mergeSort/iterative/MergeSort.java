package src.algorithms.sorting.mergeSort.iterative;

/** Here, we are implementing MergeSort where we sort the array in increasing (or more precisely, non-decreasing)
 * order iteratively.
 *
 * Brief Description:
 * The iterative implementation of MergeSort takes a bottom-up approach, where the sorting process starts by merging
 * intervals of size 1. Intervals of size 1 are trivially in sorted order. The algorithm then proceeds to merge
 * adjacent sorted intervals, doubling the interval size with each merge step, until the entire array is fully sorted.
 *
 * Implementation Invariant:
 * At each iteration of the merging process, the main array is divided into sub-arrays of a certain interval
 * size (<interval>). Each of these sub-arrays is sorted within itself. The last sub-array may not be of size
 * <interval> but it is still sorted since its size is necessarily less than <interval>.
 *
 * Complexity Analysis:
 * Time:
 * - Worst case: O(nlogn)
 * - Average case: O(nlogn)
 * - Best case: O(nlogn)
 *
 * Given two sorted arrays of size p and q, we need O(p + q) time to merge the two arrays into one sorted array, since
 * we have to iterate through every element in both arrays once.
 *
 * At the 1st level of the merge process, our merging subroutine involves n sub-arrays of size 1, taking O(n) time.
 * At the 2nd level of the merge process, our merging subroutine involves (n/2) sub-arrays of size 2, taking O(n) time.
 * At the kth level of the merge process, our merging subroutine involves (n/(2^(k-1))) sub-arrays of size (2^(k-1)),
 * taking O(n) time.
 *
 * Since <interval> doubles at every iteration of the merge process, there are logn such levels. Every level takes
 * O(n) time, hence overall time complexity is n * logn = O(nlogn)
 *
 * Regardless of how sorted the input array is, MergeSort carries out the partitioning and merging process, so the
 * time complexity of MergeSort is O(nlogn) for all cases.
 *
 * Space:
 * - O(n) since we require a temporary array to temporarily store the merged elements in sorted order
 */

public class MergeSort {

    /**
     * Sorts the given array in non-decreasing order.
     *
     * @param arr The given array to be sorted.
     */
    public static void sort(int[] arr) {
        int interval = 1;
        int n = arr.length;
        int[] temp = new int[n];

        while (interval < n) {
            for (int i = 0; i < n - interval; i += 2 * interval) {
                int end = Math.min(i + 2 * interval - 1, n - 1);
                int mid = i + interval - 1;
                merge(arr, i, mid, end, temp);
            }
            interval *= 2;
        }
    }

    /**
     * Merges two sorted sub-arrays within the given array. The two sub-arrays are arr[start, mid] and
     * arr[mid + 1, end]. Upon completion of this function, arr[start, end] will be in sorted order.
     *
     * @param arr The array containing the sub-arrays to be merged.
     * @param start The starting index of the first sub-array to be merged.
     * @param mid The ending index (inclusive) of the first sub-array to be merged. In the iterative implementation,
     *            the mid parameter is required in the merge function to determine the splitting point between the
     *            sub-arrays to be merged.
     * @param end The ending index (inclusive) of the second sub-array to be merged.
     * @param temp A temporary array used for merging intermediate results.
     */
    private static void merge(int[] arr, int start, int mid, int end, int[] temp) {
        int i = start;
        int j = mid + 1;
        int pointer = start;

        // Merge the two sorted sub-arrays into the temp array
        while (i <= mid && j <= end) {
            if (arr[i] <= arr[j]) {
                //we use <= here to maintain stability of MergeSort. If arr[i] == arr[j], the algorithm prefers the
                //one from the left sub-array (arr[i]). This decision preserves the relative order of equal elements.

                //if we change this to arr[i] >= arr[j], we can sort the array in non-increasing order.

                temp[pointer] = arr[i];
                i++;
            } else {
                temp[pointer] = arr[j];
                j++;
            }
            pointer++;
        }

        // Copy any remaining elements from the left sub-array
        while (i <= mid) {
            temp[pointer] = arr[i];
            i++;
            pointer++;
        }

        // Copy any remaining elements from the right sub-array
        while (j <= end) {
            temp[pointer] = arr[j];
            j++;
            pointer++;
        }

        // Copy the merged elements back to the original array
        for (int k = start; k <= end; k++) {
            arr[k] = temp[k];
        }
    }

}
