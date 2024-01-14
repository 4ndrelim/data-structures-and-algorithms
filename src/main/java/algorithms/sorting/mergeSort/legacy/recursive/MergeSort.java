package algorithms.sorting.mergeSort.legacy.recursive;

import java.util.ArrayList;
import java.util.List;

/**
 * Recursive implementation of merge sort (in ascending order)
 * with only additional O(n) space required.
 */
public class MergeSort {

    /**
     * Sorts a list from a specified start to end point, making use of
     * and additional copy list to store sorted elements.
     *
     * @param <T>   generic type of object
     * @param lst   list of objects to be sorted from start to end
     * @param start sorting of elements starting from this index
     * @param end   sorting of elements up till and inclusive of this index
     * @param copy  copy of the list to store and extract elements in sorted position
     */
    private static <T extends Comparable<T>> void mergeSort(List<T> lst, int start, int end, List<T> copy) {
        if (start == end) {
            return;
        }
        int mid = start + (end - start) / 2;
        mergeSort(copy, start, mid, lst);
        mergeSort(copy, mid + 1, end, lst);
        merge(lst, start, mid, end, copy);
    }

    /**
     * Merging algorithm that merges two sorted sub-lists into one final sorted list.
     *
     * @param <T> generic type of object
     * @param arr at the end, elements from s to e (inclusive) of arr are sorted
     * @param s   start of first sub-list
     * @param m   end (inclusive) of first sub-list; note start of second sub-list is m+1
     * @param e   end (inclusive) of second sub-list
     * @param cpy copy of the sorted sub-lists to extract and insert sorted final list into arr
     */
    private static <T extends Comparable<T>> void merge(List<T> arr, int s, int m, int e, List<T> cpy) {
        int start = s;
        int startLeft = s;
        int startRight = m + 1;

        while (startLeft < m + 1 && startRight < e + 1) {
            if (cpy.get(startLeft).compareTo(cpy.get(startRight)) < 0) {
                arr.set(start++, cpy.get(startLeft++));
            } else {
                arr.set(start++, cpy.get(startRight++));
            }
        }

        while (startLeft < m + 1) {
            arr.set(start++, cpy.get(startLeft++));
        }

        while (startRight < e + 1) {
            arr.set(start++, cpy.get(startRight++));
        }
    }

    /**
     * Sorting algorithm to be called by client.
     *
     * @param <T> generic type
     * @param lst list of elements to be sorted.
     */
    public static <T extends Comparable<T>> void sort(List<T> lst) {
        List<T> copy = new ArrayList<>();
        for (int i = 0; i < lst.size(); i++) {
            copy.add(lst.get(i));
        }
        mergeSort(lst, 0, lst.size() - 1, copy);
    }
}
