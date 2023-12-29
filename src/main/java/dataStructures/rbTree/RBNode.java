package dataStructures.rbTree;

public class RBNode <T extends Comparable<T>> {
  T value;
  RBNode<T> left = null;
  RBNode<T> right = null;

  RBNode<T> parent = null;

  /**
   * Marks the color of the RB node, true if red, false if black.
   */
  boolean tag;

  public RBNode(T value, boolean tag) {
    this.value = value;
    this.tag = tag;
  }

  public T getValue() {
    return this.value;
  }
}
