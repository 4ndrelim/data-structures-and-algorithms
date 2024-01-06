package dataStructures.hashSet.chaining;

import java.util.ArrayList;
import java.util.List;

import dataStructures.linkedList.LinkedList;

/**
 * Implementation of a HashSet that uses chaining to resolve collisions.
 *
 * <p>A HashSet is a data structure that provides expected constant-time complexity for basic operations such as add,
 * remove, and contains.
 * It achieves this performance by leveraging the Simple Uniform Hashing Assumption (SUHA), where elements are
 * uniformly distributed across the available hash buckets. The hash function maps elements to their respective
 * buckets, allowing efficient insertion, retrieval, and removal of elements from the set.
 *
 * <p>This approach is inspired by the need to efficiently store and retrieve elements from a large universe of possible
 * keys, where the number of actual keys is relatively small.
 * Chaining is used to handle collisions, where multiple elements are mapped to the same bucket, by maintaining a
 * linked list of elements within each bucket.
 *
 * <p>The expected load, or load factor, for this approach is an important consideration. The load factor is defined as
 * the ratio of the number of elements in the set to the number of buckets (n/m). A higher load factor increases the
 * likelihood of collisions, leading to longer linked lists and potentially degrading performance. To maintain optimal
 * time complexity, the hash function should aim for an equal distribution of elements across the buckets, avoiding
 * clustering and ensuring efficient access to elements.
 *
 * <p>The time complexity of operations in this HashSet implementation consists of two components. Firstly, there is
 * the time to compute the hash value, which is typically a constant-time operation. Secondly, there is the time to
 * access the corresponding bucket, which involves traversing the linked list in case of collisions. On average,
 * these operations have a constant-time complexity.
 *
 * <p>Public methods (along with their time-complexity):
 * int size()                   gets the number of elements (cardinality) in this HashSet. O(1).
 * boolean isEmpty()            checks if the HashSet is empty. O(1).
 * boolean add(T element)       adds the given element into the HashSet. Expected O(1) assuming SUHA.
 * boolean contains(T element)  checks if the given element is present in the HashSet. Expected O(1) assuming SUHA.
 * boolean remove(T element)    removes the given element in the HashSet. Expected O(1) assuming SUHA.
 * List toList()                returns a List representation of this HashSet. O(n).
 *
 * @param <T> the type of objects that are contained within this HashSet. T must override
 *            Object::equals and Object::hashCode for the methods add, remove, and contains to be well-defined.
 */
public class HashSet<T extends Comparable<T>> {
    private static final int NUMBER_OF_BUCKETS = 256; // Arbitrary number of buckets.
    private final LinkedList<T>[] buckets;
    private int size;

    /**
     * Creates a HashSet with 256 number of buckets.
     */
    public HashSet() {
        // Safe cast because the only way to add elements is through HashSet::add, which only accepts elements of
        // type T.
        @SuppressWarnings("unchecked")
        LinkedList<T>[] temp = (LinkedList<T>[]) new LinkedList<?>[NUMBER_OF_BUCKETS];
        for (int i = 0; i < NUMBER_OF_BUCKETS; i++) {
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
     *
     * @param element the specified element to be hashed.
     * @return the bucket to add the element to.
     */
    private int hashFunction(T element) {
        return Math.abs(element.hashCode() % NUMBER_OF_BUCKETS);
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
     *     element
     */
    public boolean add(T element) {
        int bucket = this.hashFunction(element);
        LinkedList<T> bucketLinkedList = this.buckets[bucket];
        if (bucketLinkedList.search(element) != -1) {
            return false; // element is already in the set.
        }
        ++this.size; // updates the cardinality of this hashset.
        return bucketLinkedList.insertFront(element);
    }

    /**
     * Returns true if this set contains the specified element.
     *
     * @param element the element whose presence in this set is to be tested
     * @return true if this set contains the specified element
     */
    public boolean contains(T element) {
        int bucket = this.hashFunction(element);
        LinkedList<T> bucketLinkedList = this.buckets[bucket];
        return bucketLinkedList.search(element) != -1;
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
        LinkedList<T> bucketLinkedList = this.buckets[bucket];
        int index = bucketLinkedList.search(element);
        if (index == -1) {
            return false; // If the element is not in the hashset.
        }
        bucketLinkedList.remove(index);
        --this.size; // updates the cardinality of the hash set.
        return true;
    }

    /**
     * Returns the elements of this Hash Set in a List representation.
     *
     * @return a List containing the elements in this set.
     */
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
