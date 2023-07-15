package src.algorithms.patternFinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of KMP.
 *
 * Illustration of getPrefixIndices:
 * Here we make a distinction between position and index. The position is basically 1-indexed.
 * Note the return indices are still 0-indexed of the pattern string.
 *  Position:  0   1   2   3   4   5   6   7   8   9   10  11  12  13  14  15
 *   Pattern:      A   B   C   A   B   C   N   O   A   B   C   A   B   C   A ...
 *    Return: -1   0   0   0   1   2   3   0   0   1   2   3   4   5   6   4 ...
 *             ^  an indexing trick; consider 1-indexed characters for clarity and simplicity in the main algor
 *                 ^ 'A' is the first character of the pattern string,
 *                 there is no prefix ending before its index, 0, that can be matched with
 *                     ^   ^ 'B' and 'C' cannot be matched with any prefix which are just 'A' and 'AB'
 *                             ^ Can be matched with an earlier 'A'. So we store 1. Which can also be interpreted as the
 *                             index of the next character to match against!
 *                                 ^   ^ Similarly, continue matching
 *                                          ^  ^ No matches, so 0
 *                                                 ^   ^   ^   ^   ^   ^ Match with prefix until index 6!
 *                                                                         ^ where the magic happens, we know ABC of
 *                                                                         index 0-2 exists and can 'restart' from there
 *
 */
public class KMP {
    /**
     * Find and indicate all suffix that match with a prefix.
     * @param pattern to search
     * @return an array of indices where the suffix ending at each position of they array can be matched with
     * corresponding a prefix of the pattern ending before the specified index
     */
    private static int[] getPrefixIndices(String pattern) {
        int len = pattern.length();
        int[] prefixIndices = new int[len + 1];
        prefixIndices[0] = -1;

        int currPrefixMatched = 0; // num of chars of prefix pattern currently matched
        int pos = 2; // Starting from the 2nd character, recall 1-indexed
        while (pos <= len) {
            if (pattern.charAt(pos-1) == pattern.charAt(currPrefixMatched)) {
                currPrefixMatched += 1;
                // note, the line below can also be interpreted as the index of the next char to match
                prefixIndices[pos] = currPrefixMatched; // an indexing trick, store at the pos, num of chars matched
                pos += 1;
            } else if (currPrefixMatched > 0) {
                // go back to a previous known match and try to match again
                currPrefixMatched = prefixIndices[currPrefixMatched];
            } else {
                // unable to match, time to move on
                prefixIndices[pos] = 0;
                pos += 1;
            }
        }
        return prefixIndices;
    }

    /**
     * Main logic of KMP. Iterate the sequence, looking for patterns. If a difference is found, resume matching from
     * a previously identified sub-pattern, if possible.
     * @param sequence to search against
     * @param pattern to search for
     * @return start indices of all occurrences of pattern found
     */
    public static List<Integer> findOccurrences(String sequence, String pattern) {
        int sLen = sequence.length();
        int pLen = pattern.length();
        int[] prefixIndices = getPrefixIndices(pattern);
        List<Integer> indicesFound = new ArrayList<>();

        int s = 0;
        int p = 0;

        while (s < sLen) {
            if (pattern.charAt(p) == sequence.charAt(s)) {
                p += 1;
                s += 1;
                if (p == pLen) {
                    // occurrence found
                    indicesFound.add(s - pLen); // start index of this occurrence
                    p = prefixIndices[p]; // reset
                }
            } else {
                p = prefixIndices[p];
                if (p < 0) { // move on
                    p += 1;
                    s += 1;
                }
            }
        }
        return indicesFound;
    }
}
