package testers;

import java.util.Iterator;

import implementations.LinearProbingHashingDictionary_V1;
import interfaces.DictionaryInterface;
import interfaces.Entry;

public class SimpleLinearProbingHashingDictionary_V1_Tester_2 {

	public static void main(String[] args) {
		Character[] letras = {'a', 'c', 'a', 'd', 'b', 'f', 'h', 'w', 'k', 'b', 'y'}; 
		Integer[] keys = {5, 9, 8, 1, 6, 10, 13, 2, 11, 20, 14}; 

		DictionaryInterface<Integer, Character> dict = 
				new LinearProbingHashingDictionary_V1<Integer, Character>(5); 
		
		for (Integer i=0; i<keys.length; i++)
			dict.add(keys[i],  letras[i]); 
		

		System.out.println("Entries using the EntryIterable"); 
		for (Entry<Integer, Character> e : dict.entries()) {
			System.out.println(e); 
		}
		
		
		System.out.println("Keys using the KeyIterator"); 
		Iterator<Integer> iter = dict.getKeyIterator(); 
		
		while (iter.hasNext()) { 
			System.out.println(iter.next()); 
		}
  
		System.out.println("Keys using the KeyIterable"); 
		for (Integer key : dict.keys())
			System.out.println(key); 
		
		System.out.println("Values using the ValueIterable"); 
		for (Character v : dict.values())
			System.out.println(v); 
		
	}

}
