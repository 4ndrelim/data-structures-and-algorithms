package dataStructures.avlTree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Implementation of Adelson-Velsky, Landis (AVL) Tree.
 *
 * @param <T> generic type of object to be stored; must be comparable
 *            client methods:
 *            height(Node n)
 *            height(T key)
 *            root()
 *            insert(T key)
 *            delete(T key)
 *            search(T key)
 *            predecessor(T key)
 *            successor(T key)
 *            printInorder()
 *            printPreorder()
 *            printPostorder()
 *            printLevelOrder()
 */
public class AVLTree<T extends Comparable<T>> {

    private Node<T> root;

    /**
     * Get root of tree.
     *
     * @return root
     */
    public Node<T> root() {
        return root;
    }

    /**
     * Get height of node in avl tree.
     *
     * @param n node whose height is to be queried
     * @return int value denoting height
     */
    public int height(Node<T> n) {
        return n == null ? -1 : n.getHeight();
    }

    /**
     * Get height of node that holds the specified key
     *
     * @param key the key value of the node whose height is to be found
     * @return int value representing height
     */
    public int height(T key) {
        return height(search(key));
    }

    /**
     * Update height of node in avl tree during rebalancing.
     *
     * @param n node whose height is to be updated
     */
    private void updateHeight(Node<T> n) {
        n.setHeight(1 + Math.max(height(n.getLeft()), height(n.getRight())));
    }

    /**
     * Get balance factor to check if height-balanced property is violated.
     * Note: negative value means tree is right heavy,
     * positive value means tree is left heavy,
     * 0 means tree is balanced in weight.
     *
     * @param n check balance factor of node
     * @return int value representing the balance factor
     */
    private int getBalance(Node<T> n) {
        return n == null ? 0 : height(n.getLeft()) - height(n.getRight());
    }

    /**
     * Performs a right rotation on the specified node.
     * Note that function should be called only if the
     * node has a left child since it will be the
     * new root.
     *
     * @param n node to perform right rotation on.
     * @return the new root after rotation.
     */
    private Node<T> rotateRight(Node<T> n) {
        Node<T> newRoot = n.getLeft();
        Node<T> newLeftSub = newRoot.getRight();
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
     * Note that function should be called only if the
     * node has a right child since it will be the
     * new root.
     *
     * @param n node to perform left rotation on
     * @return new root after rotation
     */
    private Node<T> rotateLeft(Node<T> n) {
        Node<T> newRoot = n.getRight();
        Node<T> newRightSub = newRoot.getLeft();
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
    private Node<T> rebalance(Node<T> n) {
        updateHeight(n);
        int balance = getBalance(n);
        if (balance < -1) { // right-heavy case
            if (height(n.getRight().getLeft()) > height(n.getRight().getRight())) {
                n.setRight(rotateRight(n.getRight()));
            }
            n = rotateLeft(n);
        } else if (balance > 1) { // left-heavy case
            if (height(n.getLeft().getRight()) > height(n.getLeft().getLeft())) {
                n.setLeft(rotateLeft(n.getLeft()));
            }
            n = rotateRight(n);
        }
        return n;
    }

    /**
     * Find the left-most child of the (sub)tree rooted at a specified node
     *
     * @param n tree is rooted at this node
     * @return left-most node
     */
    private Node<T> getMostLeft(Node<T> n) {
        if (n.getLeft() == null) {
            return n;
        } else {
            return getMostLeft(n.getLeft());
        }
    }

    private Node<T> getMostRight(Node<T> n) {
        if (n.getRight() == null) {
            return n;
        } else {
            return getMostRight(n.getRight());
        }
    }

    /**
     * Inserts a key into the tree
     *
     * @param key to be inserted
     */
    public void insert(T key) {
        root = insert(root, key);
    }

    /**
     * Insert a key which will be wrapped in a node, into the tree rooted at a specified node.
     * NOTE: ASSUMPTION THAT NO TWO NODES SHARE THE SAME KEY VALUE.
     *
     * @param node the (sub)tree rooted at node which the key will be inserted into
     * @param key  the key to insert
     * @return the (new) node which the tree is rooted at after rebalancing
     */
    private Node<T> insert(Node<T> node, T key) {
        if (node == null) {
            return new Node<>(key);
        } else if (node.getKey().compareTo(key) < 0) {
            node.setRight(insert(node.getRight(), key));
            node.getRight().setParent(node);
            // note that insufficient to update parent in rotateLeft & rotateRight if still considered balanced
        } else if (node.getKey().compareTo(key) > 0) {
            node.setLeft(insert(node.getLeft(), key));
            node.getLeft().setParent(node);
        } else {
            throw new RuntimeException("Duplicate key not supported!");
        }
        return rebalance(node);
    }

    /**
     * Removes a key from the tree, if it exists
     *
     * @param key to be removed
     */
    public void delete(T key) {
        root = delete(root, key);
    }

    /**
     * Delete a key from the avl tree rooted at a specified node.
     * Find the node that holds the key and remove the node from the tree.
     *
     * @param node the (sub)tree rooted at node which the key will be deleted from
     * @param key  the key to remove
     * @return the (new) root which the tree is rooted at after rebalancing
     */
    private Node<T> delete(Node<T> node, T key) {
        if (node == null) {
            return null;
        } else if (node.getKey().compareTo(key) < 0) {
            node.setRight(delete(node.getRight(), key));
        } else if (node.getKey().compareTo(key) > 0) {
            node.setLeft(delete(node.getLeft(), key));
        } else {
            if (node.getLeft() == null || node.getRight() == null) { // case of 1 or 0 child
                // node = node.left == null ? node.right : node.left;
                if (node.getLeft() == null && node.getRight() == null) {
                    node = null; // 0-child case
                } else if (node.getRight() == null) {
                    node.getLeft().setParent(node.getParent());
                    node = node.getLeft();
                } else {
                    node.getRight().setParent(node.getParent());
                    node = node.getRight();
                }
            } else { // 2-children case
                Node<T> successor = getMostLeft(node.getRight());
                node.setKey(successor.getKey());
                // since this is a 2-children case, successor of deleted node have
                // at most one child; right-child (else it would continue going left)
                node.setRight(delete(node.getRight(), successor.getKey()));
            }
        }

        if (node != null) { // make sure it isnt the 0-child case
            rebalance(node);
        }
        return node;
    }

    /**
     * Search for a node with the specified key.
     *
     * @param key the key to look for
     * @return node that has the specified key; null if not found
     */
    public Node<T> search(T key) {
        Node<T> curr = root;
        while (curr != null) {
            if (curr.getKey().compareTo(key) < 0) {
                curr = curr.getRight();
            } else if (curr.getKey().compareTo(key) > 0) {
                curr = curr.getLeft();
            } else {
                return curr;
            }
        }
        return null;
    }

    /**
     * Search for the predecessor of a given key.
     *
     * @param key find predecessor of this key
     * @return generic type value; null if key has no predecessor
     */
    public T predecessor(T key) {
        Node<T> curr = root;
        while (curr != null) {
            if (curr.getKey().compareTo(key) == 0) {
                break;
            } else if (curr.getKey().compareTo(key) < 0) {
                if (curr.getRight() == null) {
                    break;
                }
                curr = curr.getRight();
            } else {
                if (curr.getLeft() == null) {
                    break;
                }
                curr = curr.getLeft();
            }
        }
        if (curr.getKey().compareTo(key) < 0) { // we are done
            return curr.getKey();
        }
        return predecessor(curr); // pred could be an ancestor or child of curr node and hence handled separately
    }

    /**
     * Find the key of the predecessor of a specified node that exists in the tree
     * NOTE: the input node is assumed to be in the tree
     *
     * @param node node that exists in the tree
     * @return key value; null if node has no predecessor
     */
    private T predecessor(Node<T> node) {
        Node<T> curr = node;
        if (curr.getLeft() != null) { // has left-child
            return getMostRight(curr.getLeft()).getKey();
        } else { // so pred must be an ancestor
            while (curr != null) {
                if (curr.getKey().compareTo(node.getKey()) < 0) {
                    return curr.getKey();
                }
                curr = curr.getParent();
            }
        }
        return null;
    }

    /**
     * Search for the successor of a given key.
     *
     * @param key find successor of this key
     * @return generic type value; null if key has no successor
     */
    public T successor(T key) {
        Node<T> curr = root;
        while (curr != null) {
            if (curr.getKey().compareTo(key) == 0) {
                break;
            } else if (curr.getKey().compareTo(key) < 0) {
                if (curr.getRight() == null) {
                    break;
                }
                curr = curr.getRight();
            } else {
                if (curr.getLeft() == null) {
                    break;
                }
                curr = curr.getLeft();
            }
        }
        if (curr.getKey().compareTo(key) > 0) { // we are done
            return curr.getKey();
        }
        return successor(curr); // same exp as in the pred fn
    }

    /**
     * Find the key of the successor of a specified node that exists in the tree
     * NOTE: the input node is assumed to be in the tree
     *
     * @param node node that exists in the tree
     * @return key value; null if node has no successor
     */
    private T successor(Node<T> node) {
        Node<T> curr = node;
        if (curr.getRight() != null) { // has right-child
            return getMostLeft(curr.getRight()).getKey();
        }
        while (curr != null) {
            if (curr.getKey().compareTo(node.getKey()) > 0) {
                return curr.getKey();
            }
            curr = curr.getParent();
        }
        return null;
    }

    /**
     * prints in order traversal of the entire tree.
     */
    public void printInorder() {
        System.out.print("In-order: ");
        printInorder(root);
        System.out.println();
    }

    /**
     * Prints out in-order traversal of tree rooted at node
     *
     * @param node node which the tree is rooted at
     */
    private void printInorder(Node<T> node) {
        if (node == null) {
            return;
        }
        if (node.getLeft() != null) {
            printInorder(node.getLeft());
        }
        System.out.print(node + " ");
        if (node.getRight() != null) {
            printInorder(node.getRight());
        }
    }

    /**
     * prints pre-order traversal of the entire tree
     */
    public void printPreorder() {
        System.out.print("Pre-order: ");
        printPreorder(root);
        System.out.println();
    }


    /**
     * Prints out pre-order traversal of tree rooted at node
     *
     * @param node node which the tree is rooted at
     */
    private void printPreorder(Node<T> node) {
        if (node == null) {
            return;
        }
        System.out.print(node + " ");
        if (node.getLeft() != null) {
            printPreorder(node.getLeft());
        }
        if (node.getRight() != null) {
            printPreorder(node.getRight());
        }
    }

    /**
     * prints post-order traversal of the entire tree
     */
    public void printPostorder() {
        System.out.print("Post-order: ");
        printPostorder(root);
        System.out.println();
    }

    /**
     * Prints out post-order traversal of tree rooted at node
     *
     * @param node node which the tree is rooted at
     */
    private void printPostorder(Node<T> node) {
        if (node.getLeft() != null) {
            printPostorder(node.getLeft());
        }
        if (node.getRight() != null) {
            printPostorder(node.getRight());
        }
        System.out.print(node + " ");
    }

    /**
     * prints level-order traversal of the entire tree
     */
    public void printLevelorder() {
        System.out.print("Level-order: ");
        printLevelorder(root);
        System.out.println();
    }

    /**
     * Prints out level-order traversal of tree rooted at node
     *
     * @param node node which the tree is rooted at
     */
    private void printLevelorder(Node<T> node) {
        if (node == null) {
            return;
        }
        Queue<Node<T>> q = new LinkedList<>();
        q.add(node);
        while (!q.isEmpty()) {
            Node<T> curr = q.poll();
            System.out.print(curr.toString() + " ");
            if (curr.getLeft() != null) {
                q.add(curr.getLeft());
            }
            if (curr.getRight() != null) {
                q.add(curr.getRight());
            }
        }
    }
}
