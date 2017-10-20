package data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

import interfaces.HashListI;

/**
 * Linked List Implementation
 * 
 * @author Khaled Elsayed
 * CS 310 - Edwards
 */
public class HashList<E> implements HashListI<E> {

	private Node<E> head = null;
	private Node<E> tail = null;
	private int currentSize = 0;

	@SuppressWarnings("hiding")
	class Node<E> {
		E data;
		Node<E> next;

		public Node(E obj) {
			data = obj;
			next = null;
		}
	}

	class IteratorHelper implements Iterator<E> {

		Node<E> index;

		public IteratorHelper() {
			index = head;
		}

		@Override
		public boolean hasNext() {
			return index != null;
		}

		@Override
		public E next() {
			if (!hasNext())
				throw new NoSuchElementException();

			E tmp = index.data;
			index = index.next;

			return tmp;
		}

	}
	
	/**
	 * Adds an object to the end of the list.
	 * 
	 * @param obj the object to be added to the list.
	 */
	public void add(E obj) {
		Node<E> node = new Node<E>(obj);

		if (head == null) {
			head = tail = node;
			currentSize++;
		}
		else {
			tail.next = node;
			tail = node;
			currentSize++;
		}
	}
	
	/**
	 * Removes the an Object in the list and returns it.
	 * Returns null if the list is empty.
	 * @return the object removed.
	 */
	@SuppressWarnings("unchecked")
	public E remove(E obj) {

		if (isEmpty())
			return null;
		
		Node<E> current = head, previous = null;

		while (current != null) {
			if (((Comparable<E>) current.data).compareTo(obj) == 0) {
				if (current == head)
					return removeFirst();
				if (current == tail)
					return removeLast();

				previous.next = current.next;
				currentSize--;
				return current.data;
			}
			previous = current;
			current = current.next;
		}

		return null;
	}
	
	/**
	 * Return the list to an empty state.
	 * This should generally be a constant time operation.
	 */
	public void makeEmpty() {
		tail = head = null;
		currentSize = 0;
	}
	
	/**
	 * Test whether the list is empty.
	 * @return true if the list is empty, otherwise false
	 */
	public boolean isEmpty() {
		return head == null;
	}
	
	/**
	 * Returns the number of Objects currently in the list.
	 * @return the number of Objects currently in the list.
	 */
	public int size() {
		return currentSize;
	}
	
	/**
	 * Test whether the list contains an object. This will use the object's
	 * compareTo method to determine whether two objects are the same.
	 * 
	 * @param obj The object to look for in the list
	 * @return true if the object is found in the list, false if it is not found
	 */
	@SuppressWarnings("unchecked")
	public boolean contains(E obj) {
		Node<E> current = head;

		while (current != null) {
			if (((Comparable<E>) current.data).compareTo(obj) == 0)
				return true;
			current = current.next;
		}
		return false;
	}
	
	/**
	 * Returns an Iterator of the values in the list, presented in
	 * the same order as the list.
	 * @see java.lang.Iterable#iterator()
	 * @return Iterator
	 */
	public Iterator<E> iterator() {
		return new IteratorHelper();
	}
	
	/**
	 * Removes the first Object in the list and returns it.
	 * Returns null if the list is empty.
	 * @return the object removed.
	 */
	public E removeFirst() {
		if (head == null)
			return null;

		E tmp = head.data;

		if (head == tail)
			head = tail = null;
		else
			head = head.next;

		currentSize--;

		return tmp;
	}

	/**
	 * Removes the last Object in the list and returns it.
	 * Returns null if the list is empty.
	 * @return the object removed.
	 */
	public E removeLast() {
		if (isEmpty())
			return null;
		if (head == tail)
			return removeFirst();

		E tmp = tail.data;

		Node<E> current = head, previous = null;
		while (current != tail) {
			previous = current;
			current = current.next;
		}

		previous.next = null;
		tail = previous;
		currentSize--;

		return tmp;
	}
}