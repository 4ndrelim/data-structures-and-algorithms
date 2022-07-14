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
    print(nums);
  }
}
