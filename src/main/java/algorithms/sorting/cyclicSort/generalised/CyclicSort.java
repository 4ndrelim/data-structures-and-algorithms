package src.algorithms.sorting.cyclicSort.generalised;
/**
 * Implementation of cyclic sort in the generalised case where the input can contain any integer and even duplicates.
 * <p></p>
 *
 * Analysis: <br>
 *          Time:
 *              Best: O(n^2) even though no swaps, array still needs to be traversed in O(n) at each iteration <br>
 *              Worst: O(n^2) since we need O(n) time to find the correct position of an element at each iteration <br>
 *              Average: O(n^2)
 *          Space: O(1) auxiliary space, this is an in-place algorithm <p></p>
 *
 * Invariant: <br>
 *          At the end of the ith iteration, the ith element is correctly positioned
 *          (smallest or largest depending on implementation). <br>
 *
 *          This is exactly the same invariant as Selection sort. But the algorithm for cyclic sort actually does a bit
 *          more. In the process of trying to find the correct element for the ith position, whatever elements that were
 *          encountered will be correctly placed in their positions as well. Though, unlike the simple case where the
 *          ordering of the elements are known between one another, the time complexity here remains at O(n^2) because
 *          cyclic sort will still have to iterate over all the elements at each iteration to verify or find the correct
 *          position of the element. <p></p>
 *
 * Illustration: <br>
 *     Result:  6  10  6  5  7  4  2  1
 *       Read:  ^ 6 should be placed at index 4, so we do a swap,
 *     Result:  7  10  6  5  6  4  2  1
 *       Read:  ^ 7 should be placed at index 6, so we do a swap. Note that index 5 should be taken up by dup 6
 *     Result:  2  10  6  5  6  4  7  1
 *       Read:  ^ 2 should be placed at index 1, so swap.
 *     Result:  10  2  6  5  6  4  7  1
 *       Read:  ^ 10 is the largest, should be placed at last index, so swap.
 *     Result:   1  2  6  5  6  4  7  10
 *       Read:   ^ Correctly placed, so move on. Same for 2.
 *       Read:         ^ should be placed at index 4. But index 4 already has a 6. So place at index 5 and so on.
 *     Result:   1  2  4  5  6  6  7  10
 *       Read:            ^  ^  ^  ^  ^ Continue with O(n) verification of correct position at each iteration
 *
 */
public class CyclicSort {
    public static void cycleSort(int[] arr, int n) {
        for (int currIdx = 0; currIdx < n - 1; currIdx++) {
            int currElement = arr[currIdx];
            int rightfulPos = currIdx;
            for (int i = currIdx + 1; i < n; i++) { // O(n), find the rightful position of the curr element
                if (arr[i] < currElement) {
                    rightfulPos++;
                }
            }
            if (rightfulPos == currIdx) { // verified currElement is already in its right position, go to next index
                continue;
            }

            while (currElement == arr[rightfulPos]) { // when attempting to place currElement at rightful pos,
                rightfulPos++; // found that currElement is a duplicate, so place duplicates at next immediate positions
            }

            int tmp = currElement; // swap, put item in its rightful position
            arr[currIdx] = arr[rightfulPos];
            arr[rightfulPos] = tmp;

            /*
            The currElement has now been placed at its right position, with the element previously at that position
            at currIdx now. We shall continue this process until the element being swapped and placed at currIdx is the
            correct one. In other words, the rightfulPos of the element to be considered is the same as currIdx.
            Only then will we move on to the next index.
             */
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

    /*
    Overloaded helper method that calls internal implementation.
     */
    public static void sort(int[] arr) {
        cycleSort(arr, arr.length);
    }
}
