package algorithms.minimumSpanningTree.prim;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

public class PrimTest {

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
        List<List<Prim.Edge>> graph = Prim.buildGraph(3, edges);

        Prim.Result result = Prim.mst(3, graph);

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
        List<List<Prim.Edge>> graph = Prim.buildGraph(4, edges);

        Prim.Result result = Prim.mst(4, graph);

        // MST: (0,1,1), (1,2,2), (2,3,1) = total 4
        assertEquals(4, result.totalWeight);
        assertEquals(3, result.mstEdges.size()); // V-1 edges
    }

    @Test
    public void test_readmeWalkthrough() {
        // Graph from README visual walkthrough
        // 0: [(1,4), (2,2)]
        // 1: [(0,4), (2,1), (3,5)]
        // 2: [(0,2), (1,1), (3,3)]
        // 3: [(1,5), (2,3)]
        int[][] edges = {
            {0, 1, 4},
            {0, 2, 2},
            {1, 2, 1},
            {1, 3, 5},
            {2, 3, 3}
        };
        List<List<Prim.Edge>> graph = Prim.buildGraph(4, edges);

        Prim.Result result = Prim.mst(4, graph);

        // MST: (0,2,2), (2,1,1), (2,3,3) = total 6
        assertEquals(6, result.totalWeight);
        assertEquals(3, result.mstEdges.size());
    }

    @Test
    public void test_linearChain() {
        // Graph: 0 --1-- 1 --2-- 2 --3-- 3
        int[][] edges = {
            {0, 1, 1},
            {1, 2, 2},
            {2, 3, 3}
        };
        List<List<Prim.Edge>> graph = Prim.buildGraph(4, edges);

        Prim.Result result = Prim.mst(4, graph);

        assertEquals(6, result.totalWeight); // 1 + 2 + 3
        assertEquals(3, result.mstEdges.size());
    }

    @Test
    public void test_choosesLighterEdge() {
        // 0 --10-- 2
        // 0 --3-- 1 --2-- 2
        // Should choose path via 1, not direct
        int[][] edges = {
            {0, 2, 10},
            {0, 1, 3},
            {1, 2, 2}
        };
        List<List<Prim.Edge>> graph = Prim.buildGraph(3, edges);

        Prim.Result result = Prim.mst(3, graph);

        assertEquals(5, result.totalWeight); // 3 + 2, not 10 + 3
        assertEquals(2, result.mstEdges.size());
    }

    // ==================== Edge cases ====================

    @Test
    public void test_singleVertex() {
        List<List<Prim.Edge>> graph = Prim.buildGraph(1, new int[][]{});

        Prim.Result result = Prim.mst(1, graph);

        assertEquals(0, result.totalWeight);
        assertEquals(0, result.mstEdges.size()); // No edges needed
    }

    @Test
    public void test_twoVertices() {
        int[][] edges = {{0, 1, 5}};
        List<List<Prim.Edge>> graph = Prim.buildGraph(2, edges);

        Prim.Result result = Prim.mst(2, graph);

        assertEquals(5, result.totalWeight);
        assertEquals(1, result.mstEdges.size());
    }

    @Test
    public void test_emptyGraph() {
        List<List<Prim.Edge>> graph = Prim.buildGraph(0, new int[][]{});

        Prim.Result result = Prim.mst(0, graph);

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
        List<List<Prim.Edge>> graph = Prim.buildGraph(4, edges);

        int weight = Prim.mstWeight(4, graph);

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
        List<List<Prim.Edge>> graph = Prim.buildGraph(4, edges);

        int weight = Prim.mstWeight(4, graph);

        assertEquals(6, weight); // 2 + 1 + 3
    }

    // ==================== buildGraph tests ====================

    @Test
    public void test_buildGraph() {
        int[][] edges = {
            {0, 1, 5},
            {0, 2, 3}
        };
        List<List<Prim.Edge>> graph = Prim.buildGraph(3, edges);

        assertEquals(3, graph.size());
        assertEquals(2, graph.get(0).size()); // 0 connects to 1 and 2
        assertEquals(1, graph.get(1).size()); // 1 connects to 0
        assertEquals(1, graph.get(2).size()); // 2 connects to 0
    }

    // ==================== Larger graph tests ====================

    @Test
    public void test_sixVertexGraph() {
        //     0
        //    /|\
        //   2 1 4
        //  /  |  \
        // 1---3---2
        //  \     /
        //   5   6
        //    \ /
        //     3
        int[][] edges = {
            {0, 1, 2}, {0, 2, 1}, {0, 3, 4},
            {1, 2, 3}, {1, 3, 5},
            {2, 3, 6}
        };
        List<List<Prim.Edge>> graph = Prim.buildGraph(4, edges);

        Prim.Result result = Prim.mst(4, graph);

        // MST: (0,2,1), (0,1,2), (0,3,4) = 7
        assertEquals(7, result.totalWeight);
        assertEquals(3, result.mstEdges.size());
    }

    @Test
    public void test_completeGraphK4() {
        // Complete graph with 4 vertices
        int[][] edges = {
            {0, 1, 1}, {0, 2, 4}, {0, 3, 3},
            {1, 2, 2}, {1, 3, 5},
            {2, 3, 6}
        };
        List<List<Prim.Edge>> graph = Prim.buildGraph(4, edges);

        Prim.Result result = Prim.mst(4, graph);

        // MST: (0,1,1), (1,2,2), (0,3,3) = 6
        assertEquals(6, result.totalWeight);
        assertEquals(3, result.mstEdges.size());
    }
}
