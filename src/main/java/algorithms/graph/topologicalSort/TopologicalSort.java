package algorithms.graph.topologicalSort;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Topological Sort using Kahn's Algorithm (BFS-based).
 *
 * Idea:
 *   - Track in-degree (number of incoming edges) for each vertex
 *   - Process vertices with in-degree 0 (no dependencies)
 *   - When processing a vertex, decrement in-degree of its neighbors
 *   - If a neighbor's in-degree becomes 0, add it to the queue
 *   - If not all vertices are processed, a cycle exists
 *
 * Key insight:
 *   - Vertices with in-degree 0 have no unprocessed prerequisites
 *   - Processing order respects all edge directions (u -> v means u before v)
 */
public class TopologicalSort {

    /**
     * Performs topological sort on a directed graph using Kahn's algorithm.
     *
     * @param numVertices number of vertices (0 to numVertices-1)
     * @param graph adjacency list where graph.get(u) contains vertices that u points to
     * @return list of vertices in topological order, or empty list if cycle exists
     */
    public static List<Integer> sort(int numVertices, List<List<Integer>> graph) {
        // TODO: Implement Kahn's algorithm
        int[] inDegree = new int[numVertices];
        for (List<Integer> neighbours : graph) {
            for (int neighbour : neighbours) {
                inDegree[neighbour]++;
            }
        }
        List<Integer> result = new ArrayList<>();
        List<Integer> queue = new ArrayList<>();
        for (int i = 0; i < numVertices; i++) {
            if (inDegree[i] == 0) {
                queue.add(i);
            }
        }

        while (!queue.isEmpty()) {
            List<Integer> nextQueue = new ArrayList<>();
            for (int vertex : queue) {
                result.add(vertex);
                for (int neighbour : graph.get(vertex)) {
                    inDegree[neighbour]--;
                    if (inDegree[neighbour] == 0) {
                        nextQueue.add(neighbour);
                    }
                }
            }
            queue = nextQueue;
        }

        // check for cycle
        if (result.size() != numVertices) { // not all vertices processed
            return new ArrayList<>(); // cycle exists
        }
        return result;
    }

    /**
     * Checks if the directed graph contains a cycle.
     *
     * @param numVertices number of vertices
     * @param graph adjacency list representation
     * @return true if graph has a cycle, false if it's a valid DAG
     */
    public static boolean hasCycle(int numVertices, List<List<Integer>> graph) {
        if (numVertices == 0) {
            return false; // empty graph has no cycles
        }
        List<Integer> topoSort = sort(numVertices, graph);
        return topoSort.isEmpty();
    }

    /**
     * Checks if a given ordering is a valid topological sort of the graph.
     *
     * @param numVertices number of vertices
     * @param graph adjacency list representation
     * @param ordering the ordering to validate
     * @return true if ordering is a valid topological sort
     */
    public static boolean isValidTopologicalOrder(int numVertices, List<List<Integer>> graph,
                                                   List<Integer> ordering) {
        // TODO: Implement validation
        // For each edge u -> v, u must appear before v in the ordering
        if (ordering.size() != numVertices) {
            return false; // ordering must include all vertices
        }
        Set<Integer> uniqueVertices = new HashSet<>(ordering);
        if (uniqueVertices.size() != numVertices) {
            return false; // ordering must contain unique vertices
        }

        int[] inDegree = new int[numVertices];
        for (List<Integer> neighbours : graph) {
            for (int n : neighbours) {
                inDegree[n]++;
            }
        }
        for (int vertex : ordering) {
            if (inDegree[vertex] != 0) {
                return false; // vertex has unprocessed dependencies
            }
            for (int neighbour : graph.get(vertex)) {
                inDegree[neighbour]--;
            }
        }
        return true;
    }
}
