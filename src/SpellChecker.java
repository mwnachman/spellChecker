import java.io.*;
import java.util.*;

public class SpellChecker {
	private static String filename;
	private static WordRecommender wc;
	private static Dictionary d;
	private static Scanner in;

	/**
	 * Gets the file to spell check and handles errors if not a valid file
	 * @return Scanner with file ready to read
	 */
	private static Scanner getFile() {
		System.out.println("Please enter the name of the file you would like to spell check. ");
		filename = in.nextLine();
		File fileToCheck = new File(filename);
		Scanner s = null;
		if (fileToCheck.exists()) {
			try {
				s = new Scanner(fileToCheck);
				return s;
			} catch (FileNotFoundException e) {
				System.out.println("That was not a valid file.");
				return getFile();
			} 
		} else {
			System.out.println("That was not a valid file.");
			return getFile();
		}
	}
	
	/**
	 * Creates a file of all words in lowercase
	 * @param s Scanner with readable file
	 */
	private static void createLowerCaseFile(Scanner s) {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileWriter(new File("lowerCase.txt")));
			while (s.hasNextLine()) {
				String line = s.nextLine();
				pw.println(line.toLowerCase());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			pw.flush();
			pw.close();
			s.close();
		}
	}
	
	/**
	 * Offers the user replacement suggestions and requests input about how to proceed
	 * @param word Misspelled word
	 * @return Replacement word
	 */
	private static String getUserPreference(String word) {
		// Use default word suggestion parameters - within two characters, 0.8 common percent, three suggestions
		ArrayList<String> suggestions = wc.getWordSuggestions(word, 2, 0.8, 3);
		String response = null;
		String choice = null;
		
		System.out.println("The word " + word + " is misspelled.");
		
		// If there are suggestions to fix the word, offer them
		if (suggestions.size() > 0) {
			System.out.println("The following suggestions are available: ");
			System.out.print(wc.prettyPrint(suggestions));			
			System.out.println("Press 'r' for replace, 'a' for accept as is, 't' for type in manually.");
			choice = in.nextLine();
			if (choice.equals("r")) {
				System.out.println("Your word will now be replaced with one of the suggestions.");
				System.out.println("Please enter the number corresponding to the word you want to use for replacement");
				String selection = in.nextLine();
				int i = 0;
				try {
					i = Integer.parseInt(selection);
				} catch (NumberFormatException e) {
					System.out.println("That is not a valid selection. ");
					return getUserPreference(word);
				}
				int idx = i - 1;
				if (idx >= 0 && idx < suggestions.size()) {
					return suggestions.get(idx);
				} else {
					System.out.println("That is not a valid selection. ");
					return getUserPreference(word);
				}
			} else if (choice.equals("a")) {
				return word;
			} else if (choice.equals("t")) {
				System.out.println("Please type the word that will be used as the replacement in the output file.");
				response = in.nextLine();
			} else {
				System.out.println("That was not a valid selection.");
				return getUserPreference(word);
			}
		// If no options, offer only to accept or type in manually
		} else {
			System.out.println("There are 0 suggestions in our dictionary for this word.");
			System.out.println("Press 'a' for accept as is or 't' for type in manually.");
			choice = in.nextLine();
			if (choice.equals("a")) {
				return word;
			} else if (choice.equals("t")) {
				System.out.println("Please type the word that will be used as the replacement in the output file.");
				response = in.nextLine();
			} else {
				System.out.println("That was not a valid selection.");
				return getUserPreference(word);
			}
		}
		
		return response;
	}
	
	/**
	 * Runs the spell checker and returns corrected file
	 * @param args
	 */
	public static void main(String[] args) {
		// Create a static scanner and file to spell check
		in = new Scanner(System.in);
		Scanner s = getFile(); 
		
		// Get the dictionary and recommender objects
		d = new Dictionary("engDictionary.txt");
		wc = new WordRecommender(filename);
		
		createLowerCaseFile(s);
		
		String[] filenameArray = filename.split("\\.");
		String newFilename = filenameArray[0] + "_chk." + filenameArray[1];
		File lowerCaseFile = new File("lowerCase.txt");
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileWriter(new File(newFilename)));
			Scanner sc = null;
			try {
				sc = new Scanner(lowerCaseFile);
				while (sc.hasNext()) {
					String word = sc.next();
					// If the word is not in the dictionary, offer the user suggestions
					// and ask how they would like to proceed; replace word as indicated
					if (!d.dictionary.contains(word)) {
						String replacement = getUserPreference(word);
						pw.print(replacement + " ");
					} else {
						pw.print(word + " ");
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} finally {
				sc.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			pw.flush();
			pw.close();
		}
		
		System.out.println("Your corrected file is named " + newFilename);
		in.close();
		
		// Uncomment to see contents of new file printed:
		/**
		Scanner sc = new Scanner(new File(newFilename));
		while (sc.hasNextLine()) {
			System.out.println(sc.nextLine());
		}
		*/
		
	}
}
