package algorithms.minimumSpanningTree.prim;

/**
 * Node class to represent a node in the graph
 * Note: In our Node class, we do not allow the currMinWeight to be updated after initialization to prevent any
 * reference issues in the PriorityQueue.
 */
public class Node {
    private final int currMinWeight; // Current minimum weight to get to this node
    private int index; // Index of this node in the adjacency matrix
    private final String identifier;

    /**
     * Constructor for Node
     * @param identifier
     * @param index
     * @param currMinWeight
     */
    public Node(String identifier, int index, int currMinWeight) {
        this.identifier = identifier;
        this.index = index;
        this.currMinWeight = currMinWeight;
    }

    /**
     * Constructor for Node with default currMinWeight
     * @param identifier
     * @param index
     */
    public Node(String identifier, int index) {
        this.identifier = identifier;
        this.index = index;
        this.currMinWeight = Integer.MAX_VALUE;
    }

    /**
     * Getter and setter for currMinWeight
     */
    public int getCurrMinWeight() {
        return currMinWeight;
    }

    /**
     * Getter for identifier
     * @return identifier
     */
    public String getIdentifier() {
        return identifier;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return "Node{" + "identifier='" + identifier + '\'' + ", index=" + index + '}';
    }
}
