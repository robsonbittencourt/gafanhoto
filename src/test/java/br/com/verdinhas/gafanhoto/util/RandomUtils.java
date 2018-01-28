package br.com.verdinhas.gafanhoto.util;

import java.util.Random;

public class RandomUtils {
	
	public static String getRandomString() {
		return getRandomString(10);
	}

	
	public static String getRandomString(int length) {
		Random random = new Random();
		
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < length; i++) {
			switch (random.nextInt(3)) {
				case 0:
					builder.append(new Character((char) (48 + random.nextInt((57 + 1 - 48)))));
					break;
				case 1:
					builder.append(new Character((char) (97 + random.nextInt((122 + 1 - 97)))));
					break;
				case 2:
					builder.append(new Character((char) (65 + random.nextInt((90 + 1 - 65)))));
					break;
			}
		}
		return builder.toString();
	}
	
}
