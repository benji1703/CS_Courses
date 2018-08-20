package dict;


import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test a persistent dictionary.
 * 
 * This test is abstract, and must be subclassed to test for a concrete 
 * PersistentDictionary. 
 * @author talm
 *
 */
public abstract class PersistentDictionaryTest {

	/**
	 * Return a persistent dictionary. If one doesn't exist by that name, create it.
	 * Subclasses should override this method to return a concrete PersistentDictionary.
	 * @param name
	 * @return
	 */
	abstract PersistentDictionary getDictionary(File file) throws IOException;

	File dictFile;

	public PersistentDictionaryTest() {
		try {
			dictFile = File.createTempFile("DictTest", ".tmp");
		} catch (IOException e) {
			Assert.fail("Couldn't create temporary file for testing");
		}
	}

	String[] testWords = {
			"Second",
			"First",
			"CAB",
			"BBA",
			"",
			"1",
			"4"
	};

	String[] testDefs = {
			"Some definition",
			"another definition",
			"",
			"yet another definition",
			"lorem ipsum dolor",
			"",
			"- ? d  ddd cccc e"
	};

	String[] addWords = {
			"Second",
			"Third",
			"AAAA",
			"CAB"
	};


	String[] addDefs = {
			"A completely different definition",
			"1 2 3",
			"BBBB",
			"yahoo!",
	};


	/**
	 * Fill a dictionary with words.
	 * Any class with a "put(String,String)" method is supported as a dictionary.
	 * 
	 * @param dict the dictionary to be filled
	 * @param words the array of words that will fill it
	 * @param defs the definitions corresponding to the words (must have the same length as words).
	 * @throws Exception may throw a reflection-related exception if dict 
	 * 	is not a valid dictionary or an IOException if dict.put does.
	 */
	void fillMap(Object dict, String[] words, String[] defs)  throws Exception {
		Class<?> dictClass = dict.getClass();

		Method put = null;
		
		// Go over all the methods of dict and see if there is a put(*,*) method
		// for which both arguments can be assigned Strings. (this matches (put(Object,Object) and put(String,String)).
		for (Method m : dictClass.getMethods()) {
			if (!m.getName().equals("put"))
				continue;
			 Class<?>[] params = m.getParameterTypes();
			 if (params.length == 2 && params[0].isAssignableFrom(String.class) && params[1].isAssignableFrom(String.class)) {
				put = m;
			 	break;
			 }
		}
		if (put == null)
			throw new NoSuchMethodException();
		
		for (int i = 0; i < words.length; ++i) {
			put.invoke(dict, words[i], defs[i]);
		}
	}
	
	/**
	 * Compare a Java map to a dictionary. Checks that the sizes are the same, then iterates
	 * over all keys in the map and compares the values.
	 * @param map
	 * @param dict
	 * @return
	 * @throws IOException
	 */
	void testEquality(String msg, Map<String,String> map, PersistentDictionary dict) throws IOException {
		assertEquals(msg + ": Map and dictionary have different sizes", map.size(), dict.size());
		
		for (String key : map.keySet()) {
			assertEquals(msg + ": Recall failed for key " + key, map.get(key), dict.get(key));
		}
	}

	/**
	 * Test that the dictionary can recall words that were stored in it.
	 * @throws Exception
	 */
	@Test
	public void fullRecallTest() throws Exception {
		TreeMap<String,String> expected = new TreeMap<String, String>();
		fillMap(expected, testWords, testDefs);

		dictFile.delete();
		PersistentDictionary dict = getDictionary(dictFile);
		assertNotNull(dict);

		dict.open();
		fillMap(dict, testWords, testDefs);

		// In memory test
		testEquality("In-memory recall", expected, dict);

		dict.close();


		// Persistence test
		dict = getDictionary(dictFile);
		dict.open();

		testEquality("Persistent recall", expected, dict);

		// Combined test
		fillMap(expected, addWords, addDefs);
		fillMap(dict, addWords, addDefs);

		testEquality("Combined recall", expected, dict);

		dict.close();
		dictFile.delete();
	}

	/**
	 * Check that elements disappear after we remove them.
	 * @throws Exception
	 */
	@Test
	public void removeElementsInMemoryTest() throws Exception  {
		TreeMap<String,String> expected = new TreeMap<String, String>();
		fillMap(expected, testWords, testDefs);

		dictFile.delete();
		PersistentDictionary dict = getDictionary(dictFile);
		assertNotNull(dict);

		dict.open();
		fillMap(dict, testWords, testDefs);
		for (String word : addWords) {
			expected.remove(word);
			dict.remove(word);
		}

		// In memory test
		testEquality("In-memory remove", expected, dict);

		dict.close();
		dictFile.delete();
	}

	@Test
	public void removeElementsPersistentTest() throws Exception  {
		TreeMap<String,String> expected = new TreeMap<String, String>();
		fillMap(expected, testWords, testDefs);

		dictFile.delete();
		PersistentDictionary dict = getDictionary(dictFile);
		assertNotNull(dict);

		dict.open();
		fillMap(dict, testWords, testDefs);
		dict.close();

		// Persistence test
		dict = getDictionary(dictFile);
		dict.open();

		for (String word : addWords) {
			expected.remove(word);
			dict.remove(word);
		}

		dict.close();
		dict = getDictionary(dictFile);
		dict.open();

		testEquality("Persistent remove", expected, dict);

		dict.close();
		dictFile.delete();
	}

	@Test
	public void clearTest() throws Exception  {
		TreeMap<String,String> expected = new TreeMap<String, String>();
		fillMap(expected, testWords, testDefs);

		dictFile.delete();
		PersistentDictionary dict = getDictionary(dictFile);
		assertNotNull(dict);

		dict.open();
		fillMap(dict, testWords, testDefs);
		dict.close();

		// Filled dictionary and closed, now we open and clear.
		dict = getDictionary(dictFile);
		dict.open();

		dict.clear();
		expected.clear();

		fillMap(expected, addWords, addDefs);
		fillMap(dict, addWords, addDefs);

		// Check that in-memory everything looks good.
		
		testEquality("In-memory comparison after clear()", expected, dict);

		dict.close();

		// Check that after persisting the comparison is good.
		dict = getDictionary(dictFile);
		dict.open();
		// Check that in-memory everything looks good.
		testEquality("Persistent comparison after clear()", expected, dict);

		dict.close();
		dictFile.delete();

	}

}
