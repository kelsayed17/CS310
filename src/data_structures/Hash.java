package data_structures;

import java.util.Iterator;

import interfaces.HashI;
import interfaces.HashListI;

/**
 * The Hash data structure has O(1) time complexity (best case) for add, remove, and find
 * for an object in the data structure. The methods in the Hash data structure are defined
 * by the HashI interface. The Hash consists of an array of Linked Lists,
 * the Linked Lists are defined by the HashListI interface.
 *
 * @param <K> The key for entries in the hash
 * @param <V> The value for entries in the hash
 * @author Khaled Elsayed
 * CS 310 - Edwards
 */

public class Hash<K, V> implements HashI<K, V> {

    private LinkedList<HashElement<K, V>>[] hash_array;
    private int tableSize, numElements;
    private double maxLoadFactor;

    /**
     * The Hash constructor accepts a single parameter, an int, that
     * sets the initial size of the Dictionary.
     */
    @SuppressWarnings("unchecked")
    public Hash() {
        tableSize = 50000;
        hash_array = (LinkedList<HashElement<K, V>>[]) new LinkedList[tableSize];

        for (int i = 0; i < tableSize; i++)
            hash_array[i] = new LinkedList<HashElement<K, V>>();

        maxLoadFactor = 0.75;
        numElements = 0;
    }

    @SuppressWarnings("hiding")
    class HashElement<K, V> implements Comparable<HashElement<K, V>> {

        K key;
        V value;

        public HashElement(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @SuppressWarnings("unchecked")
        public int compareTo(HashElement<K, V> o) {
            return ((Comparable<K>) o.key).compareTo(this.key);
        }
    }

    class IteratorHelper<T> implements Iterator<T> {
        T[] keys;
        int position;

        @SuppressWarnings("unchecked")
        public IteratorHelper() {
            keys = (T[]) new Object[numElements];
            int p = 0;
            for (int i = 0; i < tableSize; i++) {
                HashListI<HashElement<K, V>> list = (HashListI<Hash<K, V>.HashElement<K, V>>) hash_array[i];

                for (HashElement<K, V> h : list)
                    keys[p++] = (T) h.key;
            }
            position = 0;
        }

        public boolean hasNext() {
            return position < keys.length;
        }

        public T next() {
            if (!hasNext())
                return null;
            return keys[position++];
        }
    }

    /**
     * Adds the given key/value pair to the dictionary.  Returns
     * false if the dictionary is full, or if the key is a duplicate.
     * Returns true if addition succeeded.
     *
     * @param key   the key to add
     * @param value the value associated with the key
     * @return true if the key/value are added to the hash.
     */
    public boolean add(K key, V value) {
        if (loadFactor() > maxLoadFactor)
            resize(tableSize * 2);

        HashElement<K, V> he = new HashElement<K, V>(key, value);

        int hashval = (key.hashCode() & 0x7FFFFFFF) % tableSize;

        hash_array[hashval].add(he);
        numElements++;

        return true;
    }

    /**
     * Deletes the key/value pair identified by the key parameter.
     * Returns true if the key/value pair was found and removed,
     * otherwise returns false.
     *
     * @param key   the key to remove
     * @return true if key was removed
     */
    @SuppressWarnings("unchecked")
    public boolean remove(K key) {
        int hashval = (key.hashCode() & 0x7FFFFFFF) % tableSize;

        for (HashElement<K, V> he : hash_array[hashval])
            if (((Comparable<K>) he.key).compareTo(key) == 0) {
                hash_array[hashval].remove(he);
                numElements--;
                return true;
            }

        return false;
    }

    @SuppressWarnings("unchecked")
    public V getMaxValue() {
        HashElement<K, V> max = null;

        for (int i = 0; i < tableSize; i++) {
            LinkedList<HashElement<K, V>> list = hash_array[i];
            for (HashElement<K, V> he : list)
                if (max == null || ((Comparable<V>) he.value).compareTo(max.value) > 0)
                    max = he;
        }

        return max.value;
    }

    /**
     * Change the value associated with an existing key.
     *
     * @param key   The key to change
     * @param value
     * @return true if value changed
     */
    @SuppressWarnings("unchecked")
    public boolean changeValue(K key, V value) {
        int hashval = (key.hashCode() & 0x7FFFFFFF) % tableSize;

        for (HashElement<K, V> he : hash_array[hashval])
            if (((Comparable<K>) he.key).compareTo(key) == 0) {
                he.value = value;
                return true;
            }

        return false;
    }

    /**
     * Test whether the hash has the entry associated with the key
     *
     * @param key the key to look for
     * @return whether it is there.
     */
    @SuppressWarnings("unchecked")
    public boolean contains(K key) {
        int hashval = (key.hashCode() & 0x7FFFFFFF) % tableSize;

        for (HashElement<K, V> he : hash_array[hashval])
            if (((Comparable<K>) he.key).compareTo(key) == 0) {
                return true;
            }

        return false;
    }

    /**
     * Returns the value associated with the parameter key.
     * Returns null if the key is not found or the dictionary is empty.
     *
     * @param key the key to find the value for
     * @return the value
     */
    @SuppressWarnings("unchecked")
    public V getValue(K key) {
        int hashval = (key.hashCode() & 0x7FFFFFFF) % tableSize;

        for (HashElement<K, V> he : hash_array[hashval])
            if (((Comparable<K>) he.key).compareTo(key) == 0)
                return he.value;

        return null;
    }

    /**
     * Returns the number of key/value pairs currently stored in the dictionary
     *
     * @return the number of elements
     */
    public int size() {
        return numElements;
    }

    /**
     * Returns true if the dictionary is empty
     *
     * @return whether the dictionary is empty
     */
    public boolean isEmpty() {
        if (numElements == 0)
            return true;

        return false;
    }

    /**
     * Make the dictionary empty
     */
    public void makeEmpty() {
        for (int i = 0; i < hash_array.length; i++)
            hash_array[i] = null;

        numElements = 0;
    }

    /**
     * Returns the current load factor of the dictionary (lambda)
     *
     * @return the loadFactor
     */
    public double loadFactor() {
        return numElements / tableSize;
    }

    /**
     * Get the maximum load factor (at which point we need to resize)
     *
     * @return the maximum load factor of the hash
     */
    public double getMaxLoadFactor() {
        return maxLoadFactor;
    }

    /**
     * Set the maximum load factor (at which point we need to resize)
     *
     * @param loadfactor the maximum load factor
     */
    public void setMaxLoadFActor(double loadfactor) {
        maxLoadFactor = loadfactor;
    }

    /**
     * Resizes the dictionary
     *
     * @param newSize the size of the new dictionary
     */
    @SuppressWarnings("unchecked")
    public void resize(int newSize) {
        LinkedList<HashElement<K, V>>[] new_array = (LinkedList<HashElement<K, V>>[]) new LinkedList[newSize];

        for (int i = 0; i < newSize; i++)
            new_array[i] = new LinkedList<HashElement<K, V>>();

        for (K key : this) {
            V val = getValue(key);

            HashElement<K, V> he = new HashElement<>(key, val);
            int hashVal = (key.hashCode() & 0x7FFFFFFF) % newSize;
            new_array[hashVal].add(he);
        }

        hash_array = new_array;
        tableSize = newSize;

    }

    /**
     * Returns an Iterator of the keys in the dictionary, in ascending
     * sorted order
     */
    public Iterator<K> iterator() {
        return new IteratorHelper<K>();
    }
}