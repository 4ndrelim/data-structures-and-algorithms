import java.util.*;

/**
 * Very ugly use of java generics - will refactor one day..
 * Utilize union find to check for cycles
 */
public class kruskal {
    private class Node {
        int size;
        Node parent;

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
         * @param child
         */
        public void makeChild(Node child) {
            this.size += child.size;
            child.parent = this;
        }
    }

    public int minCostConnectPoints(int[][] adjM) {
        int v = adjM.length;
        Node[] nodesMapping = new Node[v];
        for (int i = 0; i < v; i++) {
            nodesMapping[i] = new Node();
        }

        PriorityQueue<Pair<Integer, Pair<Integer, Integer>>> heap = new PriorityQueue<>(
            (a,b) -> a.key - b.key
        );
        for (int i = 0; i < v; i++) {
            for (int j = i + 1; j < v; j++) {
                Pair<Integer, Integer> edge = new Pair<>(i, j);
                if (adjM[i][j] != 0) {
                    heap.add(new Pair<Integer, Pair<Integer, Integer>>(adjM[i][j], edge));
                }
            }
        }

        int ans = 0;
        while (heap.size() != 0) {
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
