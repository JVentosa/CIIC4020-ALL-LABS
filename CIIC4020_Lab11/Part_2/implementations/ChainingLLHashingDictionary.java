package implementations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

import interfaces.DictionaryInterface;
import interfaces.Entry;

public class ChainingLLHashingDictionary<K, V> implements DictionaryInterface<K, V> {
	private static final float LOADFACTOR = 0.5f;
	private static final int INITCAP = 7;  // a prime number 
	
	private Node<K, V>[] table;    // where entries are stored
	
	private int size;   // number of entries in the dictionary
	
	
	@SuppressWarnings("hiding")
	private class MyEntry<K, V> implements Entry<K, V> {

		private K key; 
		private V value; 
		
		public MyEntry(K key, V value) { 
			this.key = key; 
			this.value = value; 
		}
		
		@Override
		public K getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return value;
		} 
		
		public V setValue(V value) { 
			V old = this.value; 
			this.value = value; 
			return old; 
		}
		
		public String toString() { 
			return "[" + key + ", " + value + "]"; 
		}
	}
	
	private class Node<K, V> { 
		private MyEntry<K, V> data; 
		private Node<K, V> next; 
		
		public Node(MyEntry<K, V> data, Node<K, V> next) { 
			this.data = data; 
			this.next = next; 
		}
		
		public MyEntry<K, V> getData() {
			return data;
		}
		public void setData(MyEntry<K, V> data) {
			this.data = data;
		}
		public Node<K, V> getNext() {
			return next;
		}
		public void setNext(Node<K, V> next) {
			this.next = next;
		}
		
	    public void clear() { 
	    	data = null; 
	    	next = null; 
	    }
	}

	// constructors of class SimpleLinearProbingHashing
	public ChainingLLHashingDictionary() { 
		this(INITCAP); 
	}
	
	@SuppressWarnings("unchecked")
	public ChainingLLHashingDictionary(int cap) { 
		if (cap < INITCAP) 
			cap = INITCAP; 
		table  = (Node<K, V>[]) new Node[cap]; 
		size = 0; 
	}

	// To compute the hash index value
	private int hashValue(K key) { 
		int index = key.hashCode() % table.length;
		
		// the previous value can be a negative value in the range
		// -(table.length-1) to 0. So, need to verify to adjust if needed; 
		// which is just to add table.length. 
		if (index < 0) 
			index += table.length; 
		
		return index; 
		
	}
	
	
	@Override
	public V add(K key, V value) {
		int h = hashValue(key); 
		if (table[h] != null) { 
			Node<K, V> current = table[h]; 
			while (current != null) {
				if (key.equals(current.getData().getKey()))
					return current.getData().setValue(value); 
				else 
					current = current.getNext(); 
			}
		}
		// If it reaches here, then the new pair (key, value) 
		// needs to be added to the list table[h]. Add it as
		// the new first node in that list. 
		table[h] = new Node<>(new MyEntry<>(key, value), table[h]); 
		size++;

		// check if the LOADFACTOR has been exceeded. 
		// If so, increase capacity and relocate items. 
		if (((float) size)/table.length > LOADFACTOR)
			increaseCapacity(); 

		return null; 
	}

	@Override
	public V remove(K key) {
		int h = hashValue(key); 
			
		if (table[h] != null) {
			Node<K, V> prev = null; 
			Node<K, V> current = table[h]; 
			while (current != null && !current.getData().getKey().equals(key)) { 
				prev = current; 
				current = current.getNext(); 
			}
			if (current != null) { 
				// need to remove node current
				V vtr = current.getData().getValue(); 
				if (prev != null)
				   prev.setNext(current.getNext());
				else 
					table[h] = current.getNext(); 
				current.clear(); 
				size--; 
				return vtr;   // return value of entry removed
			}
		}
		return null; 
	}

	@Override
	public V getValue(K key) {
		int h = hashValue(key); 
		if (table[h] != null) { 
			Node<K, V> current = table[h]; 
			while (current != null) {
				if (key.equals(current.getData().getKey()))
					return current.getData().getValue(); 
				else 
					current = current.getNext(); 
			}
		}
		return null; 
	}

	@Override
	public boolean contains(K key) {
		return getValue(key) != null;
	}

	@Override
	public Iterator<K> getKeyIterator() {
		return null; 
	}

	@Override
	public Iterator<V> getValueIterator() {
		return null;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Iterable<Entry<K, V>> entries() {
		
		return new EntryIterable(); 
	}

	@Override
	public Iterable<K> keys() {
		return null; 
	}

	@Override
	public Iterable<V> values() {
		return null;
	}
		
	@SuppressWarnings("unchecked")
	private void increaseCapacity() { 
		// Increase the capacity to at list the double of the current capacity. But make sure
		// that the new capacity (length of table) is a prime number...
		//
		int newCap = findNextPrime(2*table.length+1); 
	
	// the following output statements are just for testing
	System.out.println("Increasing Capacity"); 
	System.out.println("Table before: "); 
	showTableContentAsIs(); 
		
		Node<K, V>[] old = table;   // the current table to be replaced
		
		table = (Node<K, V>[]) new Node[newCap];   // the new table
		
		// Move all the entries, from the old table to the new table. But notice that 
		// the hashing indexes for each entry have automatically changed (because we have
		// a new value for table.length). So, the entries in the old table must be relocated 
		// in the new table as per the new hashing indexes. 
		// The algorithm is then to find the index where each entry must be located in the 
		// new table. 
		//
		for (int i = 0; i<old.length; i++) { 
			while (old[i] != null) {
				Node<K, V> nodeToMove = old[i]; 
				old[i] = old[i].getNext(); 
				int newIndex = hashValue(nodeToMove.getData().getKey());
				nodeToMove.setNext(table[newIndex]); 
				table[newIndex] = nodeToMove; 
			}
		} 
			
	System.out.println("Table AFTER: "); 
	showTableContentAsIs(); 
		
	}
	
	// to make sure that the length of the table is always a prime number.
	private int findNextPrime(int n) {
		while (!isPrime(n))
			n += 2; 
		return n;
	}

	// A simple brute-force algorithm to determine if a given integer is a prime number. 
	private boolean isPrime(int value) {
		boolean isPrime = true; 
		for (int d=3; d<=Math.sqrt(value) && isPrime; d+=2)
			if (value % d == 0) 
				isPrime = false; 
		return isPrime;
	}


	// for testing only -- remove later
	public void showTableContentAsIs() { 
		System.out.println("Content of Internal table in the dictionary: ");
		for (int i=0; i<table.length; i++) {
			System.out.print("table[" + i + "] = {"); 
			for (Node<K, V> current = table[i]; current!= null; current = current.getNext())
				System.out.print(" "+current.getData() + " "); 
			System.out.println("}"); 
		}
	}
	
	
	
	// iterators and iterable of entries, keys and variables
	// Iterator of entries
	private class EntryIterator implements Iterator<Entry<K, V>> {

		int index = -1;   // current index in table
		int count = size; // how many next operations can still be executed
		Node<K, V> currentNode = null; 
		
		@Override
		public boolean hasNext() {
			return count > 0;
		}

		@Override
		public Entry<K, V> next() {
			if (!hasNext()) 
				throw new NoSuchElementException(); 
			
			if (currentNode == null) { 
				index++; 
				while (table[index] == null)
					index++;  
				currentNode = table[index]; 
				// at this moment, table[index] is the first node of a nonempty list
				// of nodes containing entries whose keys have hash value equal to index.
				// The variable currentNode is actually referencing the node that
				// contains the next entry to be returned by this iterator. 
			}
			

			// The variable currentNode is actually referencing the node that
			// contains the next entry to be returned by this iterator. 
				
			Entry<K, V> etr = currentNode.getData(); 
			currentNode = currentNode.getNext();     // prepare for the next time
						
			count--;      // one entry less for this iterator
			
			return etr;
		} 
		
	}
	
	// Iterable of entries
	private class EntryIterable implements Iterable<Entry<K, V>> {

		@Override
		public Iterator<Entry<K, V>> iterator() { 
			return new EntryIterator();
		} 
		
	}
	

}
