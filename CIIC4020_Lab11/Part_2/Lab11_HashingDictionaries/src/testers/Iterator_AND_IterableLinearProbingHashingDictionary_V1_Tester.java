package testers;

import implementations.LinearProbingHashingDictionary_V1;
import interfaces.Entry;

public class Iterator_AND_IterableLinearProbingHashingDictionary_V1_Tester {

	public static void main(String[] args) {
		Integer[] input = {4, 4, 4, 8, 7, 10, 2, 1, 8, 17, 20, 30, 
				23, 27, 29, 38, 48, 33, 43, 80, 92, 1, 6, 5, 7, 
				13, 18, 20, 2, 5, 28, 29, 23, 2, 8, 17, 4, 7, 2, 9, 
				30, 30, 23, 38, 43, 30, 43, 43, 43, 43, 80, 20,
				13, 18, 20, 2, 5, 28, 29, 23, 2, 8, 17, 4, 7, 2, 9, 
				30, 30, 23, 38, 43, 30, 43, 43, 43, 43, 80, 20,
				13, 18, 20, 2, 5, 28, 29, 23, 2, 8, 17, 4, 7, 2, 9, 
				30, 30, 23, 38, 43, 30, 43, 43, 43, 43, 80, 20,
				23, 27, 29, 38, 48, 33, 43, 80, 92, 1, 6, 5, 7, 
				13, 18, 20, 2, 5, 28, 29, 23, 2, 8, 17, 4, 7, 2, 9, 
				30, 30, 23, 38, 43, 30, 43, 43, 43, 43, 80, 20,
				13, 18, 20, 2, 5, 28, 29, 23, 2, 8, 17, 4, 7, 2, 9, 
				30, 30, 23, 38, 43, 30, 43, 43, 43, 43, 80, 20,
				13, 18, 20, 2, 5, 28, 29, 23, 2, 8, 17, 4, 7, 2, 9, 
				30, 30, 23, 38, 43, 30, 43, 43, 43, 43, 80, 20}; 
		

		LinearProbingHashingDictionary_V1<Integer, Integer> dict = new LinearProbingHashingDictionary_V1<>();
		
		for (Integer k : input) {
			Integer c = dict.getValue(k); 
			if (c != null) 
				dict.add(k, c+1); 
			else 
				dict.add(k,  1); 
		}
		
		for (Entry<Integer, Integer> e : dict.entries()) 
			System.out.println(e); 

		System.out.println("\n Keys: "); 
		for (Integer k : dict.keys()) 
			System.out.print(k + ", "); 

		System.out.println("\n Values: "); 
		for (Integer v : dict.values()) 
			System.out.print(v + ", "); 
		
		System.out.println(); 
}

}
