package algorithms.graph.dfs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Depth-First Search (DFS) - Iterative implementation using Stack.
 *
 * Idea:
 *   - Same traversal pattern as recursive DFS, but uses explicit Stack
 *   - Useful when recursion depth might cause stack overflow
 *   - Push neighbors in reverse order to match recursive traversal order
 */
public class DFSIterative {

    /**
     * Performs DFS traversal starting from the given node using a stack.
     *
     * @param graph adjacency list where graph.get(i) contains neighbors of node i
     * @param start the starting node for traversal
     * @return list of nodes in DFS traversal order
     */
    public static List<Integer> traverse(List<List<Integer>> graph, int start) {
        // TODO: Implement using Stack
        List<Integer> traverseOrder = new ArrayList<>();
        List<Integer> stack = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        stack.add(start);
        visited.add(start);

        while (!stack.isEmpty()) {
            int curr = stack.remove(stack.size() - 1); // pop from stack. O(1)
            traverseOrder.add(curr);
            for (int neighbour : graph.get(curr)) {
                if (!visited.contains(neighbour)) {
                    visited.add(neighbour);
                    stack.add(neighbour);
                }
            }
        }
        return traverseOrder;
    }

    /**
     * Checks if a path exists between two nodes using iterative DFS.
     *
     * @param graph adjacency list representation
     * @param start the starting node
     * @param end the target node
     * @return true if a path exists from start to end, false otherwise
     */
    public static boolean hasPath(List<List<Integer>> graph, int start, int end) {
        // TODO: Implement
        // Similar to traverse, but return true early if end is found
        List<Integer> stack = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        stack.add(start);
        visited.add(start);

        while (!stack.isEmpty()) {
            int curr = stack.remove(stack.size() - 1);
            if (curr == end) {
                return true;
            }
            for (int neighbour : graph.get(curr)) {
                if (!visited.contains(neighbour)) {
                    visited.add(neighbour);
                    stack.add(neighbour);
                }
            }
        }
        return false;
    }
}
