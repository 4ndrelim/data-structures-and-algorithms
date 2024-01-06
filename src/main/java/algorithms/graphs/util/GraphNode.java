package algorithms.graphs.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Encapsulates a Graph Node.
 *
 * @param <T> the Generic type stored in the node.
 */
public class GraphNode<T> {
    private final T val;
    private final List<GraphNode<T>> neighbour;

    /**
     * Constructs a GraphNode.
     *
     * @param val the value to be encapsulated by the node.
     */
    public GraphNode(T val) {
        this.val = val;
        neighbour = new ArrayList<>();
    }

    /**
     * Constructs a GraphNode with edges.
     *
     * @param val        the value to be encapsulated.
     * @param neighbours the neighbours of the node.
     */
    public GraphNode(T val, List<GraphNode<T>> neighbours) {
        this.val = val;
        this.neighbour = neighbours;
    }

    /**
     * Connects two nodes with an edge.
     *
     * @param a   the node to be connected.
     * @param b   the other node to be connected.
     * @param <T> the generic type of the nodes to be connected.
     */
    public static <T> void connect(GraphNode<T> a, GraphNode<T> b) {
        a.neighbour.add(b);
        b.neighbour.add(a);
    }

    /**
     * Returns the neighbours of this node.
     *
     * @return the neighbours of this node.
     */
    public List<GraphNode<T>> neighbours() {
        return this.neighbour;
    }

    public T getVal() {
        return this.val;
    }

    @Override
    public String toString() {
        return String.valueOf(this.val);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof GraphNode)) {
            return false;
        }
        // Safe cast.
        @SuppressWarnings("unchecked")
        GraphNode<T> node = (GraphNode<T>) other;
        return this.val == node.val;
    }

    @Override
    public int hashCode() {
        return val.hashCode();
    }
}
