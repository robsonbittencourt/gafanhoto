package br.com.verdinhas.gafanhoto.url;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NewUrlFilter {

	@Autowired
	private UrlRepository urlRepository;

	public Set<Url> filter(List<Url> urls) {
		Set<Url> newUrls = new HashSet<>();
		Set<Url> databaseUrls = new HashSet<Url>(urlRepository.findAll());
		
		urls.forEach(u -> {
			if (databaseUrls.add(u)) {
				urlRepository.save(u);
				newUrls.add(u);
			}
		});

		return newUrls;
	}

}
