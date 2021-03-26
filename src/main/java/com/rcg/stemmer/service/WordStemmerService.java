package com.rcg.stemmer.service;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rcg.stemmer.model.OriginalWord;
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

	@Autowired
	public WordStemmerService(StopWord stopWord, OriginalWord originalWord) {
		this.stopWord = stopWord;
		this.originalWord = originalWord;
	}

	public SortableValueMap<String, StemWord> processTextFile(File stopWordsFile, File textFile) {
		SortableValueMap<String, StemWord> stemmedMap = new SortableValueMap<>();
		try {
			// initialize the stop words
			stopWord.init(stopWordsFile);

			// extract the words from the file
			List<String> extractedWords = originalWord.extractWords(textFile);

			// stem the words
			stemmedMap = stemWords(extractedWords);

		} catch (Exception ex) {
			LOGGER.error("Error processing the text file", ex);
		}

		return stemmedMap;
	}

	private SortableValueMap<String, StemWord> stemWords(List<String> extractedWords) {
		SortableValueMap<String, StemWord> stemmedMap = new SortableValueMap<>();

		for (String word : extractedWords) {
			Stemmer stemmer = new Stemmer();
			stemmer.add(word.toCharArray(), word.length());

			if (!stopWord.contains(word.trim())) {
				stemmer.stem();
				String stemmed = stemmer.toString();

				StemWord stemWord = stemmedMap.get(stemmed);
				if (stemWord != null) {
					stemWord.incrementCount();
					stemWord.addOriginalWord(word.trim());
				} else {
					stemWord = new StemWord(stemmed, word.trim());
					stemmedMap.put(stemmed, stemWord);
				}
			}
		}
		return stemmedMap;
	}

}
