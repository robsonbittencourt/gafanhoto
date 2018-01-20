package br.com.verdinhas.gafanhoto.alert;

import org.springframework.data.annotation.Id;

public class Alert {

	@Id
	public String id;

	private int userId;
	private long chatId;
	private String url;

	public Alert(int userId, long chatId, String url) {
		this.userId = userId;
		this.chatId = chatId;
		this.url = url;
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

	public String getUrl() {
		return url;
	}

}