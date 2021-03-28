package com.rcg.stemmer.model;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OriginalWordTest {

	private OriginalWord originalWord;

	@BeforeEach
	public void init() throws URISyntaxException {
		originalWord = new OriginalWord();
	}

	@Test
	public void testExtractWordsText3() throws Exception {
		File textFile = getFile("Text3.txt");

		List<String> cleanseWords = originalWord.extractWords(textFile);
		//		cleanseWords.stream().forEach(p -> System.out.println(p));

		List<String> rawWords = new ArrayList<>();
		rawWords.add("Course;");
		rawWords.add("events,");
		rawWords.add("events?");
		rawWords.add("truths/");
		rawWords.add("above!");

		for (String word : rawWords) {
			Assertions.assertFalse(cleanseWords.contains(word));
		}
	}

	//	@Test
	public void testExtractWordsText2() throws Exception {
		File textFile = getFile("Text2.txt");
		String filterWord = "said";

		List<String> originalWords = getOriginalWords(textFile, filterWord);
		System.out.println("--- Original ---");
		originalWords.stream().forEach(p -> System.out.println(p));

		System.out.println("--- Cleansed ---");
		List<String> cleanseWords = originalWord.extractWords(textFile);
		int ctr = 0;
		for (String word : cleanseWords) {
			if (word.indexOf(filterWord) >= 0) {
				ctr++;
				//				System.out.println(word);
			}
		}
		Assertions.assertEquals(462, ctr);
	}

	private File getFile(String fileName) throws URISyntaxException {
		URL url = OriginalWordTest.class.getClassLoader().getResource(fileName);
		return Paths.get(url.toURI()).toFile();
	}

	private List<String> getOriginalWords(File textFile, String filterWord) throws Exception {
		List<String> originalWords = new ArrayList<>();
		try (Scanner scanner = new Scanner(textFile)) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (!line.isEmpty()) {
					String[] words = line.split(" ");
					for (String word : words) {
						if (word.toLowerCase().contains(filterWord.toLowerCase())) {
							originalWords.add(word);
						}
					}
				}
			}

		} catch (Exception ex) {
			throw ex;
		}
		return originalWords;
	}

}
