package algorithms.graphs;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class dijsktraTest {

    @Test
    public void correctShortestPath() {
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

        int[] res = new int[]{0, 5, 3, 2, 3, 3};
        assert Arrays.equals(d.getDist(), res);
    }
}
