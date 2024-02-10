package algorithms.minimumSpanningTree.prims;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Node class for Prim's algorithm.
 *
 * Each node has a unique identifier and contains a Map of adjacent nodes and their corresponding edge weights.
 */
public class Node {
    private boolean isVisited = false;
    private int currMinWeight; // Current minimum weight to get to this node to calculate Prim's MST
    private final Map<Node, Integer> adjacentNodes;
    private String identifier;

    /**
     * Constructor for a node with no adjacent nodes.
     *
     * @param identifier Unique identifier for the node
     */
    public Node(String identifier) {
        this.adjacentNodes = new HashMap<>();
        this.identifier = identifier;
    }

    /**
     * Constructor for a node with a list of adjacent nodes.
     *
     * @param identifier    Unique identifier for the node
     * @param adjacentNodes List of adjacent nodes
     */
    public Node(String identifier, Map<Node, Integer> adjacentNodes) {
        this.identifier = identifier;
        this.adjacentNodes = adjacentNodes;
    }

    public Map<Node, Integer> getAdjacentNodes() {
        return adjacentNodes;
    }

    public int getCurrMinWeight() {
        return currMinWeight;
    }

    public void setCurrMinWeight(int currMinWeight) {
        this.currMinWeight = currMinWeight;
    }

    public boolean isVisited() {
        return this.isVisited;
    }

    public void setVisited(boolean isVisited) {
        this.isVisited = isVisited;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public String toString() {
        return "Node{"
                + "identifier='"
                + identifier + '\''
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Node node = (Node) o;
        return Objects.equals(identifier, node.identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier);
    }
}
