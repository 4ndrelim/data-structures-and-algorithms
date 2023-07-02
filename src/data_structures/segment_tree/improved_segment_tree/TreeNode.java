package src.data_structures.segment_tree.improved_segment_tree;

/**
 * helper TreeNode class to store relevant information
 * of binary segment tree
 */
public class TreeNode {
    TreeNode left, right;
    int sum;
    int start, end;

    public TreeNode(int s, int e) {
        this.start = s;
        this.end = e;
    }
}