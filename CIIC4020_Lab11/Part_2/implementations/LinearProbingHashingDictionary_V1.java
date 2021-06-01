package implementations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

import interfaces.DictionaryInterface;
import interfaces.Entry;

public class LinearProbingHashingDictionary_V1<K, V> implements DictionaryInterface<K, V> {
	private static final float LOADFACTOR = 0.5f;
	private static final int INITCAP = 7;  // a prime number 
	private final MyEntry<K, V> AVAILABLE = new MyEntry<K, V>(); 
	
	private MyEntry<K, V>[] table;    // where entries are stored
	
	private int size;   // number of entries in the dictionary
	
	
	@SuppressWarnings("hiding")
	private class MyEntry<K, V> implements Entry<K, V> {

		private K key; 
		private V value; 
		
		public MyEntry() { 
			this(null, null); 
		}
		
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
			if (this == AVAILABLE)
				return "AVAILABLE"; 
			else 
				return "[" + key + ", " + value + "]"; 
		}
	}
	
	// constructors of class SimpleLinearProbingHashing
	public LinearProbingHashingDictionary_V1() { 
		this(INITCAP); 
	}
	
	@SuppressWarnings("unchecked")
	public LinearProbingHashingDictionary_V1(int cap) { 
		if (cap < INITCAP) 
			cap = INITCAP; 
		table  = (MyEntry<K, V>[]) new MyEntry[cap]; 
		size = 0; 
	}

	// NOTE: THE FOLLOWING METHOD IS MADE PUBLIC JUST FOR TESTING AND ILLUSTRATION
	// PURPOSES. IN A FINAL VERSION OF THIS CLASS, IT MUST BE PRIVATE
	// To compute the hash index value
	public /*private*/ int hashValue(K key) { 
		int index = key.hashCode() % table.length;
		
		// the previous value can be a negative value in the range
		// -(table.length-1) to -1. So, need to verify to adjust if needed; 
		// which is just to add table.length. 		
		if (index < 0) 
			index += table.length; 
		
		return index; 
		
	}
	
	// To find the index where the given key will be, if present; or 
	// where it should be placed if not present in this dictionary. 
	// This implementation presumes that there is always at least one null
	// position in the table or there is at least one AVAILABLE position. 
	// Notice that the previous assumption is always true if we guarantee
	// a load factor < 1. 
	private int findHashPos(K key) { 
		
		int index = hashValue(key);  // the first candidate index for the given key
		int i = index;    // to iterate if needed
		int count = 0;    // to count iterations
		Integer firstAvailable = null;   // to keep the index of first available position while searching
		boolean found = false;   // to mark if key was found during the search
		
		// Iterate until found or until determined that the key is not in the table (which happens
		// when a null position is found in the table or the iteration process has gone over all 
		// the table once. 
		while (!found && count < table.length) {
			count++; 
			if (table[i] == null) {
				// the key is not in this dictionary. 
				// Return the index where it should be if added. 
				if (firstAvailable == null) 
					return i;     
				else 
					return firstAvailable; 
			}
			if (table[i] == AVAILABLE) {
               if (firstAvailable == null)  // keep the first available position found, just in case
				   firstAvailable = i;      // this is done once at most
			}
			else if (key.equals(table[i].getKey()))  // the key is found at position table[i].
				found = true;
			
			if (!found)
				i = (i+1) % table.length;    // move to the next position in the array. 
		} // end while
		
		if (!found) 
			return firstAvailable; 
		else 
			return i; 
	}
	
	@Override
	public V add(K key, V value) {
		// find the index of the candidate position where the key is or
		// where it should go if not present in this dictionary
		
		int h = findHashPos(key); 
		
		// check if the key is present at position h in the table
		// and proceed accordingly
		if (!positionInTableContainsKey(h, key)) {
			// the key is not present in this dictionary
			// the new entry is to be added at position h. 
			
			table[h] = new MyEntry<>(key, value); 
			size++;      // adjust the size - number of entries in the dictionary
			
			// check if the LOADFACTOR has been exceeded. 
			// If so, increase capacity and relocate items. 
			if (((float) size)/table.length > LOADFACTOR)
				increaseCapacity(); 

			return null;   // See the specification of the add method in DictionaryInterface.
		}
		
		// if here, it is because the key was found at position h. Just set the value
		// of the entry there to the new value and return the old value. 
		
		return table[h].setValue(value);   // see setValue for the class MyEntry
	}

	/**
	 * Checks if a given position (index h) in the table contains a given
	 * key. 
	 * @param h The index of the position to look at the table.
	 * @param key The key that is being looked for.
	 * @return true if the key is at table[h]; false if not. 
	 */
	private boolean positionInTableContainsKey(int h, K key) {
		if (table[h] != null) 
			return key.equals(table[h].getKey()); 
		else
		    return false; 
	}

	@Override
	public V remove(K key) {
		// Find the index where the key would be if present.
		int index = findHashPos(key); 
		
		// test if the key is present at position index in the table
		// and proceed accordingly.
		if (positionInTableContainsKey(index, key))  {
			// yes, the key is at position index in the table
			
			V vtr = table[index].getValue();  // value to be returned
			size--;     // adjust the size -- number of entries in the dictionary
			
			// the following is to determine if the position that is being emptied is
			// is set to null or to AVAILABLE. We could always set it to AVAILABLE, but that 
			// is not needed if the position after (circular) is already null. THINK about this.
			int pIndex = (index+1) % table.length; 
			
			if (table[pIndex] != null)
				table[index] = AVAILABLE; 
			else
				table[index] = null; 
					
			// return the value of the entry that was removed...
			return vtr; 
		}
		else 
			return null;   // key is not found
	}

	@Override
	public V getValue(K key) {
		int h = findHashPos(key); 
		if (positionInTableContainsKey(h, key)) 
			return table[h].getValue();
		else 
			return null; 
	}

	@Override
	public boolean contains(K key) {
		return positionInTableContainsKey(findHashPos(key), key);
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
		return new EntryIterable();   // see class EntryIterable at the end
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
	
	// just for testing and illustration purposes
	System.out.println("\n ^^^^ Need to increase table capacity"); 
	System.out.println("     Table BEFORE increasing capacity: "); 
	showTableContentAsIs(); 
	//////
	
		MyEntry<K, V>[] old = table;   // the current table to be replaced
		
		table = new MyEntry[newCap];   // the new table
		
		// Move all the entries, from the old table to the new table. But notice that 
		// the hashing indexes for each entry have automatically changed (because we have
		// a new value for table.length). So, the entries in the old table must be relocated 
		// in the new table as per the new hashing indexes. 
		// The algorithm is then to find the index where each entry must be located in the 
		// new table. For that, we apply the findPos method to the key of each entry. Notice
		// that none of them are found, so, in this case, the method will always return 
		// the index where the entry needs to be put in the new table. 
		//
		for (int i = 0; i<old.length; i++) { 
			if (old[i] != null && old[i] != AVAILABLE)   // old[i] is an entry
				table[findHashPos(old[i].getKey())] = old[i];
			old[i] = null; 
		} 

	// just for testing and illustration purposes
	System.out.println("\n    Table AFTER increasing capacity: "); 
	showTableContentAsIs(); 
	///////
	
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


	// for testing only and illustration purposes only -- remove later
	public void showTableContentAsIs() { 
		//System.out.println("\n**** Content of Internal table in the dictionary: ");
		for (int i=0; i<table.length; i++) 
			System.out.println("table[" + i + "] = " + table[i]); 
	}
	
	// Iterator of entries
	private class EntryIterator implements Iterator<Entry<K, V>> {

		int index = 0;   // current index in table
		int count = size; // how many next operations can still be executed
		
		@Override
		public boolean hasNext() {
			return count > 0;
		}

		@Override
		public Entry<K, V> next() {
			if (!hasNext()) 
				throw new NoSuchElementException(); 
			count--; 
			
			while (table[index] == null || table[index] == AVAILABLE )
				index++;  
			
			Entry<K, V> etr = table[index]; 
			
			index++;  // prepare for the next time
			
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












