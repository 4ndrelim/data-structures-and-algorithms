package dataStructures.binarySearchTree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Implementation of Binary Search Tree.
 *
 * @param <T> generic type of object to be stored; must be comparable
 *            client methods:
 *            root()
 *            insert(T key)
 *            delete(T key)
 *            search(T key)
 *            predecessor(T key)
 *            successor(T key)
 *            searchMin()
 *            searchMax()
 *            getInorder()
 *            getPreorder()
 *            getPostorder()
 *            getLevelorder()
 */
public class BinarySearchTree<T extends Comparable<T>, V> {

    private Node<T, V> root;

    /**
     * Insert a key-value pair into the tree rooted at a specified node.
     * NOTE: ASSUMPTION THAT NO TWO NODES SHARE THE SAME KEY VALUE.
     *
     * @param node  the (sub)tree rooted at node which the key will be inserted into
     * @param key   the key to insert
     * @param value the value tied to the key to insert
     */
    private void insert(Node<T, V> node, T key, V value) {
        if (node.getKey().compareTo(key) < 0) {
            if (node.getRight() == null) {
                node.setRight(new Node<>(key, value));
            } else {
                insert(node.getRight(), key, value);
            }
        } else if (node.getKey().compareTo(key) > 0) {
            if (node.getLeft() == null) {
                node.setLeft(new Node<>(key, value));
            } else {
                insert(node.getLeft(), key, value);
            }
        } else {
            throw new RuntimeException("Duplicate key not supported!");
        }
    }

    /**
     * Delete a key from the binary search tree rooted at a specified node.
     * Find the node that holds the key and remove the node from the tree.
     *
     * @param node the (sub)tree rooted at node which the key will be deleted from
     * @param key  the key to remove
     * @return the (new) root which the tree is rooted at after rebalancing
     */
    private Node<T, V> delete(Node<T, V> node, T key) {
        if (node.getKey().compareTo(key) < 0) { // key > current node
            if (node.getRight() == null) {
                throw new RuntimeException("Key does not exist!");
            } else {
                node.setRight(delete(node.getRight(), key));
            }
        } else if (node.getKey().compareTo(key) > 0) { // key < current node
            if (node.getLeft() == null) {
                throw new RuntimeException("Key does not exist!");
            } else {
                node.setLeft(delete(node.getLeft(), key));
            }
        } else { // key == current node
            if (node.getLeft() == null && node.getRight() == null) { // 0 child case
                node = null;
            } else if (node.getLeft() == null || node.getRight() == null) { // 1 child case
                if (node.getRight() != null) {
                    node.getRight().setParent(node.getParent());
                    return node.getRight();
                } else {
                    node.getLeft().setParent(node.getParent());
                    return node.getLeft();
                }
            } else { // 2-children case
                T successorKey = successor(key);
                Node<T, V> successor = search(successorKey);

                // replaces the current node with successor
                node.setKey(successor.getKey());
                node.setValue(successor.getValue());

                // delete the original successor
                // successor will definitely be in right subtree of current node and not an ancestor
                node.setRight(delete(node.getRight(), successor.getKey()));
            }
        }

        return node;
    }

    /**
     * Find the node with the minimum key in the tree rooted at a specified node.
     *
     * @param n the (sub)tree rooted at node which the minimum key will be searched for
     * @return node with the minimum key
     *     NOTE: ASSUMPTION THAT TREE IS NOT EMPTY
     */
    private Node<T, V> searchMin(Node<T, V> n) {
        if (n.getLeft() == null) {
            return n;
        } else {
            return searchMin(n.getLeft());
        }
    }

    /**
     * Find the node with the maximum key in the tree rooted at a specified node.
     *
     * @param n the (sub)tree rooted at node which the maximum key will be searched for
     * @return node with the maximum key
     *     NOTE: ASSUMPTION THAT TREE IS NOT EMPTY
     */
    private Node<T, V> searchMax(Node<T, V> n) {
        if (n.getRight() == null) {
            return n;
        } else {
            return searchMax(n.getRight());
        }
    }

    /**
     * Find the key of the predecessor of a specified node that exists in the tree
     * NOTE: the input node is assumed to be in the tree
     *
     * @param node node that exists in the tree
     * @return key value; null if node has no predecessor
     */
    private T predecessor(Node<T, V> node) {
        Node<T, V> curr = node;
        if (curr.getLeft() != null) { // predecessor in children
            return searchMax(curr.getLeft()).getKey();
        } else { // predecessor in ancestor
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
     * Find the key of the successor of a specified node that exists in the tree
     * NOTE: the input node is assumed to be in the tree
     *
     * @param node node that exists in the tree
     * @return key value; null if node has no successor
     */
    private T successor(Node<T, V> node) {
        Node<T, V> curr = node;
        if (curr.getRight() != null) { // successor in children
            return searchMin(curr.getRight()).getKey();
        } else { // successor in ancestor
            while (curr != null) {
                if (curr.getKey().compareTo(node.getKey()) > 0) { // finds the closest
                    return curr.getKey();
                }
                curr = curr.getParent();
            }
        }
        return null;
    }

    /**
     * Stores in-order traversal of tree rooted at node into a list
     *
     * @param node node which the tree is rooted at
     */
    private void getInorder(Node<T, V> node, List<String> result) {
        if (node == null) {
            return;
        }

        if (node.getLeft() != null) {
            getInorder(node.getLeft(), result);
        }

        result.add(node.toString());

        if (node.getRight() != null) {
            getInorder(node.getRight(), result);
        }
    }

    /**
     * Stores in-order traversal of tree rooted at node into a list
     *
     * @param node node which the tree is rooted at
     */
    private void getPreorder(Node<T, V> node, List<String> result) {
        if (node == null) {
            return;
        }

        result.add(node.toString());

        if (node.getLeft() != null) {
            getPreorder(node.getLeft(), result);
        }

        if (node.getRight() != null) {
            getPreorder(node.getRight(), result);
        }
    }

    /**
     * Stores post-order traversal of tree rooted at node into a list
     *
     * @param node node which the tree is rooted at
     */
    private void getPostorder(Node<T, V> node, List<String> result) {
        if (node == null) {
            return;
        }

        if (node.getLeft() != null) {
            getPostorder(node.getLeft(), result);
        }

        if (node.getRight() != null) {
            getPostorder(node.getRight(), result);
        }

        result.add(node.toString());
    }

    /**
     * Stores level-order traversal of tree rooted at node into a list
     *
     * @param node node which the tree is rooted at
     */
    private void getLevelorder(Node<T, V> node, List<String> result) {
        if (node == null) {
            return;
        }

        Queue<Node<T, V>> queue = new LinkedList<>();
        queue.add(node);
        while (!queue.isEmpty()) {
            Node<T, V> current = queue.poll();
            result.add(current.toString());

            if (current.getLeft() != null) {
                queue.add(current.getLeft());
            }
            if (current.getRight() != null) {
                queue.add(current.getRight());
            }
        }
    }

    /**
     * Get root of tree.
     *
     * @return root
     */
    public Node<T, V> root() {
        return root;
    }

    /**
     * Inserts a key into the tree
     *
     * @param key to be inserted
     */
    public void insert(T key, V value) {
        if (root == null) {
            root = new Node<>(key, value);
        } else {
            insert(root, key, value);
        }
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
     * Search for a node with the specified key.
     *
     * @param key the key to look for
     * @return node that has the specified key; null if not found
     */
    public Node<T, V> search(T key) {
        Node<T, V> curr = root;
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
        Node<T, V> curr = root;
        while (curr != null) {
            if (curr.getKey().compareTo(key) == 0) {
                break;
            } else if (curr.getKey().compareTo(key) < 0) {
                curr = curr.getRight();
            } else {
                curr = curr.getLeft();
            }
        }

        return predecessor(curr);
    }

    /**
     * Search for the successor of a given key.
     *
     * @param key find successor of this key
     * @return generic type value; null if key has no successor
     */
    public T successor(T key) {
        Node<T, V> curr = root;
        while (curr != null) {
            if (curr.getKey().compareTo(key) == 0) {
                break;
            } else if (curr.getKey().compareTo(key) < 0) {
                curr = curr.getRight();
            } else {
                curr = curr.getLeft();
            }
        }

        return successor(curr);
    }

    /**
     * Search for the minimum key in the tree.
     *
     * @return node with the minimum key; null if tree is empty
     */
    public Node<T, V> searchMin() {
        if (root == null) {
            return null;
        } else {
            return searchMin(root);
        }
    }

    /**
     * Search for the maximum key in the tree.
     *
     * @return node with the maximum key; null if tree is empty
     */
    public Node<T, V> searchMax() {
        if (root == null) {
            return null;
        } else {
            return searchMax(root);
        }
    }

    public List<String> getInorder() {
        List<String> result = new ArrayList<>();
        getInorder(root, result);
        return result;
    }

    public List<String> getPreorder() {
        List<String> result = new ArrayList<>();
        getPreorder(root, result);
        return result;
    }

    public List<String> getPostorder() {
        List<String> result = new ArrayList<>();
        getPostorder(root, result);
        return result;
    }

    public List<String> getLevelorder() {
        List<String> result = new ArrayList<>();
        getLevelorder(root, result);
        return result;
    }
}
