import java.util.Iterator;

@SuppressWarnings("unused")
public class Lab03P1Wrapper {

	public static interface IndexList<E> {
		public int size();

		public boolean isEmpty();

		public E get(int index) throws IndexOutOfBoundsException;

		public E remove(int index) throws IndexOutOfBoundsException;

		public E set(int index, E e) throws IndexOutOfBoundsException;

		public void add(int index, E e) throws IndexOutOfBoundsException;

		public void add(E e);

		public int removeAll(E e);
	}

	/**
	 * 
	 * @author pedroi.rivera-vega
	 *
	 * @param <E>
	 */
	public static class ArrayIndexList<E> implements IndexList<E> {

		private static final int INITIAL_CAPACITY = 10;
		private E[] elements;
		private int currentSize;

		@SuppressWarnings("unchecked")
		public ArrayIndexList() {
			elements = (E[]) new Object[INITIAL_CAPACITY];
			currentSize = 0;
		}

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

			E etr = elements[index];

			for (int i = index; i < currentSize - 1; i++)
				elements[i] = elements[i + 1];

			elements[currentSize - 1] = null;
			currentSize--;
			return etr;
		}

		@Override
		public E set(int index, E e) throws IndexOutOfBoundsException {
			if (index < 0 || index >= currentSize)
				throw new IndexOutOfBoundsException("Invalid index for SET operation, index = " + index);

			E etr = elements[index];

			elements[index] = e;

			return etr;
		}

		@Override
		public void add(int index, E e) throws IndexOutOfBoundsException {
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
					elements[i - countRM] = elements[i];
					elements[i] = null;
				}
			}
			currentSize = currentSize - countRM;
			return countRM;
		}

	}

	/**
	 * Implement a non-member static method for the IndexList ADT called indexesOf()
	 * 
	 * The method takes in as parameter an instance of IndexList<E> and generic type
	 * element e, and it should return an IndexList of type Integer.
	 * 
	 * The purpose of the method is to find all indexes of those positions in a
	 * given IndexList object in which there are copies of a given element
	 * 
	 * For example assume L = {1,2,3,2,4,5,2}
	 * 
	 * A call to indexesOf(L, 2); returns result = {1,3,6} A call to indexesOf(L,
	 * 8); returns result = {}
	 * 
	 * @param <E>
	 * @param list
	 * @param e
	 * @return an instance of IndexList that contains the positions in which e is
	 *         located in list
	 */
	public static <E> IndexList<Integer> indexesOf(IndexList<E> list, E e) {
		/* ADD YOUR CODE HERE */
		IndexList<Integer> indexes = new ArrayIndexList<>();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).equals(e)) {
				indexes.add(i);
			}
		}
		return indexes;

	}

}
