package src.algorithms.graphs;

import java.util.*;
import src.algorithms.graphs.util.TreeNode;

/**
 * Implementation of DFS
 *
 * Depth-First search is a graph traversal algorithm that utilizes a stack (FIFO) data structure
 * It is useful in finding a shortest-path solution (IN TREES), or a single solution in graphs
 * This method is also used to obtain order-traversals of trees.
 *
 * In general, DFS works as such:
 *  - Start with a stack that contains the root node
 *  - While the stack is not empty:
 *      - Pop a vertex from the stack
 *      - Push all neighbours to the stack if they have not been visited yet
 *      - Update any variables as needed
 *
 * Time: O(V + E), where V is the number of vertices/nodes, and E is the number of edges in the graph
 * Explanation: Each vertex is popped in O(1) time from the stack exactly once, hence O(V)
 *              For each edge, we must check in O(1) time if we have visited the adjacent vertex to know
 *              whether to push it into the stack, hence O(E).
 *              Note that if the graph is a forest, DFS will likely terminate earlier, as fewer vertices are traversed
 * Space: O(V): We utilize a Hashset to store the vertices we have visited already. In the worst case we have a
 *              connected graph where all vertices are traversed, and our Hashset stores O(V) vertices.
 *              Further, we use a stack to hold the vertices to be traversed. In the worst case, the root will have all
 *              other vertices as neighbours, and our stack will contain O(V) vertices.
 * ** Note: The above description assumes an adjacency list in order to consider neighbours in an efficient manner
 *          If an adjacency matrix were used, it would cost O(V) to find neighbours for a single vertex, making our
 *          average case time complexity O(V^2) for a connected graph
 *
 * The implementation demonstrates the use of DFS in finding the pre-order (Root, Left, Right) traversal of a binary tree
 * The tree is represented using a custom TreeNode class
 */

public class depthFirstSearch {

    public static List<Integer> preOrder(TreeNode root) {
        if (root == null) { return new ArrayList<>(); }
        List<Integer> traversal = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);

        while (!stack.empty()) {
            TreeNode curr = stack.pop();
            traversal.add(curr.getVal());
            if (curr.getRight() != null) { stack.push(curr.getRight()); }
            if (curr.getLeft() != null) { stack.push(curr.getLeft()); }
        }

        return traversal;
    }

    // call this for visualization of process
    public static List<Integer> preOrderVisualize(TreeNode root) {
        if (root == null) { return new ArrayList<>(); }
        List<Integer> traversal = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);

        while (!stack.empty()) {
            System.out.println("Current stack: " + stack.toString() + ", Current traversal: " + traversal.toString());
            TreeNode curr = stack.pop();
            System.out.println("Popped Node: " + Integer.toString(curr.getVal()));
            traversal.add(curr.getVal());
            System.out.println("Current stack: " + stack.toString() + ", Current traversal: " + traversal.toString());

            if (curr.getRight() != null) {
                stack.push(curr.getRight());
                System.out.println("Pushing right node: " + Integer.toString(curr.getRight().getVal()));
                System.out.println("Current stack: " + stack.toString() + ", Current traversal: " + traversal.toString());
            }
            if (curr.getLeft() != null) {
                stack.push(curr.getLeft());
                System.out.println("Pushing left node: " + Integer.toString(curr.getLeft().getVal()));
                System.out.println("Current stack: " + stack.toString() + ", Current traversal: " + traversal.toString());
            }
        }

        return traversal;
    }

}
