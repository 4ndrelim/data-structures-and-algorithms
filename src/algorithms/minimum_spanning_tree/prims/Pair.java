/**
 * Helper class to encapsulate information about an edge; 
 * specifically, tells us the wieght of an edge and the node on the other end
 * (assumes that the starting node of the edge is known)
 */
class Pair {
    int dist;
    int endNode;
    public Pair(int dist, int endNode) {
        this.dist = dist;
        this.endNode = endNode;
    }
}