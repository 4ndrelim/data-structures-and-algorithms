package algorithms.minimumSpanningTree.kruskals;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Very ugly use of java generics - will refactor one day..
 * Utilize union find to check for cycles
 */
public class Kruskal {
    private class Node {
        private int size;
        private Node parent;

        public Node() {
            this.size = 1;
            this.parent = this;
        }

        public Node findParent() {
            Node trav = this;
            while (trav.parent != trav) {
                Node tmp = trav.parent;
                trav.parent = trav.parent.parent; // inverse ackermann (close enuf)
                trav = tmp;
            }
            return trav;
        }

        /**
         * Make child a child of this node
         *
         * @param child
         */
        public void makeChild(Node child) {
            this.size += child.size;
            child.parent = this;
        }
    }

    /**
     * TODO documentation.
     *
     * @param adjM the adjacency matrix.
     * @return TODO return.
     */
    public int minCostConnectPoints(int[][] adjM) {
        int v = adjM.length;
        Node[] nodesMapping = new Node[v];
        for (int i = 0; i < v; i++) {
            nodesMapping[i] = new Node();
        }

        PriorityQueue<Pair<Integer, Pair<Integer, Integer>>> heap =
            new PriorityQueue<>(Comparator.comparingInt(Pair::getKey));

        for (int i = 0; i < v; i++) {
            for (int j = i + 1; j < v; j++) {
                Pair<Integer, Integer> edge = new Pair<>(i, j);
                if (adjM[i][j] != 0) {
                    heap.add(new Pair<>(adjM[i][j], edge));
                }
            }
        }

        int ans = 0;
        while (!heap.isEmpty()) {
            Pair<Integer, Pair<Integer, Integer>> curr = heap.poll();
            Pair<Integer, Integer> edge = curr.getValue();
            Node fr = nodesMapping[edge.getKey()];
            Node to = nodesMapping[edge.getValue()];
            Node p1 = fr.findParent();
            Node p2 = to.findParent();
            if (p1 != p2) {
                ans += curr.getKey();
                if (p1.size >= p2.size) {
                    p1.makeChild(p2);
                } else {
                    p2.makeChild(p1);
                }
            }

        }
        return ans;
    }
}
