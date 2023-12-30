package algorithms.graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import algorithms.graphs.util.BinaryTreeNode;

/**
 * Test cases for {@link depthFirstSearch}.
 */
public class DepthFirstSearchTest {

    @Test
    public void dfs_preOrderTraversal_shouldReturnAccurate() {
        // empty tree
        List<Integer> firstList = new ArrayList<>();
        BinaryTreeNode root1 = null;
        List<Integer> firstResult = depthFirstSearch.preOrder(root1);

        //standard tree
        //     1
        //    / \
        //   2   3
        //      / \
        //     4   5
        List<Integer> secondList = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        BinaryTreeNode rootRight2 = new BinaryTreeNode(3, new BinaryTreeNode(4), new BinaryTreeNode(5));
        BinaryTreeNode root2 = new BinaryTreeNode(1, new BinaryTreeNode(2), rootRight2);
        List<Integer> secondResult = depthFirstSearch.preOrder(root2);

        //standard tree 2
        //          1
        //         / \
        //        2   7
        //       / \
        //      3   5
        //     /   /
        //    4   6
        List<Integer> thirdList = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7));
        BinaryTreeNode rootLeft3 =
            new BinaryTreeNode(2, new BinaryTreeNode(3, new BinaryTreeNode(4), null),
                new BinaryTreeNode(5, new BinaryTreeNode(6), null)
            );
        BinaryTreeNode root3 = new BinaryTreeNode(1, rootLeft3, new BinaryTreeNode(7));
        List<Integer> thirdResult = depthFirstSearch.preOrder(root3);

        assert firstResult.equals(firstList);
        assert secondResult.equals(secondList);
        assert thirdResult.equals(thirdList);
    }

    @Test
    public void dfs_inOrderTraversal_shouldReturnAccurate() {
        // empty tree
        List<Integer> firstList = new ArrayList<>();
        BinaryTreeNode root1 = null;
        List<Integer> firstResult = depthFirstSearch.inOrder(root1);

        //standard tree
        //     1
        //    / \
        //   2   3
        //      / \
        //     4   5
        List<Integer> secondList = new ArrayList<>(Arrays.asList(2, 1, 4, 3, 5));
        BinaryTreeNode rootRight2 = new BinaryTreeNode(3, new BinaryTreeNode(4), new BinaryTreeNode(5));
        BinaryTreeNode root2 = new BinaryTreeNode(1, new BinaryTreeNode(2), rootRight2);
        List<Integer> secondResult = depthFirstSearch.inOrder(root2);

        //standard tree 2
        //          1
        //         / \
        //        2   7
        //       / \
        //      3   5
        //     /   /
        //    4   6
        List<Integer> thirdList = new ArrayList<>(Arrays.asList(4, 3, 2, 6, 5, 1, 7));
        BinaryTreeNode rootLeft3 =
            new BinaryTreeNode(2, new BinaryTreeNode(3, new BinaryTreeNode(4), null),
                new BinaryTreeNode(5, new BinaryTreeNode(6), null)
            );
        BinaryTreeNode root3 = new BinaryTreeNode(1, rootLeft3, new BinaryTreeNode(7));
        List<Integer> thirdResult = depthFirstSearch.inOrder(root3);

        assert firstResult.equals(firstList);
        assert secondResult.equals(secondList);
        assert thirdResult.equals(thirdList);
    }

    @Test
    public void dfs_postOrderTraversal_shouldReturnAccurate() {
        // empty tree
        List<Integer> firstList = new ArrayList<>();
        BinaryTreeNode root1 = null;
        List<Integer> firstResult = depthFirstSearch.inOrder(root1);

        //standard tree
        //     1
        //    / \
        //   2   3
        //      / \
        //     4   5
        List<Integer> secondList = new ArrayList<>(Arrays.asList(2, 4, 5, 3, 1));
        BinaryTreeNode rootRight2 = new BinaryTreeNode(3, new BinaryTreeNode(4), new BinaryTreeNode(5));
        BinaryTreeNode root2 = new BinaryTreeNode(1, new BinaryTreeNode(2), rootRight2);
        List<Integer> secondResult = depthFirstSearch.postOrder(root2);

        //standard tree 2
        //          1
        //         / \
        //        2   7
        //       / \
        //      3   5
        //     /   /
        //    4   6
        List<Integer> thirdList = new ArrayList<>(Arrays.asList(4, 3, 6, 5, 2, 7, 1));
        BinaryTreeNode rootLeft3 =
            new BinaryTreeNode(2, new BinaryTreeNode(3, new BinaryTreeNode(4), null),
                new BinaryTreeNode(5, new BinaryTreeNode(6), null)
            );
        BinaryTreeNode root3 = new BinaryTreeNode(1, rootLeft3, new BinaryTreeNode(7));
        List<Integer> thirdResult = depthFirstSearch.postOrder(root3);

        assert firstResult.equals(firstList);
        assert secondResult.equals(secondList);
        assert thirdResult.equals(thirdList);
    }
}
