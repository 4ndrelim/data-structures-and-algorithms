package dataStructures.trie;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of a TrieNode.
 */
public class TrieNode {
    // CHECKSTYLE:OFF: VisibilityModifier
    public Map<Character, TrieNode> children; // or an array of size 26 (assume not case-sensitive) to denote each char
    // CHECKSTYLE:OFF: VisibilityModifier
    public boolean isEnd; // a marker to indicate whether the path from the root to this node forms a known word

    /**
     * Basic constructor.
     */
    public TrieNode() {
        children = new HashMap<Character, TrieNode>();
        isEnd = false;
    }
}
