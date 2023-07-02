package src.data_structures.trie;

import java.util.*;

/**
 * Implementation of Trie structure.
 * Supports the follwing common operations (see below for doc):
 * insert(String word)
 * search(String word)
 * startsWith(String prefix)
 * prune(String word)
 */
public class Trie {
  private TrieNode root;

  public Trie() {
    root = new TrieNode();
  }

  /**
   * Insert a word into the trie; converts word to
   * to lower-case characters before insertion.
   * @param word the string to be inserted
   */
  public void insert(String word) {
    word = word.toLowerCase();
    System.out.println(String.format("~~~~~~~Inserting '%s'~~~~~~~", word));
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
   * Search for a word (converted to lower-case) in the trie.
   * @param word the string to look for
   * @return boolean representing whether the word was found
   */
  public boolean search(String word) {
    word.toLowerCase();
    System.out.println(String.format("~~~~~~~Searching '%s'~~~~~~~", word));
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
   * Search for a prefix (converted to lower-case) in the trie.
   * Note: very similar in implementation to search method
   *       except the search here does not need to look for end flag
   * @param prefix the string to look for
   * @return boolean representing whether the prefix exists
   */
  public boolean startsWith(String prefix) {
    prefix = prefix.toLowerCase();
    System.out.println(String.format("~~~~~~~Looking for prefix '%s'~~~~~~~", prefix));
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
  
  /**
   * Removes a word from the trie by toggling the end flag;
   * if any of the end nodes (next nodes relative to current) 
   * do not hold further characters, repetitively prune the trie 
   * by removing these nodes from the hashmap of the current node.
   * Note: This method is useful in optimizing searching for a set of known words  
   *       especially when the data to be traversed has words that are similar in spelling/
   *       repeated words which might have been previously found.
   * @param word the word to be removed
   */
  public void prune(String word) {
    word = word.toLowerCase();
    System.out.println(String.format("~~~~~~~Removing '%s'~~~~~~~", word));
    TrieNode node = root;
    TrieNode[] track = new TrieNode[word.length()];
    for (int i = 0; i < word.length(); i++) {
      char curr = word.charAt(i);
      track[i] = node;
      node = node.getNext(curr);
    }
    node.removeEnd();
    for (int i = word.length() - 1; i >= 0; i--) {
      char curr = word.charAt(i);
      if (track[i].getNext(curr).getCharacters().size() > 0) {
        break; // done further nodes are required
      } else {
        track[i].getCharacters().remove(curr);
      }
    }
    return;
  }
}
