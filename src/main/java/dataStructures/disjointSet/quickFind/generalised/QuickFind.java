package dataStructures.disjointSet.quickFind.generalised;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of quick-find structure; Turns a list of objects into a data structure that supports union operations
 *
 * @param <T> generic type of object to be stored
 */
public class QuickFind<T> {
    private final List<T> objects;
    private final List<Integer> identity;

    /**
     * TODO documentation
     */
    public QuickFind() {
        this.objects = new ArrayList<>();
        this.identity = new ArrayList<>();
    }

    /**
     * TODO documentation
     *
     * @param input
     */
    public QuickFind(List<T> input) {
        this.objects = input;
        this.identity = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            this.identity.add(i);
        }
    }

    /**
     * Adds a new item into the existing list.
     *
     * @param item to be added
     */
    public void add(T item) {
        objects.add(item);
        identity.add(identity.size()); // identity of the new item
    }

    /**
     * Merges the objects in two different components identified by a member from each.
     *
     * @param objOne object in one of the components
     * @param objTwo object in another component
     */
    public void union(T objOne, T objTwo) {
        if (!objects.contains(objOne) || !objects.contains(objTwo)) {
            System.out.println("One or more of the objects do not exist!");
            return;
        }
        int idxOne = objects.indexOf(objOne);
        int compOne = identity.get(idxOne);
        int idxTwo = objects.indexOf(objTwo);
        int compTwo = identity.get(idxTwo);

        int size = this.objects.size();
        for (int i = 0; i < size; i++) {
            if (identity.get(i) == compOne) {
                identity.set(i, compTwo);
            }
        }
    }

    /**
     * Retrieves all elements in the same component as the given object.
     *
     * @param objOne
     * @return list of elements.
     */
    public List<T> retrieveComponent(T objOne) {
        int idx = objects.indexOf(objOne);
        int comp = identity.get(idx);
        List<T> ret = new ArrayList<>();
        for (int i = 0; i < objects.size(); i++) {
            if (identity.get(i) == comp) {
                ret.add(objects.get(i));
            }
        }
        return ret;
    }
}
