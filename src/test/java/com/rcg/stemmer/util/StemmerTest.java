package com.rcg.stemmer.util;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StemmerTest {

	@Test
	public void testStemWord() {

		List<String> originalWords = new ArrayList<>();
		originalWords.add("jumping");
		originalWords.add("jumped");
		originalWords.add("jumps");

		for (String word : originalWords) {
			String stemmed = stemWord(word);
			Assertions.assertEquals("jump", stemmed);
		}

	}

	private String stemWord(String word) {
		Stemmer stemmer = new Stemmer();
		stemmer.add(word.toCharArray(), word.length());

		stemmer.stem();
		return stemmer.toString();
	}

}
