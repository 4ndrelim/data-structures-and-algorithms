import java.util.*;

/**
 * Implementation of a conventional max heap structure
 * using binary heap stored as an array.
 * Note that array is filled in in BFS/level-order.
 * Note implementation assumes a 0-based index.
 * Callable methods are:
 * size()
 * offer(T item)
 * poll()
 * peek()
 * heapify(List<T> lst)
 * heapify(T ...seq)
 * remove(T obj)
 * @param <T> generic type for objects to be stored and queried
 */
class Heap<T extends Comparable<T>> {
  private List<T> heap;
  private Map<T, Integer> index;

  public Heap() {
    heap = new ArrayList<>();
    index = new HashMap<>();
  }

  public int size() {
    return this.heap.size();
  }

  /**
   * @param i given index
   * @return element at index i
   */
  private T get(int i) {
    return this.heap.get(i);
  }
  
  /**
   * Swaps two objects at the specifed indices in the heap.
   * @param idx1 index of the first object
   * @param idx2 index of the second object
   */
  private void swap(int idx1, int idx2) {
    // update the index of each value in the map
    this.index.put(this.get(idx1), idx2);
    this.index.put(this.get(idx2), idx1);

    T tmp = this.get(idx1); // Note internally implemented with an ArrayList
    this.heap.set(idx1, this.get(idx2));
    this.heap.set(idx2, tmp);
  }

  /**
   * @param i given index
   * @return index of the parent of the element at index i
   */
  private int getParentIndex(int i) {
    return (i + 1) / 2 - 1;
  }

  /**
   * @param i given index
   * @return parent of element at index i
   */
  private T getParent(int i) {
    return this.get(this.getParentIndex(i));
  }
  
  /**
   * @param i given index
   * @return index of left child of element at index i
   */
  private int getLeftIndex(int i) {
    return (i + 1) * 2 - 1;
  }

  /**
   * @param i given index
   * @return left child of element at index i
   */
  private T getLeft(int i) {
    return this.get(this.getLeftIndex(i));
  }

  /**
   * @param i given index
   * @return right child of element at index i
   */
  private int getRightIndex(int i) {
    return (i + 1) * 2;
  }

  /**
   * @param i given index
   * @return right child of element at index i
   */
  private T getRight(int i) {
    return this.get(this.getRightIndex(i));
  }

  /**
   * Bubble up element at index i until heap property is achieved
   * i.e its value is not larger than its parent; O(logn).
   * @param i given index
   */
  private void bubbleUp(int i) {
    // while i does not rep the root
    while (i > 0 && this.get(i).compareTo(this.getParent(i)) > 0) {
      int parentIdx = this.getParentIndex(i);
      this.swap(i, parentIdx);
      i = parentIdx;
    }
  }

  /**
   * Checks if element at i is a leaf node in the binary tree representation
   * @param i given index
   * @return boolean value that determines is leaf or not
   */
  private boolean isLeaf(int i) {
    return this.getRightIndex(i) >= this.size() && this.getLeftIndex(i) >= this.size();
  }

  /**
   * Bubble down element at index i until heap property is achieved
   * i.e its value is not smaller than any of its children; O(logn)
   * @param i given index
   */
  private void bubbleDown(int i) {
    while (!this.isLeaf(i)) {
      T maxItem = this.get(i);
      int maxIndex = i; // index of max item

      // check if left child is greater in priority, if left exists
      if (this.getLeftIndex(i) < this.size() && maxItem.compareTo(this.getLeft(i)) < 0) {
        maxItem = this.getLeft(i);
        maxIndex = this.getLeftIndex(i);
      }
      // check if right child is greater in priority, if right exists
      if (this.getRightIndex(i) < this.size() && maxItem.compareTo(this.getRight(i)) < 0) {
        maxItem = this.getRight(i);
        maxIndex = this.getRightIndex(i);
      }

      if (maxIndex != i) {
        this.swap(i, maxIndex);
        i = maxIndex;
      } else {
        break;
      }
    }
  }

  /**
   * Remove item at index i; runs in O(log n).
   * @param i given index
   * @return deleted element
   */
  private T remove(int i) {
    // remember element to be removed
    T item = this.get(i);
    // swap with last element in the heap
    this.swap(i, this.size() - 1); // O(1)
    this.heap.remove(this.size() - 1); // O(1)
    this.index.remove(item); // remove from index map
    this.bubbleDown(i); // O(log n)
    return item;
  }

  /**
   * Return element with highest priority
   * @return head of heap
   */
  public T poll() {
    if (this.size() > 0) {
      return remove(0);
    } else {
      return null;
    }
  }

  /**
   * Inserts item into heap; runs in O(log n).
   * @param item item to be inserted
   */
  public void offer(T item) {
    this.heap.add(item); // add to the end of the arraylist
    this.index.put(item, this.size() - 1); // add item into index map
    this.bubbleUp(this.size() - 1); // bubbleUp to rightful place
  }

  /**
   * Return, but does not remove, the element with the highest priority
   * @return head of heap
   */
  public T peek() {
    if (this.size() > 0) {
      return this.heap.get(0);
    } else {
      return null;
    }
  }

  /**
   * Takes in a list of objects and convert it into a heap structure; O(n).
   * @param lst the list of objects
   */
  public void heapify(List<T> lst) {
    this.heap = new ArrayList<>(lst);
    for (int i = 0; i < this.size(); i++) {
      this.index.put(this.get(i), i);
    }
    for (int i = this.size() - 1; i >= 0; i--) {
      this.bubbleDown(i);
    }
  }

  /**
   * Takes in a sequence of objects and insert into a heap; O(n).
   * @param ...seq sequence of T objects
   */
  @SuppressWarnings("unchecked")
  public void heapify(T ...seq) {
    this.heap = new ArrayList<T>();
    int j = 0;
    for (T obj : seq) {
      this.heap.add(obj);
      this.index.put(obj, j);
      j++;
    }
    for (int i = this.size() - 1; i >= 0; i--) {
      this.bubbleDown(i);
    }
  }

  /**
   * Remove specified object from the heap.
   * @param obj object to be removed
   */
  public void remove(T obj) {
    if (!this.index.containsKey(obj)) {
      System.out.println(String.format("%s does not exist!", obj));
      return;
    }
    this.remove(this.index.get(obj));
  }

  /**
   * @return the string representation of the heap
   */
  @Override
  public String toString() {
    StringBuilder ret = new StringBuilder("[");
    for (int i = 0; i < this.size(); i++) {
      ret.append(this.heap.get(i));
      ret.append(", ");
    }
    return ret.substring(0, ret.length() - 2) + "]";
  }

}
