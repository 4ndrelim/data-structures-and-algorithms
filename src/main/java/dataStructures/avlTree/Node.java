package dataStructures.avlTree;

/**
 * TreeNode implementation for AVL Tree.
 * Note: Properties should rightfully be kept private
 * and accessed/modified via public getters/setters.
 * But it was left as such to avoid clutter.
 *
 * @param <T> generic type of objects to be stored in the tree; must be comparable
 */
public class Node<T extends Comparable<T>> {
    private T key;
    private Node<T> left;
    private Node<T> right;
    private Node<T> parent;
    private int height;
    /*
     * Can insert more properties here for augmentation
     * e.g. If key is not unique, introduce a value property as a tie-breaker
     * or weight property for order statistics
     */

    public Node(T key) {
        this.key = key;
        this.height = 0; // height of a new node is 0 (leaf)
    }

    public boolean isLeaf() {
        return this.left == null && this.right == null;
    }

    public T getKey() {
        return key;
    }

    public void setKey(T key) {
        this.key = key;
    }

    public Node<T> getLeft() {
        return left;
    }

    public void setLeft(Node<T> node) {
        this.left = node;
        if (node != null) {
            node.parent = this;
        }
    }

    public Node<T> getRight() {
        return right;
    }

    public void setRight(Node<T> node) {
        this.right = node;
        if (node != null) {
            node.parent = this;
        }
    }

    public Node<T> getParent() {
        return parent;
    }

    public void setParent(Node<T> parent) {
        this.parent = parent;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return key.toString();
    }
}
