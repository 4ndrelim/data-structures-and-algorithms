package algorithms.graph.dijkstra;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class DijkstraTest {

    private static final int INF = Integer.MAX_VALUE;

    // ==================== Basic shortest path tests ====================

    @Test
    public void test_simpleGraph() {
        // 0 --5--> 1 --2--> 2
        int[][] edges = {
            {0, 1, 5},
            {1, 2, 2}
        };
        List<List<Dijkstra.Edge>> graph = Dijkstra.buildGraph(3, edges);

        Dijkstra.Result result = Dijkstra.shortestPath(3, graph, 0);

        Assert.assertEquals(0, result.distances[0]);
        Assert.assertEquals(5, result.distances[1]);
        Assert.assertEquals(7, result.distances[2]); // 0->1->2 = 5+2
    }

    @Test
    public void test_graphFromReadme() {
        // Graph from README walkthrough
        // 0 --4--> 1 --1--> 3
        // |        |
        // 2        2
        // v        v
        // 2 --3--> 4
        int[][] edges = {
            {0, 1, 4},
            {0, 2, 2},
            {1, 3, 1},
            {1, 4, 2},
            {2, 4, 3}
        };
        List<List<Dijkstra.Edge>> graph = Dijkstra.buildGraph(5, edges);

        Dijkstra.Result result = Dijkstra.shortestPath(5, graph, 0);

        Assert.assertEquals(0, result.distances[0]);
        Assert.assertEquals(4, result.distances[1]);
        Assert.assertEquals(2, result.distances[2]);
        Assert.assertEquals(5, result.distances[3]); // 0->1->3 = 4+1
        Assert.assertEquals(5, result.distances[4]); // 0->2->4 = 2+3
    }

    @Test
    public void test_multiplePathsChoosesShortest() {
        // 0 --10--> 2
        // 0 --3--> 1 --2--> 2
        // Shortest to 2: via 1 (3+2=5), not direct (10)
        int[][] edges = {
            {0, 2, 10},
            {0, 1, 3},
            {1, 2, 2}
        };
        List<List<Dijkstra.Edge>> graph = Dijkstra.buildGraph(3, edges);

        Dijkstra.Result result = Dijkstra.shortestPath(3, graph, 0);

        Assert.assertEquals(5, result.distances[2]); // Via 1, not direct
    }

    @Test
    public void test_bidirectionalEdges() {
        // Undirected graph: 0 <--3--> 1 <--2--> 2
        int[][] edges = {
            {0, 1, 3}, {1, 0, 3},
            {1, 2, 2}, {2, 1, 2}
        };
        List<List<Dijkstra.Edge>> graph = Dijkstra.buildGraph(3, edges);

        Dijkstra.Result result = Dijkstra.shortestPath(3, graph, 0);

        Assert.assertEquals(0, result.distances[0]);
        Assert.assertEquals(3, result.distances[1]);
        Assert.assertEquals(5, result.distances[2]); // 0->1->2
    }

    @Test
    public void test_differentSource() {
        // 0 --1--> 1 --2--> 2
        int[][] edges = {
            {0, 1, 1},
            {1, 2, 2}
        };
        List<List<Dijkstra.Edge>> graph = Dijkstra.buildGraph(3, edges);

        // Start from vertex 1
        Dijkstra.Result result = Dijkstra.shortestPath(3, graph, 1);

        Assert.assertEquals(INF, result.distances[0]); // Can't reach 0 from 1
        Assert.assertEquals(0, result.distances[1]);
        Assert.assertEquals(2, result.distances[2]);
    }

    // ==================== Edge cases ====================

    @Test
    public void test_singleVertex() {
        List<List<Dijkstra.Edge>> graph = Dijkstra.buildGraph(1, new int[][] {});

        Dijkstra.Result result = Dijkstra.shortestPath(1, graph, 0);

        Assert.assertEquals(0, result.distances[0]);
    }

    @Test
    public void test_disconnectedGraph() {
        // 0 --> 1    2 (isolated)
        int[][] edges = {{0, 1, 5}};
        List<List<Dijkstra.Edge>> graph = Dijkstra.buildGraph(3, edges);

        Dijkstra.Result result = Dijkstra.shortestPath(3, graph, 0);

        Assert.assertEquals(0, result.distances[0]);
        Assert.assertEquals(5, result.distances[1]);
        Assert.assertEquals(INF, result.distances[2]); // Unreachable
    }

    @Test
    public void test_selfLoop() {
        // 0 --5--> 0 (self-loop), 0 --1--> 1
        int[][] edges = {
            {0, 0, 5},
            {0, 1, 1}
        };
        List<List<Dijkstra.Edge>> graph = Dijkstra.buildGraph(2, edges);

        Dijkstra.Result result = Dijkstra.shortestPath(2, graph, 0);

        Assert.assertEquals(0, result.distances[0]); // Self-loop doesn't help
        Assert.assertEquals(1, result.distances[1]);
    }

    @Test
    public void test_parallelEdges() {
        // Multiple edges between same vertices
        // 0 --5--> 1
        // 0 --3--> 1 (shorter)
        int[][] edges = {
            {0, 1, 5},
            {0, 1, 3}
        };
        List<List<Dijkstra.Edge>> graph = Dijkstra.buildGraph(2, edges);

        Dijkstra.Result result = Dijkstra.shortestPath(2, graph, 0);

        Assert.assertEquals(3, result.distances[1]); // Takes shorter edge
    }

    @Test
    public void test_zeroWeightEdge() {
        // 0 --0--> 1 --1--> 2
        int[][] edges = {
            {0, 1, 0},
            {1, 2, 1}
        };
        List<List<Dijkstra.Edge>> graph = Dijkstra.buildGraph(3, edges);

        Dijkstra.Result result = Dijkstra.shortestPath(3, graph, 0);

        Assert.assertEquals(0, result.distances[0]);
        Assert.assertEquals(0, result.distances[1]);
        Assert.assertEquals(1, result.distances[2]);
    }

    // ==================== shortestDistance tests ====================

    @Test
    public void test_shortestDistance_exists() {
        int[][] edges = {
            {0, 1, 4},
            {0, 2, 2},
            {1, 3, 1},
            {2, 3, 5}
        };
        List<List<Dijkstra.Edge>> graph = Dijkstra.buildGraph(4, edges);

        int dist = Dijkstra.shortestDistance(4, graph, 0, 3);

        Assert.assertEquals(5, dist); // 0->1->3 = 4+1
    }

    @Test
    public void test_shortestDistance_noPath() {
        int[][] edges = {{0, 1, 1}};
        List<List<Dijkstra.Edge>> graph = Dijkstra.buildGraph(3, edges);

        int dist = Dijkstra.shortestDistance(3, graph, 0, 2);

        Assert.assertEquals(INF, dist);
    }

    @Test
    public void test_shortestDistance_sameVertex() {
        int[][] edges = {{0, 1, 1}};
        List<List<Dijkstra.Edge>> graph = Dijkstra.buildGraph(2, edges);

        int dist = Dijkstra.shortestDistance(2, graph, 0, 0);

        Assert.assertEquals(0, dist);
    }

    // ==================== Path reconstruction tests ====================

    @Test
    public void test_pathReconstruction() {
        // 0 --1--> 1 --2--> 2 --3--> 3
        int[][] edges = {
            {0, 1, 1},
            {1, 2, 2},
            {2, 3, 3}
        };
        List<List<Dijkstra.Edge>> graph = Dijkstra.buildGraph(4, edges);

        Dijkstra.Result result = Dijkstra.shortestPath(4, graph, 0);
        List<Integer> path = Dijkstra.reconstructPath(result.parents, 0, 3);

        Assert.assertEquals(Arrays.asList(0, 1, 2, 3), path);
    }

    @Test
    public void test_pathReconstructionDirectEdge() {
        int[][] edges = {{0, 1, 5}};
        List<List<Dijkstra.Edge>> graph = Dijkstra.buildGraph(2, edges);

        Dijkstra.Result result = Dijkstra.shortestPath(2, graph, 0);
        List<Integer> path = Dijkstra.reconstructPath(result.parents, 0, 1);

        Assert.assertEquals(Arrays.asList(0, 1), path);
    }

    @Test
    public void test_pathReconstructionSameVertex() {
        int[][] edges = {{0, 1, 1}};
        List<List<Dijkstra.Edge>> graph = Dijkstra.buildGraph(2, edges);

        Dijkstra.Result result = Dijkstra.shortestPath(2, graph, 0);
        List<Integer> path = Dijkstra.reconstructPath(result.parents, 0, 0);

        Assert.assertEquals(Arrays.asList(0), path);
    }

    @Test
    public void test_pathReconstructionNoPath() {
        int[][] edges = {{0, 1, 1}};
        List<List<Dijkstra.Edge>> graph = Dijkstra.buildGraph(3, edges);

        Dijkstra.Result result = Dijkstra.shortestPath(3, graph, 0);
        List<Integer> path = Dijkstra.reconstructPath(result.parents, 0, 2);

        Assert.assertTrue(path.isEmpty());
    }

    @Test
    public void test_pathReconstructionChoosesShortest() {
        // Two paths to 2:
        // 0 --1--> 1 --1--> 2 (total: 2)
        // 0 --10--> 2 (total: 10)
        int[][] edges = {
            {0, 1, 1},
            {1, 2, 1},
            {0, 2, 10}
        };
        List<List<Dijkstra.Edge>> graph = Dijkstra.buildGraph(3, edges);

        Dijkstra.Result result = Dijkstra.shortestPath(3, graph, 0);
        List<Integer> path = Dijkstra.reconstructPath(result.parents, 0, 2);

        Assert.assertEquals(Arrays.asList(0, 1, 2), path); // Via 1, not direct
    }

    // ==================== Larger graph tests ====================

    @Test
    public void test_largerGraph() {
        // 6-vertex graph
        //     0
        //    /|\
        //   1 2 4
        //   |/ \|
        //   3   5
        int[][] edges = {
            {0, 1, 2}, {0, 2, 4}, {0, 4, 1},
            {1, 3, 3}, {2, 3, 1}, {2, 5, 5},
            {4, 5, 2}
        };
        List<List<Dijkstra.Edge>> graph = Dijkstra.buildGraph(6, edges);

        Dijkstra.Result result = Dijkstra.shortestPath(6, graph, 0);

        Assert.assertEquals(0, result.distances[0]);
        Assert.assertEquals(2, result.distances[1]);
        Assert.assertEquals(4, result.distances[2]);
        Assert.assertEquals(5, result.distances[3]); // 0->2->3 = 4+1
        Assert.assertEquals(1, result.distances[4]);
        Assert.assertEquals(3, result.distances[5]); // 0->4->5 = 1+2
    }

    @Test
    public void test_completeGraph() {
        // Complete graph K4 with varying weights
        int[][] edges = {
            {0, 1, 1}, {0, 2, 4}, {0, 3, 3},
            {1, 0, 1}, {1, 2, 2}, {1, 3, 5},
            {2, 0, 4}, {2, 1, 2}, {2, 3, 1},
            {3, 0, 3}, {3, 1, 5}, {3, 2, 1}
        };
        List<List<Dijkstra.Edge>> graph = Dijkstra.buildGraph(4, edges);

        Dijkstra.Result result = Dijkstra.shortestPath(4, graph, 0);

        Assert.assertEquals(0, result.distances[0]);
        Assert.assertEquals(1, result.distances[1]); // Direct
        Assert.assertEquals(3, result.distances[2]); // 0->1->2 = 1+2
        Assert.assertEquals(3, result.distances[3]); // 0->3 = 3 (direct is shortest)
    }

    @Test
    public void test_longChain() {
        // Linear chain: 0 -> 1 -> 2 -> 3 -> 4 -> 5
        int[][] edges = {
            {0, 1, 1}, {1, 2, 1}, {2, 3, 1}, {3, 4, 1}, {4, 5, 1}
        };
        List<List<Dijkstra.Edge>> graph = Dijkstra.buildGraph(6, edges);

        Dijkstra.Result result = Dijkstra.shortestPath(6, graph, 0);

        for (int i = 0; i < 6; i++) {
            Assert.assertEquals(i, result.distances[i]);
        }
    }

    // ==================== buildGraph utility tests ====================

    @Test
    public void test_buildGraph() {
        int[][] edges = {
            {0, 1, 5},
            {0, 2, 3},
            {1, 2, 1}
        };
        List<List<Dijkstra.Edge>> graph = Dijkstra.buildGraph(3, edges);

        Assert.assertEquals(3, graph.size());
        Assert.assertEquals(2, graph.get(0).size()); // 0 has edges to 1 and 2
        Assert.assertEquals(1, graph.get(1).size()); // 1 has edge to 2
        Assert.assertEquals(0, graph.get(2).size()); // 2 has no outgoing edges
    }

    @Test
    public void test_buildGraphEmpty() {
        List<List<Dijkstra.Edge>> graph = Dijkstra.buildGraph(2, new int[][] {});

        Assert.assertEquals(2, graph.size());
        Assert.assertTrue(graph.get(0).isEmpty());
        Assert.assertTrue(graph.get(1).isEmpty());
    }
}
