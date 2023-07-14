package test.algorithms.kmp;

import src.algorithms.patternFinding.KMP;

import java.util.Arrays;
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
        Assert.assertEquals(expected, KMP.findOccurrences(seq, pattern));
    }
}
