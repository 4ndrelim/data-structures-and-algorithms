/**
 * Basic Testing
 */

public class Test {
  public static void main(String[] args) {
    LinkedList<Integer> LL = new LinkedList<>(); 
    System.out.println(LL);
    LL.insert(6, 0);
    LL.insert(7,1);
    LL.insert(10, 2);
    LL.insertEnd(3);
    LL.insertFront(0);
    System.out.println(LL);
    LL.insert(8, 6);

    System.out.println(LL.search(7));
    System.out.println(LL.search(8));
    System.out.println();
    System.out.println(LL.get(3));
    LL.remove(3);
    System.out.println(LL);

    LinkedList<Integer> NLL = new LinkedList<>();
    System.out.println(NLL.remove(0));
    System.out.println(NLL.insertFront(2));
    System.out.println(NLL.remove(0));
    NLL.insertEnd(3);
    NLL.insertEnd(4);
    System.out.println(NLL);
    System.out.println(NLL.delete(2));
    System.out.println(NLL.delete(3));
    System.out.println(NLL);
  }
}
