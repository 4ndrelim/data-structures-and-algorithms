package algorithms.minimumSpanningTree.prim;

import java.util.Arrays;
import java.util.PriorityQueue;

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
public class Prim {
    public static int[][] getPrimsMST(Node[] nodes, int[][] adjacencyMatrix) {
        // Recall that PriorityQueue is a min heap by default
        PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> a.getCurrMinWeight() - b.getCurrMinWeight());
        int[][] mstMatrix = new int[nodes.length][nodes.length]; // MST adjacency matrix

        int[] parent = new int[nodes.length]; // To track the parent node of each node in the MST
        Arrays.fill(parent, -1); // Initialize parent array with -1, indicating no parent

        boolean[] visited = new boolean[nodes.length]; // To track visited nodes
        Arrays.fill(visited, false); // Initialize visited array with false, indicating not visited

        // Initialize the MST matrix to represent no edges with Integer.MAX_VALUE and 0 for self loops
        for (int i = 0; i < nodes.length; i++) {
            for (int j = 0; j < nodes.length; j++) {
                mstMatrix[i][j] = (i == j) ? 0 : Integer.MAX_VALUE;
            }
        }

        // Add all nodes to the priority queue, with each node's curr min weight already set to Integer.MAX_VALUE
        pq.addAll(Arrays.asList(nodes));

        while (!pq.isEmpty()) {
            Node current = pq.poll();

            int currentIndex = current.getIndex();

            if (visited[currentIndex]) { // Skip if node is already visited
                continue;
            }

            visited[currentIndex] = true;

            for (int i = 0; i < nodes.length; i++) {
                if (adjacencyMatrix[currentIndex][i] != Integer.MAX_VALUE && !visited[nodes[i].getIndex()]) {
                    int weight = adjacencyMatrix[currentIndex][i];

                    if (weight < nodes[i].getCurrMinWeight()) {
                        Node newNode = new Node(nodes[i].getIdentifier(), nodes[i].getIndex(), weight);
                        parent[i] = currentIndex; // Set current node as parent of adjacent node
                        pq.add(newNode);
                    }
                }
            }
        }

        // Build MST matrix based on parent array
        for (int i = 1; i < nodes.length; i++) {
            int p = parent[i];
            if (p != -1) {
                int weight = adjacencyMatrix[p][i];
                mstMatrix[p][i] = weight;
                mstMatrix[i][p] = weight; // For undirected graphs
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
        private final int currMinWeight; // Current minimum weight to get to this node
        private int index; // Index of this node in the adjacency matrix
        private final String identifier;

        /**
         * Constructor for Node
         * @param identifier
         * @param index
         * @param currMinWeight
         */
        public Node(String identifier, int index, int currMinWeight) {
            this.identifier = identifier;
            this.index = index;
            this.currMinWeight = currMinWeight;
        }

        /**
         * Constructor for Node with default currMinWeight
         * @param identifier
         * @param index
         */
        public Node(String identifier, int index) {
            this.identifier = identifier;
            this.index = index;
            this.currMinWeight = Integer.MAX_VALUE;
        }

        /**
         * Getter and setter for currMinWeight
         */
        public int getCurrMinWeight() {
            return currMinWeight;
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
}

