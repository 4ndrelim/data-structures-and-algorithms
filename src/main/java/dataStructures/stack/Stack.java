package dataStructures.stack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Simple implementation of a stack using ArrayList.
 * Note: ArrayList can be implemented using LinkedList
 * and hence the code below can be replicated with
 * LinkedList.
 * Callable methods:
 * push(T item)
 * pop()
 * peek()
 * isEmpty()
 * Note: Calling pop() or peek() on an empty stack returns null
 *
 * @param <T> generic type of objects to be stored in the stack
 */
public class Stack<T> {
    private final List<T> stack;

    /**
     * Constructor to create an empty stack
     */
    public Stack() {
        stack = new ArrayList<>();
    }

    /**
     * Constructor to create a stack with a given list holding the same type
     */
    public Stack(List<T> lst) {
        stack = lst;
    }

    /**
     * Constructor to create a stack given a sequence of objects of the same generic type
     */
    public Stack(T... lst) {
        stack = new ArrayList<T>();
        Collections.addAll(stack, lst);
    }

    /**
     * @param item element to be pushed as the last element into the stack
     */
    public void push(T item) {
        stack.add(item);
    }

    /**
     * Removes the last element from the stack.
     *
     * @return last element of the stack; null if the stack is empty
     */
    public T pop() {
        if (this.isEmpty()) {
            return null;
        }
        // note that the operation below is O(1) when called on the last index
        return stack.remove(stack.size() - 1);
    }

    /**
     * Displays the last element of the stack.
     *
     * @return last element of the stack; null if the stack is empty
     */
    public T peek() {
        if (this.isEmpty()) {
            return null;
        }
        // similarly, below is an O(1) operation
        return stack.get(stack.size() - 1);
    }

    /**
     * Checks if the stack is empty.
     *
     * @return boolean value representing whether the stack is empty
     */
    public boolean isEmpty() {
        return stack.isEmpty();
    }
}
