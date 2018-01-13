package br.com.verdinhas.gafanhoto.monitor;

import java.util.List;

import org.springframework.data.annotation.Id;

public class Monitor {

	@Id
	public String id;

	private String userId;
	private String mainKeyWord;
	private List<String> otherKeyWords;

	public Monitor(String userId, String mainKeyWord, List<String> otherKeyWords) {
		this.userId = userId;
		this.mainKeyWord = mainKeyWord;
		this.otherKeyWords = otherKeyWords;
	}

	public String getId() {
		return id;
	}

	public String getUserId() {
		return userId;
	}

	public String getMainKeyWord() {
		return mainKeyWord;
	}

	public List<String> getOtherKeyWords() {
		return otherKeyWords;
	}

}
