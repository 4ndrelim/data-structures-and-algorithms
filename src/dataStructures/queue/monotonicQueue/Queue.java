package src.dataStructures.queue.monotonicQueue;

/**
 * Implementation of a queue structure using LinkedList.
 * Node class to be used as nodes for the LinkedList
 * is documented at the bottom.
 *
 * Callable methods / commonly supported ops:
 * size()
 * isEmpty()
 * enqueue(T item) - java's offer() / add() equivalent
 * dequeue() - java's poll() / remove() equivalent
 * peek()
 *
 * Note: calling dequeue() or peek() on an eqmpty queue
 *       returns null
 * @param <T> generic type of object to be stored in the queue
 */
public class Queue<T> {
  private Node<T> first;
  private Node<T> last;
  private int size;

  /**
   * Constructor to initialize an empty queue
   */
  public Queue() {
    first = last = null;
    size = 0;
  }

  /**
   * Gets the size of the queue.
   * @return size of queue
   */
  public int size() {
    return this.size;
  }

  /*
   * Checks if queue is empty.
   * @return boolean value
   */
  public boolean isEmpty() {
    return this.size == 0;
  }

  /**
   * Add an element to the end of the queue.
   * Note: java's equivalent methods are offer() / add()
   * @param item item to be pushed
   */
  public void enqueue(T item) {
    Node<T> toInsert = new Node<>(item);
    if (this.isEmpty()) {
      this.first = this.last = toInsert;
    } else {
      this.last.next = toInsert;
      this.last = toInsert;
    }
    this.size++;
  }

  /**
   * Remove an element from the start of the queue.
   * Note: java's equivalent methods are poll() / remove()
   * @return head of the queue; null is empty
   */
  public T dequeue() {
    if (this.isEmpty()) {
      return null;
    }
    this.size--;
    Node<T> removed = this.first;
    this.first = this.first.next;
    return removed.val;
  }

  /**
   * Display the element at the head of the queue.
   * @return first item of the queue; null is queue is empty
   */
  public T peek() {
    if (this.isEmpty()) {
      return null;
    }
    return this.first.val;
  }

  /**
   * Node class to wrap the object.
   * @param <T> generic type of object to be stored in the queue
   */
  private static class Node<T> {
    T val;
    Node<T> next;
    
    private Node(T val) {
      this.val = val;
    }
  }
}
