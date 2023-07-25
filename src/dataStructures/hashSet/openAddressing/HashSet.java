package src.dataStructures.hashSet.openAddressing;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Implementation of a HashSet that uses Open Addressing and linear probing to resolve collisions.
 *
 * <p>The time complexity of operations in this HashSet implementation consists of two components. Firstly, there is the time to
 * compute the hash value, which is typically a constant-time operation. Secondly, there is the time to access the corresponding
 * bucket, which involves probing the buckets using linear probing.
 *
 * <p>Public methods (along with their time-complexity):
 * boolean add(T element)       adds the given element into the HashSet. Expected O(1) assuming SUHA.
 * boolean contains(T element)  checks if the given element is present in the HashSet. Expected O(1) assuming SUHA.
 * boolean remove(T element)    removes the given element in the HashSet. Expected O(1) assuming SUHA.
 * List<T> toList()             returns a List representation of this HashSet. O(n).
 * int size()                   gets the number of elements (cardinality) in this HashSet. O(1).
 * boolean isEmpty()            checks if the HashSet is empty. O(1).
 * int capacity()               returns the capacity of this HashSet. O(1).
 *
 * @param <T> the type of objects that are contained within this HashSet. T must override
 *           Object::equals and Object::hashCode for the methods add, remove, and contains to be well-defined.
 */
public class HashSet<T>{
    private final int INITIAL_CAPACITY = 16; // Initial capacity of the hash set.
    private final double LOAD_FACTOR = 0.75; // Load factor threshold for resizing.
    private final int ELEMENT_NOT_FOUND = -1;
    private int size; // Number of elements present in the Set (its cardinality).
    private T[] buckets;
    private final T TOMBSTONE;

    /**
     * Creates a HashSet with an initial capacity of 16.
     */
    public HashSet() {
        // Safe cast because the only way to add elements into this HashSet is through the add method, which
        // only takes in elements of type T.
        @SuppressWarnings("unchecked")
        T[] tempBuckets = (T[]) new Object[INITIAL_CAPACITY];
        this.buckets = tempBuckets;
        this.size = 0;

        // There is no way to retrieve an instance of Tombstone. Therefore, it is safe to cast Tombstone to T.
        @SuppressWarnings("unchecked")
        T tempVar = (T) Tombstone.TOMBSTONE;
        this.TOMBSTONE = tempVar;
    }

    /**
     * Adds the specified element to this set if it is not already present
     * If this set already contains the element, the call leaves the set unchanged and returns false.
     * <p>
     *     If load factor (0.75) is exceeded, triggers a resize operation and double the current capacity.
     *     It's important to note that resizing is not performed with every add operation but rather when the load
     *     factor exceeds the threshold. Therefore, the amortized time complexity of adding elements remains O(1)
     *
     * @param element the element to be added to this set
     * @return true if this set did not already contain the specified
     * element
     */
    public boolean add(T element) {
        if (isLoadFactorExceeded()) {
            resize(this.capacity() * 2); // Resize to double the capacity.
        }

        int bucketIndex = this.linearProbe(element);
        if (!this.isEmptyBucket(bucketIndex)) { // probe function returns the index of an empty bucket or the index containing the element.
            return false; // Duplicate elements are not added to the set.
        }
        this.buckets[bucketIndex] = element;
        this.size++;
        return true;
    }

    /**
     * Removes the specified element from this set if it is present. Returns true if this set
     * contained the element (or equivalently, if this set changed as a result of the call).
     * (This set will not contain the element once the call returns.)
     *<p>
     *     Removed elements are replaced with a Tombstone instead of NULL. This is to prevent search from terminating earlier
     *     than expected when looking for an element.
     * <p>
     *     If load factor falls below 0.25, trigger a resize and halve the current capacity.
     *     It's important to note that resizing is not performed with every remove operation but rather when the
     *     load factor falls below a certain limit. Therefore, the amortized time complexity of removing elements
     *     remains O(1)
     *
     * @param element the element to be removed from this set, if present
     * @return true if this set contained the specified element
     */
    public boolean remove(T element) {
        // If load factor falls below 0.25 and still above minimum size (16), shrink the hashset by half.
        if (this.size() <= this.capacity() * 0.25 && this.capacity() / 2 >= INITIAL_CAPACITY) {
            resize(this.capacity() / 2);
        }

        int bucketIndex = this.search(element);
        if (bucketIndex == ELEMENT_NOT_FOUND) {
            return false; // If the index returned by the probe function contains an empty bucket, then the element is not present in the set.
        }
        this.buckets[bucketIndex] = this.TOMBSTONE; // marks the current bucket with a TOMBSTONE.
        this.size--;
        return true;
    }

    /**
     * Returns true if this set contains the specified element.
     *
     * @param element the element whose presence in this set is to be tested
     * @return true if this set contains the specified element
     */
    public boolean contains(T element) {
        int bucketIndex = this.search(element);

        if (bucketIndex == ELEMENT_NOT_FOUND) {
            return false;
        }

        // Checks equality of element using Object::equals and Object::hashCode
        return element.equals(this.buckets[bucketIndex])
                && element.hashCode() == this.buckets[bucketIndex].hashCode();
    }

    /**
     * Returns true if this HashSet is empty (Cardinality is zero). False otherwise.
     *
     * @return true if this HashSet is empty, false otherwise.
     */
    public boolean isEmpty() {
        return this.size() == 0;
    }

    /**
     * Returns the number of elements present in this HashSet (its cardinality).
     *
     * @return the number of elements present in this HashSet.
     */
    public int size() {
        return this.size;
    }

    /**
     * Returns the list representation of this HashSet.
     *
     * @return the list representation of this HashSet.
     */
    public List<T> toList() {
        return Arrays.stream(this.buckets)
                     .filter(element -> element != null && !element.equals(this.TOMBSTONE))
                     .collect(Collectors.toList());
    }

    /**
     * Returns the number of buckets of this HashSet. Equivalently, returns the maximum number of elements that can
     * be stored in this HashSet.
     *
     * @return the number of buckets of this HashSet.
     */
    public int capacity() {
        return this.buckets.length; // returns the number of buckets.
    }

    /**
     * Hashes the specified element to determine the bucket index for placement within the array.
     * The hash function calculates the index by performing the following steps:
     * <p>
     * 1. Obtains the hash code of the element using its `hashCode` method.
     * <p>
     * 2. Applies a bitwise AND operation with `0x7FFFFFFF` to clear the sign bit of the hash code,
     *    ensuring that the resulting value is a non-negative integer.
     *    This is necessary because array indices must be non-negative to access elements correctly.
     * <p>
     * 3. Performs the modulus operation (%) with the length of the `buckets` array to wrap the index
     *    within the valid range of the array bounds.
     *    This ensures that the index falls within the range of available buckets.
     *
     * @param element the element to be hashed
     * @return the bucket index where the element should be placed
     */
    private int hashFunction(T element) {
        int hashCode = element.hashCode();
        return (hashCode & 0x7FFFFFFF) % buckets.length;
    }

    /**
     * Given an element, returns the index of an empty (defined as null OR tombstone) bucket to insert the element at.
     * If the element is already present in the HashSet, return its index.
     *
     * @param element the given element to probe an empty bucket for.
     * @return the index of an empty bucket.
     */
    private int linearProbe(T element) {
        int startingProbeIndex = hashFunction(element);

        int currentBucketIndex = startingProbeIndex;
        for (int i = 0; i < this.capacity() - 1; i ++) {
            T existingElement = this.buckets[currentBucketIndex];
            // check for empty / available bucket.
            if (this.isEmptyBucket(currentBucketIndex)) {
                return currentBucketIndex;
            }
            
            // check if element is equals to the element in the bucket.
            // Checks equality of element using Object::equals and Object::hashCode
            if (element.equals(existingElement) 
                    && element.hashCode() == existingElement.hashCode()) {
                return currentBucketIndex;
            }
            currentBucketIndex = (currentBucketIndex + 1) % this.capacity();
        }
        return ELEMENT_NOT_FOUND; // placeholder return value for now. Will never reach this line.
    }

    /**
     * Given an element, return the index of the bucket containing the element.
     * Performance degrades badly as load factor approaches 1.
     *
     * @param element the element to look for.
     * @return the index of the bucket containing the element.
     */
    private int search(T element) {
        int startingProbeIndex = hashFunction(element);

        int currentBucketIndex = startingProbeIndex;
        for (int i = 0; i < this.capacity() - 1; i++) {
            // if bucket contains NULL, means element is not present because deleted elements are marked with TOMBSTONE.
            // That is to say given an arbitrary probe sequence of index 1, 2, 3, ..., there can never be a case where
            // there is a NULL bucket in the middle of the probe sequence; only TOMBSTONE markers.
            if (this.isNullBucket(currentBucketIndex)) {
                return ELEMENT_NOT_FOUND;
            }
            // if bucket contains TOMBSTONE, skip checking current bucket index for equality.
            if (this.isTombstoneBucket(currentBucketIndex)) {
                continue;
            }

            // Checks equality of elements using Object::equals and Object::hashCode.
            if (this.buckets[currentBucketIndex].equals(element)
                    && this.buckets[currentBucketIndex].hashCode() == element.hashCode()) {
                return currentBucketIndex;
            }
            currentBucketIndex = (currentBucketIndex + 1) % this.capacity();
        }

        return ELEMENT_NOT_FOUND; // element is not in the HashSet.
    }

    /**
     * Returns true if the bucket at the given bucketIndex contains no elements (Either null or Tombstone).
     *
     * @param bucketIndex the given index of the bucket to check.
     * @return true if the bucket at the given index contains no element, false otherwise.
     */
    private boolean isEmptyBucket(int bucketIndex) {
        return this.isNullBucket(bucketIndex) || this.isTombstoneBucket(bucketIndex);
    }

    /**
     * Returns true if the bucket at the given bucketIndex contains null.
     *
     * @param bucketIndex the given index of the bucket to check.
     * @return true if the bucket contains null at the given bucketIndex.
     */
    private boolean isNullBucket(int bucketIndex) {
        return this.buckets[bucketIndex] == null;
    }

    /**
     * Returns true if the bucket at the given bucketIndex contains a Tombstone marker.
     *
     * @param bucketIndex the given index of the bucket to check.
     * @return true if the bucket contains a Tombstone at the given bucketIndex.
     */
    private boolean isTombstoneBucket(int bucketIndex) {
        return this.TOMBSTONE.equals(this.buckets[bucketIndex]);
    }

    /**
     * If the load factor is exceeded, the capacity is increased by doubling it (possibly triggered after an add operation),
     * or if the load factor falls below 1/4 (arbitrary) of the capacity (and the capacity is larger than the minimum capacity), the
     * capacity is decreased by halving it (possibly triggered after a remove operation).
     * <p>
     *     The resizing operation involves rehashing all existing elements into a new array with the updated capacity.
     *     This process takes O(n) time, where n is the number of elements in the hash set.
     */
    private void resize(int newCapacity) {
        // creates a temporary reference to the original bucket
        T[] temp = this.buckets;

        // Safe cast because the only way to add elements into this HashSet is through the add method, which
        // only takes in elements of type T.
        @SuppressWarnings("unchecked")
        T[] newBuckets = (T[]) new Object[newCapacity];
        this.buckets = newBuckets;
        this.size = 0;

        // re-hashes every element and re-insert into the newly created buckets.
        Arrays.stream(temp)
                .filter(Objects::nonNull)
                .filter(element -> !element.equals(this.TOMBSTONE))
                .forEach(this::add);
    }

    /**
     * Returns true if the current load factor is exceeded. The load factor of this HashSet is defined as the ratio of
     * the number of elements present in this set (cardinality) against the number of buckets (capacity), n/m.
     *
     * @return true if the current load factor is exceeded, false otherwise.
     */
    private boolean isLoadFactorExceeded() {
        return this.size() >= this.capacity() * this.LOAD_FACTOR;
    }

    /**
     * The `Tombstone` class is a marker used to represent removed elements in the `HashSet`.
     * When an element is removed from the set, its corresponding bucket is marked with a tombstone
     * instead of setting it to `null`. This allows the set to differentiate between an empty bucket
     * and a bucket that previously contained an element.
     */
    private static class Tombstone {
        /**The singleton instance of the Tombstone.*/
        private static final Tombstone TOMBSTONE = new Tombstone();
        /**Private constructor to prevent instantiation of `Tombstone` objects from outside the class.*/
        private Tombstone() {}

        /**
         * Checks if the given object is an instance of Tombstone.
         *
         * @param obj the object to compare
         * @return true if the object is an instance of Tombstone, false otherwise
         */
        @Override
        public boolean equals(Object obj) {
            return obj instanceof HashSet.Tombstone;
        }

        /**
         * Returns the hash code value for the Tombstone object.
         *
         * @return the hash code value for the Tombstone object
         */
        @Override
        public int hashCode() {
            return System.identityHashCode(this);
        }
    }
}
