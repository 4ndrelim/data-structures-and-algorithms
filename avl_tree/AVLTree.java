import java.util.*;

public class AVLTree<T extends Comparable<T>> {
  private Node<T> root;

  /**
   * Get height of node in avl tree.
   * @param n node whose height is to be queried
   * @return int value denoting height 
   */
  public int height(Node<T> n) {
    return n == null ? -1 : n.height;
  }

  /**
   * Update height of node in avl tree during rebalancing.
   * @param n node whose height is to be updated
   */
  public void updateHeight(Node<T> n) {
    n.height = 1 + Math.max(height(n.left), height(n.right));
  }

  /**
   * Get balance factor to check if height-balanced property is violated.
   * Note: negative value means tree is right heavy,
   *       positive value means tree is left heavy.
   * @param n check balance factor of node
   * @return int value representing the balance factor
   */
  public int getBalance(Node<T> n) {
    return n == null ? 0 : height(n.left) - height(n.right);
  }

  /**
   * Performs a right rotation on the specified node.
   * Note that function should be called only if the 
   * node has a left child since it will be the 
   * new root.
   * @param n node to perform right rotation on.
   * @return the new root after rotation.
   */
  public Node<T> rotateRight(Node<T> n) {
    Node<T> newRoot = n.left;
    Node<T> newLeftSub = newRoot.right;
    newRoot.right = n;
    n.left = newLeftSub;
    updateHeight(n);
    updateHeight(newRoot);
    return newRoot;
  }

  /**
   * Performs a left rotation on the specified node.
   * Note that function should be called only if the
   * node has a right child since it will be the
   * new root.
   * @param n node to perform left rotation on
   * @return new root after rotation
   */
  public Node<T> rotateLeft(Node<T> n) {
    Node<T> newRoot = n.right;
    Node<T> newRightSub = newRoot.left;
    newRoot.left = n;
    n.right = newRightSub;
    updateHeight(n);
    updateHeight(newRoot);
    return newRoot;
  }

  /**
   * Rebalances a node in the tree based on balance factor.
   * @param n node to be rebalanced
   * @return new root after rebalancing
   */
  public Node<T> rebalance(Node<T> n) {
    updateHeight(n);
    int balance = getBalance(n);
    if (balance < -1) { // right-heavy case
      if (height(n.right.left) > height(n.right.right)) {
        n.right = rotateRight(n.right);
      }
      n = rotateLeft(n);
    } else if (balance > 1) { // left-heavy case
      if (height(n.left.right) > height(n.left.left)) {
        n.left = rotateLeft(n.left);
      }
      n = rotateRight(n);
    }
    return n;
  }

  /**
   * Insert a key which will be wrapped in a node, into the tree.
   * NOTE: ASSUMPTION THAT NO TWO NODES SHARE THE SAME KEY VALUE.
   * @param node the (sub)tree rooted at node which the key will be inserted into
   * @param key the key to insert
   * @return the (new) node which the tree is rooted at after rebalancing
   */
  public Node<T> insert(Node<T> node, T key) {
    if (node == null) {
      return new Node<>(key);
    } else if (node.key.compareTo(key) < 0) {
      node.right = insert(node.right, key);
    } else if (node.key.compareTo(key) > 0) {
      node.left = insert(node.left, key);
    } else {
      throw new RuntimeException("Duplicate key not supported!");
    }
    return rebalance(node);
  }

  /**
   * Delete a key from the avl tree.
   * Find the node that holds the key and remove the node from the tree.
   * @param node the (sub)tree rooted at node which the key will be deleted from
   * @param key the key to remove
   * @return the (new) root which the tree is rooted at after rebalancing
   */
  public Node<T> delete(Node<T> node, T key) {
    if (node == null) {
      return null;
    } else if (node.key.compareTo(key) < 0) {
      node.right = delete(node.right, key);
    } else if (node.key.compareTo(key) > 0) {
      node.left = delete(node.left, key);
    } else {
      if (node.left == null || node.right == null) { // case of 1 or 0 child
        node = node.left == null ? node.right : node.left;
      } else { // 2-children case
        Node<T> successor = getMostLeft(node.right);
        node.key = successor.key;
        node.right = delete(node.right, successor.key);
      }
    }

    if (node != null) { // make sure it isnt the 0-child case
      rebalance(node);
    }
    return node;
  }

  private Node<T> getMostLeft(Node<T> n) {
    if (n.left == null) {
      return n;
    } else {
      return getMostLeft(n.left);
    }
  }
}
