package dataStructures.disjointSet.quickFind;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class DisjointSetTest {
    @Test
    public void construct_shouldCorrectlyInitializeEmpty() {
        DisjointSet<Integer> ds = new DisjointSet<>();
        Assert.assertEquals(ds.size(), 0);
    }

    @Test
    public void construct_shouldCorrectlyInitializeNonEmpty() {
        List<String> lst = Arrays.asList("andre", "chang xian", "jun neng", "kai ting", "shu heng");

        DisjointSet<String> ds = new DisjointSet<>(lst);
        Assert.assertEquals(ds.size(), 5);

        Assert.assertFalse(ds.find("andre", "kai ting"));
    }

    @Test
    public void find_shouldCorrectlyFindItself() {
        List<String> lst = Arrays.asList("andre", "chang xian", "jun neng");

        DisjointSet<String> ds = new DisjointSet<>(lst);
        Assert.assertTrue(ds.find("chang xian", "chang xian"));
    }

    @Test
    public void union_shouldCorrectlyUpdate() {
        List<String> lst = Arrays.asList("andre", "chang xian", "jun neng", "kai ting", "shu heng");

        DisjointSet<String> ds = new DisjointSet<>(lst);

        Assert.assertFalse(ds.find("andre", "kai ting"));

        ds.union("andre", "kai ting");
        Assert.assertTrue(ds.find("andre", "kai ting"));
        Assert.assertFalse(ds.find("andre", "chang xian"));
        Assert.assertFalse(ds.find("andre", "shu heng"));
        Assert.assertFalse(ds.find("jun neng", "kai ting"));
    }

    @Test
    public void retrieve_shouldCorrectlyRetrieveComponents() {
        List<String> lst = Arrays.asList("andre", "chang xian", "jun neng", "kai ting", "shu heng", "seth", "gilbert");

        DisjointSet<String> ds = new DisjointSet<>(lst);
        ds.union("andre", "kai ting");
        ds.union("chang xian", "jun neng");
        ds.union("jun neng", "gilbert");
        ds.union("chang xian", "seth");

        List<String> resultA = ds.retrieveFromSameComponent("kai ting");
        Collections.sort(resultA);
        List<String> expectedA = Arrays.asList("andre", "kai ting");
        Collections.sort(expectedA);
        Assert.assertEquals(expectedA, resultA);

        List<String> resultB = ds.retrieveFromSameComponent("gilbert");
        Collections.sort(resultB);
        List<String> expectedB = Arrays.asList("chang xian", "jun neng", "seth", "gilbert");
        Collections.sort(expectedB);
        Assert.assertEquals(expectedB, resultB);

        List<String> resultC = ds.retrieveFromSameComponent("shu heng");
        Collections.sort(resultC);
        List<String> expectedC = Arrays.asList("shu heng");
        Collections.sort(expectedC);
        Assert.assertEquals(expectedC, resultC);
    }
}
