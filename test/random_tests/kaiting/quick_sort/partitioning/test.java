package test.random_tests.kaiting.quick_sort.partitioning;

import src.algorithms.quick_sort.partitioning.ThreeWay;

/*
 * Basic testing for partitioning
 */
public class test {
    public static void main(String[] args) {
        int[] arr1 = new int[] {7};
        int[] arr2 = new int[] {4,3,4,4,2,5,6,7,4,4,4,4,1,2,1,6,8,1,9,2,9,3,7,1,10,0};
        System.out.println(String.format("Pivot is: %d", arr1[0]));
        ThreeWay.partition(arr1);
        printArray(arr1);
        System.out.println(String.format("Pivot is: %d", arr2[0]));
        ThreeWay.partition(arr2);
        printArray(arr2);
    }

    private static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(String.format("%d ", arr[i]));
        }
        System.out.println();
    }
}