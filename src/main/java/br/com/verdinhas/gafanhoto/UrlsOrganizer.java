package br.com.verdinhas.gafanhoto;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.verdinhas.gafanhoto.redis.RedisSetService;

@Component
public class UrlsOrganizer {

	private static final String SET_URLS = "urls";
	private static final String SET_ACTUAL_URLS = "actualUrls";

	@Autowired
	private Gafanhoto gafanhoto;

	@Autowired
	private RedisSetService redisSetService;

	@Scheduled(fixedDelay = 60000)
	public Set<String> updateDataBaseWithNewUrls() {
		List<String> actualUrls = gafanhoto.getActualUrls();

		Set<String> newUrls = discoveryNewUrls(actualUrls);

		redisSetService.saveElements(actualUrls, SET_URLS);

		return newUrls;
	}

	private Set<String> discoveryNewUrls(List<String> actualUrls) {
		redisSetService.saveElements(actualUrls, SET_ACTUAL_URLS);

		Set<String> newUrls = redisSetService.getDifference(SET_ACTUAL_URLS, SET_URLS);

		redisSetService.delete(SET_ACTUAL_URLS);

		return newUrls;
	}

}
