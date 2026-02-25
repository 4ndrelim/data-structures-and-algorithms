package dataStructures.lruCache;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class LRUTest {

    @Test
    public void testBasicGetPut() {
        LRU<String, Integer> cache = new LRU<>(3);
        cache.put("a", 1);
        cache.put("b", 2);
        cache.put("c", 3);

        assertEquals(Integer.valueOf(1), cache.get("a"));
        assertEquals(Integer.valueOf(2), cache.get("b"));
        assertEquals(Integer.valueOf(3), cache.get("c"));
    }

    @Test
    public void testGetNonExistent() {
        LRU<String, Integer> cache = new LRU<>(3);
        cache.put("a", 1);

        assertNull(cache.get("nonexistent"));
    }

    @Test
    public void testEviction() {
        LRU<String, Integer> cache = new LRU<>(3);
        cache.put("a", 1);
        cache.put("b", 2);
        cache.put("c", 3);
        // Cache is now full: [c, b, a] (most to least recent)

        cache.put("d", 4);
        // "a" should be evicted as LRU: [d, c, b]

        assertNull(cache.get("a"));
        assertEquals(Integer.valueOf(2), cache.get("b"));
        assertEquals(Integer.valueOf(3), cache.get("c"));
        assertEquals(Integer.valueOf(4), cache.get("d"));
    }

    @Test
    public void testAccessUpdatesOrder() {
        LRU<String, Integer> cache = new LRU<>(3);
        cache.put("a", 1);
        cache.put("b", 2);
        cache.put("c", 3);
        // Order: [c, b, a]

        cache.get("a");  // Access "a", moves it to front
        // Order: [a, c, b]

        cache.put("d", 4);
        // "b" should be evicted: [d, a, c]

        assertNull(cache.get("b"));
        assertEquals(Integer.valueOf(1), cache.get("a"));
        assertEquals(Integer.valueOf(3), cache.get("c"));
        assertEquals(Integer.valueOf(4), cache.get("d"));
    }

    @Test
    public void testUpdateExistingKey() {
        LRU<String, Integer> cache = new LRU<>(3);
        cache.put("a", 1);
        cache.put("b", 2);
        cache.put("c", 3);

        cache.put("a", 100);  // Update "a" with new value

        assertEquals(Integer.valueOf(100), cache.get("a"));
        // Verify other values unchanged
        assertEquals(Integer.valueOf(2), cache.get("b"));
        assertEquals(Integer.valueOf(3), cache.get("c"));
    }

    @Test
    public void testUpdateMovesToFront() {
        LRU<String, Integer> cache = new LRU<>(3);
        cache.put("a", 1);
        cache.put("b", 2);
        cache.put("c", 3);
        // Order: [c, b, a]

        cache.put("a", 100);  // Update "a", should move to front
        // Order: [a, c, b]

        cache.put("d", 4);
        // "b" should be evicted

        assertNull(cache.get("b"));
        assertEquals(Integer.valueOf(100), cache.get("a"));
    }

    @Test
    public void testCapacityOne() {
        LRU<String, Integer> cache = new LRU<>(1);

        cache.put("a", 1);
        assertEquals(Integer.valueOf(1), cache.get("a"));

        cache.put("b", 2);
        assertNull(cache.get("a"));  // "a" evicted
        assertEquals(Integer.valueOf(2), cache.get("b"));
    }

    @Test
    public void testIntegerKeys() {
        LRU<Integer, String> cache = new LRU<>(2);
        cache.put(1, "one");
        cache.put(2, "two");

        assertEquals("one", cache.get(1));
        assertEquals("two", cache.get(2));

        cache.put(3, "three");
        assertNull(cache.get(1));  // evicted
    }
}
