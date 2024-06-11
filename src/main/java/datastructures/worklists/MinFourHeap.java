package datastructures.worklists;

import cse332.datastructures.containers.Item;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.PriorityWorkList;
import datastructures.dictionaries.HashTrieMap;

import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/PriorityWorkList.java
 * for method specifications.
 */
public class MinFourHeap<E> extends PriorityWorkList<E> implements Comparator<E> {
    /* Do not change the name of this field; the tests rely on it to work correctly. */
    private E[] data;
    private static final int START_SIZE = 23;
    Comparator<E> comparator;
    private int size;

    public MinFourHeap(Comparator<E> c) {
        data = (E[])new Object[START_SIZE];
        size = -1;
        comparator = c;
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
        while (hole > 0 && compare(work,data[(hole - 1)/4]) < 0){
            data[hole] = data[(hole - 1) / 4];
            hole = (hole - 1) / 4;
        }
        return hole;
    }

    private void resize() {
        E[] freshData = (E[])new Object[data.length * 2];
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
                        if(compare(data[index + i], data[index + 1]) <= 0 &&
                                compare(data[index + i], data[index + 2]) <= 0) {
                            target = index + i;
                        }
                    } else if(index + 4 > this.size) {
                        if(compare(data[index + i], data[index + 1]) <= 0 &&
                                compare(data[index + i], data[index + 2]) <= 0 &&
                                compare(data[index + i],data[index + 3]) <= 0) {
                            target = index + i;
                        }
                    } else {
                        if(compare(data[index + i], data[index + 1]) <= 0 &&
                                compare(data[index + i],data[index + 2]) <= 0 &&
                                compare(data[index + i],data[index + 3]) <= 0 &&
                                compare(data[index + i], data[index + 4]) <= 0) {
                            target = index + i;
                        }
                    }
                }

            }
            //we have identified the smallest child
            //now we swap it
            if(compare(data[target],val) < 0) {
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

    public Comparator<E> getComparator() {
        return comparator;
    }

    @Override
    public void clear() {
        data = (E[])new Object[START_SIZE];
        size = -1;
    }

    @Override
    public int compare(E o1, E o2) {
        return comparator.compare(o1,o2);
    }
}
