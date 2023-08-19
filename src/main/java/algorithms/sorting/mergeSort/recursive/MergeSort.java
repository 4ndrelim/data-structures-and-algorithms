package algorithms.sorting.mergeSort.recursive;

/** Here, we are implementing MergeSort where we sort the array in increasing (or more precisely, non-decreasing)
 * order recursively.
 *
 * Brief Description:
 * MergeSort is a divide-and-conquer sorting algorithm. The recursive implementation takes a top-down approach by
 * recursively dividing the array into two halves, sorting each half separately, and then merging the sorted halves
 * to produce the final sorted output.
 *
 * Implementation Invariant (for the merging subroutine):
 * The sub-array temp[start, (k-1)] consists of the (𝑘−start) smallest elements of arr[start, mid] and
 * arr[mid + 1, end], in sorted order.
 *
 * Complexity Analysis:
 * Time:
 * - Worst case: O(nlogn)
 * - Average case: O(nlogn)
 * - Best case: O(nlogn)
 * Merging two sorted sub-arrays of size (n/2) requires O(n) time as we need to iterate through every element in both
 * sub-arrays in order to merge the two sorted sub-arrays into one sorted array.
 *
 * Recursion expression: T(n) = 2T(n/2) + O(n) => O(nlogn)
 *
 * Regardless of how sorted the input array is, MergeSort carries out the same divide-and-conquer strategy, so the
 * time complexity of MergeSort is O(nlogn) for all cases.
 *
 * Space:
 * - O(n) since we require a temporary array to temporarily store the merged elements in sorted order
 */

public class MergeSort {

    /**
     * Sorts the sub-array arr[start, end] using the MergeSort algorithm.
     *
     * @param arr The given array to be sorted.
     * @param start The starting index of the sub-array to be sorted.
     * @param end The ending index (inclusive) of the sub-array to be sorted.
     * @param temp A temporary array used for merging.
     */
    private static void mergeSort(int[] arr, int start, int end, int[] temp) {
        if (start >= end) {
            return;
        }
        int mid = (start + end) / 2;
        mergeSort(arr, start, mid, temp);
        mergeSort(arr, mid + 1, end, temp);
        merge(arr, start, end, temp);
    }

    /**
     * Merges two sorted sub-arrays within the given array. The two sub-arrays are arr[start, mid] and
     * arr[mid + 1, end]. Upon completion of this function, arr[start, end] will be in sorted order.
     *
     * @param arr The array containing the sub-arrays to be merged.
     * @param start The starting index of the first sub-array to be merged.
     * @param end The ending index (inclusive) of the second sub-array to be merged.
     * @param temp A temporary array used for merging intermediate results.
     */
    private static void merge(int[] arr, int start, int end, int[] temp) {
        int mid = (start + end) / 2;
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

    /**
     * Sorts the given array in non-decreasing order.
     *
     * @param arr The given array to be sorted
     */
    public static void sort(int[] arr) {
        int n = arr.length;
        int[] temp = new int[n];
        mergeSort(arr, 0, n-1, temp);
    }

}
