import java.util.ArrayList;

public class QueryManager {

	private ArrayList<ArrayList<String>> namesAndLocations;
	//TODO: get connection variables
	
	public QueryManager()
	{
		//TODO: set connection
		namesAndLocations = new ArrayList<ArrayList<String>>();
	}
	
	//TODO: implement this function with the actual database data
	//returns name in the array in position 0 and returns locations in the array in position 1
	public ArrayList<ArrayList<String>> getAllBranchNamesAndLocationsQuery()
	{
		ArrayList<String> names = new ArrayList<String>();
		names.add("name1");
		names.add("name2");
		names.add("name3");
		
		ArrayList<String> locations = new ArrayList<String>();
		locations.add("location1");
		locations.add("location2");
		locations.add("location3");
		
		namesAndLocations.clear();
		namesAndLocations.add(names);
		namesAndLocations.add(locations);
		
		return namesAndLocations;
	}
	
	public ArrayList<ArrayList<String>> getPrevAllBranchNamesAndLocationsQuery()
	{
		return namesAndLocations;
	}
	
}
