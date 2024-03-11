package algorithms.orthogonalRangeSearching;

public class RangeTreeNode<T> {
  T val;
  int height;
  RangeTreeNode<T> left = null;
  RangeTreeNode<T> right = null;
  RangeTreeNode<T> parent = null;
  RangeTreeNode<T> yTree = null;

  public RangeTreeNode(T val) {
    this.val = val;
  }

  public RangeTreeNode(T val, RangeTreeNode<T> left, RangeTreeNode<T> right) {
    this.val = val;
    this.left = left;
    this.right = right;
  }

  public T getVal() {
    return this.val;
  }

  public int getHeight() {
    return this.height;
  }

  public RangeTreeNode<T> getLeft() {
    return this.left;
  }

  public RangeTreeNode<T> getRight() {
    return this.right;
  }

  public RangeTreeNode<T> getParent() {
    return this.parent;
  }

  public RangeTreeNode<T> getYTree() {
    return this.yTree;
  }

  public void setVal(T val) {
    this.val = val;
  }

  public void setLeft(RangeTreeNode<T> left) {
    this.left = left;
  }

  public void setRight(RangeTreeNode<T> right) {
    this.right = right;
  }

  public void setParent(RangeTreeNode<T> parent) {
    this.parent = parent;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public void setYTree(RangeTreeNode<T> yTree) {
    this.yTree = yTree;
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }
    if (!(other instanceof RangeTreeNode)) {
      return false;
    }
    RangeTreeNode<T> node = (RangeTreeNode<T>) other;
    return this.val == node.val;
  }

  @Override
  public String toString() {
    return String.valueOf(this.val);
  }

}
