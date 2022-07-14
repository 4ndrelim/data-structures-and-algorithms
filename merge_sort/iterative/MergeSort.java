import java.util.*;

// Sorts in ascending order
public class MergeSort {

  private static <T extends Comparable<T>> void mergeSort(List<T> lst, int start, int end) {
    if (start == end) {
      return;
    }
    int size = lst.size();
    int interval = 1;
    while (interval < lst.size()) {
      for (int i = 0; i + interval < size; i += 2*interval) {
        int start1 = i;
        int start2 = i + interval;
        int e = i + 2 * interval - 1 < size ? i + 2 * interval - 1 : size - 1;
        merge(lst, start1, start2, e);
      }
      interval *= 2;
    }
    
  }

  private static <T extends Comparable<T>> void merge(List<T> lst, int s1, int s2, int e) {
    int startLeft = s1;
    int startRight = s2;
    List<T> tmp = new ArrayList<>();

    while (startLeft < s2 && startRight < e + 1) {
      if (lst.get(startLeft).compareTo(lst.get(startRight)) < 0) {
        tmp.add(lst.get(startLeft++));
      } else {
        tmp.add(lst.get(startRight++));
      }
    }

    while (startLeft < s2) {
      tmp.add(lst.get(startLeft++));
    }

    while (startRight < e + 1) {
      tmp.add(lst.get(startRight++));
    }

    for (int i = 0; i < e-s1+1; i++) {
      lst.set(s1+i, tmp.get(i));
    }
  }

  public static <T extends Comparable<T>> void sort(List<T> lst) {
    mergeSort(lst, 0, lst.size() - 1);
  }
}
