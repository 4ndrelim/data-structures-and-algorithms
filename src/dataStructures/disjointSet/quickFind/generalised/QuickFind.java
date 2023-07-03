package src.dataStructures.disjointSet.quickFind.generalised;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of quick-find algorithm; Turns a list of objects into a data structure that supports union operations
 * @param <T> generic type of object to be stored
 */
public class QuickFind<T> {
    private List<T> objects;
    private List<Integer> identity;

    public QuickFind() {
        this.objects = new ArrayList<T>();
        this.identity = new ArrayList<Integer>();
    }

    public QuickFind(List<T> input) {
        this.objects = input;
        this.identity = new ArrayList<Integer>();
        for (int i = 0; i < input.size(); i++) {
            this.identity.add(i);
        }
    }

    /**
     * Adds a new item into the existing list.
     * @param item to be added
     */
    public void add(T item) {
        this.objects.add(item);
        this.identity.add(this.identity.size()); // identity of the new item
    }

    /**
     * Merges the objects in two different components identified by a member from each.
     * @param objOne object in one of the components
     * @param objTwo object in another component
     */
    public void union(T objOne, T objTwo) {
        if (!this.objects.contains(objOne) || !this.objects.contains(objTwo)) {
            System.out.println("One or more of the objects do not exist!");
            return;
        }
        int idxOne = this.objects.indexOf(objOne);
        int compOne = this.identity.get(idxOne);
        int idxTwo = this.objects.indexOf(objTwo);
        int compTwo = this.identity.get(idxTwo);

        int size = this.objects.size();
        for (int i = 0; i < size; i++) {
            if (this.identity.get(i) == compOne) {
                this.identity.set(i, compTwo);
            }
        }
    }

    /**
     * Retrieves all elements in the same component as the given object.
     * @param objOne
     * @return list of elements.
     */
    public List<T> retrieveComponent(T objOne) {
        int idx = this.objects.indexOf(objOne);
        int comp = this.identity.get(idx);
        int size = this.objects.size();
        List<T> ret = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if (this.identity.get(i) == comp) {
                ret.add(this.objects.get(i));
            }
        }
        return ret;
    }
}
