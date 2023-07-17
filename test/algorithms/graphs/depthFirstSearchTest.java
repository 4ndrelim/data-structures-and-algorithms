package test.algorithms.graphs;

import org.junit.Test;

import src.algorithms.graphs.depthFirstSearch;
import src.algorithms.graphs.util.TreeNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class depthFirstSearchTest {

    @Test
    public void dfs_preOrderTraversal_shouldReturnAccurate() {
        // empty tree
        List<Integer> firstList = new ArrayList<>();
        TreeNode root1 = null;
        List<Integer> firstResult = depthFirstSearch.preOrder(root1);

        //standard tree
        //     1
        //    / \
        //   2   3
        //      / \
        //     4   5
        List<Integer> secondList = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        TreeNode rootRight2 = new TreeNode(3, new TreeNode(4), new TreeNode(5));
        TreeNode root2 = new TreeNode(1, new TreeNode(2), rootRight2);
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
        TreeNode rootLeft3 = new TreeNode(2, new TreeNode(3, new TreeNode(4), null), new TreeNode(5, new TreeNode(6), null));
        TreeNode root3 = new TreeNode(1, rootLeft3, new TreeNode(7));
        List<Integer> thirdResult = depthFirstSearch.preOrder(root3);

        assert firstResult.equals(firstList);
        assert secondResult.equals(secondList);
        assert thirdResult.equals(thirdList);
    }
}
