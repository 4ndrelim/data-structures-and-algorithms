package randomTests.changxian.stackAndQueue.stack;

import dataStructures.stack.Stack;

/*
 * Basic Testing
 */
public class Test {
  public static void main(String[] args) {
    System.out.println("Testing on empty stack: ");
    Stack<String> myStack = new Stack<>();
    System.out.println(myStack.pop());
    System.out.println(myStack.peek());

    System.out.println("Initialize stack with 'abc', 'def', 'ghi'");
    myStack = new Stack<>("abc", "def", "ghi");

    System.out.println("call peek(): ");
    System.out.println(myStack.peek());

    System.out.println("call pop(): ");
    System.out.println(myStack.pop());

    System.out.println("call peek(): ");
    System.out.println(myStack.peek());

    System.out.println("pushing an element: ");
    myStack.push("midnight prince");
    System.out.println("peek to see pushed element: ");
    System.out.println(myStack.peek());
  }
}
