package br.com.verdinhas.gafanhoto.fixture;

import static br.com.verdinhas.gafanhoto.util.RandomUtils.getRandomString;
import static java.util.Arrays.asList;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.verdinhas.gafanhoto.url.Url;

public class UrlFixture {

	private String url;
	private List<String> urlWords;
	private String identifier;
	private LocalDateTime date;

	public static UrlFixture get() {
		return new UrlFixture();
	}

	public UrlFixture complete() {
		String identifier = getRandomString();
		
		this.url = "www.test.com/" + identifier + "/car";
		this.urlWords = new ArrayList<>(asList("car"));
		this.identifier = identifier;
		this.date = LocalDateTime.now();
		
		return this;
	}

	public UrlFixture url(String url) {
		this.url = url;
		return this;
	}

	public UrlFixture urlWords(List<String> urlWords) {
		this.urlWords = urlWords;
		return this;
	}

	public UrlFixture identifier(String identifier) {
		this.identifier = identifier;
		return this;
	}

	public UrlFixture date(LocalDateTime date) {
		this.date = date;
		return this;
	}

	public Url build() {
		return new Url(url, urlWords, identifier, date);
	}

	public List<Url> buildList(int amount) {
		List<Url> urls = new ArrayList<>();
		
		for (int i = 0; i < amount; i++) {
			urls.add(this.build());
		}
		
		return urls;
	}
}
