package dataStructures.heap;

import org.junit.Assert;

import java.util.Arrays;

import org.junit.Test;

/**
 * We will illustrate with the following elements:
 *
 *                              13                                 13
 *                           /      \         INSERT 10         /      \       REMOVE 10
 *                         9        11       ----------->     10       11     ----------->    ORIGINAL
 *                       /   \     /  \                     /   \     /  \
 *                      4    7    6   3                    4     9   6    3
 *                    /  \                               /  \   /
 *                   2    1                             2    1 7
 *
 *
 *                              13                                 11
 *                           /      \         DEC 13 to 5       /      \       INC 5 to 13
 *                         9        11       ----------->     9         6     ------------>   ORIGINAL
 *                       /   \     /  \                     /   \     /  \
 *                      4    7    6   3                    4     7   5    3
 *                    /  \                               /  \
 *                   2    1                             2    1
 *
 */
public class MaxHeapTest {
    @Test
    public void test_offer_shouldConstructArray() {
        MaxHeap<Integer> heap = new MaxHeap<>();
        heap.offer(13);
        heap.offer(9);
        heap.offer(11);
        heap.offer(4);
        heap.offer(7);
        heap.offer(6);
        heap.offer(3);
        heap.offer(2);
        heap.offer(1);

        String expected = "[13, 9, 11, 4, 7, 6, 3, 2, 1]";
        Assert.assertEquals(expected, heap.toString());

        // insert 10
        heap.offer(10);
        expected = "[13, 10, 11, 4, 9, 6, 3, 2, 1, 7]";
        Assert.assertEquals(expected, heap.toString());
    }

    @Test
    public void test_bothHeapifyMethods_shouldConstructArray() {
        MaxHeap<Integer> heap = new MaxHeap<>();
        // 1st heapify, takes in a list of values
        heap.heapify(Arrays.asList(1, 9, 11, 4, 7, 6, 3, 2, 13));
        String first_heap = heap.toString();

        // 2nd heapify, take sin sequence of values
        heap.heapify(2, 9, 11, 4, 7, 6, 3, 13, 1);
        String second_heap = heap.toString();

        Assert.assertEquals(first_heap, second_heap); // should be the same
        String expected = "[13, 9, 11, 4, 7, 6, 3, 2, 1]";
        Assert.assertEquals(expected, first_heap);
    }

    @Test
    public void test_size_shouldReturnSizeOfHeap() {
        MaxHeap<Integer> heap = new MaxHeap<>();
        heap.heapify(2, 9, 11, 4, 7, 6, 3, 13, 1);

        Assert.assertEquals(9, heap.size());
    }

    @Test
    public void test_peek_shouldReturnHighestKey() {
        MaxHeap<Integer> heap = new MaxHeap<>();
        heap.heapify(2, 9, 11, 4, 7, 6, 3, 13, 1);

        Assert.assertEquals((Integer) 13, (Integer) heap.peek()); // no method definition for comparing primitive int
    }

    @Test
    public void test_poll_shouldRemoveAndReturnHighestKey() {
        MaxHeap<Integer> heap = new MaxHeap<>();
        heap.heapify(2, 9, 11, 4, 7, 6, 3, 13, 1);
        Integer removed = (Integer) heap.poll();

        Assert.assertEquals((Integer) 13, removed);
        Assert.assertEquals("[11, 9, 6, 4, 7, 1, 3, 2]", heap.toString());
    }

    @Test
    public void test_remove_shouldRemoveSpecifiedKey() {
        // As illustrated in the example above, we will insert and remove 10
        MaxHeap<Integer> heap = new MaxHeap<>();
        heap.heapify(2, 9, 11, 4, 7, 6, 3, 13, 1);
        heap.offer(10);
        Assert.assertEquals("[13, 10, 11, 4, 9, 6, 3, 2, 1, 7]", heap.toString()); // check added

        heap.remove(10);
        // Note that element 10 was specifically chosen to simulate the example shown (bubble up/down operations)
        Assert.assertEquals("[13, 9, 11, 4, 7, 6, 3, 2, 1]", heap.toString());
    }

    @Test
    public void test_decreaseKey_increaseKey_shouldUpdateHeapToMaintainProperty() {
        // As illustrated in the example above, we will insert and remove 10
        MaxHeap<Integer> heap = new MaxHeap<>();
        heap.heapify(2, 9, 11, 4, 7, 6, 3, 13, 1);
        heap.decreaseKey(13, 5);
        Assert.assertEquals("[11, 9, 6, 4, 7, 5, 3, 2, 1]", heap.toString()); // check updated

        heap.increaseKey(5, 13);
        Assert.assertEquals("[13, 9, 11, 4, 7, 6, 3, 2, 1]", heap.toString());
    }
}
