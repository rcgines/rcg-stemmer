package com.rcg.stemmer.controller;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.rcg.stemmer.configuration.StemmerConfig;
import com.rcg.stemmer.model.StemWord;
import com.rcg.stemmer.model.TextFileSearch;
import com.rcg.stemmer.service.WordStemmerService;
import com.rcg.stemmer.util.SortableValueMap;

@Controller
public class IndexController {

	private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

	@Autowired
	private WordStemmerService wordStemmerService;

	@Autowired
	private StemmerConfig stemmerConfig;

	@RequestMapping("/stemmer")
	public String index(Model model) {
		model.addAttribute("words", new ArrayList<StemWord>());
		model.addAttribute("search", new TextFileSearch());
		return "index";
	}

	@RequestMapping(value = "/stemmer/execute", method = RequestMethod.POST)
	public String execute(@ModelAttribute TextFileSearch textFileSearch, Model model) {
		model.addAttribute("words", executeStemmer(textFileSearch));
		model.addAttribute("search", textFileSearch);
		return "index";
	}

	private List<StemWord> executeStemmer(TextFileSearch textFileSearch) {
		List<StemWord> stemmedWords = new ArrayList<>();
		try {
			if (textFileSearch.getFileName() != null) {
				File stopWordFile = getFile(stemmerConfig.getStopWordFile());

				File textFile = getFile(textFileSearch.getFileName());

				SortableValueMap<String, StemWord> stemmedMap = wordStemmerService.processTextFile(stopWordFile, textFile);
				stemmedMap.sortByValue(true, 20);

				int rowNumber = 0;
				Iterator<Entry<String, StemWord>> iterator = stemmedMap.entrySet().iterator();
				while (iterator.hasNext()) {
					Entry<String, StemWord> next = iterator.next();
					StemWord stemWord = next.getValue();
					rowNumber++;
					stemWord.setRowNumber(rowNumber);
					stemmedWords.add(stemWord);
				}

			}

		} catch (Exception ex) {
			LOGGER.error("Error processing stemming algorithm", ex);
		}
		return stemmedWords;
	}

	private File getFile(String fileName) throws URISyntaxException {
		return new File(stemmerConfig.getPath() + fileName);
	}

}
