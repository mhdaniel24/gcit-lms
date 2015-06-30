import java.util.Scanner;


public class Main {

	public static void main(String[] args) {
		// initializing game
		RandomNumberGame randGame = new RandomNumberGame();
		Scanner sc = new Scanner(System.in);
		int selectedNumber;
		
		//TODO: delete this is just for testing
		System.out.println("The number is: " + randGame.getRandNumber());
		
		//checks if it should continue or not based on the number of trials and correct answer of user
		while (randGame.hasTrialAvailable() && !randGame.anyValidGuess()) {
			System.out.println("Guess an integer number:");			
			try {
				selectedNumber = sc.nextInt();
				randGame.increaseNumbTrials();
				
				if(randGame.play(selectedNumber))
				{
					//randGame.setValidGuess(true);
					System.out.println("You won. The correct number was " + randGame.getRandNumber());
				}
				
			} catch (Exception e) {
				sc.nextLine();
				System.out.println("The value you entered is not valid. Try Again.");
			}
			
		}
		
		if(!randGame.hasTrialAvailable())
		{
			System.out.println("Sorry");
		}
		
		sc.close();
	}

}
