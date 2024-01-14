package algorithms.patternFinding;

import java.util.List;

/**
 * Runs the KMP algorithm.
 */
public class RunKMP {
    //\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\
    //////////////////////////////////////////   This section is for user input   \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    private static String seq = "abclaabcabcabc";
    private static String pattern = "abc";

    //\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\

    public static void main(String[] args) {
        List<Integer> indices = KMP.findOccurrences(seq, pattern);
        display(indices);
        prettyPrint(seq, pattern, indices);
    }

    /**
     * Prints the string representation of the List.
     * @param arr the given List.
     */
    public static void display(List<Integer> arr) {
        StringBuilder toDisplay = new StringBuilder("[");
        for (int num : arr) {
            toDisplay.append(String.format("%d ", num));
        }
        toDisplay = toDisplay.replace(toDisplay.length() - 1, toDisplay.length(), "]");
        System.out.println(toDisplay);
    }

    /**
     * Pretty prints the sequence and pattern.
     * @param seq the given string sequence.
     * @param pattern the given string pattern.
     * @param indices TODO.
     */
    public static void prettyPrint(String seq, String pattern, List<Integer> indices) {
        StringBuilder prettySeq = new StringBuilder(" Sequence: ");
        StringBuilder prettyPtr = new StringBuilder("Start Pts: ");
        int currPos = 0;
        for (int i = 0; i < seq.length(); i++) {
            char curr = seq.charAt(i);
            prettySeq.append(String.format("%c  ", curr));

            if (currPos < indices.size()) {
                int currIdx = indices.get(currPos);
                if (currIdx == i) {
                    prettyPtr.append("^  ");
                    currPos++;
                } else {
                    prettyPtr.append("   ");
                }
            }
        }
        System.out.println("  Pattern: " + pattern);
        System.out.println(prettySeq);
        System.out.println(prettyPtr);
    }
}
