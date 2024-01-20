package dataStructures.trie;

import java.util.Map;

/**
 * Implementation of a TrieNode.
 */
public class TrieNode {
    public Map<Character, TrieNode> children; // or an array of size 26 (assume not case-sensitive) to denote each char
    public boolean isEnd; // the marker to indicate whether the path from the root to this node forms a known word
}
