package algorithms.graph.bellmanFord;

import java.util.Arrays;
import java.util.List;

/**
 * Bellman-Ford Algorithm for Single-Source Shortest Path.
 *
 * Idea:
 *   - Relax all edges V-1 times (where V = number of vertices)
 *   - After V-1 iterations, shortest paths are guaranteed (if no negative cycle)
 *   - One more iteration can detect negative cycles
 *
 * Key advantage over Dijkstra:
 *   - Works with negative edge weights
 *   - Can detect negative cycles
 *
 * Trade-off:
 *   - Slower than Dijkstra: O(V * E) vs O((V + E) log V)
 */
public class BellmanFord {

    /**
     * Represents a weighted directed edge.
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
     * Result of Bellman-Ford algorithm.
     */
    public static class Result {
        /** Shortest distances from source. Integer.MAX_VALUE if unreachable. */
        public final int[] distances;

        /** Parent array for path reconstruction. -1 if no parent. */
        public final int[] parents;

        /** True if a negative cycle is reachable from source. */
        public final boolean hasNegativeCycle;

        public Result(int[] distances, int[] parents, boolean hasNegativeCycle) {
            this.distances = distances;
            this.parents = parents;
            this.hasNegativeCycle = hasNegativeCycle;
        }
    }

    /**
     * Computes shortest paths from source to all vertices using Bellman-Ford.
     *
     * @param numVertices number of vertices (0 to numVertices-1)
     * @param edges list of weighted directed edges
     * @param source starting vertex
     * @return Result containing distances, parents, and negative cycle flag
     */
    public static Result shortestPath(int numVertices, List<Edge> edges, int source) {
        int[] dist = new int[numVertices];
        int[] parent = new int[numVertices];

        // init. distances to infinity, source to 0
        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);
        dist[source] = 0;

        // relax all edges V-1 times
        for (int i = 0; i < numVertices - 1; i++) {
            boolean relaxed = false;
            for (Edge edge : edges) {
                if (dist[edge.from] != Integer.MAX_VALUE
                        && dist[edge.from] + edge.weight < dist[edge.to]) {
                    dist[edge.to] = dist[edge.from] + edge.weight;
                    parent[edge.to] = edge.from;
                    relaxed = true;
                }
            }
            // Early termination: if no relaxation happened, we're done
            if (!relaxed) {
                break;
            }
        }

        // check for negative cycles
        // essentially running for 1 more iteration, same as above, but split for clarity
        boolean hasNegativeCycle = false;
        for (Edge edge : edges) {
            if (dist[edge.from] != Integer.MAX_VALUE
                    && dist[edge.from] + edge.weight < dist[edge.to]) {
                hasNegativeCycle = true;
                break;
            }
        }

        return new Result(dist, parent, hasNegativeCycle);
    }

    /**
     * Checks if the graph contains a negative cycle reachable from source.
     *
     * @param numVertices number of vertices
     * @param edges list of weighted directed edges
     * @param source starting vertex
     * @return true if negative cycle exists and is reachable from source
     */
    public static boolean hasNegativeCycle(int numVertices, List<Edge> edges, int source) {
        return shortestPath(numVertices, edges, source).hasNegativeCycle;
    }

    /**
     * Reconstructs the shortest path from source to target.
     *
     * @param parents parent array from Bellman-Ford result
     * @param target destination vertex
     * @return list of vertices in path order, empty if unreachable
     */
    public static List<Integer> reconstructPath(int[] parents, int target) {
        java.util.LinkedList<Integer> path = new java.util.LinkedList<>();

        if (parents[target] == -1 && target != findSource(parents)) {
            return path; // unreachable
        }

        int current = target;
        while (current != -1) {
            path.addFirst(current);
            current = parents[current];
        }

        return path;
    }

    /**
     * Finds the source vertex (the one with no parent that has distance 0).
     */
    private static int findSource(int[] parents) {
        for (int i = 0; i < parents.length; i++) {
            if (parents[i] == -1) {
                return i;
            }
        }
        return -1;
    }
}
