package algorithms.minimumSpanningTree.kruskal;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of quick-find structure; Turns a list of objects into a data structure that supports union operations
 */
public class DisjointSet {
    private final Map<Node, String> identifier;

    /**
     * Constructor to initialize Disjoint Set with a known list of listOfNodes.
     * @param listOfNodes
     */
    public DisjointSet(Node[] listOfNodes) {
        identifier = new HashMap<>();
        for (Node currNode : listOfNodes) {
            // Component identifier is the same as the node's identifier
            identifier.put(currNode, currNode.getIdentifier());
        }
    }

    /**
     * Checks if object a and object b are in the same component.
     * @param a
     * @param b
     * @return a boolean value
     */
    public boolean find(Node a, Node b) {
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
    public void union(Node a, Node b) {
        if (!identifier.containsKey(a) || !identifier.containsKey(b)) { // key(s) does not even exist; do nothing
            return;
        }

        if (identifier.get(a).equals(identifier.get(b))) { // already same; do nothing
            return;
        }

        String compOfA = identifier.get(a);
        String compOfB = identifier.get(b);
        for (Node obj : identifier.keySet()) {
            if (identifier.get(obj).equals(compOfA)) {
                identifier.put(obj, compOfB);
            }
        }
    }
}
