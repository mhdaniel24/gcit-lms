
public class Pile {
	private int numbOfChips;

	public Pile(int numbOfChips) {
		this.numbOfChips = numbOfChips;
	}
	
	public int getNumbOfChips() {
		return numbOfChips;
	}

	public void reduceChipsBy(int decrement)
	{
		numbOfChips = numbOfChips - decrement;
	}
	
	public String getPileSummary()
	{
		return "There are "+Integer.toString(numbOfChips)+" chips remaining.\n";
	}
		
}
