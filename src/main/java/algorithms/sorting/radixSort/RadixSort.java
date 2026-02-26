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
        // bit masking here to extract each segment from the integer.
        int mask = (1 << NUM_BITS) - 1;
        return (num >> (segment * NUM_BITS)) & mask;  // we do a right-shift on num to focus on the desired segment
    }

    /**
     * Radix sorts a given input array. Uses O(n) auxiliary space.
     *
     * @param arr original input array (will contain sorted result).
     * @param tmp temporary buffer array.
     */
    private static void radixSort(int[] arr, int[] tmp) {
        int[] input = arr;
        int[] output = tmp;

        // Code in the loop is essentially counting sort; sort the N numbers by segments, starting from right-most
        for (int i = 0; i < NUM_SEGMENTS; i++) {
            int[] freqMap = new int[1 << NUM_BITS]; // at most this number of elements

            // count each element
            for (int num : input) {
                freqMap[getSegmentMasked(num, i)]++;
            }
            // get prefix sum
            for (int j = 1; j < freqMap.length; j++) {
                freqMap[j] += freqMap[j - 1];
            }
            // place each number in its correct sorted position up until the given segment
            for (int k = input.length - 1; k >= 0; k--) {
                int curr = input[k];
                int id = getSegmentMasked(curr, i);
                output[freqMap[id] - 1] = curr;
                freqMap[id]--;
            }
            // swap input and output so results from this segment become input for next segment.
            // reuses arrays instead of allocating new ones, keeping space at O(n).
            int[] swap = input;
            input = output;
            output = swap;
        }

        // NOTE: this is necessary if NUM_SEGMENTS is odd, result is in tmp, so copy is required.
        // If NUM_SEGMENTS is even, result is already in arr, copy has no effect.
        System.arraycopy(input, 0, arr, 0, arr.length);
    }

    /**
     * Sorts the given array using radix sort. Time: O(n), Space: O(n).
     * Only works for non-negative integers.
     *
     * @param arr The array to be sorted (modified with sorted result).
     */
    public static void radixSort(int[] arr) {
        if (arr.length == 0) {
            return;
        }
        int[] tmp = new int[arr.length];
        radixSort(arr, tmp);
    }
}
