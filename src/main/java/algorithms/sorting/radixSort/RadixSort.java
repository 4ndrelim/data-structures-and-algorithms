package algorithms.sorting.radixSort;

/**
 * This class implements a Radix Sort Algorithm.
 */
public class RadixSort {

    private static final int NUM_BITS = 8;
    private static final int NUM_SEGMENTS = 4;

    /**
     * Creates masking on the segment to obtain the value of the digit.
     *
     * @param num     The number.
     * @param segment The segment we are interested in.
     * @return The value of the digit in the number at the given segment.
     */
    private static int getSegmentMasked(int num, int segment) {
        // Bit masking here to extract each segment from the integer.
        int mask = ((1 << NUM_BITS) - 1) << (segment * NUM_BITS);
        return (num & mask) >> (segment * NUM_BITS);
    }

    /**
     * Radix sorts a given input array and updates the output array in-place.
     *
     * @param arr    original input array.
     * @param sorted output array.
     */
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
                freqMap[j] += freqMap[j - 1];
            }
            // place each number in its correct sorted position up until the given segment
            for (int k = arr.length - 1; k >= 0; k--) {
                int curr = arr[k];
                int id = getSegmentMasked(curr, i);
                sorted[freqMap[id] - 1] = curr;
                freqMap[id]--;
            }
            // We do a swap so that our results above for this segment is
            // saved and passed as input to the next segment.
            // By doing this we no longer need to create a new array
            // every time we shift to a new segment to sort.
            // We can constantly reuse the array, allowing us to only use O(n) space.
            int[] tmp = arr;
            arr = sorted;
            sorted = tmp;
        }
        sorted = arr;
    }

    /**
     * Calls radix sort inplace on a given array.
     *
     * @param arr The array to be sorted.
     */
    public static void radixSort(int[] arr) {
        int[] sorted = new int[arr.length];
        radixSort(arr, sorted);
        arr = sorted; // swap back lol
    }
}
