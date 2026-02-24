package dataStructures.heap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of an (0-indexed) array-based max heap structure derived from a binary heap.
 * <p>
 * Callable methods are:
 * size()               - O(1)
 * peek()               - O(1)
 * offer(T item)        - O(log(n))
 * poll()               - O(log(n)); Often named as extractMax(), poll is the corresponding counterpart in PriorityQueue
 * remove(T obj)        - O(log(n))
 * updateKey(T obj)     - O(log(n))
 * decreaseKey(T obj)   - O(log(n))
 * increaseKey(T obj)   - O(log(n))
 * heapify(List lst)    - O(n)
 * heapify(T ...seq)    - O(n)
 * toString()
 *
 * @param <T> generic type for objects to be stored and queried
 */
public class MaxHeap<T extends Comparable<T>> {
    private List<T> heap;
    private final Map<T, Integer> indexOf; // Identify nodes given a key

    /**
     * Constructs a Max Heap.
     */
    public MaxHeap() {
        heap = new ArrayList<>();
        indexOf = new HashMap<>();
    }

    public int size() {
        return heap.size();
    }

    /**
     * Return, but does not remove, the element with the highest priority.
     *
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
     *
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
     * Note: Duplicates are not supported due to the Map augmentation.
     *
     * @param item item to be inserted
     */
    public void offer(T item) {
        if (indexOf.containsKey(item)) {
            return; // duplicates not supported
        }

        heap.add(item); // add to the end of the arraylist
        indexOf.put(item, size() - 1); // add item into index map
        bubbleUp(size() - 1); // bubbleUp to rightful place
    }

    /**
     * Remove specified object from the heap.
     *
     * @param obj object to be removed
     */
    public void remove(T obj) {
        if (!indexOf.containsKey(obj)) {
            return; // object not in heap
        }
        remove(indexOf.get(obj));
    }

    /**
     * Remove item at index i
     *
     * @param i given index
     * @return deleted element
     */
    private T remove(int i) {
        T item = get(i);
        swap(i, size() - 1); // O(1) swap with last element in the heap
        heap.remove(size() - 1);
        indexOf.remove(item);
        if (i < size()) { // only bubbleDown if not removing the last element
            bubbleDown(i);
        }
        return item;
    }

    /**
     * Decrease the corresponding value of the object.
     *
     * @param obj        old object
     * @param updatedObj updated object with smaller value
     */
    public void decreaseKey(T obj, T updatedObj) {
        if (!indexOf.containsKey(obj) || updatedObj.compareTo(obj) > 0) {
            return; // object not found or updatedObj is not smaller
        }

        int idx = indexOf.get(obj);
        heap.set(idx, updatedObj);
        indexOf.remove(obj);
        indexOf.put(updatedObj, idx);
        bubbleDown(idx);
    }

    /**
     * Increase the corresponding value of the object.
     *
     * @param obj        old object
     * @param updatedObj updated object with larger value
     */
    public void increaseKey(T obj, T updatedObj) {
        if (!indexOf.containsKey(obj) || updatedObj.compareTo(obj) < 0) {
            return; // object not found or updatedObj is not larger
        }

        int idx = indexOf.get(obj);
        heap.set(idx, updatedObj);
        indexOf.remove(obj);
        indexOf.put(updatedObj, idx);
        bubbleUp(idx);
    }

    /**
     * Update the value of an object in the heap.
     * Delegates to increaseKey or decreaseKey based on the comparison.
     * In practice, this unified method is often sufficient.
     *
     * @param obj        old object
     * @param updatedObj updated object
     */
    public void updateKey(T obj, T updatedObj) {
        if (!indexOf.containsKey(obj)) {
            return;
        }
        int cmp = updatedObj.compareTo(obj);
        if (cmp > 0) {
            increaseKey(obj, updatedObj);
        } else if (cmp < 0) {
            decreaseKey(obj, updatedObj);
        }
        // if equal, no change needed
    }

    /**
     * Takes in a list of objects and convert it into a heap structure.
     *
     * @param lst the list of objects
     */
    public void heapify(List<T> lst) {
        heap = new ArrayList<>(lst);
        for (int i = 0; i < size(); i++) {
            indexOf.put(get(i), i);
        }
        for (int i = size() - 1; i >= 0; i--) {
            bubbleDown(i);
        }
    }

    /**
     * Takes in a sequence of objects and insert into a heap.
     *
     * @param seq sequence of T objects
     */
    public void heapify(T... seq) {
        heap = new ArrayList<T>();
        int j = 0;
        for (T obj : seq) {
            heap.add(obj);
            indexOf.put(obj, j);
            j++;
        }
        for (int i = size() - 1; i >= 0; i--) {
            bubbleDown(i);
        }
    }

    /**
     * @return the array representation of the heap in string.
     */
    @Override
    public String toString() {
        if (size() == 0) {
            return "[]";
        }
        StringBuilder ret = new StringBuilder("[");
        for (int i = 0; i < size(); i++) {
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
     *
     * @param idx1 index of the first object
     * @param idx2 index of the second object
     */
    private void swap(int idx1, int idx2) {
        // update the index of each value in the map
        indexOf.put(get(idx1), idx2);
        indexOf.put(get(idx2), idx1);

        T tmp = get(idx1); // Recall internally implemented with an ArrayList
        heap.set(idx1, get(idx2));
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
        return get(getParentIndex(i));
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
        return get(getLeftIndex(i));
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
        return get(getRightIndex(i));
    }

    /**
     * Bubbles up element at index i until heap property is achieved
     * i.e. its value is not larger than its parent
     *
     * @param i given index
     */
    private void bubbleUp(int i) {
        while (i > 0 && get(i).compareTo(getParent(i)) > 0) { // the furthest up you can go is the root
            int parentIdx = getParentIndex(i);
            swap(i, parentIdx);
            i = parentIdx;
        }
    }

    /**
     * Checks if element at index i is a leaf node in the binary tree representation
     *
     * @param i given index
     * @return boolean value that determines is leaf or not
     */
    private boolean isLeaf(int i) {
        // check if node does not have a left child and does not have a right child
        // actually, suffice to check if left child index is out of bound, as right child index > left child index
        return getRightIndex(i) >= size() && getLeftIndex(i) >= size();
    }

    /**
     * Bubble down element at index i until heap property is achieved
     * i.e. its value is not smaller than any of its children
     *
     * @param i given index
     */
    private void bubbleDown(int i) {
        while (!isLeaf(i)) {
            T biggestItem = get(i);
            int biggestItemIndex = i; // index of max item

            // check if left child is greater in priority, if left exists
            if (getLeftIndex(i) < size() && biggestItem.compareTo(getLeft(i)) < 0) {
                biggestItem = getLeft(i);
                biggestItemIndex = getLeftIndex(i);
            }
            // check if right child is greater in priority, if right exists
            if (getRightIndex(i) < size() && biggestItem.compareTo(getRight(i)) < 0) {
                biggestItem = getRight(i);
                biggestItemIndex = getRightIndex(i);
            }

            if (biggestItemIndex == i) {
                break; // heap property is achieved
            }
            swap(i, biggestItemIndex);
            i = biggestItemIndex;
        }
    }
}
