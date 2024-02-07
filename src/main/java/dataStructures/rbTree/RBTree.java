package dataStructures.rbTree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * This class represents a Red-Black (RB) tree.
 * @param <T> The class of the elements being added to the RB-Tree.
 */
public class RBTree<T extends Comparable<T>> {

    /**
     * The root node.
     */
    private RBNode<T> root;

    /**
     * A representation of the NIL node used in RB-Trees.
     */
    private RBNode<T> nilNode;

    /**
     * Constructor for the Red-Black Tree.
     */
    public RBTree() {
        nilNode = new RBNode<>();
        root = nilNode;
    }

    /**
     * Gets root of the tree.
     *
     * @return Root.
     */
    public RBNode<T> getRoot() {
        return this.root;
    }

    /**
     * Helper function to conduct left-rotate.
     *
     * @param node The node to rotate from.
     */
    private void leftRotate(RBNode<T> node) {
        RBNode<T> right = node.getRight();
        node.setRight(right.getLeft());
        if (right.getLeft() != this.nilNode) {
            right.getLeft().setParent(node);
        }
        right.setParent(node.getParent());
        if (node.getParent() == null) {
            root = right;
        } else if (node == node.getParent().getLeft()) { // We swap ourselves with our right node.
            node.getParent().setLeft(right);
        } else {
            node.getParent().setRight(right);
        }
        // We become the child of the right node.
        right.setLeft(node);
        node.setParent(right);
    }

    /**
     * Helper function to conduct right-rotate.
     *
     * @param node The node to rotate from.
     */
    private void rightRotate(RBNode<T> node) {
        RBNode<T> left = node.getLeft();
        node.setLeft(left.getRight());
        if (left.getRight() != this.nilNode) {
            left.getRight().setParent(node);
        }
        left.setParent(node.getParent());
        if (node.getParent() == null) {
            root = left;
        } else if (node.getParent().getLeft() == node) {
            node.getParent().setLeft(left);
        } else {
            node.getParent().setRight(left);
        }
        left.setRight(node);
        node.setParent(left);
    }

    /**
     * Gets an element from the tree.
     * In our implementation, this represents more of a "check-if-exists" operation.
     * But in key-value RB-Trees, such as Java's TreeMap, the get will return the value
     * tagged to the key, whilst the search will be conducted based on the key value.
     *
     * @param element The element in the tree.
     * @return The element we are looking for.
     */
    public T get(T element) {
        RBNode<T> current = root;
        while (current != null) {
            int compareResults = current.getElement().compareTo(element);
            switch (compareResults) {
            case 1:
                return current.getElement();
            case -1:
                current = current.getLeft();
                break;
            default:
                current = current.getRight();
                break;
            }
        }
        return null;
    }

    /**
     * The element to insert into the tree.
     *
     * @param element The element to insert.
     */
    public void insert(T element) {
        RBNode<T> toInsert = new RBNode<>(element);
        RBNode<T> previous = null;
        RBNode<T> current = root;

        while (current != null) {
            previous = current;
            int compareResults = toInsert.getElement().compareTo(current.getElement());
            if (compareResults < 0) { // We assume no duplicates in this implementation.
                current = current.getLeft();
            } else {
                current = current.getRight();
            }
        }

        toInsert.setParent(previous);
        if (previous == null) {
            root = toInsert;
        } else {
            int compareResults = toInsert.getElement().compareTo(previous.getElement());
            if (compareResults < 0) {
                previous.setLeft(toInsert);
            } else {
                previous.setRight(toInsert);
            }
        }

        if (toInsert.getParent() == null) {
            toInsert.changeColor(RBNode.VAL.BLACK); // Root is black!
            return;
        }

        if (toInsert.getParent().getParent() == null) { // Grandparent is root!
            return;
        }

        this.fixInsert(toInsert); // We need to fix the tree :(
    }

    /**
     * Helper function to re-balance tree post insert operations.
     *
     * @param node The node to fix from.
     */
    private void fixInsert(RBNode<T> node) {
        RBNode<T> uncle;
        while (node.getParent().getColor() == RBNode.VAL.RED) {
            // If the parent of the current node is the right node of the grandparent.
            if (node.getParent() == node.getParent().getParent().getRight()) {
                uncle = node.getParent().getParent().getLeft();
                if (uncle.getColor() == RBNode.VAL.RED) { // If uncle is currently red.
                    // We fix our uncle, our parent and the grandparent.
                    uncle.changeColor(RBNode.VAL.BLACK);
                    node.getParent().changeColor(RBNode.VAL.BLACK);
                    node.getParent().getParent().changeColor(RBNode.VAL.RED);
                    node = node.getParent().getParent();
                } else { // Our uncle is currently black.
                    if (node.getParent().getLeft() == node) { // If we are the left child of our parent.
                        node = node.getParent();
                        this.rightRotate(node);
                    }
                    // Fix our parents and grandparents, uncle is fine :)
                    node.getParent().changeColor(RBNode.VAL.BLACK);
                    node.getParent().getParent().changeColor(RBNode.VAL.RED);
                    this.leftRotate(node.getParent().getParent());
                }
            } else { // Mirror copy of the above!
                uncle = node.getParent().getParent().getRight();
                if (uncle.getColor() == RBNode.VAL.RED) {
                    uncle.changeColor(RBNode.VAL.BLACK);
                    node.getParent().changeColor(RBNode.VAL.BLACK);
                    node.getParent().getParent().changeColor(RBNode.VAL.RED);
                    node = node.getParent().getParent();
                } else {
                    if (node.getParent().getRight() == node) {
                        node = node.getParent();
                        this.leftRotate(node);
                    }
                    node.getParent().changeColor(RBNode.VAL.BLACK);
                    node.getParent().getParent().changeColor(RBNode.VAL.RED);
                    this.rightRotate(node.getParent().getParent());
                }
            }
            if (node == this.root) { // We have fixed up to the root.
                break;
            }
        }
        root.changeColor(RBNode.VAL.BLACK); // Ensure root remains black.
    }

    /**
     * Finds the minimum element in the tree.
     *
     * @param node The node the tree is rooted from.
     * @return The node containing the minimum element in the tree.
     */
    public RBNode<T> findMin(RBNode<T> node) {
        while (node.getLeft() != this.nilNode) {
            node = node.getRight();
        }
        return node;
    }

    /**
     * Helper function to transpose node b into node a's position.
     *
     * @param a The node to be transposed out.
     * @param b The node to be transposed in.
     */
    private void swap(RBNode<T> a, RBNode<T> b) {
        if (a.getParent() == null) {
            root = b;
        } else if (a == a.getParent().getLeft()) {
            a.getParent().setLeft(b);
        } else {
            a.getParent().setRight(b);
        }
        b.setParent(a.getParent());
    }

    /**
     * To delete a node containing a certain element from the tree.
     * Note that this implementation does not account for duplicates of the same element.
     *
     * @param element The element we need to delete from the tree.
     */
    public void delete(T element) {
        RBNode<T> node = root;
        RBNode<T> target = this.nilNode;
        while (node != null) {
            int compareResult = node.getElement().compareTo(element);
            switch (compareResult) {
            case 0:
                target = node;
                break;
            case 1:
                node = node.getLeft();
                break;
            default:
                node = node.getRight();
                break;
            }
        }
        if (target == this.nilNode) {
            return;
        }
        RBNode<T> temp = target;
        RBNode.VAL initialColor = temp.getColor();
        RBNode<T> fin;
        if (target.getLeft() == this.nilNode) {
            fin = temp.getRight();
            this.swap(target, target.getLeft());
        } else if (target.getRight() == this.nilNode) {
            fin = temp.getLeft();
            this.swap(target, target.getRight());
        } else {
            temp = this.findMin(target.getRight());
            initialColor = temp.getColor();
            fin = temp.getRight();
            if (temp.getParent() == target) {
                fin.setParent(temp);
            } else {
                this.swap(temp, temp.getRight());
                temp.setRight(target.getRight());
                temp.getRight().setParent(temp);
            }
            this.swap(target, temp);
            temp.setLeft(target.getLeft());
            temp.getLeft().setParent(temp);
            temp.changeColor(target.getColor());
        }
        if (initialColor.equals(RBNode.VAL.BLACK)) {
            this.fixDelete(fin); // We need to fix the final node if we removed a black.
        }
    }

    /**
     * Function to re-balance the tree post delete operations.
     *
     * @param node The node to fix upwards from.
     */
    private void fixDelete(RBNode<T> node) {
        RBNode<T> temp;
        while (node != null && node.getColor().equals(RBNode.VAL.BLACK)) {
            if (node == node.getParent().getLeft()) { // Current node is the left child.
                temp = node.getParent().getRight();
                if (temp.getColor().equals(RBNode.VAL.RED)) { // Our sibling node is red.
                    temp.changeColor(RBNode.VAL.BLACK);
                    node.getParent().changeColor(RBNode.VAL.RED);
                    this.leftRotate(node.getParent());
                    temp = node.getParent().getRight();
                }
                if (temp.getColor().equals(RBNode.VAL.BLACK)
                    && temp.getColor().equals(RBNode.VAL.BLACK)) { // Left & Right child are black.
                    temp.changeColor(RBNode.VAL.RED);
                    node = node.getParent();
                } else {
                    if (temp.getRight().getColor().equals(RBNode.VAL.BLACK)) {
                        temp.getLeft().changeColor(RBNode.VAL.BLACK);
                        temp.changeColor(RBNode.VAL.RED);
                        this.rightRotate(temp);
                        temp = node.getParent().getRight();
                    }
                    temp.changeColor(node.getParent().getColor());
                    node.getParent().changeColor(RBNode.VAL.BLACK);
                    temp.getRight().changeColor(RBNode.VAL.BLACK);
                    this.leftRotate(node.getParent());
                    node = root;
                }
            } else { // Current node is the right child, mirror of the above :)
                temp = node.getParent().getLeft();
                if (temp.getColor().equals(RBNode.VAL.RED)) {
                    temp.changeColor(RBNode.VAL.BLACK);
                    node.getParent().changeColor(RBNode.VAL.RED);
                    this.rightRotate(node.getParent());
                    temp = node.getParent().getLeft();
                }
                if (temp.getRight().getColor().equals(RBNode.VAL.BLACK)
                    && temp.getLeft().getColor().equals(RBNode.VAL.BLACK)) {
                    temp.changeColor(RBNode.VAL.RED);
                    node = node.getParent();
                } else {
                    if (temp.getLeft().getColor().equals(RBNode.VAL.BLACK)) {
                        temp.getRight().changeColor(RBNode.VAL.BLACK);
                        temp.changeColor(RBNode.VAL.RED);
                        this.leftRotate(temp);
                        temp = node.getParent().getLeft();
                    }
                    temp.changeColor(node.getParent().getColor());
                    node.getParent().changeColor(RBNode.VAL.BLACK);
                    temp.getLeft().changeColor(RBNode.VAL.BLACK);
                    this.rightRotate(node.getParent());
                    node = root;
                }
            }
        }
        node.changeColor(RBNode.VAL.BLACK); // We ensure our root remains black!
    }

    /**
     * Gets level order of the tree.
     *
     * @param node The node the tree is rooted at.
     * @return String representation of the level order of the tree.
     */
    public String getLevelOrder(RBNode<T> node) {
        if (node == this.nilNode || node == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        Queue<RBNode<T>> q = new LinkedList<>();
        q.add(node);
        while (!q.isEmpty()) {
            RBNode<T> current = q.poll();
            sb.append(current.getElement());
            if (current.getLeft() != null && current.getLeft() != this.nilNode) {
                q.add(current.getLeft());
            }
            if (current.getRight() != null && current.getRight() != this.nilNode) {
                q.add(current.getRight());
            }
        }
        return sb.toString();
    }

    /**
     * Gets depth of node.
     *
     * @param node The node the tree is rooted at.
     * @return The depth of the node within the tree.
     */
    public int getDepth(RBNode<T> node) {
        if (node == null || node == this.nilNode) {
            return 0;
        }
        int leftHeight = getDepth(node.getLeft());
        int rightHeight = getDepth(node.getRight());
        return 1 + Math.max(leftHeight, rightHeight);
    }
}
