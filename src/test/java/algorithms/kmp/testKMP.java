package test.algorithms.kmp;

import src.algorithms.patternFinding.KMP;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class testKMP {
    @Test
    public void test_findOccurrences_shouldReturnStartIndices() {
        String seq = "abclaabcabcabc";
        String pattern = "abc";

        List<Integer> indices = KMP.findOccurrences(seq, pattern);
        List<Integer> expected = Arrays.asList(0, 5, 8, 11);
        Assert.assertEquals(expected, indices);
    }

    @Test
    public void testEmptySequence_findOccurrences_shouldReturnStartIndices() {
        String seq = "";
        String pattern = "a";

        List<Integer> indices = KMP.findOccurrences(seq, pattern);
        List<Integer> expected = new ArrayList<>();
        Assert.assertEquals(expected, indices);
    }

    @Test
    public void testNoOccurence_findOccurrences_shouldReturnStartIndices() {
        String seq = "abcabcabc";
        String pattern = "noway";

        List<Integer> indices = KMP.findOccurrences(seq, pattern);
        List<Integer> expected = new ArrayList<>();
        Assert.assertEquals(expected, indices);
    }

    @Test
    public void testRepeatPatternOnly_findOccurrences_shouldReturnStartIndices() {
        String seq = "abcabcabcabcabc";
        String pattern = "abc";

        List<Integer> indices = KMP.findOccurrences(seq, pattern);
        List<Integer> expected = Arrays.asList(0, 3, 6, 9, 12);
        Assert.assertEquals(expected, indices);
    }

    @Test
    public void testAllSame_findOccurrences_shouldReturnStartIndices() {
        String seq = "aaaaaaaaaaaaa";
        String pattern = "aa";

        List<Integer> indices = KMP.findOccurrences(seq, pattern);
        List<Integer> expected = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11);
        Assert.assertEquals(expected, indices);
    }
}
