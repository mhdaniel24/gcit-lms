import java.util.Random;


public class RandomNumberGame {

	private int randNumber;
	private int numbTrials;
	private final int maxTrials = 5;
	private boolean validGuess;

	public RandomNumberGame() {
		Random rand = new Random();
		randNumber = rand.nextInt();
		numbTrials = 0;
		validGuess = false;
	}

	public int getRandNumber() {
		return randNumber;
	}

	//TODO: modify this methodso that it can have any accuracy
	public boolean play(int numb)
	{
		if((Math.abs(randNumber - numb)) <= 10)
		{
			validGuess = true;
			return true;
		}
		return false;
	}

	public void increaseNumbTrials()
	{
		numbTrials++;
	}

	public boolean hasTrialAvailable()
	{
		return numbTrials < maxTrials;
	}

	public boolean anyValidGuess() {
		return validGuess;
	}

//	public void setValidGuess(boolean validGuess) {
//		this.validGuess = validGuess;
//	}

	

	
}
