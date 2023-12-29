package dataStructures.rbTree;

import org.junit.Assert;
import org.junit.Test;
public class RBTreeTest {
  @Test
  public void testInsertAndSearch() {
    RBTree<Integer> tree = new RBTree<>();
    Assert.assertEquals(null, tree.findNode(10));
    tree.insertNode(1);
    tree.insertNode(2);
    tree.insertNode(3);
    Assert.assertEquals((Integer) 1, tree.findNode(1).getValue());
    Assert.assertEquals((Integer) 2, tree.findNode(2).getValue());
    Assert.assertEquals((Integer) 3, tree.findNode(3).getValue());
  }

  @Test
  public void testDeleteAndSearch() {
    RBTree<Integer> tree = new RBTree<>();
    Assert.assertEquals(null, tree.findNode(10));
    tree.insertNode(1);
    tree.insertNode(5);
    tree.insertNode(8);
    tree.insertNode(2);
    tree.insertNode(3);
    Assert.assertEquals((Integer) 1, tree.findNode(1).getValue());
    Assert.assertEquals((Integer) 2, tree.findNode(2).getValue());
    Assert.assertEquals((Integer) 3, tree.findNode(3).getValue());
    tree.deleteNode(3);
    Assert.assertEquals(null, tree.findNode(3));
    tree.deleteNode(1);
    Assert.assertEquals(null, tree.findNode(1));
  }

  @Test
  public void testRBRotations() {
    RBTree<Integer> tree = new RBTree<>();

    // Testing insert rotations
    Assert.assertEquals("", tree.getLevelOrder(tree.root));
    tree.insertNode(1);
    tree.insertNode(2);
    tree.insertNode(3);
    Assert.assertEquals("2 1 3 ", tree.getLevelOrder(tree.root));

    tree.insertNode(4);
    tree.insertNode(5);
    Assert.assertEquals("2 1 4 3 5 ", tree.getLevelOrder(tree.root));

    tree.insertNode(9);
    tree.insertNode(6);
    tree.insertNode(7);
    tree.insertNode(8);
    Assert.assertEquals("4 2 6 1 3 5 8 7 9 ", tree.getLevelOrder(tree.root));

    // Testing delete rotations
    tree.deleteNode(6);
    Assert.assertEquals("4 2 8 1 3 5 9 7 ", tree.getLevelOrder(tree.root));
    tree.deleteNode(5);
    Assert.assertEquals("4 2 8 1 3 7 9 ", tree.getLevelOrder(tree.root));
    tree.deleteNode(2);
    tree.deleteNode(8);
    Assert.assertEquals("4 1 7 3 9 ", tree.getLevelOrder(tree.root));
    tree.deleteNode(4);
    Assert.assertEquals("3 1 7 9 ", tree.getLevelOrder(tree.root));
  }
}
