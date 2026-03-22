package algorithms.graph.dfs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class DFSIterativeTest {

    /**
     * Helper to build adjacency list from edge definitions.
     */
    private List<List<Integer>> buildGraph(int n, int[][] edges, boolean directed) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
            if (!directed) {
                graph.get(edge[1]).add(edge[0]);
            }
        }
        return graph;
    }

    // ==================== traverse() tests ====================

    @Test
    public void test_traverse_simpleChain() {
        // Graph: 0 -> 1 -> 2 -> 3
        List<List<Integer>> graph = buildGraph(4,
                new int[][]{{0, 1}, {1, 2}, {2, 3}}, true);

        List<Integer> result = DFSIterative.traverse(graph, 0);

        Assert.assertEquals(Arrays.asList(0, 1, 2, 3), result);
    }

    @Test
    public void test_traverse_tree() {
        //        0
        //       / \
        //      1   2
        //     / \
        //    3   4
        List<List<Integer>> graph = buildGraph(5,
                new int[][]{{0, 1}, {0, 2}, {1, 3}, {1, 4}}, true);

        List<Integer> result = DFSIterative.traverse(graph, 0);

        // Verify all nodes visited exactly once
        Assert.assertEquals(5, result.size());
        Assert.assertEquals(Integer.valueOf(0), result.get(0));
        Assert.assertEquals(Set.of(0, 1, 2, 3, 4), new HashSet<>(result));

        // Verify DFS property: parent visited before children
        Assert.assertTrue(result.indexOf(1) < result.indexOf(3));
        Assert.assertTrue(result.indexOf(1) < result.indexOf(4));
        Assert.assertTrue(result.indexOf(0) < result.indexOf(1));
        Assert.assertTrue(result.indexOf(0) < result.indexOf(2));
    }

    @Test
    public void test_traverse_graphWithCycle() {
        // 0 -- 1
        // |    |
        // 3 -- 2
        List<List<Integer>> graph = buildGraph(4,
                new int[][]{{0, 1}, {1, 2}, {2, 3}, {3, 0}}, false);

        List<Integer> result = DFSIterative.traverse(graph, 0);

        // Should visit all 4 nodes exactly once
        Assert.assertEquals(4, result.size());
        Assert.assertEquals(Integer.valueOf(0), result.get(0));
        Assert.assertEquals(Set.of(0, 1, 2, 3), new HashSet<>(result));
    }

    @Test
    public void test_traverse_singleNode() {
        List<List<Integer>> graph = buildGraph(1, new int[][]{}, true);

        List<Integer> result = DFSIterative.traverse(graph, 0);

        Assert.assertEquals(Arrays.asList(0), result);
    }

    @Test
    public void test_traverse_disconnectedGraph() {
        // 0 -- 1    2 -- 3
        List<List<Integer>> graph = buildGraph(4,
                new int[][]{{0, 1}, {2, 3}}, false);

        List<Integer> result = DFSIterative.traverse(graph, 0);

        // DFS from 0 should only reach 0 and 1
        Assert.assertEquals(Set.of(0, 1), new HashSet<>(result));
    }

    @Test
    public void test_traverse_nodeWithNoNeighbors() {
        List<List<Integer>> graph = buildGraph(3,
                new int[][]{{0, 1}}, true);

        List<Integer> result = DFSIterative.traverse(graph, 2);

        Assert.assertEquals(Arrays.asList(2), result);
    }

    @Test
    public void test_traverse_selfLoop() {
        // 0 -> 0 (self-loop), 0 -> 1
        List<List<Integer>> graph = new ArrayList<>();
        graph.add(new ArrayList<>(Arrays.asList(0, 1)));
        graph.add(new ArrayList<>());

        List<Integer> result = DFSIterative.traverse(graph, 0);

        Assert.assertEquals(2, result.size());
        Assert.assertEquals(Set.of(0, 1), new HashSet<>(result));
    }

    @Test
    public void test_traverse_deepTree() {
        // Linear deep tree: 0 -> 1 -> 2 -> 3 -> 4
        // This tests that iterative DFS doesn't have stack overflow issues
        List<List<Integer>> graph = buildGraph(5,
                new int[][]{{0, 1}, {1, 2}, {2, 3}, {3, 4}}, true);

        List<Integer> result = DFSIterative.traverse(graph, 0);

        Assert.assertEquals(Arrays.asList(0, 1, 2, 3, 4), result);
    }

    // ==================== hasPath() tests ====================

    @Test
    public void test_hasPath_directPath() {
        List<List<Integer>> graph = buildGraph(4,
                new int[][]{{0, 1}, {1, 2}, {2, 3}}, true);

        Assert.assertTrue(DFSIterative.hasPath(graph, 0, 3));
        Assert.assertTrue(DFSIterative.hasPath(graph, 0, 1));
        Assert.assertTrue(DFSIterative.hasPath(graph, 1, 3));
    }

    @Test
    public void test_hasPath_noPath() {
        // Disconnected
        List<List<Integer>> graph = buildGraph(4,
                new int[][]{{0, 1}, {2, 3}}, false);

        Assert.assertFalse(DFSIterative.hasPath(graph, 0, 2));
        Assert.assertFalse(DFSIterative.hasPath(graph, 0, 3));
    }

    @Test
    public void test_hasPath_sameNode() {
        List<List<Integer>> graph = buildGraph(3,
                new int[][]{{0, 1}, {1, 2}}, true);

        Assert.assertTrue(DFSIterative.hasPath(graph, 0, 0));
    }

    @Test
    public void test_hasPath_withCycle() {
        List<List<Integer>> graph = buildGraph(3,
                new int[][]{{0, 1}, {1, 2}, {2, 0}}, true);

        Assert.assertTrue(DFSIterative.hasPath(graph, 0, 2));
        Assert.assertTrue(DFSIterative.hasPath(graph, 2, 1));
    }

    @Test
    public void test_hasPath_directedNoReversePath() {
        // 0 -> 1 -> 2 (directed)
        List<List<Integer>> graph = buildGraph(3,
                new int[][]{{0, 1}, {1, 2}}, true);

        Assert.assertTrue(DFSIterative.hasPath(graph, 0, 2));
        Assert.assertFalse(DFSIterative.hasPath(graph, 2, 0));
    }

    @Test
    public void test_hasPath_multiplePathsToTarget() {
        // 0 -> 1 -> 3
        // 0 -> 2 -> 3
        List<List<Integer>> graph = buildGraph(4,
                new int[][]{{0, 1}, {0, 2}, {1, 3}, {2, 3}}, true);

        Assert.assertTrue(DFSIterative.hasPath(graph, 0, 3));
    }
}
