package sorterClasses;

import java.util.Comparator;

import interfaces.Sorter;

public abstract class AbstractSorter<E> implements Sorter<E> {
	protected E[] arr;   // the array to sort
	protected Comparator<E> cmp; // the comparator
	private String name;   // strategy's name
	
	protected static class DefaultComparator<E> implements Comparator<E> {
		@SuppressWarnings("unchecked")
		public int compare(E o1, E o2) {
			try { 
				return ((Comparable<E>) o1).compareTo(o2); 
			} catch (ClassCastException e) { 
				throw new IllegalArgumentException("Instantiated data type must be Comparable");
			}
		} 
	}
	
	/**
	 * 
	 * @param name
	 */
	public AbstractSorter(String name) { 
		this.name = name; 
	}
	
	/**
	 * When this method is applied to the Sorter object, it will initiate
	 * the sorting process for the specified array using the given comparator. 
	 * @param arr The array to sort.
	 * @param cmp The comparator to use. If null, then the default comparator
	 * is used. 
	 */
	public void sort(E[] arr, Comparator<E> cmp) { 
		this.arr = arr; 
		this.cmp = (cmp != null ? cmp : new DefaultComparator<E>()); 
		auxSort(); 
	}
	
	public String getName() { 
		return name; 
	}
	
	protected void swapArrayElements(int i, int j) { 
		// pre: 0 <= i, j < arr.length; 
		E temp = arr[i]; 
		arr[i] = arr[j]; 
		arr[j] = temp; 
	}
	
	protected abstract void auxSort(); 
}
