package algorithms.sorting.cyclicSort.simple;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

/**
 * Test cases for Simple {@link CyclicSort}.
 */
public class SimpleCyclicSortTest {
  @Test
  public void test_cyclicSort_shouldReturnSortedArray() {
    // unsorted array of 16 integers, from 0 to 15
    int[] firstArray =
        new int[] {2, 5, 6, 4, 8, 15, 3, 1, 7, 12, 14, 0, 9, 13, 11, 10};
    int[] firstExpected =
        new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};

    // already sorted, do nothing
    int[] secondArray =
        new int[] {0, 1, 2, 3, 4, 5, 6};
    int[] secondExpected =
        new int[] {0, 1, 2, 3, 4, 5, 6};

    // empty, do nothing
    int[] thirdArray = new int[] {};
    int[] thirdExpected = new int[] {};

    CyclicSort.sort(firstArray);
    CyclicSort.sort(secondArray);
    CyclicSort.sort(thirdArray);

    assertArrayEquals(firstArray, firstExpected);
    assertArrayEquals(secondArray, secondExpected);
    assertArrayEquals(thirdArray, thirdExpected);
  }
}
