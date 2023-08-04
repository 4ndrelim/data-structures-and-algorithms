package test.dataStructures.stack;

import org.junit.Assert;
import org.junit.Test;
import src.dataStructures.stack.Stack;
public class StackTest {
    @Test
    public void testEmpty(){
        Stack<Integer> stk = new Stack<>();
        Assert.assertEquals(stk.pop(), null);
        Assert.assertEquals(stk.peek(), null);
    }

    @Test
    public void testPopAndPeek() {
        Stack<Integer> stk = new Stack<>(1, 2, 3);
        Assert.assertEquals(stk.peek().toString(), "3");
        Assert.assertEquals(stk.pop().toString(), "3");
        Assert.assertEquals(stk.peek().toString(), "2");
    }

    @Test
    public void testPush() {
        Stack<Integer> stk = new Stack<>();
        stk.push(1);
        Assert.assertEquals(stk.peek().toString(), "1");
        stk.push(2);
        stk.push(3);
        Assert.assertEquals(stk.peek().toString(), "3");
    }

}
