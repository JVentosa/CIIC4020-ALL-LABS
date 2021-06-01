package testers;

import java.util.ArrayList;
import java.util.Random;

import implementations.ChainingLLHashingDictionary;
import implementations.LinearProbingHashingDictionary_V1;
import interfaces.DictionaryInterface;
import interfaces.Entry;

public class ChainingLLHashingDictionary_Tester_1 {

	public static void main(String[] args) {
		String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; 
		ArrayList<Character> list = new ArrayList<>(); 

		Random rnd = new Random();
		// If an integer number is included as parameter of this constructor 
        // for the Random class, then it is used as seed and it always generate
        // the same sequence of random values. Good for controlled testing.
 
		for (int i=0; i<30; i++) {
			int r = rnd.nextInt(26); 
			list.add(letters.charAt(r));  
		}

		System.out.println(list); 
		
		// Find the frequency of each letter in the list
		// using the dictionary
		DictionaryInterface<Character, Integer> dict = countFrequencies(list);  
		
		// display frequencies
		displayEntries(dict); 
	}

	/** Count the frequencies of letters in a list.
	 * 
	 * @param list the list of letters
	 * @return a dictionary containing pairs of type <Character, Integer>. 
	 * There will be one such pair for each different letter in the list. 
	 * On each such pair, the letter is the key. The value associated with
	 * each key (letter) is the count of the number of occurrences of the
	 * letter in the list.
	 */
	private static DictionaryInterface<Character, Integer> 
	countFrequencies(ArrayList<Character> list) { 
		DictionaryInterface<Character, Integer> dict = new ChainingLLHashingDictionary<>(); 
		
		// add code that uses the map to count frequency of each
		// letter in list
		
		for (Character c : list) { 
			Integer v = dict.getValue(c); 
			if (v != null) 
				dict.add(c, v+1); 
			else 
				dict.add(c,  1); 

		}
		
		return dict; 
	}
	
	private static void displayEntries(DictionaryInterface<Character, Integer> dict) { 
		// display frequencies
		
		((ChainingLLHashingDictionary<Character, Integer>) dict).showTableContentAsIs(); 
		int sum = 0; 
		for (Entry<Character, Integer> e : dict.entries()) {
			System.out.println(e); 
			sum += e.getValue(); 
		}
		System.out.println("Sum = " + sum); 
	}
}
