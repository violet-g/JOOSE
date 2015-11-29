package words;

/**
 * analyse a file of input text (assume a single word per line)
 * to determine longest words and words with most occurrences of
 * particular characters.
 */
public interface WordAnalyzer {

  /**
   * @param c the character that the word must begin with
   * @return longest word (in terms of number of characters)
   *         from the input file, that begins with the letter
   *         supplied
   * @throws IllegalArgumentException if the character is not
   *         a lowercase Latin alphabetic letter 
   */
  String longestWordStartingWith(char c);

  /**
   * @param c the character that the word must contain
   * @return word from the input file that contains the most
   *         occurences of the supplied letter
   * @throws IllegalArgumentException if the character is not
   *         a lowercase Latin alphabetic letter 
   */
  String wordWithMostOccurrencesOf(char c);

}