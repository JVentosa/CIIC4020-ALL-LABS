import java.util.Iterator;
import java.util.NoSuchElementException;

public class Lab08P3Wrapper {
	
	public static class ArrayIndexList<E> extends AbstractIndexList<E> {
		private static final int INITIAL_CAPACITY = 10; 
		
		private E[] arr;  // the internal array
		private int size;   // number of elements in the list
		
		@SuppressWarnings("unchecked")
		public ArrayIndexList() { 
			arr = (E[]) new Object[INITIAL_CAPACITY]; 
			size = 0; 
		}

		@Override
		public int size() {
			return size;
		}

		@Override
		public boolean isEmpty() {
			return size == 0;
		}

		@Override
		public E get(int index) throws IndexOutOfBoundsException {
	        if (index < 0 || index >= size)
	            throw new IndexOutOfBoundsException("Invalid index for GET operation, index = " + index); 
	        return arr[index];
		}

		@Override
		public E remove(int index) throws IndexOutOfBoundsException {
	        if (index < 0 || index >= size)
	            throw new IndexOutOfBoundsException("Invalid index for REMOVE operation, index = " + index); 

			E etr = arr[index];     // element to be removed
			
			for (int i=index; i<size-1; i++)
				arr[i] = arr[i+1]; 
			
			arr[size-1] = null;    // help GC
			
			size--;    // adjust size 
			
	        return etr;
		}

		@Override
		public E set(int index, E e) throws IndexOutOfBoundsException {
	        if (index < 0 || index >= size)
	            throw new IndexOutOfBoundsException("Invalid index for SET operation, index = " + index);
	        E etr = arr[index];     // element to be replaced
			
			arr[index] = e;     // element replaced by e
			
			return etr;
		}

		@Override
		public void add(int index, E e) throws IndexOutOfBoundsException {
	        if (index < 0 || index > size)
	            throw new IndexOutOfBoundsException("Invalid index for ADD operation, index = " + index); 
	        if (size == arr.length)
	        	doubleCapacity();  
	        for (int i = size-1; i>=index; i--) 
	        	arr[i+1] = arr[i];
	        arr[index] = e; 
	        
	        size++; 
			
		}

		@Override
		public void add(E e) { 
			add(size, e);	
		} 
		private void doubleCapacity() { 
			int newCapacity = 2*arr.length; 
			
			@SuppressWarnings("unchecked")
			E[] newArr = (E[]) new Object[newCapacity];
			
			for (int i=0; i< size; i++) { 
				newArr[i] = arr[i]; 
				arr[i] = null; 
			}
			
			arr = newArr; 
		}
		
		@Override
		public int removeAll(E e) { 
			int countRM = 0; 
			for (int i=0; i<size; i++) {
				if (arr[i].equals(e)) {
					countRM++; 
					arr[i] = null; 
				}
				else if (countRM > 0){
					arr[i-countRM] = arr[i];
					arr[i] = null; 
				}
			}
			
			size = size - countRM; 
			
			return countRM;
		}
	}
	
	public static class ExternalIndexListIterator<E> implements Iterator<E> {
		
		private IndexList<E> theList; 
		private int current; 
		private boolean validToRemove = false; 
		
		public ExternalIndexListIterator(IndexList<E> list) { 
			theList = list; 
			current = 0; 
		}

		@Override
		public boolean hasNext() {
			return current < theList.size();
		}

		@Override
		public E next() {
			if (!hasNext()) 
				throw new NoSuchElementException("No more elements."); 

			E etr = theList.get(current); 
			current++; 
			validToRemove = true; 
			return etr;
		}

		@Override
		public void remove() {
			if (!validToRemove) 
				throw new IllegalStateException(); 
			
			theList.remove(current-1); 
			
			current--; 
			
			validToRemove = false; 	
		}

		
	}
	
	public static interface IndexList<E> extends Iterable<E> { 
		   int size(); 
		   boolean isEmpty(); 
		   E get(int index) throws IndexOutOfBoundsException; 
		   E remove(int index) throws IndexOutOfBoundsException; 
		   E set(int index, E e) throws IndexOutOfBoundsException; 
		   void add(int index, E e) throws IndexOutOfBoundsException; 
		   void add(E e);
		   int removeAll(E e);
		   
		   /** If arguments are valid, the method returns an Iterable<E> 
		    that allows iteration over the elements of the given IndexList, 
		    starting at position initialIndex and incrementing the index by    
		    the value of the parameter step. See the example below. 
		    @param initialIndex - the index of the first first element
		       in the list to be visited by the implicit iterator. 
		    @param step - the increment of indexes of elements to be 
		       visited. 

		    Indexes are: initialIndex, initialIndex+step, 
		                 initialIndex + 2*step, etc, until the index 
		                 exceeds size-1 (which is the last possible index)
		    @throws IllegalArgumentException when initialIndex < 0 or
		                 step <= 0. 
		**/
		Iterable<E> sequence(int initialIndex, int step);
	}

	public static class LinkedIndexList<E> extends AbstractIndexList<E> {
		private static class Node<E> { 
			private E element; 
			private Node<E> next; 
			public Node(E element, Node<E> next) { 
				this.element = element; 
				this.next = next; 
			}
			public Node(E element) { 
				this(element, null); 
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

			// Just to help the GC whenever a node is to be discarded. 
			public E clear() { 
				E etr = element; 
				element = null; 
				next = null; 
				return etr; 
			}
		} // end of class Node


		// instance fields for the class LinkedIndexList
		private Node<E> first;    // references first node
		private int size;   // number of elements in the list
		
		public LinkedIndexList() { 
			first = null; 
			size = 0; 
		}
		
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
	        Node<E> ntr = null;       // node to remove
	        if (index == 0) {      // need to remove the first node and return its element.  
	           ntr = first; 
	           first = first.getNext(); 
	        } 
	        else { 
	           Node<E> prev = findNode(index-1);   // node preceding the one to remove
	           ntr = prev.getNext();               // node to remove
	           prev.setNext(ntr.getNext());        // disconnect the node to remove from the LL
	        }
	        size--; 
	        return ntr.clear();    // Help the GC and return element in the node (see clear() in Node)
	    } 


		@Override
		public E set(int index, E e) throws IndexOutOfBoundsException {
	        if (index < 0 || index >= size)
	            throw new IndexOutOfBoundsException("Invalid index for SET operation, index = " + index); 

	        // if here, then the index is valid. 
	        Node<E> targetNode = findNode(index);   // node whose element is to be replaced
	        E etr = targetNode.getElement();        // element to be replaced
	        targetNode.setElement(e);               // replace current element by e
	        return etr;                             // return the replaced element
		}

		@Override
		public void add(int index, E e) throws IndexOutOfBoundsException {
	        if (index < 0 || index > size)
	            throw new IndexOutOfBoundsException("Invalid index for ADD operation, index = " + index); 

	        // if here, then the index is valid. 
	        Node<E> nuevoNodo = new Node<>(e);      // the new node to be linked to the LL
	        if (index == 0) {              // if true, then the new node shall become the new first
	           nuevoNodo.setNext( first );  // Notice that the previous condition is also true if size==0. Why?
	           first = nuevoNodo; 
	        }
	        else {                                  // index > 0
	           Node<E> prev = findNode(index-1);    // find node preceding location for insertion of new node
	           nuevoNodo.setNext(prev.getNext());   // properly inserting the new node between prev and its next
	           prev.setNext(nuevoNodo);             // properly inserting the new node between prev and its next
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
			Node<E> target = first; 
			for (int i=0; i<index; i++)
				target = target.getNext(); 
			
			return target;    // node representing position index in the list
		}

		@Override
		public int removeAll(E e) {
			int countRM = 0;          // to count the number of removals
			Node<E> ntr = null; 
			
			if (first == null) 
				return 0; 
			
			while (first != null && first.getElement().equals(e)) { 
				ntr = first; 
				first = first.getNext();
				countRM++; 	
				ntr.clear(); 
			}
			
			if (first != null) { 
				Node<E> current = first; 
				while (current.getNext() != null) { 
					if (current.getNext().getElement().equals(e)) { 
						ntr = current.getNext(); 
						current.setNext(ntr.getNext()); 
						countRM++; 
						ntr.clear(); 
					}
					else
						current = current.getNext(); 
				}
			}
			
			size = size - countRM; 
			
			return countRM; 
		}
		
		
		@Override
		public Iterator<E> iterator() {
			return new ElementsIterator(); 
		}


		private class ElementsIterator implements Iterator<E> {
			
		     private Node<E> curr = first;    // node containing element to return on next next()
		     private Node<E> ptntr = null;   // node preceding node valid to be removed
		     private boolean canRemove = false;       // to control when remove() is valid to execute
		     public boolean hasNext() { return curr != null; }    
		     public E next() { 
		         if (!hasNext()) throw new NoSuchElementException("Iterator is completed.");
		         if (canRemove) ptntr = (ptntr == null ? first : ptntr.getNext());  // Why this? Think...
		         canRemove = true;    
		         E etr = curr.getElement();   curr = curr.getNext();   // get element and prepare for future
		         return etr; 
		     } 
		     public void remove() { 
		         if (!canRemove) throw new IllegalStateException("Not valid to remove."); 
		         if (ptntr == null) first = first.getNext();             // removes the first node
		         else ptntr.setNext(ptntr.getNext().getNext());     // removes node after ptntr     
		         size--;    canRemove = false; 
		     }   

		}

	}
	
	public static abstract class AbstractIndexList<E> implements IndexList<E> {
		
		@Override
		public Iterator<E> iterator() {
			return new ExternalIndexListIterator<E>(this); 
		}

		
		@Override
		/*TODO ADD YOUR CODE HERE*/
		public Iterable<E> sequence(int initialIndex, int step) { 
			if (initialIndex < 0 || step <= 0) {
                throw new IllegalArgumentException("Invalid Argument: InitialStep = "+ "and step = "+ step);

            }
            Iterator<E> iterator = this.iterator();
            ArrayIndexList<E> nextElement = new ArrayIndexList<>();

            int i = 0;
            while (iterator.hasNext() && i < initialIndex) {
                iterator.next();
                i+=1;

            }
            while (iterator.hasNext()) {
                nextElement.add(iterator.next());
                i=1;
                while(iterator.hasNext() && i< step) {
                iterator.next();
                i+=1;
                }
            }
            return nextElement;   // Using the fact that ArrayIndexList<E> is Iterable<E>
			/*
			 * KEY IDEA:
			 * 		1) If arguments are invalid, we throw an IllegalArgumentExeption
			 * 		2) If not, we save the iterator and create an IndexList 
			 * 		   to store the elements to iterate over
			 * 		3) We set up a index so we find the first element to be iterated over (while the iterator has a next)
			 * `	4) If there are more elements after this, then then next one returned by the iterator
			 * 		   would be the first one of the elements to iterate over by the method
			 * 		5) While the loop does it's job, store the e that are eing iterated over
			 * 			-> Keep in mind of the step!
			 * 		6) Return the list with the elements iterated over
			 * 
			 * */
			
		}
	}
}
