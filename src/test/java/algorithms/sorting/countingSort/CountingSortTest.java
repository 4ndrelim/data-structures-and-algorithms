package algorithms.sorting.countingSort;

import static org.junit.Assert.assertArrayEquals;

import java.util.Arrays;

import org.junit.Test;

/**
 * Test cases for {@link CountingSort}.
 */
public class CountingSortTest {

  @Test
  public void countingSort_emptyArray_shouldReturnEmptyArray() {
    int[] emptyArray = new int[10];
    int[] result = CountingSort.sort(emptyArray);
    assertArrayEquals(emptyArray, result);
  }

  @Test
  public void test_countingSort_shouldReturnSortedArray() {
    int[] firstArray =
        new int[] {2, 3, 4, 1, 2, 5, 6, 7, 10, 15, 20, 13, 15, 1, 2, 15, 12, 20, 21, 120, 11, 5, 7,
            85, 30};
    int[] firstResult = CountingSort.sort(firstArray);

    int[] secondArray =
        new int[] {9, 1, 2, 8, 7, 3, 4, 6, 5, 5, 9, 8, 7, 6, 5, 4, 3, 2, 1, 1, 2, 3, 4, 5, 6, 7, 8,
            9, 10};
    int[] secondResult = CountingSort.sort(secondArray);

    int[] thirdArray = new int[] {};
    int[] thirdResult = CountingSort.sort(thirdArray);

    Arrays.sort(firstArray);  // get expected result
    Arrays.sort(secondArray); // get expected result
    Arrays.sort(thirdArray);  // get expected result

    assertArrayEquals(firstResult, firstArray);
    assertArrayEquals(secondResult, secondArray);
    assertArrayEquals(thirdResult, thirdArray);
  }
}
