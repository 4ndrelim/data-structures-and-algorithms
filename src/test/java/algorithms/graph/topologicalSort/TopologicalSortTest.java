package algorithms.graph.topologicalSort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class TopologicalSortTest {

    /**
     * Helper to build adjacency list from edge definitions.
     * @param n number of nodes (0 to n-1)
     * @param edges array of [from, to] pairs representing directed edges
     */
    private List<List<Integer>> buildGraph(int n, int[][] edges) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
        }
        return graph;
    }

    /**
     * Validates that an ordering is a valid topological sort.
     * For every edge u -> v, u must appear before v in the ordering.
     */
    private boolean isValidTopologicalOrder(int n, int[][] edges, List<Integer> ordering) {
        if (ordering.size() != n) {
            return false;
        }

        // Check all vertices present exactly once
        Set<Integer> seen = new HashSet<>(ordering);
        if (seen.size() != n) {
            return false;
        }
        for (int i = 0; i < n; i++) {
            if (!seen.contains(i)) {
                return false;
            }
        }

        // Build position map
        Map<Integer, Integer> position = new HashMap<>();
        for (int i = 0; i < ordering.size(); i++) {
            position.put(ordering.get(i), i);
        }

        // Check all edges: u must come before v
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            if (position.get(u) >= position.get(v)) {
                return false; // u should come before v
            }
        }

        return true;
    }

    // ==================== sort() tests ====================

    @Test
    public void test_sort_simpleChain() {
        // 0 -> 1 -> 2 -> 3
        int[][] edges = {{0, 1}, {1, 2}, {2, 3}};
        List<List<Integer>> graph = buildGraph(4, edges);

        List<Integer> result = TopologicalSort.sort(4, graph);

        // Only one valid ordering for a chain
        Assert.assertEquals(Arrays.asList(0, 1, 2, 3), result);
    }

    @Test
    public void test_sort_diamond() {
        //     0
        //    / \
        //   1   2
        //    \ /
        //     3
        int[][] edges = {{0, 1}, {0, 2}, {1, 3}, {2, 3}};
        List<List<Integer>> graph = buildGraph(4, edges);

        List<Integer> result = TopologicalSort.sort(4, graph);

        // Multiple valid orderings: [0,1,2,3], [0,2,1,3]
        Assert.assertTrue(isValidTopologicalOrder(4, edges, result));
        Assert.assertEquals(Integer.valueOf(0), result.get(0)); // 0 must be first
        Assert.assertEquals(Integer.valueOf(3), result.get(3)); // 3 must be last
    }

    @Test
    public void test_sort_courseScheduleExample() {
        // From README walkthrough
        // 5 -> 0, 5 -> 2, 2 -> 1, 2 -> 3, 1 -> 4
        int[][] edges = {{5, 0}, {5, 2}, {2, 1}, {2, 3}, {1, 4}};
        List<List<Integer>> graph = buildGraph(6, edges);

        List<Integer> result = TopologicalSort.sort(6, graph);

        Assert.assertTrue(isValidTopologicalOrder(6, edges, result));
        // 5 must come before 0, 2
        // 2 must come before 1, 3
        // 1 must come before 4
    }

    @Test
    public void test_sort_multipleRoots() {
        // Two independent chains: 0 -> 1, 2 -> 3
        int[][] edges = {{0, 1}, {2, 3}};
        List<List<Integer>> graph = buildGraph(4, edges);

        List<Integer> result = TopologicalSort.sort(4, graph);

        Assert.assertTrue(isValidTopologicalOrder(4, edges, result));
    }

    @Test
    public void test_sort_singleVertex() {
        List<List<Integer>> graph = buildGraph(1, new int[][]{});

        List<Integer> result = TopologicalSort.sort(1, graph);

        Assert.assertEquals(Arrays.asList(0), result);
    }

    @Test
    public void test_sort_twoVerticesNoEdge() {
        // Disconnected: 0, 1 with no edges
        List<List<Integer>> graph = buildGraph(2, new int[][]{});

        List<Integer> result = TopologicalSort.sort(2, graph);

        // Both orderings valid: [0,1] or [1,0]
        Assert.assertEquals(2, result.size());
        Assert.assertTrue(result.contains(0));
        Assert.assertTrue(result.contains(1));
    }

    @Test
    public void test_sort_wideGraph() {
        // 0 points to many: 0 -> 1, 0 -> 2, 0 -> 3, 0 -> 4
        int[][] edges = {{0, 1}, {0, 2}, {0, 3}, {0, 4}};
        List<List<Integer>> graph = buildGraph(5, edges);

        List<Integer> result = TopologicalSort.sort(5, graph);

        Assert.assertTrue(isValidTopologicalOrder(5, edges, result));
        Assert.assertEquals(Integer.valueOf(0), result.get(0)); // 0 must be first
    }

    @Test
    public void test_sort_deepGraph() {
        // Long chain: 0 -> 1 -> 2 -> 3 -> 4 -> 5
        int[][] edges = {{0, 1}, {1, 2}, {2, 3}, {3, 4}, {4, 5}};
        List<List<Integer>> graph = buildGraph(6, edges);

        List<Integer> result = TopologicalSort.sort(6, graph);

        Assert.assertEquals(Arrays.asList(0, 1, 2, 3, 4, 5), result);
    }

    @Test
    public void test_sort_complexDAG() {
        //     0
        //    /|\
        //   1 2 3
        //   |X|
        //   4 5
        //    \|
        //     6
        int[][] edges = {
            {0, 1}, {0, 2}, {0, 3},
            {1, 4}, {1, 5},
            {2, 4}, {2, 5},
            {4, 6}, {5, 6}
        };
        List<List<Integer>> graph = buildGraph(7, edges);

        List<Integer> result = TopologicalSort.sort(7, graph);

        Assert.assertTrue(isValidTopologicalOrder(7, edges, result));
    }

    // ==================== Cycle detection tests ====================

    @Test
    public void test_sort_cycleReturnsEmpty() {
        // Cycle: 0 -> 1 -> 2 -> 0
        int[][] edges = {{0, 1}, {1, 2}, {2, 0}};
        List<List<Integer>> graph = buildGraph(3, edges);

        List<Integer> result = TopologicalSort.sort(3, graph);

        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void test_sort_selfLoopReturnsEmpty() {
        // Self-loop: 0 -> 0
        int[][] edges = {{0, 0}};
        List<List<Integer>> graph = buildGraph(1, edges);

        List<Integer> result = TopologicalSort.sort(1, graph);

        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void test_sort_cycleWithTail() {
        // 0 -> 1 -> 2 -> 3 -> 1 (cycle at 1-2-3, tail at 0)
        int[][] edges = {{0, 1}, {1, 2}, {2, 3}, {3, 1}};
        List<List<Integer>> graph = buildGraph(4, edges);

        List<Integer> result = TopologicalSort.sort(4, graph);

        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void test_sort_twoCycle() {
        // Simple two-node cycle: 0 -> 1 -> 0
        int[][] edges = {{0, 1}, {1, 0}};
        List<List<Integer>> graph = buildGraph(2, edges);

        List<Integer> result = TopologicalSort.sort(2, graph);

        Assert.assertTrue(result.isEmpty());
    }

    // ==================== hasCycle() tests ====================

    @Test
    public void test_hasCycle_noCycle() {
        int[][] edges = {{0, 1}, {1, 2}};
        List<List<Integer>> graph = buildGraph(3, edges);

        Assert.assertFalse(TopologicalSort.hasCycle(3, graph));
    }

    @Test
    public void test_hasCycle_withCycle() {
        int[][] edges = {{0, 1}, {1, 2}, {2, 0}};
        List<List<Integer>> graph = buildGraph(3, edges);

        Assert.assertTrue(TopologicalSort.hasCycle(3, graph));
    }

    @Test
    public void test_hasCycle_selfLoop() {
        int[][] edges = {{0, 0}};
        List<List<Integer>> graph = buildGraph(1, edges);

        Assert.assertTrue(TopologicalSort.hasCycle(1, graph));
    }

    @Test
    public void test_hasCycle_emptyGraph() {
        List<List<Integer>> graph = buildGraph(0, new int[][]{});

        Assert.assertFalse(TopologicalSort.hasCycle(0, graph));
    }

    @Test
    public void test_hasCycle_disconnectedWithCycle() {
        // 0 -> 1 (no cycle), 2 -> 3 -> 2 (cycle)
        int[][] edges = {{0, 1}, {2, 3}, {3, 2}};
        List<List<Integer>> graph = buildGraph(4, edges);

        Assert.assertTrue(TopologicalSort.hasCycle(4, graph));
    }

    // ==================== isValidTopologicalOrder() tests ====================

    @Test
    public void test_isValidOrder_validOrdering() {
        int[][] edges = {{0, 1}, {0, 2}, {1, 3}, {2, 3}};
        List<List<Integer>> graph = buildGraph(4, edges);

        Assert.assertTrue(TopologicalSort.isValidTopologicalOrder(
                4, graph, Arrays.asList(0, 1, 2, 3)));
        Assert.assertTrue(TopologicalSort.isValidTopologicalOrder(
                4, graph, Arrays.asList(0, 2, 1, 3)));
    }

    @Test
    public void test_isValidOrder_invalidOrdering() {
        int[][] edges = {{0, 1}, {1, 2}};
        List<List<Integer>> graph = buildGraph(3, edges);

        // 2 before 1 violates edge 1 -> 2
        Assert.assertFalse(TopologicalSort.isValidTopologicalOrder(
                3, graph, Arrays.asList(0, 2, 1)));

        // 1 before 0 violates edge 0 -> 1
        Assert.assertFalse(TopologicalSort.isValidTopologicalOrder(
                3, graph, Arrays.asList(1, 0, 2)));
    }

    @Test
    public void test_isValidOrder_wrongSize() {
        int[][] edges = {{0, 1}};
        List<List<Integer>> graph = buildGraph(2, edges);

        Assert.assertFalse(TopologicalSort.isValidTopologicalOrder(
                2, graph, Arrays.asList(0)));
        Assert.assertFalse(TopologicalSort.isValidTopologicalOrder(
                2, graph, Arrays.asList(0, 1, 2)));
    }

    @Test
    public void test_isValidOrder_duplicates() {
        int[][] edges = {{0, 1}};
        List<List<Integer>> graph = buildGraph(2, edges);

        Assert.assertFalse(TopologicalSort.isValidTopologicalOrder(
                2, graph, Arrays.asList(0, 0)));
    }

    // ==================== Edge cases ====================

    @Test
    public void test_sort_emptyGraph() {
        List<List<Integer>> graph = buildGraph(0, new int[][]{});

        List<Integer> result = TopologicalSort.sort(0, graph);

        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void test_sort_parallelEdges() {
        // Multiple edges between same vertices (unusual but valid)
        // 0 -> 1 (twice)
        List<List<Integer>> graph = new ArrayList<>();
        graph.add(new ArrayList<>(Arrays.asList(1, 1))); // 0 -> 1, 0 -> 1
        graph.add(new ArrayList<>());

        List<Integer> result = TopologicalSort.sort(2, graph);

        Assert.assertEquals(Arrays.asList(0, 1), result);
    }
}
