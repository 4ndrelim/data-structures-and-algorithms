package algorithms.sorting.cyclicSort.generalised;

import static org.junit.Assert.assertArrayEquals;

import java.util.Arrays;

import org.junit.Test;

/**
 * Test cases for generalized {@link CyclicSort}.
 */
public class CyclicSortTest {
  @Test
  public void cyclicSort_emptyArray_shouldReturnEmptyArray() {
    int[] emptyArray = new int[10];
    int[] expected = new int[10];
    CyclicSort.sort(emptyArray);
    assertArrayEquals(emptyArray, expected);
  }

  @Test
  public void cyclicSort_positiveInputs_shouldReturnSortedArray() {
    int[] array =
        new int[] {2, 3, 4, 1, 2, 5, 6, 7, 10, 15, 20, 13, 15, 1, 2, 15, 12, 20, 21, 120, 11, 5, 7,
            85, 30};
    CyclicSort.sort(array);

    int[] expected = Arrays.copyOf(array, array.length);
    Arrays.sort(expected);

    assertArrayEquals(array, expected);
  }

  @Test
  public void cyclicSort_someNegativeInputs_shouldReturnSortedArray() {
    int[] array =
        new int[] {2, 3, 4, 1, 2, -5, 6, 7, 10, -15, 20, 13, 15, 1, 2, 15, -12, 20, 21, 120, 11, 5,
            7, -85, 30};
    CyclicSort.sort(array);

    int[] expected = Arrays.copyOf(array, array.length);
    Arrays.sort(expected);

    assertArrayEquals(array, expected);
  }

  @Test
  public void cyclicSort_alreadySorted_shouldReturnSortedArray() {
    int[] array =
        new int[] {1, 2, 3, 4, 5, 10, 11, 12, 13, 14, 15};
    CyclicSort.sort(array);

    int[] expected = Arrays.copyOf(array, array.length);
    Arrays.sort(expected);

    assertArrayEquals(array, expected);
  }
}
