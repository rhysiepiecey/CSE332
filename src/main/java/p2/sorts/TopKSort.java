package p2.sorts;

import cse332.exceptions.NotYetImplementedException;
import datastructures.worklists.MinFourHeap;

import java.util.Comparator;

public class TopKSort {
    public static <E extends Comparable<E>> void sort(E[] array, int k) {
        sort(array, k, (x, y) -> x.compareTo(y));
    }

    /**
     * Behaviour is undefined when k > array.length
     */
    public static <E> void sort(E[] array, int k, Comparator<E> comparator) {
        MinFourHeap<E> heap = new MinFourHeap<>(comparator);
        for (E work : array) {
            heap.add(work);
            if (heap.size() > k) {
                heap.next();
            }
        }
        for (int i = 0; i < array.length; i++) {
            if (i < k) {
                array[i] = heap.next();
            } else {
                array[i] = null;
            }
        }
    }
}
