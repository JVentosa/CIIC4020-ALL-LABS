public class Lab03P3Wrapper {

	public static interface IndexList<E> {
		public int size();

		public boolean isEmpty();

		public E get(int index) throws IndexOutOfBoundsException;

		public E remove(int index) throws IndexOutOfBoundsException;

		public E set(int index, E e) throws IndexOutOfBoundsException;

		public void add(int index, E e) throws IndexOutOfBoundsException;

		public void add(E e);

		public int removeAll(E e);

		public void insertAfter(E targetElement, E newElement);
	}

	public static class ArrayIndexList<E> implements IndexList<E> {

		// Private Fields
		private static final int INITIAL_CAPACITY = 10;
		private E[] elements; // the internal array
		private int currentSize; // Current number of elements in the list

		// Constructor
		@SuppressWarnings("unchecked")
		public ArrayIndexList() {
			elements = (E[]) new Object[INITIAL_CAPACITY];
			currentSize = 0;
		}

		/* MEMBER METHODS FOR ArrayIndexList ADT */
		@Override
		public int size() {
			return currentSize;
		}

		@Override
		public boolean isEmpty() {
			return currentSize == 0;
		}

		@Override
		public E get(int index) throws IndexOutOfBoundsException {
			if (index < 0 || index >= currentSize)
				throw new IndexOutOfBoundsException("Invalid index for GET operation, index = " + index);

			return elements[index];
		}

		@Override

		public E remove(int index) throws IndexOutOfBoundsException {

			if (index < 0 || index >= currentSize)
				throw new IndexOutOfBoundsException("Invalid index for REMOVE operation, index = " + index);

			/* If we get here, then the index is valid and such element exist in the list */
			E etr = elements[index]; // element to be removed

			/*
			 * Now we shift elements from position index+1 to size-1 one position to the
			 * left
			 */
			for (int i = index; i < currentSize - 1; i++)
				elements[i] = elements[i + 1];

			elements[currentSize - 1] = null; // We set the element to remove to null to avoid memory leaks

			currentSize--; // Decrement current size of list

			return etr;
		}

		@Override
		public E set(int index, E e) throws IndexOutOfBoundsException {
			if (index < 0 || index >= currentSize)
				throw new IndexOutOfBoundsException("Invalid index for SET operation, index = " + index);

			// if here, then the index is valid and such element exist in the list

			E etr = elements[index]; // element to be replaced

			elements[index] = e; // element replaced by e

			return etr;
		}

		@Override
		public void add(int index, E e) throws IndexOutOfBoundsException {

			/* If the index is not from [0, size() -1] then we throw an exception */
			if (index < 0 || index > currentSize)
				throw new IndexOutOfBoundsException("Invalid index for ADD operation, index = " + index);

			if (currentSize == elements.length)
				doubleCapacity();

			for (int i = currentSize - 1; i >= index; i--)
				elements[i + 1] = elements[i];

			elements[index] = e;

			currentSize++;

		}

		@Override
		public void add(E e) {
			add(currentSize, e);
		}

		@SuppressWarnings("unchecked")
		private void doubleCapacity() {
			int newCapacity = 2 * elements.length;
			E[] newArr = (E[]) new Object[newCapacity];

			for (int i = 0; i < currentSize; i++) {
				newArr[i] = elements[i];
				elements[i] = null;
			}

			elements = newArr;
		}

		@Override
		public int removeAll(E e) {
			int countRM = 0;
			for (int i = 0; i < currentSize; i++) {
				if (elements[i].equals(e)) {
					countRM++;
					elements[i] = null;
				} else if (countRM > 0) {
					elements[i - countRM] = elements[i]; // arr[i] is shifter countRM positions to the left...
					elements[i] = null;
				}
			}

			currentSize = currentSize - countRM;

			return countRM;
		}

		/**
		 * Implement a member method for the IndexList ADT called insertAfter() The
		 * methods receives as parameter two generic type elements.
		 * 
		 * The method inserts a newElement right after every current occurrence in the
		 * list, if any, of targetElement.
		 * 
		 * For example: If the list is L = {3, 4, 2, 3, 5, 2, 1, 2, 3}, then (consider
		 * each of the following operations applied to this original list)
		 * 
		 * L.insertAfter(2, 5); would leave the list as: {3, 4, 2, 5, 3, 5, 2, 5, 1, 2,
		 * 5, 3} L.insertAfter(2, 2); would leave the list as: {3, 4, 2, 2, 3, 5, 2, 2,
		 * 3, 2, 2, 3} L.insertAfter(7, 1); would leave the list as it is, because there
		 * is no occurrence of 7 in the list.
		 * 
		 * @param targetElement
		 * @param newElement
		 */
		@Override
		public void insertAfter(E targetElement, E newElement) {
			/* ADD YOUR CODE HERE */
			for (int i = 0; i < this.size(); i++) {
				if (this.get(i).equals(targetElement)) {
					this.add(i + 1, newElement);
					i++;
				}
			}
		}

	}

}
