package src.dataStructures.linkedList;

/**
 * There are many variations when it comes to implementing linked lists. Here's mine.
 * A linked list that tracks both head and tail may improve the complexity of some of the methods below
 * (e.g being able to branch off and create a new Linked List in O(1) rather than iterating to find size of branched off list)
 * 
 * Constructors:
 * LinkedList()                      -- Initialise a link list with a null head; size = 0
 * LinkedList(Node<T> head)          -- given a node, make this node the head of a new linked list; iterate to find the size
 * LinkedList(Node<T>head, int size) -- given a head node and size of linked list specified; 
 *                                      made private to avoid client from constructing a linked list with invalid size
 * 
 * Callable methods are:
 * size()                    -- Gets the size of the linked list
 * insertFront(T object)     -- inserts the object at the front of the linked list
 * insertEnd(T object)       -- inserts the object at the end of the linked list
 * insert(T object, int idx) -- inserts the object at the specified index of the linked list
 * remove(int idx)           -- remove the node at the specified index
 * delete(T object)          -- delete the 1st encounter of the specified object from the linked list
 * pop()                     -- remove the last node from the linked list
 * poll()                    -- remove the first node from the linked list
 * search(T object)          -- search for the 1st encounter of the node that holds the specified object
 * get(int idx)              -- get the node at the specified index
 * reverse()                 -- reverse the linked list (head of linked list now starts from the back)
 * sort()                    -- sorts the linked list by their natural order
 * 
 * @param <T> generic type for objects to be stored in the linked list
 */
public class LinkedList<T extends Comparable<T>> {
  private Node<T> head;
  private int size;
  
  public LinkedList() {
    head = null;
    size = 0;
  }

  public LinkedList(Node<T> head) {
    this.head = head;
    Node<T> trav = head;
    int count = 0;
    while (trav != null) { // We count the size of our linked list through iteration.
      count++;
      trav = trav.next;
    }
    size = count;
  }

  private LinkedList(Node<T> head, int size) {
    this.head = head;
    this.size = size;
  }

  /**
   * Gets the size of the linked list.
   * @return int value
   */
  public int size() {
    return this.size;
  }

  /**
   * inserts the object at the specified index of the linked list
   * @param object to be inserted
   * @param idx index which the object is to be inserted into
   * @return boolean representing whether insertion was successful
   */
  public boolean insert(T object, int idx) {
    if (idx > size) {
      System.out.println("Index out of bounds.");
      return false;
    }

    Node<T> newNode = new Node<>(object);

    if (head == null) { // Linked list empty; We just need to add the head;
      head = newNode;
      size++;
      return true;
    }

    Node<T> prev = null;
    Node<T> trav = head;
    for (int i = 0; i < idx; i++) {
      prev = trav;
      trav = trav.next;
    }

    if (prev != null) { // Reset the pointer at the index.
      prev.next = newNode;
      newNode.next = trav;
    } else { // case when inserting at index 0; need to update head
      head = newNode;
      newNode.next = trav;
    }
    size++;
    return true;
  }

  /**
   * Inserts the object at the front of the linked list
   * @param object to be inserted
   * @return boolean representing whether insertion was successful
   */
  public boolean insertFront(T object) {
    return insert(object, 0);
  }

  /**
   * Inserts the object at the end of the linked list
   * @param object to be inserted 
   * @return boolean representing whether insertion was successful
   */
  public boolean insertEnd(T object) {
    return insert(object, this.size);
  }

  /**
   * remove the node at the specified index
   * @param idx of the node to be removed
   * @return node's value
   */
  public T remove(int idx) {
    if (idx >= size) {
      System.out.println("Index out of bounds.");
      return null;
    }

    Node<T> prev = null;
    Node<T> trav = head;
    
    for (int i = 0; i < idx; i++) {
      prev = trav;
      trav = trav.next;
    } // This iteration allows us to store a copy of nodes before and after idx.

    if (prev != null) {
      prev.next = trav.next;
    } else { // case when removing head; need update head
      head = head.next;
    }
    size--;
    return trav.val;
  }

  /**
   * search for the 1st encounter of the node that holds the specified object
   * @param object
   * @return index of the node found
   */
  public int search(T object) {
    Node<T> trav = head;
    int idx = 0;
    if (trav == null) { // Empty linked list.
      return -1;
    }

    while (trav != null) {
      if (trav.val.equals(object)) {
        return idx;
      }
      idx++;
      trav = trav.next;
    }

    return -1;
  }

  /**
   * delete the 1st encounter of the specified object from the linked list
   * @param object to search and delete
   * @return boolean whether the delete op was successful
   */
  public boolean delete(T object) {
    int idx = search(object); // Get index of object to remove.
    if (idx != -1) {
      remove(idx); // Remove based on that index.
      return true;
    }
    return false;
  }

  /**
   * remove the last node from the linked list
   * @return value of popped node
   */
  public T pop() {
    return remove(size() - 1);
  }

  /**
   * remove the first node from the linked list
   * @return value of polled node
   */
  public T poll() {
    return remove(0);
  }


  /**
   * get the node at the specified index
   * @param idx of node to be found
   * @return node found
   */
  public Node<T> get(int idx) {
    Node<T> trav = head;
    if (idx < this.size && trav != null) { // Check: idx is valid & linked list not empty.
      for (int i = 0; i < idx; i++) {
        trav = trav.next;
      }
      return trav;
    }
    return null;
  }

  /**
   * reverse the linked list.
   * A good video to visualise this algorithm can be found
   * <a = https://www.youtube.com/watch?v=D7y_hoT_YZI, href = "url">here</a>.
   */
  public void reverse() {
    if (head == null || head.next == null) { // No need to reverse if list is empty or only 1 element.
      return;
    }

    Node<T> prev = head;
    Node<T> curr = head.next;
    Node<T> newHead = curr; // Store the next head.
    prev.next = null; // Reset to null, representing end of list.
    while (curr.next != null) {
      newHead = curr.next; // We set the next element as the newHead.
      curr.next = prev; // Replace our current node as the previous node.
      prev = curr;
      curr = newHead; // Set our current node as the next element (newHead) to look at.
    }
    newHead.next = prev;
    head = newHead; // newHead is last ele from org. list -> First ele of our reversed list.
  }

  /**
   * Sorts the linked list by the natural order of the elements.
   * Generally, merge sort is the most efficient sorting algorithm for linked lists.
   * Addressing a random node in the linked list incurrs O(n) time complexity.
   * This makes sort algorithms like quicksort and heapsort inefficient since they rely
   * on the O(1) lookup time of an array.
   * 
   * A good video to visualise this algorithm can be found
   * <a = https://www.youtube.com/watch?v=JSceec-wEyw, href = "url">here</a>.
   */
  public void sort() {
    if (this.size <= 1) {
      return;
    }
    int mid = (this.size - 1) / 2;
    Node<T> middle = this.get(mid);
    Node<T> nextHead = middle.next;
    LinkedList<T> next = new LinkedList<>(nextHead, this.size - 1 - mid); // Split the list into 2.
    middle.next = null;
    this.size = mid + 1; // update size of original list after split

    next.sort(); // Recursively sort the list.
    this.sort();
    head = merge(this, next);
  }

  /**
   * Merge routine helper function for two sorted linked lists
   * @param first sorted linked list
   * @param second sorted linked list
   * @return head of merged linked list
   */
  private Node<T> merge(LinkedList<T> first, LinkedList<T> second) {
    Node<T> headFirst = first.head;
    Node<T> headSecond = second.head;
    Node<T> dummy = new Node<>(null);
    Node<T> trav = dummy;

    while (headFirst != null && headSecond != null) {
      if (headFirst.val.compareTo(headSecond.val) < 0) {
        // Note that first & second either only have 2 values or are already sorted.
        trav.next = headFirst;
        headFirst = headFirst.next;
      } else {
        trav.next = headSecond;
        headSecond = headSecond.next;
      }
      trav = trav.next;
    }

    if (headFirst != null) { // Add any remaining nodes from first.
      trav.next = headFirst;
    } else { // We know loop terminated because of second; Add any remaining nodes from second.
      trav.next = headSecond;
    }
    return dummy.next;
  }

  @Override
  public String toString() {
    Node<T> trav = head;
    String ret = "";
    if (trav == null) {
      return null;
    }
    while (trav != null) {
      ret += trav + " ";
      trav = trav.next;
    }
    return ret.substring(0, ret.length());
  }
  
  /**
   * Node class for linked list
   */
  private static class Node<T> {
    T val;
    Node<T> next;

    private Node(T val) {
      this.val = val;
    }

    @Override
    public String toString() {
      return this.val.toString();
    }
  } 
}
