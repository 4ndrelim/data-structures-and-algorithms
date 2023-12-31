package algorithms.minimumSpanningTree.prims;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Implementation of Prim's Algorithm to find MSTs
 * Idea: 
 *  Starting from any source (this will be the first node to be in the MST), 
 *  pick the lightest outgoing edge, and include the node at the other end as part of a set of nodes S.
 *
 *  Now repeatedly do the above by picking the lightest outgoing edge adjacent to any node in the MST.
 *  (ensuring the other end of the node is not already in the MST)
 *  Repeat until S contains all nodes in the graph. S is the MST.
 *
 * Actual implementation:
 *
 *
 * Motivating Example: Minimum Cost to Connect All Points
 * 
          A -9- C -2- E 
         /     /  \     \ 
        3     4    7     2
       /     /      \  /
      F -1- B  --5--  D 
*/

/**
 * Implementation 1: Using heap
 * Time: O(V) + O(ElogV) (since heap could possibly hold E number of weights) + O(E-V) (nodes that have been 'seen' are still added to the heap, just not expanded) + O(V^2)
 * Space: O(V) (hashmap to decide on MST) + O(E) (heap) = O(V+E) = O(E) 
 */

public class Prim {
//    /**
//     * points: Adjacency matrix that encapsulates the distance/weight between nodes
//     *         adjM[i][j] is the weight of the edge connecting points i and j; a value of 0 suggests there is no connection between i and j
//     * @param adjM Adjacency matrix that encapsulates the distance/weight between nodes
//     * @return minimum weight of the spanning tree
//     */
//    public int minCostConnectPoints(int[][] adjM) {
//        int v = adjM.length;
//        int minCost = 0;
//        Set<Integer> mst = new HashSet<>();
//        mst.add(0);
//        PriorityQueue<Edge> pq = new PriorityQueue<>(
//                (a, b) -> a.dist - b.dist
//        );
//        for (int i = 0; i < v; i++) {
//            if (!mst.contains(i)) {
//                if (adjM[0][i] != 0) { // ensure valid edge
//                    pq.add(new Edge(adjM[0][i], i));
//                }
//            }
//        }
//        while (mst.size() != v) {
//            Edge popped = pq.poll();
//            if (mst.contains(popped.endNode)) {
//                continue;
//            }
//            minCost += popped.dist;
//            mst.add(popped.endNode);
//            for (int i = 0; i < v; i++) {
//                if (mst.contains(i)) {
//                    continue;
//                }
//                if (adjM[popped.endNode][i] != 0) { // ensure valid edge
//                    pq.add(new Edge(adjM[popped.endNode][i], i));
//                }
//            }
//        }
//        return minCost;
//    }
//
//    /**
//     * Alternative implementation that simply uses array to hold weights rather than heap.
//     * Note: Starts from the node labelled 0 and repeatedly update <weights>
//     * which stores the minimum weight from any node in the MST to other nodes.
//     * Time: O(V) + O(V*2V)
//     * Space: O(V)
//     * @param adjM Adjacency matrix that encapsulates the distance/weight between nodes
//     * @return minimum weight of the spanning tree
//     */
//    public int minCostConnectPoints2(int[][] adjM) {
//        int v = adjM.length;
//        int[] weights = new int[v];
//
//        for (int i = 0; i < v; i++) {
//            weights[i] = adjM[0][i];
//        }
//
//        Set<Integer> mst = new HashSet<>();
//        mst.add(0); // start from source 0
//        int ans = 0;
//        while (mst.size() < v) {
//            int next = v;
//            for (int i = 0; i < v; i++) {
//                if (!mst.contains(i)) {
//                    if (weights[i] != 0 && (next == v || weights[i] < weights[next])) { // first check for valid connection, then try to find min weight
//                        next = i;
//                    }
//                }
//            }
//            mst.add(next);
//            ans += weights[next];
//
//            for (int i = 0; i < v; i++) {
//                if (!mst.contains(i)) {
//                    if (weights[i] == 0 || adjM[next][i] < weights[i]) { // update shortest dist to nodes that are not added to mst yet
//                        weights[i] = adjM[next][i];
//                    }
//                }
//            }
//        }
//        return ans;
//    }

    public static List<Node> getPrimsMST(List<Node> graph) {
        PriorityQueue<Node> pq = new PriorityQueue<>(
                (a, b) -> a.getCurrMinDistance() - b.getCurrMinDistance()
        );

        // Values in the map represent the corresponding node with only the edges in the MST
        Map<Node, Node> nodeToMSTNode = new HashMap<>();

        // mstEdge map required to track the edges in the MST. The corresponding node is the start node of the edge.
        Map<Edge, Node> mstEdge = new HashMap<>();

        // Initialize each node's minDistance to infinity and add to the priority queue
        for (Node node : graph) {
            node.setCurrMinDistance(Integer.MAX_VALUE);
            pq.add(node);
            nodeToMSTNode.put(node, new Node()); // Create a corresponding MST node
        }

        // Assuming graph is not empty and the start node is the first node
        if (!graph.isEmpty()) {
            graph.get(0).setCurrMinDistance(0);
        }

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            current.setVisited(true);

            for (Edge edge : current.getEdges()) {
                Node adjacent = edge.getEndNode();
                if (!adjacent.isVisited() && edge.getWeight() < adjacent.getCurrMinDistance()) {
                    pq.remove(adjacent);
                    adjacent.setCurrMinDistance(edge.getWeight());
                    pq.add(adjacent);
                    mstEdge.put(edge, current); // Update the lightest valid outgoing edge
                }
            }
        }

        // Populate the MST nodes with the edges to be included in the MST
        for (Edge edge : mstEdge.keySet()) {
            Node start = mstEdge.get(edge);
            nodeToMSTNode.get(start).addEdge(edge);
        }

        // Extract the nodes from the map to return them
        return new ArrayList<>(nodeToMSTNode.values());
    }
}



