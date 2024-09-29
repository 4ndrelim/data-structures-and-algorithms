package dataStructures.lruCache;

import java.util.HashMap;

/**
 * Implementation of Least Recently Used (LRU) Cache
 *
 * @param <K> generic type of key to be stored
 * @param <V> generic type of associated value corresponding to a given key
 *            Constructor:
 *            LRU(int capacity)
 *            Client methods:
 *            get(K key)
 *            put(K key, V value)
 *            Both methods above run in expected O(1) time complexity
 */
class LRU<K, V> {
    /**
     * Helper node class that implements doubly linked list
     */
    private class DoublyLinkedListNode<K, V> {
        private K key;
        private V val;
        private DoublyLinkedListNode<K, V> next;
        private DoublyLinkedListNode<K, V> prev;
    }

    private DoublyLinkedListNode<K, V> dllHead;
    private DoublyLinkedListNode<K, V> dllTail;
    private HashMap<K, DoublyLinkedListNode<K, V>> keyToNode = new HashMap<>();
    private int capacity;
    private int lengthOfList = 0;

    /**
     * Constructs an instance of Least Recently Used Cache
     *
     * @param capacity the maximum capacity of the cache
     */
    public LRU(int capacity) {
        this.capacity = capacity;

        dllHead = new DoublyLinkedListNode<>();
        dllTail = new DoublyLinkedListNode<>();
        dllHead.next = dllTail;
        dllTail.prev = dllHead;
    }

    /**
     * Return the value of the key if it exists or return null
     *
     * @param key key of the value to be obtained from LRU cache
     */
    public V get(K key) {
        if (!keyToNode.containsKey(key)) {
            return null;
        }

        DoublyLinkedListNode<K, V> temp = keyToNode.get(key);
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
    public void put(K key, V value) {
        boolean addingNewNode = true;

        DoublyLinkedListNode<K, V> newlyCached;

        if (!keyToNode.containsKey(key)) {
            newlyCached = new DoublyLinkedListNode<>();
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
