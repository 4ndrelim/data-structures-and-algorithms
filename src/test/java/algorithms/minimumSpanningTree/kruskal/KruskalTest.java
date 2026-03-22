package algorithms.minimumSpanningTree.kruskal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class KruskalTest {

    // ==================== Basic MST tests ====================

    @Test
    public void test_simpleTriangle() {
        // Graph:
        //     0
        //    / \
        //   1   1
        //  /     \
        // 1 --1-- 2
        int[][] edges = {
            {0, 1, 1},
            {0, 2, 1},
            {1, 2, 1}
        };

        Kruskal.Result result = Kruskal.mst(3, edges);

        assertEquals(2, result.totalWeight); // Any 2 edges of weight 1
        assertEquals(2, result.mstEdges.size()); // V-1 edges
    }

    @Test
    public void test_complexGraph() {
        // Graph:
        //    0
        //  / | \
        // 1  4  3
        // |  |  |
        // 1--3--3
        // |     |
        // 2     1
        // |     |
        // 2-----+
        //
        // Edges: (0,1,1), (0,2,4), (0,3,3), (1,2,2), (1,3,3), (2,3,1)
        int[][] edges = {
            {0, 1, 1},
            {0, 2, 4},
            {0, 3, 3},
            {1, 2, 2},
            {1, 3, 3},
            {2, 3, 1}
        };

        Kruskal.Result result = Kruskal.mst(4, edges);

        // MST: (0,1,1), (2,3,1), (1,2,2) = total 4
        assertEquals(4, result.totalWeight);
        assertEquals(3, result.mstEdges.size()); // V-1 edges
    }

    @Test
    public void test_linearChain() {
        // Graph: 0 --1-- 1 --2-- 2 --3-- 3
        int[][] edges = {
            {0, 1, 1},
            {1, 2, 2},
            {2, 3, 3}
        };

        Kruskal.Result result = Kruskal.mst(4, edges);

        assertEquals(6, result.totalWeight); // 1 + 2 + 3
        assertEquals(3, result.mstEdges.size());
    }

    @Test
    public void test_parallelEdges() {
        // Multiple edges between same vertices
        // 0 --5-- 1
        // 0 --3-- 1 (shorter)
        int[][] edges = {
            {0, 1, 5},
            {0, 1, 3}
        };

        Kruskal.Result result = Kruskal.mst(2, edges);

        assertEquals(3, result.totalWeight); // Takes shorter edge
        assertEquals(1, result.mstEdges.size());
    }

    // ==================== Edge cases ====================

    @Test
    public void test_singleVertex() {
        int[][] edges = {};

        Kruskal.Result result = Kruskal.mst(1, edges);

        assertEquals(0, result.totalWeight);
        assertEquals(0, result.mstEdges.size()); // No edges needed
    }

    @Test
    public void test_twoVertices() {
        int[][] edges = {{0, 1, 5}};

        Kruskal.Result result = Kruskal.mst(2, edges);

        assertEquals(5, result.totalWeight);
        assertEquals(1, result.mstEdges.size());
    }

    @Test
    public void test_emptyGraph() {
        int[][] edges = {};

        Kruskal.Result result = Kruskal.mst(0, edges);

        assertEquals(0, result.totalWeight);
        assertTrue(result.mstEdges.isEmpty());
    }

    @Test
    public void test_disconnectedGraph() {
        // 0 -- 1    2 -- 3 (two components)
        int[][] edges = {
            {0, 1, 1},
            {2, 3, 2}
        };

        int weight = Kruskal.mstWeight(4, edges);

        assertEquals(-1, weight); // Cannot form spanning tree
    }

    // ==================== mstWeight tests ====================

    @Test
    public void test_mstWeight() {
        int[][] edges = {
            {0, 1, 4},
            {0, 2, 2},
            {1, 2, 1},
            {1, 3, 5},
            {2, 3, 3}
        };

        int weight = Kruskal.mstWeight(4, edges);

        assertEquals(6, weight); // 2 + 1 + 3
    }

    // ==================== matrixToEdges tests ====================

    @Test
    public void test_matrixToEdges() {
        int[][] matrix = {
            {0, 1, 3},
            {1, 0, 2},
            {3, 2, 0}
        };

        int[][] edges = Kruskal.matrixToEdges(matrix);

        assertEquals(3, edges.length); // 3 edges in upper triangle
    }

    @Test
    public void test_fromAdjacencyMatrix() {
        // Same as test_simpleTriangle but from matrix
        int[][] matrix = {
            {0, 1, 1},
            {1, 0, 1},
            {1, 1, 0}
        };

        int[][] edges = Kruskal.matrixToEdges(matrix);
        Kruskal.Result result = Kruskal.mst(3, edges);

        assertEquals(2, result.totalWeight);
        assertEquals(2, result.mstEdges.size());
    }

    // ==================== Larger graph tests ====================

    @Test
    public void test_sixVertexGraph() {
        // Graph from README walkthrough
        int[][] edges = {
            {0, 1, 1},
            {1, 2, 2},
            {0, 2, 3},
            {2, 3, 4},
            {1, 3, 5}
        };

        Kruskal.Result result = Kruskal.mst(4, edges);

        // MST: (0,1,1), (1,2,2), (2,3,4) = 7
        assertEquals(7, result.totalWeight);
        assertEquals(3, result.mstEdges.size());
    }
}
