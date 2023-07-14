package src.algorithms.patternFinding;

import java.util.ArrayList;
import java.util.List;

/**
 * O(nk) -- > O(n+k)
 * This is still IP.
 */
public class KMP {
    private static int[] getPrefixIndices(String pattern) {
        int len = pattern.length();
        int[] prefixIndices = new int[len + 1];
        prefixIndices[0] = -1;

        int currPrefixMatched = 0; // num of chars of prefix pattern currently matched
        int i = 1; // iterate until the end
        while (i < len) {
            if (pattern.charAt(i) == pattern.charAt(currPrefixMatched)) {
                currPrefixMatched += 1;
                i += 1;
                // note, the line below can also be interpreted as the index of the next char to match
                prefixIndices[i] = currPrefixMatched; // an indexing trick, store at the ith pos, num of chars matched
            } else if (currPrefixMatched > 0) {
                // go back to a previous known match and try to match again
                currPrefixMatched = prefixIndices[currPrefixMatched];
            } else {
                // unable to match, time to move on
                i += 1;
                prefixIndices[1] = 0; // recall prefixIndices is 1-indexed and hence update after incrementing
            }
        }
        return prefixIndices;
    }

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
