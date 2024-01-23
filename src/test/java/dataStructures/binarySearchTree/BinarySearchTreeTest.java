package dataStructures.binarySearchTree;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class BinarySearchTreeTest {
    @Test
    public void insert_shouldCorrectlyInsertNodes() {
        BinarySearchTree<Integer, String> bst = new BinarySearchTree<>();
        bst.insert(5, "Five");
        bst.insert(3, "Three");
        bst.insert(7, "Seven");
        bst.insert(2, "Two");
        bst.insert(4, "Four");

        // Perform in-order traversal and get result
        List<String> result = bst.getInorder();

        // Expected in-order traversal result
        List<String> expected =
            Arrays.asList("Key: 2, Value: Two", "Key: 3, Value: Three", "Key: 4, Value: Four", "Key: 5, Value: Five",
                "Key: 7, Value: Seven"
            );

        assert result.equals(expected);
    }

    @Test
    public void delete_shouldCorrectlyHandleLeafNode() {
        BinarySearchTree<Integer, String> bst = new BinarySearchTree<>();
        bst.insert(5, "Five");
        bst.insert(3, "Three");
        bst.insert(7, "Seven");

        bst.delete(7);

        // Perform in-order traversal and get result
        List<String> result = bst.getInorder();

        // Expected in-order traversal result after deletion
        List<String> expected = Arrays.asList("Key: 3, Value: Three", "Key: 5, Value: Five");

        assert result.equals(expected);
    }

    @Test
    public void delete_shouldCorrectlyHandleNodeWithOneChild() {
        BinarySearchTree<Integer, String> bst = new BinarySearchTree<>();
        bst.insert(5, "Five");
        bst.insert(3, "Three");
        bst.insert(6, "Six");
        bst.insert(7, "Seven");

        bst.delete(6);

        // Perform in-order traversal and get result
        List<String> result = bst.getInorder();

        // Expected in-order traversal result after deletion
        List<String> expected = Arrays.asList("Key: 3, Value: Three", "Key: 5, Value: Five", "Key: 7, Value: Seven");

        assert result.equals(expected);
    }

    @Test
    public void delete_shouldCorrectlyHandleNodeWithTwoChildren() {
        BinarySearchTree<Integer, String> bst = new BinarySearchTree<>();
        bst.insert(5, "Five");
        bst.insert(3, "Three");
        bst.insert(7, "Seven");
        bst.insert(2, "Two");
        bst.insert(4, "Four");

        bst.delete(3);

        // Perform in-order traversal and get result
        List<String> result = bst.getInorder();

        // Expected in-order traversal result after deletion
        List<String> expected =
            Arrays.asList("Key: 2, Value: Two", "Key: 4, Value: Four", "Key: 5, Value: Five", "Key: 7, Value: Seven");

        assert result.equals(expected);
    }
}
