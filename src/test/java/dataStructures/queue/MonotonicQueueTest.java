package dataStructures.queue;

import org.junit.Assert;
import org.junit.Test;

public class MonotonicQueueTest {
    @Test
    public void testEmpty() {
        MonotonicQueue<Integer> q = new MonotonicQueue<>();
        Assert.assertEquals(true, q.isEmpty());
        Assert.assertEquals(null, q.max());
        Assert.assertEquals(null, q.pop());
    }

    @Test
    public void testMax() {
        MonotonicQueue<Integer> q = new MonotonicQueue<>();
        q.push(2);
        Assert.assertEquals("2", q.max().toString());
        q.push(7);
        Assert.assertEquals("7", q.max().toString());
        q.push(1);
        Assert.assertEquals("7", q.max().toString());
        q.push(7);
        Assert.assertEquals("7", q.max().toString());
        q.push(5);
        Assert.assertEquals("7", q.max().toString());
        q.push(4);
        Assert.assertEquals("7", q.max().toString());
        q.push(3);
        q.push(2);
        q.push(5);
        Assert.assertEquals("7", q.max().toString());
    }

    @Test
    public void testPop() {
        MonotonicQueue<Integer> q = new MonotonicQueue<>();
        q.push(2);
        q.push(7);
        q.push(1);
        q.push(7);
        q.push(5);
        q.push(4);
        q.push(3);
        q.push(2);
        q.push(5);
        q.push(2);

        Assert.assertEquals("7", q.pop().toString());
        Assert.assertEquals("7", q.pop().toString());
        Assert.assertEquals("5", q.pop().toString());
        q.pop();
        Assert.assertEquals("2", q.pop().toString());
        Assert.assertEquals(null, q.pop());
    }
}
