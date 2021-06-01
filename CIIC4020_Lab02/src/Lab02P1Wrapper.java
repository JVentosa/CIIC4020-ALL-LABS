import java.util.Iterator;
import java.util.NoSuchElementException;


public class Lab02P1Wrapper {
	
	public interface Bag<E> extends Iterable<E> {
		
		public boolean add(E o);
		public boolean remove(E o);
		public E remove();
		public int removeAll(E o);
		public void clear();
		public int getFrequencyOf(E o);
		public boolean contains(E o);
		public int size();
		public boolean isEmpty();
		public E[] toArray();		
		
	}
	
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

		public boolean add(E elm) {
			if(size() == elements.length) 
				return false;
			
			
			elements[currentSize++] = elm;
			return true;

		}
		
		public E remove() {
			if(!this.isEmpty()){
				E last = elements[currentSize -1];
				elements[currentSize-1] = null;
				currentSize--;
				return last;
			}
			return null;
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
	 * Implementation of Static-Size Set using Bag Inheritance
	 * @author Gretchen Bonilla
	 *
	 * @param <E>
	 */

	public static class ArraySet<E> extends ArrayBag<E> {

	    public ArraySet(int initialSize){
	        super(initialSize);
	    }
	    public ArraySet(){
	        super();
	    }
	  
	    @Override
	    public boolean add(E obj) {
	        if (this.contains(obj)){
	            return false;
	        }
	        else {
	           super.add(obj);
	           return true;
	        }

	    }
	  
	    public ArraySet<E> union(ArraySet<E> S2) {
	        // S3 = S1(this) U S2
	    	ArraySet<E> S3 = new ArraySet<E>(this.size() + S2.size());
	    	// Add all elements of S1 (this)
	    	for(E e: this) {
	    		S3.add(e);
	    	}
	    	// Add all elements of S2
	    	for (E e: S2) {
	    		// Add will take care of duplicates
	    		S3.add(e);
	    	}
	        
	        return S3;
	    }

	    public ArraySet<E> difference(ArraySet<E> S2) {
	       ArraySet<E> S3 = new ArraySet<E>();
	       for(E e: this ) {
	    	   if(!S2.contains(e))
	    		   S3.add(e);
	       }
	       return S3;
	    }

	    public ArraySet<E> intersection(ArraySet<E> S2) {

	        return this.difference(this.difference(S2));
	    }

	    public boolean isSubset(ArraySet<E> S2) {

	        return this.difference(S2).isEmpty();
	    }
    }

    /**
     * Suppose you are given an array of ArraySet<Integer> instances. 
     * Write a non-member static method called checkDisjoint() 
     * which returns true if the intersection of the array is empty. 
     * If the array is empty, the method returns true
     * 
     * Hint: Remember that an intersection is just all of the elements that the Sets have in common. 
     * If there are no elements in common, the intersection should be empty!
     * 
     * @param sets - Array of Set<Integer>
     * @return True or False if the intersection of the array is empty
     */
    public static boolean checkDisjoint(ArraySet<Integer>[] sets){
    	/*ADD YOUR CODE HERE*/
    	
    	ArraySet<Integer> S1 = null;
    	ArraySet<Integer> S2 = null;
    	
    	for(int i = 0; i < sets.length; i++) {
    		S1 = sets[i];
    		for(int j = 0; j < sets.length; j++) {
    			if (i != j) {
    				S2 = sets[j];
    				if(!S1.intersection(S2).isEmpty()) {
    					return false;
    				}
    			}
    		}
    	}
    	return true;
    }
   }
