import java.util.Iterator;
import java.util.NoSuchElementException;


public class Lab02P2Wrapper {
	
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
	
    public static interface Set<E> extends Iterable<E>{

        public int size();
        public boolean isEmpty();
        public boolean contains(E obj);
        public boolean add(E obj);
        public boolean remove(E obj);
        public void clear();
        public Set<E> union(Set<E> S2); // this union S2
        public Set<E> difference(Set<E> S2);
        public Set<E> intersection(Set<E> S2);
        public boolean isSubset(Set<E> S2);
        public Object[] toArray();
        public Set<Set<E>> singletonSets();

    }

    public static class ArraySet<E> implements Set<E> {

        private Bag<E> bag;

        private static final int DEFAULT_SIZE = 10;

        public ArraySet(int initialSize){
            if (initialSize < 1){
                throw new IllegalArgumentException("Set size must be at least 1.");
            }
            
            bag = new ArrayBag<E>(initialSize);
        }
        public ArraySet(){
            this(DEFAULT_SIZE);
        }
        
        
        public int size() {
            return bag.size();
        }

        
        public boolean isEmpty() {
            return size() == 0;
        }

        
        public boolean contains(E obj) {
            
            return bag.contains(obj);
        }

        
        public boolean add(E obj) {
            if (this.contains(obj))
                return false;
            
            return bag.add(obj);
            
        }


        
        public boolean remove(E obj) {
            
            return bag.remove(obj);
        }

        
        public void clear() {
            bag.clear();
        }


        
        public Set<E> union(Set<E> S2) {
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

        
        public Set<E> difference(Set<E> S2) {
        	ArraySet<E> S3 = new ArraySet<E>();
            for(E e: this ) {
         	   if(!S2.contains(e))
         		   S3.add(e);
            }
            return S3;
        }

        
        public Set<E> intersection(Set<E> S2) {
        /*        
  		  Set<E> result = new ArraySet<E>();
          for (int i =0; i < this.size(); ++i){
              if (S2.isMember(this.elements[i])){
                  result.add(this.elements[i]);
              }
          }
          return result; //Fo! too CIIC4010/ICOM4015
        */

            // in one line??

            return this.difference(this.difference(S2));
        }

        
        public boolean isSubset(Set<E> S2) {

          /*
            // S1 = this
            // S2 = S2
            // If S1 is subset of S2
          for (int i=0; i < this.size(); ++i){
              if (!S2.isMember(this.elements[i])){
                 return false;
              }
          }
          return true; // Not cool
          */

            return this.difference(S2).isEmpty();
        }

        
        public Object[] toArray() {
            if (this.size() == 0){
                return null;
            }
            else {
                Object[] result = new Object[this.size()];
                int i = 0;
                for (E elm : this) {
					result[i] = elm;
					i++;
				}
                return result;
            }
        }

        public Iterator<E> iterator(){
        	return bag.iterator();
        }
        
		
		/**
         * Consider a member method for the Set ADT called singletonSets(). 
         * Given a set A = {x0, x1, …, xn}, the singleton set of A is a collection of sets (i.e. a set of sets), 
         * each containing only one element of A:
         * 		S = {{x0}, {x1}, …, {xn}}. 
         * 
         * Implement this method in the ArraySet class
         * 
         * HINT: Set<Set<E>> result = new ArraySet<Set<E>>(this.size());
         * 
         * @return A set in which each element is one single element from the target set 
         */
        public Set<Set<E>> singletonSets() {
        	
        	Set<Set<E>> result = new ArraySet<Set<E>>(this.size());
        	
        	for(E elm : this) {
        		Set<E> set = new ArraySet<E>();
        		set.add(elm);
        		result.add(set);
        	}
			/*ADD YOUR CODE HERE*/
			
			return result; //Dummy Return, remove this
        	
        }
    }

}