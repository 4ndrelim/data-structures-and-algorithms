package algorithms.orthogonalRangeSearching.twoDim;

import static org.junit.Assert.assertArrayEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import algorithms.orthogonalRangeSearching.RangeTreeNode;

public class OrthogonalRangeSearchingTest {
    @Test
    public void test_orthogonalRangeSearching() {

        ArrayList<Integer[]> firstInput = new ArrayList<>();
        firstInput.add(new Integer[]{4, 5});
        firstInput.add(new Integer[]{3, 3});
        firstInput.add(new Integer[]{2, 4});
        firstInput.add(new Integer[]{1, 2});
        firstInput.add(new Integer[]{5, 2});
        firstInput.add(new Integer[]{6, 3});
        firstInput.add(new Integer[]{7, 1});
        firstInput.add(new Integer[]{8, 4});

        ArrayList<Integer[]> firstExpected = new ArrayList<>();
        firstExpected.add(new Integer[]{1, 2});
        firstExpected.add(new Integer[]{3, 3});
        firstExpected.add(new Integer[]{5, 2});
        firstExpected.add(new Integer[]{6, 3});

        RangeTreeNode<Integer[]> firstTree = OrthogonalRangeSearching.buildXTree(firstInput,
                0, firstInput.size() - 1);
        List<Integer[]> firstResult = OrthogonalRangeSearching.search(firstTree, 1, 6, 1, 3);
        assertArrayEquals(firstExpected.toArray(), firstResult.toArray());

        ArrayList<Integer[]> secondExpected = new ArrayList<>();
        secondExpected.add(new Integer[]{6, 3});
        secondExpected.add(new Integer[]{7, 1});
        secondExpected.add(new Integer[]{8, 4});
        List<Integer[]> secondResult = OrthogonalRangeSearching.search(firstTree, 6, 9, 0, 4);
        assertArrayEquals(secondExpected.toArray(), secondResult.toArray());

        ArrayList<Integer[]> thirdExpected = new ArrayList<>();
        List<Integer[]> thirdResult = OrthogonalRangeSearching.search(firstTree, 6, 9, 2, 2);
        assertArrayEquals(thirdExpected.toArray(), thirdResult.toArray());
    }
}
