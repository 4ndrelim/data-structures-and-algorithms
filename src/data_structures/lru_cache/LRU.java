package src.data_structures.lru_cache;

import java.util.*;

/**
 * Implementation of Least Recently Used (LRU) Cache 
 * @param <K> generic type of key to be stored
 * @param <V> generic type of associated value corresponding to a given key
 * Constructor: 
 *  LRU(int capacity)
 * Client methods:
 *  get(K key)
 *  put(K key, V value)
 * Both methods above run in O(1) average time complexity
 */
public class LRU<K, V> {
    int cap;
    Map<K, Node<K, V>> map;
    Node<K, V> left;  // dummy left node to point to the left end
    Node<K, V> right; // dummy right node to point to the right end

    /**
     * Helper node class that encapsulates key-value pair and act as linked list to neighbour nodes.
     */
    private class Node<K, V> {
        K key;
        V val;
        Node<K, V> next; 
        Node<K,V> prev;
        Node(K key, V value) {
            this.key = key;
            this.val = value;
            this.next = null;
            this.prev = null;
        }
    }

    public LRU(int capacity) {
        this.cap = capacity;
        this.map = new HashMap<>();
        this.left = new Node<>(null, null);
        this.right = new Node<>(null, null);
        this.left.next = this.right;
        this.right.prev = this.left;
    }

    /**
     * Helper method to remove the specified node from the doubly linked list
     * @param node to be removed from the linked list
     */
    private void remove(Node<K, V> node) {
        Node<K, V> prev = node.prev;
        Node<K, V> nxt = node.next;
        prev.next = nxt;
        nxt.prev = prev;
    }

    /**
     * Helper method to insert a node to the right end of the double linked list (Most Recently Used)
     * @param node to be inserted
     */
    private void insert(Node<K, V> node) {
        Node<K, V> prev = this.right.prev;
        prev.next = node;
        node.prev = prev;
        node.next = this.right;
        this.right.prev = node;
    }

    /**
     * return the value of the key if it exists; otherwise null
     * @param key whose value, if exists, to be obtained
     */
    public V get(K key) {
        if (this.map.containsKey(key)) {
            Node<K, V> node = this.map.get(key);
            this.remove(node);
            this.insert(node);
            return node.val;
        }
        return null;
    }

    /**
     * Update the value of the key if the key exists. 
     * Otherwise, add the key-value pair to the cache. 
     * If the number of keys exceeds the capacity from this operation, evict the least recently used key 
     * @param key the key
     * @param val the associated value
     */
    public void update(K key, V val) {
        if (this.map.containsKey(key)) {
            Node<K, V> node = this.map.get(key);
            this.remove(node);
            node.val = val;
            this.insert(node); // make most recently used
        } else {
            Node<K, V> node = new Node<>(key, val);
            this.map.put(node.key, node);
            this.insert(node);
        }

        if (this.map.size() > this.cap) { // evict LRU since capacity exceeded
            Node<K, V> toRemove = this.left.next;
            this.map.remove(toRemove.key);
            this.remove(toRemove);
        }
    }

    /**
     * Custom print for testing
     * prints from LRU to MRU (Most recently used)
     */
    public void print() {
        Node<K, V> trav = this.left.next;
        System.out.print("Dummy");
        while (trav != this.right) {
            System.out.print(" ->");
            System.out.print(trav.key);
            System.out.print(",");
            System.out.print(trav.val);
            trav = trav.next;
        }
        System.out.println();
    }
}