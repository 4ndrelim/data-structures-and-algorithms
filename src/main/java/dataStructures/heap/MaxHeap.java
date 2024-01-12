package dataStructures.heap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of an (0-indexed) array-based max heap structure derived from a binary heap.
 *
 * Callable methods are:
 * size()               - O(1)
 * peek()               - O(1)
 * offer(T item)        - O(log(n))
 * poll()               - O(log(n)); Often named as extractMax(), poll is the corresponding counterpart in PriorityQueue
 * remove(T obj)        - O(log(n))
 * decreaseKey(T obj)   - O(log(n))
 * increaseKey(T obj)   - O(log(n))
 * heapify(List<T> lst) - O(n)
 * heapify(T ...seq)    - O(n)
 * toString()
 *
 * @param <T> generic type for objects to be stored and queried
 */
public class MaxHeap<T extends Comparable<T>> {
  private List<T> heap;
  private Map<T, Integer> indexOf; // Identify nodes given a key

  public MaxHeap() {
    heap = new ArrayList<>();
    indexOf = new HashMap<>();
  }

  public int size() {
    return this.heap.size();
  }

  /**
   * Return, but does not remove, the element with the highest priority.
   * @return head of heap
   */
  public T peek() {
    if (size() > 0) {
      return heap.get(0);
    } else {
      return null;
    }
  }

  /**
   * Return element with the highest priority.
   * @return head of heap
   */
  public T poll() {
    if (size() > 0) {
      return remove(0);
    } else {
      return null;
    }
  }

  /**
   * Inserts item into heap.
   * @param item item to be inserted
   */
  public void offer(T item) {
    assert !indexOf.containsKey(item) : "Duplicate objects found!";

    heap.add(item); // add to the end of the arraylist
    indexOf.put(item, size() - 1); // add item into index map; here becomes problematic if there are duplicates
    bubbleUp(size() - 1); // bubbleUp to rightful place
  }

  /**
   * Remove specified object from the heap.
   * @param obj object to be removed
   */
  public void remove(T obj) {
    if (!indexOf.containsKey(obj)) {
      System.out.println(String.format("%s does not exist!", obj));
      return;
    }
    this.remove(indexOf.get(obj));
  }

  /**
   * Decrease the corresponding value of the object.
   * @param obj old object
   * @param updatedObj updated object
   */
  public void decreaseKey(T obj, T updatedObj) {
    assert updatedObj.compareTo(obj) <= 0 : "Value should reduce or remain the same";

    int idx = indexOf.get(obj); // get the index of the object in the array implementation
    heap.set(idx, updatedObj); // simply replace
    indexOf.remove(obj); // no longer exists
    indexOf.put(updatedObj, idx);
    bubbleDown(idx);
  }

  /**
   * Increase the corresponding value of the object.
   * @param obj old object
   * @param updatedObj updated object
   */
  public void increaseKey(T obj, T updatedObj) {
    assert updatedObj.compareTo(obj) >= 0 : "Value should reduce or remain the same";

    int idx = indexOf.get(obj); // get the index of the object in the array implementation
    heap.set(idx, updatedObj); // simply replace
    indexOf.remove(obj); // no longer exists
    indexOf.put(updatedObj, idx);
    bubbleUp(idx);
  }

  /**
   * Takes in a list of objects and convert it into a heap structure.
   * @param lst the list of objects
   */
  public void heapify(List<T> lst) {
    heap = new ArrayList<>(lst);
    for (int i = 0; i < this.size(); i++) {
      indexOf.put(this.get(i), i);
    }
    for (int i = this.size() - 1; i >= 0; i--) {
      bubbleDown(i);
    }
  }

  /**
   * Takes in a sequence of objects and insert into a heap.
   * @param <...> seq sequence of T objects
   */
  public void heapify(T ...seq) {
    heap = new ArrayList<T>();
    int j = 0;
    for (T obj : seq) {
      heap.add(obj);
      indexOf.put(obj, j);
      j++;
    }
    for (int i = this.size() - 1; i >= 0; i--) {
      bubbleDown(i);
    }
  }

  /**
   * @return the array representation of the heap in string.
   */
  @Override
  public String toString() {
    StringBuilder ret = new StringBuilder("[");
    for (int i = 0; i < this.size(); i++) {
      ret.append(heap.get(i));
      ret.append(", ");
    }
    return ret.substring(0, ret.length() - 2) + "]";
  }

  /**
   * @param i given index
   * @return element at index i
   */
  private T get(int i) {
    return heap.get(i);
  }
  
  /**
   * Swaps two objects at the specified indices in the heap.
   * @param idx1 index of the first object
   * @param idx2 index of the second object
   */
  private void swap(int idx1, int idx2) {
    // update the index of each value in the map
    indexOf.put(this.get(idx1), idx2);
    indexOf.put(this.get(idx2), idx1);

    T tmp = get(idx1); // Recall internally implemented with an ArrayList
    heap.set(idx1, this.get(idx2));
    heap.set(idx2, tmp);
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
    return this.get(getParentIndex(i));
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
    return this.get(getLeftIndex(i));
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
    return this.get(getRightIndex(i));
  }

  /**
   * Bubbles up element at index i until heap property is achieved
   * i.e. its value is not larger than its parent
   * @param i given index
   */
  private void bubbleUp(int i) {
    while (i > 0 && this.get(i).compareTo(getParent(i)) > 0) { // the furthest up you can go is the root
      int parentIdx = getParentIndex(i);
      this.swap(i, parentIdx);
      i = parentIdx;
    }
  }

  /**
   * Checks if element at index i is a leaf node in the binary tree representation
   * @param i given index
   * @return boolean value that determines is leaf or not
   */
  private boolean isLeaf(int i) {
    // actually, suffice to compare index of left child of a node and size of heap
    return this.getRightIndex(i) >= this.size() && this.getLeftIndex(i) >= this.size();
  }

  /**
   * Bubble down element at index i until heap property is achieved
   * i.e. its value is not smaller than any of its children
   * @param i given index
   */
  private void bubbleDown(int i) {
    while (!this.isLeaf(i)) {
      T maxItem = this.get(i);
      int maxIndex = i; // index of max item

      // check if left child is greater in priority, if left exists
      if (getLeftIndex(i) < this.size() && maxItem.compareTo(getLeft(i)) < 0) {
        maxItem = getLeft(i);
        maxIndex = getLeftIndex(i);
      }
      // check if right child is greater in priority, if right exists
      if (getRightIndex(i) < this.size() && maxItem.compareTo(getRight(i)) < 0) {
        maxIndex = getRightIndex(i);
      }

      if (maxIndex != i) {
        swap(i, maxIndex);
        i = maxIndex;
      } else {
        break;
      }
    }
  }

  /**
   * Remove item at index i
   * @param i given index
   * @return deleted element
   */
  private T remove(int i) {
    T item = this.get(i); // remember element to be removed
    swap(i, this.size() - 1); // O(1) swap with last element in the heap
    heap.remove(this.size() - 1); // O(1)
    indexOf.remove(item); // remove from index map
    bubbleDown(i); // O(log n)
    return item;
  }

}
