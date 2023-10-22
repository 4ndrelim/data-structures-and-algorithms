package randomTests.kaiting.trie;

import dataStructures.trie.Trie;

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
    trie.insert("fail");
    trie.insert("failure");

    System.out.println(trie.search("apple"));
    System.out.println(trie.search("app"));
    System.out.println(trie.startsWith("app"));
    trie.prune("fail");
    System.out.println(trie.search("fail"));
    System.out.println(trie.search("failure"));
  }
}
