package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.FIFOWorkList;

import java.util.NoSuchElementException;


/**
 * See cse332/interfaces/worklists/FIFOWorkList.java
 * for method specifications.
 */
public class ListFIFOQueue<E> extends FIFOWorkList<E> {
    private ListNode front;
    private ListNode end;

    private int size;


    /**
     * creates the queue
     */
    public ListFIFOQueue() {
        front = null;
        end = null;
        size = 0;
    }

    /**
     * adds to the front of the queue
     * @param work the work to add to the worklist
     */
    @Override
    public void add(E work) {
        if(front == null) {
            front = new ListNode(work);
            end = front;
        } else {
            end.next = new ListNode(work);
            end = end.next;
        }
        size++;

    }

    /**
     * looks at the front of the queue
     * @throws NoSuchElementException if hasWork() is false
     * @return the front element of the queue
     *
     */
    @Override
    public E peek() {
        if(size == 0) {
            throw new NoSuchElementException();
        }
        return (E) front.getData();
    }

    /**
     * returns the front element in the queue and removes it
     * @throws NoSuchElementException if size is 0
     * @return the front element of the queue
     */
    @Override
    public E next() {
        if(size == 0) {
            throw new NoSuchElementException();
        }
        //return value
        //set front to the next value
        E value = this.peek();
        size--;
        if(size == 0) {
            front = null;
            end = front;
        } else {
             front = front.next;
        }

        return value;
    }

    /**
     * returns the size of the queue
     * @return the size of the queue
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * empties the queue
     */
    @Override
    public void clear() {
        front = null;
        end = null;
        size = 0;
    }

    class ListNode<E> {
        private E data;
        private ListNode next;
        private ListNode front;
        ListNode(E data) {
            this.data = data;
        }

        /**
         * creates an empty ListNode
         */
        ListNode() {}

        /**
         * creates a listNode with data and is connected to another listNode
         * @param data data inside of listNode
         * @param next the listNode it is connected to
         */
        ListNode(E data, ListNode next) {
            this.data = data;
            this.next = next;
        }

        /**
         *
         * @return data
         */
        public E getData() {
            return data;
        }

        /**
         *
         * @return ListNode next
         */
        public ListNode getNext() {
            return next;
        }

        /**
         *
         * @return the front listNode
         */
        public ListNode getFront() {
            return front;
        }

        /**
         * sets data for the listNode
         * @param data to replace the data
         */
        public void setData(E data) {
            this.data = data;
        }

        /**
         * sets front ListNode
         * @param front listNode
         */
        public void setFront(ListNode front) {
            this.front = front;
        }

        /**
         * sets the next ListNode
         * @param next ListNode
         */
        public void setNext(ListNode next) {
            this.next = next;
        }
    }
}
