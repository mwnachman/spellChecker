import java.io.*;
import java.util.*;



public class WordRecommender {
	String filename;
	
	public WordRecommender(String fileName) {
		filename = fileName;
	}
	
	public double getSimilarityMetric(String word1, String word2) {
		return 0;
		/**
		 * given two words, this function computes two measures of similarity and returns the average.
leftSimilarity (a made-up metric) – the number of letters that match up between word1 and
word2 as we go from left to right and compare character by character.
So the leftSimilarity for ’oblige’ and ’oblivion’ is 4, the leftSimilarity for ’aghast’ and ’gross’ is 1.
For the “oblige” and “oblivion” example the first character is an o for both strings, the second
character is a b for both, the third character is an l, the fourth character is an i and then nothing
else lines up.
rightSimilarity (another made-up metric) – the number of letters that match up, but this time
going from right to left.
So the rightSimilarity for ‘oblige’ and ‘oblivion’ is 1.
the rightSimilarity for ‘aghast’ and ‘gross’ is 2.
4
MCIT Online - 591 - Introduction to Software Engineering
For the aghast and gross example, the last character is a t and an s respectively so those do not
count, the second last character is an s in both cases, the a and the o do not line up, the h and
the r do not line up, and finally the fifth character from the end is a g in both cases and
therefore that contributes to the score as well.
Finally to get the similarity score, take the average of leftSimilarity and rightSimilarity and
return that value. So getSimilarityMetric(‘oblige’, ‘oblivion’) will return (4+1)/2.0 = 2.5
		 */
	}
	
	public ArrayList<String> getWordSuggestions(String word, int n, double commonPercent, int topN) {
		return null;
		/**
		 * given an incorrect word, return a list of legal word suggestions as per an algorithm given below.
You can safely assume this function will only be called with a word that is not already present in
the dictionary. commonPercent should be a double between 0.0, corresponding to 0%, and
1.0, corresponding to 100%.
To come up with a list of candidate words, we first come up with a list of candidate words that
satisfy both of these two criteria:
a) candidate word length is word length +/- n characters .
b) have at least commonPercent% of the letters in common.
We will define commonPercent mathematically as follows: Consider two words w1 and w2.
Create the set of letters in w1, call that S1 (remember it is a set so repeated letters show up
only once). Create the set of letters in w2, call that S2. Then commonPercent is defined as
Number of elements in S1 intersected with S2 / Number of elements in S1 union S2.
Or
Number of letters that are common across the two sets (Set 1 and Set 2) divided by the Total
number of letters in Set 1 and Set 2 (with each letter counting exactly once).
Here are two examples:
w1 - committee. Then S1 = {c, o, m, i, t, e}
w2 - comet. Then S2 = {c , o, m, e, t}
Then the numerator is the letters in common {c, o, m, e, t}. 5 of them
The denominator is all the letters {c, o, m, e, t, i}. 6 of them
5
MCIT Online - 591 - Introduction to Software Engineering
Therefore the percent is 5/6
w1 - gardener - {g, a, r, d, e, n}
w2 - nerdier - {n, e, r, d, i}
Numerator = letters in common = {n, e, r, d}. 4 of them
Denominator = all the letters = {g, a, r, d, e, n, i}. 7 of them
Common percent = 4/7
Next, for all the words that satisfy these two criteria, order them based on the similarity metric
(see above) and return the topN number of them.
This method involves more work so please ensure that you have written the previous methods
first.
		 */
	}
	
	public ArrayList<String> getWordsWithCommonLetters(String word, ArrayList<String> listOfWords, int n) {
		return listOfWords;
		/**
		 * This method is to give you more practice with string manipulation-- it won’t be used in the Spell
Checker.
Given a word and a list of words from a dictionary, return the list of words in the dictionary that
have at least (>=) n letters in common. For the purposes of this method, we will only consider
the distinct letters in the word. The position of the letters doesn’t matter.
Consider a wordList to contain [‘ban’, ‘bang’, ‘mange’, ‘gang’, ‘cling’, ‘loo’] and the word we
have is ‘cloong’.
Then we want the result of
getWordsWithCommonLetters(‘cloong’, wordList, 2) will return [‘bang’,
‘mange’, ‘gang’, ‘cling’, ‘loo’]
and
getWordsWithCommonLetters(‘cloong’, wordList, 3) will return
[‘cling’].
Note that we only count distinct letters, which is why the word ‘loo’ does not appear in the
second example.
		 */
	}
	
	public String prettyPrint(ArrayList<String> list) {
		
		return filename;
		/**
		 * Finally, here is a method that you need to write purely for display purposes. This method takes
an ArrayList and returns a String which when printed will have the list elements with a number
in front of them
prettyPrint([“biker”, “tiger”, “bigger”]) returns the string “1. biker\n2.
tiger\n3. bigger\n” so that when you print it you get
1. biker
2. tiger
3. bigger
Finally, in your README, you should assess the above design, and provide one recommendation
for how it could be made better. Think about the principles we’ve discussed in class such as
cohesion, coupling, and DRY, among other considerations.
		 */
	}
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println("Please enter the filename of the file you would like to spell check. ");
		String filename = in.next();
		
		WordRecommender spellChecker = new WordRecommender(filename);
		
		File fileToCheck = new File(filename);
		Scanner s = null;
		
		try {
			s = new Scanner(fileToCheck);
			System.out.println(s.next());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		in.close();
	}
	
}
