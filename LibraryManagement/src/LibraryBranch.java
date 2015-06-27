//TODO: Fix naming of getters and setters
public class LibraryBranch {
	private String branchAddress;
	private String branchName;
	private String branchId;
	
	public LibraryBranch(){}
	
	public LibraryBranch(String name, String location, String branchId) {
		this.branchAddress = location;
		this.branchName = name;
		this.branchId = branchId;
	}

	public String getLocation() {
		return branchAddress;
	}

	public void setLocation(String location) {
		this.branchAddress = location;
	}

	public String getName() {
		return branchName;
	}

	public void setName(String name) {
		this.branchName = name;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	
	
}
