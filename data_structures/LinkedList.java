package data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

import interfaces.ListI;

/**
 * Linked List Implementation
 * 
 * @author Khaled Elsayed
 * CS 310 - Edwards
 */
public class LinkedList<E> implements ListI<E> {

	private Node<E> head = null;
	private Node<E> tail = null;
	private int size = 0;

	@SuppressWarnings("hiding")
	private class Node<E> {
		private E data;
		private Node<E> next;

		// Zero argument constructor
		public Node() {
			data = null;
			next = null;
		}

		// One argument constructor
		public Node(E data) {
			this.data = data;
			this.next = null;
		}

		// Two argument constructor
		public Node(E data, Node<E> next) {
			this.data = data;
			this.next = next;
		}

		public Node<E> getNext() {
			return next;
		}

		public void setNext(Node<E> next) {
			this.next = next;
		}

		public E getData() {
			return data;
		}

		public void setData(E data) {
			this.data = data;
		}
	}

	class IteratorHelper implements Iterator<E> {

		Node<E> previous, current, next;

		public IteratorHelper() {
			current = head;
			next = head.getNext();
		}

		@Override
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public E next() {
			if (!hasNext())
				throw new NoSuchElementException();

			E tmp = current.data;
			current = current.next;

			return tmp;
		}

		@Override
		public void remove() {
			if (current == null)
				throw new IllegalStateException();

			previous.setNext(current);
			current = null;
			size--;
		}
	}


	/**
	 * Appends the specified element to the end of this list.
	 */
	public boolean add(E e) {
		Node<E> node = new Node<E>(e);

		if (head == null) {
			head = tail = node;
			size++;
		}
		else {
			tail.next = node;
			tail = node;
			size++;
		}

		return true;
	}

	/**
	 * Inserts the specified element at the specified position in this list.
	 */
	public void add(int index, E element) {

		if (index < 0 || index >= size())
			throw new IndexOutOfBoundsException();

		Node<E> tmp = head;

		for (int i = 0; i <= index; i++)
			tmp = tmp.getNext();

		Node<E> node = new Node<E>(element, tmp.getNext());

		tmp.setNext(node);

		size++;
	}

	/**
	 * Inserts the specified element at the beginning of this list.
	 */
	public void addFirst(E e) {
		Node<E> node = new Node<E>(e);

		if (head == null) {
			head = tail = node;
			size++;
		}
		else {
			node.next = head;
			head = node;
			size++;
		}
	}

	/**
	 * Appends the specified element to the end of this list.
	 */
	public void addLast(E e) {
		Node<E> node = new Node<E>(e);

		if (head == null) {
			head = tail = node;
			size++;
		}
		else {
			tail.next = node;
			tail = node;
			size++;
		}
	}

	/**
	 * Removes all of the elements from this list.
	 */
	public void makeEmpty() {
		tail = head = null;
		size = 0;
	}

	/**
	 * Returns true if this list contains the specified element.
	 */
	@SuppressWarnings("unchecked")
	public boolean contains(Object o) {
		Node<E> current = head;

		while (current != null) {
			if (((Comparable<Object>) current.data).compareTo(o) == 0)
				return true;
			current = current.next;
		}
		return false;
	}


	/**
	 * Returns the element at the specified position in this list.
	 */
	public E get(int index) {

		if (index < 0 || index >= size())
			throw new IndexOutOfBoundsException();

		// Empty
		if (head == null)
			return null;

		// Beginning
		if (index == 0)
			return head.getData();

		// End
		if (index == size() - 1)
			return tail.getData();

		Node<E> node = head;

		// Middle
		for (int i = 0; i < index; i++)
			node = node.getNext();

		return node.getData();
	}

	/**
	 * Retrieves, but does not remove, the first element of this list, or returns null if this list is empty.
	 */
	public E peekFirst() {
		if (head == null)
			return null;
		return head.data;
	}

	/**
	 * Retrieves, but does not remove, the last element of this list, or returns null if this list is empty.
	 */
	public E peekLast() {
		if (tail == null)
			return null;
		return tail.data;
	}

	/**
	 * Removes the element at the specified position in this list.
	 */
	public E remove(int index) {
		if (index < 0 || index >= size())
			throw new IndexOutOfBoundsException();

		// Empty
		if (head == null)
			return null;

		// Beginning
		if (index == 0)
			removeFirst();

		// End
		if (index == size() - 1)
			removeLast();

		Node<E> current = head, previous = null;

		// Middle
		for (int i = 0; i < index; i++) {
			previous = current;
			current = current.getNext();
		}

		E data = current.getData();

		previous = current.getNext();
		size--;

		return data;
	}

	/**
	 * Removes the first occurrence of the specified element from this list, if it is present.
	 */
	@SuppressWarnings("unchecked")
	public boolean remove(Object o) {

		if (isEmpty())
			return false;

		Node<E> current = head, previous = null;

		while (current != null) {
			if (((Comparable<Object>) current.data).compareTo(o) == 0) {
				if (current == head)
					//return removeFirst();
					if (current == tail)
						//return removeLast();

						previous.next = current.next;
				size--;
				return true;
			}
			previous = current;
			current = current.next;
		}

		return false;
	}

	/**
	 * Removes and returns the first element from this list.
	 */
	public E removeFirst() {

		// Empty
		if (isEmpty())
			return null;

		E data = head.data;

		// Single
		if (head == tail)
			head = tail = null;
		else
			head = head.next;

		size--;

		return data;
	}

	/**
	 * Removes and returns the last element from this list.
	 */
	public E removeLast() {

		// Empty
		if (isEmpty())
			return null;

		// Single
		if (head == tail)
			return removeFirst();

		E data = tail.getData();

		Node<E> current = head, previous = null;

		// End
		while (current != tail) {
			previous = current;
			current = current.next;
		}

		previous.next = null;
		tail = previous;
		size--;

		return data;
	}

	/**
	 * Replaces the element at the specified position in this list with the specified element.
	 */
	public E set(int index, E element) {
		if (index < 0 || index >= size())
			throw new IndexOutOfBoundsException();

		// Empty
		if (head == null)
			return null;

		// Beginning
		if (index == 0) {
			E data = head.getData();
			head.setData(element);
			return data;
		}

		// End
		if (index == size() - 1) {
			E data = tail.getData();
			tail.setData(element);
			return data;
		}

		Node<E> node = head;

		// Middle
		for (int i = 0; i < index; i++) {
			node = node.getNext();
		}

		E data = node.getData();
		node.setData(element);
		return data;
	}

	/**
	 * Returns the number of elements in this list.
	 */
	public int size() {
		return size;
	}

	/**
	 * Test whether the list is empty.
	 * @return true if the list is empty, otherwise false
	 */
	public boolean isEmpty() {
		return head == null;
	}

	/**
	 * Test whether the list is full.
	 * @return true if the list is full, otherwise false
	 */
	public boolean isFull() {
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
}