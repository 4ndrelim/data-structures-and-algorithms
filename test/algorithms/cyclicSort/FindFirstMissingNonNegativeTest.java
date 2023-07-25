package test.algorithms.cyclicSort;

import org.junit.Test;
import src.algorithms.sorting.cyclicSort.simple.FindFirstMissingNonNegative;

import static org.junit.Assert.assertEquals;

public class FindFirstMissingNonNegativeTest {
    @Test
    public void findMissing_randomArray_shouldReturnFirstMissing() {
        int[] arrayMissing3 = {0, 7, 1, 2, 4, 5, 6, 8}; // 3
        assertEquals(FindFirstMissingNonNegative.findMissing(arrayMissing3), 3);
    }

    @Test
    public void findMissing_duplicatesPresent_shouldReturnFirstMissing() {
        int[] arrayMissing5 = {1, 3, 2, 0, 2, 7, 4}; // 5
        assertEquals(FindFirstMissingNonNegative.findMissing(arrayMissing5), 5);
    }

    @Test
    public void findMissing_firstNPresent_shouldReturnFirstMissing() {
        int[] arrayMissing7 = {0, 1, 2, 3, 4, 5, 6}; // 7
        assertEquals(FindFirstMissingNonNegative.findMissing(arrayMissing7), 7);
    }

    @Test
    public void findMissing_allNegatives_shouldReturnZero() {
        int[] arrayAllNegatives = {-5, -10, -25, -500, -1}; // 0
        assertEquals(FindFirstMissingNonNegative.findMissing(arrayAllNegatives), 0);
    }

    @Test
    public void findMissing_allPositives_shouldReturnZero() {
        int[] arrayAllPositives = {5, 10, 25, 500, 1}; // 0
        assertEquals(FindFirstMissingNonNegative.findMissing(arrayAllPositives), 0);
    }

    @Test
    public void findMissing_empty_shouldReturnZero() {
        int[] empty = {};
        assertEquals(FindFirstMissingNonNegative.findMissing(empty), 0);
    }
}
