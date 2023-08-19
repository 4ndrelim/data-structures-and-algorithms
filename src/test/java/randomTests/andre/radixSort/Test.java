package randomTests.andre.radixSort;

import algorithms.sorting.radixSort.RadixSort;

public class Test {
    private static void print(int[] arr) {
        for (int num : arr) {
            System.out.print(String.format("%d ", num));
        }
        System.out.println();
    }
    public static void main(String[] args) {
        int[] test1 = new int[] {2,3,4,1,2,5,6,7,10,15,20,13,1,2,15,12,20,21,12,11,5,7,23,30};
        int[] test2 = new int[] {9,1,2,8,7,3,4,6,5,5,9,8,7,6,5,4,3,2,1,1,2,3,4,5,6,7,8,9,10};
        int[] test3 = new int[] {9,8,7,6,5,4,3,2,1,1,2,3,4,5,6,7,8,9,100};

        RadixSort.radixSort(test1);
        print(test1);
        // Arrays.sort(test1);
        // print(test1);
        RadixSort.radixSort(test2);
        print(test2);
        // Arrays.sort(test2);
        // print(test2);
        RadixSort.radixSort(test3);
        print(test3);
        // Arrays.sort(test3);
        // print(test3);

        int[] test4 = new int[] {1 << 25 + 123, 1<<28 + 340230, 34954043, 5678, 987654, 121234, 1, 0, 20493943, 1 << 20 + 543298};
        // System.out.println(1 << 25 + 123);
        RadixSort.radixSort(test4);
        print(test4);
    }
}
