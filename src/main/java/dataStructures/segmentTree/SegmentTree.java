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
         * Constructor.
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

    /**
     * Recursively builds the segment tree using divide-and-conquer.
     *
     * Intuition: Each node represents a range [start, end] of the original array.
     * - Split the range in half: left child gets [start, mid], right child gets [mid+1, end]
     * - Build children first (post-order), then combine: parent.sum = left.sum + right.sum
     * - Base case: range of size 1 (start == end) → leaf node holding single element
     */
    private SegmentTreeNode buildTree(int[] nums, int start, int end) {
        // Base case: leaf node represents a single element
        if (start == end) {
            return new SegmentTreeNode(null, null, start, end, nums[start]);
        }
        // Recursive case: split range and build children, then combine
        int mid = start + (end - start) / 2;
        SegmentTreeNode left = buildTree(nums, start, mid);       // left child: [start, mid]
        SegmentTreeNode right = buildTree(nums, mid + 1, end);    // right child: [mid+1, end]
        return new SegmentTreeNode(left, right, start, end, left.sum + right.sum);
    }

    /**
     * Queries the sum of all values in the specified range.
     */
    public int query(int leftEnd, int rightEnd) {
        return query(root, leftEnd, rightEnd);
    }

    /**
     * Recursively queries the sum over [leftEnd, rightEnd].
     *
     * Intuition: Compare query range [leftEnd, rightEnd] with node's range [start, end].
     * Three cases:
     *   1. Node's range COMPLETELY INSIDE query → return node.sum directly (no need to go deeper)
     *   2. Query range partially overlaps left child's range → recurse left
     *   3. Query range partially overlaps right child's range → recurse right
     *
     * The key insight: if query fully contains this node's range, we don't need to
     * examine children - this node already has the precomputed sum we need.
     */
    private int query(SegmentTreeNode node, int leftEnd, int rightEnd) {
        // Case 1: Node's range [start, end] is completely inside query range [leftEnd, rightEnd]
        // Example: query [1,8], node covers [3,5] → node is fully contained, return its sum
        if (leftEnd <= node.start && node.end <= rightEnd) {
            return node.sum;
        }

        int rangeSum = 0;
        int mid = node.start + (node.end - node.start) / 2;
        // Left child covers [start, mid], right child covers [mid+1, end]

        // Case 2: Query overlaps with left child's range [start, mid]
        // This happens when leftEnd <= mid (query starts before or at mid)
        if (leftEnd <= mid) {
            rangeSum += query(node.leftChild, leftEnd, rightEnd);
        }

        // Case 3: Query overlaps with right child's range [mid+1, end]
        // This happens when rightEnd > mid (query extends past mid)
        if (mid + 1 <= rightEnd) {
            rangeSum += query(node.rightChild, leftEnd, rightEnd);
        }

        return rangeSum;
    }

    /**
     * Updates the segment tree based on updates to the array at the specified index with the specified value.
     */
    public void update(int idx, int val) {
        if (idx < 0 || idx >= array.length) {
            return;
        }
        array[idx] = val;
        update(root, idx, val);
    }

    /**
     * Recursively updates the value at index idx to val.
     *
     * Intuition: Navigate down to the leaf node representing idx, update it,
     * then propagate changes back up. Each ancestor's sum depends on its children,
     * so we must update all ancestors on the path from leaf to root.
     */
    private void update(SegmentTreeNode node, int idx, int val) {
        // Base case: reached the leaf node for this index
        if (node.start == node.end) {
            node.sum = val;
            return;
        }

        // Determine which child contains idx and recurse
        int mid = node.start + (node.end - node.start) / 2;
        if (idx <= mid) {
            update(node.leftChild, idx, val);  // idx is in left half [start, mid]
        } else {
            update(node.rightChild, idx, val); // idx is in right half [mid+1, end]
        }

        // After child is updated, recalculate this node's sum (propagate change upward)
        node.sum = node.leftChild.sum + node.rightChild.sum;
    }
}
