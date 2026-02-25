package dataStructures.linkedList;

import org.junit.Assert;
import org.junit.Test;

import dataStructures.linkedList.LinkedList.Node;

/**
 * Test cases for {@link LinkedList}.
 */
public class LinkedListTest {
    @Test
    public void testInsert() {
        LinkedList<Integer> ll = new LinkedList<>();
        Assert.assertNull(ll.toString());

        ll.insertFront(2);
        ll.insertEnd(3);
        ll.insertEnd(5);
        ll.insertFront(1);
        Assert.assertEquals("1 2 3 5 ", ll.toString());

        ll.insert(0, 0);
        ll.insert(4, 4);
        ll.insert(7, 6);
        ll.insert(6, 6);
        Assert.assertEquals("0 1 2 3 4 5 6 7 ", ll.toString());
    }

    @Test
    public void testSearchAndGet() {
        LinkedList<Integer> ll = new LinkedList<>();
        ll.insertFront(2);
        ll.insertEnd(3);
        ll.insertEnd(5);
        ll.insertFront(1);
        ll.insert(0, 0);
        ll.insert(4, 4);
        ll.insert(7, 6);
        ll.insert(6, 6);

        Node<Integer> test1 = ll.get(4);
        Assert.assertEquals("4", test1.toString());

        Node<Integer> test2 = ll.get(3);
        Assert.assertEquals("3", test2.toString());

        Integer test3 = ll.search(4);
        Assert.assertEquals("4", test3.toString());

        Integer test4 = ll.search(3);
        Assert.assertEquals("3", test4.toString());

        Assert.assertEquals("0 1 2 3 4 5 6 7 ", ll.toString());
    }

    @Test
    public void testRemove() {

        LinkedList<Integer> ll = new LinkedList<>();
        ll.insertFront(2);
        ll.insertEnd(3);
        ll.insertEnd(5);
        ll.insertFront(1);
        ll.insert(0, 0);
        ll.insert(4, 4);
        ll.insert(7, 6);
        ll.insert(6, 6);

        ll.remove(5);
        ll.delete(6);
        ll.pop();
        ll.poll();

        String expected = "1 2 3 4 ";
        Assert.assertEquals(expected, ll.toString());
    }

    @Test
    public void testReverse() {
        LinkedList<Integer> ll = new LinkedList<>();
        ll.reverse();
        Assert.assertNull(ll.toString());

        ll.insertFront(3);
        ll.reverse();
        Assert.assertEquals("3 ", ll.toString());

        ll.insertFront(2);
        ll.insertFront(1);
        ll.insertEnd(4);
        ll.reverse();
        Assert.assertEquals("4 3 2 1 ", ll.toString());
    }

    @Test
    public void testSort() {
        LinkedList<Integer> ll = new LinkedList<>();
        ll.insertFront(5);
        ll.insertEnd(2);
        ll.insertFront(7);
        ll.insertEnd(3);
        ll.insertEnd(4);
        ll.insertEnd(3);
        ll.sort();

        Assert.assertEquals("2 3 3 4 5 7 ", ll.toString());
        Assert.assertEquals(6, ll.size()); // verify size is correct after sort
    }

    @Test
    public void testEmptyListOperations() {
        LinkedList<Integer> ll = new LinkedList<>();

        // Operations on empty list should not throw exceptions
        Assert.assertNull(ll.pop());
        Assert.assertNull(ll.poll());
        Assert.assertNull(ll.get(0));
        Assert.assertNull(ll.remove(0));
        Assert.assertEquals(-1, ll.search(1));
        Assert.assertFalse(ll.delete(1));
        Assert.assertEquals(0, ll.size());
    }

    @Test
    public void testNegativeIndex() {
        LinkedList<Integer> ll = new LinkedList<>();
        ll.insertEnd(1);
        ll.insertEnd(2);
        ll.insertEnd(3);

        // Negative indices should return null or false, not throw exceptions
        Assert.assertNull(ll.get(-1));
        Assert.assertNull(ll.remove(-1));
        Assert.assertFalse(ll.insert(99, -1));
        Assert.assertEquals(3, ll.size()); // size unchanged
        Assert.assertEquals("1 2 3 ", ll.toString()); // list unchanged
    }

    @Test
    public void testOutOfBoundsIndex() {
        LinkedList<Integer> ll = new LinkedList<>();
        ll.insertEnd(1);
        ll.insertEnd(2);

        Assert.assertNull(ll.get(5));
        Assert.assertNull(ll.remove(5));
        Assert.assertFalse(ll.insert(99, 5));
        Assert.assertEquals(2, ll.size());
    }
}
