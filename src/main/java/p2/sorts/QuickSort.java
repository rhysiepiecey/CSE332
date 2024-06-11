package p2.sorts;

import java.util.Comparator;

public class QuickSort {
    public static <E extends Comparable<E>> void sort(E[] array) {
        QuickSort.sort(array, (x, y) -> x.compareTo(y));
    }

    public static <E> void sort(E[] array, Comparator<E> comparator) {
        sort(array, comparator, 0, array.length - 1);
    }

    private static <E> void sort(E[] array, Comparator<E> comparator, int low, int high) {
        if (low < high) {

            // pi is partitioning index, arr[p]
            // is now at right place
            int pi = partition(array,low, high,comparator);

            // Separately sort elements before
            // partition and after partition
            sort(array, comparator, low, pi - 1);
            sort(array, comparator,pi + 1, high);
        }
    }
    private static <E> void swap(E[] arr, int i, int j) {
        E temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    private static <E> int partition(E[] arr, int low, int high, Comparator<E> comparator) {

        E pivot = arr[high];
        int i = (low -1);

        for(int j = low; j < high ; j++) {

            if(comparator.compare(arr[j], pivot) < 1) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i+ 1, high);
        return(i+ 1);


    }
}
