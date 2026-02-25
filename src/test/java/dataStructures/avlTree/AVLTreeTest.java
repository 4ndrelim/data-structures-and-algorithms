package dataStructures.avlTree;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class AVLTreeTest {

    @Test
    public void testInsertAndSearch() {
        AVLTree<Integer> tree = new AVLTree<>();
        tree.insert(10);
        tree.insert(5);
        tree.insert(15);
        tree.insert(3);
        tree.insert(7);

        assertNotNull(tree.search(10));
        assertNotNull(tree.search(5));
        assertNotNull(tree.search(15));
        assertNotNull(tree.search(3));
        assertNotNull(tree.search(7));
        assertNull(tree.search(100));
    }

    @Test
    public void testEmptyTree() {
        AVLTree<Integer> tree = new AVLTree<>();
        assertNull(tree.root());
        assertNull(tree.search(10));
        assertEquals(-1, tree.height(tree.root()));
    }

    @Test
    public void testSingleNode() {
        AVLTree<Integer> tree = new AVLTree<>();
        tree.insert(42);
        assertNotNull(tree.root());
        assertEquals(Integer.valueOf(42), tree.root().getKey());
        assertEquals(0, tree.height(tree.root()));
    }

    @Test
    public void testDeleteLeafNode() {
        AVLTree<Integer> tree = new AVLTree<>();
        tree.insert(10);
        tree.insert(5);
        tree.insert(15);

        tree.delete(5);
        assertNull(tree.search(5));
        assertNotNull(tree.search(10));
        assertNotNull(tree.search(15));
    }

    @Test
    public void testDeleteNodeWithOneChild() {
        AVLTree<Integer> tree = new AVLTree<>();
        tree.insert(10);
        tree.insert(5);
        tree.insert(15);
        tree.insert(3);  // left child of 5

        tree.delete(5);
        assertNull(tree.search(5));
        assertNotNull(tree.search(3));  // child should still exist
        assertNotNull(tree.search(10));
    }

    @Test
    public void testDeleteNodeWithTwoChildren() {
        AVLTree<Integer> tree = new AVLTree<>();
        tree.insert(10);
        tree.insert(5);
        tree.insert(15);
        tree.insert(3);
        tree.insert(7);

        tree.delete(5);  // has two children: 3 and 7
        assertNull(tree.search(5));
        assertNotNull(tree.search(3));
        assertNotNull(tree.search(7));
        assertNotNull(tree.search(10));
    }

    @Test
    public void testDeleteRoot() {
        AVLTree<Integer> tree = new AVLTree<>();
        tree.insert(10);
        tree.insert(5);
        tree.insert(15);

        tree.delete(10);
        assertNull(tree.search(10));
        assertNotNull(tree.root());  // tree should still have a root
    }

    @Test
    public void testBalanceAfterRightRotation() {
        // Insert in decreasing order to trigger right rotations
        AVLTree<Integer> tree = new AVLTree<>();
        tree.insert(30);
        tree.insert(20);
        tree.insert(10);  // triggers right rotation

        // After rotation, 20 should be root
        assertEquals(Integer.valueOf(20), tree.root().getKey());
        assertEquals(1, tree.height(tree.root()));
    }

    @Test
    public void testBalanceAfterLeftRotation() {
        // Insert in increasing order to trigger left rotations
        AVLTree<Integer> tree = new AVLTree<>();
        tree.insert(10);
        tree.insert(20);
        tree.insert(30);  // triggers left rotation

        // After rotation, 20 should be root
        assertEquals(Integer.valueOf(20), tree.root().getKey());
        assertEquals(1, tree.height(tree.root()));
    }

    @Test
    public void testBalanceAfterLeftRightRotation() {
        AVLTree<Integer> tree = new AVLTree<>();
        tree.insert(30);
        tree.insert(10);
        tree.insert(20);  // triggers left-right rotation

        assertEquals(Integer.valueOf(20), tree.root().getKey());
    }

    @Test
    public void testBalanceAfterRightLeftRotation() {
        AVLTree<Integer> tree = new AVLTree<>();
        tree.insert(10);
        tree.insert(30);
        tree.insert(20);  // triggers right-left rotation

        assertEquals(Integer.valueOf(20), tree.root().getKey());
    }

    @Test
    public void testPredecessor() {
        AVLTree<Integer> tree = new AVLTree<>();
        tree.insert(20);
        tree.insert(10);
        tree.insert(30);
        tree.insert(5);
        tree.insert(15);

        assertEquals(Integer.valueOf(15), tree.predecessor(20));
        assertEquals(Integer.valueOf(10), tree.predecessor(15));
        assertEquals(Integer.valueOf(5), tree.predecessor(10));
        assertNull(tree.predecessor(5));  // no predecessor for minimum
    }

    @Test
    public void testSuccessor() {
        AVLTree<Integer> tree = new AVLTree<>();
        tree.insert(20);
        tree.insert(10);
        tree.insert(30);
        tree.insert(5);
        tree.insert(15);

        assertEquals(Integer.valueOf(15), tree.successor(10));
        assertEquals(Integer.valueOf(20), tree.successor(15));
        assertEquals(Integer.valueOf(30), tree.successor(20));
        assertNull(tree.successor(30));  // no successor for maximum
    }

    @Test
    public void testHeight() {
        AVLTree<Integer> tree = new AVLTree<>();
        tree.insert(10);
        assertEquals(0, tree.height(10));

        tree.insert(5);
        tree.insert(15);
        assertEquals(1, tree.height(10));
        assertEquals(0, tree.height(5));
        assertEquals(0, tree.height(15));
    }
}
