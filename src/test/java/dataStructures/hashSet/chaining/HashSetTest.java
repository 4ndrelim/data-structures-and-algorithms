package dataStructures.hashSet.chaining;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

/**
 * Test cases for {@link HashSet} implemented using Chaining to resolve collisions.
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
  public void test_hashSet_shouldNotHaveDuplicates() {
    HashSet<String> hashSet = new HashSet<>();
    hashSet.add("Hello");
    hashSet.add("World");
    hashSet.add("Hello");
    hashSet.add("World");
    List<String> firstList = Stream.of("Hello", "World").sorted().collect(Collectors.toList());
    List<String> firstResult = hashSet.toList().stream().sorted().collect(Collectors.toList());

    assertEquals(firstList, firstResult);
  }
}
