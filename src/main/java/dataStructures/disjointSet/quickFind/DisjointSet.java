package dataStructures.disjointSet.quickFind;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of quick-find structure; Turns a list of objects into a data structure that supports union operations
 * Note DS structure is not suited with duplicate elements!
 *
 * @param <T> generic type of object to be stored
 */
public class DisjointSet<T> {
    private final Map<T, Integer> identifier;

    /**
     * Basic constructor to create the Disjoint Set data structure.
     */
    public DisjointSet() {
        identifier = new HashMap<>();
    }

    /**
     * Constructor to initialize Disjoint Set with a known list of objects.
     * @param objects
     */
    public DisjointSet(List<T> objects) {
        identifier = new HashMap<>();
        int size = objects.size();
        for (int i = 0; i < size; i++) {
            // internally, component identity is tracked with integers
            identifier.put(objects.get(i), identifier.size()); // each obj initialize with a unique identity using size;
        }
    }

    /**
     * Constructor to initialize Disjoint Set with a known array of objects.
     * @param objects
     */
    public DisjointSet(T[] objects) {
        identifier = new HashMap<>();
        int size = objects.length;
        for (int i = 0; i < size; i++) {
            // internally, component identity is tracked with integers
            identifier.put(objects[i], identifier.size()); // each obj initialize with a unique identity using size;
        }
    }

    public int size() {
        return identifier.size();
    }

    /**
     * Adds an object into the structure.
     * @param obj
     */
    public void add(T obj) {
        identifier.put(obj, identifier.size());
    }

    /**
     * Checks if object a and object b are in the same component.
     * @param a
     * @param b
     * @return a boolean value
     */
    public boolean find(T a, T b) {
        if (!identifier.containsKey(a) || !identifier.containsKey(b)) { // key(s) does not even exist
            return false;
        }
        return identifier.get(a).equals(identifier.get(b));
    }

    /**
     * Merge the components of object a and object b.
     * @param a
     * @param b
     */
    public void union(T a, T b) {
        if (!identifier.containsKey(a) || !identifier.containsKey(b)) { // key(s) does not even exist; do nothing
            return;
        }

        if (identifier.get(a).equals(identifier.get(b))) { // already same; do nothing
            return;
        }

        int compOfA = identifier.get(a);
        int compOfB = identifier.get(b);
        for (T obj : identifier.keySet()) {
            if (identifier.get(obj).equals(compOfA)) {
                identifier.put(obj, compOfB);
            }
        }
    }

    /**
     * Retrieves all elements that are in the same component as the specified object. Not a typical operation
     * but here to illustrate other use case.
     * @param a
     * @return a list of objects
     */
    public List<T> retrieveFromSameComponent(T a) {
        List<T> ret = new ArrayList<>();
        for (T obj : identifier.keySet()) {
            if (find(a, obj)) {
                ret.add(obj);
            }
        }
        return ret;
    }
}
