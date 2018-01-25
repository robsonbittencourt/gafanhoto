package br.com.verdinhas.gafanhoto.url;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(of = {"identifier"})
public class Url {

	@Id
	public String id;

	private String url;

	private List<String> urlWords;

	private String identifier;

	private Date date;

	public Url(String url, List<String> urlWords, String identifier, Date date) {
		this.url = url;
		this.urlWords = urlWords;
		this.identifier = identifier;
		this.date = date;
	}

}
