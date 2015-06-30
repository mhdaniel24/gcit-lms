
public class Player {
	
	private String name;
	private int numbChips;
	
	public Player(String name, int numbChips) {
		this.name = name;
		this.numbChips = numbChips;
	}

	public String getName() {
		return name;
	}
	
	public void increaseChipsBy(int increment)
	{
		numbChips = numbChips + increment;
	}

	public int getNumbChips() {
		return numbChips;
	}
	
	public String getPlayerSummary()
	{
		return name + " has " + Integer.toString(numbChips) + " chips\n";
	}
	
	
}
