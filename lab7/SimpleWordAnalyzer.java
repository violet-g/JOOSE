package words;

import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class SimpleWordAnalyzer implements WordAnalyzer {

	public List<String> words;

	public SimpleWordAnalyzer(String fileName) {
		try {
			words = Files.readAllLines(Paths.get(fileName));
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}

	public String longestWordStartingWith(char c) throws IllegalArgumentException {
		validateChar(c);
		String longestWord = "";
		for (String w : words) {
			String word = w.toLowerCase();
			char firstLetter = word.charAt(0);
			if (firstLetter == c && word.length() > longestWord.length()) {
				longestWord = word;
			}
		}
		return longestWord;
	}

	public String wordWithMostOccurrencesOf(char c) throws IllegalArgumentException {
		validateChar(c);
		String mostOccurrencesWord = "";
		int count = 0;
		for (String w : words) {
			String word = w.toLowerCase();
			int occurrences = numOccurrencesOf(c, word);
			if (occurrences > count) {
				count = occurrences;
				mostOccurrencesWord = word;
			}
		}
		return mostOccurrencesWord;
	}
	
	private void validateChar(char c) throws IllegalArgumentException {
		if (c < 'a' || c > 'z') {
			throw new IllegalArgumentException();
		}
	}
	
	private int numOccurrencesOf(char c, String word) {
		if (word.indexOf(c) == -1) {
			return 0;
		}
		int count = 0;
		for (char letter : word.toCharArray()) {
			if (letter == c) {
				count++;
			}
		}
		return count;
	}
}
