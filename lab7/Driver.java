package words;

/**
 * Java lab 7 Client code for the SimpleWordAnalyzer iterates over letters of
 * the alphabet and prints, for each letter, longest word starting with that
 * letter and word with most occurrences of that letter.
 * 
 * @author mefoster (based on code from jsinger)
 */
public class Driver {

	/**
	 * args[0] is String filename for input txt file containing words, one per
	 * line
	 */
	public static void main(String[] args) {
		for (char c = 'a'; c <= 'z'; c++) {
			WordAnalyzer wa = new SimpleWordAnalyzer(args[0]);
			System.out.printf("%c %s %s\n", c, wa.longestWordStartingWith(c), wa.wordWithMostOccurrencesOf(c));
		}
	}
}