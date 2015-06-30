import java.util.Scanner;


public class Main {

	public static void main(String[] args) {
		//decide to play another round or stop
		boolean keepPlaying = false;
		
		do
		{
			//initializing variables
			Scanner sc = new Scanner(System.in);
			String p1Name;
			String p2Name;
			int numbChips = 0;
			ChipsGame chipsGame;
			int chosenNumberOfChips;
			
			
			//get name of the first player(there is no described restrictions for naming the 1st player)
			System.out.print("What is the name of the first player? ");
			p1Name = sc.nextLine();
			
			//get the name of the seccond player
			System.out.print("What is the name of the second player? ");
			p2Name = sc.nextLine();
			
			//checking that first and second player's names do not match each other
			while(p1Name.equalsIgnoreCase(p2Name))
			{
				System.out.print("Both players cannot be named "+p2Name+". Enter a different name:");
				p2Name = sc.nextLine();
			}
			
			//getting number of chips (it must be at least 3 and odd)
			while (numbChips == 0) {
				System.out.print("How many chips does the initial pile contain? ");
				try {
					numbChips = sc.nextInt();
					if (numbChips < 3 || numbChips%2 == 0) {
						numbChips = 0;
						System.out.println("The number of chips must be a positive odd integer greather than 2. Try Again");
					}
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("The number of chips must be a positive odd integer greather than 2. Try Again");
					sc.nextLine();
				}
			}
			
			//instantiating the game
			chipsGame = new ChipsGame(p1Name, p2Name, numbChips);
			
			//checking to see if there should be another round (game is over when the number of chips in the pile is 0)
			while(!chipsGame.isGameOver())
			{
				System.out.println("\n* * * * * * * * * * * * * * * * * * * * * * * * * * *");
				System.out.println(chipsGame.getNextPlaySummary());//gets an String with description of the next play (whose turn, # of chips)
				chosenNumberOfChips = 0;//for the user to select how many he wants
				
				//loop to test that the user is entering a valid value
				while(chosenNumberOfChips == 0)
				{
					try {
						chosenNumberOfChips = sc.nextInt();
						if(chosenNumberOfChips < 1)//too small
						{
							chosenNumberOfChips = 0;
							System.out.println("Ilegal move. You must take at least one chip.");
						}
						else if(chosenNumberOfChips > chipsGame.howManyChipsCanBeTaken())//too big
						{
							chosenNumberOfChips = 0;
							System.out.println("Ilegal move. You cant take more than " + chipsGame.howManyChipsCanBeTaken());
						}
						else//right selection
						{
							//informing that the current user selected chosenNumberOfChips
							chipsGame.play(chosenNumberOfChips);
						}
					} catch (Exception e) {
						// wrong input
						sc.nextLine();
						System.out.println("The value you enter is not valid. Try again.");
					}
				}
			}
			
			System.out.print(chipsGame.getSummaryOfPlayer(PosiblePlayers.Player1));
			System.out.print(chipsGame.getSummaryOfPlayer(PosiblePlayers.Player2));
			System.out.print(chipsGame.whoWins() + " wins!\n\n");
			
			System.out.println("Play another game? (y/n)");
			String desicion = sc.next();
			keepPlaying = desicion.equalsIgnoreCase("y");
			
			if(!keepPlaying)
				sc.close();
			
		}while(keepPlaying);
		
		System.out.println("\nThanks for Playing");
	}
}
