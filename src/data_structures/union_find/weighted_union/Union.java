import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * Implementation of weighted-union algorithm; Turns a list of objects into a data structure that supports union operations.
 * Note that the commented out section below incorporates path compression to improve time complexity.
 * @param <T> generic type of object to be stored
 * Time: O(logn) without path compression.
 *       O(a) with path compression where a is the inverse Ackermann function.
 */
public class Union<T> {
    Map<T, Node<T>> items;

    public Union(List<T> items) {
        this.items = new HashMap<>();
        for (T item : items) {
            this.items.put(item, new Node<T>(item));
        }
    }

    public Union() {
        this.items = new HashMap<>();
    }

    public void add(T item) {
        if (items.containsKey(item)) {
            System.out.println("Item already exists!");
        }
        this.items.put(item, new Node<T>(item));
    }

    public void combine(T obj1, T obj2) {
        Node<T> node1 = items.get(obj1);
        Node<T> node2 = items.get(obj2);

        Node<T> p1 = node1.findParent();
        Node<T> p2 = node2.findParent();

        if (p1 == p2) {
            return; // already combined
        } else if (p1.getSize() > p2.getSize()) {
            p1.makeChild(p2);
        } else {
            p2.makeChild(p1);
        }
    }

    public boolean isSameComponent(T obj1, T obj2) {
        if (!items.containsKey(obj1) || !items.containsKey(obj2)) {
            System.out.println("Duuhh.. one or both of the items are not tracked.");
            return false;
        }
        return items.get(obj1).findParent() == items.get(obj2).findParent();
    }

    private static class Node<T> {
        private T val;
        private int size;
        private Node<T> parent;
    
        Node(T val) {
            this.val = val;
            this.size = 1;
            this.parent = this;
        }
    
        Node<T> findParent() {
            Node<T> trav = this;
            while (trav.parent != trav) {
                Node<T> curr = trav;
                trav = trav.parent;
                // start of path compression
                /**
                 * NOTE: we can correctly update the size of each subtree rooted at a node
                 * after each compression for consistency's sake,
                 * but doing so does not affect correctness of algorithm.
                 * 
                 * Because all the algorithm needs is the correct size of each subtree to decide on how to union
                 * and shifting descendants around does not affect size of subtree!
                 * trav.size -= curr.size
                 */
                curr.parent = curr.parent.parent;
                // end
            }
            return trav;
        }
    
        void makeChild(Node<T> child) {
            child.parent = this;
            this.size += child.size;
        }

        int getSize() {
            return this.size;
        }
    }
}

