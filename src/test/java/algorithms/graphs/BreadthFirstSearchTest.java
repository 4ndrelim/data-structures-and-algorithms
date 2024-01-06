package algorithms.graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import algorithms.graphs.util.BinaryTreeNode;
import algorithms.graphs.util.GraphNode;

/**
 * Test cases for {@link breadthFirstSearch}.
 */
public class BreadthFirstSearchTest {

    @Test
    public void bfs_levelOrderTraversal_shouldReturnAccurate() {
        // empty tree
        List<Integer> firstList = new ArrayList<>();
        BinaryTreeNode root1 = null;
        List<Integer> firstResult = breadthFirstSearch.levelOrder(root1);

        //standard tree
        //     1
        //    / \
        //   2   3
        //      / \
        //     4   5
        List<Integer> secondList = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        BinaryTreeNode rootRight2 = new BinaryTreeNode(3, new BinaryTreeNode(4), new BinaryTreeNode(5));
        BinaryTreeNode root2 = new BinaryTreeNode(1, new BinaryTreeNode(2), rootRight2);
        List<Integer> secondResult = breadthFirstSearch.levelOrder(root2);

        //standard tree 2
        //          1
        //         / \
        //        2   7
        //       / \
        //      3   5
        //     /   /
        //    4   6
        List<Integer> thirdList = new ArrayList<>(Arrays.asList(1, 2, 7, 3, 5, 4, 6));
        BinaryTreeNode rootLeft3 =
            new BinaryTreeNode(2, new BinaryTreeNode(3, new BinaryTreeNode(4), null),
                new BinaryTreeNode(5, new BinaryTreeNode(6), null)
            );
        BinaryTreeNode root3 = new BinaryTreeNode(1, rootLeft3, new BinaryTreeNode(7));
        List<Integer> thirdResult = breadthFirstSearch.levelOrder(root3);

        assert firstResult.equals(firstList);
        assert secondResult.equals(secondList);
        assert thirdResult.equals(thirdList);
    }

    @Test
    public void bfs_friendHops_shouldReturnAccurate() {

        // Tests based on the following friend graph:
        // Andre ------ Ben ------- Diana ------- Evelyn ------- Gerald
        //  |           |                           |
        // Cathy      Felix--------------------------
        //  |
        // Harold ------ Iris            Anonymous
        GraphNode<String> andre = new GraphNode<>("andre");
        GraphNode<String> ben = new GraphNode<>("ben");
        GraphNode<String> cathy = new GraphNode<>("cathy");
        GraphNode<String> diana = new GraphNode<>("diana");
        GraphNode<String> evelyn = new GraphNode<>("evelyn");
        GraphNode<String> felix = new GraphNode<>("felix");
        GraphNode<String> gerald = new GraphNode<>("gerald");
        GraphNode<String> harold = new GraphNode<>("harold");
        GraphNode<String> iris = new GraphNode<>("iris");
        GraphNode<String> anonymous = new GraphNode<>("anonymous");
        GraphNode.connect(andre, ben);
        GraphNode.connect(andre, cathy);
        GraphNode.connect(cathy, harold);
        GraphNode.connect(harold, iris);
        GraphNode.connect(ben, felix);
        GraphNode.connect(ben, diana);
        GraphNode.connect(diana, evelyn);
        GraphNode.connect(evelyn, felix);
        GraphNode.connect(evelyn, gerald);

        assert breadthFirstSearch.friendHops(anonymous, diana) == -1;
        assert breadthFirstSearch.friendHops(iris, gerald) == 7;
        assert breadthFirstSearch.friendHops(andre, gerald) == 4;
        assert breadthFirstSearch.friendHops(felix, harold) == 4;
    }
}
