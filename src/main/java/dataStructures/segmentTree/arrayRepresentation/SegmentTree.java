package dataStructures.segmentTree.arrayRepresentation;

/**
 * Array-based implementation of a Segment Tree.
 */
public class SegmentTree {
    private int[] tree;
    private int[] array;

    /**
     * Constructor.
     * @param nums
     */
    public SegmentTree(int[] nums) {
        tree = new int[4 * nums.length];  // Need to account for up to 4n nodes.
        array = nums;
        buildTree(nums, 0, nums.length - 1, 0);
    }

    /**
     * Builds the tree from the given array of numbers.
     * Unlikely before where we capture child nodes in the helper node class, here we capture position of child nodes
     * in the array-representation of the tree with an additional variable.
     * @param nums
     * @param start
     * @param end
     * @param idx tells us which index of the tree array we are at.
     */
    private void buildTree(int[] nums, int start, int end, int idx) {
        // recall, each node is a position in the array
        // explicitly track which position in the array to fill with idx variable
        if (start == end) {
            tree[idx] = nums[start];
            return;
        }
        int mid = start + (end - start) / 2;
        int idxLeftChild = (idx + 1) * 2 - 1; // convert from 0-based to 1-based, do computation, then revert
        buildTree(nums, start, mid, idxLeftChild);
        int idxRightChild = (idx + 1) * 2 + 1 - 1; // convert from 0-based to 1-based, do computation, then revert
        buildTree(nums, mid + 1, end, idxRightChild);
        tree[idx] = tree[idxLeftChild] + tree[idxRightChild];
    }

    /**
     * Queries the sum of all values in the specified range.
     * @param leftEnd
     * @param rightEnd
     * @return the sum.
     */
    public int query(int leftEnd, int rightEnd) {
        return query(0, 0, array.length - 1, leftEnd, rightEnd);
    }

    private int query(int nodeIdx, int startRange, int endRange, int leftEnd, int rightEnd) {
        // this is the case when:
        //                start     end
        // range query:  ^             ^  --> so simply capture the sum at this node!
        if (leftEnd <= startRange && endRange <= rightEnd) {
            return tree[nodeIdx];
        }
        int rangeSum = 0;
        int mid = startRange + (endRange - startRange) / 2;
        // Consider the 3 possible kinds of range queries
        //           start          mid          end
        // poss 1:         ^      ^
        // poss 2:             ^            ^
        // poss 3:                       ^      ^
        if (leftEnd <= mid) {
            int idxLeftChild = (nodeIdx + 1) * 2 - 1;
            rangeSum += query(idxLeftChild, startRange, mid, leftEnd, Math.min(rightEnd, mid));
        }
        if (mid + 1 <= rightEnd) {
            int idxRightChild = (nodeIdx + 1) * 2 + 1 - 1;
            rangeSum += query(idxRightChild, mid + 1, endRange, Math.max(leftEnd, mid + 1), rightEnd);
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
        update(0, 0, array.length - 1, idx, val);
    }

    private void update(int nodeIdx, int startRange, int endRange, int idx, int val) {
        if (startRange == endRange) {
            tree[nodeIdx] = val;
            return;
        }
        int mid = startRange + (endRange - startRange) / 2;
        if (idx <= mid) {
            update(nodeIdx * 2 + 1, startRange, mid, idx, val);
        } else {
            update(nodeIdx * 2 + 2, mid + 1, endRange, idx, val);
        }
        tree[nodeIdx] = tree[nodeIdx * 2 + 1] + tree[nodeIdx * 2 + 2];
    }
}
