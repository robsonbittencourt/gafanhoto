package br.com.verdinhas.gafanhoto.alertas;

import org.springframework.data.annotation.Id;

public class Alerta {

	@Id
	public String id;

	private String userId;
	private String url;

	public Alerta(String userId, String url) {
		this.userId = userId;
		this.url = url;
	}

	public String getId() {
		return id;
	}

	public String getUserId() {
		return userId;
	}

	public String getUrl() {
		return url;
	}

	@Override
	public String toString() {
		return "Alerta [id=" + id + ", userId=" + userId + ", url=" + url + "]";
	}

}
