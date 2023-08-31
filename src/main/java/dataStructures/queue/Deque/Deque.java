package dataStructures.queue;

public class Deque<T> {

    private Node<T> first;
    private Node<T> last;
    private int size;

    /**
     * Constructor for empty Deque.
     */
    public Deque() {
        this.size = 0;
        this.first = this.last = null; // implemented with double-linked list
    }

    /**
     * Check if deque is empty.
     *
     * @return True if empty.
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Get size of deque.
     *
     * @return Size of the deque.
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Add element into the back of the deque.
     *
     * @param val Element to be added.
     */
    public void addElement(T val) {
        Node<T> newNode = new Node<>(val);
        if (this.isEmpty()) {
            this.last = this.first = newNode;
            size++;
            return;
        }
        Node<T> temp = this.last;
        temp.next = newNode;
        newNode.prev = temp;
        this.last = newNode;
        this.size++;
    }

    /**
     * Add a element into the front of the deque.
     * @param val Element to be added.
     */
    public void addFirst(T val) {
        Node<T> newNode = new Node<>(val);
        if (this.isEmpty()) {
            this.last = this.first = newNode;
            size++;
            return;
        }
        Node<T> temp = this.first;
        temp.prev = newNode;
        newNode.next = temp;
        this.first = newNode;
        size++;
    }

    /**
     * Peek the first element of the deque.
     *
     * @return The value in the first node of the deque.
     */
    public T peekFirst() {
        if (this.isEmpty()) {
            return null;
        }
        return first.val;
    }

    /**
     * Peek the last element of the deque.
     *
     * @return The value in the last node of the deque.
     */
    public T peekLast() {
        if (this.isEmpty()) {
            return null;
        }
        return last.val;
    }

    /**
     * Removes and retrieves the first element of the deque.
     *
     * @return The value in the first node of the deque.
     */
    public T pollFirst() {
        if (this.isEmpty()) {
            return null;
        }
        Node<T> firstNode = this.first;
        Node<T> newFirstNode = this.first.next;
        if (newFirstNode != null) {
            newFirstNode.prev = null;
        }
        this.first = newFirstNode;
        firstNode.next = null;
        this.size--;
        return firstNode.val;
    }

    /**
     * Removes and retrieves the last element of the deque.
     *
     * @return The value in the last node of the deque.
     */
    public T pollLast() {
        if (this.isEmpty()) {
            return null;
        }
        Node<T> lastNode = this.last;
        Node<T> newLastNode = lastNode.prev;
        if (newLastNode != null) {
            newLastNode.next = null;
        }
        lastNode.prev = null;
        this.last = newLastNode;
        this.size--;
        return lastNode.val;
    }

    /**
     * Converts Deque into String iteratively.
     *
     * @return String representation of elements in the deque.
     */
    @Override
    public String toString() {
        if (this.isEmpty()) {
            return "[]";
        }
        String ret = "[";
        Node<T> temp = this.first;
        while (temp != null) {
            ret += " " + temp.toString() + " ";
            temp = temp.next;
        }
        return ret + "]";
    }

    private static class Node<T> {
        private T val;
        Node<T> next;
        Node<T> prev;

        public Node(T val) {
            this.val = val;
            this.next = this.prev = null;
        }

        @Override
        public String toString() {
            return this.val.toString();
        }
    }
}
