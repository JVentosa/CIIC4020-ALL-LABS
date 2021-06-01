package testers;

import java.util.ArrayList;
import java.util.Random;

import implementations.ChainingLLHashingDictionary;
import implementations.LinearProbingHashingDictionary_V1;
import interfaces.DictionaryInterface;
import interfaces.Entry;

public class ChainingLLHashingDictionary_Tester_3 {

	public static void main(String[] args) {
		Integer[] list = {2, 9, 27, 27, 2, 11, 10, 13, 81, 28}; 
		
		// Find the frequency of each letter in the list
		// using the dictionary
		DictionaryInterface<Integer, Integer> dict = new  ChainingLLHashingDictionary<>();  
		
		((ChainingLLHashingDictionary<Integer, Integer>) dict).showTableContentAsIs(); 
		
		// display frequencies
		for (Integer v : list) 
			addAndCount(dict, v); 
		((ChainingLLHashingDictionary<Integer, Integer>) dict).showTableContentAsIs(); 
		
		System.out.println("Removing 2  returns " + dict.remove(2)); 
		((ChainingLLHashingDictionary<Integer, Integer>) dict).showTableContentAsIs(); 
		
		System.out.println("Adding 13 and counting "); 
		addAndCount(dict, 13); 
		((ChainingLLHashingDictionary<Integer, Integer>) dict).showTableContentAsIs(); 
			

		displayEntries(dict); 
		System.out.println("Adding 2 and counting "); 
		addAndCount(dict, 2);  
		((ChainingLLHashingDictionary<Integer, Integer>) dict).showTableContentAsIs(); 

		displayEntries(dict); 
		System.out.println("Adding 9 and counting "); 
		addAndCount(dict, 9); 
		displayEntries(dict); 
		

		((ChainingLLHashingDictionary<Integer, Integer>) dict).showTableContentAsIs(); 

		System.out.println("Remove 13"); 
		dict.remove(13); 
		((ChainingLLHashingDictionary<Integer, Integer>) dict).showTableContentAsIs(); 

		System.out.println("Remove 81"); 
		dict.remove(81); 
		((ChainingLLHashingDictionary<Integer, Integer>) dict).showTableContentAsIs(); 

		System.out.println("Add 81"); 
		dict.add(81, 1); 
		((ChainingLLHashingDictionary<Integer, Integer>) dict).showTableContentAsIs(); 

		System.out.println("Remove 28"); 
		dict.remove(28); 
		((ChainingLLHashingDictionary<Integer, Integer>) dict).showTableContentAsIs(); 
		
		
	}
	
	private static void addAndCount(DictionaryInterface<Integer, Integer> d, Integer c) {
		Integer v = d.getValue(c); 
		if (v != null) 
			d.add(c, v+1); 
		else 
			d.add(c,  1); 
	}
	
	private static void displayEntries(DictionaryInterface<Integer, Integer> dict) { 
		// display frequencies
		int sum = 0; 
		for (Entry<Integer, Integer> e : dict.entries()) {
			System.out.println(e); 
			sum += e.getValue(); 
		}
		System.out.println("Sum = " + sum); 
	}
}
