package algorithms.orthogonalRangeSearching.oneDim;

import algorithms.orthogonalRangeSearching.RangeTreeNode;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class OrthogonalRangeSearchingTest {
  @Test
  public void test_OrthogonalRangeSearching() {

    int[] firstInput = new int[] {7, 12, 25, 26, 40, 45};
    Object[] firstExpected = new Object[] {12, 25, 26, 40, 45};
    RangeTreeNode firstTree = OrthogonalRangeSearching.buildTree(firstInput, 0, firstInput.length - 1);
    Object[] firstResult = OrthogonalRangeSearching.search(firstTree, 10, 50);
    assertArrayEquals(firstExpected, firstResult);

    int[] secondInput = new int[] {-26, -7, -10, -40, -43};
    Object[] secondExpected = new Object[] {-26, -10, -7};
    RangeTreeNode secondTree = OrthogonalRangeSearching.buildTree(secondInput, 0, secondInput.length - 1);
    Object[] secondResult = OrthogonalRangeSearching.search(secondTree, -30, -2);
    assertArrayEquals(secondExpected, secondResult);

    int[] thirdInput = new int[] {26, 7, 12, 40, 45};
    Object[] thirdExpected = new Object[] {12, 26};
    RangeTreeNode thirdTree = OrthogonalRangeSearching.buildTree(thirdInput, 0, thirdInput.length - 1);
    Object[] thirdResult = OrthogonalRangeSearching.search(thirdTree, 10, 35);
    assertArrayEquals(thirdExpected, thirdResult);

    // for fourth input
    // static queries
    int[] fourthInput = new int[] {3, 19, 30, 49, 59, 70, 89, 100};
    Object[] fourthExpected = new Object[] {30, 49, 59, 70};
    RangeTreeNode fourthTree = OrthogonalRangeSearching.buildTree(fourthInput, 0, fourthInput.length - 1);
    Object[] fourthResult = OrthogonalRangeSearching.search(fourthTree, 20, 71);
    assertArrayEquals(fourthExpected, fourthResult);

    Object[] fifthExpected = new Object[] {49, 59, 70, 89, 100};
    Object[] fifthResult = OrthogonalRangeSearching.search(fourthTree, 31, 130);
    assertArrayEquals(fifthExpected, fifthResult);

    // dynamic updates then query

    OrthogonalRangeSearching.configureTree(fourthTree);
    fourthTree = OrthogonalRangeSearching.insert(fourthTree, 101);
    Object[] sixthExpected = new Object[] {49, 59, 70, 89, 100, 101};
    Object[] sixthResult = OrthogonalRangeSearching.search(fourthTree, 31, 130);
    assertArrayEquals(sixthExpected, sixthResult);

    fourthTree = OrthogonalRangeSearching.insert(fourthTree, 46);
    fourthTree = OrthogonalRangeSearching.insert(fourthTree, 32);
    Object[] seventhExpected = new Object[] {30, 32, 46, 49, 59, 70};
    Object[] seventhResult = OrthogonalRangeSearching.search(fourthTree, 20, 71);
    assertArrayEquals(seventhExpected, seventhResult);

    fourthTree = OrthogonalRangeSearching.delete(fourthTree, 32);
    fourthTree = OrthogonalRangeSearching.delete(fourthTree, 59);
    Object[] eighthExpected = new Object[] {30, 46, 49, 70};
    Object[] eighthResult = OrthogonalRangeSearching.search(fourthTree, 20, 72);
    assertArrayEquals(eighthExpected, eighthResult);
    }
}
