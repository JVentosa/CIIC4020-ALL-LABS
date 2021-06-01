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
public class MergeSort<E> extends AbstractSortingStrategy<E> {
	private ArrayList<E> list;

	public MergeSort(Comparator<E> cmp) {
		super("MergeSort", cmp);
	}

	@Override
	public void sortList(ArrayList<E> dataSet) {
		list = dataSet;
		ms(0, list.size() - 1);
	}

	/**
	 * Applies the divide and conquer strategy to sort using MergeSort algorithm. It
	 * works on the portion of the list from position first to position last
	 * (first..last).
	 * 
	 * @param first the index of the first position in this portion
	 * @param last  the index of the last position in this portion
	 */
	private void ms(int first, int last) {
		if (first < last) {
			int mid = first + (last - first) / 2;

			ms(first, mid);
			ms(mid + 1, last);
			merge(first, mid, last);
			// ADD CODE HERE 1
		}
	}

	/**
	 * Merges two sorted portions of the list -- see discussion in the lab's
	 * document.
	 * 
	 * @param first
	 * @param mid
	 * @param last
	 */
	private void merge(int first, int mid, int last) {
		E[] tempList = (E[]) new Object[last - first + 1];
		int index1 = first, index2 = mid + 1;
		int last1 = mid, last2 = last;
		int indexTL = 0;
		while (index1 <= last1 && index2 <= last2)
			if (cmp.compare(list.get(index1), list.get(index2)) <= 0)
				tempList[indexTL++] = list.get(index1++);
			else
				tempList[indexTL++] = list.get(index2++);

		// move the remaining data to tempList -- notice that only one
		// of the following loops will iterate at least once
		while (index1 <= last1)
			tempList[indexTL++] = list.get(index1++);
		while (index2 <= last2)
			tempList[indexTL++] = list.get(index2++);

		// put sorted data back to the list portion....
		for (int i = 0; i < tempList.length; i++)
			list.set(first + i, tempList[i]);

	}

}
