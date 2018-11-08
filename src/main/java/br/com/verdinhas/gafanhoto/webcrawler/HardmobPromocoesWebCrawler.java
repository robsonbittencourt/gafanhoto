package br.com.verdinhas.gafanhoto.webcrawler;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class HardmobPromocoesWebCrawler implements UrlCrawler {

	@Value("${userAgent}")
	private String userAgent;

	@Value("${sources.hardmob.url}")
	private String forumUrl;

	@Value("${sources.hardmob.elements.title}")
	private String titleClass;

	@Override
	public List<String> retrieveUrlsFromSource() {
		try {
			Document document = Jsoup.connect(forumUrl).userAgent(userAgent).get();

			Elements links = document.select("a." + titleClass);

			return links.stream()
					.map(l -> l.attr("abs:href"))
					.collect(toList());
		} catch (Exception e) {
			log.error("Ocorreu um erro ao buscar as urls", e);
			return new ArrayList<>();
		}
	}

	@Override
	public List<String> decompose(String url) {
		String withoutPrefixAndSufix = url.substring(url.indexOf('-') + 1, url.indexOf("?s="));

		List<String> wordsWithSeparator = asList(withoutPrefixAndSufix.split("-"));

		return wordsWithSeparator.stream()
				.collect(toList());
	}

	@Override
	public String getIdentifier(String url) {
		return url.substring(forumUrl.replace("forums", "threads").lastIndexOf('/') + 1, url.indexOf('-'));
	}

}
