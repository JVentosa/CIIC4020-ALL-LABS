import java.util.Iterator;
import java.util.NoSuchElementException;


public class Lab08P1Wrapper {
	
	public static class ForwardArrayIterable<E> implements Iterable<E> {

		private E[] arr; 
		public ForwardArrayIterable(E[] arr) { 
			this.arr = arr; 
		}
		public Iterator<E> iterator() {	
			return new ForwardArrayIterator<E>(arr);
		}

	}
	
	public static class ForwardArrayIterator<E> implements Iterator<E> {

		private E[] arr;    // the array to iterate over
		private int now;
		/*ADD OTHER INTERNAL FIELDS HERE*/
		
		
		public ForwardArrayIterator(E[] arr) { 
			this.arr = arr;
			this.now = 0;
			/*INITIALIZE OTHER INTERNAL FIELDS HERE*/
		}
		
		public boolean hasNext() {
			return this.now < this.arr.length;
			/*ADD YOUR CODE HERE*/
		}

		public E next() throws NoSuchElementException {
			if (!hasNext())
				throw new 
					NoSuchElementException("No more elements to iterate over."); 
			E yes = this.arr[now];
			now++;
			return yes; 
			/*ADD YOUR CODE HERE*/
			
		}

		public void remove() throws UnsupportedOperationException {
			throw new UnsupportedOperationException(
					"Remove peration not implemented.");
			// do not implement
		}
	}	
}
