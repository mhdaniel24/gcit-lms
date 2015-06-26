import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryManager {

	//used to store a previous query to ensure that the order of the rows is the same
	private ArrayList<ArrayList<String>> libraryBranchNameLocationId;
	//TODO: get connection variables
	Connection conn;
	Statement stmt;
	ResultSet rs;
	
	public QueryManager()
	{
		//TODO: set connection
		
		
		libraryBranchNameLocationId = new ArrayList<ArrayList<String>>();
	}
	

	//returns name in the array in position 0 and returns locations in the array in position 1
	public ArrayList<ArrayList<String>> getAllBranchNamesAndLocationsQuery()
	{
		ArrayList<String> columnsOfInterest = new ArrayList<String>();
		columnsOfInterest.add("branchName");
		columnsOfInterest.add("branchAddress");
		columnsOfInterest.add("branchId");

		HashMap<String, ArrayList<String>> data =  executeSelectQuery("select * from tbl_library_branch", columnsOfInterest);

		libraryBranchNameLocationId.clear();
		libraryBranchNameLocationId.add(data.get("branchName"));
		libraryBranchNameLocationId.add(data.get("branchAddress"));
		libraryBranchNameLocationId.add(data.get("branchId"));

		return libraryBranchNameLocationId;
	}
	
	//in order to get the data in the same order
	public ArrayList<ArrayList<String>> getPrevAllBranchNamesAndLocationsQuery()
	{
		return libraryBranchNameLocationId;
	}
	
	//TODO: create a more general function that can handle any query not just selects that will use this one as a helper
	//able to execute any select query and return the expected columns
	private HashMap<String, ArrayList<String>> executeSelectQuery(String selectQuery, ArrayList<String> columnsOfInterest)
	{
		HashMap<String, ArrayList<String>> data = new HashMap<String, ArrayList<String>>();
		for(String element: columnsOfInterest){
			data.put(element, new ArrayList<String>());
		}
		
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "");
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(selectQuery);
			
			while(rs.next())
			{
				for(String element : columnsOfInterest){
					data.get(element).add(rs.getString(element));
				}
				
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch (Exception e2) {
				// TODO: handle exception
				System.out.println("Error clossing DB connection");
			}
		}
		return data;
	}
}
