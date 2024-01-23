package dataStructures.hashSet.openAddressing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.stream.Stream;

import org.junit.Test;


/**
 * Test cases for {@link HashSet} implemented using Open Addressing to resolve collisions.
 */
public class HashSetTest {
    @Test
    public void testAdd_noDuplicates_shouldReturnTrue() {
        HashSet<String> hashSet = new HashSet<>();
        assertTrue(hashSet.add("Hello"));
        assertTrue(hashSet.add("World"));
    }

    @Test
    public void testAdd_withDuplicates_shouldReturnFalse() {
        HashSet<String> hashSet = new HashSet<>();
        assertTrue(hashSet.add("Hello"));
        assertTrue(hashSet.add("World"));
        assertFalse(hashSet.add("Hello")); // Adding duplicate element should return false
    }

    @Test
    public void testContains() {
        HashSet<String> hashSet = new HashSet<>();
        hashSet.add("Hello");
        hashSet.add("World");
        assertTrue(hashSet.contains("Hello"));
        assertTrue(hashSet.contains("World"));
        assertFalse(hashSet.contains("Universe")); // Element not in set
    }

    @Test
    public void testRemove() {
        HashSet<String> hashSet = new HashSet<>();
        hashSet.add("Hello");
        hashSet.add("World");
        assertTrue(hashSet.remove("Hello"));
        assertFalse(hashSet.contains("Hello")); // Element should be removed
        assertFalse(hashSet.remove("Universe")); // Removing non-existent element should return false
    }

    @Test
    public void testSize() {
        HashSet<String> hashSet = new HashSet<>();
        assertEquals(0, hashSet.size()); // Initial size should be 0
        hashSet.add("Hello");
        assertEquals(1, hashSet.size()); // Size after adding one element
        hashSet.add("World");
        assertEquals(2, hashSet.size()); // Size after adding two elements
        hashSet.remove("Hello");
        assertEquals(1, hashSet.size()); // Size after removing one element
    }

    @Test
    public void testIsEmpty() {
        HashSet<String> hashSet = new HashSet<>();
        assertTrue(hashSet.isEmpty()); // Initial set should be empty
        hashSet.add("Hello");
        assertFalse(hashSet.isEmpty()); // Set should not be empty after adding an element
        hashSet.remove("Hello");
        assertTrue(hashSet.isEmpty()); // Set should be empty after removing the only element
    }

    @Test
    public void testContains_afterRemove() {
        HashSet<Integer> hashSet = new HashSet<>();
        Stream.iterate(0, i -> i + 1) // Populates the hashSet.
            .limit(16)
            .forEach(hashSet::add);
        hashSet.remove(4);
        assertTrue(hashSet.add(25)); // add should insert 25 at where 4 was at previously.
        hashSet.remove(10); // Introduce a tombstone in the probe sequence for 25.

        assertTrue(hashSet.contains(25)); // contains should still find 25.
    }

    @Test
    public void testResize() {
        // Create a HashSet with an initial capacity of 16 and load factor of 0.75 for testing.
        HashSet<Integer> set = new HashSet<>();
        for (int i = 1; i <= 12; i++) {
            set.add(i);
        }

        // Verify that the HashSet has the initial capacity of 16.
        assertEquals(16, set.capacity());

        // Adding one more element should trigger a resize operation to double the capacity to 32.
        set.add(13);

        // Verify that the HashSet has resized and doubled its capacity to 32.
        assertEquals(32, set.capacity());

        // Removing elements until it triggers a resize operation to shrink the capacity.
        // Currently size is 13. Therefore, remove 5 elements.
        for (int i = 1; i <= 5; i++) {
            set.remove(i);
        }

        // Verify that the HashSet has not resized.
        assertEquals(32, set.capacity());

        // Removing one more element should trigger a resize operation to halve the capacity back to 16.
        set.remove(10);

        // Verify that the HashSet has resized and halved its capacity back to 16.
        assertEquals(16, set.capacity());
    }

    @Test
    public void testAdd_afterRemove() {
        HashSet<Integer> hashSet = new HashSet<>();
        // these elements all map to the same initial bucket, resulting in collisions.
        hashSet.add(1);
        hashSet.add(17);
        hashSet.add(33);
        // the hashSet will look like {1, 17, 33, ...} after the series of adds

        hashSet.remove(17);
        // hashSet now looks like {1, X, 33, ...} where X denotes a tombstone.

        boolean isAdded = hashSet.add(33); // this should not be added into the hashSet.
        assertFalse(isAdded);

        List<Integer> expectedList = List.of(1, 33);
        List<Integer> actualList = hashSet.toList();

        assertEquals(expectedList, actualList);
    }
}
