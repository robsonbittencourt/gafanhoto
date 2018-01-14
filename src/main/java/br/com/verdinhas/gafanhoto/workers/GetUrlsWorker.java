package br.com.verdinhas.gafanhoto.workers;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.verdinhas.gafanhoto.Gafanhoto;
import br.com.verdinhas.gafanhoto.alertas.CriadorDeAlertas;
import br.com.verdinhas.gafanhoto.urls.Url;
import br.com.verdinhas.gafanhoto.urls.UrlsRepository;

@Component
public class GetUrlsWorker {

	@Autowired
	private Gafanhoto gafanhoto;

	@Autowired
	private UrlsRepository urlsRepository;

	@Autowired
	private CriadorDeAlertas criadorAlertas;

	@Scheduled(fixedDelay = 60000)
	public void updateDataBaseWithNewUrls() {
		List<String> actualUrls = gafanhoto.getActualUrls();

		Set<String> newUrls = discoveryNewUrls(actualUrls);

		for (String url : newUrls) {
			criadorAlertas.criarAlertas(url);
		}
	}

	private Set<String> discoveryNewUrls(List<String> actualUrls) {
		Set<String> newUrls = new HashSet<>();
		Set<Url> databaseUrls = new HashSet<Url>(urlsRepository.findAll());

		for (String url : actualUrls) {
			Url newUrl = new Url(url, new Date());

			if (databaseUrls.add(newUrl)) {
				System.out.println("Salvando url " + url);
				urlsRepository.save(newUrl);
				newUrls.add(url);
			}
		}

		return newUrls;
	}

}
