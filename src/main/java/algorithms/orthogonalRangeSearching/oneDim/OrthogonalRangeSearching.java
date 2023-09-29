package algorithms.orthogonalRangeSearching.oneDim;

import algorithms.graphs.util.BinaryTreeNode;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class OrthogonalRangeSearching {

    public static BinaryTreeNode buildTree(int[] inputs, int start, int end) {
        //build range tree from inputs
        int mid = (end + start) / 2;
        Arrays.sort(inputs);

        if (start > end) {
            return null;
        } else if (end - start + 1 > 3) {
            return new BinaryTreeNode(inputs[mid], buildTree(inputs, start, mid), buildTree(inputs, mid + 1, end));
        } else if (end - start + 1 == 3) {
            return new BinaryTreeNode(inputs[mid], buildTree(inputs, start, mid), new BinaryTreeNode(inputs[end]));
        } else if (end - start + 1 == 2) {
            return new BinaryTreeNode(inputs[mid], new BinaryTreeNode(inputs[start]), new BinaryTreeNode(inputs[end]));
        } else {
            return new BinaryTreeNode(inputs[mid]);
        }
    }
    public static BinaryTreeNode findSplit(BinaryTreeNode root, int low, int high) {
        BinaryTreeNode v = root;

        while (true) {
            if (v == null) {
                return null;
            } else {
                if (high <= v.getVal()) {
                    v = v.getLeft();
                } else if (low > v.getVal()) {
                    v = v.getRight();
                } else {
                    break;
                }
            }
        }
        return v;
    }

    public static void allLeafTraversal(BinaryTreeNode v, List<Integer> result) {
        if (v != null) {
            if (v.getLeft() != null) {
                allLeafTraversal(v.getLeft(), result);
            }
            if (v.getLeft() == null && v.getRight() == null) { // leaf
                result.add(v.getVal());
            }
            if (v.getRight() != null) {
                allLeafTraversal(v.getRight(), result);
            }
        }
    }

    public static void leftTraversal(BinaryTreeNode v, int low, List<Integer> result) {
        if (v != null) {
            if (v.getLeft() == null && v.getRight() == null) { // leaf
                result.add(v.getVal());
            } else {
                if (low <= v.getVal()) {
                    leftTraversal(v.getLeft(), low, result);
                    allLeafTraversal(v.getRight(), result);
                } else {
                    leftTraversal(v.getRight(), low, result);
                }
            }
        }
    }

    public static void rightTraversal(BinaryTreeNode v, int high, List<Integer> result) {
        //not exactly symmetrical to right traversal due to v.getval() being max of left subtree
        //if v.getval() > high, we dont want to traverse any of its subtrees in right traversal
        //but if v.getval < low, we might still want to traverse the right subtree of val
        if (v != null) {
            if (v.getLeft() == null && v.getRight() == null) { // leaf
                result.add(v.getVal());
            }
            else {
                if (high >= v.getVal()) {
                    allLeafTraversal(v.getLeft(), result);
                    rightTraversal(v.getRight(), high, result);
                }
            }
        }
    }

    public static Object[] search(int[] inputs, int low, int high) {
        BinaryTreeNode tree = OrthogonalRangeSearching.buildTree(inputs, 0, inputs.length - 1);
        BinaryTreeNode splitNode = OrthogonalRangeSearching.findSplit(tree, low, high);
        ArrayList<Integer> result = new ArrayList<>();
        if (splitNode != null) {
            leftTraversal(splitNode.getLeft(), low, result);
            System.out.println(result);
            rightTraversal(splitNode.getRight(), high, result);
            System.out.println(result);
        }
        return result.toArray();
    }

}
