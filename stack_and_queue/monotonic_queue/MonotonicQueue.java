import java.util.*;

/**
 * Implementation of a non-increasing monotonic queue.
 * Callable methods are:
 * isEmpty()
 * max()
 * pop()
 * push()
 * @param <T> generic type for objects to be stored or queried   
 */
class MonotonicQueue<T extends Comparable<T>> {
  private Deque<Pair<T>> dq = new ArrayDeque<>(); // or LinkedList

  /**
   * Checks if queue is empty.
   * @return boolean
   */
  public boolean isEmpty() {
    return dq.isEmpty();
  }

  /**
   * Push an object into the queue, maintaining the non-increasing property.
   * Note that the object will be wrapped in a node and prior nodes whose 
   * objects' values are less the object to be inserted will be popped from the queue.
   * A count of the number of removed queues will be stored in the current node to track 
   * number of replaced nodes.
   * @param T obj to be inserted
   */
  public void push(T obj) {
    Integer count = 0;
    while (!dq.isEmpty() && obj.compareTo(dq.peekLast().value) > 0) {
      Pair<T> popped = dq.pollLast();
      count += 1 + popped.countDeleted; // accumulate all objects that were deleted to accomodate this obj
    }
    dq.offerLast(new Pair<T>(obj, count));
  }

  /**
   * Returns the highest value stored in the queue.
   * @return the first value of the queue.
   */
  public T max() {
    if (isEmpty()) {
      return null;
    }
    return dq.peek().value;
  }

  /**
   * Removes the node with the highest value in the queue; i.e the first node;
   * but if the tracked deletion count is greater than 0; i.e there have been nodes
   * which have been replaced, then the current node will not yet be removed and 
   * its value will be returned until the queue is done processing all the replaced nodes.
   */
  public void pop() {
    if (dq.peek().countDeleted > 0) {
      dq.peek().countDeleted -= 1;
      return;
    }
    dq.poll();
    return;
  }

  /**
   * Node class that is represented as a pair.
   * Tracks the deleted count and the value to be wrapped.
   */
  private static class Pair<T> {
    private T value;
    private Integer countDeleted;

    private Pair(T val, Integer count) {
      this.value = val;
      this.countDeleted = count;
    }
  }
}
