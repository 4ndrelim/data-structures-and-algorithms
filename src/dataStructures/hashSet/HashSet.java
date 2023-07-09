package src.dataStructures.hashSet;

import src.dataStructures.linkedList.LinkedList;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of a HashSet that uses chaining to resolve collisions.
 * @param <T> the type of objects that are contained within this HashSet. T must override
 *           Object::equals and Object::hashCode for the methods add, remove, and contains to be well-defined.
 */
public class HashSet<T extends Comparable<T>> {
    private static final int CAPACITY = 256; // Arbitrary number for now. To implement resizing, etc.
    private final LinkedList<T>[] buckets;
    private int size;

    /**
     * Creates a HashSet with an initial capacity of 256.
     */
    public HashSet() {
        // Safe cast because the only way to add elements is through HashSet::add, which only accepts elements of
        // type T.
        @SuppressWarnings("unchecked")
        LinkedList<T>[] temp = (LinkedList<T>[]) new LinkedList<?>[CAPACITY];
        for (int i = 0; i < CAPACITY; i++) {
            temp[i] = new LinkedList<>();
        }
        this.buckets = temp;
        this.size = 0;
    }

    /**
     * Returns the number of elements in this set (its cardinality).
     *
     * @return the number of elements in this set (its cardinality)
     */
    public int size() {
        return this.size;
    }

    /**
     * Returns true if this set contains no elements.
     *
     * @return true if this set contains no elements
     */
    public boolean isEmpty() {
        return this.size() == 0;
    }

    /**
     * TODO formal documentation.
     * Simple hash function to hash the element into their respective bucket.
     * Currently uses the division method (k % m).
     * T must override both Object::equals and Object::hashCode.
     * @param element the specified element to be hashed.
     * @return the bucket to add the element to.
     */
    private int hashFunction(T element) {
        return Math.abs(element.hashCode() % CAPACITY);
    }

    /**
     * Adds the specified element to this set if it is not already present
     * More formally, adds the specified element e to this set if
     * the set contains no element e2 such that Objects.equals(e, e2).
     * If this set already contains the element, the call leaves the set
     * unchanged and returns false.
     *
     * @param element the element to be added to this set
     * @return true if this set did not already contain the specified
     * element
     */
    public boolean add(T element) {
        int bucket = this.hashFunction(element);
        if (this.buckets[bucket].search(element) != -1) {
            return false; // element is already in the set.
        }
        ++this.size;
        return this.buckets[bucket].insertFront(element);
    }

    /**
     * Returns true if this set contains the specified element.
     *
     * @param element the element whose presence in this set is to be tested
     * @return true if this set contains the specified element
     */
    public boolean contains(T element) {
        int bucket = this.hashFunction(element);
        return this.buckets[bucket].search(element) != -1;
    }

    /**
     * Removes the specified element from this set if it is present.
     * More formally, removes an element e such that Objects.equals(o, e),
     * if this set contains such an element.  Returns true if this set
     * contained the element (or equivalently, if this set changed as a
     * result of the call).  (This set will not contain the element once the
     * call returns.)
     *
     * @param element the element to be removed from this set, if present
     * @return true if this set contained the specified element
     */
    public boolean remove(T element) {
        int bucket = this.hashFunction(element);
        int index = this.buckets[bucket].search(element);
        if (index == -1) {
            return false; // If the element is not in the hashset.
        }
        this.buckets[bucket].remove(index);
        --this.size;
        return true;
    }

    public List<T> toList() {
        List<T> outputList = new ArrayList<>();
        for (LinkedList<T> bucket : this.buckets) {
            while (bucket.size() != 0) {
                outputList.add(bucket.pop());
            }
        }
        return outputList;
    }
}
