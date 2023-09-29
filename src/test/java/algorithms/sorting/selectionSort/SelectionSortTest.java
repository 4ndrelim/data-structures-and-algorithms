package algorithms.sorting.selectionSort;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;

public class SelectionSortTest {

  @Test
  public void test_selectionSort_shouldReturnSortedArray() {
    final int[] firstArray = new int[] {2, 3, 4, 1, 2, 5, 6, 7, 10, 15, 20, 13, 15, 1, 2, 15, 12, 20, 21, 120, 11, 5, 7, 85, 30};
    final int[] firstResult = SelectionSort.sort(Arrays.copyOf(firstArray, firstArray.length));
    
    final int[] secondArray = new int[] {9, 1, 2, 8, 7, 3, 4, 6, 5, 5, 9, 8, 7, 6, 5, 4, 3, 2, 1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    final int[] secondResult = SelectionSort.sort(Arrays.copyOf(secondArray, secondArray.length));

    final int[] thirdArray = new int[] {};
    final int[] thirdResult = SelectionSort.sort(Arrays.copyOf(thirdArray, thirdArray.length));

    final int[] fourthArray = new int[] {1};
    final int[] fourthResult = SelectionSort.sort(Arrays.copyOf(fourthArray, fourthArray.length));

    final int[] fifthArray = new int[] {5, 1, 1, 2, 0, 0};
    final int[] fifthResult = SelectionSort.sort(Arrays.copyOf(fifthArray, fifthArray.length));

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
