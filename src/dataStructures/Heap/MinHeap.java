package src.dataStructures.Heap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MinHeap<T extends Comparable<T>> {
    private List<T> heap; // Binary Min-Heap represented using an array
    private Map<T, Integer> indexOf; // To get the index of the item stored in the heap

    public MinHeap() {
        this.heap = new ArrayList<>();
        this.indexOf = new HashMap<>();
    }

    private T getParent(T current) {
        int parentIndex = (this.indexOf.get(current) - 1) / 2;
        return this.heap.get(parentIndex);
    }

    private boolean hasLeftChild(T current) {
        int leftChildIndex = (this.indexOf.get(current) * 2) + 1;
        return leftChildIndex < this.heap.size();
    }

    private boolean hasRightChild(T current) {
        int rightChildIndex = (this.indexOf.get(current) * 2) + 2;
        return rightChildIndex < this.heap.size();
    }

    private boolean isRoot(T current) {
        return this.indexOf.get(current) == 0;
    }

    private boolean isLeaf(T current) {
        return !(this.hasLeftChild(current) || this.hasRightChild(current));
    }

    private T getLeftChild(T current) {
        int leftChildIndex = (this.indexOf.get(current) * 2) + 1;
        if (!hasLeftChild(current)) {
            return null;
        }
        return this.heap.get(leftChildIndex);
    }

    private T getRightChild(T current) {
        int rightChildIndex = (this.indexOf.get(current) * 2) + 2;
        if (!hasRightChild(current)) {
            return null;
        }
        return this.heap.get(rightChildIndex);
    }

    private void swap(T toSwap, T dest) {
        int toSwapIndex = this.indexOf.get(toSwap);
        int destIndex = this.indexOf.get(dest);

        // updates positions in the Heap.
        this.heap.set(destIndex, toSwap);
        this.heap.set(toSwapIndex, dest);

        // updates HashMap index
        this.indexOf.put(toSwap, destIndex);
        this.indexOf.put(dest, toSwapIndex);
    }

    private void bubbleUp(T item) {
        while (!this.isRoot(item)) {
            T parent = this.getParent(item);
            if (item.compareTo(parent) < 0) {
                swap(item, parent);
            } else {
                return;
            }
        }
    }

    private void bubbleDown(T item) {
        while (!isLeaf(item)) {
            T leftChild = this.getLeftChild(item);
            T rightChild = this.getRightChild(item);
            T minPriority = item;
            // Gets the maximum priority out of the item, left and right child.
            if (leftChild != null && leftChild.compareTo(minPriority) < 0) {
                minPriority = leftChild;
            }
            if (rightChild != null && rightChild.compareTo(minPriority) < 0) {
                minPriority = rightChild;
            }

            if (minPriority != item) {
                this.swap(item, minPriority);
            } else {
                return; // item already in place
            }
        }
    }

    private T remove(T item) {
        if (!this.isLeaf(item)) {
            T last = this.heap.get(this.heap.size() - 1);
            this.swap(item, last);
            this.heap.remove(this.heap.size() - 1);
            this.indexOf.remove(item);
            bubbleDown(last);
            return item;
        }
        this.heap.remove(this.heap.size() - 1);
        this.indexOf.remove(item);
        return item;
    }

    // insert at the last position / leaf, then bubbleUp

    public void insert(T item) {
        this.heap.add(item);
        this.indexOf.put(item, this.heap.size() - 1);
        bubbleUp(item);
    }
    public T getMin() {
        return this.heap.get(0);
    }

    // swaps with last position, bubble down newRoot
    public T extractMin() {
        return this.remove(this.getMin());
    }

    public void decreaseKey(T key, T newKey) {
        // does nothing if newKey is larger than original.
        if (newKey.compareTo(key) > 0) {
            return;
        }
        // replaces key with newKey and bubbleUp
        int index = this.indexOf.get(key);
        this.heap.set(index, newKey);
        this.indexOf.remove(key);
        this.indexOf.put(newKey, index);
        bubbleUp(newKey);
    }

    public boolean isEmpty() {
        return this.heap.isEmpty();
    }

    // TODO
    public MinHeap<T> heapify(List<T> toHeapify) {
        System.out.println("TODO");
        return null;
    }

    // TODO
    public MinHeap<T> heapSort(List<T> toSort) {
        System.out.println("TODO");
        return null;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[ ");
        for (T item : this.heap) {
            sb.append(item).append(", ");
        }
        return sb.length() <= 2
                ? sb.append("]").toString()
                : sb.deleteCharAt(sb.length() - 2).append("]").toString();
    }
}
