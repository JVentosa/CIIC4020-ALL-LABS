import java.util.Arrays;
import java.util.HashMap;

@SuppressWarnings("unused")
public class Lab02P4Wrapper {

	public static interface Bag<E> {

		public boolean add(E elm);
		public boolean remove(E elm);
		public E remove();
		public int removeAll(E elm);
		public void clear();
		public int getFrequencyOf(E elm);
		public boolean contains(E elm);
		public int size();
		public boolean isEmpty();
		public E[] toArray();
		public boolean equals(Bag<E> b2);
		public Bag<Integer> getFrequencyBag();
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
		
		public boolean equals(Bag<T> b2) {
			
			T[] arr1 = this.toArray();
			T[] arr2 = b2.toArray();
			
			if(arr1.length != arr2.length) return false;
			
			Arrays.sort(arr1);
			Arrays.sort(arr2);
			
			int i = 0;
			int j = 0;
			
			while(i < arr1.length && j < arr2.length) {
				if(!arr1[i].equals(arr2[j])) {
					return false;
				}
				
				i++;
				j++;
			}
			
			return true;
			
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

		/**
		 * Consider a member method for the LinkedBag implementation of the Bag ADT called getFrequencyBag()
		 * 
		 * The method should return a bag of type integer containing the number of copies of each element inside the target bag
		 * 
		 * If the target bag is empty, the method should return an empty bag
		 * 
		 * For example, suppose we have a bag B = {'A','B','B','D','B','C'}. Then a call to B.getFrequencyBag() should return:
		 * 
		 * frequencyBag = {'1','3','1','1'}
		 * 
		 * This is because there is one copy of 'A', three copies of 'B', and one copy of 'C' and one copy of 'D'
		 * 
		 * Note that bags don't have a notion of order, so these elements in frequencyBag won't have a relative order either
		 * 
		 * Hint: 
		 * 		Read the document provided as to how to iterate and use nodes in a linked structure
		 * 
		 * @return frequencyBag - A bag of type integer containing all the frequencies of each element inside the target bag
		 * Node curNode = first;
		 * 
		 * while(curNode != null ){
		 * curNode = curNode.getNext()
		 * 
		 * method 2:
		 * 
		 * For(Node curNode = first; curNode != null; curNode = curNode.getNext()){
		 */
	
		public Bag<Integer> getFrequencyBag(){
			 Bag<Integer> freq = new LinkedBag<Integer>();
	            Bag<T> repeat = new LinkedBag<T>();
	            Node curNode = first;
	            while (curNode != null) {
	                if (repeat.contains(curNode.data) == false) {
	                    freq.add(getFrequencyOf(curNode.data));
	                    repeat.add(curNode.data);
	                }
	                curNode = curNode.next;
	            }
	            /*ADD YOUR CODE HERE*/
	            return freq; 
        }
	}
}