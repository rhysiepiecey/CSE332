package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
import cse332.datastructures.trees.BinarySearchTree;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.misc.DeletelessDictionary;
import cse332.interfaces.misc.SimpleIterator;
import cse332.interfaces.worklists.WorkList;
import datastructures.worklists.ArrayStack;
import datastructures.worklists.ListFIFOQueue;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * 1. The list is typically not sorted.
 * 2. Add new items to the front of the list.
 * 3. Whenever find or insert is called on an existing key, move it
 * to the front of the list. This means you remove the node from its
 * current position and make it the first node in the list.
 * 4. You need to implement an iterator. The iterator SHOULD NOT move
 * elements to the front.  The iterator should return elements in
 * the order they are stored in the list, starting with the first
 * element in the list. When implementing your iterator, you should
 * NOT copy every item to another dictionary/list and return that
 * dictionary/list's iterator.
 */
public class MoveToFrontList<K, V> extends DeletelessDictionary<K, V> {

    private Node current;

    public MoveToFrontList() {
        this.size = 0;
    }
    @Override
    public V insert(K key, V value) {
        if(key == null || value == null) {
            throw new NoSuchElementException();
        }

        V currVal = this.find(key);
        if (current == null || currVal == null) {
            this.size++;
        } else {
            current = current.next;
        }
        Node prev = current;
        current = new Node(key, value);
        current.next = prev;
        return currVal;
    }

    @Override
    public V find(K key) {
        if (key == null) {
            throw new NoSuchElementException();
        }
        if (current == null) {
            return null;
        }
        Node front = current;
        if (front.key.equals(key)) {
            return front.value;
        } else {
            while (front.next != null) {
                if (front.next.key.equals(key)) {
                    Node temp = front.next;
                    front.next = front.next.next;
                    temp.next = current;
                    current = temp;
                    return current.value;
                } else {
                    front = front.next;
                }
            }
            return null;
        }
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public Iterator<Item<K, V>> iterator() {
        return new frontIterator();
    }
    private class frontIterator implements Iterator<Item<K, V>> {
        private Node currIt;

        public frontIterator() {
            currIt = current;
        }

        public boolean hasNext() {
            return currIt != null;
        }

        public Item<K, V> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Node item = currIt;
            currIt = currIt.next;
            return item;
        }
    }

    private class Node extends Item<K, V>{

        private Node next;

        private Node(K key, V value) {
            super(key, value);
            this.next = null;
        }
    }
    
}
