package randomTests.changxian.linkedList;

import dataStructures.linkedList.LinkedList;

/**
 * Basic Testing.
 */
public class Test {
  /**
   * Run custom test script.
   *
   * @param args unused.
   */
  public static void main(String[] args) {
    /*
     * Testing insert methods
     */
    System.out.println("Testing insert methods: ");
    LinkedList<Integer> linkedList = new LinkedList<>();
    System.out.println(linkedList);
    linkedList.insertFront(2);
    linkedList.insertEnd(3);
    linkedList.insertEnd(5);
    linkedList.insertFront(1);
    System.out.println(linkedList);
    linkedList.insert(0, 0);
    linkedList.insert(4, 4);
    linkedList.insert(6, 7);
    linkedList.insert(7, 6);
    linkedList.insert(6, 6);
    System.out.println(linkedList);
    System.out.println();

    /*
     * Testing search and get methods
     */
    System.out.println("Testing search & get methods: ");
    System.out.println(linkedList.search(4));
    System.out.println(linkedList.search(3));
    System.out.println(linkedList.get(3));
    System.out.println(linkedList.get(10));
    System.out.println(linkedList);
    System.out.println();

    /*
     * Testing remove methods
     */
    System.out.println("Testing remove methods: ");
    linkedList.remove(5);
    linkedList.delete(6);
    linkedList.pop();
    linkedList.poll();
    System.out.println(linkedList);
    System.out.println();

    /*
     * Testing reverse of Linked Lists
     */
    System.out.println("Testing reverse method: ");
    LinkedList<Integer> newLinkedList = new LinkedList<>();
    newLinkedList.reverse();
    System.out.println(newLinkedList);
    newLinkedList.insertFront(3);
    System.out.println(newLinkedList);
    linkedList.reverse();
    System.out.println(linkedList);
    linkedList.reverse();
    System.out.println(linkedList);
    System.out.println();

    /*
     * Test sorting
     * Note: testing with entirely new nodes and linked list to avoid mutating linkedList
     */
    LinkedList<Integer> sortLinkedList = new LinkedList<>();
    sortLinkedList.insertFront(5);
    sortLinkedList.insertEnd(2);
    sortLinkedList.insertFront(7);
    sortLinkedList.insertEnd(3);
    sortLinkedList.insertEnd(4);
    sortLinkedList.insertEnd(3);
    System.out.println(sortLinkedList);
    sortLinkedList.sort();
    System.out.println(sortLinkedList);
    sortLinkedList.insertFront(8);
    sortLinkedList.sort();
    System.out.println(sortLinkedList);
  }
}
