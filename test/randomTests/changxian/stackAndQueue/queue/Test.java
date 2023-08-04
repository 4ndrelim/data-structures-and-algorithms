package test.randomTests.changxian.stackAndQueue.queue;

import src.dataStructures.queue.monotonicQueue.Queue;

/**
 * Basic Testing
 */
public class Test {
  public static void main(String[] args) {
    Queue<Integer> myQueue = new Queue<>();
    System.out.println("Adding 7, 17, 27");
    myQueue.enqueue(7);
    myQueue.enqueue(17);
    myQueue.enqueue(27);
    System.out.println("size: " + myQueue.size());
    System.out.println("peek: " + myQueue.peek());
    System.out.println("dequeue: " + myQueue.dequeue());
    System.out.println("peek: " + myQueue.peek());
    System.out.println("size: " + myQueue.size());
  }
}
