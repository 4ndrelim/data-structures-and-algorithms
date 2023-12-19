package dataStructures.binarySearchTree;

import org.junit.Test;

public class BinarySearchTreeTest {
    @Test
    public void insert_shouldCorrectlyInsertNodes() {
        // Create BST instance
        BinarySearchTree<Integer, String> bst = new BinarySearchTree<>();

        // Insert elements
        bst.insert(5, "Five");
        bst.insert(3, "Three");
        bst.insert(7, "Seven");
        bst.insert(2, "Two");
        bst.insert(4, "Four");

        // Expected in-order traversal result
        List<Integer> expected = Arrays.asList(2, 3, 4, 5, 7);

        // Perform in-order traversal and get result
        List<Integer> result = bst.inOrderTraversal();

        // Assert that the result matches the expected list
        assert result.equals(expected);
    }

}
