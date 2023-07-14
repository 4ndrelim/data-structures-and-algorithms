package src.algorithms.graphs.util;

public class TreeNode {
    int val;
    TreeNode left = null;
    TreeNode right = null;

    public TreeNode(int val) {
        this.val = val;
    }

    public TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    public int getVal() { return this.val; }
    public TreeNode getLeft() { return this.left; }
    public TreeNode getRight() { return this.right; }

    public void setLeft(TreeNode left) { this.left = left; }
    public void setRight(TreeNode right) { this.right = right; }

    @Override
    public boolean equals(Object other) {
        if (other == this) { return true; }
        if (!(other instanceof TreeNode)) { return false; }
        TreeNode Treenode = (TreeNode) other;
        return this.val == Treenode.val;
    }

    @Override
    public int hashCode() { return this.val; }

    @Override
    public String toString() { return String.valueOf(this.val); }

}
