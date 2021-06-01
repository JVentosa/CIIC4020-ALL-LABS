import org.w3c.dom.Node;

@SuppressWarnings("unused")
public class Lab06P2Wrapper {

	@SuppressWarnings("serial")
	public static class EmptyQueueException extends RuntimeException {
		public EmptyQueueException(String message) {
			super(message);
		}
	}

	public static interface Queue<E> {
		int size();

		boolean isEmpty();

		E getFront() throws Lab06P2Wrapper.EmptyQueueException;

		void enqueue(E element);

		E dequeue() throws Lab06P2Wrapper.EmptyQueueException;
	}

	public static class ArrayQueue<E> implements Queue<E> {
		private static final int INITCAP = 10;
		private E[] elements; // the array
		private int first; // the index of the first element in the queue... If size != 0, the elements
							// are:
							// elements[first], elements[(first+1)%elements.length, ...,
							// elements[(first+size-1)%elements.length.
		private int size;

		@SuppressWarnings("unchecked")
		public ArrayQueue() {
			elements = (E[]) new Object[INITCAP];
			first = 0;
			size = 0;
		}

		@Override
		/**
		 * Adds a new element to the end of the queue.
		 */
		public void enqueue(E newEntry) {
			if (size == elements.length) {
				doubleCapacity();
				/* ADD CODE HERE */
			}
			int canit = (first + this.size()) % elements.length;

			elements[canit] = newEntry;
			this.size++;

			/* ADD CODE HERE */

		}

		@Override
		/**
		 * Extracts and returns the first element in the queue. Throws an exception if
		 * the queue is empty.
		 */
		public E dequeue() throws Lab06P2Wrapper.EmptyQueueException {
	
			 E etr = elements[first];
	            
	            /*ADD YOUR CODE HERE*/
	            
	            if (isEmpty())
	                throw new EmptyQueueException("Queue is empty");
	            else
	            {
	                size --;

	               elements[first] = null;     

	               first++;
	               if (first == elements.length) {
	                   first = 0;
	               }        
	            }
	            return etr; 
		}

		@Override
		public E getFront() throws Lab06P2Wrapper.EmptyQueueException {
			if (isEmpty())
				throw new EmptyQueueException("The queue is empty.");
			return elements[first];
		}

		@Override
		public boolean isEmpty() {
			return size == 0;
		}

		@Override
		public int size() {
			return size;
		}

		@SuppressWarnings("unchecked")
		public void clear() {
			for (int i = 0; i < size; i++) {
				elements[(first + i) % elements.length] = null; // support GC
			}

			// just leave it with the initial capacity
			elements = (E[]) new Object[INITCAP];

			first = 0;

			size = 0;
		}

		@SuppressWarnings("unchecked")
		private void doubleCapacity() {
			/* ADD YOUR CODE HERE */
			E[] newArray = (E[]) new Object[2 * elements.length];
			for (int i = 0; i < size; i++) {
				newArray[i] = elements[(first + i) % elements.length];
				elements[(first + i) % elements.length] = null;
			}
			first = 0;
			elements = newArray;

		}
	}

}
