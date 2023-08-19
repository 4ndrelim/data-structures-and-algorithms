package dataStructures.stack;

import org.junit.Assert;
import org.junit.Test;

public class StackTest {
    @Test
    public void testEmpty(){
        Stack<Integer> stk = new Stack<>();
        Assert.assertEquals(null, stk.pop());
        Assert.assertEquals(null, stk.peek());
    }

    @Test
    public void testPopAndPeek() {
        Stack<Integer> stk = new Stack<>(1, 2, 3);
        Assert.assertEquals("3", stk.peek().toString());
        Assert.assertEquals("3", stk.pop().toString());
        Assert.assertEquals("2", stk.peek().toString());
    }

    @Test
    public void testPush() {
        Stack<Integer> stk = new Stack<>();
        stk.push(1);
        Assert.assertEquals("1", stk.peek().toString());
        stk.push(2);
        stk.push(3);
        Assert.assertEquals("3", stk.peek().toString());
    }

}
