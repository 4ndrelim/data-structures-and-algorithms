package algorithms.minimumSpanningTree.prims;

/**
 * Helper class to encapsulate information about an edge;
 * specifically, tells us the weight of an edge and the node on the other end
 * (assumes that the starting node of the edge is known)
 */
public class Pair {
    private int dist;
    private int endNode;

    /**
     * Constructs an instance of a pair, that represents an edge.
     *
     * @param dist    the distance encapsulated.
     * @param endNode the node at the end of the edge.
     */
    public Pair(int dist, int endNode) {
        this.dist = dist;
        this.endNode = endNode;
    }

    public int getDist() {
        return dist;
    }

    public void setDist(int dist) {
        this.dist = dist;
    }

    public int getEndNode() {
        return endNode;
    }

    public void setEndNode(int endNode) {
        this.endNode = endNode;
    }
}
