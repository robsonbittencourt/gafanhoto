package br.com.verdinhas.gafanhoto.alert;

import org.springframework.data.annotation.Id;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
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

}
