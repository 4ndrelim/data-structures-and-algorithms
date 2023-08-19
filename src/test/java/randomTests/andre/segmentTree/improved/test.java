package test.randomTests.andre.segmentTree.improved;

import src.dataStructures.segmentTree.improvedSegmentTree.ImprovedSegmentTree;

/**
 * Basic testing
 */

public class test {
    public static void main(String[] args) {
        int[] t = new int[] {1,2,3,4,5,6,7};
        ImprovedSegmentTree tree = new ImprovedSegmentTree(t);
        System.out.println(tree.sumRange(2, 5));
        tree.update(3, 10);
        System.out.println(tree.sumRange(2, 5));
    }
 }