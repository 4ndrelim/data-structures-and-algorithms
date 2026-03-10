package dataStructures.rbTree;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test cases for {@link RBTree}.
 */
public class RBTreeTest {
    @Test
    public void testInsertAndSearch() {
        RBTree<Integer> tree = new RBTree<>();
        Assert.assertEquals(null, tree.get(10));
        tree.insert(1);
        tree.insert(2);
        tree.insert(3);
        Assert.assertEquals((Integer) 1, tree.get(1));
        Assert.assertEquals((Integer) 2, tree.get(2));
        Assert.assertEquals((Integer) 3, tree.get(3));
        Assert.assertEquals(true, tree.isRedBlackTree());
    }

    @Test
    public void testDeleteAndSearch() {
        RBTree<Integer> tree = new RBTree<>();
        Assert.assertEquals(null, tree.get(10));
        Assert.assertEquals(true, tree.isRedBlackTree());
        RBNode<Integer> del1 = tree.insert(1);
        tree.insert(5);
        tree.insert(8);
        tree.insert(2);
        RBNode<Integer> del2 = tree.insert(3);
        Assert.assertEquals((Integer) 1, tree.get(1));
        Assert.assertEquals((Integer) 2, tree.get(2));
        Assert.assertEquals((Integer) 3, tree.get(3));
        Assert.assertEquals(true, tree.isRedBlackTree());
        tree.delete(del2);
        Assert.assertEquals(null, tree.get(3));
        Assert.assertEquals(true, tree.isRedBlackTree());
        tree.delete(del1);
        Assert.assertEquals(null, tree.get(1));
        Assert.assertEquals(true, tree.isRedBlackTree());
    }

    @Test
    public void testRedBlackRotations() {
        RBTree<Integer> tree = new RBTree<>();

        // Testing insert rotations
        Assert.assertEquals("", tree.getLevelOrder(tree.getRoot()));
        tree.insert(1);
        RBNode<Integer> del2 = tree.insert(2);
        tree.insert(3);
        Assert.assertEquals("2 1 3 ", tree.getLevelOrder(tree.getRoot()));
        Assert.assertEquals(true, tree.isRedBlackTree());

        RBNode<Integer> del4 = tree.insert(4);
        RBNode<Integer> del5 = tree.insert(5);
        Assert.assertEquals("2 1 4 3 5 ", tree.getLevelOrder(tree.getRoot()));
        Assert.assertEquals(true, tree.isRedBlackTree());

        tree.insert(9);
        RBNode<Integer> del6 = tree.insert(6);
        tree.insert(7);
        RBNode<Integer> del8 = tree.insert(8);
        Assert.assertEquals("4 2 6 1 3 5 8 7 9 ", tree.getLevelOrder(tree.getRoot()));

        // Testing delete rotations
        tree.delete(del6);
        Assert.assertEquals("4 2 7 1 3 5 8 9 ", tree.getLevelOrder(tree.getRoot()));
        Assert.assertEquals(4, tree.getDepth(tree.getRoot()));
        tree.delete(del5);
        Assert.assertEquals("4 2 8 1 3 7 9 ", tree.getLevelOrder(tree.getRoot()));
        Assert.assertEquals(true, tree.isRedBlackTree());
        tree.delete(del2);
        tree.delete(del8);
        Assert.assertEquals(null, tree.get(8));
        Assert.assertEquals(3, tree.getDepth(tree.getRoot()));
        Assert.assertEquals(true, tree.isRedBlackTree());
        Assert.assertEquals("4 3 9 1 7 ", tree.getLevelOrder(tree.getRoot()));
        tree.delete(del4);
        Assert.assertEquals("7 3 9 1 ", tree.getLevelOrder(tree.getRoot()));
        Assert.assertEquals(true, tree.isRedBlackTree());
        Assert.assertEquals(3, tree.getDepth(tree.getRoot()));
    }

    @Test
    public void testEmptyTree() {
        RBTree<Integer> tree = new RBTree<>();
        assertNull(tree.get(10));
        assertTrue(tree.isRedBlackTree());
        assertEquals(0, tree.getDepth(tree.getRoot()));
    }

    @Test
    public void testSingleElement() {
        RBTree<Integer> tree = new RBTree<>();
        tree.insert(42);
        assertEquals((Integer) 42, tree.get(42));
        assertTrue(tree.isRedBlackTree());
        assertEquals(1, tree.getDepth(tree.getRoot()));
        // Root should always be black (property 2)
        assertEquals(RBNode.VAL.BLACK, tree.getRoot().getColor());
    }

    @Test
    public void testLargeDataset_sortedInsertion() {
        RBTree<Integer> tree = new RBTree<>();
        int n = 1000;

        // Insert in sorted order (worst case for non-balancing BST)
        for (int i = 0; i < n; i++) {
            tree.insert(i);
            assertTrue(tree.isRedBlackTree());
        }

        // Height should be O(log n), specifically <= 2 * log2(n+1)
        int maxHeight = (int) Math.ceil(2 * Math.log(n + 1) / Math.log(2));
        assertTrue(tree.getDepth(tree.getRoot()) <= maxHeight);

        // Verify all elements accessible
        for (int i = 0; i < n; i++) {
            assertEquals((Integer) i, tree.get(i));
        }
    }

    @Test
    public void testDeleteRoot() {
        RBTree<Integer> tree = new RBTree<>();
        tree.insert(10);
        tree.insert(5);
        tree.insert(15);

        RBNode<Integer> root = tree.getRoot();
        tree.delete(root);

        assertNull(tree.get(10));
        assertTrue(tree.isRedBlackTree());
        assertNotNull(tree.getRoot()); // Tree should still have a root
        // Remaining elements should still be accessible
        assertNotNull(tree.get(5));
        assertNotNull(tree.get(15));
    }

    @Test
    public void testDeleteAllElements() {
        RBTree<Integer> tree = new RBTree<>();
        RBNode<Integer> n1 = tree.insert(1);
        RBNode<Integer> n2 = tree.insert(2);
        RBNode<Integer> n3 = tree.insert(3);

        tree.delete(n1);
        assertTrue(tree.isRedBlackTree());
        tree.delete(n2);
        assertTrue(tree.isRedBlackTree());
        tree.delete(n3);
        assertTrue(tree.isRedBlackTree());

        assertNull(tree.get(1));
        assertNull(tree.get(2));
        assertNull(tree.get(3));
        assertEquals("", tree.getLevelOrder(tree.getRoot()));
    }

    @Test
    public void testInsertReverseOrder() {
        RBTree<Integer> tree = new RBTree<>();
        // Insert in reverse order (triggers different rotation patterns than ascending)
        for (int i = 10; i >= 1; i--) {
            tree.insert(i);
            assertTrue(tree.isRedBlackTree());
        }
        // Height should still be bounded: <= 2 * log2(11) ≈ 6.9
        assertTrue(tree.getDepth(tree.getRoot()) <= 7);
    }

    @Test
    public void testRootIsAlwaysBlack() {
        RBTree<Integer> tree = new RBTree<>();
        // Verify root is black after every insertion (property 2)
        for (int i = 1; i <= 20; i++) {
            tree.insert(i);
            assertEquals(RBNode.VAL.BLACK, tree.getRoot().getColor());
        }
    }

    @Test
    public void testBlackHeightConsistency() {
        RBTree<Integer> tree = new RBTree<>();
        // countBlack returns -1 if black-height is inconsistent (property 5 violated)
        for (int i = 1; i <= 15; i++) {
            tree.insert(i);
            assertTrue(tree.countBlack(tree.getRoot()) >= 0);
        }
    }

    @Test
    public void testAlternatingOperations() {
        RBTree<Integer> tree = new RBTree<>();
        List<RBNode<Integer>> nodes = new ArrayList<>();

        // Insert 5 elements
        for (int i = 0; i < 5; i++) {
            nodes.add(tree.insert(i * 10));
        }

        // Alternating delete and insert
        tree.delete(nodes.get(2)); // Delete 20
        tree.insert(25);
        assertTrue(tree.isRedBlackTree());

        tree.delete(nodes.get(0)); // Delete 0
        tree.insert(5);
        assertTrue(tree.isRedBlackTree());

        // Verify remaining elements
        assertNull(tree.get(0));
        assertNull(tree.get(20));
        assertNotNull(tree.get(10));
        assertNotNull(tree.get(30));
        assertNotNull(tree.get(40));
        assertNotNull(tree.get(5));
        assertNotNull(tree.get(25));
    }
}
