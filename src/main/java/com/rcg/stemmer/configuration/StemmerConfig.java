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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getStopWordFile() {
		return stopWordFile;
	}

	public void setStopWordFile(String stopWordFile) {
		this.stopWordFile = stopWordFile;
	}

}
