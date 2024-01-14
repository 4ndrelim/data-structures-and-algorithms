package dataStructures.bTree;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BTreeTest {
    @Test
    public void test_BTree() {
        BTree bTree = new BTree(3); // Specify the order of the B-tree

        int[] keys = {3, 7, 1, 8, 5, 12, 2, 9, 6, 10, 11, 4};
        for (int key : keys) {
            bTree.insert(key);
        }

        assertEquals(true, bTree.search(8));
        assertEquals(true, bTree.search(4));
        assertEquals(true, bTree.search(7));
        assertEquals(false, bTree.search(13));
        assertEquals(false, bTree.search(0));

        bTree.delete(7);
        assertEquals(false, bTree.search(7));

        bTree.delete(4);
        assertEquals(false, bTree.search(4));
    }

}
