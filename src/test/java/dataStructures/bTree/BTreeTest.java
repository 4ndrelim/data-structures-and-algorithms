package dataStructures.bTree;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BTreeTest {

    @Test
    public void testBTree() {
        BTree bTree = new BTree(3); // minimum degree t=3

        int[] keys = {3, 7, 1, 8, 5, 12, 2, 9, 6, 10, 11, 4};
        for (int key : keys) {
            bTree.insert(key);
        }

        Object[] expectedLevelOrderTraversal1 = {5, 8, 1, 2, 3, 4, 6, 7, 9, 10, 11, 12};
        Object[] expectedPreOrderTraversal1 = {5, 8, 1, 2, 3, 4, 6, 7, 9, 10, 11, 12};
        assertArrayEquals(bTree.levelOrderTraversal(), expectedLevelOrderTraversal1);
        assertArrayEquals(bTree.preOrderTraversal(), expectedPreOrderTraversal1);

        assertEquals(true, bTree.search(8));
        assertEquals(true, bTree.search(4));
        assertEquals(true, bTree.search(7));
        assertEquals(false, bTree.search(13));
        assertEquals(false, bTree.search(0));

        bTree.delete(7);
        assertEquals(false, bTree.search(7));

        Object[] expectedLevelOrderTraversal2 = {4, 8, 1, 2, 3, 5, 6, 9, 10, 11, 12};
        Object[] expectedPreOrderTraversal2 = {4, 8, 1, 2, 3, 5, 6, 9, 10, 11, 12};
        assertArrayEquals(bTree.levelOrderTraversal(), expectedLevelOrderTraversal2);
        assertArrayEquals(bTree.preOrderTraversal(), expectedPreOrderTraversal2);

        bTree.delete(4);
        assertEquals(false, bTree.search(4));

        Object[] expectedLevelOrderTraversal3 = {3, 8, 1, 2, 5, 6, 9, 10, 11, 12};
        Object[] expectedPreOrderTraversal3 = {3, 8, 1, 2, 5, 6, 9, 10, 11, 12};
        assertArrayEquals(bTree.levelOrderTraversal(), expectedLevelOrderTraversal3);
        assertArrayEquals(bTree.preOrderTraversal(), expectedPreOrderTraversal3);
    }

    @Test
    public void testMinimumDegreeTwo() {
        // t=2: each node has 1-3 keys, 2-4 children
        BTree bTree = new BTree(2);

        // Insert keys to force splits
        for (int i = 1; i <= 10; i++) {
            bTree.insert(i);
        }

        // All keys should be searchable
        for (int i = 1; i <= 10; i++) {
            assertEquals(true, bTree.search(i));
        }
        assertEquals(false, bTree.search(0));
        assertEquals(false, bTree.search(11));
    }

    @Test
    public void testSingleElement() {
        BTree bTree = new BTree(2);

        bTree.insert(42);
        assertEquals(true, bTree.search(42));

        Object[] expected = {42};
        assertArrayEquals(expected, bTree.levelOrderTraversal());

        bTree.delete(42);
        assertEquals(false, bTree.search(42));
    }

    @Test
    public void testDeleteAllElements() {
        BTree bTree = new BTree(2);

        int[] keys = {5, 3, 7, 1, 9};
        for (int key : keys) {
            bTree.insert(key);
        }

        // Delete all elements
        for (int key : keys) {
            assertEquals(true, bTree.search(key));
            bTree.delete(key);
            assertEquals(false, bTree.search(key));
        }
    }

    @Test
    public void testDeleteInternalNodeKey() {
        // Test Case 2: deleting a key from an internal node
        BTree bTree = new BTree(2);

        // Build a tree where root has keys
        for (int i = 1; i <= 7; i++) {
            bTree.insert(i);
        }

        // Find and delete an internal node key
        // With t=2, after inserting 1-7, root should have internal keys
        bTree.delete(4); // likely an internal key
        assertEquals(false, bTree.search(4));

        // Remaining keys should still be searchable
        for (int i = 1; i <= 7; i++) {
            if (i != 4) {
                assertEquals(true, bTree.search(i));
            }
        }
    }

    @Test
    public void testLargerTree() {
        BTree bTree = new BTree(3);

        // Insert 30 keys
        for (int i = 1; i <= 30; i++) {
            bTree.insert(i);
        }

        // All should be searchable
        for (int i = 1; i <= 30; i++) {
            assertEquals(true, bTree.search(i));
        }

        // Delete every other key
        for (int i = 2; i <= 30; i += 2) {
            bTree.delete(i);
            assertEquals(false, bTree.search(i));
        }

        // Odd keys should still exist
        for (int i = 1; i <= 30; i += 2) {
            assertEquals(true, bTree.search(i));
        }
    }

    @Test
    public void testReverseInsertOrder() {
        BTree bTree = new BTree(2);

        // Insert in reverse order to test different split patterns
        for (int i = 10; i >= 1; i--) {
            bTree.insert(i);
        }

        for (int i = 1; i <= 10; i++) {
            assertEquals(true, bTree.search(i));
        }
    }

    @Test
    public void testBorrowFromSiblings() {
        // Create scenario where borrow operations are needed
        BTree bTree = new BTree(2);

        // Insert enough keys to create multiple levels
        int[] keys = {10, 20, 30, 40, 50, 25, 35, 5, 15};
        for (int key : keys) {
            bTree.insert(key);
        }

        // Delete keys that should trigger borrow operations
        bTree.delete(5);
        assertEquals(false, bTree.search(5));

        // Other keys should still exist
        for (int key : new int[]{10, 15, 20, 25, 30, 35, 40, 50}) {
            assertEquals(true, bTree.search(key));
        }
    }
}
