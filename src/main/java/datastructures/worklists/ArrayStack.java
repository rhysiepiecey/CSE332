package datastructures.worklists;

import cse332.interfaces.worklists.LIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/LIFOWorkList.java
 * for method specifications.
 */
public class ArrayStack<E> extends LIFOWorkList<E> {
    private int lastIntIndex;
    private E[] stackArray;

    private static final int SIZE = 10;


    /**
     * creates the stack
     */
    public ArrayStack() {
        //throw new NotYetImplementedException();
        lastIntIndex = -1;
        stackArray = (E[])new Object[SIZE];

    }

    /**
     * rebuilds the stack with double the size
     */
    private void rebuild() {
        E[] newStack = (E[])new Object[stackArray.length * 2];
        for(int i = 0; i < stackArray.length; i++) {
            newStack[i] = stackArray[i];
        }
        stackArray = newStack;
    }

    /**
     * adds to the top of the stack
     * @param work the work to add to the worklist
     */
    @Override
    public void add(E work) {
        lastIntIndex++;
        if(lastIntIndex == stackArray.length) {
            rebuild();
        }
        stackArray[lastIntIndex] = work;
    }

    /**
     * peeks at the top of the stack
     * @return the element at the top
     */
    @Override
    public E peek() {
        if(lastIntIndex == -1) {
            throw new NoSuchElementException();
        }
        return stackArray[lastIntIndex];
    }

    /**
     * returns the element at the top of the stack and removes it
     * @return the element at the top of the stack
     */
    @Override
    public E next() {
        if(lastIntIndex == -1) {
            throw new NoSuchElementException();
        }
        E temp = this.peek();
        stackArray[lastIntIndex] = null;
        lastIntIndex--;
        return temp;
    }

    /**
     *
     * @return size of the stack
     */
    @Override
    public int size() {
        return lastIntIndex + 1;
    }

    /**
     * clears the stack
     */
    @Override
    public void clear() {
        stackArray = (E[])new Object[SIZE];
        lastIntIndex = -1;
    }
}
