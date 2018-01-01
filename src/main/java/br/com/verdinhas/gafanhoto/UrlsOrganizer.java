package br.com.verdinhas.gafanhoto;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.verdinhas.gafanhoto.redis.RedisSetService;

@Component
public class UrlsOrganizer {

	@Autowired
	private Gafanhoto gafanhoto;

	@Autowired
	private RedisSetService redisSetService;

	@Scheduled(fixedDelay = 60000)
	public Set<String> updateDataBaseWithNewUrls() {
		System.out.println("Running");

		List<String> actualUrls = gafanhoto.getActualUrls();

		Set<String> newUrls = discoveryNewUrls(actualUrls);

		redisSetService.saveElements(actualUrls, "urls");

		return newUrls;
	}

	private Set<String> discoveryNewUrls(List<String> actualUrls) {
		redisSetService.saveElements(actualUrls, "actualUrls");

		Set<String> newUrls = redisSetService.getDifference("actualUrls", "urls");

		redisSetService.delete("actualUrls");

		return newUrls;
	}

}
