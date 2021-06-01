import java.util.ArrayList;

public class Lab07P2Wrapper {

	public static class Pair<E> {
		private E first;
		private E second;

		public Pair(E first, E second) {
			super();
			this.first = first;
			this.second = second;
		}

		public Pair() {
		}

		public E getFirst() {
			return first;
		}

		public void setFirst(E first) {
			this.first = first;
		}

		public E getSecond() {
			return second;
		}

		public void setSecond(E second) {
			this.second = second;
		}

	}

	public static class SinglyLinkedList<E extends Comparable<E>> {

		private static class Node<E> {

			private E element;
			private Node<E> next;

			@SuppressWarnings("unused")
			public Node() {
				this.next = null;
				this.element = null;
			}

			public Node(E elm, Node<E> nextNode) {
				this.next = nextNode;
				this.element = elm;
			}

			@SuppressWarnings("unused")
			public Node(E elm) {
				this.next = null;
				this.element = elm;
			}

			public E getElement() {
				return element;
			}

			@SuppressWarnings("unused")
			public void setElement(E element) {
				this.element = element;
			}

			public Node<E> getNext() {
				return next;
			}

			@SuppressWarnings("unused")
			public void setNext(Node<E> next) {
				this.next = next;
			}

			public void clear() {
				this.element = null;
				this.next = null;
			}

		}// end of node class

		private Node<E> first;
		private int size;

		public SinglyLinkedList() {
			this.first = null;
			this.size = 0;
		}

		public void addFirst(E e) {
			Node<E> newNode = new Node<>(e, first);
			first = newNode;
			size++;
		}

		public String toString() {
			String s = "";
			Node<E> current = first;
			while (current != null) {
				s += " " + current.getElement();
				current = current.getNext();
			}
			return s;
		}
		// NO NEED FOR OTHER METHODS FOR THE MOMENT...

		public ArrayList<Pair<E>> consecutiveIncreasingPairs() {
			ArrayList<Pair<E>> result = new ArrayList<>(); // and empty ArrayList object
			if (size > 0)
				recCIP(first, result);
			return result;
		}

		/**
		 * Implement a recursive auxiliary method called recIP for the
		 * consecutiveIncreasingPairs method
		 * 
		 * The recursive method takes as parameter a node and an ArrayList of Pairs
		 * 
		 * The main method's purpose is to construct a list of pairs of consecutive
		 * increasing elements in a list (the first element is less than the second of
		 * the two).
		 * 
		 * For example, if the list is (3, 4, 8, 1, 7, 3, 5, 4), then the list of pairs
		 * to produce would be: ((3, 4), (4, 8), (1, 7), (3, 5)).
		 */
		private void recCIP(Node<E> first, ArrayList<Pair<E>> result) {
			/* ADD YOUR CODE HERE */
			Node<E> pair = first.getNext();
			if (first.getNext() == null)
				return;

			if (first.getElement().compareTo(pair.getElement()) < 0) {
				Pair<E> list = new Pair<>(first.getElement(), pair.getElement());
				result.add(list);
			}
			recCIP(pair, result);
		}

	}
}
