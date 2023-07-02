package test.random_tests.owen.union_find.quick_find.simplified;

import src.data_structures.union_find.quick_find.simplified.QuickFind;

import java.util.List;

/**
 * Basic testing.
 */
public class Test {
    public static void main(String[] args) {
        QuickFind qf = new QuickFind(10);

        qf.union(1,9);
        qf.union(4,6);
        qf.union(1,4);

        qf.union(2,8);
        qf.union(5,7);
        qf.union(2,7);

        List<Integer> comp1 = qf.retrieveComponent(1);
        for (int i = 0; i < comp1.size(); i++) {
            System.out.print(String.format("%d, ", comp1.get(i)));
        }
        System.out.println("\nTesting next component:");
        List<Integer> comp2 = qf.retrieveComponent(2);
        for (int i = 0; i < comp2.size(); i++) {
            System.out.print(String.format("%d, ", comp2.get(i)));
        }
        System.out.println("\nTesting next component:");
        List<Integer> comp3 = qf.retrieveComponent(3);
        for (int i = 0; i < comp3.size(); i++) {
            System.out.print(String.format("%d, ", comp3.get(i)));
        }
        System.out.println();
    }
}
