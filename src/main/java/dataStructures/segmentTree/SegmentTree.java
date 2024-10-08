package dataStructures.segmentTree;

/**
 * Implementation of a Segment Tree. Uses SegmentTreeNode as a helper node class.
 */
public class SegmentTree {
    private SegmentTreeNode root;
    private int[] array;

    /**
     * Helper node class. Used internally.
     */
    private class SegmentTreeNode {
        private SegmentTreeNode leftChild;   // left child
        private SegmentTreeNode rightChild;  // right child
        private int start; // start idx of range captured
        private int end; // end idx of range captured
        private int sum; // sum of all elements between start and end index inclusive

        /**
         * Constructor
         * @param leftChild
         * @param rightChild
         * @param start
         * @param end
         * @param sum
         */
        public SegmentTreeNode(SegmentTreeNode leftChild, SegmentTreeNode rightChild, int start, int end, int sum) {
            this.leftChild = leftChild;
            this.rightChild = rightChild;
            this.start = start;
            this.end = end;
            this.sum = sum;
        }
    }

    /**
     * Constructor.
     * @param nums
     */
    public SegmentTree(int[] nums) {
        root = buildTree(nums, 0, nums.length - 1);
        array = nums;
    }

    private SegmentTreeNode buildTree(int[] nums, int start, int end) {
        if (start == end) {
            return new SegmentTreeNode(null, null, start, end, nums[start]);
        }
        int mid = start + (end - start) / 2;
        SegmentTreeNode left = buildTree(nums, start, mid);
        SegmentTreeNode right = buildTree(nums, mid + 1, end);
        return new SegmentTreeNode(left, right, start, end, left.sum + right.sum);
    }

    /**
     * Queries the sum of all values in the specified range.
     * @param leftEnd
     * @param rightEnd
     * @return the sum.
     */
    public int query(int leftEnd, int rightEnd) {
        return query(root, leftEnd, rightEnd);
    }

    private int query(SegmentTreeNode node, int leftEnd, int rightEnd) {
        // this is the case when node's range is completely within the query's range:
        // Node's range:          <--start-----end-->
        // range query :        ^                     ^  --> capture the sum at this node directly!
        if (leftEnd <= node.start && node.end <= rightEnd) {
            return node.sum;
        }
        int rangeSum = 0;
        int mid = node.start + (node.end - node.start) / 2;
        // Consider how range query interact with node's range

        // Node's range                     :       <--start----------mid----------end-->
        // leftEnd exists to the left of mid: Either  ^   Or    ^
        // Range query INVOLVES interaction with left child node that spans from indices <start> to <mid>
        if (leftEnd <= mid) {
            rangeSum += query(node.leftChild, leftEnd, Math.min(rightEnd, mid));
        }

        // Node's range                     :       <--start----------mid----------end-->
        // rightEnd exists to the right of mid:                      Either  ^   Or    ^
        // Range query INVOLVES interaction with right child node that spans from indices <mid+1> to <end>
        if (mid + 1 <= rightEnd) {
            rangeSum += query(node.rightChild, Math.max(leftEnd, mid + 1), rightEnd);
        }
        return rangeSum;
    }

    /**
     * Updates the segment tree based on updates to the array at the specified index with the specified value.
     * @param idx
     * @param val
     */
    public void update(int idx, int val) {
        if (idx > array.length) {
            return;
        }
        array[idx] = val;
        update(root, idx, val);
    }

    private void update(SegmentTreeNode node, int idx, int val) {
        if (node.start == node.end && node.start == idx) {
            node.sum = val; // node is holding a single value; now updated
            return;
        }
        int mid = node.start + (node.end - node.start) / 2;
        if (idx <= mid) {
            update(node.leftChild, idx, val);
        } else {
            update(node.rightChild, idx, val);
        }
        node.sum = node.leftChild.sum + node.rightChild.sum; // propagate update up
    }
}
