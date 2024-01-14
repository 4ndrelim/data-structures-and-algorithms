package dataStructures.bTree;

/** This BTree implementation is a simplified implementation, which supports the basic search, insert, delete and
 * in-order traversal operations. It is not designed to cover edge cases.
 */
public class BTree {
    private BTreeNode root;
    private int t;

    /**
     * Constructs a B-tree with a specified minimum degree.
     * @param t The minimum degree of the B-tree.
     */
    public BTree(int t) {
        this.root = null;
        this.t = t;
    }

    /** Inner class representing a B-tree node
     */
    private class BTreeNode {
        int[] keys;
        BTreeNode[] children;
        int keyCount; // necessary for Java implementation due to fixed-size arrays
        boolean leaf;

        /**
         * Constructor for creating a B-tree node.
         * @param t The minimum degree of the B-tree.
         * @param leaf Indicates whether the node is a leaf.
         */
        public BTreeNode(int t, boolean leaf) {
            this.keys = new int[2 * t - 1];
            this.children = new BTreeNode[2 * t];
            this.keyCount = 0;
            this.leaf = leaf;
        }
    }

    /**
     * Searches for a key in the B-tree.
     * @param key The key to search for.
     * @return true if the key is found, false otherwise.
     */
    public boolean search(int key) {
        return search(key, this.root);
    }

    private boolean search(int key, BTreeNode node) {
        int i = 0;
        while (i < node.keyCount && key > node.keys[i]) {
            i++;
        }

        if (i < node.keyCount && key == node.keys[i]) {
            return true;
        }

        if (node.leaf) {
            return false;
        } else {
            return search(key, node.children[i]);
        }
    }

    /**
     * Inserts a key into the B-tree.
     * @param key The key to insert.
     */
    public void insert(int key) {
        if (this.root == null) {
            this.root = new BTreeNode(this.t, true);
            this.root.keys[0] = key;
            this.root.keyCount = 1;
        } else {
            if (this.root.keyCount == 2 * t - 1) { // root is full
                BTreeNode newRoot = new BTreeNode(this.t, false);
                newRoot.children[0] = this.root;
                splitChild(newRoot, 0);
                insertNonFull(newRoot, key);
                this.root = newRoot;
            } else {
                insertNonFull(root, key);
            }
        }
    }

    /**
     * Splits a child node of the current node during insertion, promoting the middle key to the parent node.
     * This method is called when the child node is full and needs to be split to maintain B-tree properties.
     * @param x The parent node.
     * @param i The index of the child node to split.
     */
    private void splitChild(BTreeNode x, int i) {
        BTreeNode y = x.children[i];
        BTreeNode z = new BTreeNode(t, y.leaf);

        for (int j = x.keyCount; j >= i; j--) {
            x.children[j + 1] = x.children[j];
        }

        x.children[i + 1] = z;
        x.keys[i] = y.keys[t - 1]; // promote middle key to parent
        x.keyCount++;

        for (int j = 0; j < t - 1; j++) {
            z.keys[j] = y.keys[j + t];
        }

        y.keyCount = t - 1;
        z.keyCount = t - 1;
    }

    /**
     * Inserts a key into a non-full B-tree node. If the node is full, it recursively splits the necessary child nodes.
     * @param x The current B-tree node.
     * @param key The key to insert.
     */
    private void insertNonFull(BTreeNode x, int key) {
        int i = x.keyCount - 1;

        if (x.leaf) {
            while (i >= 0 && key < x.keys[i]) {
                x.keys[i + 1] = x.keys[i];
                i--;
            }
            x.keys[i + 1] = key;
            x.keyCount++;
        } else {
            while (i >= 0 && key < x.keys[i]) {
                i--;
            }
            i++;

            if (x.children[i].keyCount == 2 * t - 1) {
                splitChild(x, i);
                if (key > x.keys[i]) {
                    i++;
                }
            }

            insertNonFull(x.children[i], key);
        }
    }

    /**
     * Prints the keys of the B-tree level by level.
     */
    public void printBTree() {
        if (root != null) {
            printBTree(root, 0);
        }
    }

    private void printBTree(BTreeNode node, int level) {
        System.out.println("Level " + level + ": " + node.keyCount + " keys");

        for (int i = 0; i < node.keyCount; i++) {
            System.out.print(node.keys[i] + " ");
        }
        System.out.println();

        if (!node.leaf) {
            for (int i = 0; i <= node.keyCount; i++) {
                if (node.children[i] != null) {
                    printBTree(node.children[i], level + 1);
                }
            }
        }
    }

    /**
     * Deletes the specified key from the B-tree.
     * @param key key to be deleted
     */
    public void delete(int key) {
        deleteRecursive(this.root, key);
    }
    private void deleteRecursive(BTreeNode x, int key) {
        int i = 0;

        while (i < x.keyCount && key > x.keys[i]){
            i += 1;
        }

        if (i < x.keyCount && key == x.keys[i]) {
            if (x.leaf) {
                for (int curr = i; curr < x.keyCount; curr++) {
                    x.keys[curr] = x.keys[curr + 1];
                }
                x.keyCount -= 1;
            } else {
                BTreeNode y = x.children[i];
                BTreeNode z = x.children[i + 1];
                if (y.keyCount >= this.t) {
                    int predecessor = getPredecessor(y);
                    x.keys[i] = predecessor;
                    deleteRecursive(y, predecessor);
                } else if (z.keyCount >= this.t) {
                    int successor = getSuccessor(z);
                    x.keys[i] = successor;
                    deleteRecursive(z, successor);
                } else {
                    mergeNodes(x, i, y, z);
                    deleteRecursive(y, key);
                }
            }
        } else {
            if (x.leaf) {
                System.out.println("Key " +  key + " does not exist in the B-tree.");
            } else {
                if (x.children[i].keyCount < this.t) {
                    fixChild(x, i);
                }
                deleteRecursive(x.children[i], key);
            }
        }
    }

    /**
     * Retrieves the predecessor key of the given B-tree node.
     * @param x The B-tree node.
     * @return The predecessor key.
     */
    private int getPredecessor(BTreeNode x) {
        while (!x.leaf) {
            x = x.children[x.keyCount - 1];
        }
        return x.keys[x.keyCount - 1];
    }

    /**
     * Retrieves the successor key of the given B-tree node.
     * @param x The B-tree node.
     * @return The successor key.
     */
    public int getSuccessor(BTreeNode x) {
        while (!x.leaf) {
            x = x.children[0];
        }
        return x.keys[0];
    }

    /**
     * Merges two child nodes of a parent node in a B-tree.
     * This method is called when a child node has fewer keys than required.
     *
     * @param x The parent node.
     * @param i The index of the child node to be merged with its right sibling.
     * @param y The left child node to be merged.
     * @param z The right child node to be merged.
     */
    private void mergeNodes(BTreeNode x, int i, BTreeNode y, BTreeNode z) {
        // Append the key from the current node to the left child
        y.keys[y.keyCount] = x.keys[i];
        y.keyCount++;

        // Copy the keys and children of the right child to the left child
        System.arraycopy(z.keys, 0, y.keys, y.keyCount, z.keyCount);
        System.arraycopy(z.children, 0, y.children, y.keyCount, z.keyCount);

        // Adjust the key count of the left child
        y.keyCount += z.keyCount;

        // Remove the key and child reference from the current node
        for (int j = i; j < x.keyCount - 1; j++) {
            x.keys[j] = x.keys[j + 1];
            x.children[j + 1] = x.children[j + 2];
        }

        // Decrement the key count of the current node
        x.keyCount--;

        if (x.keyCount == 0) {
            this.root = y;
        }
    }

    /**
     * Fixes a child node of a parent node by borrowing keys or merging with siblings.
     *
     * @param x The parent node.
     * @param i The index of the child node to be fixed.
     */
    private void fixChild(BTreeNode x, int i) {
        if (i > 0 && x.children[i - 1].keyCount >= t) {
            borrowFromLeft(x, i);
        } else if (i < x.children.length - 1 && x.children[i + 1].keyCount >= t) {
            borrowFromRight(x, i);
        } else {
            if (i > 0) {
                mergeNodes(x, i - 1, x.children[i - 1], x.children[i]);
                i--; // Adjust i after merging
            } else {
                mergeNodes(x, i, x.children[i], x.children[i + 1]);
            }
        }
    }

    /**
     * Borrows a key from the left sibling of a child node in a B-tree.
     *
     * @param x The parent node.
     * @param i The index of the child node.
     */
    private void borrowFromLeft(BTreeNode x, int i) {
        BTreeNode child = x.children[i];
        BTreeNode sibling = x.children[i - 1];

        // Move key from parent to the beginning of the child
        for (int j = child.keyCount; j > 0; j--) {
            child.keys[j] = child.keys[j - 1];
        }
        child.keys[0] = x.keys[i - 1];

        // Update parent key with the last key from the sibling
        x.keys[i - 1] = sibling.keys[sibling.keyCount - 1];

        // If not a leaf, move child reference from the sibling to the child
        if (!child.leaf) {
            for (int j = child.keyCount + 1; j > 0; j--) {
                child.children[j] = child.children[j - 1];
            }
            child.children[0] = sibling.children[sibling.keyCount];
        }

        child.keyCount++;
        sibling.keyCount--;
    }

    /**
     * Borrows a key from the right sibling of a child node in a B-tree.
     *
     * @param x The parent node.
     * @param i The index of the child node.
     */
    private void borrowFromRight(BTreeNode x, int i) {
        BTreeNode child = x.children[i];
        BTreeNode sibling = x.children[i + 1];

        // Move key from parent to the end of the child
        child.keys[child.keyCount] = x.keys[i];

        // Update parent key with the first key from the sibling
        x.keys[i] = sibling.keys[0];

        // If not a leaf, move child reference from the sibling to the child
        if (!child.leaf) {
            child.children[child.keyCount + 1] = sibling.children[0];

            // Shift keys and children in the sibling
            for (int j = 0; j < sibling.keyCount - 1; j++) {
                sibling.keys[j] = sibling.keys[j + 1];
                sibling.children[j] = sibling.children[j + 1];
            }
            sibling.children[sibling.keyCount - 1] = sibling.children[sibling.keyCount];
        }

        child.keyCount++;
        sibling.keyCount--;
    }

}
