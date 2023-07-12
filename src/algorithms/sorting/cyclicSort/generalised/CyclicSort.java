package src.algorithms.sorting.cyclicSort.generalised;
/**
 * Implementation of cyclic sort
 * Time: O(n^2) where n is the number of elements
 * Space (auxiliary) : O(1)
 */
public class CyclicSort {
    public static void cycleSort(int[] arr, int n) {
        for (int currIdx = 0; currIdx < n - 1; currIdx++) {
            int currElement = arr[currIdx];
            int rightfulPos = currIdx;
            // find the rightful position of the curr element
            for (int i = currIdx + 1; i < n; i++) {
                if (arr[i] < currElement) {
                    rightfulPos++;
                }
            }
            // if currElement is already in its right position
            if (rightfulPos == currIdx) {
                continue;
            }
            // ignore duplicates by incrementing rightful position
            while (currElement == arr[rightfulPos]) {
                rightfulPos++;
            }
            // put item in its rightful position
            int tmp = currElement;
            arr[currIdx] = arr[rightfulPos];
            arr[rightfulPos] = tmp;

            // we know the element at rightfulPos is in its right position
            // so we shall continue this process until currIdx = rightfulPos
            // this tells us we can increment currIdx (in this case, go to the next iter)
            while (currIdx != rightfulPos) {
                currElement = arr[currIdx];
                rightfulPos = currIdx;
                for (int i = currIdx + 1; i < n; i++) {
                    if (arr[i] < currElement) {
                        rightfulPos++;
                    }
                }
                if (currIdx == rightfulPos) {
                    break;
                }
                while (currElement == arr[rightfulPos]) {
                    rightfulPos++;
                }
                tmp = currElement;
                arr[currIdx] = arr[rightfulPos];
                arr[rightfulPos] = tmp;
            }

        }
    }

    public static void cycleSort(int[] arr) {
        cycleSort(arr, arr.length);
    }
}
