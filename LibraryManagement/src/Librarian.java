
public class Librarian {
	private LibraryBranch libraryBranch;

	public Librarian(){}
	
	public Librarian(String branchName, String branchLocation)
	{
		libraryBranch = new LibraryBranch(branchName, branchLocation);
	}
	
	public String nameOfBranchWorksFor()
	{
		return libraryBranch.getName();
	}
	
	public String locationOfBranchWorksFor()
	{
		return libraryBranch.getName();
	}
	
}
