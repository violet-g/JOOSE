package words;

import java.util.HashMap;

public class CachingWordAnalyzer extends SimpleWordAnalyzer {

	private HashMap<Character, String> longestWords;
	private HashMap<Character, String> mostOccurrencesWords;

	public CachingWordAnalyzer(String fileName) {
		super(fileName);
		longestWords = new HashMap<Character, String>();
		mostOccurrencesWords = new HashMap<Character, String>();
	}

	public String longestWordStartingWith(char c) throws IllegalArgumentException {
		if (longestWords.containsKey(c)) {
			return longestWords.get(c);
		}
		String result = super.longestWordStartingWith(c);
		longestWords.put(c, result);
		return result;
	}

	public String wordWithMostOccurrencesOf(char c) throws IllegalArgumentException {
		if (mostOccurrencesWords.containsKey(c)) {
			return mostOccurrencesWords.get(c);
		}
		String result = super.wordWithMostOccurrencesOf(c);
		mostOccurrencesWords.put(c, result);
		return result;
	}
}
