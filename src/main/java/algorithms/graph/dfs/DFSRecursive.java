package algorithms.graph.dfs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Depth-First Search (DFS) - Recursive implementation.
 *
 * Idea:
 *   - Explore as deep as possible along each branch before backtracking
 *   - Use the call stack implicitly as the "stack" data structure
 *   - Track visited nodes to avoid cycles
 *
 * Implementation hints:
 *   - Create a helper method that takes visited array and result list
 *   - Mark node as visited at the START of the recursive call
 *   - Recurse on each unvisited neighbor
 */
public class DFSRecursive {

    /**
     * Performs DFS traversal starting from the given node using recursion.
     *
     * @param graph adjacency list where graph.get(i) contains neighbors of node i
     * @param start the starting node for traversal
     * @return list of nodes in DFS traversal order
     */
    public static List<Integer> traverse(List<List<Integer>> graph, int start) {
        // TODO: Implement using recursion
        List<Integer> traverseOrder = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        dfs(graph, start, visited, traverseOrder);
        return traverseOrder;
    }

    /**
     * Checks if a path exists between two nodes using recursive DFS.
     *
     * @param graph adjacency list representation
     * @param start the starting node
     * @param end the target node
     * @return true if a path exists from start to end, false otherwise
     */
    public static boolean hasPath(List<List<Integer>> graph, int start, int end) {
        // TODO: Implement
        // lazy apporahc of simply checking if end is in visited after a full DFS traversal from start
        // ideally, we should check for end at the start of the recursive call and return true early if found
        Set<Integer> visited = new HashSet<>();
        dfs(graph, start, visited, new ArrayList<>());
        return visited.contains(end);
    }

    private static void dfs(List<List<Integer>> graph, int node,
                            Set<Integer> visited, List<Integer> result) {
        visited.add(node);
        result.add(node);
        for (int neighbour : graph.get(node)) {
            if (!visited.contains(neighbour)) {
                dfs(graph, neighbour, visited, result);
            }
        }
    }
}
