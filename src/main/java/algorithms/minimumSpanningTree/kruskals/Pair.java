package src.algorithms.minimumSpanningTree.kruskals;

public class Pair<K, V> {
    K key;
    V val;
    public Pair(K key, V val) {
        this.key = key;
        this.val = val;
    }

    public K getKey() {
        return this.key;
    }

    public V getValue() {
        return this.val;
    }
}