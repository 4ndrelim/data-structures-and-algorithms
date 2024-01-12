package algorithms.sorting.cyclicSort.simple;

/**
 * Cyclic sort algorithm can be easily modified to find first missing non-negative integer (i.e. starting from 0)
 * in O(n).
 */
public class FindFirstMissingNonNegative {
    public static int findMissing(int[] arr) {
        int curr = 0;
        while (curr < arr.length) {
            int ele = arr[curr];
            if (ele >= 0 && ele < arr.length && ele != curr) { // if ele (still) needs to be placed in its correct pos
                if (arr[ele] == ele) { // the correct position of ele already has ele (i.e. duplicates), so just move on
                    curr += 1;
                    continue;
                }
                int tmp = arr[ele]; // do the swap and place ele at its right position first
                arr[ele] = ele;
                arr[curr] = tmp;
            } else {
                curr += 1; // either found the correct element, or a number out of range to ignore first.
            }
        }

        for (int i = 0; i < arr.length; i++) { // iterate to look for the missing number which will be out of place.
            if (arr[i] != i) {
                return i; // this is the missing non-negative element!
            }
        }
        return arr.length; // 0 to n-1 integers are all present. First missing non-negative element is n.
    }
}
