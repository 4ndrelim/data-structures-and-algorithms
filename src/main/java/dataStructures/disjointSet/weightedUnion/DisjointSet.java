package dataStructures.disjointSet.weightedUnion;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Implementation of weighted-union structure;
 * Turns a list of objects into a data structure that supports union operations.
 * <p>
 * Note that implementation below includes path compression. Refer to README for more details
 *
 * @param <T> generic type of object to be stored
 */
public class DisjointSet<T> {
    private final Map<T, T> parents;
    private final Map<T, Integer> size;

    /**
     * Basic constructor to initialize Disjoint Set structure using weighted union concept.
     */
    public DisjointSet() {
        parents = new HashMap<>();
        size = new HashMap<>();
    }

    /**
     * Constructor to initialize Disjoint Set structure with a known list of objects.
     * @param objects
     */
    public DisjointSet(List<T> objects) {
        parents = new HashMap<>();
        size = new HashMap<>();
        for (int i = 0; i < objects.size(); i++) {
            T obj = objects.get(i);
            parents.put(obj, obj); // initially, every object forms a tree, with itself as the root
            size.put(obj, 1); // each tree has size 1 at the start
        }
    }

    /**
     * Internal helper method to find the root (identifier) of an object. Note that path compression has been included.
     * A point of concern might be performing path compression would require updating the sizes tracked by each node
     * for consistency's sake. But doing so does not affect correctness of algorithm.
     * Because all the algorithm needs is the correct size of each subtree to decide on how to union
     * and shifting descendants around does not affect size of subtree.
     * @param obj
     * @return the root of the subtree.
     */
    private T findRoot(T obj) {
        while (!obj.equals(parents.get(obj))) {
            T parent = parents.get(obj);
            // START OF PATH COMPRESSION
            T grandParent = parents.get(parent);
            parents.put(obj, grandParent); // powerful one-liner to reduce the height of trees every traversal
            // END
            obj = parent;
        }
        return obj;
    }

    public int size() {
        return parents.size();
    }

    /**
     * Adds an object into the structure.
     * @param obj
     */
    public void add(T obj) {
        parents.put(obj, obj);
        size.put(obj, 1);
    }

    /**
     * Checks if object a and object b are in the same component.
     * @param a
     * @param b
     * @return
     */
    public boolean find(T a, T b) {
        T rootOfA = findRoot(a);
        T rootOfB = findRoot(b);
        return rootOfA.equals(rootOfB);
    }

    /**
     * Merge the components of object a and object b.
     * @param a
     * @param b
     */
    public void union(T a, T b) {
        T rootOfA = findRoot(a);
        T rootOfB = findRoot(b);
        int sizeA = size.get(rootOfA);
        int sizeB = size.get(rootOfB);

        if (sizeA < sizeB) {
            parents.put(rootOfA, rootOfB); // update root A to be child of root B
            size.put(rootOfB, size.get(rootOfB) + size.get(rootOfA)); // update size of bigger tree
        } else {
            parents.put(rootOfB, rootOfA); // update root B to be child of root A
            size.put(rootOfA, size.get(rootOfA) + size.get(rootOfB)); // update size of bigger tree
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
        for (T obj : parents.keySet()) {
            if (find(a, obj)) {
                ret.add(obj);
            }
        }
        return ret;
    }
}
