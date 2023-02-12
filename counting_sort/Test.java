// import java.util.*;
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

        int[] sorted1 = CountingSort.sort(test1);
        print(sorted1);
        // Arrays.sort(test1);
        // print(test1);
        int[] sorted2 = CountingSort.sort(test2);
        print(sorted2);
        // Arrays.sort(test2);
        // print(test2);
        int[] sorted3 = CountingSort.sort(test3);
        print(sorted3);
        // Arrays.sort(test3);
        // print(test3);
    }
}
