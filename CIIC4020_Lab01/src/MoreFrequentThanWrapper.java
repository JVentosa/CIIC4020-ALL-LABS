import java.util.Iterator;
import java.util.NoSuchElementException;


public class MoreFrequentThanWrapper {

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
		// Method to be implemented
		public Bag<E> moreFrequentThan(E elm);
	}

	/**
	 * Implementation of a Static Sized Bag 
	 * using an array of a generic type E
	 * 
	 * @author Fernando J. Bermudez
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
		
        /**
         * Implement a member method for the Bag ADT called moreFrequentThan(E obj). 
         * 
         * The method is Non-mutator, meaning it does not modify the target object.
         * 
         * The method receives a parameter of type E and returns a new Bag with all
         * the elements than appear more frequent than obj.
         * 
         * NOTE:
         * - If the Bag is empty return an empty bag. 
         * - This method should not affect the original bag.
         * - The order of the elements is irrelevant (bags are unordered). 
         * - The new Bag that is returned shouldn't have duplicates.
         * 
         * Example:
         * Bag B = {Joe, Ned, Kim, Joe, Ned, Joe, Ari, Lu}
         * Bag E = B.moreFrequentThan(Lu)
         * 
         * Then B and E should look like this:
         * B = {Joe, Ned, Kim, Joe, Ned, Joe, Ari, Lu}
         * E = {Joe, Ned}
         * 
         * @param obj - The element that will determine which elements are more frequent than this one
         * @return A new Bag<E> with all the elements more frequent than obj
         */
        public Bag<E> moreFrequentThan(E obj) {
        	
        	/**
        	 *ADD YOUR CODE HERE
        	 *
        	 *WHEN YOU ARE DONE, 
        	 *ONLY COPY WHAT IS INSIDE
        	 *OF THIS METHOD TO MOODLE
        	 */
        	Bag<E> freq = new ArrayBag<E>(this.size());
            int frequent = this.getFrequencyOf(obj);
            for(E elm : this) {
                if(this.getFrequencyOf(elm) > frequent && !freq.contains(elm)) {
                    freq.add(elm);
                }
            }
        	
        	return freq; //Dummy Return
        	
        	
        }

    }

}
