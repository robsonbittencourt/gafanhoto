package br.com.verdinhas.gafanhoto;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.verdinhas.gafanhoto.redis.RedisSetService;

@Component
@Scope("prototype")
public class UrlsOrganizer {

	@Autowired
	private Gafanhoto gafanhoto;

	@Autowired
	private RedisSetService redisSetService;

	public Set<String> updateDataBaseWithNewUrls() {
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
