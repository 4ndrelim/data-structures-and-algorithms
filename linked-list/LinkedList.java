import java.util.*;

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
    while (trav != null) {
      count++;
      trav = trav.next;
    }
    size = count;
  }

  private LinkedList(Node<T> head, int size) {
    this.head = head;
    this.size = size;
  }

  public int size() {
    return this.size;
  }

  public boolean insertFront(T object) {
    return insert(object, 0);
  }

  public boolean insertEnd(T object) {
    return insert(object, this.size);
  }

  public boolean insert(T object, int idx) {
    if (idx > size) {
      System.out.println("Index out of bounds.");
      return false;
    }

    Node<T> newNode = new Node<>(object);

    if (head == null) {
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

    if (prev != null) {
      prev.next = newNode;
      newNode.next = trav;
    } else { // case when inserting at index 0; need to update head
      head = newNode;
      newNode.next = trav;
    }
    size++;
    return true;
  }

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
    }

    if (prev != null) {
      prev.next = trav.next;
    } else { // case when removing head; need update head
      head = head.next;
    }
    size--;
    return trav.val;
  }

  public boolean delete(T object) {
    int idx = search(object);
    if (idx != -1) {
      remove(idx);
      return true;
    }
    return false;
  }

  public T pop() {
    return remove(size() - 1);
  }

  public T poll() {
    return remove(0);
  }

  public int search(T object) {
    Node<T> trav = head;
    int idx = 0;
    if (trav == null) {
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

  public Node<T> get(int idx) {
    Node<T> trav = head;
    if (idx < this.size && trav != null) {
      for (int i = 0; i < idx; i++) {
        trav = trav.next;
      }
      return trav;
    }
    return null;
  }

  public void reverse() {
    if (head == null || head.next == null) {
      return;
    }

    Node<T> prev = head;
    Node<T> curr = head.next;
    Node<T> newHead = curr;
    prev.next = null;
    while (curr.next != null) {
      newHead = curr.next;
      curr.next = prev;
      prev = curr;
      curr = newHead;
    }
    newHead.next = prev;
    head = newHead;
  }

  public void sort() {
    if (this.size <= 1) {
      return;
    }
    int mid = (this.size - 1) / 2;
    Node<T> middle = this.get(mid);
    Node<T> nextHead = middle.next;
    LinkedList<T> next = new LinkedList<>(nextHead, this.size - 1 - mid);
    middle.next = null;
    this.size = mid + 1; // update size of original LL after split

    next.sort();
    this.sort();
    head = merge(this, next);
  }

  private Node<T> merge(LinkedList<T> first, LinkedList<T> second) {
    Node<T> headFirst = first.head;
    Node<T> headSecond = second.head;
    Node<T> dummy = new Node<>(null);
    Node<T> trav = dummy;

    while (headFirst != null && headSecond != null) {
      if (headFirst.val.compareTo(headSecond.val) < 0) {
        trav.next = headFirst;
        headFirst = headFirst.next;
      } else {
        trav.next = headSecond;
        headSecond = headSecond.next;
      }
      trav = trav.next;
    }

    if (headFirst != null) {
      trav.next = headFirst;
    }
    if (headSecond != null) {
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
