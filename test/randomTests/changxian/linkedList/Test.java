package test.randomTests.changxian.linkedList;

import src.dataStructures.linkedList.LinkedList;

/**
 * Basic Testing
 */

public class Test {
  public static void main(String[] args) {
    /*
     * Testing insert methods
     */
    System.out.println("Testing insert methods: ");
    LinkedList<Integer> LL = new LinkedList<>(); 
    System.out.println(LL);
    LL.insertFront(2);
    LL.insertEnd(3);
    LL.insertEnd(5);
    LL.insertFront(1);
    System.out.println(LL);
    LL.insert(0, 0);
    LL.insert(4, 4);
    LL.insert(6, 7);
    LL.insert(7, 6);
    LL.insert(6, 6);
    System.out.println(LL);
    System.out.println();

    /*
     * Testing search and get methods
     */
    System.out.println("Testing search & get methods: ");
    System.out.println(LL.search(4));
    System.out.println(LL.search(3));
    System.out.println(LL.get(3));
    System.out.println(LL.get(10));
    System.out.println(LL);
    System.out.println();

    /*
     * Testing remove methods
     */
    System.out.println("Testing remove methods: ");
    LL.remove(5);
    LL.delete(6);
    LL.pop();
    LL.poll();
    System.out.println(LL);
    System.out.println(); 

    /*
     * Testing reverse of Linked Lists
     */
    System.out.println("Testing reverse method: ");
    LinkedList<Integer> newLL = new LinkedList<>();
    newLL.reverse();
    System.out.println(newLL);
    newLL.insertFront(3);
    System.out.println(newLL);
    LL.reverse();
    System.out.println(LL);
    LL.reverse();
    System.out.println(LL);
    System.out.println();

    /*
     * Test sorting
     * Note: testing with entirely new nodes and linked list to avoid mutating LL
     */
    LinkedList<Integer> sortLL = new LinkedList<>();
    sortLL.insertFront(5);
    sortLL.insertEnd(2);
    sortLL.insertFront(7);
    sortLL.insertEnd(3);
    sortLL.insertEnd(4);
    sortLL.insertEnd(3);
    System.out.println(sortLL);
    sortLL.sort();
    System.out.println(sortLL);
    sortLL.insertFront(8);
    sortLL.sort();
    System.out.println(sortLL);
  }
}
