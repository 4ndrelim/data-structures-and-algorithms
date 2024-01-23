package dataStructures.rbTree;

public class TombstoneNode<T extends Comparable<T>> extends RBNode<T> {
  public TombstoneNode() {
    super(null, false);
  }

  @Override
  public boolean equals(Object other) {
    return false;
  }
}
