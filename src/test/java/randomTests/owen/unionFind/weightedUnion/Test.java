package randomTests.owen.unionFind.weightedUnion;

import java.util.ArrayList;
import java.util.List;

import dataStructures.disjointSet.weightedUnion.Union;

/**
 * Some simple tests.
 */
public class Test {
  /**
   * Runs the custom test.
   *
   * @param args unused.
   */
  public static void main(String[] args) {
    List<String> lst = new ArrayList<>();
    lst.add("Dog");
    lst.add("Egg");
    lst.add("Carabiner");
    lst.add("Cat");
    lst.add("Potatoes");
    lst.add("Hamster");
    lst.add("Rope");
    lst.add("Fox");
    lst.add("Chalk");
    for (int i = 0; i < lst.size(); i++) {
      System.out.println("Added " + lst.get(i));
    }

    Union<String> wu = new Union<>(lst);
    wu.combine("Dog", "Cat");
    wu.combine("Fox", "Hamster");
    wu.combine("Fox", "Cat");
    System.out.println(wu.isSameComponent("Dog", "Fox")); // true
    System.out.println(wu.isSameComponent("Rope", "Fox")); // false

    wu.combine("Carabiner", "Rope");
    wu.combine("Chalk", "Rope");
    System.out.println(wu.isSameComponent("Rope", "Carabiner")); // true

    wu.combine("Egg", "Potatoes");
    System.out.println(wu.isSameComponent("Egg", "pot")); // duuhh.. + false

    wu.add("Coconut");
    wu.combine("Egg", "Coconut");
    System.out.println(wu.isSameComponent("Coconut", "Potatoes")); // true
  }
}
