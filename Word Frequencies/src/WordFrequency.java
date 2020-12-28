/**
 * A node class that stores a word and its frequency.
 * @author jgt31
 */
public class WordFrequency {
	private String word;
	public int freq;
	private WordFrequency next;
	
	public WordFrequency(String word) {
		this.word = word;
		freq = 1;
	}
	
	/**
	 * Increments the frequency of this word
	 */
	public void increment() {
		freq++;
	}
	
	/**
	 * Returns the word that this WordFrequency object holds
	 */
	public String getWord() {
		return word;
	}
	
	/**
	 * Sets the next WordFrequency node in the list
	 */
	public void setNext(WordFrequency next) {
		this.next = next;
	}
	
	/**
	 * Returns the next WordFrequency node in the list
	 */
	public WordFrequency getNext() {
		return next;
	}
	
	/**
	 * Returns a String, associating the word with its frequency
	 */
	@Override
	public String toString() {
		return "(" + word + " --> " + freq + ")";
	}
}
