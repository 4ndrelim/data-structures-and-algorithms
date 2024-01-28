package dataStructures.bTree;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BTreeTest {
    @Test
    public void testBTree() {
        BTree bTree = new BTree(3); // Specify the order of the B-tree

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

}
