package test.randomTests.owen.union_find.matrixToListConverter;

import org.junit.Test;

import src.algorithms.util.matrixToListConverter;
import src.algorithms.util.matrixToListConverter.InvalidMatrixException;

import java.util.*;

public class MatrixConverterTest {

    @Test
    public void test_convertArray_shouldReturnAccurateRepresentation() {
        // empty matrix
        int[][] firstMatrix = new int[0][0];
        ArrayList<Integer>[] firstList = new ArrayList[0];
        ArrayList<Integer>[] firstResult = matrixToListConverter.convert(firstMatrix);

        // symmetric matrix (undirected graph)
        int[][] secondMatrix = {
                {0, 1, 1},
                {1, 0, 1},
                {1, 1, 0},
        };
        ArrayList<Integer>[] secondList = new ArrayList[3];
        for (int i = 0; i < 3; i++) { secondList[i] = new ArrayList<>(); }
        Collections.addAll(secondList[0], 1, 2);
        Collections.addAll(secondList[1], 0, 2);
        Collections.addAll(secondList[2], 0, 1);
        ArrayList<Integer>[] secondResult = matrixToListConverter.convert(secondMatrix);

        //asymmetric matrix (directed graph)
        int[][] thirdMatrix = {
                {0, 0, 1},
                {1, 0, 0},
                {1, 1, 1},
        };
        ArrayList<Integer>[] thirdList = new ArrayList[3];
        for (int i = 0; i < 3; i++) { thirdList[i] = new ArrayList<>(); }
        Collections.addAll(thirdList[0], 2);
        Collections.addAll(thirdList[1], 0);
        Collections.addAll(thirdList[2], 0, 1, 2);
        ArrayList<Integer>[] thirdResult = matrixToListConverter.convert(thirdMatrix);

        //invalid matrix (n x m graph, n != m)
        int[][] fourthMatrix = {
                {0, 0},
                {1, 0},
                {1, 1},
        };
        ArrayList<Integer>[] fourthList = new ArrayList[3];

        assert Arrays.equals(firstList, firstResult);
        for (int i = 0; i < 1; i++) { assert secondList[i].equals(secondResult[i]); }
        for (int i = 0; i < 1; i++) { assert thirdList[i].equals(thirdResult[i]); }
        try {
            matrixToListConverter.convert(fourthMatrix);
        } catch (InvalidMatrixException error) {
            assert error.getMessage() == "The matrix must be N x N, not N x M!";
        }

    }
}
