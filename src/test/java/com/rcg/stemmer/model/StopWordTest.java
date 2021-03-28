package com.rcg.stemmer.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StopWordTest {

	private StopWord stopWord;
	private File stopWordFile;

	@BeforeEach
	public void init() throws URISyntaxException {
		stopWord = new StopWord();
		URL url = StopWordTest.class.getClassLoader().getResource("stopwords.txt");
		stopWordFile = Paths.get(url.toURI()).toFile();
	}

	@Test
	public void testInitStopWords() throws Exception {
		stopWord.init(stopWordFile);
		assertEquals(174, stopWord.getStopWords().size());
	}

	@Test
	public void testNullStopWordsFile() throws Exception {
		Exception exception = Assertions.assertThrows(Exception.class, () -> {
			stopWord.init(null);
		});

		assertTrue("StopWord file is null".equals(exception.getMessage()));
	}

	@Test
	public void testContainsSuccess() throws Exception {
		String phrase = "against";

		stopWord.init(stopWordFile);

		assertTrue(stopWord.isExists(phrase));
	}

	@Test
	public void testContainsFailure() throws Exception {
		String phrase = "odds";

		stopWord.init(stopWordFile);

		assertFalse(stopWord.isExists(phrase));
	}

}
