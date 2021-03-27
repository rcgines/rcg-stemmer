package com.rcg.stemmer.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:stemmer.properties")
public class StemmerConfig {

	@Value(value = "${stemmer.path}")
	private String path;

	@Value(value = "${stemmer.stopword}")
	private String stopWordFile;

	@Value(value = "${stemmer.text1.fileName}")
	private String text1FileName;

	@Value(value = "${stemmer.text1.fileDescription}")
	private String text1FileDescription;

	@Value(value = "${stemmer.text2.fileName}")
	private String text2FileName;

	@Value(value = "${stemmer.text2.fileDescription}")
	private String text2FileDescription;

	public String getPath() {
		return path;
	}

	public String getStopWordFile() {
		return stopWordFile;
	}

	public String getText1FileName() {
		return text1FileName;
	}

	public String getText1FileDescription() {
		return text1FileDescription;
	}

	public String getText2FileName() {
		return text2FileName;
	}

	public String getText2FileDescription() {
		return text2FileDescription;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setStopWordFile(String stopWordFile) {
		this.stopWordFile = stopWordFile;
	}

	public void setText1FileName(String text1FileName) {
		this.text1FileName = text1FileName;
	}

	public void setText1FileDescription(String text1FileDescription) {
		this.text1FileDescription = text1FileDescription;
	}

	public void setText2FileName(String text2FileName) {
		this.text2FileName = text2FileName;
	}

	public void setText2FileDescription(String text2FileDescription) {
		this.text2FileDescription = text2FileDescription;
	}

}
