package algorithms.binarySearch;

import algorithms.binarySearch.binarySearch.BinarySearch;
import algorithms.binarySearch.binarySearchTemplated.BinarySearchTemplated;
import org.junit.Test;

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

        // Test 3: target not in array but could exist within search space
        int[] thirdArray = {1, 5, 10, 11, 12};
        int thirdResult = BinarySearchTemplated.search(thirdArray, 3);

        // Test 4: target not in array but could exist on the right of search space
        int[] fourthArray = {1, 5, 10, 11, 12};
        int fourthResult = BinarySearchTemplated.search(thirdArray, 13);

        // Test 3: target not in array but could exist on the left of search space
        int[] fifthArray = {1, 5, 10, 11, 12};
        int fifthResult = BinarySearchTemplated.search(thirdArray, 0);

        assertEquals(0, firstResult);
        assertEquals(3, secondResult);
        assertEquals(-1, thirdResult);
        assertEquals(-1, fourthResult);
        assertEquals(-1, fifthResult);
    }
}
