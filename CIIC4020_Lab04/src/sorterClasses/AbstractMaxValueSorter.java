package sorterClasses;

public abstract class AbstractMaxValueSorter<E> extends AbstractSorter<E> {
	
	public AbstractMaxValueSorter(String name) {
		super(name);
	}
	protected void auxSort() { 
		for (int i = arr.length; i>1; i--) 
			relocateMaxValueToLastPositionAmongFirst(i); 
	}
	protected abstract void relocateMaxValueToLastPositionAmongFirst(int i);
}
