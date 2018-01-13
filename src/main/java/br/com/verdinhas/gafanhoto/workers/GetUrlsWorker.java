package br.com.verdinhas.gafanhoto.workers;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.verdinhas.gafanhoto.Gafanhoto;
import br.com.verdinhas.gafanhoto.alertas.CriadorDeAlertas;
import br.com.verdinhas.gafanhoto.redis.RedisSetService;

@Component
public class GetUrlsWorker {

	private static final String SET_URLS = "urls";
	private static final String SET_ACTUAL_URLS = "actualUrls";

	@Autowired
	private Gafanhoto gafanhoto;

	@Autowired
	private RedisSetService redisSetService;

	@Autowired
	private CriadorDeAlertas criadorAlertas;

	@Scheduled(fixedDelay = 60000)
	public void updateDataBaseWithNewUrls() {
		List<String> actualUrls = gafanhoto.getActualUrls();

		Set<String> newUrls = discoveryNewUrls(actualUrls);

		redisSetService.saveElements(actualUrls, SET_URLS);

		for (String url : newUrls) {
			criadorAlertas.criarAlertas(url);
		}
	}

	private Set<String> discoveryNewUrls(List<String> actualUrls) {
		redisSetService.saveElements(actualUrls, SET_ACTUAL_URLS);

		Set<String> newUrls = redisSetService.getDifference(SET_ACTUAL_URLS, SET_URLS);

		redisSetService.delete(SET_ACTUAL_URLS);

		return newUrls;
	}

}
