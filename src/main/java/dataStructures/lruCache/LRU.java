package dataStructures.lruCache;

import java.util.HashMap;

/**
 * Implementation of Least Recently Used (LRU) Cache
 *
 *            Constructor:
 *            LRU(int capacity)
 *            Client methods:
 *            get(K key)
 *            put(K key, V value)
 *            Both methods above run in O(1) average time complexity
 */
class LRU {
    /**
     * Helper node class that implements doubly linked list
     */
    private class doublyLinkedList {
        private int key;
        private int val;
        private doublyLinkedList next;
        private doublyLinkedList prev;
    }

    private doublyLinkedList dllHead;
    private doublyLinkedList dllTail;
    private HashMap<Integer, doublyLinkedList> keyToNode = new HashMap();
    private int capacity;
    private int lengthOfList = 0;

    /**
     * Constructs an instance of Least Recently Used Cache
     *
     * @param capacity the maximum capacity of the cache
     */
    public LRU(int capacity) {
        this.capacity = capacity;

        dllHead = new doublyLinkedList();
        dllHead.key = -1;
        dllHead.val = -1;

        dllTail = new doublyLinkedList();
        dllTail.key = -1;
        dllTail.val = -1;

        dllHead.prev = null;
        dllHead.next = dllTail;
        dllTail.prev = dllHead;
        dllTail.next = null;
    }

    /**
     * Return the value of the key if it exists or return null
     *
     * @param key key of the value to be obtained from LRU cache
     */
    public int get(int key) {
        if (!keyToNode.containsKey(key)) {
            return -1;
        }
    
        doublyLinkedList temp = keyToNode.get(key);
        temp.prev.next = temp.next;
        temp.next.prev = temp.prev;

        temp.next = dllHead.next;
        dllHead.next.prev = temp;
        temp.prev = dllHead;
        dllHead.next = temp;

        return keyToNode.get(key).val;
    }

    /**
     * Insert key-value pair to LRU cache
     *
     * @param key key of the value to be inserted to LRU cache
     * @param value value to be inserted to LRU cache
     */
    public void put(int key, int value) {
        boolean addingNewNode = true;

        doublyLinkedList newlyCached;

        if (!keyToNode.containsKey(key)) {
            newlyCached = new doublyLinkedList();
            newlyCached.key = key;
            newlyCached.val = value;
            keyToNode.put(key, newlyCached);
        } else {
            newlyCached = keyToNode.get(key);
            newlyCached.val = value;
            addingNewNode = false;

            newlyCached.prev.next = newlyCached.next;
            newlyCached.next.prev = newlyCached.prev;
        }

        newlyCached.next = dllHead.next;
        dllHead.next.prev = newlyCached;
        newlyCached.prev = dllHead;
        dllHead.next = newlyCached;

        if (addingNewNode) {
            if (lengthOfList == capacity) {
                keyToNode.remove(dllTail.prev.key);
                dllTail.prev.prev.next = dllTail;
                dllTail.prev = dllTail.prev.prev;
            } else {
                lengthOfList++;
            }
        }
    }
}
