package dataStructures.queue;

import java.util.Deque;
import java.util.ArrayDeque;

/**
 * Implementation of a non-increasing (decreasing) monotonic queue for certain (but common) use case:
 * When larger objects pushed to the queue are able to replace and represent smaller objects that come before it.
 * Callable methods are:
 * isEmpty()
 * max()
 * pop()
 * push()
 * @param <T> generic type for objects to be stored or queried
 *
 *           index   v   Increasing queue        Decreasing queue
 * 1       5   [5]                     [5]
 * 2       3   [3] 3 kick out 5        [5, 3] #3->5
 * 3       1   [1] 1 kick out 3        [5, 3, 1] #1->3
 * 4       2   [1, 2] #2->1            [5, 3, 2] 2 kick out 1 #2->3
 * 5       4   [1, 2, 4] #4->2         [5,4] 4 kick out 2, 3 #4->2
 */

public class MonotonicQueue<T extends Comparable<T>> {
  private Deque<T> dq = new ArrayDeque<>(); // or LinkedList

  /**
   * Checks if queue is empty.
   * @return boolean
   */
  public boolean isEmpty() {
    return dq.isEmpty();
  }

  /**
   * Push an object into the queue and maintain the non-increasing property.
   * @param obj to be inserted
   */
  public void push(T obj) {
    Integer count = 0;
    while (!dq.isEmpty() && obj.compareTo(dq.peekLast()) > 0) {
      dq.pollLast(); // Removes elements that do not conform the non-increasing sequence.
    }
    dq.offerLast(obj);
  }

  /**
   * Returns the highest value stored in the queue.
   * @return the first value of the queue.
   */
  public T max() {
    if (isEmpty()) {
      return null;
    }
    return dq.peek();
  }

  /**
   * Returns the highest valued object in the queue; i.e the first object;
   * Removal will only be done all representation of the object has been accounted for.
   */
  public T pop() {
    if (dq.isEmpty()) {
      return null;
    }
    return dq.poll(); // Returns & remove head of deque
  }
}
