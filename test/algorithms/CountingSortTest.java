package test.algorithms;

import src.algorithms.countingSort.CountingSort;

import java.util.Arrays;

public class CountingSortTest {

    @Test
    public void test_countingSort_shouldReturnSortedArray() {
        int[] firstArray =
                new int[] {2, 3, 4, 1, 2, 5, 6, 7, 10, 15, 20, 13, 15, 1, 2, 15, 12, 20, 21, 120, 11, 5, 7, 85, 30};
        int[] firstResult = CountingSort.sort(firstArray);

        int[] secondArray
                = new int[] {9, 1, 2, 8, 7, 3, 4, 6, 5, 5, 9, 8, 7, 6, 5, 4, 3, 2, 1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] secondResult = CountingSort.sort(secondArray);

        int[] thirdArray = new int[] {};
        int[] thirdResult = CountingSort.sort(thirdArray);

        Arrays.sort(firstArray);
        Arrays.sort(secondArray);
        Arrays.sort(thirdArray);

        assertEquals(firstArray, firstResult);
        assertEquals(secondArray, secondResult);
        assertEquals(thirdArray, thirdResult);
    }
}
