package dataStructures.fenwickTree;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FenwickTreeTest {

    @Test
    public void construct_shouldBuildTreeAndSupportPrefixQueries() {
        // Array: [1, 2, 3, 4, 5]
        // Prefix sums: [1, 3, 6, 10, 15]
        int[] nums = {1, 2, 3, 4, 5};
        FenwickTree tree = new FenwickTree(nums);

        assertEquals(1, tree.prefixQuery(0));   // sum of [1]
        assertEquals(3, tree.prefixQuery(1));   // sum of [1, 2]
        assertEquals(6, tree.prefixQuery(2));   // sum of [1, 2, 3]
        assertEquals(10, tree.prefixQuery(3));  // sum of [1, 2, 3, 4]
        assertEquals(15, tree.prefixQuery(4));  // sum of [1, 2, 3, 4, 5]
    }

    @Test
    public void rangeQuery_shouldReturnCorrectRangeSum() {
        // Array: [1, 2, 3, 4, 5]
        int[] nums = {1, 2, 3, 4, 5};
        FenwickTree tree = new FenwickTree(nums);

        // Single element ranges
        assertEquals(1, tree.rangeQuery(0, 0));  // just 1
        assertEquals(3, tree.rangeQuery(2, 2));  // just 3
        assertEquals(5, tree.rangeQuery(4, 4));  // just 5

        // Multi-element ranges
        assertEquals(5, tree.rangeQuery(1, 2));   // 2 + 3
        assertEquals(9, tree.rangeQuery(1, 3));   // 2 + 3 + 4
        assertEquals(12, tree.rangeQuery(2, 4));  // 3 + 4 + 5

        // Full range
        assertEquals(15, tree.rangeQuery(0, 4)); // 1 + 2 + 3 + 4 + 5
    }

    @Test
    public void update_shouldUpdateValueAndAffectQueries() {
        // Array: [1, 2, 3, 4, 5] -> sum = 15
        int[] nums = {1, 2, 3, 4, 5};
        FenwickTree tree = new FenwickTree(nums);

        // Update index 2: change 3 to 10 (delta = +7)
        tree.update(2, 7);
        // New array: [1, 2, 10, 4, 5] -> sum = 22

        assertEquals(1, tree.prefixQuery(0));   // sum of [1]
        assertEquals(3, tree.prefixQuery(1));   // sum of [1, 2]
        assertEquals(13, tree.prefixQuery(2));  // sum of [1, 2, 10]
        assertEquals(17, tree.prefixQuery(3));  // sum of [1, 2, 10, 4]
        assertEquals(22, tree.prefixQuery(4));  // sum of [1, 2, 10, 4, 5]

        // Range queries should also reflect the update
        assertEquals(10, tree.rangeQuery(2, 2)); // just 10
        assertEquals(16, tree.rangeQuery(1, 3)); // 2 + 10 + 4
    }

    @Test
    public void update_shouldHandleNegativeDelta() {
        // Array: [5, 5, 5, 5, 5] -> sum = 25
        int[] nums = {5, 5, 5, 5, 5};
        FenwickTree tree = new FenwickTree(nums);

        // Subtract 3 from index 2: change 5 to 2 (delta = -3)
        tree.update(2, -3);
        // New array: [5, 5, 2, 5, 5] -> sum = 22

        assertEquals(22, tree.prefixQuery(4));
        assertEquals(2, tree.rangeQuery(2, 2));
        assertEquals(12, tree.rangeQuery(1, 3)); // 5 + 2 + 5
    }

    @Test
    public void construct_shouldHandleSingleElement() {
        int[] nums = {42};
        FenwickTree tree = new FenwickTree(nums);

        assertEquals(42, tree.prefixQuery(0));
        assertEquals(42, tree.rangeQuery(0, 0));

        tree.update(0, 8); // 42 + 8 = 50
        assertEquals(50, tree.prefixQuery(0));
    }

    @Test
    public void construct_shouldHandleNegativeNumbers() {
        // Array: [-1, 2, -3, 4, -5]
        // Prefix sums: [-1, 1, -2, 2, -3]
        int[] nums = {-1, 2, -3, 4, -5};
        FenwickTree tree = new FenwickTree(nums);

        assertEquals(-1, tree.prefixQuery(0));
        assertEquals(1, tree.prefixQuery(1));
        assertEquals(-2, tree.prefixQuery(2));
        assertEquals(2, tree.prefixQuery(3));
        assertEquals(-3, tree.prefixQuery(4));

        assertEquals(3, tree.rangeQuery(1, 3));  // 2 + (-3) + 4 = 3
    }

    @Test
    public void construct_shouldHandleAllZeros() {
        int[] nums = {0, 0, 0, 0, 0};
        FenwickTree tree = new FenwickTree(nums);

        assertEquals(0, tree.prefixQuery(4));
        assertEquals(0, tree.rangeQuery(1, 3));

        tree.update(2, 5);
        assertEquals(5, tree.prefixQuery(4));
        assertEquals(5, tree.rangeQuery(0, 4));
    }

    @Test
    public void multipleUpdates_shouldAccumulateCorrectly() {
        // Array: [0, 0, 0, 0, 0]
        int[] nums = {0, 0, 0, 0, 0};
        FenwickTree tree = new FenwickTree(nums);

        // Build array [1, 2, 3, 4, 5] through updates
        tree.update(0, 1);
        tree.update(1, 2);
        tree.update(2, 3);
        tree.update(3, 4);
        tree.update(4, 5);

        assertEquals(15, tree.prefixQuery(4));
        assertEquals(9, tree.rangeQuery(1, 3)); // 2 + 3 + 4
    }

    @Test
    public void largerArray_shouldWorkCorrectly() {
        // Test with a larger array to verify bit manipulation works for various indices
        int[] nums = {1, 3, 5, 7, 9, 11, 13, 15}; // 8 elements, powers of 2 are important
        FenwickTree tree = new FenwickTree(nums);

        // Total sum = 1+3+5+7+9+11+13+15 = 64
        assertEquals(64, tree.prefixQuery(7));

        // Various ranges
        assertEquals(16, tree.rangeQuery(0, 3));  // 1+3+5+7
        assertEquals(48, tree.rangeQuery(4, 7));  // 9+11+13+15
        assertEquals(32, tree.rangeQuery(2, 5));  // 5+7+9+11

        // Update and verify
        tree.update(3, 3); // 7 -> 10
        assertEquals(67, tree.prefixQuery(7));
    }
}
