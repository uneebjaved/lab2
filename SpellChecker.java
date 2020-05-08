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
             //System.out.println(s);
             dictionary.put(s, s);
         }
	}
	
	public SpellChecker(String fileName) {
		dictionary = new SeparateChainingHashST<String, String>();
		initDictionary(dictionary, fileName);
	}
	
	public static void main(String[] args) {
		 SpellChecker spellChecker = new SpellChecker("words.txt");
		 String newWord;
		 ArrayList<String> ways = new ArrayList<String>();
		 ArrayList<String> possibleWords = new ArrayList<String>();
		 String s_alphabets = "abcdefghijklmnopqrstuvwxyz";
		 String b_alphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; 

		 System.out.print("Enter a word: ");
		 //String words = "abcd";
		 //System.out.println(words.substring(0));
		 do {
		 /*
		  * TODO: Add code here:
		  * Prompt the user to enter a word
		  * Read in a word
		  * If the word is correctly spelled, print "no mistakes found"
		  * Otherwise, use the five rules to check for mistakes and print a list of suggestions.
		  */

			 
			 String word;
			 word = StdIn.readString();
			 if(spellChecker.dictionary.contains(word))
				 System.out.println("no mistakes found");
			 else {
				 
				 for(int i = 0; i <26; i++) {
					 ways.add(s_alphabets.charAt(i)+word);
					 ways.add(word+s_alphabets.charAt(i));					 
				}
				 ways.add(word.substring(1));
				 ways.add(word.substring(0, word.length()-1));
				 for(int j = 0; j < word.length()-1; j++) {
					newWord = word.substring(0,j) + word.charAt(j+1) + word.charAt(j) + word.substring(j+2);
					ways.add(newWord);
				 }
				 for(String full : ways) {
					 if(spellChecker.dictionary.contains(full)) {
						 if(!possibleWords.contains(full))
							 possibleWords.add(full);
					 }
				 }
				 System.out.println("Possible words: " + possibleWords.toString());
			 }
			 
			 System.out.println();
			 System.out.print("Enter a word: ");
		 } while(!StdIn.isEmpty());
		 // Note: When running the code, hold down the Control key while pressing D to end the program.
	}
}
