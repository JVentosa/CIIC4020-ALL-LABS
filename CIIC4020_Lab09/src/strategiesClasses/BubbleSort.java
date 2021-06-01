package strategiesClasses;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;

public class BubbleSort<E> extends AbstractSortingStrategy<E> {

	public BubbleSort(Comparator<E> cmp) { 
		super("BubbleSort", cmp); 
	}
	
	@Override
	public void sortList(ArrayList<E> dataSet) {
		int n = dataSet.size(); 
		for (int i=n-1; i>0; i--) 
			for (int j=0; j<i; j++) 
				if (cmp.compare(dataSet.get(j), dataSet.get(j+1)) > 0)
					SortingUtils.swapListElements(dataSet, j, j+1); 
	}

}
