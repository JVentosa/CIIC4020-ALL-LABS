public class Lab06P1Wrapper {

	@SuppressWarnings("serial")
	public static class EmptyQueueException extends RuntimeException {
		public EmptyQueueException(String message) {
			super(message);
		}
	}

	public static interface Queue<E> {
		int size();

		boolean isEmpty();

		E getFront() throws Lab06P1Wrapper.EmptyQueueException;

		void enqueue(E element);

		E dequeue() throws Lab06P1Wrapper.EmptyQueueException;
	}

	public static class LinkedQueue<E> implements Queue<E> {

		private static class Node<E> {
			private E element;
			private Node<E> next;

			@SuppressWarnings("unused")
			public Node(E elm) {
				this.element = elm;
				this.next = null;
			}

			public Node(E elm, Node<E> nextNode) {
				this.element = elm;
				this.next = nextNode;
			}

			@SuppressWarnings("unused")
			public Node() {
				this.element = null;
				this.next = null;
			}

			public E getElement() {
				return this.element;
			}

			public Node<E> getNext() {
				return this.next;
			}

			public void setNext(Node<E> next) {
				this.next = next;
			}

			@SuppressWarnings("unused")
			public void setElement(E elm) {
				this.element = elm;
			}

			public void clear() {
				this.element = null;
				this.next = null;
			}
		} // END CLASS NODE

		private Node<E> first, last; // references to first and last node
		private int size;

		public LinkedQueue() { // initializes instance as empty queue
			first = last = null;
			size = 0;
		}

		public int size() {
			return size;
		}

		public boolean isEmpty() {
			return size == 0;
		}

		public E getFront() throws Lab06P1Wrapper.EmptyQueueException {
			if (isEmpty())
				throw new EmptyQueueException("Queue is empty");
			return first.getElement();
		}

		/* FINISH THIS MEMBER METHOD */
		public E dequeue() throws Lab06P1Wrapper.EmptyQueueException {

			E etr = getFront();
			Node<E> eqNode = first;
			first = first.getNext();
			eqNode.clear();
			size--;
			return etr; // Dummy Return
		}

		/* FINISH THIS MEMBER METHOD */
		public void enqueue(E e) {
			Node<E> newNode = new Node<>(e, null);
			if (size == 0) {
				/* ADD YOUR CODE HERE */
				first = newNode;
				last = newNode;
				last.setNext(null);

			} else {
				/* ADD YOUR CODE HERE */
				last.setNext(newNode);
				last = newNode;

			}
			size++;

		}
	}

}
