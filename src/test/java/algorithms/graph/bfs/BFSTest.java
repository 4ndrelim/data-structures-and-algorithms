package algorithms.graph.bfs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class BFSTest {

    /**
     * Helper to build adjacency list from edge definitions.
     * @param n number of nodes (0 to n-1)
     * @param edges array of [from, to] pairs
     * @param directed if true, only add edge from->to; if false, add both directions
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
        // Graph: 0 -> 1 -> 2 -> 3 (each level has one node, so order is deterministic)
        List<List<Integer>> graph = buildGraph(4,
                new int[][]{{0, 1}, {1, 2}, {2, 3}}, true);

        List<Integer> result = BFS.traverse(graph, 0);

        Assert.assertEquals(Arrays.asList(0, 1, 2, 3), result);
    }

    @Test
    public void test_traverse_tree() {
        //        0
        //       / \
        //      1   2
        //     / \
        //    3   4
        // Adjacency: 0->[1,2], 1->[3,4]
        List<List<Integer>> graph = buildGraph(5,
                new int[][]{{0, 1}, {0, 2}, {1, 3}, {1, 4}}, true);

        List<Integer> result = BFS.traverse(graph, 0);

        // Verify level-order property: all nodes at level n visited before level n+1
        // Level 0: {0}, Level 1: {1,2}, Level 2: {3,4}
        Assert.assertEquals(5, result.size());
        Assert.assertEquals(Integer.valueOf(0), result.get(0)); // Level 0

        // Level 1: indices 1-2 should contain {1, 2} in some order
        Set<Integer> level1 = new HashSet<>(result.subList(1, 3));
        Assert.assertEquals(Set.of(1, 2), level1);

        // Level 2: indices 3-4 should contain {3, 4} in some order
        Set<Integer> level2 = new HashSet<>(result.subList(3, 5));
        Assert.assertEquals(Set.of(3, 4), level2);
    }

    @Test
    public void test_traverse_graphWithCycle() {
        // 0 -- 1
        // |    |
        // 3 -- 2
        List<List<Integer>> graph = buildGraph(4,
                new int[][]{{0, 1}, {1, 2}, {2, 3}, {3, 0}}, false);

        List<Integer> result = BFS.traverse(graph, 0);

        // Should visit all 4 nodes exactly once
        Assert.assertEquals(4, result.size());
        Assert.assertEquals(Integer.valueOf(0), result.get(0));
        Assert.assertEquals(Set.of(0, 1, 2, 3), new HashSet<>(result));
    }

    @Test
    public void test_traverse_singleNode() {
        List<List<Integer>> graph = buildGraph(1, new int[][]{}, true);

        List<Integer> result = BFS.traverse(graph, 0);

        Assert.assertEquals(Arrays.asList(0), result);
    }

    @Test
    public void test_traverse_disconnectedGraph() {
        // 0 -- 1    2 -- 3 (two separate components)
        List<List<Integer>> graph = buildGraph(4,
                new int[][]{{0, 1}, {2, 3}}, false);

        List<Integer> result = BFS.traverse(graph, 0);

        // BFS from 0 should only reach 0 and 1
        Assert.assertEquals(Set.of(0, 1), new HashSet<>(result));
    }

    @Test
    public void test_traverse_nodeWithNoNeighbors() {
        // 0 -> 1, 2 is isolated but reachable as start
        List<List<Integer>> graph = buildGraph(3,
                new int[][]{{0, 1}}, true);

        // Start from isolated node 2
        List<Integer> result = BFS.traverse(graph, 2);

        Assert.assertEquals(Arrays.asList(2), result);
    }

    @Test
    public void test_traverse_denseGraph() {
        // Complete graph K4: every node connected to every other
        // 0 -- 1
        // |\ /|
        // | X |
        // |/ \|
        // 3 -- 2
        List<List<Integer>> graph = buildGraph(4,
                new int[][]{{0, 1}, {0, 2}, {0, 3}, {1, 2}, {1, 3}, {2, 3}}, false);

        List<Integer> result = BFS.traverse(graph, 0);

        Assert.assertEquals(4, result.size());
        Assert.assertEquals(Integer.valueOf(0), result.get(0));
        // All other nodes at level 1
        Assert.assertEquals(Set.of(1, 2, 3), new HashSet<>(result.subList(1, 4)));
    }

    @Test
    public void test_traverse_selfLoop() {
        // 0 -> 0 (self-loop), 0 -> 1
        List<List<Integer>> graph = new ArrayList<>();
        graph.add(new ArrayList<>(Arrays.asList(0, 1))); // 0 -> 0, 0 -> 1
        graph.add(new ArrayList<>());                     // 1 has no outgoing

        List<Integer> result = BFS.traverse(graph, 0);

        // Self-loop should not cause infinite loop or duplicate
        Assert.assertEquals(2, result.size());
        Assert.assertEquals(Set.of(0, 1), new HashSet<>(result));
    }

    // ==================== hasPath() tests ====================

    @Test
    public void test_hasPath_directPath() {
        List<List<Integer>> graph = buildGraph(4,
                new int[][]{{0, 1}, {1, 2}, {2, 3}}, true);

        Assert.assertTrue(BFS.hasPath(graph, 0, 3));
        Assert.assertTrue(BFS.hasPath(graph, 0, 1));
        Assert.assertTrue(BFS.hasPath(graph, 1, 3));
    }

    @Test
    public void test_hasPath_noPath() {
        // Disconnected: 0 -- 1    2 -- 3
        List<List<Integer>> graph = buildGraph(4,
                new int[][]{{0, 1}, {2, 3}}, false);

        Assert.assertFalse(BFS.hasPath(graph, 0, 2));
        Assert.assertFalse(BFS.hasPath(graph, 0, 3));
        Assert.assertFalse(BFS.hasPath(graph, 1, 2));
    }

    @Test
    public void test_hasPath_sameNode() {
        List<List<Integer>> graph = buildGraph(3,
                new int[][]{{0, 1}, {1, 2}}, true);

        Assert.assertTrue(BFS.hasPath(graph, 0, 0));
        Assert.assertTrue(BFS.hasPath(graph, 2, 2));
    }

    @Test
    public void test_hasPath_withCycle() {
        // 0 -> 1 -> 2 -> 0 (cycle)
        List<List<Integer>> graph = buildGraph(3,
                new int[][]{{0, 1}, {1, 2}, {2, 0}}, true);

        Assert.assertTrue(BFS.hasPath(graph, 0, 2));
        Assert.assertTrue(BFS.hasPath(graph, 2, 1));
    }

    @Test
    public void test_hasPath_directedNoReversePath() {
        // 0 -> 1 -> 2 (directed, no path from 2 to 0)
        List<List<Integer>> graph = buildGraph(3,
                new int[][]{{0, 1}, {1, 2}}, true);

        Assert.assertTrue(BFS.hasPath(graph, 0, 2));
        Assert.assertFalse(BFS.hasPath(graph, 2, 0));
        Assert.assertFalse(BFS.hasPath(graph, 1, 0));
    }

    @Test
    public void test_hasPath_longPath() {
        // Linear chain: 0 -> 1 -> 2 -> 3 -> 4 -> 5
        List<List<Integer>> graph = buildGraph(6,
                new int[][]{{0, 1}, {1, 2}, {2, 3}, {3, 4}, {4, 5}}, true);

        Assert.assertTrue(BFS.hasPath(graph, 0, 5));
        Assert.assertFalse(BFS.hasPath(graph, 5, 0));
    }
}
