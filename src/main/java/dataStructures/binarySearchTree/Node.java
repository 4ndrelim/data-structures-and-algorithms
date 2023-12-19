package dataStructures.binarySearchTree;

/**
 * TreeNode implementation for Binary Search Tree.
 * Note: Properties should rightfully be kept private 
 * and accessed/modified via public getters/setters.
 * But it was left as such to avoid clutter.
 * @param <T> generic type of objects to be stored in the tree; must be comparable
 */
public class Node <T extends Comparable<T>, V> {
  public T key;
  public Node<T, V> left;
  public Node<T, V> right;
  public Node<T, V> parent;
  public V value;

  /*
   * Can insert more properties here.
   * If key is not unique, introduce a value property
   * so when nodes are being compared, a distinction
   * can be made
   */

  public Node(T key, V value) {
    this.key = key;
    this.value = value;
  }

  public boolean isLeaf() {
    return this.left == null && this.right == null;
  }

  @Override
  public String toString() {
    return "Key: " + key.toString() + ", Value: " + (value == null ? "null" : value.toString());
  }
}
