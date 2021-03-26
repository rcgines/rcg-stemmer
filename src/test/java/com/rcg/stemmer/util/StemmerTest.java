package com.rcg.stemmer.util;

import org.junit.jupiter.api.Test;

public class StemmerTest {

	@Test
	public void testStemWord() {
		String word = "course";

		Stemmer stemmer = new Stemmer();
		stemmer.add(word.toCharArray(), word.length());

		stemmer.stem();
		String stemmed = stemmer.toString();

		System.out.println("Stemmed: " + stemmed);
	}

}
