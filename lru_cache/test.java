/**
 * Basic testing
 */

 public class test {
    public static void main(String[] args) {
        LRU lru = new LRU<Integer, String>(4);
        lru.update(5, "abc");
        lru.update(2, "def");
        lru.update(3, "ghi");
        lru.update(1, "jkl");
        lru.print(); // 5->2->3->1
        lru.update(2, "lmn");
        lru.print(); // 5->3->1->2
        lru.update(7, "opq"); // 5 should be evicted
        lru.update(8, "rst"); // 3 should be evicted
        lru.print(); // 1->2->7->8
    }
 }