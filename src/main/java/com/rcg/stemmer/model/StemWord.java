package com.rcg.stemmer.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Bean class that contains the stem word, count and original words. <br>
 * <br>
 *
 * @author Raymond Gines
 */
public class StemWord implements Comparable<StemWord> {

	private final String stemWord;
	private Integer count;
	private List<String> originalWordList;
	private String originalWords = "";
	private Integer rowNumber;

	public StemWord(String stemWord, String originalWord) {
		this.stemWord = stemWord;
		count = 1;
		addOriginalWord(originalWord);
	}

	public String getStemWord() {
		return stemWord;
	}

	public Integer getCount() {
		return count;
	}

	public List<String> getOriginalWordList() {
		if (originalWordList == null) {
			originalWordList = new ArrayList<>();
		}
		return originalWordList;
	}

	public void addOriginalWord(String originalWord) {
		if (!getOriginalWordList().contains(originalWord)) {
			getOriginalWordList().add(originalWord);
		}
	}

	public void incrementCount() {
		count++;
	}

	public String getOriginalWords() {
		if (!getOriginalWordList().isEmpty()) {
			originalWords = String.join(",", getOriginalWordList());
		}
		return originalWords;
	}

	@Override
	public int compareTo(StemWord anotherWord) {
		return getCount().compareTo(anotherWord.getCount());
	}

	public Integer getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(Integer rowNumber) {
		this.rowNumber = rowNumber;
	}

}
