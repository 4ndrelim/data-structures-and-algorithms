package dataStructures.lruCache;

import java.util.HashMap;

public class LRU { // doublyLinkedList
    int key;
    int val;
    LRU next;
    LRU prev;
}

class LRUCache {
    LRU dllHead;
    LRU dllTail;
    HashMap<Integer, LRU> keyToNode = new HashMap();
    int capacity;
    int lengthOfList = 0;

    public LRUCache(int capacity) {
        this.capacity = capacity;

        dllHead = new LRU();
        dllHead.key = -1;
        dllHead.val = -1;

        dllTail = new LRU();
        dllTail.key = -1;
        dllTail.val = -1;

        dllHead.prev = null;
        dllHead.next = dllTail;
        dllTail.prev = dllHead;
        dllTail.next = null;
    }
    
    public int get(int key) {
        if(!keyToNode.containsKey(key)) return -1;

        LRU temp = keyToNode.get(key);
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

        LRU newlyCached;

        if(!keyToNode.containsKey(key)) {
            newlyCached = new LRU();
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
