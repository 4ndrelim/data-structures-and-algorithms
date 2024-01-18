package dataStructures.queue;

import org.junit.Assert;
import org.junit.Test;

/**
 * This class implements the test cases for the queue.
 */
public class QueueTest {
    @Test
    public void testEmptyQueue() {
        Queue<Integer> q = new Queue<>();
        Assert.assertEquals(0, q.size());
        Assert.assertTrue(q.isEmpty());
        Assert.assertNull(q.dequeue());
    }

    @Test
    public void testEnqueue() {
        Queue<Integer> q = new Queue<>();
        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);
        Assert.assertEquals(3, q.size());
    }

    @Test
    public void testPeek() {
        Queue<Integer> q = new Queue<>();
        q.enqueue(1);
        Assert.assertEquals("1", q.peek().toString());
        q.enqueue(2);
        q.enqueue(3);
        q.peek();
        Assert.assertEquals("1", q.peek().toString());
    }

    @Test
    public void testDequeue() {
        Queue<Integer> q = new Queue<>();
        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);
        Assert.assertEquals("1", q.dequeue().toString());
        Assert.assertEquals(2, q.size());
        q.dequeue();
        Assert.assertEquals(1, q.size());
        Assert.assertEquals("3", q.dequeue().toString());
        Assert.assertEquals(0, q.size());
    }

}