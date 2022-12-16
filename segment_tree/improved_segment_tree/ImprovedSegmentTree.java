import java.util.*;
/**
 * Improved segment tree that does not require O(2^n) space complexity
 * Uses TreeNode to store relevant information for range sum queries
 */

 public class ImprovedSegmentTree {
    TreeNode tree;

    private TreeNode buildTree(int[] nums, int start, int end) {
        TreeNode curr = new TreeNode(start, end);
        if (start == end) {
            curr.sum = nums[start];
        } else {
            int m = start + (end-start)/2;
            TreeNode left = buildTree(nums, start, m);
            TreeNode right = buildTree(nums, m+1, end);
            curr.left = left;
            curr.right = right;
            curr.sum = left.sum + right.sum;
        }
        return curr;
    }

    public ImprovedSegmentTree(int[] nums) {
        this.tree = buildTree(nums, 0, nums.length-1);
    }

    public void update(int idx, int val) {

    }

    private int query(TreeNode root, int left, int right) {
        if (root.start == root.end) {
            return root.sum;
        } else {
            if (left <= root.start && root.end <= right) {
                return root.sum;
            }
            int ans = 0;
            int m = root.start + (root.end - root.start)/2;
            if (left <= m) {
                ans += query(root.left, left, Math.min(right, root.left.end));
            }
            if (m+1 <= right) {
                ans += query(root.right, Math.max(left, root.right.start), right);
            }
            return ans;
        }
    }

    public int sumRange(int left, int right) {
        return query(this.tree, left, right);
    }
 }