package randomTests.kaiting.trie.legacy;

import dataStructures.trie.legacy.Trie;

/**
 * Basic testing of trie.
 */
public class TrieTest {
  /**
   * Runs the custom test.
   *
   * @param args unused.
   */
  public static void main(String[] args) {
    Trie trie = new Trie();
    trie.insert("apple");
    trie.insert("coconut");
    trie.insert("apollo");

    System.out.println(trie.search("apple"));
    System.out.println(trie.search("app"));
    System.out.println(trie.startsWith("app"));
  }
}
