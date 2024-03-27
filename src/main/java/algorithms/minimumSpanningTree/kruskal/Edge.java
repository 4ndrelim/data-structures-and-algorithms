package algorithms.minimumSpanningTree.kruskal;

/**
 * Edge class to represent an edge in the graph
 */
public class Edge implements Comparable<Edge> {
    private final Node source;
    private final Node destination;
    private final int weight;

    /**
     * Constructor for Edge
     */
    public Edge(Node source, Node destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public Node getSource() {
        return source;
    }

    public Node getDestination() {
        return destination;
    }

    @Override
    public int compareTo(Edge other) {
        return Integer.compare(this.weight, other.weight);
    }
}
