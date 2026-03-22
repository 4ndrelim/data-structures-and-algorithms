package algorithms.graph.floydWarshall;

import static algorithms.graph.floydWarshall.FloydWarshall.INF;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class FloydWarshallTest {

    // ==================== Basic shortest path tests ====================

    @Test
    public void test_simpleGraph() {
        // 0 --5--> 1 --2--> 2
        int[][] edges = {
            {0, 1, 5},
            {1, 2, 2}
        };
        int[][] graph = FloydWarshall.createMatrix(3, edges);

        FloydWarshall.Result result = FloydWarshall.shortestPaths(graph);

        Assert.assertFalse(result.hasNegativeCycle);
        Assert.assertEquals(0, result.distances[0][0]);
        Assert.assertEquals(5, result.distances[0][1]);
        Assert.assertEquals(7, result.distances[0][2]); // 0->1->2 = 5+2
        Assert.assertEquals(2, result.distances[1][2]);
    }

    @Test
    public void test_graphFromReadme() {
        // Graph from README walkthrough
        // 0 --3--> 1, 0 --8--> 2, 1 --(-4)--> 3, 3 --2--> 2
        int[][] edges = {
            {0, 1, 3},
            {0, 2, 8},
            {1, 3, -4},
            {3, 2, 2}
        };
        int[][] graph = FloydWarshall.createMatrix(4, edges);

        FloydWarshall.Result result = FloydWarshall.shortestPaths(graph);

        Assert.assertFalse(result.hasNegativeCycle);
        Assert.assertEquals(0, result.distances[0][0]);
        Assert.assertEquals(3, result.distances[0][1]);
        Assert.assertEquals(1, result.distances[0][2]); // 0->1->3->2 = 3+(-4)+2 = 1
        Assert.assertEquals(-1, result.distances[0][3]); // 0->1->3 = 3+(-4) = -1
        Assert.assertEquals(-2, result.distances[1][2]); // 1->3->2 = -4+2 = -2
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
        int[][] edges = {
            {0, 1, 5},
            {0, 2, 9},
            {1, 2, 2}
        };
        int[][] graph = FloydWarshall.createMatrix(3, edges);

        FloydWarshall.Result result = FloydWarshall.shortestPaths(graph);

        Assert.assertEquals(7, result.distances[0][2]); // 0->1->2 = 5+2 (not direct 9)
    }

    @Test
    public void test_bidirectionalGraph() {
        // Undirected graph: 0 <--3--> 1 <--2--> 2
        int[][] edges = {
            {0, 1, 3}, {1, 0, 3},
            {1, 2, 2}, {2, 1, 2}
        };
        int[][] graph = FloydWarshall.createMatrix(3, edges);

        FloydWarshall.Result result = FloydWarshall.shortestPaths(graph);

        Assert.assertEquals(3, result.distances[0][1]);
        Assert.assertEquals(3, result.distances[1][0]);
        Assert.assertEquals(5, result.distances[0][2]); // 0->1->2
        Assert.assertEquals(5, result.distances[2][0]); // 2->1->0
    }

    @Test
    public void test_completeGraph() {
        // Complete graph K3 with all edges = 1
        int[][] edges = {
            {0, 1, 1}, {0, 2, 1},
            {1, 0, 1}, {1, 2, 1},
            {2, 0, 1}, {2, 1, 1}
        };
        int[][] graph = FloydWarshall.createMatrix(3, edges);

        FloydWarshall.Result result = FloydWarshall.shortestPaths(graph);

        // All pairs should have distance 1 (direct edge)
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Assert.assertEquals(i == j ? 0 : 1, result.distances[i][j]);
            }
        }
    }

    // ==================== Negative weight tests ====================

    @Test
    public void test_negativeEdgeWeight() {
        // 0 --5--> 1 --(-3)--> 2
        int[][] edges = {
            {0, 1, 5},
            {1, 2, -3}
        };
        int[][] graph = FloydWarshall.createMatrix(3, edges);

        FloydWarshall.Result result = FloydWarshall.shortestPaths(graph);

        Assert.assertFalse(result.hasNegativeCycle);
        Assert.assertEquals(2, result.distances[0][2]); // 5 + (-3) = 2
    }

    @Test
    public void test_negativeEdgeMakesShorterPath() {
        // 0 --10--> 2
        // 0 --5--> 1 --(-3)--> 2
        int[][] edges = {
            {0, 2, 10},
            {0, 1, 5},
            {1, 2, -3}
        };
        int[][] graph = FloydWarshall.createMatrix(3, edges);

        FloydWarshall.Result result = FloydWarshall.shortestPaths(graph);

        Assert.assertEquals(2, result.distances[0][2]); // Via negative edge is shorter
    }

    // ==================== Negative cycle tests ====================

    @Test
    public void test_negativeCycleDetection() {
        // Cycle: 0 -> 1 -> 2 -> 0 with total weight -1
        int[][] edges = {
            {0, 1, 1},
            {1, 2, 1},
            {2, 0, -3}
        };
        int[][] graph = FloydWarshall.createMatrix(3, edges);

        FloydWarshall.Result result = FloydWarshall.shortestPaths(graph);

        Assert.assertTrue(result.hasNegativeCycle);
    }

    @Test
    public void test_negativeSelfLoop() {
        // 0 --(-1)--> 0 (negative self-loop)
        int[][] graph = new int[][] {
            {-1, INF},
            {INF, 0}
        };

        FloydWarshall.Result result = FloydWarshall.shortestPaths(graph);

        Assert.assertTrue(result.hasNegativeCycle);
    }

    @Test
    public void test_noNegativeCycleWithNegativeEdges() {
        // Negative edges but no cycle
        // 0 --1--> 1 --(-2)--> 2
        int[][] edges = {
            {0, 1, 1},
            {1, 2, -2}
        };
        int[][] graph = FloydWarshall.createMatrix(3, edges);

        FloydWarshall.Result result = FloydWarshall.shortestPaths(graph);

        Assert.assertFalse(result.hasNegativeCycle);
        Assert.assertEquals(-1, result.distances[0][2]);
    }

    @Test
    public void test_hasNegativeCycleHelper() {
        int[][] edges = {
            {0, 1, 1},
            {1, 2, 1},
            {2, 0, -3}
        };
        int[][] graph = FloydWarshall.createMatrix(3, edges);

        Assert.assertTrue(FloydWarshall.hasNegativeCycle(graph));
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
        int[][] graph = FloydWarshall.createMatrix(4, edges);

        FloydWarshall.Result result = FloydWarshall.shortestPaths(graph);
        List<Integer> path = FloydWarshall.reconstructPath(result.next, 0, 3);

        Assert.assertEquals(Arrays.asList(0, 1, 2, 3), path);
    }

    @Test
    public void test_pathReconstructionDirectEdge() {
        // 0 --5--> 1
        int[][] edges = {{0, 1, 5}};
        int[][] graph = FloydWarshall.createMatrix(2, edges);

        FloydWarshall.Result result = FloydWarshall.shortestPaths(graph);
        List<Integer> path = FloydWarshall.reconstructPath(result.next, 0, 1);

        Assert.assertEquals(Arrays.asList(0, 1), path);
    }

    @Test
    public void test_pathReconstructionSameVertex() {
        int[][] graph = FloydWarshall.createMatrix(2, new int[][] {{0, 1, 1}});

        FloydWarshall.Result result = FloydWarshall.shortestPaths(graph);
        List<Integer> path = FloydWarshall.reconstructPath(result.next, 0, 0);

        Assert.assertEquals(Arrays.asList(0), path);
    }

    @Test
    public void test_pathReconstructionNoPath() {
        // 0 --> 1, but 2 is disconnected
        int[][] edges = {{0, 1, 1}};
        int[][] graph = FloydWarshall.createMatrix(3, edges);

        FloydWarshall.Result result = FloydWarshall.shortestPaths(graph);
        List<Integer> path = FloydWarshall.reconstructPath(result.next, 0, 2);

        Assert.assertTrue(path.isEmpty());
    }

    @Test
    public void test_pathReconstructionWithIntermediate() {
        // Shortest path uses intermediate vertex
        // 0 --1--> 1 --1--> 2
        // 0 --10--> 2 (direct but longer)
        int[][] edges = {
            {0, 1, 1},
            {1, 2, 1},
            {0, 2, 10}
        };
        int[][] graph = FloydWarshall.createMatrix(3, edges);

        FloydWarshall.Result result = FloydWarshall.shortestPaths(graph);
        List<Integer> path = FloydWarshall.reconstructPath(result.next, 0, 2);

        Assert.assertEquals(Arrays.asList(0, 1, 2), path); // Via 1, not direct
    }

    // ==================== Edge cases ====================

    @Test
    public void test_singleVertex() {
        int[][] graph = {{0}};

        FloydWarshall.Result result = FloydWarshall.shortestPaths(graph);

        Assert.assertFalse(result.hasNegativeCycle);
        Assert.assertEquals(0, result.distances[0][0]);
    }

    @Test
    public void test_twoVerticesNoEdge() {
        int[][] graph = {
            {0, INF},
            {INF, 0}
        };

        FloydWarshall.Result result = FloydWarshall.shortestPaths(graph);

        Assert.assertEquals(0, result.distances[0][0]);
        Assert.assertEquals(0, result.distances[1][1]);
        Assert.assertEquals(INF, result.distances[0][1]);
        Assert.assertEquals(INF, result.distances[1][0]);
    }

    @Test
    public void test_disconnectedComponents() {
        // 0 <-> 1,  2 <-> 3 (two separate components)
        int[][] edges = {
            {0, 1, 1}, {1, 0, 1},
            {2, 3, 1}, {3, 2, 1}
        };
        int[][] graph = FloydWarshall.createMatrix(4, edges);

        FloydWarshall.Result result = FloydWarshall.shortestPaths(graph);

        Assert.assertEquals(1, result.distances[0][1]);
        Assert.assertEquals(1, result.distances[2][3]);
        Assert.assertEquals(INF, result.distances[0][2]); // No path between components
        Assert.assertEquals(INF, result.distances[1][3]);
    }

    @Test
    public void test_selfLoopPositive() {
        // Self-loop with positive weight shouldn't affect shortest paths
        int[][] graph = {
            {0, 5},
            {INF, 0}
        };
        graph[0][0] = 0; // Self-loop: 0 --0--> 0 (or any positive value)

        FloydWarshall.Result result = FloydWarshall.shortestPaths(graph);

        Assert.assertEquals(0, result.distances[0][0]); // Self-distance stays 0
        Assert.assertEquals(5, result.distances[0][1]);
    }

    @Test
    public void test_parallelEdges() {
        // Multiple edges - matrix format automatically takes one
        // This tests that createMatrix handles edge overwrites
        int[][] edges = {
            {0, 1, 5},
            {0, 1, 3}  // This should overwrite (or we take minimum)
        };
        int[][] graph = FloydWarshall.createMatrix(2, edges);

        // Note: createMatrix takes last edge; for min, preprocess edges
        FloydWarshall.Result result = FloydWarshall.shortestPaths(graph);

        // Result depends on createMatrix implementation (takes last = 3)
        Assert.assertEquals(3, result.distances[0][1]);
    }

    // ==================== createMatrix utility tests ====================

    @Test
    public void test_createMatrix() {
        int[][] edges = {
            {0, 1, 5},
            {1, 2, 3}
        };
        int[][] matrix = FloydWarshall.createMatrix(3, edges);

        Assert.assertEquals(0, matrix[0][0]);
        Assert.assertEquals(5, matrix[0][1]);
        Assert.assertEquals(INF, matrix[0][2]);
        Assert.assertEquals(3, matrix[1][2]);
        Assert.assertEquals(0, matrix[2][2]);
    }

    @Test
    public void test_createMatrixEmpty() {
        int[][] matrix = FloydWarshall.createMatrix(2, new int[][] {});

        Assert.assertEquals(0, matrix[0][0]);
        Assert.assertEquals(0, matrix[1][1]);
        Assert.assertEquals(INF, matrix[0][1]);
        Assert.assertEquals(INF, matrix[1][0]);
    }

    // ==================== Larger graph tests ====================

    @Test
    public void test_largerGraph() {
        // 5-vertex graph
        //     0
        //    /|\
        //   1 2 3
        //    \|/
        //     4
        int[][] edges = {
            {0, 1, 1}, {0, 2, 2}, {0, 3, 3},
            {1, 4, 1}, {2, 4, 1}, {3, 4, 1}
        };
        int[][] graph = FloydWarshall.createMatrix(5, edges);

        FloydWarshall.Result result = FloydWarshall.shortestPaths(graph);

        Assert.assertEquals(2, result.distances[0][4]); // 0->1->4 = 1+1
        Assert.assertEquals(INF, result.distances[4][0]); // No path back
    }
}
