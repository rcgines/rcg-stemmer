package com.rcg.stemmer.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class OriginalWord {

	private static final Logger LOGGER = LoggerFactory.getLogger(OriginalWord.class);

	public List<String> extractWords(File textFile) throws Exception {
		if (textFile == null) {
			throw new Exception("Text file is null");
		}

		List<String> extractedWords = new ArrayList<>();
		try (Scanner scanner = new Scanner(textFile)) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (!line.isEmpty()) {
					String[] words = line.split(" ");
					addWords(extractedWords, words);
				}
			}

		} catch (Exception ex) {
			LOGGER.error("Error extracting original words from the text file = {}", textFile.getName(), ex);
			throw ex;
		}

		return extractedWords;
	}

	private void addWords(List<String> extractedWords, String[] words) {
		for (String word : words) {
			if (word != null && !word.trim().isEmpty()) {
				String cleanseWord = word.replaceAll("[0-9*+-/.,;:!?`()]", "");
				if (!cleanseWord.trim().isEmpty()) {
					extractedWords.add(cleanseWord);
				}
			}
		}
	}

	public String cleanseWord(String word) {
		boolean shouldCleanse = true;
		String cleanseWord = word;

		shouldCleanse = shouldCleanseWord(word);

		if (shouldCleanse) {
			cleanseWord = word.replaceAll("[^a-zA-Z]", "");
		}
		return cleanseWord.toLowerCase();
	}

	private boolean shouldCleanseWord(String word) {
		boolean firstAlpha = false;
		boolean lastAlpha = false;
		boolean cleanse = true;

		if (word.length() > 0) {
			firstAlpha = Character.isLetter(word.charAt(0));
			lastAlpha = Character.isLetter(word.charAt(word.length() - 1));
		}

		if (firstAlpha && lastAlpha) {
			if (!Pattern.matches("[a-zA-Z]+", removeLastCharOptional(word))) {
				cleanse = false;
			}
		}

		return cleanse;
	}

	private String removeLastCharOptional(String s) {
		return Optional.ofNullable(s).filter(str -> str.length() != 0).map(str -> str.substring(0, str.length() - 1)).orElse(s);
	}

}
