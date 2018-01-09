package br.com.verdinhas.gafanhoto;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Gafanhoto {

	@Value("${forumUrl}")
	private String forumUrl;

	@Value("${elements.title}")
	private String titleClass;

	public List<String> getActualUrls() {
		try {
			Document document = Jsoup.connect(forumUrl)
					.userAgent(
							"Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
					.get();

			Elements links = document.select("a." + titleClass);

			return links.stream().map(l -> l.attr("abs:href")).collect(toList());
		} catch (Exception e) {
			// TODO log
			return new ArrayList<>();
		}

	}

}
