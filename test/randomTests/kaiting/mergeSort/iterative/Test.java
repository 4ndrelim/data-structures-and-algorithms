package test.randomTests.kaiting.mergeSort.iterative;

import src.algorithms.sorting.mergeSort.iterative.MergeSort;

import java.util.*;
/*
 * Basic testing
 */
public class Test {
  private static <T> void print(List<T> lst) {
    String ret = "[";
    for (int i = 0; i < lst.size(); i++) {
      ret += lst.get(i).toString() + ", ";
    }
    ret = ret.substring(0, ret.length() - 2);
    ret += "]";
    System.out.println(ret);
  }
  public static void main(String[] args) {
    List<Integer> nums = new ArrayList<>(Arrays.asList(5,7,3,4,9,2,6,1,8));
    print(nums);
    MergeSort.sort(nums);
    System.out.print("Sorted: ");
    print(nums);

    List<Integer> nums2 = new ArrayList<>(Arrays.asList(3,4,65,47,86,6,45,69,52,23,99,91,42,22,15,7,78));
    print(nums2);
    MergeSort.sort(nums2);
    System.out.print("Sorted: ");
    print(nums2);
  }
}
