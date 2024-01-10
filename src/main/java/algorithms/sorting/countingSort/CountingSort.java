package algorithms.sorting.countingSort;

/**
 * <p></p> Stable implementation of Counting Sort.
 * This non-comparison-based sorting algorithm is efficient for sorting integers with a small range.
 * For detailed explanation and complexity analysis, see README.
 */
public class CountingSort {
    /**
     * Sorts the given array.
     * @param arr array to be sorted.
     * @return new array that is sorted.
     */
    public static int[] sort(int[] arr) {
        int k = 0;
        int n = arr.length;
        // Find the max. value in arr. This tells us the size of the freq map array.
        for (int i = 0; i < n; i++) {
            k = Math.max(k, arr[i]);
        }
        // Obtain frequency map
        int[] freq = new int[k+1];
        for (int num : arr) {
            freq[num]++;
        }
        // Obtain prefix sum of freq map
        for (int i = 1; i < k+1; i++) {
            freq[i] += freq[i-1];
        }
        // Sort the array by placing element in the output array
        int[] sorted = new int[n];
        for (int i = arr.length-1; i >= 0; i--) { // we start from the back to maintain stable property
            int num = arr[i];
            sorted[freq[num]-1] = num; // freq[num]-1 because 0-indexed
            freq[num]--;
        }
        return sorted;
    }
}