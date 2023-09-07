package algorithms.graphs;

import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.Set;


/**
 * Implementation of Dijkstra's Algorithm
 *
 * Dijkstra's algorithm is a graph traversal and shortest path-finding algorithm that finds the
 * shortest path between a designated starting node and all other nodes in a weighted graph.
 * It is particularly useful for finding the shortest distance in networks, such as road networks,
 * computer networks, or any other interconnected systems with weighted edges.
 *
 * In general, Dijkstra's algorithm works as follows:
 * - Initialize a distance array to store the shortest distances from the starting node
 * to all other nodes.
 * Set the distance to the starting node as 0 and all other distances to infinity.
 * - Create a priority queue or min-heap to maintain the nodes to be explored, initially containing
 * only the starting node.
 * - While the priority queue is not empty:
 * - Extract the node with the smallest distance from the priority queue.
 * - For each neighbor of the extracted node:
 * - Calculate the distance to the neighbor through the extracted node.
 * - If this distance is smaller than the previously recorded distance to the neighbor,
 * update the distance and add the neighbor to the priority queue.
 * - After the algorithm completes, the distance array will contain the shortest distances from the
 * starting node to all other nodes.
 *
 * Time: O(V^2) for a naive implementation using an adjacency matrix, where V is the number of
 * vertices/nodes.
 * O(E + V*log(V)) for a more efficient implementation using a priority queue or heap,
 * where E is the number of edges in the graph.
 * Explanation: The time complexity depends on the data structure used to represent the graph.
 * In the case of an adjacency matrix, it takes O(V^2) time to find the minimum distance vertex in
 * each iteration, resulting in a total time complexity of O(V^3).
 * With a priority queue or heap, we can reduce the time complexity to O(E + V*log(V)).
 *
 * Space: O(V) for storing the distance array and a set of visited nodes.
 * O(V + E) for storing the graph, where V is the number of vertices and E is the number of edges.
 * O(V) for the priority queue or heap.
 *
 * Note: Dijkstra's algorithm works only with non-negative edge weights. It may not give correct
 * results in the presence of negative edge weights. If negative edge weights are involved,
 * consider using algorithms like Bellman-Ford.
 */


public class dijkstra {

    private int dist[];
    private Set<Integer> visited;
    private PriorityQueue<Node> pq;

    private int V;
    List<List<Node>> adj;

    public dijkstra(int V, List<List<Node>> adj, int src) {
        this.V = V;
        dist = new int[V];
        visited = new HashSet<Integer>();
        pq = new PriorityQueue<Node>(V, new Node());
        this.adj = adj;

        for (int i = 0; i < V; i++) {
            dist[i] = Integer.MAX_VALUE;
        }

        pq.add(new Node(src, 0));
        dist[src] = 0;
        while (visited.size() != V) {
            int u = pq.remove().node;
            visited.add(u);
            processAdjacent(u);
        }
    }


    private void processAdjacent(int u) {
        int edgeDist = -1;
        int newDist = -1;

        for (int i = 0; i < adj.get(u).size(); i++) {
            Node v = adj.get(u).get(i);
            if (!visited.contains(v.node)) {
                edgeDist = v.cost;
                newDist = dist[u] + edgeDist;

                if (newDist < dist[v.node]) {
                    dist[v.node] = newDist;
                }
                pq.add(new Node(v.node, dist[v.node]));
            }
        }
    }

    public int[] getDist() {
        return dist;
    }
}


class Node implements Comparator<Node> {
    public int node;
    public int cost;

    public Node() {
    }

    public Node(int node, int cost) {
        this.node = node;
        this.cost = cost;
    }

    @Override
    public int compare(Node n1, Node n2) {
        if (n1.cost < n2.cost) {
            return -1;
        }

        if (n1.cost > n2.cost) {
            return 1;
        }

        return 0;
    }
}
