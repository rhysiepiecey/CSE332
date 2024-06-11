package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.misc.Dictionary;
import cse332.interfaces.misc.SimpleIterator;
import cse332.interfaces.trie.TrieMap;
import cse332.types.BString;
import datastructures.worklists.ArrayStack;
import datastructures.worklists.ListFIFOQueue;

import java.util.*;
import java.util.Map.Entry;
import java.util.function.Supplier;

/**
 * See cse332/interfaces/trie/TrieMap.java
 * and cse332/interfaces/misc/Dictionary.java
 * for method specifications.
 */
public class HashTrieMap<A extends Comparable<A>, K extends BString<A>, V> extends TrieMap<A, K, V> {
    public class HashTrieNode extends TrieNode<ChainingHashTable<A, HashTrieNode>, HashTrieNode> {

        public HashTrieNode() {
            this(null);
        }

        public HashTrieNode(V value) {
            this.pointers = new ChainingHashTable<>(AVLTree::new);
            this.value = value;
        }

        @Override
        public Iterator<Entry<A, HashTrieMap<A, K, V>.HashTrieNode>> iterator() {
            ArrayStack<Entry<A, HashTrieMap<A,K,V>.HashTrieNode>> a = new ArrayStack<>();
            for (Item<A, HashTrieNode> i : pointers) {
                a.add(new AbstractMap.SimpleEntry<>(i.key, i.value));
            }
            return a.iterator();
        }
    }

    public HashTrieMap(Class<K> KClass) {
        super(KClass);
        this.root = new HashTrieNode();
        this.size = 0;
    }

    @Override
    public V insert(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }
        HashTrieNode current = (HashTrieNode) this.root;
        for (A letter : key) {
            if (current.pointers.find(letter) == null) {
                current.pointers.insert(letter, new HashTrieNode());
            }
            current = current.pointers.find(letter);
        }
        V oldValue = current.value;
        if (oldValue == null) {
            this.size++;
        }
        current.value = value;
        return oldValue;
    }

    @Override
    public V find(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        HashTrieNode current = (HashTrieNode) this.root;
        for (A letter : key) {
            if (current.pointers.find(letter) == null) {
                return null;
            }
            current = current.pointers.find(letter);
        }
        return current.value;
    }

    @Override
    public boolean findPrefix(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        HashTrieNode current = (HashTrieNode) this.root;
        for (A letter : key) {
            if (current.pointers.find(letter) == null) {
                return false;
            }
            current = current.pointers.find(letter);
        }
        return !current.pointers.isEmpty() || current.value != null;
    }

    @Override
    public void delete(K key) {
        throw new UnsupportedOperationException();
    }



    @Override
    public void clear() {
        throw new UnsupportedOperationException();
//        this.root = new HashTrieNode();
//        this.size = 0;
    }
}

