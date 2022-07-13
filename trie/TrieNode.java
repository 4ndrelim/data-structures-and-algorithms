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

  public boolean containsKey(char c) {
    return next[c-'a'] != null;
  }

  public TrieNode getNext(char c) {
    return next[c-'a'];
  }

  public void insertKey(char c) {
    next[c-'a'] = new TrieNode();
  }

  public boolean isEnd() {
    return end;
  }

  public void makeEnd() {
    end = true;
  }
}
