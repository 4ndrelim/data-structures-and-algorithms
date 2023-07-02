package src.algorithms.quickSort.partitioning;

/**
 * Static class for 3-way in-place partitioning.
 */
public class ThreeWay {
    public static void partition(int[] arr) {
        // ___<___ ___=___ ___IP___ ___>___
        //         ^     ^ ^      ^

        // array of length 1
        if (arr.length == 1) {
            return;
        }
        // pick a pivot
        int pivotIdx = 0; 
        int pivot = arr[pivotIdx];

        int eqStart = 0;
        int eqEnd = 0;
        int ipStart = 1;
        int ipEnd = arr.length-1;

        while (ipStart <= ipEnd) {
            int curr = arr[ipStart];
            // case 1: < pivot
            if (curr < pivot) {
                // do a swap with eqStart
                int tmp = arr[eqStart];
                arr[eqStart] = curr;
                arr[ipStart] = tmp;

                // increment eqStart, ipStart, and eqEnd 
                eqStart++;
                ipStart++;
                eqEnd++;
            // case 2: = pivot
            } else if (curr == pivot) {
                // simply increment eqEnd and ipStart
                eqEnd++;
                ipStart++;
            // case 3: > pivot
            } else {
                // do a swap with ipEnd
                int tmp = arr[ipEnd];
                arr[ipEnd] = arr[ipStart];
                arr[ipStart] = tmp;
                // decrement ipEnd
                ipEnd--;
            }
        }

    }
}