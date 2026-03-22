package algorithms.graph.dijkstra;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Dijkstra's Algorithm for Single-Source Shortest Path.
 *
 * Idea:
 *   - Greedy approach: always process the unvisited vertex with smallest distance
 *   - Use a priority queue (min-heap) to efficiently get the minimum
 *   - Once a vertex is "finalized," its distance is guaranteed to be shortest
 *
 * Key insight:
 *   - With non-negative weights, the closest unvisited vertex cannot be improved
 *   - This greedy choice is safe because all remaining paths are longer
 *
 * Limitation:
 *   - Does NOT work with negative edge weights (greedy assumption fails)
 */
public class Dijkstra {

    /**
     * Represents a weighted directed edge.
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
     * Result of Dijkstra's algorithm.
     */
    public static class Result {
        /** Shortest distances from source. Integer.MAX_VALUE if unreachable. */
        public final int[] distances;

        /** Parent array for path reconstruction. -1 if no parent. */
        public final int[] parents;

        public Result(int[] distances, int[] parents) {
            this.distances = distances;
            this.parents = parents;
        }
    }

    /**
     * Computes shortest paths from source to all vertices using Dijkstra's algorithm.
     *
     * @param numVertices number of vertices (0 to numVertices-1)
     * @param graph adjacency list where graph.get(u) contains edges from u
     * @param source starting vertex
     * @return Result containing distances and parents arrays
     */
    public static Result shortestPath(int numVertices, List<List<Edge>> graph, int source) {
        // TODO: Implement Dijkstra's algorithm
        // init. distances to INF, source to 0
        // Use PriorityQueue<int[]> where int[] = {vertex, distance}
        int[] distances = new int[numVertices];
        int[] parents = new int[numVertices];

        for (int i = 0; i < numVertices; i++) {
            distances[i] = Integer.MAX_VALUE;
            parents[i] = -1;
        }
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Integer.compare(a[1], b[1]));
        pq.offer(new int[] {source, 0});
        Set<Integer> seen = new HashSet<>(); // not necessary. see below.

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int vertex = curr[0];
            int dist = curr[1];
            if (seen.contains(vertex)) { // alt, check if dist > distances[vertex]
                continue;
            }
            distances[vertex] = dist;
            seen.add(vertex);
            for (Edge edge : graph.get(vertex)) {
                if (!seen.contains(edge.to) && dist + edge.weight < distances[edge.to]) {
                    pq.offer(new int[] {edge.to, dist + edge.weight});
                    distances[edge.to] = dist + edge.weight;
                    parents[edge.to] = vertex;
                }
            }
        }
        return new Result(distances, parents);
    }

    /**
     * Computes shortest distance from source to a specific target.
     * Can terminate early once target is reached.
     *
     * @param numVertices number of vertices
     * @param graph adjacency list
     * @param source starting vertex
     * @param target destination vertex
     * @return shortest distance, or Integer.MAX_VALUE if unreachable
     */
    public static int shortestDistance(int numVertices, List<List<Edge>> graph,
                                        int source, int target) {
        // TODO: Implement with early termination
        // Same as shortestPath but return when target is popped from queue
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Integer.compare(a[1], b[1]));
        pq.offer(new int[] {source, 0});
        Set<Integer> seen = new HashSet<>(); // not necessary. see below.

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int vertex = curr[0];
            int dist = curr[1];
            if (vertex == target) {
                return dist;
            }
            if (seen.contains(vertex)) { // alt, check if dist > distances[vertex]
                continue;
            }
            seen.add(vertex);
            for (Edge edge : graph.get(vertex)) {
                if (!seen.contains(edge.to)) {
                    pq.offer(new int[] {edge.to, dist + edge.weight});
                }
            }
        }
        return Integer.MAX_VALUE;
    }

    /**
     * Reconstructs the shortest path from source to target.
     *
     * @param parents parent array from Dijkstra result
     * @param source starting vertex
     * @param target destination vertex
     * @return list of vertices in path order, or empty if unreachable
     */
    public static List<Integer> reconstructPath(int[] parents, int source, int target) {
        // TODO: Implement path reconstruction
        // Follow parent pointers from target back to source, then reverse
        List<Integer> path = new ArrayList<>();
        // note: source has parent -1, so loop will stop when we reach it
        for (int at = target; at != -1; at = parents[at]) {
            path.add(at);
        }
        Collections.reverse(path);
        // make sure source is actually reachable
        // note, without this check, an unreachable target would return a path with just the target
        if (path.get(0) != source) { // target was unreachable
            return new ArrayList<>();
        }
        return path;
    }

    /**
     * Helper to build adjacency list from edge definitions.
     *
     * @param numVertices number of vertices
     * @param edges array of [from, to, weight] triples
     * @return adjacency list suitable for Dijkstra
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
        }
        return graph;
    }
}
