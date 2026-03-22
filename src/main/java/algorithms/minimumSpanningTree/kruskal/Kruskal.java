package algorithms.minimumSpanningTree.kruskal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dataStructures.disjointSet.weightedUnion.DisjointSet;

/**
 * Kruskal's Algorithm for Minimum Spanning Tree.
 *
 * Idea:
 *   - Sort all edges by weight in non-decreasing order
 *   - Greedily add edges that don't form a cycle
 *   - Use Disjoint Set (Union-Find) for efficient cycle detection
 *
 * Key insight:
 *   - An edge forms a cycle iff both endpoints are already in the same component
 *   - Uses the cycle property: max-weight edge in any cycle is not in MST
 *
 * Complexity:
 *   - Time: O(E log E) for sorting, O(E α(V)) for union-find operations
 *   - Space: O(V) for disjoint set
 */
public class Kruskal {

    /**
     * Represents an edge in the MST.
     */
    public static class Edge {
        public final int from;
        public final int to;
        public final int weight;

        public Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
    }

    /**
     * Result of Kruskal's algorithm.
     */
    public static class Result {
        /** Edges in the MST */
        public final List<Edge> mstEdges;
        /** Total weight of the MST */
        public final int totalWeight;

        public Result(List<Edge> mstEdges, int totalWeight) {
            this.mstEdges = mstEdges;
            this.totalWeight = totalWeight;
        }
    }

    /**
     * Computes MST using Kruskal's algorithm.
     *
     * @param numVertices number of vertices (0 to numVertices-1)
     * @param edges array of [from, to, weight] triples (undirected edges)
     * @return Result containing MST edges and total weight
     */
    public static Result mst(int numVertices, int[][] edges) {
        if (numVertices == 0) {
            return new Result(new ArrayList<>(), 0);
        }

        // sort edges by weight
        int[][] sortedEdges = edges.clone();
        Arrays.sort(sortedEdges, (a, b) -> Integer.compare(a[2], b[2]));

        // init Disjoint Set for cycle detection
        // Use Integer objects as vertex identifiers
        Integer[] vertices = new Integer[numVertices];
        for (int i = 0; i < numVertices; i++) {
            vertices[i] = i;
        }
        DisjointSet<Integer> ds = new DisjointSet<>(vertices);

        List<Edge> mstEdges = new ArrayList<>();
        int totalWeight = 0;

        for (int[] edge : sortedEdges) {
            int u = edge[0];
            int v = edge[1];
            int weight = edge[2];

            // only add edge if diff components (no cycle)
            if (!ds.find(u, v)) {
                mstEdges.add(new Edge(u, v, weight));
                totalWeight += weight;
                ds.union(u, v);

                // MST has exactly V-1 edges
                if (mstEdges.size() == numVertices - 1) {
                    break;
                }
            }
        }

        return new Result(mstEdges, totalWeight);
    }

    /**
     * Computes only the total weight of the MST.
     *
     * @param numVertices number of vertices
     * @param edges array of [from, to, weight] triples
     * @return total weight of MST, or -1 if graph is not connected
     */
    public static int mstWeight(int numVertices, int[][] edges) {
        Result result = mst(numVertices, edges);
        // Check if MST spans all vertices (V-1 edges for V vertices)
        if (numVertices > 1 && result.mstEdges.size() < numVertices - 1) {
            return -1; // Graph is not connected
        }
        return result.totalWeight;
    }

    /**
     * Converts adjacency matrix to edge list.
     * Assumes undirected graph (only reads upper triangle).
     *
     * @param adjacencyMatrix adjacency matrix where matrix[i][j] = weight, or Integer.MAX_VALUE if no edge
     * @return array of [from, to, weight] triples
     */
    public static int[][] matrixToEdges(int[][] adjacencyMatrix) {
        int n = adjacencyMatrix.length;
        List<int[]> edgeList = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (adjacencyMatrix[i][j] != Integer.MAX_VALUE && adjacencyMatrix[i][j] != 0) {
                    edgeList.add(new int[]{i, j, adjacencyMatrix[i][j]});
                }
            }
        }

        return edgeList.toArray(new int[0][]);
    }
}
