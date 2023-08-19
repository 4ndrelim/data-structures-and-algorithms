package src.dataStructures.trie;

import java.util.*;

/**
 * Implementation of TrieNode for Trie structure (not restricited to alphabets)
 * Each TrieNode has the following attributes:
 * characters: HashMap whose keys represent the available characters and 
 *             values represent the nodes to select the next characters
 *             NOTE: Presence of a character in the hashmap means that this character 
 *             is part of the trie structure and can be used to continue forming a searched word
 *             or return the accumulation of letters as the word if the end flag is toggled to true
 * end       : Tells us whether the current TrieNode is actually a terminating flag
 */

public class TrieNode {
  private Map<Character, TrieNode> characters;
  private boolean end;

  public TrieNode() {
    characters = new HashMap<>();
  }

  /**
   * Checks if node has a character.
   * @param c character to check for presence
   * @return boolean representing if the character exists
   */
  public boolean containsKey(char c) {
    return characters.containsKey(c);
  }

  /**
   * Get the next node at the index represented by the character.
   * @param c character whose index holds the desired next node
   * @return the desired node at that index
   */
  public TrieNode getNext(char c) {
    return characters.get(c);
  }

  /**
   * Get the avaialble characters can be used as the next letter from the current node.
   * @return hashmap of characters and next nodes
   */
  public Map<Character, TrieNode> getCharacters() {
    return characters;
  }

  /**
   * Inserts a character to the current TrieNode.
   * @param c character whose index represents where to insert the node
   */
  public void insertKey(char c) {
    characters.put(c, new TrieNode());
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

  /**
   * Remove terminating flag from current TrieNode.
   */
  public void removeEnd() {
    end = false;
  }
}
