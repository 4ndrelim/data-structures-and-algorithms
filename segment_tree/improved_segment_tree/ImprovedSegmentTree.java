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

    public int sumRange(int left, int right) {
        return 0;
    }
 }