package algorithms.minimumSpanningTree.kruskal;

import java.util.ArrayList;
import java.util.List;

import dataStructures.disjointSet.weightedUnion.DisjointSet;

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
        DisjointSet<Node> ds = new DisjointSet<>(nodes);

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

    /**
     * Node class to represent a node in the graph
     * Note: In our Node class, we do not allow the currMinWeight to be updated after initialization to prevent any
     * reference issues in the PriorityQueue.
     */
    static class Node {
        private final int index; // Index of this node in the adjacency matrix
        private final String identifier;

        /**
         * Constructor for Node
         * @param identifier
         * @param index
         */
        public Node(String identifier, int index) {
            this.identifier = identifier;
            this.index = index;
        }

        /**
         * Getter for identifier
         * @return identifier
         */
        public String getIdentifier() {
            return identifier;
        }

        public int getIndex() {
            return index;
        }

        @Override
        public String toString() {
            return "Node{" + "identifier='" + identifier + '\'' + ", index=" + index + '}';
        }
    }

    /**
     * Edge class to represent an edge in the graph
     */
    static class Edge implements Comparable<Edge> {
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
}

