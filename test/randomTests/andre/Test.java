package test.randomTests.andre;

import src.dataStructures.heap.MaxHeap;
import java.util.*;

/**
 * Basic testing of MaxHeap structure
 */
public class Test {
    public static void main(String[] args) {
        MaxHeap<Integer> heap = new MaxHeap<>();
        heap.offer(1);
        heap.offer(2);
        heap.offer(9);
        heap.offer(4);
        heap.offer(5);
        System.out.println(heap);

        System.out.println("Peek: " + heap.peek());
        System.out.println("Poll: " + heap.poll());
        System.out.println("Peek: " + heap.peek());

        heap.offer(6);

        System.out.println("Peek: " + heap.peek());
        System.out.println("Poll: " + heap.poll());
        System.out.println("Poll: " + heap.poll());
        System.out.println(heap);

        heap.heapify(new ArrayList<>(Arrays.asList(5,4,6,7,2,1,9,8,0,3)));
        System.out.println(heap);
        heap.remove(10);
        heap.remove(5);
        System.out.println(heap);
    }
}

