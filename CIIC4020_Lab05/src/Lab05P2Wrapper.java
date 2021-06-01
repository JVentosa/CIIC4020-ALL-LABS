import java.util.EmptyStackException;

public class Lab05P2Wrapper {

	public static interface StackInterface<E> {
		public void push(E newEntry);
		public E pop();
		public E peek();
		public boolean isEmpty();
		public int size();
		public void clear();
	} 

	public static class LinkedStack<E> implements StackInterface<E> {

		private static class Node<E> {
			private E data; 
			private Node<E> next; 

			public Node(E data, Node<E> next) { 
				this.data = data; 
				this.next = next; 
			}

			public E getData() {
				return data;
			}

			public Node<E> getNext() {
				return next;
			}

			public void clear() {  // no need to return data
				data = null; 
				next = null; 
			}

			// notice that setters are not needed in this 
			// implementation of the stack
		}

		// instance variables for the stack object

		private Node<E> top; 
		private int size; 

		public LinkedStack() { 
			top = null; 
			size = 0; 
		}

		@Override
		public void push(E newEntry) {
			Node<E> nNode = new Node<>(newEntry, top); 
			top = nNode; 
			size++; 
		}

		@Override
		public E pop() {
			E etr = peek(); 
			Node<E> ntr = top; 
			top = top.getNext(); 
			size--; 
			ntr.clear();
			return etr;
		}

		@Override
		public E peek() {
			if (isEmpty()) 
				throw new EmptyStackException(); 
			return top.getData();
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
			Node<E> ntd = top; 
			while (top != null) { 
				ntd.clear(); 
				top = top.getNext(); 
				ntd = top; 
			}

			size = 0; 
		}


	}
	
	/**
	 * Implement a non-member method for the Stack ADT called stackSort. 
	 * The function takes as a parameter an array of integers and returns the array sorted in increasing order.
	 * 
	 * For example consider we have an array A = {9, 11, 15, 11, 1, -1, 3, 11}
	 * In order to sort values, we will use two stacks which will be called the left and right stacks. 
	 * The values in the stacks will be sorted (in non-descending order) and the values in the left stack 
	 * will all be less than or equal to the values in the right stack. 
	 * 
	 * The following example illustrates a possible state for our two stacks:
	 * 
	 * 				left		right
	 * 				[  ]		[  ]
	 * 				[  ]		[ 9]
	 * 				[ 3]		[11]
	 * 				[ 1]		[11]
	 * 				[-1]		[15]
	 * 
	 * Notice that the values in the left stack are sorted so that the smallest value is at the bottom of the stack. 
	 * The values in the right stack are sorted so that the smallest value is at the top of the stack. 
	 * If we read the values up the left stack and then down the right stack, we get:
	 * 			A = {-1, 1, 3, 9, 11, 11, 11, 15}
	 * which is in sorted order.
	 * 
	 * 
	 * Consider the following cases, using the example shown above as a point of reference, to help you design your algorithm:
	 * 		1) If we were to insert the value 5, it could be added on top of either stack and the collection would remain sorted. 
	 * 		   What other values, besides 5, could be inserted in the  example without having to move any values?
	 * 
	 * 		2) If we were to insert the value 0, some values must be moved from the left stack to the right stack before we could actually insert 0. 
	 * 		   How many values must actually be moved?
	 * 
	 *		3) If we were to insert the value 11, first some values must be moved from the right stack to the left stack. 
	 *		   How many values must actually be moved?
	 *		   What condition should we use to determine if enough values have been moved in either of the previous two cases?
	 *		   
	 * YOU MUST USE TWO STACKS, IMPLEMENTATIONS THAT USE Arrays.sort(); 
	 * OR ANY SORTING ALGORITHM (BubbleSort, SelectionSort, etc.) WILL NOT BE GIVEN CREDIT
	 * 
	 * @param array
	 * @return Sorted array using two stacks
	 */
	public static int[] stackSort(int[] array) {
		/*ADD YOUR CODE HERE*/
		int[] result = new int[array.length];
        LinkedStack<Integer>left = new LinkedStack<Integer>();
        LinkedStack<Integer>right =  new LinkedStack<Integer>();
        left.push(array[0]);
        for (int i= 1; i < array.length; i++) {
            int number = array[i];
            if ((right.isEmpty()) && (number >= left.peek())) {
                left.push(number);
            }
            else if ((!right.isEmpty()) && (number >= left.peek()) && (number <= right.peek()))
                left.push(number);
            else if (number < left.peek()) {
                while (!left.isEmpty() && number <= left.peek())
                    right.push(left.pop());
                left.push(number); 
            }
            else if (!right.isEmpty() && number >=right.peek()) {
                while (!right.isEmpty() && number >= right.peek())
                    left.push(right.pop());
                right.push(number);
            }
        }
        while (!left.isEmpty())
            right.push(left.pop());
        int i = 0;
        while(!right.isEmpty()) {
            result[i] = right.pop();
            i++;
        }
        return result;//Dummy Return
	}

}