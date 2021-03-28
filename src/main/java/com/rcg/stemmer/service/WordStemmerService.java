package com.rcg.stemmer.service;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rcg.stemmer.configuration.StemmerConfig;
import com.rcg.stemmer.model.OriginalWord;
import com.rcg.stemmer.model.StemTexFile;
import com.rcg.stemmer.model.StemWord;
import com.rcg.stemmer.model.StopWord;
import com.rcg.stemmer.util.SortableValueMap;
import com.rcg.stemmer.util.Stemmer;

/**
 * Service class that processes the following steps:<br>
 * <br>
 * 1. Reads in a text file.<br>
 * 2. Removes stop words (as listed in stopwords.txt file) and all non-alphabetical texts.<br>
 * 3. Stems the words into their root form (e.g.: jumping, jumps, jumped -> jump).<br>
 * 4. Computes the frequency of each word and add it to sortable Map. <br>
 * <br>
 *
 * @author Raymond Gines
 */
@Service
public class WordStemmerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(WordStemmerService.class);

	private StopWord stopWord;
	private OriginalWord originalWord;
	private StemmerConfig config;

	@Autowired
	public WordStemmerService(StopWord stopWord, OriginalWord originalWord, StemmerConfig config) {
		this.stopWord = stopWord;
		this.originalWord = originalWord;
		this.config = config;
	}

	public SortableValueMap<String, StemWord> processTextFile(File stopWordsFile, File textFile) {
		SortableValueMap<String, StemWord> stemmedMap = new SortableValueMap<>();
		try {
			// initialize the stop words
			stopWord.init(stopWordsFile);

			// extract the original words from the file
			List<String> originalWords = originalWord.extractWords(textFile);

			// stem the words
			stemmedMap = stemWords(originalWords);

		} catch (Exception ex) {
			LOGGER.error("Error processing the text file", ex);
		}

		return stemmedMap;
	}

	private SortableValueMap<String, StemWord> stemWords(List<String> originalWords) {
		SortableValueMap<String, StemWord> stemmedMap = new SortableValueMap<>();

		for (String word : originalWords) {
			if (!stopWord.isExists(word.trim())) {
				String cleanseWord = originalWord.cleanseWord(word);

				Stemmer stemmer = new Stemmer();
				stemmer.add(cleanseWord.toCharArray(), cleanseWord.length());

				stemmer.stem();
				String stemmed = stemmer.toString();

				if (stemmed.trim().length() == 0) {
					continue;
				}

				StemWord stemWord = stemmedMap.get(stemmed);
				if (stemWord != null) {
					stemWord.incrementCount();
					stemWord.addOriginalWord(cleanseWord.trim());
				} else {
					stemWord = new StemWord(stemmed, cleanseWord.trim());
					stemmedMap.put(stemmed, stemWord);
				}
			}
		}
		return stemmedMap;
	}

	private File getFile(String fileName) throws URISyntaxException {
		return new File(config.getPath() + fileName);
	}

	public String getFileContent(String fileName) {

		StringBuffer buffer = new StringBuffer();
		Scanner scanner = null;
		try {
			File textFile = getFile(fileName);
			scanner = new Scanner(textFile);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				buffer.append(line);
				buffer.append("\r\n");
			}

		} catch (Exception ex) {
			LOGGER.error("Error extracting content = {}", fileName, ex);
		} finally {
			if (scanner != null) {
				scanner.close();
			}
		}

		return buffer.toString();
	}

	public List<StemTexFile> getStemTexFiles() {
		List<StemTexFile> textFiles = new ArrayList<>();

		textFiles.add(new StemTexFile("", "(Please select a text file)"));
		textFiles.add(new StemTexFile(config.getText1FileName(), config.getText1FileDescription()));
		textFiles.add(new StemTexFile(config.getText2FileName(), config.getText2FileDescription()));

		return textFiles;
	}

	public String getFileDescription(String fileName) {
		String description = "";
		List<StemTexFile> textFiles = getStemTexFiles();

		Optional<StemTexFile> optional = textFiles.stream().filter(p -> p.getFileName().equals(fileName)).findFirst();
		if (optional.isPresent()) {
			description = optional.get().getFileDescription();
		}
		return description;
	}

}
