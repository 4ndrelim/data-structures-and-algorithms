package dataStructures.rbTree;

/**
 * This class represents a node for the Red-Black Tree.
 *
 * @param <T> The type of element being stored in the node.
 */
public class RBNode<T extends Comparable<T>> {

    enum VAL {
        RED,
        BLACK
    }

    /**
     * The element held by the node.
     */
    private T element;

    /**
     * The color the node is marked with.
     */
    private VAL color;


    /**
     * The left child node.
     */
    private RBNode<T> left;

    /**
     * The right child node.
     */
    private RBNode<T> right;

    /**
     * The parent node.
     */
    private RBNode<T> parent;

    /**
     * Constructor for our RB-Tree node.
     * Defaults to red.
     *
     * @param element The element to add.
     */
    public RBNode(T element) {
        this.element = element;
        this.left = null;
        this.right = null;
        this.color = VAL.RED;
        this.parent = null;
    }

    /**
     * Constructor for a NIL node.
     */
    public RBNode() {
        this.element = null;
        this.parent = null;
        this.left = null;
        this.right = null;
        this.color = VAL.BLACK;
    }

    /**
     * Sets right node.
     *
     * @param other The new right node.
     */
    public void setRight(RBNode<T> other) {
        this.right = other;
    }

    /**
     * Sets left node.
     *
     * @param other The new left node.
     */
    public void setLeft(RBNode<T> other) {
        this.left = other;
    }

    /**
     * Sets parent node.
     *
     * @param other The new parent node.
     */
    public void setParent(RBNode<T> other) {
        this.parent = other;
    }

    /**
     * Getter for element
     *
     * @return The element in the node.
     */
    public T getElement() {
        return this.element;
    }

    /**
     * Getter for parent node.
     *
     * @return The parent node.
     */
    public RBNode<T> getParent() {
        return this.parent;
    }

    /**
     * Getter for right child node.
     *
     * @return The right child node.
     */
    public RBNode<T> getRight() {
        return this.left;
    }

    /**
     * Getter for left child node.
     *
     * @return The left child node.
     */
    public RBNode<T> getLeft() {
        return this.left;
    }

    /**
     * Getter for node color.
     *
     * @return The color the node is marked in.
     */
    public VAL getColor() {
        return this.color;
    }

    /**
     * Changes the color of the node.
     *
     * @param color The color to change to.
     */
    public void changeColor(VAL color) {
        this.color = color;
    }

}
