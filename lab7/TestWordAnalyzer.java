package words;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

import java.lang.reflect.Constructor;

/**
 * Java lab 8 JUnit test cases for the WordAnalyzer
 * 
 * @author jsinger
 */

public class TestWordAnalyzer {

	private static WordAnalyzer wa;

	@Before
	public void setup() {
		// replace with CachingWordAnalyzer if
		// you implement this too...
		wa = new SimpleWordAnalyzer("words.txt");
	}

	@After
	public void teardown() {
		wa = null;
	}

	/**
	 * check correct outputs for 'a'
	 */
	@Test
	public void testLongestWord1() {
		String s = wa.longestWordStartingWith('a');
		assertTrue("longest A word is alphabets", s.equals("alphabet"));
	}

	@Test
	public void testMostCharsWord1() {
		String s = wa.wordWithMostOccurrencesOf('a');
		assertTrue("word with most A's is aardvark", s.equals("aardvark"));
	}

	/**
	 * check empty string (not null string) outputs for 'z'
	 */
	@Test
	public void testLongestWord2() {
		String s = wa.longestWordStartingWith('z');
		assertTrue("longest Z word is empty string", s.equals(""));
	}

	@Test
	public void testMostCharsWord2() {
		String s = wa.wordWithMostOccurrencesOf('z');
		assertTrue("word with most Z's is empty string", s.equals(""));
	}

	/**
	 * check incorrect inputs
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testIllegalInputCaps1() {
		String s = wa.longestWordStartingWith('A');
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIllegalInputPunctuation1() {
		String s = wa.longestWordStartingWith('!');
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIllegalInputCaps2() {
		String s = wa.wordWithMostOccurrencesOf('Z');
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIllegalInputPunctuation2() {
		String s = wa.wordWithMostOccurrencesOf('?');
	}

	// test caching word analyser
	@Test
	public void testCachingWordAnalyzer() throws Exception {
		Class cacheWAClass = Class.forName("words.CachingWordAnalyzer");
		Constructor<?> cons = cacheWAClass.getConstructor(java.lang.String.class);
		Object o = cons.newInstance("words.txt");
		wa = (WordAnalyzer) o;

		// now run a test...
		String s1 = wa.wordWithMostOccurrencesOf('a');
		assertTrue("word with most A's is aardvark", s1.equals("aardvark"));
		String s2 = wa.wordWithMostOccurrencesOf('a');
		// the second call should return the same string value
		assertTrue("caching word analyzer does not return same string for repeated call", s1.equals(s2));
		// ALSO the second call should return the same string object, if
		// cached...
		assertTrue("caching word analyzer does not cache strings", s1 == s2);

	}

	@Test
	public void testCachingWordAnalyzer2() throws Exception {
		Class cacheWAClass = Class.forName("words.CachingWordAnalyzer");
		Constructor<?> cons = cacheWAClass.getConstructor(java.lang.String.class);
		Object o = cons.newInstance("words.txt");
		wa = (WordAnalyzer) o;

		// now run a test...
		String s1 = wa.longestWordStartingWith('a');
		assertTrue("longest A word is alphabet", s1.equals("alphabet"));
		String s2 = wa.longestWordStartingWith('a');
		// the second call should return the same string value
		assertTrue("caching word analyzer does not return same string for repeated call", s1.equals(s2));
		// ALSO the second call should return the same string object, if
		// cached...
		assertTrue("caching word analyzer does not cache strings", s1 == s2);

	}

}