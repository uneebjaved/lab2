/*
 * The words.txt file is from https://github.com/dwyl/english-words
 */
import edu.princeton.cs.algs4.SeparateChainingHashST;

import java.util.ArrayList;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;

public class SpellChecker {
	SeparateChainingHashST<String, String> dictionary;

	private static void initDictionary(SeparateChainingHashST<String, String> dictionary, String fileName) {
		In input = new In(fileName);

		while (!input.isEmpty()) {
			String s = input.readLine();
			/*
			 * TODO: Remove the following line and change it to add an entry into the "dictionary"
			 * hash table.
			 */
			dictionary.put(s, s);
		}
	}

	public SpellChecker(String fileName) {
		dictionary = new SeparateChainingHashST<String, String>();
		initDictionary(dictionary, fileName);
	}

	public static void main(String[] args) {
		//make a spellChecker for the file to check
		SpellChecker spellChecker = new SpellChecker("words.txt");
		
		//all possible words that can be made by applying the given rules
		ArrayList<String> ways = new ArrayList<String>();
		//correct possible words that can be formed
		ArrayList<String> possibleWords = new ArrayList<String>();
		//strings to identify how words can be formed
		String s_alphabets = "abcdefghijklmnopqrstuvwxyz";
		String b_alphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; 
		
		//a word that is formed by applying one of the rules
		String newWord;
		
		System.out.print("Enter a word: ");

		do {
			/*
			 * TODO: Add code here:
			 * Prompt the user to enter a word
			 * Read in a word
			 * If the word is correctly spelled, print "no mistakes found"
			 * Otherwise, use the five rules to check for mistakes and print a list of suggestions.
			 */

			//the word entered by the client
			String word;
			word = StdIn.readString();
			
			//if the dictionary contains such a word, then give a correct response
			if(spellChecker.dictionary.contains(word))
				System.out.println("no mistakes found");
			//if not
			else {
				//add a small character at the beginning and end of the word to ways
				for(int i = 0; i <26; i++) {
					ways.add(s_alphabets.charAt(i)+word);
					ways.add(word+s_alphabets.charAt(i));					 
				}
				
				//remove the first character of the word and add it to ways
				ways.add(word.substring(1));
				//remove the last character of the word and add it to ways
				ways.add(word.substring(0, word.length()-1));
				
				//switch adjacent characters of the word and add all new strings to ways
				for(int j = 0; j < word.length()-1; j++) {
					newWord = word.substring(0,j) + word.charAt(j+1) + word.charAt(j) + word.substring(j+2);
					ways.add(newWord);
				}
				
				//for every possible word in ways, check if the dictionary contains that word, if our possibleWords ArrayList does not
				//currently contain that word, then add it. if it does, then go to the next word in ways
				for(String full : ways) {
					if(spellChecker.dictionary.contains(full)) {
						if(!possibleWords.contains(full))
							possibleWords.add(full);
					}
				}
				//print all of the correct possible words that can be made by applying the five rules
				System.out.println("Possible words: " + possibleWords.toString());
			}

			System.out.println();
			System.out.print("Enter a word: ");
		} while(!StdIn.isEmpty());
		// Note: When running the code, hold down the Control key while pressing D to end the program.
	}
}
