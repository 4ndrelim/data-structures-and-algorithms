package algorithms.binarySearch.binarySearchTemplated;

/**
 * Here, we are implementing BinarySearchTemplated where we search an array for a target value at O(log n) time
 * complexity.
 * <p>
 * Assumptions:
 * The array is sorted in ascending order.
 * All elements in the array are unique. (to allow for easy testing)
 * <p>
 * Brief Description and Implementation Invariant:
 * With the assumption that the array is sorted in ascending order, BinarySearchTemplated reduces the search range by
 * half or half + 1 (due to floor division) after every loop. This is done by reassigning the max (high) or min (low) of
 * the search range to the middle of the search range when the target value is smaller than or larger than the current
 * middle value respectively, and continuing the search thereafter.
 * <p>
 * In this version, there is no condition to check if the current mid is equal to the target to prematurely end the
 * search. Hence, the only way for the loop to complete is when low exceeds high.
 * <p>
 * At the end of every loop, we know that the target value is either within the search range or does not exist in the
 * array, thereby ensuring the correctness of the algorithm.
 * <p>
 * Since after each iteration, the search range is halved, it will only take a maximum of O(log n) times before the
 * target is either found or determined to not exist in the array.
 */
public class BinarySearchTemplated {
    /**
     * A utility method to compare two integers.
     *
     * @param value  The current value from the array.
     * @param target The value being searched for.
     * @return true if the current value is less than the target, otherwise false.
     */
    // The condition should be changed accordingly
    public static boolean condition(int value, int target) {
        return value >= target;
    }

    /**
     * Conducts a binary search to find the target within the sorted array.
     *
     * @param arr    The sorted input array.
     * @param target The value to be searched within the array.
     * @return The index of the target value if found, otherwise -1.
     */
    public static int search(int[] arr, int target) {
        // The search space i.e. high and low should be changed accordingly.
        int high = arr.length - 1; // max index is 3 if array length is 4
        int low = 0;
        while (low < high) {
            int mid = low + (high - low) / 2; // equivalent to high + low / 2 but reduces cases of integer overflow
            if (condition(arr[mid], target)) { // if value >= target
                high = mid;
            } else { // if value < target
                low = mid + 1;
            }
        }

        // Checks if low value is indeed the target
        // Note that the following checks may not be required in other use cases of this template
        if (low < arr.length && arr[low] == target) {
            // returned value should be changed accordingly (low or low - 1)
            return low;
        }
        // Returns -1 if loop was exited without finding the target
        return -1;
    }
}
