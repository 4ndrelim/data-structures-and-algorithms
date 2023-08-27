package algorithms.sorting.cyclicSort.simple;

/**
 * Cyclic sort algorithm can be easily modified to find first missing non-negative integer (i.e. starting from 0)
 * in O(n). <pr></pr>
 *
 * There are other ways of doing so, using a hash set for instance, but what makes cyclic sort stand out is that it is
 * able to do so in O(1) space. In other words, it is in-place and require no additional space. <pr></pr>
 *
 * The algorithm does a 2-pass iteration. In the 1st iteration, it places elements at its rightful position where
 * possible. And in the 2nd iteration, it will look for the first out of place element (element that is not supposed
 * to be in that position). The answer will be the index of that position. <br>
 * Note that the answer is necessarily between 0 and n (inclusive), where n is the length of the array,
 * otherwise there would be a contradiction. <br>
 * So, if a negative number or a number greater than n is encountered, simply ignore the number at the position and
 * move on first. It may be subject to swap later.
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
                int tmp = arr[ele]; // do the swap and place ele at its right position
                arr[ele] = ele;
                arr[curr] = tmp;
            } else {
                curr += 1; // found curr and placed at position curr, move on.
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
