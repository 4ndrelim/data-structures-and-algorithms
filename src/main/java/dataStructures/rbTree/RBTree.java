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
     * Inserts element into tree.
     * @param element The element to insert.
     */
    public void insert(T element) {
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
    }

    /**
     * Fixes tree upwards upon insert operation.
     * @param node Node to fix upwards from.
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

}
