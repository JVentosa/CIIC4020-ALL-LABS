import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;



public class Lab02P3Wrapper {
	
	public static interface Bag<T> extends Iterable<T> {
		
		public boolean add(T o);
		public boolean remove(T o);
		public T remove();
		public int removeAll(T o);
		public void clear();
		public int getFrequencyOf(T o);
		public boolean contains(T o);
		public int size();
		public boolean isEmpty();
		public T[] toArray();
		
		// Method to implement
		public void replace(T oldValue, T newValue);
		
	}
	
	public static class LinkedBag<T> implements Bag<T> {
		// the inner class for the data type of nodes in the singly linked list
		private class Node { 
			private T data;       // reference to the data element that the node holds
			private Node next;    // reference to the next node in the linked list
			private Node(T data, Node next) { 
				this.data = data; 
				this.next = next; 
			}
			private void clear() { 
				data = null; 
				next = null; 
			}
			public T getData() {
				return data;
			}
			public void setData(T data) {
				this.data = data;
			}
			public Node getNext() {
				return next;
			}
			@SuppressWarnings("unused")
			public void setNext(Node next) {
				this.next = next;
			}
			
		}

		private Node first;    // reference to the first node of the SLL holding
		                       // the elements of this bag. Notice that "first" is just
							   // a reference to a node (not a node!).
		private int currentSize;      // size of the bag.
		
		/**
		 * Initializes an empty bag of type LinkedBag. 
		 */
		public LinkedBag() { 
			first = null;     // the SLL has no nodes in an empty bag of this type
			currentSize = 0;         // the size is 0
		}
		@SuppressWarnings("hiding")
		private class BagIterator<T> implements Iterator<T>{
			
			Node currentPosition = null;
			
			public BagIterator() {
				currentPosition = first;
			}
			

			public boolean hasNext() {
				return currentPosition != null;
			}

		
			@SuppressWarnings("unchecked")
			public T next() {
				if(this.hasNext()) {
					Node ntr = currentPosition; // Node to return
					currentPosition = currentPosition.getNext();
					return (T) ntr.getData();
				} else {
					throw new NoSuchElementException();
				}
			}	
			
		}
		
		
		public int size() {
			return currentSize;
		}

		
		public boolean isEmpty() {
			return size() == 0;    
		}

		
		public boolean add(T newEntry) {
			
			Node newNode = new Node(newEntry, first); 
			first = newNode; 
			currentSize++; 
			return true;
		}

		
		
		public T remove() { 
			
			if (isEmpty()) return null; 
			
			Node ntr = first;   		
			first = first.getNext();  
			                    			
			currentSize--;   
			
			T etr = ntr.getData();    
			ntr.clear();      
			             

			return etr;
		}

		
		public boolean remove(T anEntry) {
			Node target = findNode(anEntry); 
			if (target == null)
				return false;    
			
	 
			if (target != first) 
				target.setData(first.getData());   
				
	
			Node ntr = first;     
			first = first.getNext();   

			
			currentSize--;  
			
	
			ntr.clear(); 
			
			return true;
		}

		
		public void clear() {
			
			while (first != null) { 
				Node ntr = first; 
				first = first.getNext(); 
				ntr.clear();
			}
			currentSize = 0; 
			
		}

		
		public int getFrequencyOf(T anEntry) {
			
			int count = 0; 
			Node current = first; 
			while (current != null) {
				if (current.getData().equals(anEntry)) 
					count++; 
				current = current.getNext(); 
			}
			return count;
		}

		
		public boolean contains(T anEntry) {
			return findNode(anEntry) != null;
		}

		@SuppressWarnings("unchecked")
		public T[] toArray() { 
			T[] result = (T[]) new Object[currentSize]; 
			Node current = first; 
			for (int index = 0; current != null; index++, current = current.getNext())
				result[index] = current.getData(); 
			return result;
		}

		/**
		 * Internal private method to find the first node, if any, 
		 * that contains a given element. 
		 * 
		 * @param taget - the element to search for.
		 * 
		 * @return null -  if target is nod found in the SLL; otherwise, 
		 * it returns the first node that references an element
		 * that is equals to target. 
		 */
		private Node findNode(T target) { 
			Node current = first; 
			while (current != null) 
				if (current.getData().equals(target))
					return current; 
				else 
					current = current.getNext(); 
			
			return null;    // when the list is empty target is not in there...
		}

		
		public int removeAll(T elm) {
			int count = 0;
			while(remove(elm))
				count++;
			return count;
		}

		
		public Iterator<T> iterator() {
			return new BagIterator<T>();
		}
		
		@Override
		public boolean equals(Object obj) {
			
			@SuppressWarnings("unchecked")
			Bag<T> b2 = (Bag<T>) obj;
			
			if(this.size() == b2.size()) {
				
				T[] bag1 = this.toArray();
				T[] bag2 = b2.toArray();
				
				Arrays.sort(bag1);
				Arrays.sort(bag2);
				
				for (int i = 0; i < bag1.length; i++) {
					if (!bag1[i].equals(bag2[i])) {
						return false;
					}
				}
				return true;
			}
			return false;			
		}


		/** 
		 * Consider a member method for the Bag ADT called replace(). 
		 * The method receives two generic type values: 
		 * 		The first is a value that must be searched for within the Bag (oldValue) 
		 * 		The second is the value that will replace the first (newValue)
		 * 
		 * The goal of this method is to replace the value of the first occurrence of oldValue in the Bag. 
		 * If there are no occurrences of oldValue inside the bag or the bag is empty, then nothing should be modified inside the bag.
		 * 
		 * For example, suppose we have a bag B = {'A', 'B', 'C', 'D', 'B'}. 
		 * If we call B.replace('B', 'Z'), then B should now be B = {'A', 'Z', 'C', 'D', 'B'}
		 * 
		 * @param oldValue - Value to search for in the Bag
		 * @param newValue - The value that will replace oldValue  
		 */
		
		public void replace(T oldValue, T newValue) {
			/*ADD YOUR CODE HERE*/ 
	        if(this.getFrequencyOf(oldValue) >= 1) {
	        	this.remove(oldValue);
	        	this.add(newValue);
	        	}
	      } 
		}
	}