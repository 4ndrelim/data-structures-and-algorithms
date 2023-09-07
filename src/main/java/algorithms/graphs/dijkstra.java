package algorithms.graphs;

import java.util.*;

/**
 *
 *
 */


public class dijkstra {

    private int dist[];
    private Set<Integer> visited;
    private PriorityQueue<Node> pq;

    private int V;
    List<List<Node>> adj;

    public dijkstra(int V, List<List<Node>> adj, int src) {
        // initialize var
        this.V = V;
        dist = new int[V];
        visited = new HashSet<Integer>();
        pq = new PriorityQueue<Node>(V, new Node());
        this.adj = adj;

        for (int i = 0; i <V; i++) {
            dist[i] = Integer.MAX_VALUE;
        }

        // Add source to pq
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

    public static void main(String[] args) {
        int V = 6;
        int src = 0;
        List<List<Node>> adj = new ArrayList<List<Node>>();

        for (int i = 0; i < V; i++) {
            List<Node> item = new ArrayList<Node>();
            adj.add(item);
        }
        adj.get(0).add(new Node(1, 5));
        adj.get(0).add(new Node(2, 3));
        adj.get(0).add(new Node(3, 2));
        adj.get(0).add(new Node(4, 3));
        adj.get(0).add(new Node(5, 3));
        adj.get(2).add(new Node(1, 2));
        adj.get(2).add(new Node(3, 3));

        dijkstra d = new dijkstra(V, adj, src);

        for (int i = 0; i < d.dist.length; i++) {
            System.out.println(d.dist[i]);
        }

    }

}



class Node implements Comparator<Node> {
    public int node;
    public int cost;

    public Node() {}

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
