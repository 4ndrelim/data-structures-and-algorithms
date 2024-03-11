package dataStructures.trie;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class TrieTest {
    @Test
    public void search_shouldFindWordIfExists() {
        Trie trie = new Trie();
        trie.insert("cs2040s");
        trie.insert("cs3230");
        trie.insert("cs4231");
        trie.insert("cs4234");

        Assert.assertTrue(trie.search("cs2040s"));
        Assert.assertTrue(trie.search("cs4231"));
        Assert.assertFalse(trie.search("cs2040"));
    }

    @Test
    public void delete_shouldNotFindDeletedWord() {
        Trie trie = new Trie();
        trie.insert("cs2040s");
        trie.insert("cs3230");
        trie.insert("cs4231");
        trie.insert("cs4234");

        Assert.assertTrue(trie.search("cs4231"));
        trie.delete("cs4231");
        Assert.assertFalse(trie.search("cs4231"));
    }

    @Test
    public void deleteAndPrune_shouldNotFindDeletedWord() {
        Trie trie = new Trie();
        trie.insert("cs2040s");
        trie.insert("cs3230");
        trie.insert("cs4231");
        trie.insert("cs4234");

        Assert.assertTrue(trie.search("cs4231"));
        trie.deleteAndPrune("cs4231");
        Assert.assertFalse(trie.search("cs4231"));
    }

    @Test
    public void deleteAndPrune_shouldPrune() {
        Trie trie = new Trie();
        trie.insert("cs2040s");
        trie.insert("cs3230");
        trie.insert("cs4231");
        trie.insert("cs4234");
        trie.insert("cs4261");

        trie.deleteAndPrune("cs4261");
        Assert.assertFalse(trie.checkNodeExistsAtPosition("cs4261", 5));
        Assert.assertTrue(trie.checkNodeExistsAtPosition("cs4261", 4)); // because of presence of other "cs42"s
        trie.deleteAndPrune("cs3230");
        Assert.assertFalse(trie.checkNodeExistsAtPosition("cs3230", 3));
        Assert.assertTrue(trie.checkNodeExistsAtPosition("cs3230", 2));
    }

    @Test
    public void deleteAndPrune_shouldNotDeletePrefixWords() {
        Trie trie = new Trie();
        trie.insert("art");
        trie.insert("artist");
        trie.insert("artistic");
        trie.insert("artistically");

        Assert.assertTrue(trie.search("artistically"));
        trie.deleteAndPrune("artistically");
        Assert.assertTrue(trie.search("artistic")); // should not be pruned away
        Assert.assertTrue(trie.search("art")); // should not be pruned away
    }

    @Test
    public void wordsWithPrefix_shouldDisplayCurrentWordsThatMatches() {
        Trie trie = new Trie();
        trie.insert("biology");
        trie.insert("biodiversity");
        trie.insert("bioinformatics");
        trie.insert("biotechnology");
        trie.insert("biotic");
        trie.insert("bicycle");
        trie.insert("bijection");
        trie.insert("symbiotic");
        trie.insert("submarine");
        trie.insert("submerged");

        List<String> resultA = trie.wordsWithPrefix("bio");
        Collections.sort(resultA);
        List<String> expectedA = Arrays.asList("biology", "biodiversity", "bioinformatics", "biotechnology", "biotic");
        Collections.sort(expectedA);
        Assert.assertEquals(expectedA, resultA);

        List<String> resultB = trie.wordsWithPrefix("sub");
        Collections.sort(resultB);
        List<String> expectedB = Arrays.asList("submarine", "submerged");
        Collections.sort(expectedB);
        Assert.assertEquals(expectedB, resultB);

        trie.delete("biodiversity");
        trie.delete("biotic");

        List<String> resultC = trie.wordsWithPrefix("bio");
        Collections.sort(resultC);
        List<String> expectedC = Arrays.asList("biology", "bioinformatics", "biotechnology");
        Collections.sort(expectedC);
        Assert.assertEquals(expectedC, resultC);

        List<String> resultD = trie.wordsWithPrefix("sy");
        Collections.sort(resultD);
        List<String> expectedD = Arrays.asList("symbiotic");
        Collections.sort(expectedD);
        Assert.assertEquals(expectedD, resultD);

        trie.delete("symbiotic");

        List<String> resultE = trie.wordsWithPrefix("sy");
        Collections.sort(resultE);
        List<String> expectedE = Arrays.asList();
        Collections.sort(expectedE);
        Assert.assertEquals(expectedE, resultE);
    }

    @Test
    public void getAllWords_shouldDisplayCurrentWords() {
        Trie trie = new Trie();
        trie.insert("eldric");
        trie.insert("seth");
        trie.insert("gilbert");
        trie.insert("KAI TING"); // converted to lower-case
        trie.insert("daniel");
        trie.insert("andre");

        List<String> resultA = trie.getAllWords();
        Collections.sort(resultA);
        List<String> expectedA = Arrays.asList("eldric", "seth", "gilbert", "kai ting", "andre", "daniel");
        Collections.sort(expectedA);
        Assert.assertEquals(expectedA, resultA);

        trie.delete("daniel");
        trie.delete("eldric");
        List<String> resultB = trie.getAllWords();
        Collections.sort(resultB);
        List<String> expectedB = Arrays.asList("seth", "gilbert", "kai ting", "andre");
        Collections.sort(expectedB);
        Assert.assertEquals(expectedB, resultB);
    }
}
