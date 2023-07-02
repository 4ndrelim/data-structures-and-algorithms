package test.random_tests.changxian.stack_and_queue.monotonic_queue;

import src.data_structures.stack_and_queue.monotonic_queue.MonotonicQueue;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

/**
 * Basic Testing
 * Maintains a non-increasing queue structure; useful for maximum/minimum sliding window fixed length problem
 * Expected behaviour: Numbers that come before a larger number will be displayed as that larger number
 */
public class Test {
  public static void main(String[] args) {
    List<Integer> toInsert = new ArrayList<>(Arrays.asList(new Integer[] {2,7,1,3,7,5,4,3,2,5}));
    MonotonicQueue<Integer> mq = new MonotonicQueue<>();
    for (int num : toInsert) {
      System.out.println(String.format("Pushing %d", num));
      mq.push(num);
      System.out.println(String.format("Max in queue now is %d", mq.max()));
    }

    System.out.println("\nPopping thrice: ");
    for (int i = 0; i < 7; i++) {
      System.out.println(mq.pop());
    }
  }
}
