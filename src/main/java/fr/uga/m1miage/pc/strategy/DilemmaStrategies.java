package fr.uga.m1miage.pc.strategy;

import java.security.SecureRandom;
import java.util.List;

class DilemmaStrategies {
	
	private static final SecureRandom random = new SecureRandom();

	private DilemmaStrategies() {
	    throw new IllegalStateException("Utility class");
	  }

	// #1
	public static String donnantDonnant(List<String> previousChoices) {
		if (previousChoices != null && !previousChoices.isEmpty()) {
			return previousChoices.get(previousChoices.size() - 1);
		}
		return null;
	}
	
	// #2
	public static String donnantDonnantAleatoire(List<String> previousChoices) {
		Integer integer = random.nextInt(0, 10);
		String[] choices = {"c", "t"};
		if (previousChoices != null && !previousChoices.isEmpty()) {
			if (integer < 7)
				return previousChoices.get(previousChoices.size() - 1);
			return choices[random.nextInt(2)];
		}
		return null;
	}
	
	// #4
	public static String donnantDeuxDonnant(List<String> previousChoices) {
		String[] choices = {"c", "t"};
		if (previousChoices != null && previousChoices.size() > 2) {
			int size = previousChoices.size();
			if (previousChoices.get(size - 1).equals(previousChoices.get(size - 2)))
				return previousChoices.get(size - 1);
			return choices[random.nextInt(2)];
		}
		return null;
	}
	
	// #9
	public static String aleatoire() {
		String[] choices = {"c", "t"};
		return choices[random.nextInt(2)];
	}
	
	// #12
	public static String rancunier(List<String> previousChoices) {
		if (previousChoices != null && !previousChoices.isEmpty()) {
			if (previousChoices.contains("t"))
				return "t";
			return "c";
		}
		return null;
	}
}
