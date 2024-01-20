package algorithms.minimumSpanningTree.prims;

/**
 * Helper class to encapsulate information about an edge;
 * specifically, tells us the weight of an edge and the node on the other end
 * (assumes that the starting node of the edge is known)
 */
public class Edge {
    private final int weight;
    private final Node endNode;

    /**
     * @param weight  Weight of the edge
     * @param endNode Node on the other end of the edge
     */
    public Edge(int weight, Node endNode) {
        this.weight = weight;
        this.endNode = endNode;
    }

    public Node getEndNode() {
        return this.endNode;
    }

    public int getWeight() {
        return this.weight;
    }
}
