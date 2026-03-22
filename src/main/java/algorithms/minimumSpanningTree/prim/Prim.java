package algorithms.minimumSpanningTree.prim;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Prim's Algorithm for Minimum Spanning Tree.
 *
 * Idea:
 *   - Start from any vertex and grow the MST one edge at a time
 *   - Always add the lightest edge that connects the tree to a new vertex
 *   - Use a priority queue to efficiently find the minimum crossing edge
 *
 * Key insight:
 *   - Uses the cut property: minimum weight edge crossing any cut is in MST
 *   - The "cut" is between vertices in MST and vertices not yet in MST
 *
 * Complexity:
 *   - Time: O(E log V) with binary heap
 *   - Space: O(V + E)
 */
public class Prim {

    /**
     * Represents a weighted edge in the adjacency list.
     */
    public static class Edge {
        public final int to;
        public final int weight;

        public Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    /**
     * Represents an edge in the MST result.
     */
    public static class MSTEdge {
        public final int from;
        public final int to;
        public final int weight;

        public MSTEdge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
    }

    /**
     * Result of Prim's algorithm.
     */
    public static class Result {
        /** Edges in the MST */
        public final List<MSTEdge> mstEdges;
        /** Total weight of the MST */
        public final int totalWeight;

        public Result(List<MSTEdge> mstEdges, int totalWeight) {
            this.mstEdges = mstEdges;
            this.totalWeight = totalWeight;
        }
    }

    /**
     * Computes MST using Prim's algorithm starting from vertex 0.
     *
     * @param numVertices number of vertices (0 to numVertices-1)
     * @param graph adjacency list where graph.get(u) contains edges from u
     * @return Result containing MST edges and total weight
     */
    public static Result mst(int numVertices, List<List<Edge>> graph) {
        if (numVertices == 0) {
            return new Result(new ArrayList<>(), 0);
        }

        // Track minimum edge weight to reach each vertex
        int[] minWeight = new int[numVertices];
        Arrays.fill(minWeight, Integer.MAX_VALUE);

        // Track which vertex connects to each vertex in MST
        int[] parent = new int[numVertices];
        Arrays.fill(parent, -1);

        // Track vertices already in MST
        boolean[] inMST = new boolean[numVertices];

        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Integer.compare(a[1], b[1]));
        minWeight[0] = 0;
        pq.offer(new int[]{0, 0});

        List<MSTEdge> mstEdges = new ArrayList<>();
        int totalWeight = 0;

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int u = curr[0];
            int weight = curr[1];

            if (inMST[u]) {
                continue;
            }

            inMST[u] = true;
            totalWeight += weight;

            // add edge to MST (except for starting vertex)
            if (parent[u] != -1) {
                mstEdges.add(new MSTEdge(parent[u], u, weight));
            }

            for (Edge edge : graph.get(u)) {
                int v = edge.to;
                int edgeWeight = edge.weight;

                // If v not in MST and this edge is lighter than current best
                if (!inMST[v] && edgeWeight < minWeight[v]) {
                    minWeight[v] = edgeWeight;
                    parent[v] = u;
                    pq.offer(new int[]{v, edgeWeight});
                }
            }
        }

        return new Result(mstEdges, totalWeight);
    }

    /**
     * Computes only the total weight of the MST.
     *
     * @param numVertices number of vertices
     * @param graph adjacency list
     * @return total weight of MST, or -1 if graph is not connected
     */
    public static int mstWeight(int numVertices, List<List<Edge>> graph) {
        Result result = mst(numVertices, graph);
        // Check if MST spans all vertices (V-1 edges for V vertices)
        if (numVertices > 1 && result.mstEdges.size() < numVertices - 1) {
            return -1; // Graph is not connected
        }
        return result.totalWeight;
    }

    /**
     * Builds adjacency list from edge array.
     * Treats edges as undirected (adds both directions).
     *
     * @param numVertices number of vertices
     * @param edges array of [from, to, weight] triples
     * @return adjacency list suitable for Prim
     */
    public static List<List<Edge>> buildGraph(int numVertices, int[][] edges) {
        List<List<Edge>> graph = new ArrayList<>();
        for (int i = 0; i < numVertices; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] edge : edges) {
            int from = edge[0];
            int to = edge[1];
            int weight = edge[2];
            graph.get(from).add(new Edge(to, weight));
            graph.get(to).add(new Edge(from, weight)); // Undirected
        }
        return graph;
    }
}
