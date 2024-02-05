package algorithms.patternFinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of KMP.
 * <p>
 * Illustration of getPrefixTable: with pattern ABCABCNOABCABCA
 * We consider 1-indexed positions. Position 0 will be useful later in as a trick to inform that are no prefix matches
 * Position:  0   1   2   3   4   5   6   7   8   9   10  11  12  13  14  15
 *  Pattern:      A   B   C   A   B   C   N   O   A   B   C   A   B   C   A ...
 *   Return: -1   0   0   0   1   2   3   0   0   1   2   3   4   5   6   4 ...   CAN BE READ AS NUM OF CHARS MATCHED
 *     Read:  ^ -1 can be interpreted as invalid number of chars matched but exploited for simplicity in the main algor.
 *     Read:      ^ 'A' is the first character of the pattern, there is no prefix ending before itself, to match.
 *     Read:          ^   ^ 'B' and 'C' cannot be matched with any prefix which are just 'A' and 'AB' respectively.
 *     Read:                  ^ can be matched with an earlier prefix, 'A'. So we store 1, the number of chars matched.
 *                            Realise 1 can also be interpreted as the index of the next character to match against!
 *     Read:                      ^   ^ Similarly, continue matching
 *     Read:                               ^  ^ No matches, so 0
 *     Read:                                      ^   ^   ^   ^   ^   ^   Match with prefix, "ABCABC", until 6th char
 *                                                                        of pattern string.
 *     Read:                                                              ^ where the magic happens, we can't match 'N'
 *                                                                        at position 7 with 'A' at position 15, but
 *                                                                        we know "ABC" exists as an earlier sub-pattern
 *                                                                        from 1st to 3rd and start matching the 4th
 *                                                                        char onwards.
 * <p>
 * Illustration of main logic:
 * Pattern: ABABAB
 * String : ABABCABABABAB
 * <p>
 *        A  B  A  B  C  A  B  A  B  A  B  A  B
 * Read:  ^    to  ^ Continue matching where possible, leading to 1st 4 characters matched.
 *        unable to match Pattern[4]. But notice that last two characters
 *        form a sub-pattern with the 1st 2, Maybe Pattern[2] == 'C' and we can 're-use' "AB"
 * Read:        ^     ^ check if Pattern[2] == 'C'
 * Read:              Turns out no. No previously identified sub-pattern with 'C'. Restart matching Pattern.
 * Read:                 ^              ^ Found complete match! But rather than restart, notice that last 4 characters
 * Read:                 of "ABABAB" form a prefix sub-pattern of Pattern, which is "ABAB", so,
 * Read:                       ^  reuse  ^     ^ then match 5th and 6th char of pattern which happens to be "AB"
 */
public class KMP {
    /**
     * Captures the longest prefix which is also a suffix for some substring ending at each index, starting from 0.
     * Does this by tracking the number of characters (of the prefix and suffix) matched.
     *
     * @param pattern to search
     * @return an array of indices
     */
    private static int[] getPrefixTable(String pattern) {
        // 1-indexed implementation
        int len = pattern.length();
        int[] numCharsMatched = new int[len + 1];
        numCharsMatched[0] = -1;
        numCharsMatched[1] = 0; // 1st character has no prefix to match with

        int currPrefixMatched = 0; // num of chars of prefix pattern currently matched
        int pos = 2; // Starting from the 2nd character
        while (pos <= len) {
            if (pattern.charAt(pos - 1) == pattern.charAt(currPrefixMatched)) {
                currPrefixMatched += 1;
                // note, the line below can also be interpreted as the index of the next char to match
                numCharsMatched[pos] = currPrefixMatched;
                pos += 1;
            } else if (currPrefixMatched > 0) {
                // go back to a previous known match and try to match again
                currPrefixMatched = numCharsMatched[currPrefixMatched];
            } else {
                // unable to match, time to move on
                numCharsMatched[pos] = 0;
                pos += 1;
            }
        }
        return numCharsMatched;
    }

    /**
     * Main logic of KMP. Iterate the sequence, looking for patterns. If a mismatch is found, resume matching from
     * a previously identified sub-pattern, if possible. Here we assume length of pattern is at least one.
     * @param sequence to search against
     * @param pattern  to search for
     * @return start indices of all occurrences of pattern found
     */
    public static List<Integer> findOccurrences(String sequence, String pattern) {
        int sLen = sequence.length();
        int pLen = pattern.length();
        int[] prefixTable = getPrefixTable(pattern);
        List<Integer> indicesFound = new ArrayList<>();

        int sTrav = 0;
        int pTrav = 0;

        while (sTrav < sLen) {
            if (pattern.charAt(pTrav) == sequence.charAt(sTrav)) {
                pTrav += 1;
                sTrav += 1;
                if (pTrav == pLen) { // matched a complete pattern string
                    indicesFound.add(sTrav - pLen); // start index of this occurrence
                    // recall the number of chars matched in p can be read as the index of the next char in p to match
                    pTrav = prefixTable[pTrav]; // start matching from a repeated sub-pattern, if possible
                }
            } else {
                pTrav = prefixTable[pTrav];
                if (pTrav < 0) { // move on; using -1 trick
                    pTrav += 1;
                    sTrav += 1;
                }
                // ALTERNATIVELY
                // if pTrav == 0 i.e. nothing matched, move on
                //    sTrav += 1
                //    continue
                //
                // pTrav = prefixTable[pTrav]
            }
        }
        return indicesFound;
    }
}
