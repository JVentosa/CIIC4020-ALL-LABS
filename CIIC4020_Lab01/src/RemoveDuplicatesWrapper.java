import java.util.Iterator;
import java.util.NoSuchElementException;

//import jdk.internal.org.jline.reader.impl.history.DefaultHistory;



public class RemoveDuplicatesWrapper {

	// Bag Interface
	public interface Bag<E> extends Iterable<E> {
		// Adds element to bag
		public void add(E elm);
		// Removes element elm from bag
		public boolean remove(E elm);
		// Removes last element from bag
		public void remove();
		// Removes all instances of element elm
		public int removeAll(E elm);
		// Empties the bag
		public void clear();
		// Returns the how many instances of elm there are in the bag
		public int getFrequencyOf(E elm);
		// Returns true if elm in present in the bag
		public boolean contains(E elm);
		// Returns the current size of the list
		public int size();
		// Return whether the list is empty
		public boolean isEmpty();
		// Returns an array with the elements of the bag
		public E[] toArray();
		// Iterator for the bag
		public Iterator<E> iterator();
	}

	/**
	 * Implementation of a Static Sized Bag 
	 * using an array of a generic type E
	 * 
	 * @author bermed28
	 *
	 * @param <E>
	 */
	public static class ArrayBag<E> implements Bag<E> {
		
		private int currentSize;
		private E[] elements;
		
		private final static int DEFAULT_SIZE = 25;
		
		public ArrayBag() {
			this(DEFAULT_SIZE);
		}
		
		@SuppressWarnings("unchecked")
		public ArrayBag(int maxCapacity) {
			if(maxCapacity < 1) {
				elements = (E[]) new Object[DEFAULT_SIZE];
			} else {
				elements = (E[]) new Object[maxCapacity];
			}
			
			currentSize = 0;
		}
		
		
		private class BagIterator<T> implements Iterator<T>{
			
			int currentPosition;
			

			public boolean hasNext() {
				return currentPosition < size();
			}

		
			@SuppressWarnings("unchecked")
			public T next() {
				if(this.hasNext()) {
					return (T) elements[currentPosition++];
				} else {
					throw new NoSuchElementException();
				}
			}	
			
		}

		public void add(E elm) {
			if(size() == elements.length) 
				throw new IllegalArgumentException("Bag is Full!");
			
			
			elements[currentSize++] = elm;

		}
		
		public void remove() {
			if(!this.isEmpty()){
				elements[currentSize-1] = null;
				currentSize--;
			}
		}

		public boolean remove(E elm) {
			for (int i = 0; i < size(); i++) {
				if(elements[i].equals(elm)) {
					elements[i] = elements[--currentSize];
					elements[currentSize] = null;
					return true;
				}
			}
			
			return false;
		}

		public int removeAll(E elm) {
			int copiesRemoved = 0;
			
			while(remove(elm))
				copiesRemoved++;
			
			
			return copiesRemoved;
		}

		public void clear() {
			for (int i = 0; i < elements.length; i++) 
				elements[i] = null;
			
			currentSize = 0;
		}

		public int getFrequencyOf(E elm) {
			int copies = 0;
			
			for (int i = 0; i < size(); i++) 
				if(elements[i].equals(elm)) copies++;
			
			return copies;
		}

		public boolean contains(E elm) {
			return getFrequencyOf(elm) > 0;
		}

		public int size() {
			return currentSize;
		}

		public boolean isEmpty() {
			return size() == 0;
		}
		
		@SuppressWarnings("unchecked")
		public E[] toArray() {
			Object[] arr = new Object[size()];
			
			for (int i = 0; i < size(); i++) {
				arr[i] = (Object) elements[i];
			}
			return (E[]) arr;
		}

		public Iterator<E> iterator() {
			return new BagIterator<E>();
		}
	}
		
	/**
	 * Implement a non-member method for the Bag ADT called removeDuplicates()
	 * 
	 * This method receives a Bag<E> as parameter and removes all duplicates from it. 
	 * The method returns the amount of duplicates removed from the bag.
	 * 
	 * The method MODIFIES the bag B
	 * 
	 * Example:
	 * Bag b = {a, b, a, c, d, a, b}
	 * removeDuplicates(b) should return 3
	 * After the method b should have: {a, b, c, d}
	 * 
	 * @param B - bag to search for duplicates
	 * @return the amount of duplicates that were removed from B
	 */	
	public static <E> int removeDuplicates(Bag<E> B) {
		/**
		 * ADD YOUR CODE HERE
		 * 
		 * ONLY COPY TO MOODLE THE CODE 
		 * IMPLEMENTED INSIDE THIS METHOD
		 */
		int duplicate = 0;
		for(E elm : B) {
			if(B.getFrequencyOf(elm) > 1) {
				B.remove();
				duplicate++;
			}
		}
		return duplicate; //Dummy Return
		
	}

}