import java.util.*;

/** 
 * Implementation of Prim's Algorithm to find MSTs
 * Idea: 
 *  Starting from any source (first node to be in the MST), 
 *  pick the lighest outgoing edge, and include the node at the other end as part of MST
 *  Now repeatedly do the above by picking the lighest outgoing edge adjacent to any node in the MST
 *  (ensuring the other end of the node is not already in the MST)
 *  Do until the MST has all the nodes.
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
 * Time: O(VlogE + ElogE) // node heap could possibly hold E number of items since 
 * Space: O(V) (hashmap to decide on MST) + O(E) (heap) = O(V+E) = O(E) 
 */

class prims {
    public int minCostConnectPoints(int[][] adjM) {
        /*
        * points: Adjacency matrix that encapsulates the distance/weight between nodes
        * NOTE: adjM[i][j] is the weight of the edge connecting points i and j
        * A value of 0 suggests there is no connection between i and j
        */
        int v = adjM.length;
        int minCost = 0;
        Set<Integer> mst = new HashSet<>();
        mst.add(0);
        PriorityQueue<Pair> pq = new PriorityQueue<>(
            (a, b) -> a.dist - b.dist
        );
        for (int i = 0; i < v; i++) {
            if (!mst.contains(i)) {
                if (adjM[0][i] != 0) { // ensure valid edge
                    pq.add(new Pair(adjM[0][i], i));
                }
            }
        }
        while (mst.size() != v) {
            Pair popped = pq.poll();
            if (mst.contains(popped.endNode)) {
                continue;
            }
            minCost += popped.dist;
            mst.add(popped.endNode);
            for (int i = 0; i < v; i++) {
                if (mst.contains(i)) {
                    continue;
                }
                if (adjM[popped.endNode][i] != 0) { // ensure valid edge
                    pq.add(new Pair(adjM[popped.endNode][i], i));
                }
            }
        }
        return minCost;
    }
}

