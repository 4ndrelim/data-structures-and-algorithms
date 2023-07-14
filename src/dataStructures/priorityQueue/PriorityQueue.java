package src.dataStructures.priorityQueue;

import src.dataStructures.heap.MinHeap;

import java.util.HashMap;

/**
 * implementation of a Priority Queue using a Binary Min-Heap
 * public methods:
 * void insert(Key k, Priority p) insert key with priority p
 * Data extractMin() remove key with minimum priority
 * Data peek() looks at the minimum key without removing it.
 * void decreaseKey(Key k, Priority p) reduce the priority of a key to priority p
 * boolean contains(Key k) checks if the priority queue contains the key
 * boolean isEmpty() checks if the priority queue is empty
 */
public class PriorityQueue<Key, Priority extends Comparable<Priority>> {
    private final MinHeap<PQNode<Key, Priority>> heap;
    private final HashMap<Key, PQNode<Key, Priority>> inHeap;

    public PriorityQueue() {
        this.heap = new MinHeap<>();
        this.inHeap = new HashMap<>();
    }

    public void insert(Key key, Priority priority) {
        PQNode<Key, Priority> toInsert = new PQNode<>(key, priority);
        this.heap.insert(toInsert);
        this.inHeap.put(key, toInsert);
    }

    public Key extractMin() {
        Key minKey = this.heap.extractMin().key;
        this.inHeap.remove(minKey);
        return minKey;
    }

    public Key peek() {
        return this.heap.getMin().key;
    }

    public void decreaseKey(Key key, Priority newPriority) {
        PQNode<Key, Priority> original = this.inHeap.get(key);
        PQNode<Key, Priority> newNode = new PQNode<>(key, newPriority);
        this.heap.decreaseKey(original, newNode);
        this.inHeap.put(key, newNode); // replaces value of k-v pair.
    }

    public boolean contains(Key key) {
        return this.inHeap.containsKey(key);
    }

    public boolean isEmpty() {
        return this.heap.isEmpty();
    }

    @Override
    public String toString() {
        return String.format("%s", this.heap);
    }

    public static void main(String[] args) {
        PriorityQueue<String, Integer> pq = new PriorityQueue<>();
        pq.insert("B", 2);
        pq.insert("A", 1);
        System.out.println(pq.extractMin());
        System.out.println(pq.extractMin());
        System.out.println(pq);
    }

    private static class PQNode<Key, Priority extends Comparable<Priority>>
            implements Comparable<PQNode<Key, Priority>> {
        Key key; // Key
        Priority priority; // Priority - smaller priority => extracted first - trippy

        public PQNode(Key key, Priority priority) {
            this.key = key;
            this.priority = priority;
        }


        // TODO: check for whether an arbitrary tie-breaker is needed. PECS for method signature also?
        @Override
        public int compareTo(PQNode<Key, Priority> node) {
            return this.priority.compareTo(node.priority);
        }


        // Assuming no duplicate Keys, two nodes are only equal if they are the same keys.
        @Override
        public boolean equals(Object obj) {
            if (obj instanceof PQNode<?,?>) {
                PQNode<?, ?> givenNode = (PQNode<?, ?>) obj;
                return this.key.equals(givenNode.key);
            }
            return false;
        }

        // Assuming no duplicate Keys, two nodes are only equal if they are the same keys
        @Override
        public int hashCode() {
            return this.key.hashCode();
        }

        @Override
        public String toString() {
            return String.format("Key: %s, Priority: %s", this.key, this.priority);
        }
    }
}

