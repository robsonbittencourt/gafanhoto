package br.com.verdinhas.gafanhoto.alertas;

import org.springframework.data.annotation.Id;

public class Alerta {

	@Id
	public String id;

	private int userId;
	private String url;

	public Alerta(int userId, String url) {
		this.userId = userId;
		this.url = url;
	}

	public String getId() {
		return id;
	}

	public int getUserId() {
		return userId;
	}

	public String getUrl() {
		return url;
	}

}
