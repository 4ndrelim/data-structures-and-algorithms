package algorithms.minimumSpanningTree.prims;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Node {
    private boolean isVisited = false;
    private int currMinWeight;
    private Map<Node, Integer> adjacentNodes;
    private String identifier; // Unique identifier for the node

    public Node(String identifier) {
        this.adjacentNodes = new HashMap<>();
        this.identifier = identifier;
    }

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
        return "Node{" +
                "identifier='" + identifier + '\'' +
                ", adjacentNodes=" + adjacentNodes +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return isVisited == node.isVisited &&
                Objects.equals(identifier, node.identifier) &&
                Objects.equals(adjacentNodes, node.adjacentNodes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, adjacentNodes, isVisited);
    }
}
