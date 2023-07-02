import java.util.*;

/**
 * Implementation of TrieNode for Trie structure (assumes chracters are lower-cased alphabets).
 * Each TrieNode has the following attributes:
 * TrieNode Array: Indices of the array represent characters, with a starting from 0 and z ending at 26
 *                 NOTE: Presence of a node at a given index means that character represented by that 
 *                 index is present / is part of the trie structure 
 *                 (regardless whether the node has end flag toggled to True)
 * Capacity      : The size of the TrieNode array
 * end flag      : Tells us whether the current TrieNode is actually a terminating flag
 */

public class TrieNode {
  private TrieNode[] next;
  private final int CAPACITY = 26; // implementation assumes lower-cased alphabets
  private boolean end;

  public TrieNode() {
    next = new TrieNode[CAPACITY];
  }

  /**
   * Checks if node has a character.
   * @param c character to check for presence
   * @return boolean representing if the character exists
   */
  public boolean containsKey(char c) {
    return next[c-'a'] != null;
  }

  /**
   * Get the next node at the index represented by the character.
   * @param c character whose index holds the desired next node
   * @return the desired node at that index
   */
  public TrieNode getNext(char c) {
    return next[c-'a'];
  }

  /**
   * Inserts a character to the current TrieNode.
   * @param c character whose index represents where to insert the node
   */
  public void insertKey(char c) {
    next[c-'a'] = new TrieNode();
  }

  /**
   * Checks if the current TrieNode is a terminating flag.
   * @return boolean value
   */
  public boolean isEnd() {
    return end;
  }

  /**
   * Make the current TrieNode a terminating flag/node.
   */
  public void makeEnd() {
    end = true;
  }
}
