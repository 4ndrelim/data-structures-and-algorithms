package test.algorithms.quickSort.threeWayPartitioning;

import org.junit.Test;

import src.algorithms.sorting.quickSort.threeWayPartitioning.QuickSort;

import java.util.Arrays;

import static org.junit.Assert.*;

public class QuickSortTest {

    @Test
    public void test_selectionSort_shouldReturnSortedArray() {
        int[] firstArray =
                new int[] {2, 3, 4, 1, 2, 5, 6, 7, 10, 15, 20, 13, 15, 1, 2, 15, 12, 20, 21, 120, 11, 5, 7, 85, 30};
        int[] firstResult = Arrays.copyOf(firstArray, firstArray.length);
        QuickSort.sort(firstResult);

        int[] secondArray
                = new int[] {9, 1, 2, 8, 7, 3, 4, 6, 5, 5, 9, 8, 7, 6, 5, 4, 3, 2, 1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] secondResult = Arrays.copyOf(secondArray, secondArray.length);
        QuickSort.sort(secondResult);

        int[] thirdArray = new int[] {};
        int[] thirdResult = Arrays.copyOf(thirdArray, thirdArray.length);
        QuickSort.sort(thirdResult);

        int[] fourthArray = new int[] {1};
        int[] fourthResult = Arrays.copyOf(fourthArray, fourthArray.length);
        QuickSort.sort(fourthResult);

        int[] fifthArray = new int[] {5,1,1,2,0,0};
        int[] fifthResult = Arrays.copyOf(fifthArray, fifthArray.length);
        QuickSort.sort(fifthResult);

        int[] sixthArray = new int[] {1,1,1,1,1,1,1,1,1,1,1,1,1,1};
        int[] sixthResult = Arrays.copyOf(sixthArray, sixthArray.length);
        QuickSort.sort(sixthResult);

        Arrays.sort(firstArray);  // get expected result
        Arrays.sort(secondArray); // get expected result
        Arrays.sort(thirdArray);  // get expected result
        Arrays.sort(fourthArray);  // get expected result
        Arrays.sort(fifthArray);  // get expected result
        Arrays.sort(sixthArray);  // get expected result

        assertArrayEquals(firstResult, firstArray);
        assertArrayEquals(secondResult, secondArray);
        assertArrayEquals(thirdResult, thirdArray);
        assertArrayEquals(fourthResult, fourthArray);
        assertArrayEquals(fifthResult, fifthArray);
        assertArrayEquals(sixthResult, sixthArray);
    }

}