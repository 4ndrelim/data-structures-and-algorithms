/**
 * Basic testing
 * Note that a tree structure can be entirely 
 * determined with one of the following:
 * 1. In-order + pre-order
 * 2. In-order + post-order
 * 3. In-order + level-order
 */

public class Test {
  public static void main(String[] args) {
    //              5
    //          /       \
    //        3           7
    //      /   \       /   \
    //    1      4     6     8
    //                   
    //                    
    AVLTree<Integer> tree = new AVLTree<>();
    Node<Integer> root = tree.root();
    tree.insert(5);
    tree.insert(4);
    tree.insert(8);
    tree.insert(6);
    tree.insert(7);
    tree.insert(3);
    tree.insert(1);

    tree.delete(5);
    tree.delete(7);
    tree.printInorder();
    tree.printPreorder();
    tree.printLevelorder();
  }
}
