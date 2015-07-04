import java.util.ArrayList;


public class Librarian {
	private LibraryBranch libraryBranch;

	public Librarian(){}

	public Librarian(String branchName, String branchLocation, String branchId)
	{
		libraryBranch = new LibraryBranch(branchName, branchLocation, branchId);
	}

	public LibraryBranch getLibraryBranch() {
		return libraryBranch;
	}





}
