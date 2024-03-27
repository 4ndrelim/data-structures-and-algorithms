package algorithms.minimumSpanningTree.kruskal;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of Prim's Algorithm to find MSTs
 * Idea:
 *  Starting from any source (this will be the first node to be in the MST), pick the lightest outgoing edge, and
 *  include the node at the other end as part of a set of nodes S. Now repeatedly do the above by picking the lightest
 *  outgoing edge adjacent to any node in the MST (ensure the other end of the node is not already in the MST).
 *  Repeat until S contains all nodes in the graph. S is the MST.
 * Actual implementation:
 *  No Edge class was implemented. Instead, the weights of the edges are stored in a 2D array adjacency matrix. An
 *  adjacency list may be used instead
 *  A Node class is implemented to encapsulate the current minimum weight to reach the node.
 */
public class Kruskal {
    public static int[][] getKruskalMST(Node[] nodes, int[][] adjacencyMatrix) {
        int numOfNodes = nodes.length;
        List<Edge> edges = new ArrayList<>();

        // Convert adjacency matrix to list of edges
        for (int i = 0; i < numOfNodes; i++) {
            for (int j = i + 1; j < numOfNodes; j++) {
                if (adjacencyMatrix[i][j] != Integer.MAX_VALUE) {
                    edges.add(new Edge(nodes[i], nodes[j], adjacencyMatrix[i][j]));
                }
            }
        }

        // Sort edges by weight
        edges.sort(Edge::compareTo);

        // Initialize Disjoint Set for vertex tracking
        DisjointSet ds = new DisjointSet(nodes);

        int[][] mstMatrix = new int[numOfNodes][numOfNodes];

        // Initialize the MST matrix to represent no edges with Integer.MAX_VALUE and 0 for self loops
        for (int i = 0; i < nodes.length; i++) {
            for (int j = 0; j < nodes.length; j++) {
                mstMatrix[i][j] = (i == j) ? 0 : Integer.MAX_VALUE;
            }
        }

        // Process edges to build MST
        for (Edge edge : edges) {
            Node source = edge.getSource();
            Node destination = edge.getDestination();
            if (!ds.find(source, destination)) {
                mstMatrix[source.getIndex()][destination.getIndex()] = edge.getWeight();
                mstMatrix[destination.getIndex()][source.getIndex()] = edge.getWeight();
                ds.union(source, destination);
            }
        }

        return mstMatrix;
    }
}

