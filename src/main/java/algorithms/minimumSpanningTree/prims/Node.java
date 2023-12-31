package algorithms.minimumSpanningTree.prims;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Helper class to encapsulate information about a node;
 * specifically, tells us the weight of an edge and the node on the other end
 * (assumes that the starting node of the edge is known)
 */
public class Node {
    private final List<Edge> edges;
    private boolean isVisited = false;
    private int currMinDistance;

    public Node(List<Edge> edges) {
        this.edges = new ArrayList<>(edges);
    }
    public Node() {
        this.edges = new ArrayList<>();
    }

    public List<Edge> getEdges() {
        return this.edges;
    }

    public int getCurrMinDistance() {
        return currMinDistance;
    }

    public void setVisited(boolean isVisited) {
        this.isVisited = isVisited;
    }

    public boolean isVisited() {
        return this.isVisited;
    }

    public void setCurrMinDistance(int currMinDistance) {
        this.currMinDistance = currMinDistance;
    }

    public String toString() {
        return this.edges.toString();
    }

    public void addEdge(Edge mstEdge) {
        this.edges.add(mstEdge);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return isVisited == node.isVisited && edges.equals(node.edges);
    }

    @Override
    public int hashCode() {
        return Objects.hash(edges, isVisited);
    }
}
