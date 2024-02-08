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
    RBNode<Integer> del1 = tree.insert(1);
    tree.insert(5);
    tree.insert(8);
    tree.insert(2);
    RBNode<Integer> del2 = tree.insert(3);
    Assert.assertEquals((Integer) 1, tree.get(1));
    Assert.assertEquals((Integer) 2, tree.get(2));
    Assert.assertEquals((Integer) 3, tree.get(3));
    tree.delete(del2);
    Assert.assertEquals(null, tree.get(3));
    tree.delete(del1);
    Assert.assertEquals(null, tree.get(1));
  }

  @Test
  public void testRBRotations() {
    RBTree<Integer> tree = new RBTree<>();

    // Testing insert rotations
    Assert.assertEquals("", tree.getLevelOrder(tree.getRoot()));
    tree.insert(1);
    RBNode<Integer> del2 = tree.insert(2);
    tree.insert(3);
    Assert.assertEquals("2 1 3 ", tree.getLevelOrder(tree.getRoot()));

    RBNode<Integer> del4 = tree.insert(4);
    RBNode<Integer> del5 = tree.insert(5);
    Assert.assertEquals("2 1 4 3 5 ", tree.getLevelOrder(tree.getRoot()));

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
    tree.delete(del2);
    tree.delete(del8);
    Assert.assertEquals(null, tree.get(8));
    Assert.assertEquals(4, tree.getDepth(tree.getRoot()));
    Assert.assertEquals("9 4 3 7 1 ", tree.getLevelOrder(tree.getRoot()));
    tree.delete(del4);
    Assert.assertEquals("9 7 3 1 ", tree.getLevelOrder(tree.getRoot()));
    Assert.assertEquals(4, tree.getDepth(tree.getRoot()));
  }
}
