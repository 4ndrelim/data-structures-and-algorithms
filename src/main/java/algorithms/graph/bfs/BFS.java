package algorithms.graph.bfs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * Breadth-First Search (BFS) implementation.
 *
 * Idea:
 *   - Use a Queue to explore nodes level by level
 *   - Visit all neighbors at current depth before moving to next depth
 *   - Track visited nodes to avoid cycles
 */
public class BFS {

    /**
     * Performs BFS traversal starting from the given node.
     *
     * @param graph adjacency list where graph.get(i) contains neighbors of node i
     * @param start the starting node for traversal
     * @return list of nodes in BFS traversal order
     */
    public static List<Integer> traverse(List<List<Integer>> graph, int start) {
        // TODO: Implement using Queue
        // we can use linkedlist as queue as described in the readme
        // but here we will show how to implement a queue using arraylist
        List<Integer> traverseOrder = new ArrayList<>();
        List<Integer> queue = new ArrayList<>();
        queue.add(start);
        Set<Integer> visited = new HashSet<>();
        visited.add(start);
        while (!queue.isEmpty()) {
            int queueSize = queue.size();
            List<Integer> newQueue = new ArrayList<>();
            for (int i = 0; i < queueSize; i++) {
                int curr = queue.remove(queue.size() - 1); // remove from last is O(1)
                traverseOrder.add(curr);
                for (int neighbour : graph.get(curr)) {
                    if (!visited.contains(neighbour)) {
                        visited.add(neighbour);
                        newQueue.add(neighbour);
                    }
                }
            }
            queue = newQueue;
        }
        return traverseOrder;
    }

    /**
     * Checks if a path exists between two nodes using BFS.
     *
     * @param graph adjacency list representation
     * @param start the starting node
     * @param end the target node
     * @return true if a path exists from start to end, false otherwise
     */
    public static boolean hasPath(List<List<Integer>> graph, int start, int end) {
        // TODO: Implement
        // the implementation above uses arraylist as queue
        // we will use linkedlist as queue as described in the readme here
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);
        Set<Integer> visited = new HashSet<>();
        visited.add(start);
        while (!queue.isEmpty()) {
            int curr = queue.poll();
            if (curr == end) {
                return true;
            }
            for (int neighbour : graph.get(curr)) {
                // can also check if neighbour is <end> here to return true immediately
                if (!visited.contains(neighbour)) {
                    visited.add(neighbour);
                    queue.offer(neighbour);
                }
            }
        }
        return false;
    }
}
