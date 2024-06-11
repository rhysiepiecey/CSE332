package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.misc.DeletelessDictionary;
import cse332.interfaces.misc.Dictionary;
import cse332.interfaces.misc.SimpleIterator;
import datastructures.worklists.ListFIFOQueue;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

/**
 * 1. You must implement a generic chaining hashtable. You may not
 * restrict the size of the input domain (i.e., it must accept
 * any key) or the number of inputs (i.e., it must grow as necessary).
 * 3. Your HashTable should rehash as appropriate (use load factor as
 * shown in class!).
 * 5. HashTable should be able to resize its capacity to prime numbers for more
 * than 200,000 elements. After more than 200,000 elements, it should
 * continue to resize using some other mechanism.
 * 6. We suggest you hard code some prime numbers. You can use this
 * list: http://primes.utm.edu/lists/small/100000.txt
 * NOTE: Do NOT copy the whole list!
 * 7. When implementing your iterator, you should NOT copy every item to another
 * dictionary/list and return that dictionary/list's iterator.
 */
public class ChainingHashTable<K, V> extends DeletelessDictionary<K, V> {
    private Supplier<Dictionary<K, V>> newChain;

    private int primeIndex;
    public final int[] PRIME_CAPACITY = {367, 733, 1567 ,3019, 6007, 12007, 24007, 48017, 98947, 200000};
    public final double LOAD_FACTOR = 1;
    private Dictionary<K,V>[] table;
        public ChainingHashTable(Supplier<Dictionary<K, V>> newChain) {
            this.newChain = newChain;
            this.primeIndex = 0;
            this.table = (Dictionary<K, V>[]) new Dictionary[PRIME_CAPACITY[primeIndex]];
            size = 0;
        }


    @Override
    public V insert(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }

        // double capacity and rehash if load factor is reached
        if ((double) size / table.length >= LOAD_FACTOR) {
            primeIndex++;
            resize();
        }
        V oldVal = hash(table, key, value);
        if (oldVal == null) {
            size++;
        }
        return oldVal;
    }

    private V hash(Dictionary<K,V>[] table, K key, V value) {
        int index = Math.abs(key.hashCode() % table.length);
        if (table[index] == null) {
            table[index] = newChain.get();
        }
        return table[index].insert(key, value);
    }

    private void resize() {
        // create copy table of x2 size or next prime num
        Dictionary<K,V>[] doubled;
        if (primeIndex < PRIME_CAPACITY.length) {
            doubled = (Dictionary<K,V>[]) new Dictionary[PRIME_CAPACITY[primeIndex]];
        } else {
            doubled = (Dictionary<K,V>[]) new Dictionary[table.length * 2];
        }

        // add each item and rehash it
        for (Item<K,V> i : this) {
            hash(doubled, i.key, i.value);
        }
        table = doubled;
    }

    @Override
    public V find(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        int index = Math.abs(key.hashCode() % table.length);
        return table[index] != null ? table[index].find(key) : null;
    }

    @Override
    public Iterator<Item<K, V>> iterator() {
        return new CHTIterator();
    }

    private class CHTIterator extends SimpleIterator<Item<K, V>> {

        private final ListFIFOQueue<Item<K, V>> keyList;

        public CHTIterator() {
            this.keyList = new ListFIFOQueue<>();
            for (Dictionary<K, V> d : table) {
                if (d != null) {
                    for (Item<K, V> i : d) {
                        this.keyList.add(i);
                    }
                }
            }
        }

        @Override
        public boolean hasNext() {
            return keyList.hasWork();
        }

        @Override
        public Item<K, V> next() {
            return keyList.next();
        }
    }
}
