package algorithms.minimumSpanningTree.prims;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Implementation of Prim's Algorithm to find MSTs
 * Idea:
 *  Starting from any source (this will be the first node to be in the MST), pick the lightest outgoing edge, and
 *  include the node at the other end as part of a set of nodes S. Now repeatedly do the above by picking the lightest
 *  outgoing edge adjacent to any node in the MST (ensure the other end of the node is not already in the MST).
 *  Repeat until S contains all nodes in the graph. S is the MST.
 * Actual implementation:
 *  No Edge class was implemented. Instead, each node has a Map of adjacent nodes and their corresponding edge weights.
 *  To represent the MST, a new list of nodes is created with the same identifiers as the original graph, with each node
 *  containing only the edges in the MST.
 */
public class Prim {
    public static int[][] getPrimsMST(Node[] nodes, int[][] adjacencyMatrix) {
        PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> a.getCurrMinWeight() - b.getCurrMinWeight());
        int[][] mstMatrix = new int[nodes.length][nodes.length]; // MST adjacency matrix

        // Initialize the MST matrix to represent no edges with Integer.MAX_VALUE
        for (int i = 0; i < nodes.length; i++) {
            for (int j = 0; j < nodes.length; j++) {
                mstMatrix[i][j] = Integer.MAX_VALUE;
            }
        }

        // Initialize all nodes' currMinWeight to infinity, except the first node
        for (Node node : nodes) {
            node.setCurrMinWeight(Integer.MAX_VALUE);
            pq.add(node);
        }

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            current.setVisited(true);

            int currentIndex = current.getIndex();

            for (int i = 0; i < nodes.length; i++) {
                if (adjacencyMatrix[currentIndex][i] != Integer.MAX_VALUE && !nodes[i].isVisited()) {
                    int weight = adjacencyMatrix[currentIndex][i];

                    if (weight < nodes[i].getCurrMinWeight()) {
                        pq.remove(nodes[i]);
                        nodes[i].setCurrMinWeight(weight);
                        pq.add(nodes[i]);
                        // Update the MST matrix
                        mstMatrix[currentIndex][i] = weight;
                        mstMatrix[i][currentIndex] = weight; // For undirected graphs
                    }
                }
            }
        }
        return mstMatrix;
    }
}

