import java.util.*;

public class WordRecommender {
	String filename;
	
	public WordRecommender(String fileName) {
		filename = fileName;
	}
	
	/**
	 * Given two words, computes two measures of similarity and returns the average
	 * @param word1 First word to compare
	 * @param word2 Second word to compare
	 * @return Similarity score
	 */
	public double getSimilarityMetric(String word1, String word2) {
		// Compute leftSimilarity
		List<String> word1Letters = Arrays.asList(word1.split(""));
		List<String> word2Letters = Arrays.asList(word2.split(""));
		int leftSimilarity = computeSimilarity(word1Letters, word2Letters);
		
		// Compute rightSimilarity
		Collections.reverse(word1Letters);
		Collections.reverse(word2Letters);
		int rightSimilarity = computeSimilarity(word1Letters, word2Letters);

		// Average the scores
		double similarityScore = (leftSimilarity + rightSimilarity) / 2.0;
		return similarityScore;
	}
	
	/**
	 * Computes a similarity score for two arrays of letters
	 * @param word1Letters Array of letters arranged in arbitrary sequence
	 * @param word2Letters Array of letters arranged in arbitrary sequence
	 * @return Number of same letters that appear in same index
	 */
	private int computeSimilarity(List<String> word1Letters, List<String> word2Letters) {
		List<String> longerWord = word1Letters.size() >= word2Letters.size() ? word1Letters : word2Letters;
		List<String> shorterWord = word1Letters.size() < word2Letters.size() ? word1Letters : word2Letters;
		int similarityCounter = 0;
		for (int i = 0; i < shorterWord.size(); i++) {
			String let1 = shorterWord.get(i);
			String let2 = longerWord.get(i);
			if (let1.equals(let2)) {
				similarityCounter++;
			}
		}
		return similarityCounter;
	}
	
	/**
	 * Given an incorrectly-spelled word, returns a list of legal word suggestions
	 * @param word
	 * @param n
	 * @param commonPercent
	 * @param topN
	 * @return
	 */
	public ArrayList<String> getWordSuggestions(String word, int n, double commonPercent, int topN) {
		int len = word.length();
		
		// Dedupe the letters in the word we are spell checking
		List<String> lettersInWord = Arrays.asList(word.split(""));
		List<String> lettersInWordWithoutDuplicates = new ArrayList<String>(new HashSet<String>(lettersInWord));
		
		// Get the dictionary
		Dictionary d = new Dictionary("engDictionary.txt");
		
		// Create a sorted hash map for common percent results
		TreeMap<Double, ArrayList<String>> sortedResults = new TreeMap<Double, ArrayList<String>>(Collections.reverseOrder());
		
		// For words in the dictionary's hash map of words by length, check all words of within
		// n characters of the word's length, and add them by common percent value to the sorted
		// results hash map
		int min = len - n > 1 ? len - n : 2;
		for (int i = min; i <= len + n; i++) {
			for (String wrd : d.wordsByLength.get(i)) {
				// Dedupe the dictionary word
				List<String> letters = Arrays.asList(wrd.split(""));
				List<String> lettersWithoutDuplicates = new ArrayList<String>(new HashSet<String>(letters));
				
				double numerator = 0;
				for (String let : lettersWithoutDuplicates) {
					if (lettersInWordWithoutDuplicates.contains(let)) {
						numerator++;
					}
				}
				
				// Dedupe the two words together
				String allLetters = word + wrd;
				List<String> allLettersArray = Arrays.asList(allLetters.split(""));
				List<String> allLettersWithoutDuplicates = new ArrayList<String>(new HashSet<String>(allLettersArray));
				double denominator = allLettersWithoutDuplicates.size();
				
				double result = numerator / denominator;
				
				ArrayList<String> words = sortedResults.get(result);
				
				// If there is no key for the common percentage, create one; 
				// If there is a key already, add the word to its array
				if (words == null) {
					words = new ArrayList<String>();
					words.add(wrd);
					sortedResults.put(result, words);
				} else {
					words.add(wrd);
				}
			}
		}
		
		// Create an array of all words with the necessary common percent
		Double key = 1.1;
		ArrayList<String> results = new ArrayList<String>();
		for (Map.Entry<Double, ArrayList<String>> entry : sortedResults.entrySet()) {
			key = entry.getKey();
			if (key >= commonPercent) {
				ArrayList<String> values = entry.getValue();
				for (String val : values) {
					results.add(val);
				}
			}
		}

		// Create a sorted hash map for similarity metrics
		TreeMap<Double, ArrayList<String>> scoredResults = new TreeMap<Double, ArrayList<String>>(Collections.reverseOrder());
		for (String wrd : results) {
			double score = getSimilarityMetric(word, wrd);
			ArrayList<String> words = scoredResults.get(score);
			
			// If the similarity metric is already a key, add the word to its array
			// If not, create the key
			if (words == null) {
				words = new ArrayList<String>();
				words.add(wrd);
				scoredResults.put(score, words);
			} else {
				words.add(wrd);
			}
		}
		
		// Put the topN words by similarity metric in an array to return
		key = Double.MAX_VALUE;
		ArrayList<String> correctionWords = new ArrayList<String>();
		for (Map.Entry<Double, ArrayList<String>> entry : scoredResults.entrySet()) {
			ArrayList<String> values = entry.getValue();
			for (String val : values) {
				if (correctionWords.size() < topN) {
					correctionWords.add(val);
				}
			}
		}

		return correctionWords;
	}
	
	/**
	 * Given a word and a list of words from a dictionary, returns the list of words 
	 * in the dictionary that have at least (>=) n letters in common. 
	 * @param word Letters to check for
	 * @param listOfWords Words to check for letters
	 * @param n Number of common letters, without duplicates
	 * @return List of words that meet the criteria
	 */
	public ArrayList<String> getWordsWithCommonLetters(String word, ArrayList<String> listOfWords, int n) {
		List<String> letters = Arrays.asList(word.split(""));
		List<String> lettersWithoutDuplicates = new ArrayList<String>(new HashSet<String>(letters));
		ArrayList<String> result = new ArrayList<String>();
		for (String wrd : listOfWords) {
			int counter = 0;
			List<String> lettersInWrd = Arrays.asList(wrd.split(""));
			List<String> lettersInWrdWithoutDuplicates = new ArrayList<String>(new HashSet<String>(lettersInWrd));
			for (int i = 0; i < lettersInWrdWithoutDuplicates.size(); i++) {
				for (String let : lettersWithoutDuplicates) {
					if (let.equals(lettersInWrdWithoutDuplicates.get(i))) {
						counter++;
					}
				}
			}
			if (counter >= n) {
				result.add(wrd);
			}
		}
		return result;
	}
	
	public String prettyPrint(ArrayList<String> list) {
		String options = new String("");
		for (int i = 0; i < list.size(); i++) {
			int num = i + 1;
			options += num + ". ";
			options += list.get(i) + "\n";
		}
		return options;
	}
	
}
