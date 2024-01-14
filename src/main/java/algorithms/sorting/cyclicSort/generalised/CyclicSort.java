package algorithms.sorting.cyclicSort.generalised;
/**
 * Implementation of cyclic sort in the generalised case where the input can contain any integer and duplicates.
 */
public class CyclicSort {
    /**
     * Sorts the given array using CyclicSort. Generalised case where the input array can contain any integer, and
     * can contain duplicates.
     * See documentation in README for more details.
     *
     * @param arr the given array to be sorted. Can contain any integers and can contain duplicates.
     * @param n the size of the given array to be sorted.
     */
    public static void cyclicSort(int[] arr, int n) {
        for (int currIdx = 0; currIdx < n - 1; currIdx++) {
            int currElement;
            int rightfulPos;

            do {
                rightfulPos = currIdx; // initialization since elements before currIdx are correctly placed
                currElement = arr[currIdx];
                for (int i = currIdx + 1; i < n; i++) { // O(n) find rightfulPos for the currElement
                    if (arr[i] < currElement) {
                        rightfulPos++;
                    }
                }
                if (rightfulPos == currIdx) { // verified curr position is correct for curr element
                    break;
                }
                while (currElement == arr[rightfulPos]) { // duplicates found, so find next suitable position
                    rightfulPos++;
                }
                int tmp = currElement; // swap, put item in its rightful position
                arr[currIdx] = arr[rightfulPos];
                arr[rightfulPos] = tmp;
            } while (rightfulPos != currIdx);
        }
    }

    /**
     * Overloaded helper method that calls internal implementation.
     * @param arr the input array to be sorted.
     */
    public static void sort(int[] arr) {
        cyclicSort(arr, arr.length);
    }
}
