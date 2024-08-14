package dataStructures.lruCache;

import java.util.HashMap;

public class doublyLinkedList {
    int key;
    int val;
    doublyLinkedList next;
    doublyLinkedList prev;
}

class LRUCache {
    doublyLinkedList dllHead;
    doublyLinkedList dllTail;
    HashMap<Integer, doublyLinkedList> keyToNode = new HashMap();
    int capacity;
    int lengthOfList = 0;

    public LRUCache(int capacity) {
        this.capacity = capacity;

        dllHead = new doublyLinkedList();
        dllHead.key = -1;
        dllHead.val = -1;

        dllTail = new doublyLinkedList();
        dllTail.key = -1;
        dllTail.val = -1;

        dllHead.prev = null;
        dllHead.next = dllTail;
        dllTail.prev = dllHead;
        dllTail.next = null;
    }
    
    public int get(int key) {
        if(!keyToNode.containsKey(key)) return -1;

        doublyLinkedList temp = keyToNode.get(key);
        temp.prev.next = temp.next;
        temp.next.prev = temp.prev;

        temp.next = dllHead.next;
        dllHead.next.prev = temp;
        temp.prev = dllHead;
        dllHead.next = temp;

        return keyToNode.get(key).val;
    }
    
    public void put(int key, int value) {
        boolean addingNewNode = true;

        doublyLinkedList newlyCached;

        if(!keyToNode.containsKey(key)) {
            newlyCached = new doublyLinkedList();
            newlyCached.key = key;
            newlyCached.val = value;
            keyToNode.put(key, newlyCached);
        }
        else {
            newlyCached = keyToNode.get(key);
            newlyCached.val = value;
            addingNewNode = false;

            newlyCached.prev.next = newlyCached.next;
            newlyCached.next.prev = newlyCached.prev;
        }

        newlyCached.next = dllHead.next;
        dllHead.next.prev = newlyCached;
        newlyCached.prev = dllHead;
        dllHead.next = newlyCached;

        if(addingNewNode) {
            if(lengthOfList == capacity) {
                keyToNode.remove(dllTail.prev.key);
                dllTail.prev.prev.next = dllTail;
                dllTail.prev = dllTail.prev.prev;
            }
            else {
                lengthOfList++;
            }
        }
    }
}
