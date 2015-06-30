
public class ChipsGame {
	private Player p1;
	private Player p2;
	private Pile pile;
	private boolean gameOver;
	//true for p1 false for p2
	private boolean playerOneTurn;
	
	public ChipsGame(String p1Name, String p2Name, int totalNumbChips) {
		p1 = new Player(p1Name, 0);
		p2 = new Player(p2Name, 0);
		pile = new Pile(totalNumbChips);
		gameOver = false;
		playerOneTurn = true;
	}
	
	public int howManyChipsCanBeTaken()
	{
		if(pile.getNumbOfChips() == 1)
			return 1;
		
		return pile.getNumbOfChips()/2;
	}
	
	
	//it sets the game over to alert that there are no more moves left
	//it alternates the players
	public void play(int numbChips)
	{
		pile.reduceChipsBy(numbChips);
		
		if(playerOneTurn)
		{
			p1.increaseChipsBy(numbChips);
		}
		else
		{
			p2.increaseChipsBy(numbChips);
		}
		
		if(pile.getNumbOfChips() == 0)
		{
			gameOver = true;
		}
		
		playerOneTurn = !playerOneTurn;
	}
	
	public String whoWins()
	{
		if(p1.getNumbChips() % 2 == 0)
		{
			return p1.getName();
		}
		else
		{
			return p2.getName();
		}
	}
	
	//TODO: fix this String construction if needed (move it to main)
	public String getNextPlaySummary()
	{
		return getSummaryOfPlayer(PosiblePlayers.Player1) + getSummaryOfPlayer(PosiblePlayers.Player2) + getSummaryOfTurn() + getSummaryOfPile() + getSummaryOfRemainingChips();
	}
	public String getSummaryOfTurn()
	{
		return "It is your turn, " + whoseTurn() + ".\n";
	}
	public String getSummaryOfRemainingChips()
	{
		return "You may take any number of chips from 1 to " + Integer.toString(howManyChipsCanBeTaken()) + ". How many would you like, " + whoseTurn() + "? "; 
	}
	public String getSummaryOfPlayer(PosiblePlayers player)
	{
		switch (player) {
		case Player1:
			return p1.getPlayerSummary();
		default:
			return p2.getPlayerSummary();
		}
	}
	
	public String getSummaryOfPile()
	{
		return pile.getPileSummary();
	}
	public String whoseTurn()
	{
		if (playerOneTurn) {
			return p1.getName();
		}
		
		return p2.getName();
	}
	//--------------------------
	
	
	public boolean isGameOver()
	{
		return gameOver;
	}
	
	
	
	
	
	
}
