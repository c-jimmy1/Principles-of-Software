package hw0;

import java.util.Random;

public class RandomHello {
	/**
	* Uses a RandomHello object to print
	* a random greeting to the console.
	*/
	public static void main(String[] argv) {
	RandomHello randomHello = new RandomHello();
	System.out.println(randomHello.getGreeting());
	}
	/**
	* @return a random greeting from a list of five different greetings.
	*/
	public String getGreeting() {
		String[] greetings = new String[5];
		greetings[0] = "Hello, World";
		greetings[1] = "Hola Mundo";
		greetings[2] = "Bonjour, le Monde";
		greetings[3] = "Hallo Welt";
		greetings[4] = "Ciao Mondo";
		
		Random randomGenerator = new Random();
		// Use nextInt to choose a random index from the greetings array
        int randomIndex = randomGenerator.nextInt(greetings.length);

        // Return the randomly selected greeting
        return greetings[randomIndex];
	}
}