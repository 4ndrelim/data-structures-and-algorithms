import java.util.*;

/**
 * Basic Testing
 * Maintains a non-increasing queue structure; useful for maximum/minimum sliding window fixed length problem
 * Expected behaviour: Numbers that come before a larger number will be displayed as that larger number
 */
public class Test {
  public static void main(String[] args) {
    List<Integer> toInsert = new ArrayList<>(Arrays.asList(new Integer[] {1,3,5,4,3,2}));
    MonotonicQueue mq = new MonotonicQueue<>();
    for (int num : toInsert) {
      mq.push(num);
      System.out.println(mq.max());
    }

    System.out.println("\nPopping thrice: ");
    for (int i = 0; i < 3; i++) {
      mq.pop();
      System.out.println(mq.max());
    }
  }
}
