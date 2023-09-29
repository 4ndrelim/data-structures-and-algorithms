package algorithms.orthogonalRangeSearching.oneDim;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class OrthogonalRangeSearchingTest {

    @Test
    public void test_OrthogonalRangeSearching() {

        int[] firstInput = new int[] {3, 19, 30, 49, 59, 70, 89, 100};
        Object[] firstExpected = new Object[] {30, 49, 59, 70};
        Object[] firstResult = OrthogonalRangeSearching.search(firstInput, 20, 71);
        assertArrayEquals(firstExpected, firstResult);

        int[] secondInput = new int[] {7, 12, 25, 26, 40, 45};
        Object[] secondExpected = new Object[] {12, 25, 26, 40, 45};
        Object[] secondResult = OrthogonalRangeSearching.search(secondInput, 10, 50);
        assertArrayEquals(secondExpected, secondResult);

        int[] thirdInput = new int[] {26, 7, 12, 40, 45};
        Object[] thirdExpected = new Object[] {12, 26};
        Object[] thirdResult = OrthogonalRangeSearching.search(thirdInput, 10, 35);
        assertArrayEquals(thirdExpected, thirdResult);

        int[] fourthInput = new int[] {-26, -7, -10, -40, -43};
        Object[] fourthExpected = new Object[] {-26, -10, -7};
        Object[] fourthResult = OrthogonalRangeSearching.search(fourthInput, -30, -2);
        assertArrayEquals(fourthExpected, fourthResult);

        int[] fifthInput = new int[] {25, 12, 26, 7, 45, 40};
        Object[] fifthExpected = new Object[] {};
        Object[] fifthResult = OrthogonalRangeSearching.search(fifthInput, 0, 5);
        assertArrayEquals(fifthExpected, fifthResult);

    }
}
