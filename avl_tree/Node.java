import java.util.*;

public class Node <T extends Comparable<T>> {
  T key;
  Node<T> left;
  Node<T> right;
  int height;

  public Node(T key) {
    this.key = key;
  }

  public boolean isLeaf() {
    return this.left == null && this.right == null;
  }
}
