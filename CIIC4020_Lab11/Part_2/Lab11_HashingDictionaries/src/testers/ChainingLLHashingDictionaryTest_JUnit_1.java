package testers;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import implementations.ChainingLLHashingDictionary;
import interfaces.DictionaryInterface;

public class ChainingLLHashingDictionaryTest_JUnit_1 {
	DictionaryInterface<Integer, Character> dict;
	@Before
	public void setUp() throws Exception {
		dict = new ChainingLLHashingDictionary<>(); 
		Character[] letras = {'a', 'c', 'a', 'd', 'b', 'f', 'h', 'w', 'k', 'b'}; 
		Integer[] keys = {5, 9, 8, 1, 6, 10, 13, 2, 11, 20};  
		for (Integer i=0; i<letras.length; i++)
			dict.add(keys[i],  letras[i]); 
	}

	@Test
	public void test1() {
		dict = new ChainingLLHashingDictionary<>();
		assertTrue(dict.isEmpty()); 
		Character r = dict.add(5, 'S'); 
		assertTrue(!dict.isEmpty()); 
		assertEquals(r, null); 
		assertTrue(dict.size()==1); 
	}

	@Test
	public void test2() {
		int s = dict.size(); 
		Character r = dict.add(7, 'M');
		assertEquals(r, null); 
		assertEquals(dict.size(), s+1); 
		r = dict.getValue(7); 
		assertTrue(r == 'M'); 
	}

	@Test
	public void test3() {
		int s = dict.size(); 
		Character r = dict.add(7, 'M');
		assertEquals(r, null); 
		assertEquals(dict.size(), s+1); 
	}

	@Test
	public void test4() {
		int s = dict.size(); 
		Character r = dict.add(8, 'M');
		assertTrue(r == 'a'); 
		assertTrue(dict.getValue(8) == 'M'); 
		assertEquals(dict.size(), s); 
	}

	@Test
	public void test5() {
		int s = dict.size(); 
		Character r = dict.remove(30);
		assertTrue(r == null); 
		assertTrue(dict.getValue(30) == null); 
		assertEquals(dict.size(), s); 
		
		r = dict.remove(2); 
		assertTrue(r == 'w'); 
		assertEquals(dict.size(), s-1); 
		
		assertTrue(dict.getValue(2) == null); 

	}

	
}
