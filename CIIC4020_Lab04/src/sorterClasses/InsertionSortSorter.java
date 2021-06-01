package sorterClasses;

public class InsertionSortSorter<E> extends AbstractSorter<E> {
	public InsertionSortSorter() {
		super("Insertion Sort");
	}

	protected void auxSort() { 
		for (int i=2; i<=arr.length; i++) { 
			int j = i-2; 
			E v = arr[i-1]; 
			while (j >= 0 && cmp.compare(v, arr[j]) < 0) { 
				arr[j+1] = arr[j]; 
				j--;
			}
			arr[j+1] = v; 
		}
	}
}
