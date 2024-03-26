package algorithms.minimumSpanningTree.prims;

import java.util.Objects;

public class Node {
    private boolean isVisited = false;
    private int currMinWeight = Integer.MAX_VALUE; // Current minimum weight to get to this node
    private int index; // Index of this node in the adjacency matrix
    private String identifier;

    public Node(String identifier, int index) {
        this.identifier = identifier;
        this.index = index;
    }

    // Getter and setter for currMinWeight
    public int getCurrMinWeight() {
        return currMinWeight;
    }

    public void setCurrMinWeight(int currMinWeight) {
        this.currMinWeight = currMinWeight;
    }

    // Getter and setter for isVisited
    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean isVisited) {
        this.isVisited = isVisited;
    }

    // Getter for identifier
    public String getIdentifier() {
        return identifier;
    }

    // Getter and setter for index
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    // toString method
    @Override
    public String toString() {
        return "Node{" + "identifier='" + identifier + '\'' + ", index=" + index + '}';
    }

    // equals and hashCode based on identifier and index
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return index == node.index && Objects.equals(identifier, node.identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, index);
    }
}
