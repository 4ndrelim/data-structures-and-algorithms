package algorithms.minimumSpanningTree.kruskals;

/**
 * Encapsulates a key-value pair.
 *
 * @param <K> the generic type of the key.
 * @param <V> the generic type of the value.
 */
public class Pair<K, V> {
    private final K key;
    private final V val;

    /**
     * Constructs a Pair containing a key and a value.
     *
     * @param key the key to be held.
     * @param val the value to be held.
     */
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
