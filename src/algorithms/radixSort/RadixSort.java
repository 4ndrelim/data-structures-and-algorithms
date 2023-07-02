package src.algorithms.radixSort;

/**
 * Implementation of Radix sort.
 * O((b/r)*(N + 2^r)) 
 * where N is the number of integers, 
 * b is the total number of bits (32 bits for int), 
 * and r is the number of bits for each segment.
 * Space: O(N) auxiliary space.
 */
public class RadixSort {
    private static final int NUM_BITS = 8;
    private static final int NUM_SEGMENTS = 4;

    private static int getSegmentMasked(int num, int segment) {
        int mask = ((1 << NUM_BITS) - 1) << (segment * NUM_BITS);
        return (num & mask) >> (segment * NUM_BITS);
    }

    private static void radixSort(int[] arr, int[] sorted) {
        // sort the N numbers by segments, starting from left-most segment
        for (int i = 0; i < NUM_SEGMENTS; i++) { 
            int[] freqMap = new int[1 << NUM_BITS]; // at most this number of elements

            // count each element
            for (int num : arr) {
                freqMap[getSegmentMasked(num, i)]++;
            }
            // get prefix sum
            for (int j = 1; j < freqMap.length; j++) {
                freqMap[j] += freqMap[j-1];
            }
            // place each number in its correct sorted position up until the given segment
            for (int k = arr.length-1; k >= 0; k--) {
                int curr = arr[k];
                int id = getSegmentMasked(curr, i);
                sorted[freqMap[id] - 1] = curr;
                freqMap[id]--;
            }
            // we do a swap so that our results above for this segment is saved and passed as input to the next segment
            int[] tmp = arr;
            arr = sorted;
            sorted = tmp;
        }
        sorted = arr;
    }

    public static void radixSort(int[] arr) {
        int[] sorted = new int[arr.length];
        radixSort(arr, sorted);
        arr = sorted; // swap back lol
    }
}
