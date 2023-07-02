package src.dataStructures.disjointSet.quickFind.simplified;

import java.util.List;
import java.util.ArrayList;

/**
 * Simplified implementation of quick-find where the objects are integers range from 1-n.
 */
public class QuickFind {
    private int[] identity;
    private int size;

    public QuickFind(int size) {
        // we will ignore index 0. So index 1 corresponds to element 1, 
        // index 2 corresponds with element 2 and so on..
        this.identity = new int[size + 1];
        this.size = size;
        for (int i = 0; i < size + 1; i++) {
            this.identity[i] = i;
        }
    }

    /**
     * Forms a union between elements of two different groups merging all the elements with the same identity as the former element to that of the latter element.
     * @param fr identity of the first element
     * @param to identity of the second element
     */
    public void union(int fr, int to) {
        int updateComp = this.identity[fr];
        for (int i = 1; i < this.size + 1; i++) {
            if (this.identity[i] == updateComp) {
                this.identity[i] = this.identity[to]; // updates element i's identity to that of <to>
            }
        }
    }

    /**
     * Retrieves all the elements in the component whose identity is the same as that of the given element.
     * @param element whose component we would lie to find.
     * @return all elements in the component
     */
    public List<Integer> retrieveComponent(int element) {
        int id = this.identity[element];
        List<Integer> ret = new ArrayList<>();
        for (int i = 1; i < this.size + 1; i++) {
            if (this.identity[i] == id) {
                ret.add(i);
            }
        }
        return ret;
    }
}
