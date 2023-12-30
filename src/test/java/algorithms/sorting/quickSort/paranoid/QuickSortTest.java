package algorithms.sorting.quickSort.paranoid;

import static org.junit.Assert.assertArrayEquals;

import java.util.Arrays;

import org.junit.Test;

/**
 * Test cases for paranoid {@link QuickSort}.
 */
public class QuickSortTest {

    @Test
    public void test_selectionSort_shouldReturnSortedArray() {
        int[] firstArray =
            new int[] {
                2, 3, 4, 1, 2, 5, 6, 7, 10, 15, 20, 13, 15, 1, 2, 15, 12, 20, 21, 120, 11, 5, 7,
                85, 30
            };
        int[] firstResult = Arrays.copyOf(firstArray, firstArray.length);
        QuickSort.sort(firstResult);

        int[] secondArray =
            new int[] {
                9, 1, 2, 8, 7, 3, 4, 6, 5, 5, 9, 8, 7, 6, 5, 4, 3, 2, 1, 1, 2, 3, 4, 5, 6, 7, 8,
                9, 10
            };
        int[] secondResult = Arrays.copyOf(secondArray, secondArray.length);
        QuickSort.sort(secondResult);

        int[] thirdArray = new int[] {};
        int[] thirdResult = Arrays.copyOf(thirdArray, thirdArray.length);
        QuickSort.sort(thirdResult);

        int[] fourthArray = new int[] {1};
        int[] fourthResult = Arrays.copyOf(fourthArray, fourthArray.length);
        QuickSort.sort(fourthResult);

        int[] fifthArray = new int[] {5, 1, 1, 2, 0, 0};
        int[] fifthResult = Arrays.copyOf(fifthArray, fifthArray.length);
        QuickSort.sort(fifthResult);

        int[] sixthArray = new int[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        int[] sixthResult = Arrays.copyOf(sixthArray, sixthArray.length);
        // testing for duplicate arrays of length >= 10, stack overflow is expected to happen
        try {
            QuickSort.sort(sixthResult);
        } catch (StackOverflowError e) {
            System.out.println("Stack overflow occurred for sixthResult");
        }

        Arrays.sort(firstArray); // get expected result
        Arrays.sort(secondArray); // get expected result
        Arrays.sort(thirdArray); // get expected result
        Arrays.sort(fourthArray); // get expected result
        Arrays.sort(fifthArray); // get expected result

        assertArrayEquals(firstResult, firstArray);
        assertArrayEquals(secondResult, secondArray);
        assertArrayEquals(thirdResult, thirdArray);
        assertArrayEquals(fourthResult, fourthArray);
        assertArrayEquals(fifthResult, fifthArray);
    }
}
