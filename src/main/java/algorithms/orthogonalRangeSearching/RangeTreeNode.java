package algorithms.orthogonalRangeSearching;

public class RangeTreeNode {
  int val;
  int height;
  RangeTreeNode left = null;
  RangeTreeNode right = null;
  RangeTreeNode parent = null;

  public RangeTreeNode(int val) {
    this.val = val;
  }
  public RangeTreeNode(int val, RangeTreeNode left, RangeTreeNode right) {
    this.val = val;
    this.left = left;
    this.right = right;
  }

  public int getVal() { return this.val; }
  public int getHeight() { return this.height; }
  public RangeTreeNode getLeft() { return this.left; }
  public RangeTreeNode getRight() { return this.right; }
  public RangeTreeNode getParent() { return this.parent; }

  public void setVal(int val) { this.val = val; }
  public void setLeft(RangeTreeNode left) { this.left = left; }
  public void setRight(RangeTreeNode right) { this.right = right; }

  public void setParent(RangeTreeNode parent) { this.parent = parent; }
  public void setHeight(int height) { this.height = height; }
  @Override
  public boolean equals(Object other) {
    if (other == this) { return true; }
    if (!(other instanceof RangeTreeNode)) { return false; }
      RangeTreeNode node = (RangeTreeNode) other;
      return this.val == node.val;
  }

  @Override
  public int hashCode() { return this.val; }
  @Override
  public String toString() { return String.valueOf(this.val); }

}
