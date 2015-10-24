package scrabblescorer;

import java.util.Scanner;

public class ScrabbleScorer {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter string: ");
		String str = scanner.nextLine();
		int score = computeScore(str);
		System.out.println("The score for '" + str + "' is: " + score);
		scanner.close();
	}

	public static int computeScore(String str) {

		String string = str.toLowerCase();
		int score = 0;

		for (int index = 0; index < string.length(); index++) {

			char letter = string.charAt(index);
			int letterscore = 0;

			switch (letter) {
			case 'a':
			case 'e':
			case 'i':
			case 'l':
			case 'n':
			case 'o':
			case 'r':
			case 's':
			case 't':
			case 'u':
				letterscore = 1;
				break;
			case 'd':
			case 'g':
				letterscore = 2;
				break;
			case 'b':
			case 'c':
			case 'm':
			case 'p':
				letterscore = 3;
				break;
			case 'f':
			case 'h':
			case 'v':
			case 'w':
			case 'y':
				letterscore = 4;
				break;
			case 'k':
				letterscore = 5;
				break;
			case 'j':
			case 'x':
				letterscore = 8;
				break;
			case 'q':
			case 'z':
				letterscore = 10;
				break;
			default:
				System.out.println("Invalid character: " + letter);
				letterscore = 0;
				break;
			}
			score += letterscore;
		}
		return score;
	}
}