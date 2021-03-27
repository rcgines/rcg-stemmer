package com.rcg.stemmer.model;

public class StemTexFile {

	private String fileName;
	private String fileDescription;

	public StemTexFile(String fileName, String fileDescription) {
		this.fileName = fileName;
		this.fileDescription = fileDescription;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileDescription() {
		return fileDescription;
	}

	public void setFileDescription(String fileDescription) {
		this.fileDescription = fileDescription;
	}

}
