package br.com.verdinhas.gafanhoto.monitor;

import static br.com.verdinhas.gafanhoto.util.Utils.addSeparators;
import static br.com.verdinhas.gafanhoto.util.Utils.normalizeString;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;

import br.com.verdinhas.gafanhoto.util.Utils;
import lombok.Getter;

@Getter
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
		this.otherKeyWords = otherKeyWords.stream().map(Utils::normalizeString).collect(toList());
	}

	@Override
	public String toString() {
		List<String> keywords = new ArrayList<>();

		keywords.add(mainKeyWord);
		keywords.addAll(otherKeyWords);

		return addSeparators(keywords);
	}

}
