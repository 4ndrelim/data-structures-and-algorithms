package test.randomTests.andre.cyclicSort.simple;

import src.algorithms.sorting.cyclicSort.simple.CyclicSort;

public class Test {
    public static void main(String[] args) {
        int[] arr = {5, 6, 4, 7, 2, 1, 9, 3, 8, 0};
        CyclicSort.sort(arr);
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            System.out.print(" ");
        }

        System.out.println("\n\nTest find first non-negative: ");
        int[] arr1 = {0, 1, 2, 3, 4, 5, 6}; // 7
        int[] arr2 = {0, 0, 1, 2, 4, 5, 6, 8}; // 3
        int[] arr3 = {0, 6, 4, 2, 7, 4, 3, 9, 1, 10, 0, 5}; // 8
        int[] arr4 = {2, 3, 1, 5, 4 , 6, 7}; // 0
        System.out.println(CyclicSort.findMissing(arr1));
        System.out.println(CyclicSort.findMissing(arr2));
        System.out.println(CyclicSort.findMissing(arr3));
        System.out.println(CyclicSort.findMissing(arr4));
    }
}
