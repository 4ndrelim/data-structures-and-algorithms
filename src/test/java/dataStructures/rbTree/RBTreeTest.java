package dataStructures.rbTree;

import org.junit.Assert;
import org.junit.Test;
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
  }

  @Test
  public void testDeleteAndSearch() {
    RBTree<Integer> tree = new RBTree<>();
    Assert.assertEquals(null, tree.get(10));
    tree.insert(1);
    tree.insert(5);
    tree.insert(8);
    tree.insert(2);
    tree.insert(3);
    Assert.assertEquals((Integer) 1, tree.get(1));
    Assert.assertEquals((Integer) 2, tree.get(2));
    Assert.assertEquals((Integer) 3, tree.get(3));
    tree.delete(3);
    Assert.assertEquals(null, tree.get(3));
    tree.delete(1);
    Assert.assertEquals(null, tree.get(1));
  }

  @Test
  public void testRBRotations() {
    RBTree<Integer> tree = new RBTree<>();

    // Testing insert rotations
    Assert.assertEquals("", tree.getLevelOrder(tree.getRoot()));
    tree.insert(1);
    tree.insert(2);
    tree.insert(3);
    Assert.assertEquals("2 1 3 ", tree.getLevelOrder(tree.getRoot()));

    tree.insert(4);
    tree.insert(5);
    Assert.assertEquals("2 1 4 3 5 ", tree.getLevelOrder(tree.getRoot()));

    tree.insert(9);
    tree.insert(6);
    tree.insert(7);
    tree.insert(8);
    Assert.assertEquals("4 2 6 1 3 5 8 7 9 ", tree.getLevelOrder(tree.getRoot()));

    // Testing delete rotations
    tree.delete(6);
    Assert.assertEquals("4 2 7 1 3 5 8 9 ", tree.getLevelOrder(tree.getRoot()));
    Assert.assertEquals(4, tree.getDepth(tree.getRoot()));
    tree.delete(5);
    Assert.assertEquals("4 2 7 1 3 9 8 ", tree.getLevelOrder(tree.getRoot()));
    tree.delete(2);
    tree.delete(8);
    Assert.assertEquals(3, tree.getDepth(tree.getRoot()));
    Assert.assertEquals("4 3 7 1 9 ", tree.getLevelOrder(tree.getRoot()));
    tree.delete(4);
    Assert.assertEquals("7 3 9 1 ", tree.getLevelOrder(tree.getRoot()));
    Assert.assertEquals(3, tree.getDepth(tree.getRoot()));
  }
}
