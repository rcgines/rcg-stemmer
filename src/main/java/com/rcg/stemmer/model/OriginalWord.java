package com.rcg.stemmer.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
		Scanner scanner = null;
		try {
			scanner = new Scanner(textFile);
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

		} finally {
			if (scanner != null) {
				scanner.close();
			}
		}

		return extractedWords;
	}

	private void addWords(List<String> extractedWords, String[] words) {
		for (String word : words) {
			if (word != null && !word.trim().isEmpty()) {
				String cleanseWord = word.replaceAll("[^a-zA-Z]", "");
				if (!cleanseWord.trim().isEmpty()) {
					extractedWords.add(cleanseWord.toLowerCase());
				}
			}
		}
	}

}
