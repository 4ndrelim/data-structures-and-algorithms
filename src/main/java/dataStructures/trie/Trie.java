package dataStructures.trie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of a Trie; Here we consider strings (not case-sensitive)
 */
public class Trie {
    private final TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    /**
     * TrieNode implementation. Note, fields are set to public for decreased verbosity.
     */
    private class TrieNode {
        // CHECKSTYLE:OFF: VisibilityModifier
        public Map<Character, TrieNode> children; // or array of size 26 (assume not case-sensitive) to denote each char
        // CHECKSTYLE:OFF: VisibilityModifier
        public boolean isEnd; // a marker to indicate whether the path from the root to this node forms a known word

        public TrieNode() {
            children = new HashMap<Character, TrieNode>();
            isEnd = false;
        }
    }

    /**
     * Inserts a word into the trie.
     * @param word
     */
    public void insert(String word) {
        word = word.toLowerCase(); // ignore case-sensitivity
        TrieNode trav = root;
        char curr;
        for (int i = 0; i < word.length(); i++) {
            curr = word.charAt(i);
            if (!trav.children.containsKey(curr)) {
                trav.children.put(curr, new TrieNode()); // recall: new char is represented by this child node
            }
            trav = trav.children.get(curr);
        }
        trav.isEnd = true; // set word
    }

    /**
     * Searches for a word in the trie.
     * @param word
     * @return true if the word is found, false otherwise.
     */
    public boolean search(String word) {
        word = word.toLowerCase();
        TrieNode trav = root;
        char curr;
        for (int i = 0; i < word.length(); i++) {
            curr = word.charAt(i);
            if (!trav.children.containsKey(curr)) {
                return false;
            }
            trav = trav.children.get(curr);
        }
        return trav.isEnd;
    }

    /**
     * Deletes a word from the trie.
     * @param word
     */
    public void delete(String word) {
        word = word.toLowerCase();
        TrieNode trav = root;
        char curr;
        for (int i = 0; i < word.length(); i++) {
            curr = word.charAt(i);
            if (!trav.children.containsKey(curr)) {
                return; // word does not exist in trie, so just return
            }
            trav = trav.children.get(curr);
        }
        trav.isEnd = false; // remove word from being tracked
    }

    // ABOVE ARE STANDARD METHODS OF A TYPICAL TRIE IMPLEMENTATION
    // BELOW IMPLEMENTS TWO MORE COMMON / USEFUL METHODS FOR TRIE; IN PARTICULAR, NOTE THE PRUNING METHOD

    /**
     * Deletes a word from the trie, and also prune redundant nodes. This is useful in keeping the trie compact.
     * @param word
     */
    public void deleteAndPrune(String word) {
        List<TrieNode> trackNodes = new ArrayList<>();
        TrieNode trav = root;
        char curr;
        for (int i = 0; i < word.length(); i++) {
            curr = word.charAt(i);
            if (!trav.children.containsKey(curr)) {
                return; // word does not exist in trie
            }
            trackNodes.add(trav);
            trav = trav.children.get(curr);
        }
        trav.isEnd = false;

        // now, we start pruning
        TrieNode currNode; // nodes representing chars, where chars will be iterated from the back
        TrieNode nodeBeforeCurr;
        for (int i = word.length() - 1; i >= 0; i--) { // consider chars from back
            curr = word.charAt(i);
            nodeBeforeCurr = trackNodes.get(i);
            currNode = nodeBeforeCurr.children.get(curr);
            if (!currNode.isEnd && currNode.children.size() == 0) { // node essentially doesn't track anything, remove
                nodeBeforeCurr.children.remove(curr);
            } else { // children.size() > 0; i.e. this node is still useful; no need to further prune upwards
                break;
            }
        }
    }

    /**
     * Find all words with the specified prefix.
     * @param prefix
     * @return a list of words.
     */
    public List<String> wordsWithPrefix(String prefix) {
        List<String> ret = new ArrayList<>();
        TrieNode trav = root;
        for (int i = 0; i < prefix.length(); i++) {
            char curr = prefix.charAt(i);
            if (!trav.children.containsKey(curr)) {
                return ret; // no words with this prefix
            }
            trav = trav.children.get(curr);
        }
        List<StringBuilder> allSuffix = getAllSuffixFromNode(trav);
        for (StringBuilder sb : allSuffix) {
            ret.add(prefix + sb.toString());
        }
        return ret;
    }

    /**
     * Find all words in the trie.
     * @return a list of words.
     */
    public List<String> getAllWords() {
        List<StringBuilder> allWords = getAllSuffixFromNode(root);
        List<String> ret = new ArrayList<>();
        for (StringBuilder sb : allWords) {
            ret.add(sb.toString());
        }
        return ret;
    }

    /**
     * Helper method to get suffix from the node.
     * @param node
     * @return
     */
    private List<StringBuilder> getAllSuffixFromNode(TrieNode node) {
        List<StringBuilder> ret = new ArrayList<>();
        if (node.isEnd) {
            ret.add(new StringBuilder(""));
        }
        for (char c : node.children.keySet()) {
            TrieNode nextNode = node.children.get(c);
            List<StringBuilder> allSuffix = getAllSuffixFromNode(nextNode);
            for (StringBuilder sb : allSuffix) {
                sb.insert(0, c); // insert c at the front
                ret.add(sb);
            }
        }
        return ret;
    }

    // BELOW IS A METHOD THAT IS USED FOR TESTING PURPOSES ONLY

    /**
     * Helper method for testing purposes.
     * @param str
     * @param pos
     * @return
     */
    public Boolean checkNodeExistsAtPosition(String str, Integer pos) {
        TrieNode trav = root;
        for (int i = 0; i < pos; i++) {
            char c = str.charAt(i);
            if (trav.children.containsKey(c)) {
                trav = trav.children.get(c);
            } else {
                return false;
            }
        }
        return true;
    }
}
