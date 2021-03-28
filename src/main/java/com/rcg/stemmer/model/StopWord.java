package com.rcg.stemmer.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Bean class that reads in the stopwords text file and add it to a List. <br>
 * <br>
 *
 * @author Raymond Gines
 */
@Component
public class StopWord {

	private static final Logger LOGGER = LoggerFactory.getLogger(StopWord.class);

	private List<String> stopWords;

	public void init(File stopWordsFile) throws Exception {
		if (stopWordsFile == null) {
			throw new Exception("StopWord file is null");
		}

		stopWords = new ArrayList<String>();

		Scanner scanner = null;
		try {
			scanner = new Scanner(stopWordsFile);
			while (scanner.hasNextLine()) {
				stopWords.add(scanner.nextLine());
			}

		} catch (Exception ex) {
			LOGGER.error("Error initializing StopWords", ex);
			throw ex;

		} finally {
			if (scanner != null) {
				scanner.close();
			}
		}
	}

	public boolean isExists(String word) {
		Optional<String> optional = stopWords.stream().filter(p -> p.equalsIgnoreCase(word)).findAny();
		return optional.isPresent();
	}

	public boolean startsWith(String word) {
		Optional<String> optional = stopWords.stream().filter(p -> p.startsWith(word)).findAny();
		return optional.isPresent();
	}

	public List<String> getStopWords() {
		return stopWords;
	}

}
