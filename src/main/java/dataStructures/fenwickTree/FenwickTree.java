package dataStructures.fenwickTree;

/**
 * Fenwick Tree (Binary Indexed Tree / BIT)
 *
 * A data structure that efficiently supports:
 * - Point updates: O(log n)
 * - Prefix sum queries: O(log n)
 * - Range sum queries: O(log n)
 *
 * Key insight: Uses bit manipulation to navigate the tree.
 * - idx & (-idx) gives the lowest set bit (LSB)
 * - Update traverses upward by adding LSB
 * - Query traverses downward by subtracting LSB
 *
 * Note: Internally uses 1-based indexing for cleaner bit manipulation.
 *       The public API is 0-based for consistency with array conventions.
 */
public class FenwickTree {

    // The tree array (1-indexed, so size is n + 1)
    private int[] tree;

    // Size of the original array
    private int n;

    /**
     * Constructs a Fenwick Tree from the given array.
     *
     * Time:  O(n)
     * Space: O(n)
     *
     * @param nums the input array to build the tree from
     */
    public FenwickTree(int[] nums) {
        this.n = nums.length;
        this.tree = new int[n + 1];
        for (int i = 1; i < n + 1; i++) {
            tree[i] = nums[i - 1];
        }
        for (int i = 1; i < n + 1; i++) {
            int p = i + (i & -i);
            if (p < n + 1) {
                tree[p] += tree[i];
            }
        }
    }

    /**
     * Adds delta to the element at index idx.
     *
     * Time: O(log n)
     *
     * @param idx   the 0-based index to update
     * @param delta the value to add (can be negative for subtraction)
     */
    public void update(int idx, int delta) {
        idx += 1; // convert to 1-based index
        while (idx < n + 1) {
            tree[idx] += delta;
            idx += (idx & -idx);
        }
    }

    /**
     * Returns the prefix sum from index 0 to idx (inclusive).
     *
     * Time: O(log n)
     *
     * @param idx the 0-based ending index (inclusive)
     * @return sum of elements from index 0 to idx
     */
    public int prefixQuery(int idx) {
        int sum = 0;
        idx += 1; // convert to 1-based index
        while (idx > 0) {
            sum += tree[idx];
            idx -= (idx & -idx);
        }
        return sum;
    }

    /**
     * Returns the sum of elements from index left to right (inclusive).
     *
     * Time: O(log n)
     *
     * @param left  the 0-based starting index (inclusive)
     * @param right the 0-based ending index (inclusive)
     * @return sum of elements from index left to right
     */
    public int rangeQuery(int left, int right) {
        return prefixQuery(right) - prefixQuery(left - 1);
    }
}
