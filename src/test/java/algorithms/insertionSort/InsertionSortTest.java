package algorithms.insertionSort;

import org.junit.Test;

import algorithms.sorting.insertionSort.InsertionSort;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;

public class InsertionSortTest {

    @Test
    public void test_insertionSort_shouldReturnSortedArray() {
        int[] firstArray =
                new int[] {2, 3, 4, 1, 2, 5, 6, 7, 10, 15, 20, 13, 15, 1, 2, 15, 12, 20, 21, 120, 11, 5, 7, 85, 30};
        int[] firstResult = InsertionSort.sort(Arrays.copyOf(firstArray, firstArray.length));

        int[] secondArray
                = new int[] {9, 1, 2, 8, 7, 3, 4, 6, 5, 5, 9, 8, 7, 6, 5, 4, 3, 2, 1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] secondResult = InsertionSort.sort(Arrays.copyOf(secondArray, secondArray.length));

        int[] thirdArray = new int[] {};
        int[] thirdResult = InsertionSort.sort(Arrays.copyOf(thirdArray, thirdArray.length));

        int[] fourthArray = new int[] {1};
        int[] fourthResult = InsertionSort.sort(Arrays.copyOf(fourthArray, fourthArray.length));

        int[] fifthArray = new int[] {5,1,1,2,0,0};
        int[] fifthResult = InsertionSort.sort(Arrays.copyOf(fifthArray, fifthArray.length));

        Arrays.sort(firstArray);  // get expected result
        Arrays.sort(secondArray); // get expected result
        Arrays.sort(thirdArray);  // get expected result
        Arrays.sort(fourthArray);  // get expected result
        Arrays.sort(fifthArray);  // get expected result

        assertArrayEquals(firstResult, firstArray);
        assertArrayEquals(secondResult, secondArray);
        assertArrayEquals(thirdResult, thirdArray);
        assertArrayEquals(fourthResult, fourthArray);
        assertArrayEquals(fifthResult, fifthArray);
    }
}
