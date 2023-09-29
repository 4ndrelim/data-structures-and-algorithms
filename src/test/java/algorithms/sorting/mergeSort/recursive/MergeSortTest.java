package algorithms.sorting.mergeSort.recursive;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;

public class MergeSortTest {

    @Test
    public void test_insertionSort_shouldReturnSortedArray() {
        int[] firstArray =
                new int[] {2, 3, 4, 1, 2, 5, 6, 7, 10, 15, 20, 13, 15, 1, 2, 15, 12, 20, 21, 120, 11, 5, 7, 85, 30};
        int[] firstResult = Arrays.copyOf(firstArray, firstArray.length);
        MergeSort.sort(firstResult);

        int[] secondArray
                = new int[] {9, 1, 2, 8, 7, 3, 4, 6, 5, 5, 9, 8, 7, 6, 5, 4, 3, 2, 1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] secondResult = Arrays.copyOf(secondArray, secondArray.length);
        MergeSort.sort(secondResult);

        int[] thirdArray = new int[] {};
        int[] thirdResult = Arrays.copyOf(thirdArray, thirdArray.length);
        MergeSort.sort(thirdResult);

        int[] fourthArray = new int[] {1};
        int[] fourthResult = Arrays.copyOf(fourthArray, fourthArray.length);
        MergeSort.sort(fourthResult);

        int[] fifthArray = new int[] {5, 1, 1, 2, 0, 0};
        int[] fifthResult = Arrays.copyOf(fifthArray, fifthArray.length);
        MergeSort.sort(fifthResult);

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
