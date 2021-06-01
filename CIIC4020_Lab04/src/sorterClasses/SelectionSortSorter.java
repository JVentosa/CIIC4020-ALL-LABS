package sorterClasses;

public class SelectionSortSorter<E> extends AbstractMaxValueSorter<E> {

	public SelectionSortSorter() {
		super("Selection Sort");
	}

	@Override
	protected void relocateMaxValueToLastPositionAmongFirst(int i) {
		// pre: 1 < i < arr.length
		int mpi = 0; 
		for (int j=1; j < i; j++) 
			if (cmp.compare(arr[j], arr[mpi]) > 0)
				mpi = j; 
		
		if (mpi < i-1)
			super.swapArrayElements(mpi, i-1);
		
	}

}
