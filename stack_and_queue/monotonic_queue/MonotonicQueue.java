import java.util.*;

/**
 * Implementation of a non-increasing monotonic queue.
 */
class MonotonicQueue<T extends Comparable<T>> {
  private Deque<Pair<T>> dq = new ArrayDeque<>(); // or LinkedList

  public boolean isEmpty() {
    return dq.isEmpty();
  }
  public void push(T obj) {
    Integer count = 0;
    while (!dq.isEmpty() && obj.compareTo(dq.peekLast().value) > 0) {
      Pair<T> popped = dq.pollLast();
      count += 1 + popped.countDeleted; // accumulate all objects that were deleted to accomodate this obj
    }
    dq.offerLast(new Pair<T>(obj, count));
  }

  public T max() {
    if (isEmpty()) {
      return null;
    }
    return dq.peek().value;
  }

  public void pop() {
    if (dq.peek().countDeleted > 0) {
      dq.peek().countDeleted -= 1;
      return;
    }
    dq.poll();
    return;
  }

  private static class Pair<T> {
    private T value;
    private Integer countDeleted;

    private Pair(T val, Integer count) {
      this.value = val;
      this.countDeleted = count;
    }
  }
}
