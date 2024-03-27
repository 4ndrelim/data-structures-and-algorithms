package dataStructures.segmentTree.arrayRepresentation;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * This file is essentially duplicated from the parent.
 */
public class SegmentTreeTest {
    @Test
    public void construct_shouldConstructSegmentTree() {
        int[] arr1 = new int[] {7, 77, 37, 67, 33, 73, 13, 2, 7, 17, 87, 53};
        SegmentTree tree1 = new SegmentTree(arr1);
        assertEquals(arr1[1] + arr1[2] + arr1[3], tree1.query(1, 3));
        assertEquals(arr1[4] + arr1[5] + arr1[6] + arr1[7], tree1.query(4, 7));
        int sum1 = 0;
        for (int i = 0; i < arr1.length; i++) {
            sum1 += arr1[i];
        }
        assertEquals(sum1, tree1.query(0, arr1.length - 1));


        int[] arr2 = new int[] {7, -77, 37, 67, -33, 0, 73, -13, 2, -7, 17, 0, -87, 53, 0}; // some negatives and 0s
        SegmentTree tree2 = new SegmentTree(arr1);
        assertEquals(arr1[1] + arr1[2] + arr1[3], tree2.query(1, 3));
        assertEquals(arr1[4] + arr1[5] + arr1[6] + arr1[7], tree2.query(4, 7));
        int sum2 = 0;
        for (int i = 0; i < arr1.length; i++) {
            sum2 += arr1[i];
        }
        assertEquals(sum2, tree2.query(0, arr1.length - 1));
    }

    @Test
    public void update_shouldUpdateSegmentTree() {
        int[] arr = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        SegmentTree tree = new SegmentTree(arr);
        assertEquals(55, tree.query(0, 10));
        tree.update(5, 55);
        assertEquals(105, tree.query(0, 10));
    }
}
