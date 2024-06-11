package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.PriorityWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/PriorityWorkList.java
 * for method specifications.
 */
public class MinFourHeapComparable<E extends Comparable<E>> extends PriorityWorkList<E> {
    /* Do not change the name of this field; the tests rely on it to work correctly. */
    private E[] data;
    private static final int START_SIZE = 21;

    private int size;

    public MinFourHeapComparable() {
        data = (E[])new Comparable[START_SIZE];
        size = -1;
    }

    @Override
    public boolean hasWork() {
        return size >= 0;
    }

    @Override
    public void add(E work) {
        if(size() == data.length - 1) {
            resize();
        }
        size++;
        int i = percolateUp(size, work);
        data[i] = work;
    }

    private int percolateUp(int hole, E work) {
        while(hole > 0 && work.compareTo(data[(hole - 1)/4]) < 0){
            data[hole] = data[(hole - 1) / 4];
            hole = (hole - 1) / 4;
        }
        return hole;
    }

    private void resize() {
        E[] freshData = (E[])new Comparable[data.length * 2];
        for(int i = 0; i < data.length; i++) {
            freshData[i] = data[i];
        }
        data = freshData;
    }

    @Override
    public E peek() {
        if(size == -1) {
            throw new NoSuchElementException();
        }
        return data[0];
    }

    @Override
    public E next() {
        if(size == -1) {
            throw new NoSuchElementException();
        }
        E ans = data[0];
        int hole = percolateDown(0, data[size]);
        data[hole] = data[size];
        size--;
        return ans;
    }

    private int percolateDown(int hole, E val) {
        //check if it is less than any of its children
        //swap places with the lowest one
        int target;
        while(4 * hole + 1 <= size) {
            int index = 4 * hole;
            target = index + 1;
            for(int i = 4; i > 0; i--) {
                if((index + i) <= this.size ) {
                    if(index + 2 > this.size) {
                        target = index + 1;
                    } else if(index + 3 >  this.size) {
                        if(data[index + i].compareTo(data[index + 1]) <= 0 &&
                                data[index + i].compareTo(data[index + 2]) <= 0) {
                            target = index + i;
                        }
                    } else if(index + 4 > this.size) {
                        if(data[index + i].compareTo(data[index + 1]) <= 0 &&
                                data[index + i].compareTo(data[index + 2]) <= 0 &&
                                data[index + i].compareTo(data[index + 3]) <= 0) {
                            target = index + i;
                        }
                    } else {
                        if(data[index + i].compareTo(data[index + 1]) <= 0 &&
                                data[index + i].compareTo(data[index + 2]) <= 0 &&
                                data[index + i].compareTo(data[index + 3]) <= 0 &&
                                data[index + i].compareTo(data[index + 4]) <= 0) {
                            target = index + i;
                        }
                    }
                }

            }
            //we have identified the smallest child
            //now we swap it
            if(data[target].compareTo(val) < 0) {
                E temp = data[hole];
                data[hole] = data[target];
                data[target] = temp;
                hole = target;
            } else {
                break;
            }
        }
        return hole;
    }
    @Override
    public int size() {
        return size + 1;
    }

    @Override
    public void clear() {
        data = (E[])new Comparable[START_SIZE];
        size = -1;
    }
}
