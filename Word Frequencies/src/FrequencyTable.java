import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
/**
 * A hash table storing all of the word frequencies, using chaining to handle collisions
 * Because this particular task doesn't require entry removal, deletion and shrinking are unimplemented
 * @author jgt31
 */
public class FrequencyTable {
	private static final int DEFAULT_SIZE = 64;
	private static final float LOAD_FACTOR = 0.75f;
	private static int size = 0;
	
	private static WordFrequency[] table = new WordFrequency[DEFAULT_SIZE];
	
	/**
	 * Puts a new word to this table, resizing the table as necessary
	 */
	private static void put(String word) {
		int index = Math.abs(word.hashCode() % table.length);
		
		// putting the word in the table
		if(table[index] == null) {
			table[index] = new WordFrequency(word);
			size++;
		} else {
			WordFrequency temp = table[index];
			while(temp != null) {
				// if the word is already in the table, we'll just increment its value
				if(temp.getWord().equals(word)) {
					temp.increment();
					return;
				}
				// otherwise we'll move on until we reach the end of the chained list
				temp = temp.getNext();
			}
			// if we can't find it, we'll add the element to the front of the chained list
			temp = new WordFrequency(word);
			temp.setNext(table[index]);
			table[index] = temp;
			size++;
		}

		// expanding the table if the size exceeds the table's load factor
		if(size >= table.length * LOAD_FACTOR) {
			expand();
		}
	}
	
	/**
	 * If the table's load factor is met or exceeded, this method is invoked to double the size of the array and re-hash its elements
	 */
	private static void expand() {
		// create new array with double the original's capacity
		WordFrequency[] temp = new WordFrequency[table.length * 2];
		
		// iterate through the old array, rehashing every node into the new array
		for(int i = 0; i < table.length; i++) {
			WordFrequency traverser = table[i];
			while(traverser != null) {
				WordFrequency next = traverser.getNext();
				int index = Math.abs(traverser.getWord().hashCode() % temp.length); // position of node in new array
				traverser.setNext(temp[index]);
				temp[index] = traverser; // adding node at the front of that list
				traverser = next;
			}
		}
		table = temp;
	}
	
	/**
	 * Takes in an input file, adding each individual word to the table
	 */
	private static void read(String inputFile) throws IOException {
		Scanner sc = new Scanner(new File(inputFile));
		// looks for any number or other non-alphabetical character to delimit the input file
		sc.useDelimiter("\\W|\\d");
		
		// reading in the file, word by word
		while(sc.hasNext()) {
			String s = sc.next().toLowerCase();
			if(!s.isEmpty())
				put(s);
		}
		sc.close();
	}
	
	/**
	 * Prints out all of the word-frequency pairs and writes the results to a specified file
	 * The pairs are not printed in any particular order
	 */
	private static void write(String outputFile) throws IOException {
		FileWriter fw = new FileWriter(outputFile);
		
		// iterate through the table, printing out each pair and writing them to the output file
		for(int i = 0; i < table.length; i++) {
			WordFrequency traverser = table[i];
			while(traverser != null) {
				System.out.println(traverser);
				fw.write(traverser.toString() + "\n");
				traverser = traverser.getNext();
			}
		}
		fw.close();
		System.out.println("Final hash table load: " + (float)size / table.length);
	}
	
	/**
	 * Main method of the Word Frequencies project - reads in a file, puts elements in the hash table, and outputs the results in a separate file
	 */
	public static String generateWordFreq(String inputFile, String outputFile) {
		try {
			long startTime = System.nanoTime();
			
			// reads the input file and puts the word-frequency pairs into the hash table
			read(inputFile);
			// writes the contents of the hash table to the output file while printing the pairs
			write(outputFile);
			
			long timeElapsed = (System.nanoTime() - startTime) / 1000000;
			return "Success! (Time elapsed: " + timeElapsed + "ms)";
		} catch(IOException e) {
			return e.getMessage();
		}
	}
	
	public static void main(String[] args) {
		System.out.println(generateWordFreq("inputFile", "outputFile"));
	}
}