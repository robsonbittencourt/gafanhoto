package br.com.verdinhas.gafanhoto.redis;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisSetService {

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	public void saveElements(List<String> elements, String setName) {
		elements.stream().forEach(e -> redisTemplate.opsForSet().add(setName, e));
	}

	public Set<String> getDifference(String setName1, String setName2) {
		return redisTemplate.opsForSet().difference(setName1, setName2);
	}

	public void delete(String setName) {
		redisTemplate.delete(setName);
	}
}
