package br.com.verdinhas.gafanhoto.monitor;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class UrlDecomposer {

	public List<String> decompose(String url) {
		String withoutPrefixAndSufix = url.substring(url.indexOf("-") + 1, url.indexOf(".html"));

		List<String> wordsWithSeparator = asList(withoutPrefixAndSufix.split("-"));

		List<String> words = wordsWithSeparator.stream().filter(w -> w.length() > 1).collect(toList());

		return words;
	}

}
