package algorithms.graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import algorithms.graphs.util.BinaryTreeNode;
import algorithms.graphs.util.GraphNode;

/**
 * Implementation of BFS
 * <p>
 * Breadth-First search is a graph traversal algorithm that utilizes a queue (FIFO) data structure
 * It is useful in finding a shortest-hops solution in graphs
 * This method is also used to obtain level order traversals of trees.
 * <p>
 * In general, BFS works as such:
 * - Start with a queue that contains the root node
 * - While the queue is not empty:
 * - Pop a vertex from the queue
 * - Push all neighbours to the queue if they have not been visited yet
 * - Update any variables as needed
 * <p>
 * Time: O(V + E), where V is the number of vertices/nodes, and E is the number of edges in the graph
 * Explanation: Each vertex is popped in O(1) time from the stack exactly once, hence O(V)
 * For each edge, we must check in O(1) time if we have visited the adjacent vertex to know
 * whether to push it into the queue, hence O(E).
 * Note that if the graph is a forest, BFS will likely terminate earlier, as fewer vertices are traversed
 * <p>
 * Space: O(V): We utilize a Hashset to store the vertices we have visited already. In the worst case we have a
 * connected graph where all vertices are traversed, and our Hashset stores all O(V) vertices.
 * Further, we use a queue to hold the vertices to be traversed. In the worst case, the root will have all
 * other vertices as neighbours, and our queue will contain O(V) vertices.
 * <p>
 * ** Note: The above description assumes an adjacency list in order to consider neighbours in an efficient manner
 * If an adjacency matrix were used, it would cost O(V) to find neighbours for a single vertex, making our
 * average case time complexity O(V^2) for a connected graph
 * <p>
 * The implementation demonstrates the use of BFS in finding the level-order (Root, Left, Right) traversal of a
 * binary tree
 * The tree is represented using a custom BinaryTreeNode class
 */
public class BreadthFirstSearch {

    /**
     * Returns the levelOrder traversal of the tree.
     *
     * @param root the node to start the traversal from.
     * @return the list of nodes in the order they were visited.
     */
    public static List<Integer> levelOrder(BinaryTreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        List<Integer> traversal = new ArrayList<>();
        Queue<BinaryTreeNode> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            BinaryTreeNode curr = queue.remove();
            traversal.add(curr.getVal());
            if (curr.getLeft() != null) {
                queue.add(curr.getLeft());
            }
            if (curr.getRight() != null) {
                queue.add(curr.getRight());
            }
        }

        return traversal;
    }

    /**
     * Finds the number of friend hops needed to go from person A to person B.
     * Uses GraphNode, where a node holds a String of the persons name, and an edge represents a friendship
     *
     * @param personA the node to start from.
     * @param personB the node to end.
     * @return the number of hops needed.
     */
    public static int friendHops(GraphNode<String> personA, GraphNode<String> personB) {
        // Hashset to store the people we have seen already
        HashSet<GraphNode<String>> checked = new HashSet<>();
        // Hashmap to remember how many hops were needed to get to a specific friend *
        HashMap<GraphNode<String>, Integer> map = new HashMap<>();

        Queue<GraphNode<String>> queue = new LinkedList<>();
        queue.add(personA);
        // the number of hops to the person themselves is 0, so we map: personA -> 0
        map.put(personA, 0);
        int hops = 0;

        while (!queue.isEmpty()) {
            // poll the queue to get the next person to consider
            GraphNode<String> currPerson = queue.remove();
            // add the person to the checked hashset so we don't consider them again
            checked.add(currPerson);
            // grab the number of hops from the hashmap and add 1
            hops = map.get(currPerson) + 1;

            List<GraphNode<String>> neighbours = currPerson.neighbours();
            for (GraphNode<String> neighbour : neighbours) {
                if (neighbour == personB) {
                    return hops;
                }
                if (!checked.contains(neighbour)) {
                    queue.add(neighbour);
                    map.put(neighbour, hops);
                }
            }
        }
        // Returns -1 if person not found
        return -1;

        // * Note that we can actually use just the hashmap instead of both the hashmap and the hashset!
        //   This is because the hashmap supports the map.containsKey() function.
    }

    /**
     * Finds the number of friend hops needed to go from person A to person B. Prints to std::out as a side-effect.
     * Uses GraphNode, where a node holds a String of the persons name, and an edge represents a friendship
     *
     * @param personA the node to start from.
     * @param personB the node to end.
     * @return the number of hops needed.
     */
    public static int friendHopsVisualize(GraphNode<String> personA, GraphNode<String> personB) {
        // Hashset to store the people we have seen already
        HashSet<GraphNode<String>> checked = new HashSet<>();
        // Hashmap to remember how many hops were needed to get to a specific friend *
        HashMap<GraphNode<String>, Integer> map = new HashMap<>();

        Queue<GraphNode<String>> queue = new LinkedList<>();
        queue.add(personA);
        // the number of hops to the person themselves is 0, so we map: personA -> 0
        map.put(personA, 0);
        int hops = 0;

        while (!queue.isEmpty()) {
            System.out.println("Current queue: " + queue + ", current hops: " + hops);
            // poll the queue to get the next person to consider
            GraphNode<String> currPerson = queue.remove();
            // add the person to the checked hashset so we don't consider them again
            checked.add(currPerson);
            // grab the number of hops from the hashmap and add 1
            hops = map.get(currPerson) + 1;

            System.out.println("Looking at friends of: " + currPerson.toString());
            List<GraphNode<String>> neighbours = currPerson.neighbours();
            for (GraphNode<String> neighbour : neighbours) {
                if (neighbour == personB) {
                    return hops;
                }
                if (!checked.contains(neighbour)) {
                    queue.add(neighbour);
                    map.put(neighbour, hops);
                }
            }
            System.out.println("Current queue: " + queue + ", current hops: " + hops);
        }
        // Returns -1 if person not found
        return -1;
    }
}
