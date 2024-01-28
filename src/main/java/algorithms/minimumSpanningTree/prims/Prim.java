package algorithms.minimumSpanningTree.prims;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class Prim {
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



