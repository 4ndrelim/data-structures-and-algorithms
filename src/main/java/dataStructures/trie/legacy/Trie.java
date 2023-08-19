package src.dataStructures.trie.legacy;

/**
 * Implementation of Trie structure.
 * Supports the follwing common operations:
 * insert(String word)
 * search(String word)
 * startsWith(String prefix)
 */
public class Trie {
  private TrieNode root;

  public Trie() {
    root = new TrieNode();
  }

  /**
   * Insert a word into the trie.
   * @param word the string to be inserted
   */
  public void insert(String word) {
    System.out.println(String.format("~~~~~~~Inserting %s~~~~~~~", word));
    TrieNode node = root;
    for (int i = 0; i < word.length(); i++) {
      char curr = word.charAt(i);
      if (!node.containsKey(curr)) {
        node.insertKey(curr);
      }
      node = node.getNext(curr); // go to the subsequent node!
    }
    node.makeEnd();
  }

  /**
   * Search for a word in the trie.
   * @param word the string to look for
   * @return boolean representing whether the word was found
   */
  public boolean search(String word) {
    System.out.println(String.format("~~~~~~~Searching %s~~~~~~~", word));
    TrieNode node = root;
    for (int i = 0; i < word.length(); i++) {
      char curr = word.charAt(i);
      if (node.containsKey(curr)) {
        node = node.getNext(curr);
      } else {
        return false;
      }
    }
    return node.isEnd();
  }

  /**
   * Search for a prefix in the trie.
   * Note: very similar in implementation to search method
   *       except the search here does not need to look for end flag
   * @param prefix the string to look for
   * @return boolean representing whether the prefix exists
   */
  public boolean startsWith(String prefix) {
    System.out.println(String.format("~~~~~~~Looking for prefix %s~~~~~~~", prefix));
    TrieNode node = root;
    for (int i = 0; i < prefix.length(); i++) {
      char curr = prefix.charAt(i);
      if (node.containsKey(curr)) {
        node = node.getNext(curr);
      } else {
        return false;
      }
    }
    return true;
  }
}
