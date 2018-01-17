package br.com.verdinhas.gafanhoto.util;

import static org.apache.commons.lang3.StringUtils.stripAccents;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

public class Utils {

	public static String normalizeString(String string) {
		return stripAccents(string.toLowerCase());
	}

	public static String addSeparators(List<String> words) {
		StringBuilder sb = new StringBuilder();

		for (String arg : words) {
			sb.append(arg)
					.append(" - ");
		}

		String withSeparators = sb.toString();
		String withoutLastSeparator = withSeparators.substring(0, withSeparators.length() - 2);

		return withoutLastSeparator;
	}

	public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
		Set<Object> seen = ConcurrentHashMap.newKeySet();
		return t -> seen.add(keyExtractor.apply(t));
	}

}
