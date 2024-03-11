package algorithms.orthogonalRangeSearching.twoDim;

import algorithms.orthogonalRangeSearching.RangeTreeNode;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class OrthogonalRangeSearching {

    /**
     * Builds the X-tree, a data structure for the given range of 2D points.
     *
     * @param inputs List of 2D points in the format Integer[], where each array
     *               represents a point with two coordinates (x, y).
     * @param start  The starting index of the current range.
     * @param end    The ending index of the current range.
     * @return The root node of the X-tree for the specified range.
     */
    public static RangeTreeNode<Integer[]> buildXTree(List<Integer[]> inputs, int start, int end) {

        int mid = (end + start) / 2;
        inputs.sort(Comparator.comparingInt(a -> a[0]));

        if (start > end) {
            return null;
        } else if (start == end) {
            RangeTreeNode<Integer[]> node = new RangeTreeNode<>(inputs.get(mid));
            node.setYTree(buildYTree(inputs, start, end));
            return node;
        } else {
            RangeTreeNode<Integer[]> node = new RangeTreeNode<>(inputs.get(mid), buildXTree(inputs, start, mid),
                    buildXTree(inputs, mid + 1, end));
            node.setYTree(buildYTree(inputs, start, end));
            return node;
        }
    }

    /**
     * Builds the Y-tree, a data structure for the given range of 2D points.
     *
     * @param inputs List of 2D points in the format Integer[], where each array
     *               represents a point with two coordinates (x, y).
     * @param start  The starting index of the current range.
     * @param end    The ending index of the current range.
     * @return The root node of the Y-tree for the specified range.
     */
    private static RangeTreeNode<Integer[]> buildYTree(List<Integer[]> inputs, int start, int end) {

        List<Integer[]> ySortedSublist = inputs.subList(start, end + 1);
        ySortedSublist.sort(Comparator.comparingInt(a -> a[1])); //sort by y-coordinate

        return buildYTreeHelper(ySortedSublist, 0, ySortedSublist.size() - 1);
    }

    /**
     * Helper function to build the Y-tree.
     *
     * @param inputs List of 2D points in the format Integer[], where each array
     *               represents a point with two coordinates (x, y).
     * @param start  The starting index of the current range.
     * @param end    The ending index of the current range.
     * @return The root node of the Y-tree for the specified range.
     */
    private static RangeTreeNode<Integer[]> buildYTreeHelper(List<Integer[]> inputs, int start, int end) {

        int mid = (end + start) / 2;

        if (start > end) {
            return null;
        } else if (start == end) {
            return new RangeTreeNode<>(inputs.get(start));
        } else {
            return new RangeTreeNode<>(inputs.get(mid), buildYTree(inputs, start, mid),
                    buildYTree(inputs, mid + 1, end));
        }
    }

    /**
     * Finds the X-split node in the X-tree based on the specified X-coordinate range.
     *
     * @param root  The root node of the X-tree.
     * @param xLow   The lower bound of the X-coordinate range.
     * @param xHigh  The upper bound of the X-coordinate range.
     * @return The X-split node or null if not found.
     */
    public static RangeTreeNode<Integer[]> findXSplit(RangeTreeNode<Integer[]> root, int xLow, int xHigh) {
        RangeTreeNode<Integer[]> v = root;

        while (true) {
            if (v == null) {
                return null;
            } else {
                if (xHigh <= v.getVal()[0]) {
                    v = v.getLeft();
                } else if (xLow > v.getVal()[0]) {
                    v = v.getRight();
                } else {
                    break;
                }
            }
        }
        return v;
    }
    /**
     * Performs a left traversal of the X-tree to find points within the specified range.
     *
     * @param v     The current node in the X-tree.
     * @param xLow  The lower bound of the X-coordinate range.
     * @param xHigh The upper bound of the X-coordinate range.
     * @param yLow  The lower bound of the Y-coordinate range.
     * @param yHigh The upper bound of the Y-coordinate range.
     * @param result A list to store the results.
     */
    public static void XLeftTraversal(RangeTreeNode<Integer[]> v, int xLow, int xHigh, int yLow, int yHigh,
                                      ArrayList<Integer[]> result) {
        if (v != null) {
            if (isLeaf(v) && v.getVal()[0] >= xLow && v.getVal()[0] <= xHigh) { //leaf
                YSearch(v, yLow, yHigh, result);
            } else {
                if (xLow <= v.getVal()[0]) {
                    XLeftTraversal(v.getLeft(), xLow, xHigh, yLow, yHigh, result);
                    YSearch(v.getRight(), yLow, yHigh, result);
                } else {
                    XLeftTraversal(v.getRight(), xLow, xHigh, yLow, yHigh, result);
                }
            }
        }
    }
    /**
     * Performs a right traversal of the X-tree to find points within the specified range.
     *
     * @param v     The current node in the X-tree.
     * @param xLow  The lower bound of the X-coordinate range.
     * @param xHigh The upper bound of the X-coordinate range.
     * @param yLow  The lower bound of the Y-coordinate range.
     * @param yHigh The upper bound of the Y-coordinate range.
     * @param result A list to store the results.
     */
    public static void XRightTraversal(RangeTreeNode<Integer[]> v, int xLow, int xHigh, int yLow, int yHigh,
                                       ArrayList<Integer[]> result) {
        if (v != null) {
            if (isLeaf(v) && v.getVal()[0] >= xLow && v.getVal()[0] <= xHigh) { //leaf
                YSearch(v, yLow, yHigh, result);
            } else {
                if (xHigh >= v.getVal()[0]) {
                    YSearch(v.getLeft(), yLow, yHigh, result);
                    XRightTraversal(v.getRight(), xLow, xHigh, yLow, yHigh, result);
                } else {
                    XRightTraversal(v.getLeft(), xLow, xHigh, yLow, yHigh, result);
                }
            }
        }
    }
    /**
     * Finds the Y-split node in the X-tree based on the specified X-coordinate range.
     *
     * @param root  The root node of the X-tree.
     * @param yLow   The lower bound of the X-coordinate range.
     * @param yHigh  The upper bound of the X-coordinate range.
     * @return The X-split node or null if not found.
     */
    public static RangeTreeNode<Integer[]> findYSplit(RangeTreeNode<Integer[]> root, int yLow, int yHigh) {
        RangeTreeNode<Integer[]> v = root;

        while (true) {
            if (v == null) {
                return null;
            } else {
                if (yHigh <= v.getVal()[1]) {
                    if (isLeaf(v)) { // extra check since ysplit might be leaf node
                        break;
                    } else {
                        v = v.getLeft();
                    }
                } else if (yLow > v.getVal()[1]) {
                    v = v.getRight();
                } else {
                    break;
                }
            }
        }
        return v;
    }

    /**
     * Performs a recursive traversal of the Range Tree and adds leaf node values to the result list.
     *
     * @param v      The current node being processed during traversal.
     * @param result The list to store the values of leaf nodes encountered during traversal.
     */
    public static void allLeafTraversal(RangeTreeNode<Integer[]> v, List<Integer[]> result) {
        if (v != null) {
            if (v.getLeft() != null) {
                allLeafTraversal(v.getLeft(), result);
            }
            if (isLeaf(v)) {
                result.add(v.getVal());
            }
            if (v.getRight() != null) {
                allLeafTraversal(v.getRight(), result);
            }
        }
    }

    /**
     * Performs a left traversal of the Y-tree to find points within the specified range.
     *
     * @param v      The current node in the Y-tree.
     * @param low    The lower bound of the Y-coordinate range.
     * @param result A list to store the results.
     */
    public static void YLeftTraversal(RangeTreeNode<Integer[]> v, int low, List<Integer[]> result) {
        if (v != null) {
            if (isLeaf(v)) {
                result.add(v.getVal());
            } else {
                if (low <= v.getVal()[1]) {
                    YLeftTraversal(v.getLeft(), low, result);
                    allLeafTraversal(v.getRight(), result);
                } else { // definitely a qualifying leaf has to exist
                    YLeftTraversal(v.getRight(), low, result);
                }
            }
        }
    }

    /**
     * Performs a right traversal of the Y-tree to find points within the specified range.
     *
     * @param v      The current node in the Y-tree.
     * @param high    The upper bound of the Y-coordinate range.
     * @param result A list to store the results.
     */
    public static void YRightTraversal(RangeTreeNode<Integer[]> v, int high, List<Integer[]> result) {
        if (v != null) {
            if (isLeaf(v) && v.getVal()[1] <= high) { // leaf, need extra check
                result.add(v.getVal());
            } else {
                if (high > v.getVal()[1]) {
                    allLeafTraversal(v.getLeft(), result);
                    YRightTraversal(v.getRight(), high, result);
                } else { // a qualifying leaf might or might not exist, we are just exploring
                    YRightTraversal(v.getLeft(), high, result);
                }
            }
        }
    }

    /**
     * Searches for 2D points within the specified Y-coordinate range in the Y-tree.
     *
     * @param v      The root node of the Y-tree.
     * @param yLow   The lower bound of the Y-coordinate range.
     * @param yHigh  The upper bound of the Y-coordinate range.
     * @param result A list to store the results.
     */
    public static void YSearch(RangeTreeNode<Integer[]> v, int yLow, int yHigh, ArrayList<Integer[]> result) {
        if (v != null) {
            RangeTreeNode<Integer[]> splitNodeY = findYSplit(v.getYTree(), yLow, yHigh);
            if (splitNodeY != null) {
                if (isLeaf(splitNodeY)
                        && splitNodeY.getVal()[1] >= yLow && splitNodeY.getVal()[1] <= yHigh) { // if split node is leaf
                    result.add(splitNodeY.getVal());
                }
                YLeftTraversal(splitNodeY.getLeft(), yLow, result);
                YRightTraversal(splitNodeY.getRight(), yHigh, result);
            }
        }
    }

    /**
     * Searches for 2D points within the specified orthogonal range in the X-tree.
     *
     * @param tree   The root node of the X-tree.
     * @param xLow   The lower bound of the X-coordinate range.
     * @param xHigh  The upper bound of the X-coordinate range.
     * @param yLow   The lower bound of the Y-coordinate range.
     * @param yHigh  The upper bound of the Y-coordinate range.
     * @return A list of 2D points within the specified orthogonal range.
     */
    public static List<Integer[]> search(RangeTreeNode<Integer[]> tree, int xLow, int xHigh, int yLow, int yHigh) {
        RangeTreeNode<Integer[]> splitNodeX = findXSplit(tree, xLow, xHigh);
        ArrayList<Integer[]> result = new ArrayList<>();
        if (splitNodeX != null) {
            if (isLeaf(splitNodeX)
                    && splitNodeX.getVal()[0] >= xLow && splitNodeX.getVal()[0] <= xHigh) { // if split node is leaf
                YSearch(splitNodeX, yLow, yHigh, result);
            }
            XLeftTraversal(splitNodeX.getLeft(), xLow, xHigh, yLow, yHigh, result);
            XRightTraversal(splitNodeX.getRight(), xLow, xHigh, yLow, yHigh, result);
        }
        return result;
    }

    private static <T> boolean isLeaf(RangeTreeNode<T> node) {
        return node.getLeft() == null && node.getRight() == null;
    }

}
