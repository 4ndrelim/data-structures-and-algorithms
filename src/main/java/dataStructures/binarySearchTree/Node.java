package dataStructures.binarySearchTree;

/**
 * Node implementation for Binary Search Tree.
 * Note: Properties should rightfully be kept private
 * and accessed/modified via public getters/setters.
 * But it was left as such to avoid clutter.
 *
 * @param <T> generic type of objects to be stored in the tree; must be comparable
 */
public class Node<T extends Comparable<T>, V> {
    private T key;
    private Node<T, V> left;
    private Node<T, V> right;
    private Node<T, V> parent;
    private V value;

    /**
     * Constructor for a BST node.
     * Can insert more properties here.
     * If key is not unique, introduce a value property, so when nodes are being compared, a distinction can be made.
     * @param key the key for the BST Node.
     * @param value the value encapsulated by the BST node.
     */
    public Node(T key, V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Determines whether the node is a leaf node.
     * @return true if the node is a leaf node, false if not.
     */
    public boolean isLeaf() {
        return this.left == null && this.right == null;
    }

    public T getKey() {
        return key;
    }

    public void setKey(T key) {
        this.key = key;
    }

    public Node<T, V> getLeft() {
        return left;
    }

    public void setLeft(Node<T, V> left) {
        this.left = left;
    }

    public Node<T, V> getRight() {
        return right;
    }

    public void setRight(Node<T, V> right) {
        this.right = right;
    }

    public Node<T, V> getParent() {
        return parent;
    }

    public void setParent(Node<T, V> parent) {
        this.parent = parent;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Key: " + key.toString() + ", Value: " + (value == null ? "null" : value.toString());
    }
}
