package strategiesClasses;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Implementation of the in-place version of Quicksort.
 * 
 * @author pedroirivera-vega
 *
 * @param <E>
 */
public class QuickSort<E> extends AbstractSortingStrategy<E> {
	private ArrayList<E> list; 

	public QuickSort(Comparator<E> cmp) { 
		super("QuickSort", cmp); 
	}
	
	@Override
	public void sortList(ArrayList<E> dataSet) {
		list = dataSet; 
		qs(0, list.size()-1); 
	}

	/**
	 * Applies the divide and conquer strategy to sort using 
	 * QuickSort algorithm. It works on the portion of the list
	 * from position first to position last (first..last). 
	 * 
	 * @param first the index of the first position in this
	 * portion
	 * @param last the index of the last position in this
	 * portion
	 */
	private void qs(int first, int last) {
		if(first < last) {
			int mid = partitionList(first, last);
			qs(first, mid-1);
			qs(mid, last);
		}
		// ADD CODE HERE 
	}

	/**
	 * Partition method. ... See discussion in lab's document.
	 * @param first
	 * @param last
	 * @return
	 */
	private int partitionList(int first, int last) { 
		E pivot = list.get(last); 
		int left = first, right = last-1; 
		
		while (left <= right) {
			while (left <= right && cmp.compare(list.get(left), pivot) <= 0)
				left++; 
			while (left <= right && cmp.compare(list.get(right), pivot) > 0)
				right--; 
			if (left < right) {
				SortingUtils.swapListElements(list, left, right); 
				left++; 
				right--; 
			}
		}
		SortingUtils.swapListElements(list, left, last); 

		return left; 

	}
}
