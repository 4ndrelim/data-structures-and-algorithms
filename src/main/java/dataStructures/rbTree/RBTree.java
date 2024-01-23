package dataStructures.rbTree;
import dataStructures.rbTree.RBNode;
import java.lang.StringBuilder;

import java.util.*;
public class RBTree<T extends Comparable<T>> {
  RBNode<T> root = null;

  /**
   * Inserts a node into RB Tree.
   *
   * @param val The value to be inserted.
   */
  public void insertNode(T val) {
    RBNode<T> current = root;
    RBNode<T> parent = null;
    while (current != null) { // Iteratively search for insertion point.
      parent = current;
      if (val.compareTo(current.value) == -1) {
        current = current.left;
      } else {
        current = current.right;
      }
    }
    // Inserted node will always be kept as red first.
    RBNode<T> toInsert = new RBNode<>(val, true);
    if (this.root == null) { // First node.
      this.root = toInsert;
    } else if (val.compareTo(parent.value) == -1) {
      parent.left = toInsert;
      toInsert.parent = parent;
    } else {
      parent.right = toInsert;
      toInsert.parent = parent;
    }
    this.fixAfterInsert(toInsert);
  }

  /**
   * Searches the RB Tree iteratively for a given value.
   *
   * @param val The value to search for, null if it does not exist.
   */
  public RBNode<T> findNode(T val) {
    RBNode<T> current = this.root;
    while (current != null) {
      if (current.value.equals(val)) {
        return current;
      } else if (val.compareTo(current.value) == -1) {
        current = current.left;
      } else {
        current = current.right;
      }
    }
    return null;
  }

  /**
   * Helper function that conducts update on the relationship
   * between a parent node with a new child node.
   *
   * @param par The parent node.
   * @param curr The current node that is a child of the parent.
   * @param updated The new child to replace the current node.
   */
  private void swap(RBNode<T> par, RBNode<T> curr, RBNode<T> updated) {
    if (par == null) {
      root = updated;
    } else if (par.left == curr) { // If the old node is originally the left child.
      par.left = updated;
    } else {
      par.right = updated;
    }
    if (updated != null) {
      updated.parent = par;
    }
  }

  /**
   * This conducts right-rotation on a given node.
   *
   * @param node The node to rotate from.
   */
  private void rightRotate(RBNode<T> node) {
    RBNode<T> par = node.parent;
    RBNode<T> left = node.left;

    // Swap current node's left with the right node of the left child.
    node.left = left.right;
    if (left.right != null) {
      left.right.parent = node;
    }

    // Swap current node with left's right node.
    left.right = node;
    node.parent = left;

    // Swap parent of node to left. (Which is the new "higher" node in the tree).
    swap(par, node, left);
  }

  /**
   * This conducts left-rotation on a given node.
   *
   * @param node The node to rotate from.
   */
  private void leftRotate(RBNode<T> node) {
    RBNode<T> par = node.parent;
    RBNode<T> right = node.right;

    // Swap current node's right with the left node of the right child.
    node.right = right.left;
    if (right.left != null) {
      right.left.parent = node;
    }

    // Swap current node with right's left node.
    right.left = node;
    node.parent = right;

    // Swap parent of node to right.
    swap(par, node, right);
  }

  /**
   * Helper function to find the brother of the node.
   *
   * @param node The node we are interested in.
   * @return Brother of the node.
   */
  private RBNode<T> findBrother(RBNode<T> node) {
    RBNode<T> par = node.parent;
    if (par.left == node) {
      return par.right;
    } else {
      return par.left;
    }
  }

  /**
   * Fixes properties of the RB tree after insertion of a node.
   *
   * @param node The node to fix.
   */
  private void fixAfterInsert(RBNode<T> node) {
    RBNode<T> par = node.parent;
    if (par == null) {
      node.tag = false; // Roots must be black.
      return;
    }
    if (!par.tag) {return;} // Parent is of the correct color.

    RBNode<T> grandpa = par.parent;

    // Case where grandpa == null, parent is the root of the tree.
    if (grandpa == null) {
      par.tag = false; // Roots must be black.
      return;
    }

    RBNode<T> uncle = this.findBrother(par);

    // If uncle is red, we have to recolor the parent, grandpa and uncle.
    if (uncle != null && uncle.tag) {
      par.tag = false;
      grandpa.tag = true;
      uncle.tag = false;
      // Fix upwards.
      fixAfterInsert(grandpa);
    } else if (par.equals(grandpa.left)) { // If parent is the left node of grandpa...
      if (node.equals(par.right)) { // And we are the right child.
        leftRotate(par);
        rightRotate(grandpa);

        // We become the new "parent" of our parent, recolor accordingly.
        node.tag = false;
      } else { // And we are the left child.
        rightRotate(grandpa);

        // Parent becomes the new parent of grandpa, hence we recolor accordingly.
        par.tag = false;
      }
      grandpa.tag = true;
    } else { // If parent is the right node of grandpa...
      if (node.equals(par.right)) { // And we are the left child.
        leftRotate(grandpa);
        par.tag = false;
      } else { // And we are the left child.
        rightRotate(par);
        leftRotate(grandpa);
        node.tag = false;
      }
      grandpa.tag = true;
    }
  }

  /**
   * Deletes a node from the RB tree.
   *
   * @param val The value to remove from the tree.
   */
  public void deleteNode(T val) {

    // Finds node with the value within the tree.
    RBNode<T> current = findNode(val);
    if (current == null) {return;}

    RBNode<T> nextNode; // Node to fix next.
    boolean deletedColor;

    // Current node does not have 2 children.
    if (current.left == null || current.right == null) {
      nextNode = deleteZeroOrOne(current);
      deletedColor = current.tag;
    } else {
      // Find the successor of this node.
      // Our successor would be the smallest node in the subtree
      // with the current node's right child as the root.
      RBNode<T> successor = current.right;
      while (successor.left != null) {
        successor = successor.left;
      }
      // We replace the value with the successor.
      current.value = successor.value;

      // Since our successor must be a leaf, it has no children.
      // We remove the successor node from this "path".
      nextNode = deleteZeroOrOne(successor);
      deletedColor = successor.tag;
    }
    // If we deleted a black node, we need to fix the tree.
    if (!deletedColor) {
      fixAfterDelete(nextNode); // We fix the next node that causes problems with the property.
      if (nextNode.value.equals(-1)) {
        // In our implementation, -1 represents the node being a temporary node, but this could also
        // be replaced by a sentinel RBTree node.
        swap(nextNode.parent, nextNode, null);
      }
    }
  }

  /**
   * Helper function to delete a node with zero or one child.
   *
   * @param node The node in focus.
   *
   * @return The next node in concern.
   */
  private RBNode<T> deleteZeroOrOne(RBNode<T> node) {
    // If something has no child, we can ignore.
    if (node.left == null && node.right == null) {
      if (node.tag) { // If our node is red, we can just remove it.
        swap(node.parent, node, null);
        return null;
      } else {
        // Else, we need a temp. black node to hold the spot first, because the number of
        // black nodes down this path has reduced.
        RBNode<T> temp = new RBNode<>(null, false);
        swap(node.parent, node, temp);
        return temp;
      }
    }
    // We replace ourselves with the respective child we have.
    if (node.left != null) {
      swap(node.parent, node, node.left);
      return node.left;
    } else {
      swap(node.parent, node, node.right);
      return node.right;
    }
  }

  /**
   * Fixes the tree after a delete operation.
   *
   * @param node Node in question.
   */
  private void fixAfterDelete(RBNode<T> node) {
    if (node.equals(root)) { // No need to fix the root.
      return;
    }
    RBNode<T> brother = findBrother(node);
    // If our brother is red...
    if (brother.tag) {
      fixRedSibling(node, brother);
      brother = findBrother(node); // Get our new brother (who will now be black.)
    }
    if (!brother.left.tag && !brother.right.tag) { // Brother's children are all black.
      brother.tag = true; // Color our brother node red.
      if (node.parent.tag) { // Parent is red, brother's children are both black.
        node.parent.tag = false; // Recolor parent black.
      } else {
        // Else, we fix upwards.
        fixAfterDelete(node.parent);
      }
    } else { // Brother has at least one red child.
      fixBlackSibling(node, brother);
    }
  }

  /**
   * Helper function that fixes node with a brother that is red.
   *
   * @param node The black node.
   * @param brother The red node (our sibling node), that we need to fix to black.
   */
  private void fixRedSibling(RBNode<T> node, RBNode<T> brother) {
    // Recolor nodes (remember that we are black!)
    brother.tag = false;
    node.parent.tag = true;

    // Rotate node.
    if (node.equals(node.parent.left)) {
      leftRotate(node.parent);
    } else {
      rightRotate(node.parent);
    }
  }

  /**
   * Helper function that fixes node with a brother is black and has at least one red child.
   *
   * @param node The node.
   * @param brother The node's sibling.
   */
  private void fixBlackSibling(RBNode<T>  node, RBNode<T> brother) {
    // If we are the left child and our brother's right child is black.
    if (node.parent.left.equals(node) && !brother.right.tag) {
      // Recolor our brother and his child.
      brother.left.tag = false;
      brother.tag = true;

      // Fix position and update to our new brother.
      rightRotate(brother);
      brother = node.parent.right;
    } else if (!node.parent.left.equals(node) && !brother.left.tag){
      // If we are the right child and our brother's left child is black.
      brother.right.tag = false;
      brother.tag = true;
      leftRotate(brother);
      brother = node.parent.left;
    }

    // Brother has at least one red child, and the outer child with respect to our position
    // is red.
    brother.tag = node.parent.tag;
    node.parent.tag = false;
    if (node.parent.left.equals(node)) {
      // Recolor nodes.
      brother.right.tag = false;
      // Readjust by bring parent about us.
      leftRotate(node.parent);
    } else {
      brother.left.tag = false;
      rightRotate(node.parent);
    }
  }

  /**
   * Prints out in-order traversal of tree rooted at node.
   *
   * @param node Node which the tree is rooted at.
   */
  public void printInOrder(RBNode<T> node) {
    if (node == null) {
      return;
    }
    if (node.left != null) {
      printInOrder(node.left);
    }
    System.out.print(node.toString() + " ");
    if (node.right != null) {
      printInOrder(node.right);
    }
  }

  /**
   * Prints out pre-order traversal of tree rooted at node.
   *
   * @param node node which the tree is rooted at.
   */
  public void printPreOrder(RBNode<T> node) {
    if (node == null) {
      return;
    }
    System.out.print(node.toString() + " ");
    if (node.left != null) {
      printPreOrder(node.left);
    }
    if (node.right != null) {
      printPreOrder(node.right);
    }
  }

  /**
   * Prints out level-order traversal of tree rooted at node.
   *
   * @param node node which the tree is rooted at.
   *
   * @return String representing the level order.
   */
  public String getLevelOrder(RBNode<T> node) {
    if (node == null) {
      return "";
    }
    Queue<RBNode<T>> q = new LinkedList<>();
    StringBuilder sb = new StringBuilder();
    q.add(node);
    while (!q.isEmpty()) {
      RBNode<T> curr = q.poll();
      sb.append(curr.toString() + " ");
      if (curr.left != null) {
        q.add(curr.left);
      }
      if (curr.right != null) {
        q.add(curr.right);
      }
    }
    return sb.toString();
  }

  /**
   * Prints out post-order traversal of tree rooted at node.
   *
   * @param node node which the tree is rooted at.
   */
  private void printPostOrder(RBNode<T> node) {
    if (node.left != null) {
      printPostOrder(node.left);
    }
    if (node.right != null) {
      printPostOrder(node.right);
    }
    System.out.print(node.toString() + " ");
  }
}
