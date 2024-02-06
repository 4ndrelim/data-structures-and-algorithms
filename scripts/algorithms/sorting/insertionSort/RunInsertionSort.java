package algorithms.sorting.insertionSort;

/**
 * Script to run Insertion Sort.
 */
public class RunInsertionSort {

    //\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//
    //////////////////////////////////////////   This section is for user input   \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    private static int[] toSort =
        new int[] {3, 4, 2, 65, 76, 93, 22, 1, 5, 7, 88, 54, 44, 7, 5, 6, 2, 64, 43, 22, 27, 33, 59, 64, 76, 99, 37, 7};

    //\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//

    public static void main(String[] args) {
        int[] sorted = InsertionSort.sort(toSort);
        display(sorted);
    }

    /**
     * Prints the string representation of the array.
     *
     * @param arr the given array.
     */
    public static void display(int[] arr) {
        StringBuilder toDisplay = new StringBuilder("[");
        for (int num : arr) {
            toDisplay.append(String.format("%d ", num));
        }
        toDisplay = toDisplay.replace(toDisplay.length() - 1, toDisplay.length(), "]");
        System.out.println(toDisplay);
    }
}
