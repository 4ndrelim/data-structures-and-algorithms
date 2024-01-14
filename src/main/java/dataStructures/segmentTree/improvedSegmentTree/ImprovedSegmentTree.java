package dataStructures.segmentTree.improvedSegmentTree;

/**
 * Improved segment tree that does not require O(2^n) space complexity
 * Uses TreeNode to store relevant information for range sum queries
 */
public class ImprovedSegmentTree {
    private final TreeNode tree;

    public ImprovedSegmentTree(int[] nums) {
        this.tree = buildTree(nums, 0, nums.length - 1);
    }

    private TreeNode buildTree(int[] nums, int start, int end) {
        TreeNode curr = new TreeNode(start, end);
        if (start == end) {
            curr.setSum(nums[start]);
        } else {
            int m = start + (end - start) / 2;
            TreeNode left = buildTree(nums, start, m);
            TreeNode right = buildTree(nums, m + 1, end);
            curr.setLeft(left);
            curr.setRight(right);
            curr.setSum(left.getSum() + right.getSum());
        }
        return curr;
    }

    private void update(TreeNode root, int idx, int val) {
        if (root.getStart() == root.getEnd()) {
            root.setSum(val);
        } else {
            if (idx <= root.getLeft().getEnd()) {
                update(root.getLeft(), idx, val);
            } else {
                update(root.getRight(), idx, val);
            }
            root.setSum(root.getLeft().getSum() + root.getRight().getSum());
        }
    }

    /**
     * Updates the values of the idx node to the given val.
     *
     * @param idx the idx of the intended node to be updated.
     * @param val the new value.
     */
    public void update(int idx, int val) {
        if (idx > this.tree.getEnd()) {
            return;
        }
        update(this.tree, idx, val);
    }

    private int query(TreeNode root, int left, int right) {
        if (root.getStart() == root.getEnd()) {
            return root.getSum();
        } else {
            if (left <= root.getStart() && root.getEnd() <= right) {
                return root.getSum();
            }
            int ans = 0;
            int m = root.getStart() + (root.getEnd() - root.getStart()) / 2;
            if (left <= m) {
                ans += query(root.getLeft(), left, Math.min(right, root.getLeft().getEnd()));
            }
            if (m + 1 <= right) {
                ans += query(root.getRight(), Math.max(left, root.getRight().getStart()), right);
            }
            return ans;
        }
    }

    /**
     * Returns the sum of values in the range.
     *
     * @param left  the lower range.
     * @param right the upper range.
     * @return the total sum of values in the range
     */
    public int sumRange(int left, int right) {
        return query(this.tree, left, right);
    }
}
