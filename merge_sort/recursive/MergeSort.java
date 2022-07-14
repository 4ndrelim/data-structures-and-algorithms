import java.util.*;

// Sorts in ascending order; implemented with O(n) extra space req
public class MergeSort {

  private static <T extends Comparable<T>> void mergeSort(List<T> lst, int start, int end, List<T> copy) {
    if (start == end) {
      return;
    }
    int mid = start + (end - start) / 2;
    mergeSort(copy, start, mid, lst);
    mergeSort(copy, mid + 1, end, lst);
    merge(lst, start, mid, end, copy);
  }

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

  public static <T extends Comparable<T>> void sort(List<T> lst) {
    List<T> copy = new ArrayList<>();
    for (int i = 0; i < lst.size(); i++) {
      copy.add(lst.get(i));
    }
    mergeSort(lst, 0, lst.size() - 1, copy);
  }
}
