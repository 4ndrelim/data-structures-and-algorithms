package test.random_tests.owen.union_find.quick_find.generalised;

import src.data_structures.union_find.quick_find.generalised.QuickFind;

import java.util.ArrayList;
import java.util.List;

/**
 * Some simple tests
 */
public class Test {
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
        
        QuickFind<String> qf = new QuickFind<>(lst);
        qf.union("Dog", "Cat");
        qf.union("Fox", "Hamster");
        qf.union("Fox", "Cat");

        qf.union("Carabiner", "Rope");
        qf.union("Chalk", "Rope");

        qf.union("Egg", "Potatoes");

        List<String> comp1 = qf.retrieveComponent("Hamster");
        System.out.print("Comp1: ");
        for (int i = 0; i < comp1.size(); i++) {
            System.out.print(comp1.get(i) + ", ");
        }

        List<String> comp2 = qf.retrieveComponent("Chalk");
        System.out.print("\nComp2: ");
        for (int i = 0; i < comp2.size(); i++) {
            System.out.print(comp2.get(i) + ", ");
        }

        List<String> comp3 = qf.retrieveComponent("Egg");
        System.out.print("\nComp3: ");
        for (int i = 0; i < comp3.size(); i++) {
            System.out.print(comp3.get(i) + ", ");
        }

        qf.add("Coconut");
        qf.union("Egg", "Coconut");
        List<String> update = qf.retrieveComponent("Egg");
        System.out.print("\nUpdate Comp3: ");
        for (int i = 0; i < update.size(); i++) {
            System.out.print(update.get(i) + ", ");
        }
    }
}