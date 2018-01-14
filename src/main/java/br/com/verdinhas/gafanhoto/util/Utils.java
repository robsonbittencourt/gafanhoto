package br.com.verdinhas.gafanhoto.util;

import java.util.List;

public class Utils {

	public static String addSeparators(List<String> words) {
		StringBuilder sb = new StringBuilder();

		for (String arg : words) {
			sb.append(arg).append(" - ");
		}

		String withSeparators = sb.toString();
		String withoutLastSeparator = withSeparators.substring(0, withSeparators.length() - 2);

		return withoutLastSeparator;
	}

}
