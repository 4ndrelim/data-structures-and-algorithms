package algorithms.orthogonalRangeSearching.oneDim;

import algorithms.orthogonalRangeSearching.RangeTreeNode;
import dataStructures.avlTree.Node;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;

public class OrthogonalRangeSearching {

    public static RangeTreeNode buildTree(int[] inputs, int start, int end) {
        //build range tree from inputs
        int mid = (end + start) / 2;
        Arrays.sort(inputs);

        if (start > end) {
            return null;
        } else if (end - start + 1 > 3) {
            return new RangeTreeNode(inputs[mid], buildTree(inputs, start, mid), buildTree(inputs, mid + 1, end));
        } else if (end - start + 1 == 3) {
            return new RangeTreeNode(inputs[mid], buildTree(inputs, start, mid), new RangeTreeNode(inputs[end]));
        } else if (end - start + 1 == 2) {
            return new RangeTreeNode(inputs[mid], new RangeTreeNode(inputs[start]), new RangeTreeNode(inputs[end]));
        } else {
            return new RangeTreeNode(inputs[mid]);
        }
    }

    public static void configureTree(RangeTreeNode node) {
        if (node.getLeft() == null && node.getRight() == null) {
            node.setHeight(0);
        } else if (node.getLeft() == null) {
            configureTree(node.getRight());
            node.setHeight(node.getRight().getHeight() + 1);
            node.getRight().setParent(node);
        } else if (node.getRight() == null) {
            configureTree(node.getLeft());
            node.setHeight(node.getLeft().getHeight() + 1);
            node.getLeft().setParent(node);
        } else {
            configureTree(node.getLeft());
            configureTree(node.getRight());
            node.setHeight(Math.max(node.getLeft().getHeight(), node.getRight().getHeight()) + 1);
            node.getLeft().setParent(node);
            node.getRight().setParent(node);
        }
    }

    public static RangeTreeNode findSplit(RangeTreeNode root, int low, int high) {
        RangeTreeNode v = root;

        while (true) {
            if (v == null) {
                return null;
            } else {
                if (high <= v.getVal()) {
                    v = v.getLeft();
                } else if (low > v.getVal()) {
                    v = v.getRight();
                } else {
                    break;
                }
            }
        }
        return v;
    }

    public static void allLeafTraversal(RangeTreeNode v, List<Integer> result) {
        if (v != null) {
            if (v.getLeft() != null) {
                allLeafTraversal(v.getLeft(), result);
            }
            if (v.getLeft() == null && v.getRight() == null) { // leaf
                result.add(v.getVal());
            }
            if (v.getRight() != null) {
                allLeafTraversal(v.getRight(), result);
            }
        }
    }

    public static void leftTraversal(RangeTreeNode v, int low, List<Integer> result) {
        if (v != null) {
            if (v.getLeft() == null && v.getRight() == null) { // leaf
                result.add(v.getVal());
            } else {
                if (low <= v.getVal()) {
                    leftTraversal(v.getLeft(), low, result);
                    allLeafTraversal(v.getRight(), result);
                } else { //definitely a qualifying leaf has to exist
                    leftTraversal(v.getRight(), low, result);
                }
            }
        }
    }

    public static void rightTraversal(RangeTreeNode v, int high, List<Integer> result) {
        if (v != null) {
            if (v.getLeft() == null && v.getRight() == null && v.getVal() < high) { // leaf, need extra check
                result.add(v.getVal());
            }
            else {
                if (high > v.getVal()) {
                    allLeafTraversal(v.getLeft(), result);
                    rightTraversal(v.getRight(), high, result);
                } else { //might or might not exist, we are just exploring
                    rightTraversal(v.getLeft(), high, result);
            }
            }
        }
    }

    public static Object[] search(RangeTreeNode tree, int low, int high) {
        RangeTreeNode splitNode = OrthogonalRangeSearching.findSplit(tree, low, high);
        ArrayList<Integer> result = new ArrayList<>();
        if (splitNode != null) {
            leftTraversal(splitNode.getLeft(), low, result);
            rightTraversal(splitNode.getRight(), high, result);
        }
        return result.toArray();
    }

    // Functions from here onwards are designed to support dynamic updates.

    public static RangeTreeNode insert(RangeTreeNode node, int val) {
        if (val < node.getVal()) {
            if (node.getLeft() != null) {
                node.setLeft(insert(node.getLeft(), val));
            } else {
                node.setLeft(new RangeTreeNode(val));
                node.getLeft().setParent(node);
                node.setRight(new RangeTreeNode(node.getVal()));
                node.getRight().setParent(node);
                node.setVal(val);
            }
        } else if (val > node.getVal()) {
            if (node.getRight() != null) {
                node.setRight(insert(node.getRight(), val));
            } else {
                node.setLeft(new RangeTreeNode(node.getVal()));
                node.getLeft().setParent(node);
                node.setRight(new RangeTreeNode(val));
                node.getRight().setParent(node);
                node.setVal(node.getVal());

            }
        } else {
            throw new RuntimeException("Duplicate key not supported!");
        }
        return rebalance(node);
    }

    public static int height(RangeTreeNode node) {
        return node == null ? -1 : node.getHeight();
    }

    /**
     * Update height of node in range tree during rebalancing.
     * @param node node whose height is to be updated
     */
    private static void updateHeight(RangeTreeNode node) {
        node.setHeight(1 + Math.max(height(node.getLeft()), height(node.getRight())));
    }

    /**
     * Get balance factor to check if height-balanced property is violated.
     * Note: negative value means tree is right heavy,
     *       positive value means tree is left heavy,
     *       0 means tree is balanced in weight.
     * @param node check balance factor of node
     * @return int value representing the balance factor
     */
    private static int getBalance(RangeTreeNode node) {
      return node == null
        ? 0
        : height(node.getLeft()) - height(node.getRight());
    }

    /**
     * Performs a right rotation on the specified node.
     * Note that function should be called only if the
     * node has a left child since it will be the
     * new root.
     * @param n node to perform right rotation on.
     * @return the new root after rotation.
     */
    private static RangeTreeNode rotateRight(RangeTreeNode n) {
        RangeTreeNode newRoot = n.getLeft();
        RangeTreeNode newLeftSub = newRoot.getRight();
        newRoot.setRight(n);
        n.setLeft(newLeftSub);

        newRoot.setParent(n.getParent());
        n.setParent(newRoot);

        updateHeight(n);
        updateHeight(newRoot);
        return newRoot;
    }

    /**
     * Performs a left rotation on the specified node.
     * Note that function should be called only if the
     * node has a right child since it will be the
     * new root.
     * @param n node to perform left rotation on
     * @return new root after rotation
     */
    private static RangeTreeNode rotateLeft(RangeTreeNode n) {
        RangeTreeNode newRoot = n.getRight();
        RangeTreeNode newRightSub = newRoot.getLeft();
        newRoot.setLeft(n);
        n.setRight(newRightSub);

        newRoot.setParent(n.getParent());
        n.setParent(newRoot);

        updateHeight(n);
        updateHeight(newRoot);
        return newRoot;
    }

    /**
     * Rebalances a node in the tree based on balance factor.
     * @param n node to be rebalanced
     * @return new root after rebalancing
     */
    private static RangeTreeNode rebalance(RangeTreeNode n) {
        updateHeight(n);
        int balance = getBalance(n);
        if (balance < -1) { // right-heavy case
            System.out.println("rebalancing");
            if (height(n.getRight().getLeft()) > height(n.getRight().getRight())) {
                n.setRight(rotateRight(n.getRight()));
            }
            n = rotateLeft(n);
        } else if (balance > 1) { // left-heavy case
            System.out.println("rebalancing");
            if (height(n.getLeft().getRight()) > height(n.getLeft().getLeft())) {
                n.setLeft(rotateLeft(n.getLeft()));
            }
            n = rotateRight(n);
        }
        return n;
    }

    public static RangeTreeNode delete(RangeTreeNode node, int val) {
        if (node.getLeft().getLeft() == null && node.getLeft().getRight() == null
                && val == node.getLeft().getVal()) { // left node is the leaf node
            node.setVal(node.getRight().getVal());
            node.setLeft(null);
            node.setRight(null);
        } else if (node.getRight().getLeft() == null && node.getRight().getRight() == null
                && val == node.getRight().getVal()) { // right node is the leaf node
            node.setLeft(null);
            node.setRight(null);
        } else {
            if (val <= node.getVal()) {
                if (node.getLeft() != null) {
                    node.setLeft(delete(node.getLeft(), val));
                }
                if (val == node.getVal()) { // duplicate node
                    node.setVal(getMostRight(node.getLeft()).getVal()); // update the duplicate key
                }
            } else {
                if (node.getRight() != null) {
                    node.setRight(delete(node.getRight(), val));
                }
            }
        }
        return rebalance(node);
    }

    private static RangeTreeNode getMostRight(RangeTreeNode n) {
        if (n.getRight() == null) {
            return n;
        } else {
            return getMostRight(n.getRight());
        }
    }

    public static void levelOrderTraversal(RangeTreeNode root) {
        if (root == null) {
            return;
        }

        Queue<RangeTreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            RangeTreeNode current = queue.poll();
            System.out.print(current.getVal() + " ");
            if (current.getLeft() != null) {
                queue.add(current.getLeft());
            }
            if (current.getRight() != null) {
                queue.add(current.getRight());
            }
        }
    }

}
