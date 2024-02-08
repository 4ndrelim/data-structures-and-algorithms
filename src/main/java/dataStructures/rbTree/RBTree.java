package dataStructures.rbTree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * This class represents a Red-Black (RB) tree.
 * @param <T> The class of the elements being added to the RB-Tree.
 */
public class RBTree<T extends Comparable<T>> {

    /**
     * Root of the tree.
     */
    RBNode<T> root;

    /**
     * NIL Node
     */
    RBNode<T> nil = new RBNode<>();

    /**
     * Constructor for RB Tree.
     */
    public RBTree() {
        this.root = nil;
    }

    /**
     * Getter for root.
     * @return The root of the RB tree.
     */
    public RBNode<T> getRoot() {
        return this.root;
    }

    /**
     * Inserts element into tree.
     * @param element The element to insert.
     * @return The newly added node.
     */
    public RBNode<T> insert(T element) {
        RBNode<T> toAdd = new RBNode<>(element);
        RBNode<T> prev = nil;
        RBNode<T> curr = root;
        while (curr != nil) {
            prev = curr;
            if (element.compareTo(curr.getElement()) < 0) {
                curr = curr.getLeft();
            } else {
                curr = curr.getRight();
            }
        }
        toAdd.setParent(prev);
        if (prev == nil) {
            this.root = toAdd;
        } else if (element.compareTo(curr.getElement()) < 0) {
            prev.setLeft(toAdd);
        } else {
            prev.setRight(toAdd);
        }
        this.fixInsert(toAdd);
        return toAdd;
    }

    /**
     * Fixes red-black properties upwards upon insert operation.
     * @param node The node to fix.
     */
    public void fixInsert(RBNode<T> node) {
        while (node.getParent().getColor().equals(RBNode.VAL.RED)) {
            // Parent is a left child.
            if (node.getParent() == node.getParent().getParent().getLeft()) {
                RBNode<T> uncle = node.getParent().getParent().getRight();
                if (uncle.getColor().equals(RBNode.VAL.RED)) {
                    node.getParent().setColor(RBNode.VAL.BLACK);
                    uncle.setColor(RBNode.VAL.BLACK);
                    node.getParent().getParent().setColor(RBNode.VAL.RED);
                    node = node.getParent().getParent();
                } else {
                    if (node == node.getParent().getRight()) { // Node is right child.
                        node = node.getParent();
                        this.leftRotate(node);
                    }
                    node.getParent().setColor(RBNode.VAL.BLACK);
                    node.getParent().getParent().setColor(RBNode.VAL.RED);
                    this.rightRotate(node.getParent().getParent());
                }
            } else { // Mirror image of above.
                RBNode<T> uncle = node.getParent().getParent().getLeft();
                if (uncle.getColor().equals(RBNode.VAL.RED)) {
                    node.getParent().setColor(RBNode.VAL.BLACK);
                    uncle.setColor(RBNode.VAL.BLACK);
                    node.getParent().getParent().setColor(RBNode.VAL.RED);
                    node = node.getParent().getParent();
                } else {
                    if (node == node.getParent().getLeft()) {
                        node = node.getParent();
                        this.rightRotate(node);
                    }
                    node.getParent().setColor(RBNode.VAL.BLACK);
                    node.getParent().getParent().setColor(RBNode.VAL.RED);
                    this.leftRotate(node.getParent().getParent());
                }
            }
        }
        root.setColor(RBNode.VAL.BLACK);
    }

    /**
     * Find element. In this case, this is "it-exists" checker.
     * However, in a map-based RB Tree like Java's TreeMap, this would conduct a search based on
     * a key and return the value in the key : value pair.
     * @param element
     * @return The element if it's in the tree, else null.
     */
    public T get(T element) {
        RBNode<T> curr = root;
        while (curr != nil) {
            if (curr.getElement().equals(element)) {
                return element;
            }
            if (element.compareTo(curr.getElement()) < 0) {
                curr = curr.getLeft();
            } else {
                curr = curr.getRight();
            }
        }
        return null;
    }

    /**
     * Helper function to conduct a left-rotate.
     * @param node The node to rotate on.
     */
    private void leftRotate(RBNode<T> node) {
        RBNode<T> temp = node.getRight();
        node.setRight(temp.getLeft());
        if (temp.getLeft() != nil) {
            temp.getLeft().setParent(node);
        }
        temp.setParent(node.getParent());
        if (node.getParent() == nil) {
            root = temp;
        } else if (node.getParent().getLeft() == node) {
            node.getParent().setLeft(temp);
        } else {
            node.getParent().setRight(temp);
        }
        temp.setLeft(node);
        node.setParent(temp);
    }

    /**
     * Helper function to conduct a right-rotate.
     * @param node The node to rotate on.
     */
    private void rightRotate(RBNode<T> node) {
        RBNode<T> temp = node.getLeft();
        node.setLeft(temp.getRight());
        if (temp.getRight() != nil) {
            temp.getRight().setParent(node);
        }
        temp.setParent(node.getParent());
        if (node.getParent() == nil) {
            root = temp;
        } else if (node == node.getParent().getLeft()) {
            node.getParent().setLeft(temp);
        } else {
            node.getParent().setRight(temp);
        }
        temp.setRight(node);
        node.setParent(temp);
    }

    /**
     * Helper function that transposes node A into B.
     * @param a The node to transpose.
     * @param b The node to transpose to.
     */
    public void transplant(RBNode<T> a, RBNode<T> b) {
        if (b.getParent() == nil) {
            root = a;
        } else if (b == b.getParent().getLeft()) {
            b.getParent().setLeft(a);
        } else {
            b.getParent().setRight(a);
        }
        a.setParent(b.getParent());
    }

    /**
     * Gets the node with the minimum value.
     * @param node The node tree is rooted at.
     * @return The node with the minimum value.
     */
    public RBNode<T> getMin(RBNode<T> node) {
        while (node != nil) {
            node = node.getLeft();
        }
        return node;
    }

    /**
     * Deletes a node from the tree.
     * @param node The node to delete.
     */
    public void delete(RBNode<T> node) {
        RBNode<T> x;
        RBNode<T> y;
        RBNode.VAL deletedColor = node.getColor();
        if (node.getLeft() == nil) {
            x = node.getRight();
            this.transplant(node.getRight(), node);
        } else if (node.getRight() == nil) {
            x = node.getLeft();
            this.transplant(node.getLeft(), node);
        } else {
            y = this.getMin(node);
            deletedColor = y.getColor();
            x = y.getRight();
            if (y.getParent() == node) {
                x.setParent(y);
            } else {
                this.transplant(y.getRight(), y);
                y.setRight(node.getRight());
                y.getRight().setParent(y);
            }
            this.transplant(y, node);
            y.setLeft(node.getLeft());
            y.getLeft().setParent(y);
            y.setColor(node.getColor());
        }
        if (deletedColor.equals(RBNode.VAL.BLACK)) {
            this.fixDelete(x);
        }
    }

    /**
     * Fixes red-black properties after delete operation.
     * @param node The node to fix.
     */
    private void fixDelete(RBNode<T> node) {
        while (node != root && node.getColor().equals(RBNode.VAL.BLACK)) {
            if (node == node.getParent().getLeft()) {
                RBNode<T> brother = node.getParent().getRight();
                if (brother.getColor().equals(RBNode.VAL.RED)) {
                    brother.setColor(RBNode.VAL.BLACK);
                    node.getParent().setColor(RBNode.VAL.RED);
                    this.leftRotate(node.getParent());
                    brother = node.getParent().getRight();
                }
                if (brother.getLeft().getColor().equals(RBNode.VAL.BLACK)
                    && brother.getRight().getColor().equals(RBNode.VAL.BLACK)) {
                    brother.setColor(RBNode.VAL.RED);
                    node = node.getParent();
                } else {
                    if (brother.getRight().getColor().equals(RBNode.VAL.BLACK)) {
                        brother.getLeft().setColor(RBNode.VAL.BLACK);
                        brother.setColor(RBNode.VAL.RED);
                        this.rightRotate(brother);
                        brother = node.getParent().getRight();
                    }
                    brother.setColor(node.getParent().getColor());
                    node.getParent().setColor(RBNode.VAL.BLACK);
                    brother.getRight().setColor(RBNode.VAL.BLACK);
                    this.leftRotate(node.getParent());
                    node = this.root;
                }
            } else {
                RBNode<T> brother = node.getParent().getLeft();
                if (brother.getColor().equals(RBNode.VAL.RED)) {
                    brother.setColor(RBNode.VAL.BLACK);
                    node.getParent().setColor(RBNode.VAL.RED);
                    this.rightRotate(node.getParent());
                    brother = node.getParent().getLeft();
                }
                if (brother.getLeft().getColor().equals(RBNode.VAL.BLACK)
                    && brother.getRight().getColor().equals(RBNode.VAL.BLACK)) {
                    brother.setColor(RBNode.VAL.RED);
                    node = node.getParent();
                } else {
                    if (brother.getLeft().getColor().equals(RBNode.VAL.BLACK)) {
                        brother.getRight().setColor(RBNode.VAL.BLACK);
                        brother.setColor(RBNode.VAL.RED);
                        this.leftRotate(brother);
                        brother = node.getParent().getLeft();
                    }
                    brother.setColor(node.getParent().getColor());
                    node.getParent().setColor(RBNode.VAL.BLACK);
                    brother.getLeft().setColor(RBNode.VAL.BLACK);
                    this.rightRotate(node.getParent());
                    node = this.root;
                }
            }
            node.setColor(RBNode.VAL.BLACK);
        }
    }

}
