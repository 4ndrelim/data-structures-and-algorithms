import java.util.*;

/**
 * Basic testing of Heap structure
 */
public class HeapTest {
  public static void main(String[] args) {
    Heap<Integer> heap = new Heap<>();
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
