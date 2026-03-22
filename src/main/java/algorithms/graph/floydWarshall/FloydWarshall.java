package algorithms.graph.floydWarshall;

/**
 * Floyd-Warshall Algorithm for All-Pairs Shortest Path.
 *
 * Idea:
 *   - Dynamic programming approach using intermediate vertices
 *   - For each vertex k, check if path i -> k -> j is shorter than i -> j
 *   - After considering all k, dist[i][j] contains shortest path from i to j
 *
 * Key insight:
 *   - "Can I get from i to j faster by going through k?"
 *   - dist[i][j] = min(dist[i][j], dist[i][k] + dist[k][j])
 *
 * Trade-offs vs other algorithms:
 *   - Simpler than running Dijkstra/Bellman-Ford V times
 *   - O(V^3) time regardless of edge density
 *   - Works with negative weights (detects negative cycles)
 */
public class FloydWarshall {

    /** Constant representing no edge / infinite distance. */
    public static final int INF = Integer.MAX_VALUE / 2; // Avoid overflow when adding

    /**
     * Result of Floyd-Warshall algorithm.
     */
    public static class Result {
        /** Shortest distances between all pairs. dist[i][j] = shortest path from i to j. */
        public final int[][] distances;

        /** Next vertex on shortest path. next[i][j] = next vertex after i on path to j. */
        public final int[][] next;

        /** True if graph contains a negative cycle. */
        public final boolean hasNegativeCycle;

        public Result(int[][] distances, int[][] next, boolean hasNegativeCycle) {
            this.distances = distances;
            this.next = next;
            this.hasNegativeCycle = hasNegativeCycle;
        }
    }

    /**
     * Computes shortest paths between all pairs of vertices.
     *
     * @param graph adjacency matrix where graph[i][j] = weight of edge i->j, or INF if no edge.
     *              graph[i][i] should be 0.
     * @return Result containing distance matrix, next matrix for path reconstruction,
     *         and negative cycle flag
     */
    public static Result shortestPaths(int[][] graph) {
        int n = graph.length;
        if (n == 0) {
            return new Result(new int[0][0], new int[0][0], false);
        }

        // Initialize distance and next matrices
        int[][] dist = new int[n][n];
        int[][] next = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                dist[i][j] = graph[i][j];
                if (graph[i][j] != INF && i != j) {
                    next[i][j] = j; // next vertex on path from i to j is j (direct edge)
                } else {
                    next[i][j] = -1; // no path
                }
            }
            next[i][i] = i; // path to self
        }

        // Floyd-Warshall: consider each vertex k as intermediate
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    // Check if path i -> k -> j is shorter than current i -> j
                    if (dist[i][k] != INF && dist[k][j] != INF
                            && dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                        next[i][j] = next[i][k]; // go toward k first
                    }
                }
            }
        }

        // Check for negative cycles (diagonal becomes negative)
        boolean hasNegativeCycle = false;
        for (int i = 0; i < n; i++) {
            if (dist[i][i] < 0) {
                hasNegativeCycle = true;
                break;
            }
        }

        return new Result(dist, next, hasNegativeCycle);
    }

    /**
     * Checks if the graph contains a negative cycle.
     *
     * @param graph adjacency matrix
     * @return true if negative cycle exists
     */
    public static boolean hasNegativeCycle(int[][] graph) {
        return shortestPaths(graph).hasNegativeCycle;
    }

    /**
     * Reconstructs the shortest path from source to target.
     *
     * @param next the next matrix from Floyd-Warshall result
     * @param source starting vertex
     * @param target destination vertex
     * @return list of vertices in path order, or empty if no path exists
     */
    public static java.util.List<Integer> reconstructPath(int[][] next, int source, int target) {
        java.util.List<Integer> path = new java.util.ArrayList<>();

        // No path exists
        if (next[source][target] == -1) {
            if (source == target) {
                path.add(source);
            }
            return path;
        }

        // Follow next pointers from source to target
        int current = source;
        while (current != target) {
            path.add(current);
            current = next[current][target];
            // Safety check to avoid infinite loop (shouldn't happen with valid input)
            if (current == -1) {
                return new java.util.ArrayList<>();
            }
        }
        path.add(target);

        return path;
    }

    /**
     * Creates an adjacency matrix from edge list.
     * Utility method to convert edge list to matrix format.
     *
     * @param numVertices number of vertices
     * @param edges array of [from, to, weight] triples
     * @return adjacency matrix suitable for Floyd-Warshall
     */
    public static int[][] createMatrix(int numVertices, int[][] edges) {
        int[][] matrix = new int[numVertices][numVertices];

        // Initialize with INF
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                matrix[i][j] = (i == j) ? 0 : INF;
            }
        }

        // Add edges
        for (int[] edge : edges) {
            int from = edge[0];
            int to = edge[1];
            int weight = edge[2];
            matrix[from][to] = weight;
        }

        return matrix;
    }
}
