import java.io.*;
import java.util.*;

public class Dictionary {
	HashSet<String> dictionary;
	HashMap<Integer, ArrayList<String>> wordsByLength;
	
	public Dictionary(String filename) {
		dictionary = new HashSet<String>();
		File dict = new File(filename);
		
		// Read the dictionary into a set
		try {
			Scanner in = new Scanner(dict);
			while (in.hasNextLine()) {
				String word = in.nextLine();
				dictionary.add(word);
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		// Create a hashmap of dictionary words by length
		HashMap<Integer, ArrayList<String>> wordsByLength = new HashMap<Integer, ArrayList<String>>();
		for (String word : dictionary) {
			int len = word.length();
			ArrayList<String> words = wordsByLength.get(len);
			
			if (words == null) {
				words = new ArrayList<String>();
				words.add(word);
				wordsByLength.put(len, words);
			} else {
				words.add(word);
			}
		}
		this.wordsByLength = wordsByLength;
	}
}
