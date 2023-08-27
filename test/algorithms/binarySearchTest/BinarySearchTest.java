package test.algorithms.binarySearchTest;

import org.junit.Test;

import src.algorithms.searches.binarySearch.BinarySearchTemplated;
import src.algorithms.searches.binarySearch.BinarySearch;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class BinarySearchTest {
    @Test
    public void test_binarySearch() {
        // Test 1: even number of elements
        int[] firstArray = {1, 5, 10, 12};
        int firstResult = BinarySearch.search(firstArray, 1);

        // Test 2: odd number of elements
        int[] secondArray = {1, 5, 10, 11, 12};
        int secondResult = BinarySearch.search(secondArray, 11);

        // Test 3: target not in array
        int[] thirdArray = {1, 5, 10, 11, 12};
        int thirdResult = BinarySearch.search(thirdArray, 3);

        assertEquals(0, firstResult);
        assertEquals(3, secondResult);
        assertEquals(-1, thirdResult);
    }

    @Test
    public void test_binarySearchTemplated() {
        // Test 1: even number of elements
        int[] firstArray = {1, 5, 10, 12};
        int firstResult = BinarySearchTemplated.search(firstArray, 1);

        // Test 2: odd number of elements
        int[] secondArray = {1, 5, 10, 11, 12};
        int secondResult = BinarySearchTemplated.search(secondArray, 11);

        // Test 3: target not in array
        int[] thirdArray = {1, 5, 10, 11, 12};
        int thirdResult = BinarySearchTemplated.search(thirdArray, 3);

        assertEquals(0, firstResult);
        assertEquals(3, secondResult);
        assertEquals(-1, thirdResult);
    }
}
