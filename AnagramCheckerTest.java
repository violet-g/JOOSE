package anagramchecker;

import org.junit.Assert;
import org.junit.Test;

public class AnagramCheckerTest {

	@Test
	public void testEmptyString () {
		Assert.assertTrue ("The empty string is an anagram of itself", AnagramChecker.isAnagram("", ""));
	}
	
	@Test
	public void testLowerCase () {
		Assert.assertTrue("A string is an anagram of itself", AnagramChecker.isAnagram ("eager", "eager"));
		Assert.assertTrue("Single word anagrams", AnagramChecker.isAnagram ("eager", "agree"));
		Assert.assertTrue("Single word with white space", AnagramChecker.isAnagram ("  eager", "a g ree"));
		Assert.assertFalse("Different amounts of white space", AnagramChecker.isAnagram ("  eager", "agree"));
		Assert.assertTrue("Punctuation is the same", AnagramChecker.isAnagram("eager; agree ", "; agree eager"));
		
		Assert.assertTrue("A string is an anagram of itself", AnagramChecker.isAnagram("aaa", "aaa"));
		Assert.assertFalse("Different lengths", AnagramChecker.isAnagram("aaaaa", "aaa"));
		Assert.assertFalse("Different lengths", AnagramChecker.isAnagram("aaa", "aaaaa"));
	}

	@Test
	public void testMixedCase () {
		Assert.assertTrue("A string is an anagram of itself, even when the case is changed", AnagramChecker.isAnagram ("EAGER", "eager"));
		Assert.assertTrue("A string is an anagram of itself, even when the case is changed", AnagramChecker.isAnagram ("EaGeR", "EaGeR"));
		Assert.assertTrue("Single word anagrams", AnagramChecker.isAnagram ("eager", "AGREE"));
		Assert.assertTrue("Single word with white space", AnagramChecker.isAnagram ("  eager", "a g ree"));
		Assert.assertFalse("Different amounts of white space", AnagramChecker.isAnagram ("  eager", "agree"));
		Assert.assertTrue("Punctuation is the same", AnagramChecker.isAnagram("eager; agree ", "; agree eager"));
		
		Assert.assertTrue("A string is an anagram of itself", AnagramChecker.isAnagram("AaA", "aaa"));
		Assert.assertFalse("Different lengths", AnagramChecker.isAnagram("aaaAA", "aaa"));
		Assert.assertFalse("Different lengths", AnagramChecker.isAnagram("aaa", "aaaAA"));
	}
}
