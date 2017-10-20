package data_structures;

import java.util.Iterator;

import interfaces.ListI;

public class Stack<E> {
    private ListI<E> list;

    public Stack() {
        list = new LinkedList<>();
    }

    /**
     * Adds an object to the beginning of the stack.
     *
     * @param obj the object to be added to the stack.
     */
    public void push(E obj) {
        list.addFirst(obj);
    }

    /**
     * Removes the first Object in the stack and returns it.
     * Returns null if the stack is empty.
     *
     * @return the object removed.
     */
    public E pop() {
        return list.removeFirst();
    }

    /**
     * Returns the number of Objects currently in the stack.
     *
     * @return the number of Objects currently in the stack.
     */
    public int size() {
        return list.size();
    }

    /**
     * Test whether the stack is empty.
     *
     * @return true if the stack is empty, otherwise false
     */
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * Test whether the stack is full.
     *
     * @return true if the stack is full, otherwise false
     */
    public boolean isFull() {
        return false;
    }

    /**
     * Returns the first Object in the stack, but does not remove it.
     * Returns null if the stack is empty.
     *
     * @return the object at the beginning of the stack.
     */
    public E peek() {
        return list.peekFirst();
    }

    /**
     * Test whether the stack contains an object. This will use the object's
     * compareTo method to determine whether two objects are the same.
     *
     * @param obj The object to look for in the stack
     * @return true if the object is found in the stack, false if it is not found
     */
    public boolean contains(E obj) {
        return list.contains(obj);
    }

    /**
     * Return the stack to an empty state.
     * This should generally be a constant time operation.
     */
    public void makeEmpty() {
        list.makeEmpty();
    }

    /**
     * Returns an Iterator of the values in the stack, presented in
     * the same order as the stack.
     *
     * @return Iterator
     * @see java.lang.Iterable#iterator()
     */
    public Iterator<E> iterator() {
        return list.iterator();
    }

    //public int capacity();
    //public boolean push(E e);
    //public boolean equals(java.lang.Object o);
}