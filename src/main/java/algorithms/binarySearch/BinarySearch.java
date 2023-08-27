package algorithms.binarySearch;

/** Here, we are implementing BinarySearch where we search an array for a target value at O(log n) time complexity.
 *
 * Assumptions:
 * The array is sorted in ascending order.
 * All elements in the array are unique. (to allow for easy testing)
 *
 * Brief Description and Implementation Invariant:
 * With the assumption that the array is sorted in ascending order, BinarySearch reduces the search range by half or
 * half + 1 (due to floor division) after every loop. This is done by reassigning the max (high) or min (low) of the
 * search range to the middle of the search range when the target value is smaller than or larger than the current
 * middle value respectively, and continuing the search thereafter.
 *
 * In both scenarios where the target is not equal to arr[mid], the high and low pointers are reassigned mid decremented
 * /incremented by 1. This ensures that there will never be an infinite loop as the search range will no longer include
 * the mid-value, causing the search range to constantly "shrink". We know we can decrement/increment mid by 1 during
 * the reassignment as the mid-value is definitely not the target and should no longer be in the search range.
 *
 * At the end of every loop, we know that the target value is either within the search range or does not exist in the
 * array, thereby ensuring the correctness of the algorithm.
 *
 * Since after each iteration, the search range is halved, it will only take a maximum of O(log n) times before the
 * target is either found or determined to not exist in the array.
 */
public class BinarySearch {
    /**
     * Searches for a target value within a sorted array using the binary search algorithm.
     * @param arr the sorted array in which the search is to be performed.
     * @param target the value to be searched for.
     * @return the index of the target if found, otherwise -1.
     */
    public static int search(int[] arr, int target) {
        int high = arr.length - 1; // max index is 3 if array length is 4
        int low = 0;
        while (low <= high) { // When low == high, arr[low] can still == target, therefore should still check
            int mid = low + (high - low) / 2; // equivalent to high + low / 2 but reduces cases of integer overflow
            if (arr[mid] > target) {
                high = mid - 1; // -1 since current mid is not == target and should not be in the search range anymore
            } else if (arr[mid] < target) {
                low = mid + 1; // +1 since current mid is not == target and should not be in the search range anymore
            } else {
                return mid;
            }
        }

        return -1;
    }
}