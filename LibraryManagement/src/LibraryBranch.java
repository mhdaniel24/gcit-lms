
public class LibraryBranch {
	private String location;
	private String name;
	private String branchId;
	
	public LibraryBranch(){}
	
	public LibraryBranch(String name, String location, String branchId) {
		this.location = location;
		this.name = name;
		this.branchId = branchId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	
	
}
