package algorithms.graphs;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import algorithms.graphs.util.BinaryTreeNode;

/**
 * Implementation of DFS
 * <p>
 * Depth-First search is a graph traversal algorithm that utilizes a stack (LIFO) data structure
 * It is useful in finding a shortest-path solution (IN TREES), or a single solution in graphs
 * This method is also used to obtain order-traversals of trees.
 * <p>
 * In general, DFS works as such:
 * - Start with a stack that contains the root node
 * - While the stack is not empty:
 * - Pop a vertex from the stack
 * - Push all neighbours to the stack if they have not been visited yet
 * - Update any variables as needed
 * <p>
 * Time: O(V + E), where V is the number of vertices/nodes, and E is the number of edges in the graph
 * Explanation: Each vertex is popped in O(1) time from the stack exactly once, hence O(V)
 * For each edge, we must check in O(1) time if we have visited the adjacent vertex to know
 * whether to push it into the stack, hence O(E).
 * Note that if the graph is a forest, DFS will likely terminate earlier, as fewer vertices are traversed
 * <p>
 * Space: O(V): We utilize a Hashset to store the vertices we have visited already. In the worst case we have a
 * connected graph where all vertices are traversed, and our Hashset stores all O(V) vertices.
 * Further, we use a stack to hold the vertices to be traversed. In the worst case, the root will have all
 * other vertices as neighbours, and our stack will contain O(V) vertices.
 * <p>
 * ** Note: The above description assumes an adjacency list in order to consider neighbours in an efficient manner
 * If an adjacency matrix were used, it would cost O(V) to find neighbours for a single vertex, making our
 * average case time complexity O(V^2) for a connected graph
 * <p>
 * The implementation demonstrates the use of DFS in finding the order traversals of a binary tree
 * The tree is represented using a custom BinaryTreeNode class
 */

public class DepthFirstSearch {

    /**
     * Returns the preorder traversal of the tree.
     *
     * @param root the node to start the traversal from.
     * @return the list of nodes in the order they were visited.
     */
    public static List<Integer> preOrder(BinaryTreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        List<Integer> traversal = new ArrayList<>();
        Stack<BinaryTreeNode> stack = new Stack<>();
        stack.push(root);

        while (!stack.empty()) {
            BinaryTreeNode curr = stack.pop();
            traversal.add(curr.getVal());
            if (curr.getRight() != null) {
                stack.push(curr.getRight());
            }
            if (curr.getLeft() != null) {
                stack.push(curr.getLeft());
            }
        }

        return traversal;
    }

    /**
     * Returns the inorder traversal of the tree.
     *
     * @param root the node to start the traversal from.
     * @return the list of nodes in the order they were visited.
     */
    public static List<Integer> inOrder(BinaryTreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        List<Integer> traversal = new ArrayList<>();
        traversal.add(root.getVal());
        if (root.getLeft() == null && root.getRight() == null) {
            return traversal;
        } else {
            // we combine the traversal of the left subtree with the root and the traversal of the right subtree
            traversal.addAll(inOrder(root.getRight()));
            List<Integer> left = inOrder(root.getLeft());
            left.addAll(traversal);
            return left;
        }
    }

    /**
     * Returns the postorder traversal of the tree.
     *
     * @param root the node to start the traversal from.
     * @return the list of nodes in the order they were visited.
     */
    public static List<Integer> postOrder(BinaryTreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        List<Integer> traversal = new ArrayList<>();
        traversal.add(root.getVal());
        if (root.getLeft() == null && root.getRight() == null) {
            return traversal;
        } else {
            // we combine the traversal of the left and right subtrees with the root
            List<Integer> leftAndRight = postOrder(root.getLeft());
            leftAndRight.addAll(postOrder(root.getRight()));
            leftAndRight.addAll(traversal);
            return leftAndRight;
        }
    }

    /**
     * Returns the preorder traversal of the tree. Prints the nodes visited to std::out as a side-effect.
     *
     * @param root the node to start the traversal from.
     * @return the list of nodes in the order they were visited.
     */
    public static List<Integer> preOrderVisualize(BinaryTreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        List<Integer> traversal = new ArrayList<>();
        Stack<BinaryTreeNode> stack = new Stack<>();
        stack.push(root);

        while (!stack.empty()) {
            System.out.println("Current stack: " + stack + ", Current traversal: " + traversal);
            BinaryTreeNode curr = stack.pop();
            System.out.println("Popped Node: " + curr.getVal());
            traversal.add(curr.getVal());
            System.out.println("Current stack: " + stack + ", Current traversal: " + traversal);

            if (curr.getRight() != null) {
                stack.push(curr.getRight());
                System.out.println("Pushing right node: " + curr.getRight().getVal());
                System.out.println(
                    "Current stack: " + stack + ", Current traversal: " + traversal);
            }
            if (curr.getLeft() != null) {
                stack.push(curr.getLeft());
                System.out.println("Pushing left node: " + curr.getLeft().getVal());
                System.out.println(
                    "Current stack: " + stack + ", Current traversal: " + traversal);
            }
        }

        return traversal;
    }

}
