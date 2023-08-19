package src.dataStructures.avlTree;

/**
 * TreeNode implementation for AVL Tree.
 * Note: Properties should rightfully be kept private 
 * and accessed/modified via public getters/setters.
 * But it was left as such to avoid clutter.
 * @param <T> generic type of objects to be stored in the tree; must be comparable
 */
public class Node <T extends Comparable<T>> {
  public T key;
  public Node<T> left;
  public Node<T> right;
  public Node<T> parent;
  public int height;
  /*
   * Can insert more properties here.
   * If key is not unique, introduce a value property
   * so when nodes are being compared, a distinction
   * can be made
   */

  public Node(T key) {
    this.key = key;
  }

  public boolean isLeaf() {
    return this.left == null && this.right == null;
  }

  @Override
  public String toString() {
    return key.toString();
  }
}
