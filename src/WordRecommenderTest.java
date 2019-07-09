import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;

public class WordRecommenderTest {

	private WordRecommender wr = new WordRecommender("engDictionary.txt");


	@Test
	public void similarityMetricTest1() {
		String word1 = "ponderous";
		String word2 = "pandering";

		double expected = 5.0;
		assertEquals(expected, wr.getSimilarityMetric(word1, word2), 0.01);
	}

	@Test
	public void similarityMetricTest2() {
		String word1 = "camper";
		String word2 = "glamper";

		double expected = 2.5;
		assertEquals(expected, wr.getSimilarityMetric(word1, word2), 0.01);
	}

	@Test
	public void similarityMetricTest3() {
		String word1 = "aaaa";
		String word2 = "bbbb";

		double expected = 0.0;
		assertEquals(expected, wr.getSimilarityMetric(word1, word2), 0.01);
	}


	/*
	 * This test checks that only expected words are returned, making this the strictest test
	 * The next tests check that, even if wrong words are returned, there are also words that are correctly returned
	 */
	@Test
	public void commonLettersTest1() {
		ArrayList<String> listOfWords = new ArrayList<>();
		listOfWords.add("cloud");
		listOfWords.add("loo");
		listOfWords.add("bang");
		listOfWords.add("water");
		listOfWords.add("boil");
		listOfWords.add("sling");

		String word = "cloong";

		ArrayList<String> answers = wr.getWordsWithCommonLetters(word, listOfWords, 3);

		// Only expecting "cloud" and "sling"
		if( answers.size() != 2 ) {
			fail( "getWordsWithCommonLetters returned the wrong number of words" );
		}

		for( String str : answers ) {

			// Should not contain these words
			if( str.equals("loo") || str.equals("bang") || str.equals("boil") || str.equals("water") ) {
				fail( "getWordsWithCommonLetters did not return the correct words" );
			}
		}


	}

	/*
	 * This is a way for students to get partial credit if they properly return some words
	 */
	@Test
	public void commonLettersTest2() {
		ArrayList<String> listOfWords = new ArrayList<>();
		listOfWords.add("cloud");
		listOfWords.add("loo");
		listOfWords.add("bang");
		listOfWords.add("water");
		listOfWords.add("boil");
		listOfWords.add("sling");

		String word = "cloong";

		ArrayList<String> answers = wr.getWordsWithCommonLetters(word, listOfWords, 3);

		for( String str : answers ) {

			// If it returns cloud or sling successfully, pass the test
			if( str.equals("cloud") || str.equals("sling") ) {
				return;
			}
		}

		fail("getWordsWithCommonLetters did not return the correct words");
	}
	
	/*
	 * This is a way for students to get partial credit if they don't return words that shouldn't be returned
	 */
	@Test
	public void commonLettersTest3() {
		ArrayList<String> listOfWords = new ArrayList<>();
		listOfWords.add("cloud");
		listOfWords.add("loo");
		listOfWords.add("bang");
		listOfWords.add("sling");

		String word = "cloong";

		ArrayList<String> answers = wr.getWordsWithCommonLetters(word, listOfWords, 3);

		if( answers.contains("loo") || answers.contains("bang") ) {
			fail( "getWordsWithCommonLetters returned words that were not expected" );
		}

	}
	


	/*
	 * This checks that the proper words are returned from getWordSuggestions
	 * It will not accept extra words, and will fail if all expected words are not returned
	 * This test does not test if they are in sorted order (they all have a similarityMetric of 4.0)
	 */
	@Test
	public void wordSuggestionTest1() {

		String incorrectWord = "starf";
                                  
		// Adds all of these to a HashSet
		String expectedWords[] = {"staff", "stars", "afars", "start", "staffs", "star", "staffer", "starts"};
		HashSet<String> answers = new HashSet<>();
		for( String str : expectedWords ) {
			answers.add(str);
		}

		ArrayList<String> actuals = wr.getWordSuggestions(incorrectWord, 2, 0.80, 8);
		

		// For the above expectedWords
		for( String actual : actuals ) {

			if( !answers.contains(actual) ) {
				fail("getWordSuggestions inaccurately returned '" + actual + "'");
			} else {
				answers.remove(actual);
			}
		}

		if( !answers.isEmpty() ) {
			fail("getWordSuggestions did not return all the expected words");
		}		
	}


	/**
	 * This test checks that the returned words are returned in order
	 * This will not fail if extra words are returned
	 */
	@Test
	public void wordSuggestionTest2() {

		String incorrectWord = "helloarvind";

		String expectedWords[] = {"overloading", "revalidation", "headliner"};
		ArrayList<String> actuals =  wr.getWordSuggestions(incorrectWord, 2, 0.80, 3);

		// Checks that the first word matches
		assertEquals( expectedWords[0], actuals.get(0), "getWordSuggestions returned an incorrect order" );

		// Checks that the fifth word matches
		assertEquals( expectedWords[2], actuals.get(2), "getWordSuggestions returned an incorrect order" );

	}



	/**
	 * This test checks that all expected words are properly returned
	 * This is less strict than the first wordSuggestionTest, as it will not fail if extra words are returned
	 * Essentially a partial-credit tester
	 */
	@Test
	public void wordSuggestionTest3() {

		String incorrectWord = "writed";

		String expectedWords[] = {"waited", "whited", "writer", "writes"};
		HashSet<String> expected = new HashSet<>();
		for( String str : expectedWords ) {
			expected.add(str);
		}

		ArrayList<String> actuals =  wr.getWordSuggestions(incorrectWord, 4, 0.70, 4);

		
		// Only fails if one of the expected words is not returned
		for( String str : actuals ) {
			expected.remove(str);
		}

		assertEquals(0, expected.size(), "getWordSuggestions did not properly return all expected words");
	}



	/**
	 * This test checks that improper words were not returned
	 * This is less strict than the first wordSuggestionTest, as it will not fail if not all the correct words are returned
	 * Essentially a partial-credit tester
	 */
	@Test
	public void wordSuggestionTest4() {

		String incorrectWord = "writed";

		String expectedWords[] = {"waited", "whited", "writer", "writes"};
		HashSet<String> expected = new HashSet<>();
		for( String str : expectedWords ) {
			expected.add(str);
		}

		ArrayList<String> actuals =  wr.getWordSuggestions(incorrectWord, 4, 0.70, 4);


		// Only fails if an unexpected word is returned
		for( String str : actuals ) {
			if( !expected.contains(str) ) {
				fail("getWordSuggestions returned unexpected words" );
			}
		}

	}


	/**
	 *  This test checks for words with a common-letter similarity of 100%
	 *  but allows for +/- 4 letters in length
	 *  
	 *  Checks that the proper words, and only proper words, are returned
	 *  Will fail otherwise
	 */
	@Test
	public void wordSuggestionTest5() {

		String incorrectWord = "writed";

		String expectedWords[] = {"twittered"};
		HashSet<String> expected = new HashSet<>();
		for( String str : expectedWords ) {
			expected.add(str);
		}
		ArrayList<String> actuals =  wr.getWordSuggestions(incorrectWord, 4, 1, 5);

		// For the above expectedWords
		for( String actual : actuals ) {

			if( !expected.contains(actual) ) {
				fail("getWordSuggestions inaccurately returned '" + actual + "'");
			} else {
				expected.remove(actual);
			}
		}

		if( !expected.isEmpty() ) {
			fail("getWordSuggestions did not return all the expected words");
		}	

	}
	
	

}