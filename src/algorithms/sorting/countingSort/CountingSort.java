package src.algorithms.sorting.countingSort;

/**
 * Stable implementation of Counting Sort.
 *
 * Brief Description:
 * Counting sort is a non-comparison based sorting algorithm and isn't bounded by the O(nlogn) lower-bound
 * of most sorting algorithms.
 * It first obtains the frequency map of all elements (ie counting the occurrence of every element),
 * and then computes the prefix sum for the map. This prefix map tells us which position an element should be inserted.
 * Ultimately, each group of elements will be placed together, and the groups in succession in the sorted output.
 *
 * Assumption for use:
 * To perform counting sort, the elements must first have total ordering and their rank must be known.
 *
 * Implementation Invariant:
 * At the end of the ith iteration, the ith element from the back will be placed in its rightful position.
 *
 * COMMON MISCONCEPTION: Counting sort does not require total ordering of elements since it is non-comparison based.
 * This is incorrect. It requires total ordering of elements to determine their relative positions in the sorted output.
 * In fact, in conventional implementation, the total ordering property is reflected by virtue of the structure
 * of the frequency map.
 *
 * Complexity Analysis:
 * Time: O(k+n)=O(max(k,n)) where k is the value of the largest element and n is the number of elements.
 * Space: O(k+n)=O(max(k,n))
 * Counting sort is most efficient if the range of input values do not exceed the number of input values.
 * Counting sort is NOT AN IN-PLACE algorithm. For one, it requires additional space to store freq map.
 *
 * Note: Below deals with integers but the idea is the same and can be generalised to other objects,
 * as long as what was discussed above remains true.
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
        // Find the max. value in arr
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
        // Sort the array by placing in output array
        int[] sorted = new int[n];
        for (int i = arr.length-1; i >= 0; i--) { // Notice we start from the back to maintain stable property
            int num = arr[i];
            sorted[freq[num]-1] = num;
            freq[num]--;
        }
        return sorted;
    }
}