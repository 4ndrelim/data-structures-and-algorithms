package src.algorithms.sorting.countingSort;

/**
 * Stable implementation of Counting Sort. 
 * Below focuses on sorting integers but the idea is the same and can be generalised for other objects.
 * Time: O(k+n) where k is the value of the largest element and n is the number of elements.
 * Space: O(k)
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
        // find the max value in arr
        for (int i = 0; i < n; i++) {
            k = Math.max(k, arr[i]);
        }
        // count and track the freq of each element
        int[] freq = new int[k+1];
        for (int num : arr) {
            freq[num]++;
        }
        // get prefix sum of freq map
        for (int i = 1; i < k+1; i++) {
            freq[i] += freq[i-1];
        }
        // now sort the array by placing in output array
        int[] sorted = new int[n];
        for (int i = arr.length-1; i >= 0; i--) {
            int num = arr[i];
            sorted[freq[num]-1] = num;
            freq[num]--;
        }
        return sorted;
    }
}