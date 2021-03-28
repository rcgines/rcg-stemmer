package com.rcg.stemmer.service;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.rcg.stemmer.configuration.StemmerConfig;
import com.rcg.stemmer.model.OriginalWord;
import com.rcg.stemmer.model.StemWord;
import com.rcg.stemmer.model.StopWord;
import com.rcg.stemmer.util.SortableValueMap;

public class WordStemmerServiceTest {

	private WordStemmerService service;
	private StopWord stopWord;
	private File stopWordFile;
	private StemmerConfig config;

	@BeforeEach
	public void init() throws URISyntaxException {
		config = initConfig();

		stopWord = new StopWord();
		URL url = WordStemmerServiceTest.class.getClassLoader().getResource("stopwords.txt");
		stopWordFile = Paths.get(url.toURI()).toFile();
	}

	@Test
	public void testProcessText1File() throws URISyntaxException {
		String fileName = "Text1.txt";

		SortableValueMap<String, StemWord> stemmedMap = processFile(getFile(fileName));
		printOut(stemmedMap, fileName);

		StemWord stemWord = getFirstStemWord(stemmedMap);
		Assertions.assertTrue(stemWord != null);
		Assertions.assertEquals("us", stemWord.getStemWord());
		Assertions.assertEquals(11, stemWord.getCount());
		Assertions.assertEquals("us", stemWord.getOriginalWords());

	}

	@Test
	public void testProcessText2File() throws URISyntaxException {
		String fileName = "Text2.txt";

		SortableValueMap<String, StemWord> stemmedMap = processFile(getFile(fileName));
		printOut(stemmedMap, fileName);

		StemWord stemWord = getFirstStemWord(stemmedMap);
		Assertions.assertTrue(stemWord != null);
		Assertions.assertEquals("said", stemWord.getStemWord());
		Assertions.assertEquals(462, stemWord.getCount());
		Assertions.assertEquals("said", stemWord.getOriginalWords());

	}

	@Test
	public void testProcessText3File() throws URISyntaxException {
		String fileName = "Text3.txt";

		SortableValueMap<String, StemWord> stemmedMap = processFile(getFile(fileName));
		printOut(stemmedMap, fileName);

		StemWord stemWord = getFirstStemWord(stemmedMap);
		Assertions.assertTrue(stemWord != null);
		Assertions.assertEquals("jump", stemWord.getStemWord());
		Assertions.assertEquals(3, stemWord.getCount());
		Assertions.assertEquals("jumps, jumping, jumped", stemWord.getOriginalWords());

	}

	private SortableValueMap<String, StemWord> processFile(File textFile) {
		OriginalWord stemRawWord = new OriginalWord();

		service = new WordStemmerService(stopWord, stemRawWord, config);

		SortableValueMap<String, StemWord> stemmedMap = service.processTextFile(stopWordFile, textFile);
		stemmedMap.sortByValue(true, 20);

		return stemmedMap;
	}

	private void verifyNotInStopWords(SortableValueMap<String, StemWord> stemmedMap) {
		Iterator<Entry<String, StemWord>> iterator = stemmedMap.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, StemWord> next = iterator.next();
			Assertions.assertFalse(stopWord.startsWith(next.getKey()));
		}
	}

	private void printOut(SortableValueMap<String, StemWord> stemmedMap, String fileName) {
		System.out.println("-----" + fileName + "-----");
		Iterator<Entry<String, StemWord>> iterator = stemmedMap.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, StemWord> next = iterator.next();
			System.out.println("Stem: " + next.getKey() + " = " + next.getValue().getCount() + "; Raw Words -> ("
					+ next.getValue().getOriginalWords() + ") ");
		}
	}

	private File getFile(String fileName) throws URISyntaxException {
		URL url = WordStemmerServiceTest.class.getClassLoader().getResource(fileName);
		return Paths.get(url.toURI()).toFile();
	}

	private StemWord getFirstStemWord(SortableValueMap<String, StemWord> stemmedMap) {
		StemWord stemWord = null;

		Optional<String> optional = stemmedMap.keySet().stream().findFirst();
		if (optional.isPresent()) {
			stemWord = stemmedMap.get(optional.get());
		}
		return stemWord;
	}

	private StemmerConfig initConfig() {
		config = new StemmerConfig();
		//		config.setPath("/src/resources/");
		config.setPath("");
		config.setStopWordFile("stopwords.txt");
		config.setText1FileName("Text1.txt");
		config.setText1FileDescription("Declaration of Independence");
		config.setText2FileName("Text2.txt");
		config.setText2FileDescription("Alice in Wonderland");
		return config;
	}

}
