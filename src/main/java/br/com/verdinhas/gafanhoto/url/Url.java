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

	private String urlAddress;

	private List<String> urlWords;

	private String identifier;

	private Date date;

	public Url(String urlAddress, List<String> urlWords, String identifier, Date date) {
		this.urlAddress = urlAddress;
		this.urlWords = urlWords;
		this.identifier = identifier;
		this.date = date;
	}

}
