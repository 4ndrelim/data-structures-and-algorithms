package dataStructures.hashSet.openAddressing;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Implementation of a HashSet that uses Open Addressing and linear probing to resolve collisions.
 *
 * <p>The time complexity of operations in this HashSet implementation consists of two components. Firstly,
 * there is the time to compute the hash value, which is typically a constant-time operation.
 * Secondly, there is the time to access the corresponding bucket, which involves probing the buckets using
 * linear probing.
 *
 * <p>Public methods (along with their time-complexity):
 * boolean add(T element)       adds the given element into the HashSet. Expected O(1) assuming SUHA.
 * boolean contains(T element)  checks if the given element is present in the HashSet. Expected O(1) assuming SUHA.
 * boolean remove(T element)    removes the given element in the HashSet. Expected O(1) assuming SUHA.
 * List toList()                returns a List representation of this HashSet. O(n).
 * int size()                   gets the number of elements (cardinality) in this HashSet. O(1).
 * boolean isEmpty()            checks if the HashSet is empty. O(1).
 * int capacity()               returns the capacity of this HashSet. O(1).
 *
 * @param <T> the type of objects that are contained within this HashSet. T must override
 *            Object::equals and Object::hashCode for the methods add, remove, and contains to be well-defined.
 */
public class HashSet<T> {
    private static final int INITIAL_CAPACITY = 16; // Initial capacity of the hash set. Arbitrary.
    private static final double LOAD_FACTOR = 0.75; // Load factor threshold for resizing. Arbitrary.
    private int size; // Number of elements present in the Set (its cardinality).

    // An array is used, instead of an ArrayList, to prevent automatic resizing. This introduces some complexity,
    // since Java arrays are covariant, which could lead to heap pollution if not properly handled.
    private T[] buckets;

    /**
     * Creates an empty HashSet with an initial capacity of 16.
     */
    public HashSet() {
        // Safe cast because the only way to add elements into this HashSet is through the add method, which
        // only takes in elements of type T.
        @SuppressWarnings("unchecked")
        T[] tempBuckets = (T[]) new Object[INITIAL_CAPACITY];
        this.buckets = tempBuckets;
        this.size = 0;

    }

    /**
     * Adds the specified element to this set if it is not already present.
     * If this set already contains the element, the call leaves the set unchanged and returns false.
     * <p>
     * If load factor (0.75) is exceeded, triggers a resize operation and double the current capacity.
     * It's important to note that resizing is not performed with every add operation but rather when the load
     * factor exceeds the threshold. Therefore, the amortized time complexity of adding elements remains O(1).
     *
     * @param element the element to be added to this set.
     * @return true if this set did not already contain the specified element.
     */
    public boolean add(T element) {
        if (contains(element)) {
            return false; // Element is not added.
        }

        if (isLoadFactorExceeded()) {
            resize(capacity() * 2); // Resize to double the capacity.
        }

        int collisions = 0;
        while (collisions < capacity()) {
            int bucketIndex = hashFunction(element, collisions);

            // Insert into empty bucket.
            if (isEmptyBucket(bucketIndex)) {
                buckets[bucketIndex] = element;
                this.size++;
                return true;
            }

            // Bucket is not empty
            collisions++;
        }

        return false;
    }

    /**
     * Removes the specified element from this set if it is present. Returns true if this set
     * contained the element (or equivalently, if this set changed as a result of the call).
     * (This set will not contain the element once the call returns.)
     * <p>
     * Removed elements are replaced with a Tombstone instead of NULL. This is to prevent search from terminating
     * earlier than expected when looking for an element.
     * <p>
     * If load factor falls below 0.25, trigger a resize and halve the current capacity.
     * It's important to note that resizing is not performed with every remove operation but rather when the
     * load factor falls below a certain limit. Therefore, the amortized time complexity of removing elements
     * remains O(1)
     *
     * @param element the element to be removed from this set, if present
     * @return true if this set contained the specified element
     */
    public boolean remove(T element) {
        // If load factor falls below 0.25 and still above minimum size (16), shrink the hashset by half.
        if (size() <= capacity() * 0.25 && capacity() / 2 >= INITIAL_CAPACITY) {
            resize(this.capacity() / 2);
        }

        int collisions = 0;
        while (collisions < capacity()) {
            int bucketIndex = hashFunction(element, collisions);

            // Element is not removed, because it is not in the Set.
            if (isNullBucket(bucketIndex)) {
                return false;
            }

            if (buckets[bucketIndex].equals(element)) {
                buckets[bucketIndex] = tombstone(); // Mark the current bucket with a Tombstone.
                size--;
                return true;
            }

            collisions++;
        }
        return false;
    }

    /**
     * Returns true if this set contains the specified element.
     *
     * @param element the element whose presence in this set is to be tested
     * @return true if this set contains the specified element
     */
    public boolean contains(T element) {
        int collisions = 0;
        while (collisions < capacity()) {
            int bucketIndex = hashFunction(element, collisions);

            // Invariant: Probe sequence is unbroken (no null values between buckets in the sequence).
            // This is maintained by add and delete.
            // This means that given a probe sequence e.g. (1, 2, 3, 4, 5, ...) for a given element, add will attempt to
            // add the element into the buckets in the given order. As a result, if an element is in bucket 3, there
            // will be elements in buckets 1 and 2, given that there must have been collisions for the element to be
            // added to bucket 3 instead of bucket 1, or bucket 2.
            // Similarly, to maintain that invariant, delete will not replace the element with null, but with a
            // marker (Tombstone).
            if (isNullBucket(bucketIndex)) {
                return false;
            }

            if (buckets[bucketIndex].equals(element)) {
                return true;
            }
            // Skips Tombstones/Deleted elements.
            collisions++;
        }
        return false;
    }

    /**
     * Returns true if this HashSet is empty (Cardinality is zero). False otherwise.
     *
     * @return true if this HashSet is empty, false otherwise.
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns the number of elements present in this HashSet (its cardinality).
     *
     * @return the number of elements present in this HashSet.
     */
    public int size() {
        return size;
    }

    /**
     * Returns the list representation of this HashSet.
     *
     * @return the list representation of this HashSet.
     */
    public List<T> toList() {
        return Arrays.stream(buckets)
            .filter(element -> element != null && !element.equals(tombstone()))
            .collect(Collectors.toList());
    }

    /**
     * Returns the number of buckets of this HashSet. Equivalently, returns the maximum number of elements that can
     * be stored in this HashSet.
     *
     * @return the number of buckets of this HashSet.
     */
    public int capacity() {
        return buckets.length; // returns the number of buckets.
    }

    /**
     * Hashes the specified element to determine the bucket index for placement within the array.
     * Note that the hashFunction for an open-addressed HashSet is defined differently from that of chaining,
     * in that it receives an extra <code>position</code> parameter. This position will be taken into account
     * based on the probe strategy (Linear Probing in this implementation).
     * <p>
     * The hash function calculates the index by performing the following steps:
     * <p>
     * 1. Obtains the hash code of the element using its `hashCode` method.
     * <p>
     * 2. Applies a bitwise AND operation with `0x7FFFFFFF` to clear the sign bit of the hash code,
     * ensuring that the resulting value is a non-negative integer.
     * This is necessary because array indices must be non-negative to access elements correctly.
     * <p>
     * 3. Performs the modulus operation (%) with the length of the `buckets` array to wrap the index
     * within the valid range of the array bounds.
     * This ensures that the index falls within the range of available buckets.
     *
     * @param element    the element to be hashed
     * @param collisions the number of collisions so far.
     * @return the bucket index where the element should be placed
     */
    private int hashFunction(T element, int collisions) {
        int hashCode = element.hashCode() & 0x7FFFFFFF;

        // This step is where the OA and chaining implementation differs.
        int probeAdjustedHash = linearProbe(hashCode, collisions);

        return probeAdjustedHash % capacity(); // Division-method. Not the most ideal.
    }

    /**
     * Returns the new hash based on the number of collisions so far. Uses linear probing which increments the hash
     * linearly, hash + (c * collisions), where c is 1 in this case.
     * <p>
     * NOTE: Quadratic probing would look something like
     * hash + (c ^ collisions).
     *
     * @param hash       the original hash value, without accounting for the number of collisions. This would be the
     *                   same hash value as the chaining implementation.
     * @param collisions the number of collisions so far.
     * @return the new hash value, after adjusting for the number of collisions.
     */
    private int linearProbe(int hash, int collisions) {
        return hash + collisions;
    }

    /**
     * Returns true if the bucket at the given bucketIndex contains no elements (Either null or Tombstone).
     *
     * @param bucketIndex the given index of the bucket to check.
     * @return true if the bucket at the given index contains no element, false otherwise.
     */
    private boolean isEmptyBucket(int bucketIndex) {
        return this.isNullBucket(bucketIndex) || isTombstoneBucket(bucketIndex);
    }

    /**
     * Returns true if the bucket at the given bucketIndex contains null.
     *
     * @param bucketIndex the given index of the bucket to check.
     * @return true if the bucket contains null at the given bucketIndex.
     */
    private boolean isNullBucket(int bucketIndex) {
        return buckets[bucketIndex] == null;
    }

    /**
     * Returns true if the bucket at the given bucketIndex contains a Tombstone marker.
     *
     * @param bucketIndex the given index of the bucket to check.
     * @return true if the bucket contains a Tombstone at the given bucketIndex.
     */
    private boolean isTombstoneBucket(int bucketIndex) {
        return tombstone().equals(buckets[bucketIndex]);
    }

    /**
     * If the load factor is exceeded, the capacity is increased by doubling it (possibly triggered after an
     * add operation),
     * or if the load factor falls below 1/4 (arbitrary) of the capacity (and the capacity is larger than the
     * minimum capacity), the
     * capacity is decreased by halving it (possibly triggered after a remove operation).
     * <p>
     * The resizing operation involves rehashing all existing elements into a new array with the updated capacity.
     * This process takes O(n) time, where n is the number of elements in the hash set.
     */
    private void resize(int newCapacity) {
        // creates a temporary reference to the original bucket
        T[] temp = buckets;

        // Safe cast because the only way to add elements into this HashSet is through the add method, which
        // only takes in elements of type T.
        @SuppressWarnings("unchecked")
        T[] newBuckets = (T[]) new Object[newCapacity];
        buckets = newBuckets;
        size = 0;

        // re-hashes every element and re-insert into the newly created buckets.
        Arrays.stream(temp)
            .filter(Objects::nonNull)
            .filter(element -> !element.equals(tombstone()))
            .forEach(this::add);
    }

    /**
     * Returns true if the current load factor is exceeded. The load factor of this HashSet is defined as the ratio of
     * the number of elements present in this set (cardinality) against the number of buckets (capacity), n/m.
     *
     * @return true if the current load factor is exceeded, false otherwise.
     */
    private boolean isLoadFactorExceeded() {
        return this.size() >= this.capacity() * LOAD_FACTOR;
    }

    private static <T> T tombstone() {
        // It is safe to cast Tombstone to T, because methods retrieving elements (HashSet::get) from the HashSet
        // should, and will check whether the item is a Tombstone object, returning null in-place of the Tombstone.
        @SuppressWarnings("unchecked")
        T tombstone = (T) Tombstone.TOMBSTONE;
        return tombstone;
    }

    /**
     * The `Tombstone` class is a marker used to represent removed elements in the `HashSet`.
     * When an element is removed from the set, its corresponding bucket is marked with a tombstone
     * instead of setting it to `null`. This allows the set to differentiate between an empty bucket
     * and a bucket that previously contained an element.
     */
    private static class Tombstone {
        /** The singleton instance of the Tombstone. */
        private static final Tombstone TOMBSTONE = new Tombstone();

        /** Private constructor to prevent instantiation of `Tombstone` objects from outside the class. */
        private Tombstone() {}

        /**
         * Checks if the given object is an instance of Tombstone.
         * Two Tombstone instances will always be the same.
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
