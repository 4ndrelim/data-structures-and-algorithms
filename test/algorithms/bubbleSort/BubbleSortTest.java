package test.algorithms.bubbleSort;

import org.junit.Test;

import src.algorithms.sorting.bubbleSort.BubbleSort;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;

public class BubbleSortTest {
    @Test
    public void test_bubbleSort_shouldReturnSortedArray() {
        int[] firstArray =
                new int[] {2, 3, 4, 1, 2, 5, 6, 7, 10, 15, 20, 13, 15, 1, 2, 15, 12, 20, 21, 120, 11, 5, 7, 85, 30};
        int[] firstResult = BubbleSort.sort(Arrays.copyOf(firstArray, firstArray.length));

        int[] secondArray
                = new int[] {9, 1, 2, 8, 7, 3, 4, 6, 5, 5, 9, 8, 7, 6, 5, 4, 3, 2, 1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] secondResult = BubbleSort.sort(Arrays.copyOf(secondArray, secondArray.length));

        int[] thirdArray = new int[] {};
        int[] thirdResult = BubbleSort.sort(Arrays.copyOf(thirdArray, thirdArray.length));

        int[] fourthArray = new int[] {1};
        int[] fourthResult = BubbleSort.sort(Arrays.copyOf(fourthArray, fourthArray.length));

        Arrays.sort(firstArray);
        Arrays.sort(secondArray);
        Arrays.sort(thirdArray);
        Arrays.sort(fourthArray);

        assertArrayEquals(firstResult, firstArray);
        assertArrayEquals(secondResult, secondArray);
        assertArrayEquals(thirdResult, thirdArray);
        assertArrayEquals(fourthResult, fourthArray);
    }
}
