package algorithms.graph.bellmanFord;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import algorithms.graph.bellmanFord.BellmanFord.Edge;
import algorithms.graph.bellmanFord.BellmanFord.Result;

public class BellmanFordTest {

    // ==================== Basic shortest path tests ====================

    @Test
    public void test_simpleGraph() {
        // 0 --5--> 1 --2--> 2
        List<Edge> edges = Arrays.asList(
                new Edge(0, 1, 5),
                new Edge(1, 2, 2)
        );

        Result result = BellmanFord.shortestPath(3, edges, 0);

        Assert.assertFalse(result.hasNegativeCycle);
        Assert.assertArrayEquals(new int[]{0, 5, 7}, result.distances);
    }

    @Test
    public void test_multiplePathsChoosesShortest() {
        //     1
        //    /|\
        //   5 | 2
        //  /  |  \
        // 0   3   2
        //  \     /
        //   --9--
        List<Edge> edges = Arrays.asList(
                new Edge(0, 1, 5),
                new Edge(1, 2, 2),
                new Edge(0, 2, 9),
                new Edge(1, 2, 3)  // Longer path via 1
        );

        Result result = BellmanFord.shortestPath(3, edges, 0);

        Assert.assertFalse(result.hasNegativeCycle);
        // Shortest to 2: 0->1->2 = 5+2 = 7 (not 0->2 = 9)
        Assert.assertEquals(7, result.distances[2]);
    }

    @Test
    public void test_graphFromReadmeExample() {
        // Graph from README walkthrough:
        // 0 --4--> 1
        // |        |
        // 2        -3
        // v        v
        // 3 <--1-- 2
        List<Edge> edges = Arrays.asList(
                new Edge(0, 1, 4),
                new Edge(0, 3, 2),
                new Edge(1, 2, -3),
                new Edge(2, 3, 1)
        );

        Result result = BellmanFord.shortestPath(4, edges, 0);

        Assert.assertFalse(result.hasNegativeCycle);
        Assert.assertArrayEquals(new int[]{0, 4, 1, 2}, result.distances);
    }

    // ==================== Negative weight tests ====================

    @Test
    public void test_negativeEdgeWeight() {
        // 0 --5--> 1 ---(-3)--> 2
        List<Edge> edges = Arrays.asList(
                new Edge(0, 1, 5),
                new Edge(1, 2, -3)
        );

        Result result = BellmanFord.shortestPath(3, edges, 0);

        Assert.assertFalse(result.hasNegativeCycle);
        Assert.assertArrayEquals(new int[]{0, 5, 2}, result.distances);
    }

    @Test
    public void test_negativeEdgeMakesShorterPath() {
        // 0 --10--> 2
        // 0 --5--> 1 ---(-3)--> 2
        // Direct: 10, via 1: 5 + (-3) = 2
        List<Edge> edges = Arrays.asList(
                new Edge(0, 2, 10),
                new Edge(0, 1, 5),
                new Edge(1, 2, -3)
        );

        Result result = BellmanFord.shortestPath(3, edges, 0);

        Assert.assertFalse(result.hasNegativeCycle);
        Assert.assertEquals(2, result.distances[2]); // Via negative edge is shorter
    }

    // ==================== Negative cycle tests ====================

    @Test
    public void test_negativeCycleDetection() {
        // 0 --> 1 --> 2 --> 1 (cycle with negative total weight)
        //       ^---------/
        // 0->1: 1, 1->2: 2, 2->1: -4 (cycle: 1->2->1 = 2 + (-4) = -2)
        List<Edge> edges = Arrays.asList(
                new Edge(0, 1, 1),
                new Edge(1, 2, 2),
                new Edge(2, 1, -4)
        );

        Result result = BellmanFord.shortestPath(3, edges, 0);

        Assert.assertTrue(result.hasNegativeCycle);
    }

    @Test
    public void test_negativeCycleHelper() {
        List<Edge> edges = Arrays.asList(
                new Edge(0, 1, 1),
                new Edge(1, 2, 2),
                new Edge(2, 1, -4)
        );

        Assert.assertTrue(BellmanFord.hasNegativeCycle(3, edges, 0));
    }

    @Test
    public void test_noNegativeCycleWithNegativeEdges() {
        // Negative edges but no negative cycle
        // 0 --1--> 1 --(-2)--> 2 --3--> 3
        List<Edge> edges = Arrays.asList(
                new Edge(0, 1, 1),
                new Edge(1, 2, -2),
                new Edge(2, 3, 3)
        );

        Result result = BellmanFord.shortestPath(4, edges, 0);

        Assert.assertFalse(result.hasNegativeCycle);
        Assert.assertArrayEquals(new int[]{0, 1, -1, 2}, result.distances);
    }

    @Test
    public void test_negativeCycleNotReachableFromSource() {
        // Negative cycle exists but not reachable from source
        // 0 --1--> 1    2 --> 3 --> 2 (cycle, unreachable from 0)
        List<Edge> edges = Arrays.asList(
                new Edge(0, 1, 1),
                new Edge(2, 3, 1),
                new Edge(3, 2, -3)
        );

        Result result = BellmanFord.shortestPath(4, edges, 0);

        // Unreachable negative cycle should not affect result
        Assert.assertFalse(result.hasNegativeCycle);
    }

    // ==================== Path reconstruction tests ====================

    @Test
    public void test_pathReconstruction() {
        // 0 --1--> 1 --2--> 2 --3--> 3
        List<Edge> edges = Arrays.asList(
                new Edge(0, 1, 1),
                new Edge(1, 2, 2),
                new Edge(2, 3, 3)
        );

        Result result = BellmanFord.shortestPath(4, edges, 0);
        List<Integer> path = BellmanFord.reconstructPath(result.parents, 3);

        Assert.assertEquals(Arrays.asList(0, 1, 2, 3), path);
    }

    @Test
    public void test_pathReconstructionToSource() {
        List<Edge> edges = Arrays.asList(
                new Edge(0, 1, 1)
        );

        Result result = BellmanFord.shortestPath(2, edges, 0);
        List<Integer> path = BellmanFord.reconstructPath(result.parents, 0);

        Assert.assertEquals(Arrays.asList(0), path);
    }

    // ==================== Edge cases ====================

    @Test
    public void test_singleVertex() {
        List<Edge> edges = new ArrayList<>();

        Result result = BellmanFord.shortestPath(1, edges, 0);

        Assert.assertFalse(result.hasNegativeCycle);
        Assert.assertArrayEquals(new int[]{0}, result.distances);
    }

    @Test
    public void test_disconnectedGraph() {
        // 0 --> 1    2 (isolated)
        List<Edge> edges = Arrays.asList(
                new Edge(0, 1, 5)
        );

        Result result = BellmanFord.shortestPath(3, edges, 0);

        Assert.assertFalse(result.hasNegativeCycle);
        Assert.assertEquals(0, result.distances[0]);
        Assert.assertEquals(5, result.distances[1]);
        Assert.assertEquals(Integer.MAX_VALUE, result.distances[2]); // Unreachable
    }

    @Test
    public void test_selfLoop() {
        // 0 --5--> 0 (self-loop), 0 --1--> 1
        List<Edge> edges = Arrays.asList(
                new Edge(0, 0, 5),
                new Edge(0, 1, 1)
        );

        Result result = BellmanFord.shortestPath(2, edges, 0);

        Assert.assertFalse(result.hasNegativeCycle);
        Assert.assertEquals(0, result.distances[0]); // Self-loop doesn't help
        Assert.assertEquals(1, result.distances[1]);
    }

    @Test
    public void test_negativeSelfLoop() {
        // 0 --(-1)--> 0 (negative self-loop = negative cycle)
        List<Edge> edges = Arrays.asList(
                new Edge(0, 0, -1)
        );

        Result result = BellmanFord.shortestPath(1, edges, 0);

        Assert.assertTrue(result.hasNegativeCycle);
    }

    @Test
    public void test_parallelEdges() {
        // Multiple edges between same vertices
        // 0 --5--> 1 (edge 1)
        // 0 --3--> 1 (edge 2, shorter)
        List<Edge> edges = Arrays.asList(
                new Edge(0, 1, 5),
                new Edge(0, 1, 3)
        );

        Result result = BellmanFord.shortestPath(2, edges, 0);

        Assert.assertEquals(3, result.distances[1]); // Takes shorter edge
    }

    @Test
    public void test_differentSource() {
        // 0 --1--> 1 --2--> 2
        List<Edge> edges = Arrays.asList(
                new Edge(0, 1, 1),
                new Edge(1, 2, 2)
        );

        // Start from vertex 1
        Result result = BellmanFord.shortestPath(3, edges, 1);

        Assert.assertEquals(Integer.MAX_VALUE, result.distances[0]); // Can't reach 0
        Assert.assertEquals(0, result.distances[1]); // Source
        Assert.assertEquals(2, result.distances[2]); // 1->2
    }

    // ==================== Larger graph tests ====================

    @Test
    public void test_largerGraph() {
        // More complex graph
        //     1---3---5
        //    /|   |   |\
        //   2 1   2   1 4
        //  /  |   |   |  \
        // 0   2---1---4   6
        //  \         /
        //   ----7----
        List<Edge> edges = Arrays.asList(
                new Edge(0, 1, 2),
                new Edge(0, 2, 1),
                new Edge(0, 4, 7),
                new Edge(1, 2, 1),
                new Edge(1, 3, 3),
                new Edge(2, 4, 2),
                new Edge(3, 4, 1),
                new Edge(3, 5, 1),
                new Edge(4, 5, 1),
                new Edge(5, 6, 4)
        );

        Result result = BellmanFord.shortestPath(7, edges, 0);

        Assert.assertFalse(result.hasNegativeCycle);
        Assert.assertEquals(0, result.distances[0]);
        Assert.assertEquals(2, result.distances[1]);
        Assert.assertEquals(1, result.distances[2]);
        Assert.assertEquals(5, result.distances[3]);
        Assert.assertEquals(3, result.distances[4]); // 0->2->4 = 1+2 = 3 (not 0->4 = 7)
        Assert.assertEquals(4, result.distances[5]); // 0->2->4->5 = 1+2+1 = 4
        Assert.assertEquals(8, result.distances[6]); // 0->2->4->5->6 = 4+4 = 8
    }

    @Test
    public void test_bidirectionalEdges() {
        // Undirected graph represented as bidirectional edges
        // 0 <--3--> 1 <--2--> 2
        List<Edge> edges = Arrays.asList(
                new Edge(0, 1, 3),
                new Edge(1, 0, 3),
                new Edge(1, 2, 2),
                new Edge(2, 1, 2)
        );

        Result result = BellmanFord.shortestPath(3, edges, 0);

        Assert.assertArrayEquals(new int[]{0, 3, 5}, result.distances);
    }
}
