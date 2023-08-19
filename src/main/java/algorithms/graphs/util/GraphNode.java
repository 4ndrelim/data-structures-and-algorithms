package algorithms.graphs.util;

import java.util.*;

public class GraphNode <T> {
    public T val;
    public List<GraphNode> neighbour;

    public GraphNode(T val) {
        this.val = val;
        neighbour = new ArrayList<>();
    }

    public GraphNode(T val, List<GraphNode> neighbours) {
        this.val = val;
        this.neighbour = neighbours;
    }

    public static <T> void connect(GraphNode<T> a, GraphNode<T> b) {
        a.neighbour.add(b);
        b.neighbour.add(a);
    }


    public List<GraphNode> neighbours() { return this.neighbour; }

    public T getVal() { return this.val; }

    @Override
    public String toString() { return String.valueOf(this.val); }

    @Override
    public boolean equals(Object other) {
        if (other == this) { return true; }
        if (!(other instanceof GraphNode)) { return false; }
        GraphNode node = (GraphNode) other;
        return this.val == node.val;
    }

    @Override
    public int hashCode() {
        return val.hashCode();
    }
}
