import java.util.*;

/**
 * Data structure that is commonly used to solve range sum queries in O(logn)
 * 
 */
public class SegmentTree {
    int[] tree;
    int n;
    int[] arr;

    public SegmentTree(int[] nums) {
        this.arr = nums;
        this.n = nums.length;
        this.tree = new int[(int) Math.pow(2, this.n)];
        buildTree(0, 0, this.n-1);
    }

    private void buildTree(int nodeIdx, int start, int end) {
        if (start == end) {
            this.tree[nodeIdx] = this.arr[start];
        } else {
            int mid = start + (end-start)/2;
            buildTree(nodeIdx * 2 + 1, start, mid);
            buildTree(nodeIdx * 2 + 2, mid+1, end);
            tree[nodeIdx] = tree[nodeIdx * 2 + 1] + tree[nodeIdx * 2 + 2];
        }
    }

    public void update(int index, int val){
        return;
    }

    public int sumRange(int left, int right) {
        return query(0, 0, this.n-1, left, right);
    }

    private int query(int nodeIdx, int start, int end, int left, int right) {
        if (start > right || end < left) {
            return 0;
        }
        if (left <= start && right >= end) {
            return this.tree[nodeIdx];
        } else {
            int m = start + (end-start)/2;
            return query(nodeIdx * 2 + 1, start, m, left, right) + query(nodeIdx * 2 + 2, m+1, end, left, right);
        }
    }


}