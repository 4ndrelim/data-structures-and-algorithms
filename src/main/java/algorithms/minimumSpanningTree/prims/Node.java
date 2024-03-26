package algorithms.minimumSpanningTree.prims;

import java.util.Objects;

/**
 * Node class to represent a node in the graph
 */
public class Node {
    private boolean isVisited = false;
    private int currMinWeight = Integer.MAX_VALUE; // Current minimum weight to get to this node
    private int index; // Index of this node in the adjacency matrix
    private final String identifier;

    /**
     * Constructor
     * @param identifier
     * @param index
     */
    public Node(String identifier, int index) {
        this.identifier = identifier;
        this.index = index;
    }

    /**
     * Getter and setter for currMinWeight
     */
    public int getCurrMinWeight() {
        return currMinWeight;
    }

    public void setCurrMinWeight(int currMinWeight) {
        this.currMinWeight = currMinWeight;
    }

    /**
     * Getter for identifier
     * @return identifier
     */
    public boolean isVisited() {
        return isVisited;
    }

    /**
     * Setter for isVisited
     * @param isVisited
     */
    public void setVisited(boolean isVisited) {
        this.isVisited = isVisited;
    }

    /**
     * Getter for identifier
     * @return identifier
     */
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "Node{" + "identifier='" + identifier + '\'' + ", index=" + index + '}';
    }
}
