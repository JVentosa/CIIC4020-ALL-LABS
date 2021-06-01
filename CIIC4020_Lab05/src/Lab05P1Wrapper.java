import java.util.EmptyStackException;

public class Lab05P1Wrapper {

	public static interface StackInterface<E> {
		public void push(E newEntry);
		public E pop();
		public E peek();
		public boolean isEmpty();
		public int size();
		public void clear();
	} 
	
	public static class ArrayStack<E> implements StackInterface<E> {
		private static final int INITIAL_CAPACITY = 10; 
		private E[] elements; 
		private int size; 
		
		@SuppressWarnings("unchecked")
		public ArrayStack() { 
			elements = (E[]) new Object[INITIAL_CAPACITY]; 
			size = 0; 
		}

		@Override
		public void push(E newEntry) {
			if (size == elements.length)
				doubleCapacity(); 
			
			elements[size++] = newEntry; 
			
		}

		@Override
		public E pop() {
			E etr = peek();     // throws ESE if needed...
			elements[--size] = null; 
			return etr;
		}

		@Override
		public E peek() {
			if (isEmpty()) 
				throw new EmptyStackException(); 
			return elements[size-1];
		}

		@Override
		public boolean isEmpty() {
			return size == 0;
		}

		@Override
		public int size() { 
			return size; 
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public void clear() {
			for (int i=0; i<size; i++) 
				elements[i] = null; 
			
			// just leave it with the initial capacity
			elements = (E[]) new Object[INITIAL_CAPACITY]; 
			
			size = 0; 
			
		}
		
		@SuppressWarnings("unchecked")
		private void doubleCapacity() { 
			E[] newArray = (E[]) new Object[2*elements.length]; 
			
			for (int i=0; i<size; i++) { 
				newArray[i] = elements[i]; 
				elements[i] = null;        // support GC
			}
			
			elements = newArray; 
		}

	}
	
	/**
	 * Implement a non-member method for the Stack ADT called balancedBrackets. 
	 * The function that takes as input a string made up of brackets symbols: 
	 *		'(', ')'
	 *		'\', '/'
	 *		'{', '}'
	 *		'[', ']'
	 *		'<', '>'
	 *		'-', '+' 
	 * and other optional characters like letters, words, or white-spaces.  
	 *
	 * The function returns true if the string is balanced with regards to brackets, or false otherwise.  
	 * A string is said to be balanced if it has as many opening brackets of a certain type 
	 * as it has closing brackets of that type and if no bracket is unmatched. 
	 *
	 * Note that an opening bracket cannot match a corresponding closing bracket that comes before it, 
	 * and similarly, a closing bracket cannot match a corresponding opening bracket that comes after it.  
	 * 
	 * Also, brackets cannot overlap each other as in "[(])". 
	 *
	 * For example, consider the following strings:
	 *		1) "- ( < { CIIC4020 } > ) +" returns true
	 *		2) "-(ICOM})4035]/" return false
	 *
	 * HINT: Use "\\" to check for the '\' character in Java
	 * 
	 * YOU MUST USE A STACK, DO NOT USE INDEX POINTERS 
	 * IMPLEMENTATIONS THAT DO NOT USE A STACK WILL NOT BE GIVEN CREDIT
	 * 
	 * 
	 * @param s
	 * @return whether the string was balanced
	 */
	public static boolean balancedBrackets(String str) {
		/* ADD YOUR CODE HERE */
		StackInterface<Character> stack = new ArrayStack<Character>();
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == '(' || str.charAt(i) == '<' || str.charAt(i) == '[' || str.charAt(i) == '-'
					|| str.charAt(i) == '\\' || str.charAt(i) == '{') {
				stack.push(str.charAt(i));
			}
			if (!stack.isEmpty()) {
				if (str.charAt(i) == ')') {
					if (stack.peek() == '(')
						stack.pop();
					else
						return false;
				} else if (str.charAt(i) == '>') {
					if (stack.peek() == '<')
						stack.pop();
					else
						return false;
				} else if (str.charAt(i) == ']') {
					if (stack.peek() == '[')
						stack.pop();
					else
						return false;
				} else if (str.charAt(i) == '+') {
					if (stack.peek() == '-')
						stack.pop();
					else
						return false;
				} else if (str.charAt(i) == '/') {
					if (stack.peek() == '\\')
						stack.pop();
					else
						return false;
				} else if (str.charAt(i) == '}') {
					if (stack.peek() == '{')
						stack.pop();
					else
						return false;
				}
			} else {
				return false;
			}
		}
		return stack.isEmpty(); // Dummy Return
	}

}
