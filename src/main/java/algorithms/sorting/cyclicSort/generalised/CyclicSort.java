package algorithms.sorting.cyclicSort.generalised;
/**
 * Implementation of cyclic sort in the generalised case where the input can contain any integer and duplicates.
 */
public class CyclicSort {
    public static void cyclicSort(int[] arr, int n) {
        for (int currIdx = 0; currIdx < n-1; currIdx++) {
            int currElement = arr[currIdx];
            int rightfulPos;
            do {
                rightfulPos = currIdx; // initialization since elements before currIdx are correctly placed
                for (int i = currIdx + 1; i < n; i++) { // O(n) find rightfulPos for the currElement
                    if (arr[i] < currElement) {
                        rightfulPos++;
                    }
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

    /*
    Overloaded helper method that calls internal implementation.
     */
    public static void sort(int[] arr) {
        cyclicSort(arr, arr.length);
    }
}
