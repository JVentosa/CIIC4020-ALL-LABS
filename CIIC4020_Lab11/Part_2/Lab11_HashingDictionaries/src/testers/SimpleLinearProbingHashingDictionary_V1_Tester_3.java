package testers;

import java.util.ArrayList;
import java.util.Random;

import implementations.LinearProbingHashingDictionary_V1;
import interfaces.DictionaryInterface;
import interfaces.Entry;

public class SimpleLinearProbingHashingDictionary_V1_Tester_3 {

	public static void main(String[] args) {
		Integer[] list = {2, 9, 27, 27, 2, 11, 10, 13, 81, 28}; 
		
		// Find the frequency of each letter in the list
		// using the dictionary
		LinearProbingHashingDictionary_V1<Integer, Integer> dict = new  LinearProbingHashingDictionary_V1<>();  
		
		
		// display frequencies
		for (Integer v : list) {
			addAndCount(dict, v); 
		} 
		
		addAndCount(dict, 13); 
			
		addAndCount(dict, 2);  
  
		addAndCount(dict, 9);  
		

		remove(dict, 13); 

		remove(dict, 81); ; 

		addAndCount(dict, 81);  

		remove(dict, 28); 
		
		displayEntries(dict); 
		
	}
	
	private static void addAndCount(LinearProbingHashingDictionary_V1<Integer, Integer> d, Integer v) {
		System.out.println("\nCurrent content of table: ");
		d.showTableContentAsIs(); 
		
		System.out.println("\n +++ Adding " + v + " and counting. hashValue(" + 
						 v + ") = " + d.hashValue(v)); 
		Integer c = d.getValue(v); 
		if (c != null) 
			d.add(v, c+1); 
		else 
			d.add(v,  1); 
		System.out.println("\nContent of table after adding and counting " + v); 
		d.showTableContentAsIs(); 
	}
	
	private static void remove(LinearProbingHashingDictionary_V1<Integer, Integer> d, Integer v) { 
		System.out.println("\nCurrent content of table: ");
		d.showTableContentAsIs(); 
		System.out.println("\n --- Removing " + v); 
		d.remove(v); 
		System.out.println("\nContent of table after removing " + v); 
		d.showTableContentAsIs(); 
	}

	private static void displayEntries(DictionaryInterface<Integer, Integer> dict) { 
		// display frequencies 
		for (Entry<Integer, Integer> e : dict.entries())
			System.out.println(e); 

	}
}
