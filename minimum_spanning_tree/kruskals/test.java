import java.util.*;
/**
 * Basic testing
 * Motivating Example: Minimum Cost to Connect All Points
 * 
          A -9- C -2- E 
         /     /  \     \ 
        3     4    7     2
       /     /      \  /
      F -1- B  --5--  D 
*/

public class test {
    public static void main(String[] args) {
        int[][] adjM = new int[6][6];
        adjM[0][2] = 9;
        adjM[2][0] = 9;

        adjM[0][5] = 3;
        adjM[5][0] = 3;

        adjM[1][5] = 1;
        adjM[5][1] = 1;

        adjM[1][3] = 5;
        adjM[3][1] = 5;

        adjM[1][2] = 4;
        adjM[2][1] = 4;

        adjM[2][3] = 7;
        adjM[3][2] = 7;

        adjM[2][4] = 2;
        adjM[4][2] = 2;

        adjM[3][4] = 2;
        adjM[4][3] = 2;
        kruskal kruskalAlgor = new kruskal();
        System.out.println(kruskalAlgor.minCostConnectPoints(adjM));
    }
}