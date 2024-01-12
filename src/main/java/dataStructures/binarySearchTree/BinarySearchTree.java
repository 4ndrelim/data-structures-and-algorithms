package dataStructures.binarySearchTree;

import java.util.*;

/**
 * Implementation of Binary Search Tree.
 * @param <T> generic type of object to be stored; must be comparable
 * client methods:
 *  root()
 *  insert(T key)
 *  delete(T key)
 *  search(T key)
 *  predecessor(T key)
 *  successor(T key)
 *  searchMin()
 *  searchMax()
 *  getInorder()
 *  getPreorder()
 *  getPostorder()
 *  getLevelorder()
 */
public class BinarySearchTree<T extends Comparable<T>, V> {

    private Node<T, V> root;

    /**
     * Insert a key-value pair into the tree rooted at a specified node.
     * NOTE: ASSUMPTION THAT NO TWO NODES SHARE THE SAME KEY VALUE.
     * @param node the (sub)tree rooted at node which the key will be inserted into
     * @param key the key to insert
     * @param value the value tied to the key to insert
     */
    private void insert(Node<T, V> node, T key, V value) {
        if (node.key.compareTo(key) < 0) {
            if (node.right == null) {
                node.right = new Node<>(key, value);
            } else {
                insert(node.right, key, value);
            }
        } else if (node.key.compareTo(key) > 0) {
            if (node.left == null) {
                node.left = new Node<>(key, value);
            } else {
                insert(node.left, key, value);
            }
        } else {
            throw new RuntimeException("Duplicate key not supported!");
        }
    }

    /**
     * Delete a key from the binary search tree rooted at a specified node.
     * Find the node that holds the key and remove the node from the tree.
     * @param node the (sub)tree rooted at node which the key will be deleted from
     * @param key the key to remove
     * @return the (new) root which the tree is rooted at after rebalancing
     */
    private Node<T, V> delete(Node<T, V> node, T key) {
        if (node.key.compareTo(key) < 0) { // key > current node
            if (node.right == null) {
                throw new RuntimeException("Key does not exist!");
            } else {
                node.right = delete(node.right, key);
            }
        } else if (node.key.compareTo(key) > 0) { // key < current node
            if (node.left == null) {
                throw new RuntimeException("Key does not exist!");
            } else {
                node.left = delete(node.left, key);
            }
        } else { // key == current node
            if (node.left == null && node.right == null) { // 0 child case
                node = null;
            } else if (node.left == null || node.right == null) { // 1 child case
                if (node.right != null) {
                    node.right.parent = node.parent;
                    return node.right;
                } else {
                    node.left.parent = node.parent;
                    return node.left;
                }
            } else { // 2-children case
                T successorKey = successor(key);
                Node<T, V> successor = search(successorKey);

                // replaces the current node with successor
                node.key = successor.key;
                node.value = successor.value;

                // delete the original successor
                // successor will definitely be in right subtree of current node and not an ancestor
                node.right = delete(node.right, successor.key);
            }
        }

        return node;
    }

    /**
     * Find the node with the minimum key in the tree rooted at a specified node.
     *
     * @param n the (sub)tree rooted at node which the minimum key will be searched for
     * @return node with the minimum key
     * NOTE: ASSUMPTION THAT TREE IS NOT EMPTY
     */
    private Node<T, V> searchMin(Node<T, V> n) {
        if (n.left == null) {
            return n;
        } else {
            return searchMin(n.left);
        }
    }

    /**
     * Find the node with the maximum key in the tree rooted at a specified node.
     *
     * @param n the (sub)tree rooted at node which the maximum key will be searched for
     * @return node with the maximum key
     * NOTE: ASSUMPTION THAT TREE IS NOT EMPTY
     */
    private Node<T, V> searchMax(Node<T, V> n) {
        if (n.right == null) {
            return n;
        } else {
            return searchMax(n.right);
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
        if (curr.left != null) { // predecessor in children
            return searchMax(curr.left).key;
        } else { // predecessor in ancestor
            while (curr != null) {
                if (curr.key.compareTo(node.key) < 0) {
                    return curr.key;
                }
                curr = curr.parent;
            }
        }
        return null;
    }

    /**
     * Find the key of the successor of a specified node that exists in the tree
     * NOTE: the input node is assumed to be in the tree
     * @param node node that exists in the tree
     * @return key value; null if node has no successor
     */
    private T successor(Node<T, V> node) {
        Node<T, V> curr = node;
        if (curr.right != null) { // successor in children
            return searchMin(curr.right).key;
        } else { // successor in ancestor
            while (curr != null) {
                if (curr.key.compareTo(node.key) > 0) { // finds the cloests
                    return curr.key;
                }
                curr = curr.parent;
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

        if (node.left != null) {
            getInorder(node.left, result);
        }

        result.add(node.toString());

        if (node.right != null) {
            getInorder(node.right, result);
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

        if (node.left != null) {
            getPreorder(node.left, result);
        }

        if (node.right != null) {
            getPreorder(node.right, result);
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

        if (node.left != null) {
            getPostorder(node.left, result);
        }

        if (node.right != null) {
            getPostorder(node.right, result);
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

            if (current.left != null) {
                queue.add(current.left);
            }
            if (current.right != null) {
                queue.add(current.right);
            }
        }
    }

    /**
     * Get root of tree.
     * @return root
     */
    public Node<T, V> root() {
        return root;
    }

    /**
     * Inserts a key into the tree
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
     * @param key to be removed
     */
    public void delete(T key) {
        root = delete(root, key);
    }

    /**
     * Search for a node with the specified key.
     * @param key the key to look for
     * @return node that has the specified key; null if not found
     */
    public Node<T, V> search(T key) {
        Node<T, V> curr = root;
        while (curr != null) {
            if (curr.key.compareTo(key) < 0) {
                curr = curr.right;
            } else if (curr.key.compareTo(key) > 0) {
                curr = curr.left;
            } else {
                return curr;
            }
        }
        return null;
    }

    /**
     * Search for the predecessor of a given key.
     * @param key find predecessor of this key
     * @return generic type value; null if key has no predecessor
     */
    public T predecessor(T key) {
        Node<T, V> curr = root;
        while (curr != null) {
            if (curr.key.compareTo(key) == 0) {
                break;
            } else if (curr.key.compareTo(key) < 0) {
                curr = curr.right;
            } else {
                curr = curr.left;
            }
        }

        return predecessor(curr);
    }

    /**
     * Search for the successor of a given key.
     * @param key find successor of this key
     * @return generic type value; null if key has no successor
     */
    public T successor(T key) {
        Node<T, V> curr = root;
        while (curr != null) {
            if (curr.key.compareTo(key) == 0) {
                break;
            } else if (curr.key.compareTo(key) < 0) {
                curr = curr.right;
            } else {
                curr = curr.left;
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
