package com.rcg.stemmer.service;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map.Entry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.rcg.stemmer.model.OriginalWord;
import com.rcg.stemmer.model.StemWord;
import com.rcg.stemmer.model.StopWord;
import com.rcg.stemmer.util.SortableValueMap;

public class WordStemmerServiceTest {

	private WordStemmerService service;
	private StopWord stopWord;
	private File stopWordFile;

	@BeforeEach
	public void init() throws URISyntaxException {
		stopWord = new StopWord();
		URL url = WordStemmerServiceTest.class.getClassLoader().getResource("stopwords.txt");
		stopWordFile = Paths.get(url.toURI()).toFile();
	}

	@Test
	public void testProcessText1File() throws URISyntaxException {
		String fileName = "Text1.txt";

		SortableValueMap<String, StemWord> stemmedMap = processFile(getFile(fileName));

		printOut(stemmedMap, fileName);
	}

	@Test
	public void testProcessText2File() throws URISyntaxException {
		String fileName = "Text2.txt";

		SortableValueMap<String, StemWord> stemmedMap = processFile(getFile(fileName));

		printOut(stemmedMap, fileName);
	}

	@Test
	public void testProcessText3File() throws URISyntaxException {
		String fileName = "Text3.txt";

		SortableValueMap<String, StemWord> stemmedMap = processFile(getFile(fileName));

		printOut(stemmedMap, fileName);
	}

	private SortableValueMap<String, StemWord> processFile(File textFile) {
		OriginalWord stemRawWord = new OriginalWord();

		service = new WordStemmerService(stopWord, stemRawWord);

		SortableValueMap<String, StemWord> stemmedMap = service.processTextFile(stopWordFile, textFile);
		stemmedMap.sortByValue(true, 20);

		return stemmedMap;
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

}
