package br.com.verdinhas.gafanhoto.monitor;

import static br.com.verdinhas.gafanhoto.util.Utils.addSeparators;
import static br.com.verdinhas.gafanhoto.util.Utils.normalizeString;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;

public class Monitor {

	@Id
	public String id;

	private int userId;
	private long chatId;
	private String mainKeyWord;
	private List<String> otherKeyWords;

	public Monitor(int userId, long chatId, String mainKeyWord, List<String> otherKeyWords) {
		this.userId = userId;
		this.chatId = chatId;
		this.mainKeyWord = normalizeString(mainKeyWord);
		this.otherKeyWords = otherKeyWords.stream().map(k -> normalizeString(k)).collect(toList());
	}

	public String getId() {
		return id;
	}

	public int getUserId() {
		return userId;
	}

	public long getChatId() {
		return chatId;
	}

	public String getMainKeyWord() {
		return mainKeyWord;
	}

	public List<String> getOtherKeyWords() {
		return otherKeyWords;
	}

	@Override
	public String toString() {
		List<String> keywords = new ArrayList<>();

		keywords.add(mainKeyWord);
		keywords.addAll(otherKeyWords);

		return addSeparators(keywords);
	}

}
