public class Lab03P2Wrapper {

	public static interface IndexList<E> {
		public int size();

		public boolean isEmpty();

		public E get(int index) throws IndexOutOfBoundsException;

		public E remove(int index) throws IndexOutOfBoundsException;

		public E set(int index, E e) throws IndexOutOfBoundsException;

		public void add(int index, E e) throws IndexOutOfBoundsException;

		public void add(E e);

		public int removeAll(E e);

		public IndexList<Integer> indexesOf(E elm);

	}

	/**
	 * 
	 * @author pedroi.rivera-vega
	 *
	 * @param <E>
	 */
	public static class LinkedIndexList<E> implements IndexList<E> {

		private static class Node<E> {

			private E element;
			private Node<E> next;

			public Node(E element, Node<E> next) {
				this.element = element;
				this.next = next;
			}

			public Node(E element) { // Delegate to other constructor
				this(element, null);
			}

			@SuppressWarnings("unused")
			public Node() { // Delegate to other constructor
				this(null, null);
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

			// Just to help the GC whenever a node is to be discarded to avoid memory leaks.
			public E clear() {
				E etr = element;
				element = null;
				next = null;
				return etr;
			}
		} // end of class Node

		// Private fields for the class LinkedIndexList

		private Node<E> head; // references first node, this is NOT a dummy header
		private int size; // number of elements in the list

		// Constructor
		public LinkedIndexList() {
			head = null;
			size = 0;
		}

		/* MEMBER METHODS FROM THE IndexList ADT */
		@Override
		public int size() {
			return size;
		}

		@Override
		public boolean isEmpty() {
			return size == 0;
		}

		@Override
		public E get(int index) {
			if (index < 0 || index >= size)
				throw new IndexOutOfBoundsException("Invalid index for GET operation, index = " + index);

			// if here, then the index is valid.
			Node<E> targetNode = findNode(index);
			return targetNode.getElement();
		}

		@Override
		public E remove(int index) throws IndexOutOfBoundsException {
			if (index < 0 || index >= size)
				throw new IndexOutOfBoundsException("Invalid index for REMOVE operation, index = " + index);

			// if here, then the index is valid.
			Node<E> ntr = null; // node to remove
			if (index == 0) { // need to remove the first node and return its element.
				ntr = head;
				head = head.getNext();
			} else {
				Node<E> prev = findNode(index - 1); // node preceding the one to remove, why index - 1?
				ntr = prev.getNext(); // node to remove, notice that we find the predecessor node to the one we want
										// to remove
				prev.setNext(ntr.getNext()); // disconnect the node to remove from the List by deviating the predecessor
												// node's next
				// to the node to remove's next node
			}
			size--; // Decrement size
			return ntr.clear(); // Help the GC and return element in the node (see clear() in Node)
		}

		@Override
		public E set(int index, E e) throws IndexOutOfBoundsException {
			if (index < 0 || index >= size)
				throw new IndexOutOfBoundsException("Invalid index for SET operation, index = " + index);

			// if here, then the index is valid.
			Node<E> targetNode = findNode(index); // node whose element is to be replaced
			E etr = targetNode.getElement(); // element to be replaced
			targetNode.setElement(e); // replace current element by e
			return etr; // return the replaced element
		}

		@Override
		public void add(int index, E e) throws IndexOutOfBoundsException {
			if (index < 0 || index > size)
				throw new IndexOutOfBoundsException("Invalid index for ADD operation, index = " + index);

			// if here, then the index is valid.
			Node<E> newNode = new Node<>(e); // the new node to be linked to the LL
			if (index == 0) { // if true, then the new node shall become the new first
				newNode.setNext(head); // Notice that the previous condition is also true if size==0. Why?
				head = newNode;
			} else { // index > 0
				Node<E> prev = findNode(index - 1); // find node preceding location for insertion of new node
				newNode.setNext(prev.getNext()); // properly inserting the new node between prev and its next
				prev.setNext(newNode); // properly inserting the new node between prev and its next
			}

			size++;

		}

		@Override
		public void add(E e) {
			// just add at the end of the list.
			add(size, e);

		}

		// auxiliary method to find a specific node using a valid index
		private Node<E> findNode(int index) {
			// pre: index is valid; that is: index >= 0 and index < size.
			Node<E> target = head;
			for (int i = 0; i < index; i++)
				target = target.getNext();

			return target; // node representing position index in the list
		}

		@Override
		public int removeAll(E e) {
			int countRM = 0; // to count the number of removals
			Node<E> ntr = null;

			// If list is empty, return 0
			if (head == null)
				return 0;

			// Traverse list and check if the element we're traversing at the moment is the
			// one we are looking for
			while (head != null && head.getElement().equals(e)) {
				ntr = head;
				head = head.getNext();
				countRM++;
				ntr.clear();
			}

			if (head != null) {
				Node<E> current = head;
				while (current.getNext() != null) {
					if (current.getNext().getElement().equals(e)) { // Find predecessor node to the one we want to
																	// remove
						// Found it!
						ntr = current.getNext();
						current.setNext(ntr.getNext()); // Deviate predecessor's reference to the node to remove's next
						countRM++;
						ntr.clear(); // Avoid memory leaks, clear node
					} else
						current = current.getNext();
				}
			}

			size = size - countRM;

			return countRM;
		}

		/**
		 * Implement a member method for the IndexList ADT called indexesOf()
		 * 
		 * The method takes in as parameter a generic type element elm, and it should
		 * return an IndexList of type Integer.
		 * 
		 * The purpose of the method is to find all indexes of those positions in the
		 * target IndexList object in which there are copies of a given element
		 * 
		 * For example assume L = {1,2,3,2,4,5,2}
		 * 
		 * A call to this.indexesOf(L, 2); returns result = {1,3,6} A call to
		 * this.indexesOf(L, 8); returns result = {}
		 * 
		 * @param e
		 * @return an instance of IndexList that contains the positions in which e is
		 *         located in the target list
		 */
		@Override
		public IndexList<Integer> indexesOf(E elm) {
			/* ADD YOUR CODE HERE */
			IndexList<Integer> indexes = new LinkedIndexList<>();
			int y = 0;
			Node<E> curNode = head;
			while (curNode != null) {
				if (curNode.getElement().equals(elm)) {
					indexes.add(y);
				}
				y++;
				curNode = curNode.getNext();
			}
			return indexes;
		}

	}
}
