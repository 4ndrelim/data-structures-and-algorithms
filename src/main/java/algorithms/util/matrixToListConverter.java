package algorithms.util;
import java.util.*;
/**
 * Supports method to convert an Adjacency Matrix into an Adjacency list
 *
 * Importance: In some cases, an algorithm's efficiency depends on the use of an Adjacency list over a Matrix
 * This is because an Adjacency list supports certain functions more efficiently, such as:
 *  - O(1) time for adding vertices as opposed to O(V^2) in a matrix
 *  - O(V + E) space complexity as opposed to O(V^2) <- Much more efficient for sparse graphs
 *  - Much easier to iterate over all edges/compute all neighbours
 *
 * Adjacency Matrix in the form of a 2D Integer Array int[][].
 * Example matrix: [[0, 1, 1], <- Matrix[i][j] == 1 iff Vertex i shares an edge with Vertex j
 *                  [1, 0, 0]
 *                  [1, 0, 0]]
 *
 * Adjacency list in the form of an array of lists: ArrayList<Integer>[] , where array[i] is a list of neighbour indexes
 * Example list: [(1, 2), (1), (1)] <- Here, square brackets [] denote arrays and round brackets () denote lists
 *               (This is the list representation of the example matrix above,
 *               where Vertex 0 shares an edge with Vertex 1 and 2)
 *
 * While a list implementation is used here, the conventional implementation of adjacency lists utilizes linked lists or nodes
 * To implement this, we can create a private node class to hold our original object and whatever data that comes
 * alongside (eg weights), as well as additional node attribute(s) that serve as pointers to next (or prev) node.
 * Then, initialize an array of V nodes, where the ith node represents a list of all neighbouring vertices.
 * Neighbouring vertices can be added simply by chaining a new node to the ith node using pointers
 *
 * Note: in the event where integers aren't used to denote elements or perhaps are sparse (eg 0, 1, 123456545),
 * one can consider using a dictionary where the key is the element and value is the head node.
 */

public class matrixToListConverter {

    public static List<Integer>[] convert(int[][] adjM) throws InvalidMatrixException {

        int numOfVertices = adjM.length;
        // Handling empty matrix case so no index out of bounds error
        if (Arrays.equals(adjM, new int[0][0])) { return new ArrayList[0]; }
        // Handling invalid matrix case (Graph matrix must be nxn)
        if (numOfVertices != adjM[0].length) {
            throw new InvalidMatrixException("The matrix must be N x N, not N x M!");
        }

        ArrayList<Integer>[] adjL = new ArrayList[numOfVertices];

        for (int i = 0; i < numOfVertices; i++) {
            adjL[i] = new ArrayList<>();
            for (int j = 0; j < numOfVertices; j++) {
                if (adjM[i][j] == 1) {
                    adjL[i].add(j);
                }
            }
        }
        return adjL;
    }

    public static class InvalidMatrixException extends RuntimeException {
        public InvalidMatrixException(String message) {
            super(message);
        }
    }
}
