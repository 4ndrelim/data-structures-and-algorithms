import java.util.*;

public class Node <T extends Comparable<T>> {
  T key;
  Node parent;
  Node left;
  Node right;
  long height;

  public Node(T key) {
    this.key = key;
  }

  public boolean isLeaf() {
    return this.left == null && this.right == null;
  }
}
