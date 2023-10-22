package randomTests.changxian.stackAndQueue.monotonicQueue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dataStructures.queue.MonotonicQueue;

/**
 * Basic Testing.
 * Maintains a non-increasing queue structure; useful for maximum/minimum sliding window fixed
 * length problem
 * Expected behaviour: Numbers that come before a larger number will be displayed as that larger
 * number
 */
public class Test {
  /**
   * Runs the custom test.
   *
   * @param args unused.
   */
  public static void main(String[] args) {
    List<Integer> toInsert =
        new ArrayList<>(Arrays.asList(2, 7, 1, 3, 7, 5, 4, 3, 2, 5));
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
