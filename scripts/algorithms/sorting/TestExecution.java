import src.algorithms.patternFinding.KMP;

import java.util.List;

public class TestExecution {
    public static void main(String[] args) {
        // Your code goes here
        System.out.println("Wassup");

        String seq = "aaaaaaaaaaaaa";
        String pattern = "aa";
        List<Integer> indices = KMP.findOccurrences(seq, pattern);
        for (int i = 0; i < indices.size(); i++) {
            System.out.print(indices.get(i));
            System.out.print(" ");
        }
    }
}