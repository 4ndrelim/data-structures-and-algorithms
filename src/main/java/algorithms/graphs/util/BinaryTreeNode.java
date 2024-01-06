package algorithms.graphs.util;

/**
 * Encapsulates a Binary Tree Node.
 */
public class BinaryTreeNode {
    private final int val;
    private BinaryTreeNode left = null;
    private BinaryTreeNode right = null;

    /**
     * Constructs a Binary Tree Node.
     *
     * @param val the value encapsulated in the node.
     */
    public BinaryTreeNode(int val) {
        this.val = val;
    }

    /**
     * Constructs a Binary Tree Node with a left and right child.
     *
     * @param val   the value encapsulated in the node.
     * @param left  the left child.
     * @param right the right child.
     */
    public BinaryTreeNode(int val, BinaryTreeNode left, BinaryTreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    public int getVal() {
        return this.val;
    }

    public BinaryTreeNode getLeft() {
        return this.left;
    }

    public BinaryTreeNode getRight() {
        return this.right;
    }

    public void setLeft(BinaryTreeNode left) {
        this.left = left;
    }

    public void setRight(BinaryTreeNode right) {
        this.right = right;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof BinaryTreeNode)) {
            return false;
        }
        BinaryTreeNode node = (BinaryTreeNode) other;
        return this.val == node.val;
    }

    @Override
    public int hashCode() {
        return this.val;
    }

    @Override
    public String toString() {
        return String.valueOf(this.val);
    }

}
