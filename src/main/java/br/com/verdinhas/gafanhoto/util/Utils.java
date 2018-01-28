package br.com.verdinhas.gafanhoto.util;

import static org.apache.commons.lang3.StringUtils.stripAccents;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Utils {
	
	private Utils() {
		// Just to hide creation
	}

	public static String normalizeString(String string) {
		return stripAccents(string.toLowerCase());
	}

	public static String addSeparators(List<String> words) {
		StringBuilder sb = new StringBuilder();
		
		words.forEach(w -> {
			sb.append(w);
			sb.append(" - ");
		});

		String withSeparators = sb.toString();

		return withSeparators.substring(0, withSeparators.length() - 3);
	}

	public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
		Set<Object> seen = ConcurrentHashMap.newKeySet();
		return t -> seen.add(keyExtractor.apply(t));
	}

	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			log.error("Error on thread sleep", e);
			Thread.currentThread().interrupt();
		}
	}

}
