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

  public boolean getTag() {return this.tag;}

  public RBNode<T> getLeft() {return this.left;}

  public RBNode<T> getRight() {return this.right;}

  @Override
  public String toString() {
    return this.value.toString();
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }
    if (!(other instanceof RBNode)) {
      return false;
    }
    RBNode<T> otherNode = (RBNode<T>) other;
    return this.getValue() == otherNode.getValue();
  }

}
