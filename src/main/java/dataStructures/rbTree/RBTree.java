package dataStructures.rbTree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Implementation of a Red-Black Tree, a self-balancing binary search tree.
 *
 * <p>Red-Black trees guarantee O(log n) operations by maintaining these properties:
 * <ol>
 *   <li>Every node is colored RED or BLACK</li>
 *   <li>Root is BLACK</li>
 *   <li>All leaves (NIL) are BLACK</li>
 *   <li>Red nodes have only black children (no red-red)</li>
 *   <li>All paths from root to leaves have the same black count</li>
 * </ol>
 *
 * <p>Compared to AVL trees, RB-trees have looser balance (height up to 2 log(n+1))
 * but require fewer rotations on insert/delete (at most 2-3 vs O(log n)).
 *
 * @param <T> The type of elements; must be Comparable
 */
public class RBTree<T extends Comparable<T>> {

    /**
     * Root of the tree.
     */
    private RBNode<T> root;

    /**
     * Sentinel NIL node representing all leaves and the root's parent.
     * Using a single NIL node (always BLACK) eliminates null checks and
     * simplifies fix-up logic. All leaf pointers and root.parent point here.
     */
    private RBNode<T> nil = new RBNode<>();

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
     * Gets depth of the RB tree.
     * @param node The node the tree is rooted from.
     * @return Depth.
     */
    public int getDepth(RBNode<T> node) {
        if (node == nil || node == null) {
            return 0;
        }
        int hLeft = getDepth(node.getLeft());
        int hRight = getDepth(node.getRight());
        return 1 + Math.max(hRight, hLeft);
    }

    /**
     * Gets level order of the tree.
     * @param node The node the tree is rooted from.
     * @return The string representation of the tree.
     */
    public String getLevelOrder(RBNode<T> node) {
        if (node == nil) {
            return "";
        }
        Queue<RBNode<T>> q = new LinkedList<>();
        q.add(node);
        StringBuilder sb = new StringBuilder();
        while (!q.isEmpty()) {
            RBNode<T> curr = q.poll();
            sb.append(curr.getElement() + " ");
            if (curr.getLeft() != nil) {
                q.add(curr.getLeft());
            }
            if (curr.getRight() != nil) {
                q.add(curr.getRight());
            }
        }
        return sb.toString();
    }

    /**
     * Inserts element into tree using standard BST insertion, then fixes RB properties.
     * @param element The element to insert.
     * @return The newly added node.
     */
    public RBNode<T> insert(T element) {
        // New nodes are RED by default. Why RED?
        // - Inserting BLACK would violate property 5 (black-height) on one path
        // - Inserting RED might only violate property 4 (no red-red), easier to fix
        RBNode<T> toAdd = new RBNode<>(element, nil, nil);
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
        } else if (element.compareTo(prev.getElement()) < 0) {
            prev.setLeft(toAdd);
        } else {
            prev.setRight(toAdd);
        }
        this.fixInsert(toAdd);
        return toAdd;
    }

    /**
     * Restores RB properties after insertion by fixing red-red violations (property 4).
     *
     * <p>Three cases based on uncle's color:
     * <ul>
     *   <li>Case 1 (Uncle RED): Recolor parent, uncle → BLACK, grandparent → RED. Push problem up.</li>
     *   <li>Case 2 (Uncle BLACK, inner child): Rotate to transform into Case 3.</li>
     *   <li>Case 3 (Uncle BLACK, outer child): Rotate grandparent + recolor. Done.</li>
     * </ul>
     *
     * <p>Key insight: Cases 2+3 do at most 2 rotations total, then loop terminates.
     *
     * @param node The newly inserted node (initially RED)
     */
    public void fixInsert(RBNode<T> node) {
        // Loop while we have a red-red violation (node and parent both red)
        while (node.getParent().getColor().equals(RBNode.VAL.RED)) {
            // Parent is left child of grandparent
            if (node.getParent() == node.getParent().getParent().getLeft()) {
                RBNode<T> uncle = node.getParent().getParent().getRight();
                if (uncle.getColor().equals(RBNode.VAL.RED)) {
                    // Case 1: Uncle is RED
                    // Recolor and push the problem up to grandparent
                    node.getParent().setColor(RBNode.VAL.BLACK);
                    uncle.setColor(RBNode.VAL.BLACK);
                    node.getParent().getParent().setColor(RBNode.VAL.RED);
                    node = node.getParent().getParent();
                } else {
                    // Cases 2 & 3: Uncle is BLACK
                    if (node == node.getParent().getRight()) {
                        // Case 2: Node is "inner" child (right child of left parent)
                        // Rotate left to transform into Case 3
                        node = node.getParent();
                        this.leftRotate(node);
                    }
                    // Case 3: Node is "outer" child (left child of left parent)
                    // Rotate grandparent right and recolor
                    node.getParent().setColor(RBNode.VAL.BLACK);
                    node.getParent().getParent().setColor(RBNode.VAL.RED);
                    this.rightRotate(node.getParent().getParent());
                }
            } else {
                // Mirror: Parent is right child of grandparent
                RBNode<T> uncle = node.getParent().getParent().getLeft();
                if (uncle.getColor().equals(RBNode.VAL.RED)) {
                    // Case 1: Uncle is RED
                    node.getParent().setColor(RBNode.VAL.BLACK);
                    uncle.setColor(RBNode.VAL.BLACK);
                    node.getParent().getParent().setColor(RBNode.VAL.RED);
                    node = node.getParent().getParent();
                } else {
                    // Cases 2 & 3: Uncle is BLACK
                    if (node == node.getParent().getLeft()) {
                        // Case 2: Inner child - rotate to Case 3
                        node = node.getParent();
                        this.rightRotate(node);
                    }
                    // Case 3: Outer child - rotate and recolor
                    node.getParent().setColor(RBNode.VAL.BLACK);
                    node.getParent().getParent().setColor(RBNode.VAL.RED);
                    this.leftRotate(node.getParent().getParent());
                }
            }
        }
        // Ensure root is always BLACK (may have been recolored red in Case 1)
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
     * Performs a left rotation around the given node.
     *
     * <pre>
     * Before:       After:
     *     x           y
     *    / \         / \
     *   a   y   =>  x   c
     *      / \     / \
     *     b   c   a   b
     * </pre>
     *
     * Preserves BST property: a &lt; x &lt; b &lt; y &lt; c
     *
     * @param node The pivot node (x in the diagram)
     */
    private void leftRotate(RBNode<T> node) {
        RBNode<T> temp = node.getRight();   // y becomes new subtree root
        node.setRight(temp.getLeft());      // x adopts y's left subtree (b)
        if (temp.getLeft() != nil) {
            temp.getLeft().setParent(node); // update b's parent to x
        }
        temp.setParent(node.getParent());   // y takes x's parent
        // Update parent's child pointer to y (or update root)
        if (node.getParent() == nil) {
            root = temp;
        } else if (node.getParent().getLeft() == node) {
            node.getParent().setLeft(temp);
        } else {
            node.getParent().setRight(temp);
        }
        temp.setLeft(node);                 // y's left child is now x
        node.setParent(temp);               // x's parent is now y
    }

    /**
     * Performs a right rotation around the given node.
     *
     * <pre>
     * Before:       After:
     *     y           x
     *    / \         / \
     *   x   c   =>  a   y
     *  / \             / \
     * a   b           b   c
     * </pre>
     *
     * Preserves BST property: a &lt; x &lt; b &lt; y &lt; c
     *
     * @param node The pivot node (y in the diagram)
     */
    private void rightRotate(RBNode<T> node) {
        RBNode<T> temp = node.getLeft();     // x becomes new subtree root
        node.setLeft(temp.getRight());       // y adopts x's right subtree (b)
        if (temp.getRight() != nil) {
            temp.getRight().setParent(node); // update b's parent to y
        }
        temp.setParent(node.getParent());    // x takes y's parent
        // Update parent's child pointer to x (or update root)
        if (node.getParent() == nil) {
            root = temp;
        } else if (node == node.getParent().getLeft()) {
            node.getParent().setLeft(temp);
        } else {
            node.getParent().setRight(temp);
        }
        temp.setRight(node);                 // x's right child is now y
        node.setParent(temp);                // y's parent is now x
    }

    /**
     * Replaces the subtree rooted at node b with the subtree rooted at node a.
     *
     * <p>This operation:
     * <ol>
     *   <li>Updates b's parent to point to a instead of b</li>
     *   <li>Updates a's parent pointer to b's former parent</li>
     * </ol>
     *
     * <p>Note: Does NOT update a's children or b's connections to its children.
     * Caller is responsible for handling those.
     *
     * @param a The subtree root to move in (replacement)
     * @param b The subtree root being replaced
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
        while (node.getLeft() != nil) {
            node = node.getLeft();
        }
        return node;
    }

    /**
     * Deletes a node from the tree using standard BST deletion, then fixes RB properties.
     *
     * <p>Three cases for BST deletion:
     * <ul>
     *   <li>Case 1: Node has no left child - replace with right child</li>
     *   <li>Case 2: Node has no right child - replace with left child</li>
     *   <li>Case 3: Node has two children - replace with in-order successor</li>
     * </ul>
     *
     * @param node The node to delete.
     */
    public void delete(RBNode<T> node) {
        RBNode<T> x;
        RBNode<T> y;
        // Track color of physically removed node (for fix-up decision)
        RBNode.VAL deletedColor = node.getColor();

        if (node.getLeft() == nil) {
            // Case 1: No left child - replace with right child (may be nil)
            x = node.getRight();
            this.transplant(node.getRight(), node);
        } else if (node.getRight() == nil) {
            // Case 2: No right child - replace with left child
            x = node.getLeft();
            this.transplant(node.getLeft(), node);
        } else {
            // Case 3: Two children - replace with in-order successor (min of right subtree)
            // The successor has at most 1 child (no left child, else it's not minimum)
            y = this.getMin(node.getRight());
            deletedColor = y.getColor(); // This is what we're physically removing
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

        // Only fix if we removed a BLACK node
        // (removing RED doesn't violate any property)
        if (deletedColor.equals(RBNode.VAL.BLACK)) {
            this.fixDelete(x);
        }
    }

    /**
     * Restores RB properties after deleting a BLACK node (property 5 violation).
     *
     * <p>The node x that moved into the deleted position has an implicit "extra black"
     * that we need to either absorb or push up/resolve.
     *
     * <p>Four cases based on sibling's color and its children:
     * <ul>
     *   <li>Case 1 (Sibling RED): Rotate + recolor, transforms to Case 2/3/4</li>
     *   <li>Case 2 (Sibling BLACK, both children BLACK): Push extra black up</li>
     *   <li>Case 3 (Sibling BLACK, far BLACK, near RED): Rotate to Case 4</li>
     *   <li>Case 4 (Sibling BLACK, far RED): Rotate parent + recolor. Done.</li>
     * </ul>
     *
     * @param node The replacement node with an implicit "extra black"
     */
    private void fixDelete(RBNode<T> node) {
        // Loop while x has the "extra black" (not root, still black)
        while (node != root && node.getColor().equals(RBNode.VAL.BLACK)) {
            if (node == node.getParent().getLeft()) {
                // x is left child, sibling is on the right
                RBNode<T> sibling = node.getParent().getRight();

                if (sibling.getColor().equals(RBNode.VAL.RED)) {
                    // Case 1: Sibling is RED
                    // Rotate and recolor to get a black sibling (transforms to Case 2/3/4)
                    sibling.setColor(RBNode.VAL.BLACK);
                    node.getParent().setColor(RBNode.VAL.RED);
                    this.leftRotate(node.getParent());
                    sibling = node.getParent().getRight();
                }

                if (sibling.getLeft().getColor().equals(RBNode.VAL.BLACK)
                    && sibling.getRight().getColor().equals(RBNode.VAL.BLACK)) {
                    // Case 2: Sibling BLACK, both children BLACK
                    // Push extra black up to parent
                    sibling.setColor(RBNode.VAL.RED);
                    node = node.getParent();
                } else {
                    if (sibling.getRight().getColor().equals(RBNode.VAL.BLACK)) {
                        // Case 3: Sibling BLACK, far child (right) BLACK, near child (left) RED
                        // Rotate sibling to transform into Case 4
                        sibling.getLeft().setColor(RBNode.VAL.BLACK);
                        sibling.setColor(RBNode.VAL.RED);
                        this.rightRotate(sibling);
                        sibling = node.getParent().getRight();
                    }
                    // Case 4: Sibling BLACK, far child (right) RED
                    // Rotate parent and recolor - resolves the extra black
                    sibling.setColor(node.getParent().getColor());
                    node.getParent().setColor(RBNode.VAL.BLACK);
                    sibling.getRight().setColor(RBNode.VAL.BLACK);
                    this.leftRotate(node.getParent());
                    node = this.root; // Done!
                }
            } else {
                // Mirror: x is right child, sibling is on the left
                RBNode<T> sibling = node.getParent().getLeft();

                if (sibling.getColor().equals(RBNode.VAL.RED)) {
                    // Case 1: Sibling RED
                    sibling.setColor(RBNode.VAL.BLACK);
                    node.getParent().setColor(RBNode.VAL.RED);
                    this.rightRotate(node.getParent());
                    sibling = node.getParent().getLeft();
                }

                if (sibling.getLeft().getColor().equals(RBNode.VAL.BLACK)
                    && sibling.getRight().getColor().equals(RBNode.VAL.BLACK)) {
                    // Case 2: Both children BLACK
                    sibling.setColor(RBNode.VAL.RED);
                    node = node.getParent();
                } else {
                    if (sibling.getLeft().getColor().equals(RBNode.VAL.BLACK)) {
                        // Case 3: Far child BLACK, near child RED
                        sibling.getRight().setColor(RBNode.VAL.BLACK);
                        sibling.setColor(RBNode.VAL.RED);
                        this.leftRotate(sibling);
                        sibling = node.getParent().getLeft();
                    }
                    // Case 4: Far child RED
                    sibling.setColor(node.getParent().getColor());
                    node.getParent().setColor(RBNode.VAL.BLACK);
                    sibling.getLeft().setColor(RBNode.VAL.BLACK);
                    this.rightRotate(node.getParent());
                    node = this.root; // Done!
                }
            }
        }
        // Absorb the extra black by coloring x black
        node.setColor(RBNode.VAL.BLACK);
    }

    /**
     * Counts the number of black nodes in a tree.
     * @param node The node the tree is rooted from.
     * @return The number of black nodes. If the property has been broken, -1.
     */
    public int countBlack(RBNode<T> node) {
        if (node == this.nil) {
            return 0;
        }
        int leftCount = countBlack(node.getLeft());
        int rightCount = countBlack(node.getRight());
        // Check equality in black nodes.
        if (leftCount == -1 || rightCount == -1 || leftCount != rightCount) {
            return -1;
        } else {
            if (node.getColor().equals(RBNode.VAL.BLACK)) {
                return leftCount + 1;
            }
            return leftCount;
        }
    }

    /**
     * Checks if red black property is met.
     * @return True if property is met.
     */
    public boolean isRedBlackTree() {
        return countBlack(this.root) != -1;
    }
}
