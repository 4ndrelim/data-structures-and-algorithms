package dataStructures.segmentTree.improvedSegmentTree;

/**
 * helper TreeNode class to store relevant information
 * of binary segment tree. Data class.
 */
public class TreeNode {
    private TreeNode left;
    private TreeNode right;
    private int sum;
    private int start;
    private int end;

    /**
     * Constructs a tree node.
     *
     * @param s start of the segment.
     * @param e end of the segment
     */
    public TreeNode(int s, int e) {
        this.start = s;
        this.end = e;
    }

    public TreeNode getLeft() {
        return left;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public TreeNode getRight() {
        return right;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }
}
