import java.util.*;

public class LinkedList<T> {
  Node<T> head;
  int size;
  
  public LinkedList() {
    head = null;
    size = 0;
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
