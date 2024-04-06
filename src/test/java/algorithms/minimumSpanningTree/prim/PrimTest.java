package algorithms.minimumSpanningTree.prim;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

public class PrimTest {

    @Test
    public void test_simpleGraph() {
        // Graph setup (Adjacency Matrix)
        //     B
        //    / \
        //   1   1
        //  /     \
        // A - 1 - C
        int[][] adjacencyMatrix = {
                {0, 1, 1}, // A: A-B, A-C
                {1, 0, 1}, // B: B-A, B-C
                {1, 1, 0}  // C: C-A, C-B
        };

        Prim.Node[] nodes = {
            new Prim.Node("A", 0),
            new Prim.Node("B", 1),
            new Prim.Node("C", 2)
        };

        // Run Prim's algorithm
        int[][] actualMST = Prim.getPrimsMST(nodes, adjacencyMatrix);

        // Expected MST
        // A -1- B -1- C
        int[][] expectedMST = {
            {0, 1, Integer.MAX_VALUE}, // A: A-B
            {1, 0, 1}, // B: B-A, B-C
            {Integer.MAX_VALUE, 1, 0}  // C: C-B
        };

        // Assertion
        assertArrayEquals(expectedMST, actualMST);
    }

    @Test
    public void test_complexGraph() {
        // Graph setup
        //    A
        //  / | \
        // 1  4  3
        ///   |   \
        //B --3-- D
        // \  |  /
        //  2 4 1
        //   \|/
        //    C
        int[][] adjacencyMatrix = {
            {0, 1, 4, 3}, // A: A-B, A-C, A-D
            {1, 0, 2, 3}, // B: B-A, B-C, B-D
            {4, 2, 0, 1}, // C: C-A, C-B, C-D
            {3, 3, 1, 0}  // D: D-A, D-B, D-C
        };

        Prim.Node[] nodes = {
            new Prim.Node("A", 0),
            new Prim.Node("B", 1),
            new Prim.Node("C", 2),
            new Prim.Node("D", 3)
        };

        // Run Prim's algorithm
        int[][] actualMST = Prim.getPrimsMST(nodes, adjacencyMatrix);

        // Expected MST
        // Based on the graph, assuming the MST is correctly computed
        int[][] expectedMST = {
            {0, 1, Integer.MAX_VALUE, Integer.MAX_VALUE}, // A: A-B
            {1, 0, 2, Integer.MAX_VALUE}, // B: B-A, B-C
            {Integer.MAX_VALUE, 2, 0, 1}, // C: C-B, C-D
            {Integer.MAX_VALUE, Integer.MAX_VALUE, 1, 0}  // D: D-C
        };

        // Assertion
        assertArrayEquals(expectedMST, actualMST);
    }
}
