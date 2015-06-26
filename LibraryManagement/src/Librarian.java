import java.util.ArrayList;


public class Librarian {
	private LibraryBranch libraryBranch;

	public Librarian(){}
	
	public Librarian(String branchName, String branchLocation, String branchId)
	{
		libraryBranch = new LibraryBranch(branchName, branchLocation, branchId);
	}
	
	public String nameOfBranchWorksFor()
	{
		return libraryBranch.getName();
	}
	
	public String locationOfBranchWorksFor()
	{
		return libraryBranch.getName();
	}
	
	public String idOfBranchWorksFor()
	{
		return libraryBranch.getBranchId();
	}
	
	
	
}
