package anagramchecker;

import java.util.Scanner;

public class AnagramChecker {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter string 1: ");
		String string1 = scanner.nextLine();
		System.out.print("Enter string 2: ");
		String string2 = scanner.nextLine();

		boolean check = isAnagram(string1, string2);
		System.out.println("Anagram check for '" + string1 + "' and '" + string2 + "': " + check);
		scanner.close();
	}

	public static boolean isAnagram(String string1, String string2) {

		if (string1.isEmpty() && string2.isEmpty()) {
			return true;
		}

		String str1 = string1.toLowerCase();
		String str2 = string2.toLowerCase();
		
		char first[] = str1.toCharArray();
		char second[] = str2.toCharArray();

		sort(first);
		sort(second);
		
		return equals(first,second);
	}
	
	public static boolean equals(char first[], char second[]) {
		if (first.length == second.length) {
			for ( int index = 0; index < first.length; index++ ) {
				if (first[index] != second[index]) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	public static char[] sort(char[] chars) {
		int index = 1;
		char temp;
		while (index < chars.length) {
			if (chars[index] < chars[index - 1]) {
				temp = chars[index];
				chars[index] = chars[index - 1];
				chars[index - 1] = temp;
				index--;
				if (index == 0) {
					index = 1;
				}
			} else {
				index++;
			}
		}
		return chars;
	}
}