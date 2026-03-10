package dataStructures.segmentTree.arrayImplementation;

/**
 * Array-based implementation of a Segment Tree.
 *
 * Instead of explicit node objects with pointers, we store the tree in an array:
 * - tree[i] holds the sum for the range that node i represents
 * - Parent at index i → left child at 2i+1, right child at 2i+2
 *
 * Key difference from node-based: we must explicitly track the range [start, end]
 * each node represents, since there's no node object to store it.
 */
public class SegmentTree {
    private int[] tree;   // tree[i] = sum of range that node i represents
    private int[] array;  // original array (kept for bounds checking)

    /**
     * Constructor. Allocates 4n space for the tree array (see README for why 4n).
     */
    public SegmentTree(int[] nums) {
        tree = new int[4 * nums.length];
        array = nums;
        buildTree(nums, 0, nums.length - 1, 0);
    }

    /**
     * Recursively builds the segment tree.
     *
     * Unlike the node-based version where children are stored as pointers,
     * here we compute child positions in the array using index arithmetic:
     *   - Left child of node at idx  → 2*idx + 1
     *   - Right child of node at idx → 2*idx + 2
     *
     * We also pass [start, end] explicitly since there's no node object to store the range.
     */
    private void buildTree(int[] nums, int start, int end, int idx) {
        // Base case: leaf node (range of size 1)
        if (start == end) {
            tree[idx] = nums[start];
            return;
        }

        // Recursive case: build children, then combine
        int mid = start + (end - start) / 2;
        int leftChildIdx = 2 * idx + 1;
        int rightChildIdx = 2 * idx + 2;

        buildTree(nums, start, mid, leftChildIdx);      // left child covers [start, mid]
        buildTree(nums, mid + 1, end, rightChildIdx);   // right child covers [mid+1, end]

        tree[idx] = tree[leftChildIdx] + tree[rightChildIdx];
    }

    /**
     * Queries the sum of elements in range [leftEnd, rightEnd].
     */
    public int query(int leftEnd, int rightEnd) {
        return query(0, 0, array.length - 1, leftEnd, rightEnd);
    }

    /**
     * Recursively queries sum over [leftEnd, rightEnd].
     *
     * Same logic as node-based version, but we pass the node's range explicitly
     * since there's no node object to store [start, end].
     */
    private int query(int nodeIdx, int nodeStartRange, int nodeEndRange, int leftEnd, int rightEnd) {
        // Case 1: Node's range completely inside query range → return this node's sum
        if (leftEnd <= nodeStartRange && nodeEndRange <= rightEnd) {
            return tree[nodeIdx];
        }

        int rangeSum = 0;
        int mid = nodeStartRange + (nodeEndRange - nodeStartRange) / 2;

        // Case 2: Query overlaps left child's range [startRange, mid]
        if (leftEnd <= mid) {
            rangeSum += query(2 * nodeIdx + 1, nodeStartRange, mid, leftEnd, rightEnd);
        }

        // Case 3: Query overlaps right child's range [mid+1, endRange]
        if (mid + 1 <= rightEnd) {
            rangeSum += query(2 * nodeIdx + 2, mid + 1, nodeEndRange, leftEnd, rightEnd);
        }

        return rangeSum;
    }

    /**
     * Updates element at index idx to new value val.
     */
    public void update(int idx, int val) {
        if (idx < 0 || idx >= array.length) {
            return;
        }
        array[idx] = val;
        update(0, 0, array.length - 1, idx, val);
    }

    /**
     * Recursively updates the tree for a point update at idx.
     *
     * Navigate down to the leaf, update it, then propagate changes back up.
     * Same logic as node-based version, but passing range explicitly.
     */
    private void update(int nodeIdx, int startRange, int endRange, int idx, int val) {
        // Base case: reached leaf node
        if (startRange == endRange) {
            tree[nodeIdx] = val;
            return;
        }

        // Determine which child contains idx
        int mid = startRange + (endRange - startRange) / 2;
        if (idx <= mid) {
            update(2 * nodeIdx + 1, startRange, mid, idx, val);     // go left
        } else {
            update(2 * nodeIdx + 2, mid + 1, endRange, idx, val);   // go right
        }

        // Propagate change upward
        tree[nodeIdx] = tree[2 * nodeIdx + 1] + tree[2 * nodeIdx + 2];
    }
}
