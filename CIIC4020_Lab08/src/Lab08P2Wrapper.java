import java.util.Iterator;
import java.util.NoSuchElementException;

public class Lab08P2Wrapper {

	public static interface IndexList<E> extends Iterable<E> {
		int size();

		boolean isEmpty();

		E get(int index) throws IndexOutOfBoundsException;

		E remove(int index) throws IndexOutOfBoundsException;

		E set(int index, E e) throws IndexOutOfBoundsException;

		void add(int index, E e) throws IndexOutOfBoundsException;

		void add(E e);

		int removeAll(E e);
	}

	public static class LinkedDHIndexList<E> implements IndexList<E> {
		private static class Node<E> {
			private E element;
			private Node<E> next;

			public Node() {
			}

			public Node(E element, Node<E> next) {
				this.element = element;
				this.next = next;
			}

			@SuppressWarnings("unused")
			public Node(E element) {
				this(element, null);
			}

			public E getElement() {
				return element;
			}

			public E setElement(E element) {
				E old = this.element;
				this.element = element;
				return old;
			}

			public Node<E> getNext() {
				return next;
			}

			public void setNext(Node<E> next) {
				this.next = next;
			}

			// Just to help the GC whenever a node is to be discarded.
			public E clear() {
				E etr = element;
				element = null;
				next = null;
				return etr;
			}
		} // end of class Node

		private Node<E> header = new Node<>(); // the dummy node
		private int size = 0;

		public void add(int index, E e) {
			if (index < 0 || index > size)
				throw new IndexOutOfBoundsException();
			Node<E> prev = findNodePreceding(index);
			prev.setNext(new Node<>(e, prev.getNext()));
			// assuming such constructor in class Node
			size++;
		}

		private Node<E> findNodePreceding(int i) {
			// PRE: i >= 0 and i < size
			// Returns the node that is the node that would be preceding the node
			// corresponding to the i-th position in the list. That node always
			// exist; the least it can be is the dummy node.
			Node<E> prev = header;
			for (int p = 0; p < i; p++)
				prev = prev.getNext();
			return prev;
		}

		public E remove(int index) {
			if (index < 0 || index >= size)
				throw new IndexOutOfBoundsException();
			Node<E> prev = findNodePreceding(index);
			Node<E> ntr = prev.getNext();
			prev.setNext(ntr.getNext());
			size--;
			return ntr.clear();
		}

		@Override
		public int size() {
			return size;
		}

		@Override
		public boolean isEmpty() {
			return size == 0;
		}

		@Override
		public E get(int index) throws IndexOutOfBoundsException {
			if (index < 0 || index >= size)
				throw new IndexOutOfBoundsException();
			return findNodePreceding(index + 1).getElement();
		}

		@Override
		public E set(int index, E e) throws IndexOutOfBoundsException {
			if (index < 0 || index >= size)
				throw new IndexOutOfBoundsException();
			return findNodePreceding(index + 1).setElement(e);
		}

		@Override
		public void add(E e) {
			add(size, e);

		}

		@Override
		public int removeAll(E e) {
			int countRM = 0; // to count the number of removals
			Node<E> ntr = null;

			if (header.getNext() == null)
				return 0;

			while (header.getNext() != null && header.getNext().getElement().equals(e)) {
				ntr = header.getNext();
				header.setNext(ntr.getNext());
				countRM++;
				ntr.clear();
			}

			if (header.getNext() != null) {
				Node<E> current = header.getNext();
				while (current.getNext() != null) {
					if (current.getNext().getElement().equals(e)) {
						ntr = current.getNext();
						current.setNext(ntr.getNext());
						countRM++;
						ntr.clear();
					} else
						current = current.getNext();
				}
			}

			size = size - countRM;

			return countRM;

		}

		public Iterator<E> iterator() {
			return new ElementsIterator();
		}

		private class ElementsIterator implements Iterator<E> {
			private Node<E> now = header.getNext();
			private Node<E> top = header;
			private boolean yeetout = false;
			/**
			 * We need 3 variables to implement this iterator correctly: 1) A curNode 2) A
			 * predecessorNode to curNode 3) A control variable to indicate when remove() is
			 * valid to execute
			 * 
			 * HINT: Take advantage that you have a dummy header
			 */

			public boolean hasNext() {
				return now != null;
			}

			public E next() {

				/*
				 * KEY IDEA: 1) If we don't have an element that we haven't iterated through, we
				 * throw an exception (NoSuchElement) -> Why? 2) If we can call remove(), we
				 * move the predecessorNode one element forward -> Why is this? Think! 3) After
				 * moving one element forward we indicate that we can still call remove() 4)
				 * Next, we get the element in curNode and return the element that was in
				 * curNode
				 */
				if (!hasNext())
					throw new NoSuchElementException("It's done");
				if (yeetout)
					top = top.getNext();
				
				yeetout = true;
				E removing_element = now.getElement();
				now = now.getNext();
				
				return removing_element;
			}

			public void remove() {

				/*
				 * KEY IDEA: 1) If we can't remove an element, throw an exception (IllegalState)
				 * -> Why? 2) Then we deviate predecessorNode's pointer to the next element
				 * after the nodeToRemove -> Who is nodeToRemove? This is a LinkedList, think
				 * about previous lectures 3) Reduce size and indicate we cannot remove; as well
				 * as clear nodeToRemove to avoid memory leaks
				 */
				if (!yeetout)
					throw new IllegalStateException("Can't be yeeted out");
				Node<E> remove_Node = top.getNext();
				top.setNext(remove_Node.getNext());
				size--;
				yeetout = false;
				remove_Node.clear();
			}

		}
	}

}
