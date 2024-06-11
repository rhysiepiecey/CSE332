package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.FixedSizeFIFOWorkList;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/FixedSizeFIFOWorkList.java
 * for method specifications.
 */
public class CircularArrayFIFOQueue<E extends Comparable<E>> extends FixedSizeFIFOWorkList<E> {
    private E[] arrs;
    private int front;
    private int back;
    private int capacity;

    private int size;
    /**
     * creates the queue
     * @param capacity : the max length that can fit into the queue
     */
    public CircularArrayFIFOQueue(int capacity) {
        super(capacity);
        arrs = (E[])new Comparable[capacity];
        this.capacity = capacity;
        front = capacity/2;
        back = front;
    }

    /**
     * adds element work to the end of the queue
     * @param work the work to add to the worklist
     *
     */
    @Override
    public void add(E work) {
        //test cases:
        //if array is full, throw an IllegalStateException
        if(capacity == this.size()) {
            throw new IllegalStateException();
        }
        //if reaches the end of the array
        arrs[back] = work;
        back = (back + 1) % capacity;
        size++;
    }

    /**
     * returns element at the front of queue without removing it
     * @return the element at the front of the queue
     */
    @Override
    public E peek() {
        if(size == 0) {
            throw new NoSuchElementException();
        }
        return arrs[front];
    }

    /**
     *
     * @param i the index of the element to peek at
     * @return the element at index i of the queue
     * @throws NoSuchElementException if queue is empty
     * @throws IndexOutOfBoundsException if index is less than 0 or greater than size
     */
    @Override
    public E peek(int i) {
        if(size == 0) {
            throw new NoSuchElementException();
        }
        if(i < 0 || i >= size) {
            throw new IndexOutOfBoundsException();
        }
        return arrs[(front + i) % capacity];
    }

    /**
     *
     * @throws NoSuchElementException if the queue is empty
     * @return the element at the front of the queue, and removes it from the queue
     */
    @Override
    public E next() {
        //if queue is empty, throw an IllegalStateException
        if(arrs[front] == null) {
            throw new NoSuchElementException();
        }
        E element = arrs[front];
        arrs[front] = null;
        front = (front + 1) % capacity;
        size--;
        return element;
    }

    /**
     * updates queue at index i of the queue to value
     * @param i     the index of the element to update
     * @param value the value to update index i with
     * @throws NoSuchElementException    if hasWork() is false (this exception takes precedence over
     *                                   all others)
     * @throws IndexOutOfBoundsException if i < 0 or i >= size()
     */
    @Override
    public void update(int i, E value) {
        if(size == 0) {
            throw new NoSuchElementException();
        }
        if(i >= size() || i < 0) {
            throw new IndexOutOfBoundsException();
        }
        arrs[(front + i) % capacity] = value;
    }

    /**
     * returns size of the queue
     * @return size of the queue
     */
    @Override
    public int size() {
        return size;
    }

    /**
     *  clears the queue of all elements
     */
    @Override
    public void clear() {
        arrs = (E[])new Comparable[capacity];
        front = capacity/2;
        back = front;
        size = 0;
    }


    @Override
    public int compareTo(FixedSizeFIFOWorkList<E> other) {
        Iterator<E> curI = this.iterator();
        Iterator<E> otherI = other.iterator();

        while (curI.hasNext() && otherI.hasNext()) {
            E curW = curI.next();
            E otherW = otherI.next();
            if (curW.compareTo(otherW) != 0) {
                return curW.compareTo(otherW);
            }
        }
        return this.size - other.size();
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        // You will finish implementing this method in project 2. Leave this method unchanged for project 1.
        if (this == obj) {
            return true;
        } else if (!(obj instanceof FixedSizeFIFOWorkList<?>)) {
            return false;
        } else {
            FixedSizeFIFOWorkList<E> other = (FixedSizeFIFOWorkList<E>) obj;
            return this.compareTo(other) == 0;
        }
    }


    @Override
    public int hashCode() {
        // You will implement this method in project 2. Leave this method unchanged for project 1.
        int hashCode = Integer.hashCode(this.size);
        for(E work : this) {
            hashCode = 31 * hashCode + work.hashCode();
        }
        return hashCode;
    }
}
