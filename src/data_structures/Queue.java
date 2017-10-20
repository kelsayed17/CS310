package data_structures;

import java.util.Iterator;

import interfaces.ListI;

public class Queue<E> {
    private ListI<E> list;

    public Queue() {
        list = new LinkedList<>();
    }

    /**
     * Adds an object to the end of the queue.
     *
     * @param obj the object to be added to the queue.
     */
    public void enqueue(E obj) {
        list.addLast(obj);
    }

    /**
     * Removes the first Object in the queue and returns it.
     * Returns null if the queue is empty.
     *
     * @return the object removed.
     */
    public E dequeue() {
        return list.removeFirst();
    }

    /**
     * Returns the number of Objects currently in the queue.
     *
     * @return the number of Objects currently in the queue.
     */
    public int size() {
        return list.size();
    }

    /**
     * Test whether the queue is empty.
     *
     * @return true if the queue is empty, otherwise false
     */
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * Returns the first Object in the queue, but does not remove it.
     * Returns null if the queue is empty.
     *
     * @return the object at the beginning of the queue.
     */
    public E peek() {
        return list.peekFirst();
    }

    /**
     * Test whether the queue contains an object. This will use the object's
     * compareTo method to determine whether two objects are the same.
     *
     * @param obj The object to look for in the queue
     * @return true if the object is found in the queue, false if it is not found
     */
    public boolean contains(E obj) {
        return list.contains(obj);
    }

    /**
     * Return the queue to an empty state.
     * This should generally be a constant time operation.
     */
    public void makeEmpty() {
        list.makeEmpty();
    }

    /**
     * Returns an Iterator of the values in the queue, presented in
     * the same order as the queue.
     *
     * @return Iterator
     * @see java.lang.Iterable#iterator()
     */
    public Iterator<E> iterator() {
        return list.iterator();
    }
}