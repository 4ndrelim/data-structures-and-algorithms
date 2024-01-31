package algorithms.minimumSpanningTree.prims;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

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
            (a, b) -> a.getCurrMinWeight() - b.getCurrMinWeight()
        );

        // Values in the map represent the corresponding node with only the edges in the MST
        Map<Node, Node> nodeToMSTNode = new HashMap<>();
        Map<Node, Node> parentInMST = new HashMap<>();

        // Initialize each node's minWeight to infinity and add to the priority queue
        for (Node node : graph) {
            node.setCurrMinWeight(Integer.MAX_VALUE);
            pq.add(node);
            nodeToMSTNode.put(node, new Node(node.getIdentifier())); // Create a corresponding MST node
            parentInMST.put(node, null);
        }

        // Assuming graph is not empty and the start node is the first node
        if (!graph.isEmpty()) {
            graph.get(0).setCurrMinWeight(0);
        }

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            current.setVisited(true);

            Map<Node, Integer> currentAdjacentNodes = current.getAdjacentNodes();

            for (Map.Entry<Node, Integer> entry : currentAdjacentNodes.entrySet()) {
                Node adjacent = entry.getKey();
                Integer weight = entry.getValue();

                if (!adjacent.isVisited() && weight < adjacent.getCurrMinWeight()) {
                    adjacent.setCurrMinWeight(weight);

                    // Update the parent in MST
                    parentInMST.put(adjacent, current);
                }
            }
        }

        // Construct the MST using the parent-child relationships
        for (Node originalNode : graph) {
            Node mstNode = nodeToMSTNode.get(originalNode);
            Node parent = parentInMST.get(originalNode);

            if (parent != null) {
                Node mstParent = nodeToMSTNode.get(parent);
                int weight = originalNode.getAdjacentNodes().get(parent);
                mstParent.getAdjacentNodes().put(mstNode, weight);
                mstNode.getAdjacentNodes().put(mstParent, weight); // For undirected graphs
            }
        }

        // Extract the nodes from the map to return them
        return new ArrayList<>(nodeToMSTNode.values());
    }
}



