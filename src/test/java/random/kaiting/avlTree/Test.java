package random.kaiting.avlTree;

import dataStructures.avlTree.AVLTree;

/**
 * Basic testing.
 * Note that a tree structure can be entirely
 * determined with one of the following:
 * 1. In-order + pre-order
 * 2. In-order + post-order
 * 3. In-order + level-order
 */
public class Test {
    /**
     * Runs the custom test.
     *
     * @param args unused.
     */
    public static void main(String[] args) {
        AVLTree<Integer> tree = new AVLTree<>();
        System.out.println("Inserting...");
        tree.insert(5);
        tree.insert(4);
        tree.insert(8);
        tree.insert(6);
        tree.insert(7);
        tree.insert(3);
        tree.insert(1);

        // tree:
        //              5
        //          /       \
        //        3           7
        //      /   \       /   \
        //    1      4     6     8
        //

        Integer x = 6;
        System.out.println("parent of " + x + " is " + tree.search(x).getParent().getKey().toString());

        System.out.println("Deleting...");
        tree.delete(5);
        tree.delete(7);
        tree.printInorder();
        tree.printPreorder();
        tree.printLevelorder();

        System.out.println("Inserting...");
        tree.insert(9);
        tree.insert(2);
        tree.printInorder();
        tree.printPreorder();
        tree.printPostorder();

        // updated tree:
        //             6
        //          /     \
        //         3       8
        //       /   \       \
        //      1     4       9
        //       \
        //        2

        x = 4;
        System.out.println("parent of " + x + " is " + tree.search(x).getParent().getKey().toString());

        System.out.println("Testing successors & predecessors queries...");
        System.out.println(tree.predecessor(1));
        System.out.println(tree.predecessor(6));

        System.out.println(tree.successor(1));
        System.out.println(tree.successor(6));
        System.out.println(tree.successor(9));
    }
}
