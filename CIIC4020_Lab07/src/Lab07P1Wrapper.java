public class Lab07P1Wrapper {

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

	public static class SinglyLinkedList<E> {

		protected static class Node<E> {

			private E element;
			private Node<E> next;

			public Node() {
				this.next = null;
				this.element = null;
			}

			public Node(E elm, Node<E> nextNode) {
				this.next = nextNode;
				this.element = elm;
			}

			public Node(E elm) {
				this.next = null;
				this.element = elm;
			}

			public E getElement() {
				return element;
			}

			public void setElement(E element) {
				this.element = element;
			}

			public Node<E> getNext() {
				return next;
			}

			public void setNext(Node<E> next) {
				this.next = next;
			}

			public void clear() {
				this.element = null;
				this.next = null;
			}

		}// end of node class

		protected Node<E> first;
		protected int size;

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

		/*
		 * CLASSIC/EFFICIENT SOLUTION TO REVERSE A SINGLY LINKED LIST COMPLEXITY: O(n)
		 * time, O(1) space TYPE: ITERATIVE
		 */
		public void reverse1() {
			if (size > 1) { // if size is 1, the list is already its reverse...
				Node<E> curNode = first, nextNode = first.getNext(), prevNode = null;
				while (curNode != null) {
					prevNode.setNext(nextNode);
					nextNode = prevNode;
					prevNode = curNode;
					curNode = curNode.getNext();
				}
				prevNode.setNext(nextNode);
				first = prevNode;
			}

		}

		/*
		 * FIRST RECURSIVE SOLUTION TO REVERSE A SINGLY LINKED LIST COMPLEXITY: O(n)
		 * time, O(1) space TYPE: RECURSIVE
		 */
		public void reverse2() {
			if (size > 1)
				first = recReverse2(null, first);
		}

		/**
		 * 
		 * @param b - Reference to the first node of the reversed list consisting of the
		 *          nodes in the original list from the first node up to node b
		 * @param c - reference to the first node of the remaining nodes in the original
		 *          list; that is, the sublist of nodes that follow node b in the
		 *          original list
		 */
		private Node<E> recReverse2(Node<E> b, Node<E> c) {
			if (c == null)
				return b;

			// a is the reference to the node that follows node c in the original list
			// (it is null if c is the last node in the original list)
			Node<E> a = c.getNext();
			c.setNext(b);

			return recReverse2(c, a);
		}

		/**
		 * Consider the following idea for a recursive solution to the list reversing
		 * problem
		 * 
		 * The function recReverse(f) will construct the reverse list of nodes whose
		 * first node is f in the original list.
		 * 
		 * It then returns a pair (fr, lr), where fr is the first node in that resulting
		 * reversed linked list and lr is the last node in that reversed linked list.
		 * 
		 * As before, your algorithm will work just modifying links as needed; no data
		 * movements and no new nodes!
		 */
		public void reverse3() {
			if (size > 1)
				first = recReverse3(first).getFirst(); // Method to code
		}

		/**
		 * EXERCISE 1: TODO Complete the implementation of this strategy by implementing
		 * the internal private function recReverse as needed for the above to work
		 * correctly.
		 * 
		 * Take a look at the instructions on Moodle for an approach on how to solve
		 * this problem
		 */
		private Pair<Node<E>> recReverse3(Node<E> first) {
			Node<E> rec = first.getNext();
			if (rec == null) {
				return new Pair<>(first, rec);
			}
			return recReverse3(rec);
			/* ADD YOUR CODE HERE */

		}
	}
}
