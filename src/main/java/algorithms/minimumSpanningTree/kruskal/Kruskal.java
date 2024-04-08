package algorithms.minimumSpanningTree.kruskal;

import java.util.ArrayList;
import java.util.List;

import dataStructures.disjointSet.weightedUnion.DisjointSet;

/**
 * Implementation of Kruskal's Algorithm to find MSTs
 * Idea:
 *  Sort all edges by weight in non-decreasing order. Consider the edges in this order. If an edge does not form a cycle
 *  with the edges already in the MST, add it to the MST. Repeat until all nodes are in the MST.
 * Actual implementation:
 *  An Edge class is implemented for easier sorting of edges by weight and for identifying the source and destination.
 *  A Node class is implemented for easier tracking of nodes in the graph for the disjoint set.
 *  A DisjointSet class is used to track the nodes in the graph and to determine if adding an edge will form a cycle.
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

        // MST adjacency matrix to be returned
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

