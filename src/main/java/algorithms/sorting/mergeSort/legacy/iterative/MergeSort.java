package algorithms.sorting.mergeSort.legacy.iterative;

import java.util.ArrayList;
import java.util.List;

/**
 * Iterative implementation of Merge sort with O(n) space
 */
public class MergeSort {

    /**
     * Sorts a list from a specified start to end point.
     * <p>
     * Note: starting with an interval size (call this var interval) of 1 and iteratively *2,
     * Merge two sub-lists of size interval together using merge routine.
     * Invariant: Partitioning the main list into sub-lists of size interval,
     * each of this sub-list is sorted within itself (the last sub-list may not be of
     * size interval, but it is still sorted since the size is necessarily less than interval).
     *
     * @param <T>   generic type of object
     * @param lst   list of objects to be sorted
     * @param start sorting starts at this index
     * @param end   sorting ends (inclusive) at this index
     */
    private static <T extends Comparable<T>> void mergeSort(List<T> lst, int start, int end) {
        if (start == end) {
            return;
        }
        int size = lst.size();
        int interval = 1;
        List<T> tmp = new ArrayList<>();
        for (T item : lst) {
            tmp.add(item);
        }
        while (interval < lst.size()) {
            for (int i = 0; i + interval < size; i += 2 * interval) {
                int start1 = i;
                int start2 = i + interval;
                int e = i + 2 * interval - 1;
                if (e > size - 1) {
                    e = size - 1;
                }
                merge(lst, tmp, start1, start2, e);
            }
            interval *= 2;
        }

    }

    /**
     * Merging algorithm that merges two sorted sub-lists into one final sorted list.
     *
     * @param <T> generic type of object
     * @param lst at the end, elements from s1 to e (inclusive) of lst are sorted
     * @param s1  start index of first sub-list
     * @param s2  start index of second sub-list; note that end index of first sub-list is s2-1
     * @param e   end index of second sub-list
     */
    private static <T extends Comparable<T>> void merge(List<T> lst, List<T> tmp, int s1, int s2, int e) {
        int startLeft = s1;
        int startRight = s2;
        int tmpIdx = s1;
        while (startLeft < s2 && startRight < e + 1) {
            if (lst.get(startLeft).compareTo(lst.get(startRight)) < 0) {
                tmp.set(tmpIdx, lst.get(startLeft++));
            } else {
                tmp.set(tmpIdx, lst.get(startRight++));
            }
            tmpIdx++;
        }

        while (startLeft < s2) {
            tmp.set(tmpIdx++, lst.get(startLeft++));
        }

        while (startRight < e + 1) {
            tmp.set(tmpIdx++, lst.get(startRight++));
        }

        for (int i = s1; i < e + 1; i++) {
            lst.set(i, tmp.get(i));
        }
    }

    /**
     * Sorting algorithm that clients calls
     *
     * @param <T> generic type
     * @param lst list to be sorted
     */
    public static <T extends Comparable<T>> void sort(List<T> lst) {
        mergeSort(lst, 0, lst.size() - 1);
    }
}
