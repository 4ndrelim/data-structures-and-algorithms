package algorithms.orthogonalRangeSearching.oneDim;

import algorithms.orthogonalRangeSearching.RangeTreeNode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class OrthogonalRangeSearching {
  /**
   * Builds a Range Tree from an array of integers.
   *
   * @param inputs The array of integers.
   * @param start  The starting index of the input array.
   * @param end    The ending index of the input array.
   * @return The root node of the constructed Range Tree.
   */
  public static RangeTreeNode<Integer> buildTree(int[] inputs, int start, int end) {
    int mid = (end + start) / 2;
    Arrays.sort(inputs);

    if (start > end) {
      return null;
    } else if (start == end) {
      return new RangeTreeNode<>(inputs[start]);
    } else {
      return new RangeTreeNode<>(inputs[mid], buildTree(inputs, start, mid),
              buildTree(inputs, mid + 1, end));
    }
  }

  /**
   * Finds the split node in the Range Tree based on a given range.
   *
   * @param root The root node of the Range Tree.
   * @param low  The lower bound of the range.
   * @param high The upper bound of the range.
   * @return The split node in the Range Tree.
   */
  public static RangeTreeNode<Integer> findSplit(RangeTreeNode<Integer> root, int low, int high) {
    RangeTreeNode<Integer> v = root;

    while (true) {
      if (v == null) {
        return null;
      } else {
        if (high <= v.getVal()) {
          if (isLeaf(v)) {
            break;
          }
          v = v.getLeft();
        } else if (low > v.getVal()) {
          v = v.getRight();
        } else {
          break;
        }
      }
    }
    return v;
  }

  /**
   * Performs a recursive traversal of the Range Tree and adds leaf node values to the result list.
   *
   * @param v      The current node being processed during traversal.
   * @param result The list to store the values of leaf nodes encountered during traversal.
   */
  public static void allLeafTraversal(RangeTreeNode<Integer> v, List<Integer> result) {
    if (v != null) {
      if (v.getLeft() != null) {
        allLeafTraversal(v.getLeft(), result);
      }
      if (isLeaf(v)) {
        result.add(v.getVal());
      }
      if (v.getRight() != null) {
        allLeafTraversal(v.getRight(), result);
      }
    }
  }

  /**
   * Performs a left traversal of the Range Tree to find nodes within a specified range.
   *
   * @param v      The current node being processed.
   * @param low    The lower bound of the range.
   * @param result A list to store the results of the traversal.
   */
  public static void leftTraversal(RangeTreeNode<Integer> v, int low, List<Integer> result) {
    if (v != null) {
      if (isLeaf(v)) {
        result.add(v.getVal());
      } else {
        if (low <= v.getVal()) {
          leftTraversal(v.getLeft(), low, result);
          allLeafTraversal(v.getRight(), result);
        } else { // definitely a qualifying leaf has to exist
          leftTraversal(v.getRight(), low, result);
        }
      }
    }
  }

  /**
   * Performs a right traversal of the Range Tree to find nodes within a specified range.
   *
   * @param v      The current node being processed.
   * @param high   The upper bound of the range.
   * @param result A list to store the results of the traversal.
   */
  public static void rightTraversal(RangeTreeNode<Integer> v, int high, List<Integer> result) {
    if (v != null) {
      if (isLeaf(v) && v.getVal() <= high) { // leaf, need extra check
        result.add(v.getVal());
      } else {
        if (high > v.getVal()) {
          allLeafTraversal(v.getLeft(), result);
          rightTraversal(v.getRight(), high, result);
        } else { // a qualifying leaf might or might not exist, we are just exploring
            rightTraversal(v.getLeft(), high, result);
        }
      }
    }
  }

  /**
   * Searches for elements within a specified range in the Range Tree.
   *
   * @param tree The root node of the Range Tree.
   * @param low  The lower bound of the range.
   * @param high The upper bound of the range.
   * @return An array of elements within the specified range.
   */
  public static Object[] search(RangeTreeNode<Integer> tree, int low, int high) {
    RangeTreeNode<Integer> splitNode = OrthogonalRangeSearching.findSplit(tree, low, high);
    ArrayList<Integer> result = new ArrayList<>();
    if (splitNode != null) {
      if (isLeaf(splitNode) && splitNode.getVal() >= low
              && splitNode.getVal() <= high) {
          result.add(splitNode.getVal());
      }
      leftTraversal(splitNode.getLeft(), low, result);
      rightTraversal(splitNode.getRight(), high, result);
    }
    return result.toArray();
  }

  private static boolean isLeaf(RangeTreeNode<Integer> node) {
    return node.getLeft() == null && node.getRight() == null;
  }

  // FUNCTIONS FROM HERE ONWARDS ARE DESIGNED TO SUPPORT DYNAMIC UPDATES.

  /**
   * Configures the height and parent nodes for the nodes in the Range Tree.
   * Note that this is only needed if we want to support dynamic updating of the range tree.
   *
   * @param node The root node of the Range Tree.
   */
  public static void configureTree(RangeTreeNode<Integer> node) {
    if (node.getLeft() == null && node.getRight() == null) {
      node.setHeight(0);
    } else if (node.getLeft() == null) {
      configureTree(node.getRight());
      node.setHeight(node.getRight().getHeight() + 1);
      node.getRight().setParent(node);
    } else if (node.getRight() == null) {
      configureTree(node.getLeft());
      node.setHeight(node.getLeft().getHeight() + 1);
      node.getLeft().setParent(node);
    } else {
      configureTree(node.getLeft());
      configureTree(node.getRight());
      node.setHeight(Math.max(node.getLeft().getHeight(), node.getRight().getHeight()) + 1);
      node.getLeft().setParent(node);
      node.getRight().setParent(node);
    }
  }

  /**
   * Inserts a new element into the Range Tree while maintaining balance.
   *
   * @param node The root node of the Range Tree.
   * @param val  The value to be inserted.
   * @return The root node of the updated Range Tree.
   */
  public static RangeTreeNode<Integer> insert(RangeTreeNode<Integer> node, int val) {
    if (val < node.getVal()) {
      if (node.getLeft() != null) {
        node.setLeft(insert(node.getLeft(), val));
      } else {
        node.setLeft(new RangeTreeNode(val));
        node.getLeft().setParent(node);
        node.setRight(new RangeTreeNode(node.getVal()));
        node.getRight().setParent(node);
        node.setVal(val);
      }
    } else if (val > node.getVal()) {
      if (node.getRight() != null) {
        node.setRight(insert(node.getRight(), val));
      } else {
        node.setLeft(new RangeTreeNode(node.getVal()));
        node.getLeft().setParent(node);
        node.setRight(new RangeTreeNode(val));
        node.getRight().setParent(node);
        node.setVal(node.getVal());
      }
    } else {
          throw new RuntimeException("Duplicate key not supported!");
    }
    return rebalance(node);
  }

  /**
   * Calculates and returns the height of the given Range Tree node.
   *
   * @param node The Range Tree node for which to calculate the height.
   * @return The height of the node, or -1 if the node is null.
   */
  private static int height(RangeTreeNode node) {
      return node == null
              ? -1
              : node.getHeight();
  }

  /**
   * Update height of node in range tree during rebalancing.
   * @param node node whose height is to be updated
   */
  private static void updateHeight(RangeTreeNode node) {
    node.setHeight(1 + Math.max(height(node.getLeft()), height(node.getRight())));
  }

  /**
   * Get balance factor to check if height-balanced property is violated.
   * Note: negative value means tree is right heavy,
   *       positive value means tree is left heavy,
   *       0 means tree is balanced in weight.
   * @param node check balance factor of node
   * @return int value representing the balance factor
   */
  private static int getBalance(RangeTreeNode node) {
    return node == null
            ? 0
            : height(node.getLeft()) - height(node.getRight());
  }

  /**
   * Performs a right rotation on the specified node.
   * Note that function should be called only if the node has a left child since it will be the new root.
   * @param n node to perform right rotation on.
   * @return the new root after rotation.
   */
  private static RangeTreeNode<Integer> rotateRight(RangeTreeNode<Integer> n) {
    RangeTreeNode<Integer> newRoot = n.getLeft();
    RangeTreeNode<Integer> newLeftSub = newRoot.getRight();
    newRoot.setRight(n);
    n.setLeft(newLeftSub);

    newRoot.setParent(n.getParent());
    n.setParent(newRoot);

    updateHeight(n);
    updateHeight(newRoot);
    return newRoot;
  }

  /**
   * Performs a left rotation on the specified node.
   * Note that function should be called only if the node has a right child since it will be the new root.
   *
   * @param n node to perform left rotation on
   * @return new root after rotation
   */
  private static RangeTreeNode<Integer> rotateLeft(RangeTreeNode<Integer> n) {
    RangeTreeNode<Integer> newRoot = n.getRight(); // newRoot is the right subtree of the original root
    RangeTreeNode<Integer> newRightSub = newRoot.getLeft();
    newRoot.setLeft(n);
    n.setRight(newRightSub);

    newRoot.setParent(n.getParent());
    n.setParent(newRoot);

    updateHeight(n);
    updateHeight(newRoot);
    return newRoot;
  }

  /**
   * Rebalances a node in the tree based on balance factor.
   *
   * @param n node to be rebalanced
   * @return new root after rebalancing
   */
  private static RangeTreeNode<Integer> rebalance(RangeTreeNode<Integer> n) {
    updateHeight(n);
    int balance = getBalance(n);
    if (balance < -1) { // right-heavy case
      RangeTreeNode<Integer> rightChild = n.getRight();
      if (height(rightChild.getLeft()) > height(rightChild.getRight())) {
        n.setRight(rotateRight(n.getRight()));
      }
      n = rotateLeft(n);
    } else if (balance > 1) { // left-heavy case
      RangeTreeNode<Integer> leftChild = n.getLeft();
      if (height(leftChild.getRight()) > height(leftChild.getLeft())) {
        n.setLeft(rotateLeft(n.getLeft()));
      }
      n = rotateRight(n);
    }
    return n;
  }

  /**
   * Deletes an element from the Range Tree while maintaining balance.
   *
   * @param node The root node of the Range Tree.
   * @param val  The value to be deleted.
   * @return The root node of the updated Range Tree.
   */
  public static RangeTreeNode<Integer> delete(RangeTreeNode<Integer> node, int val) {
    RangeTreeNode<Integer> leftChild = node.getLeft();
    RangeTreeNode<Integer> rightChild = node.getRight();

    if (leftChild.getLeft() == null && leftChild.getRight() == null
        && val == leftChild.getVal()) { // left node is the leaf node
      node.setVal(rightChild.getVal());
      node.setLeft(null);
      node.setRight(null);
    } else if (rightChild.getLeft() == null && rightChild.getRight() == null
        && val == rightChild.getVal()) { // right node is the leaf node
      node.setLeft(null);
      node.setRight(null);
    } else {
      if (val <= node.getVal()) {
        if (leftChild != null) {
          node.setLeft(delete(leftChild, val));
        }
        if (val == node.getVal()) { // duplicate node
          node.setVal(getMostRight(leftChild).getVal()); // update the duplicate key
        }
      } else {
        if (rightChild != null) {
          node.setRight(delete(rightChild, val));
        }
      }
    }
    return rebalance(node);
  }

  /**
   * Finds and returns the rightmost node in the Range Tree rooted at the given node.
   *
   * @param n The root node of a subtree to search in.
   * @return The rightmost node in the subtree, or null if the input node is null.
   */
  private static RangeTreeNode<Integer> getMostRight(RangeTreeNode<Integer> n) {
    if (n.getRight() == null) {
      return n;
    } else {
      return getMostRight(n.getRight());
    }
  }

  /**
   * Performs a level order traversal of the Range Tree and prints the elements.
   * This is not a necessary function for orthogonal range searching, but merely a utility function for debugging
   * and visualisation purposes.
   *
   * @param root The root node of the Range Tree.
   */
  public static void levelOrderTraversal(RangeTreeNode<Integer> root) {
    if (root == null) {
      return;
    }
    Queue<RangeTreeNode<Integer>> queue = new LinkedList<>();
    queue.add(root);
    while (!queue.isEmpty()) {
      RangeTreeNode<Integer> current = queue.poll();
      System.out.print(current.getVal() + " ");
      if (current.getLeft() != null) {
        queue.add(current.getLeft());
      }
      if (current.getRight() != null) {
        queue.add(current.getRight());
      }
    }
  }

}
