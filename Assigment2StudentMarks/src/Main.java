import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;


public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File file = new File("data.txt");
		Scanner sc;
		TreeMap<String, ArrayList<Double>> aphaOrderedtreeMap = new TreeMap<String, ArrayList<Double>>();
		
		try {
			//reading the file and creating the structure
			sc = new Scanner(file);
			while (sc.hasNext()) {
				String name = sc.next();
				Double mark = sc.nextDouble();
				
				if(aphaOrderedtreeMap.containsKey(name))
				{
					aphaOrderedtreeMap.get(name).add(mark);
				}
				else
				{
					//for iteration since I will be iterating the elements multiple times (I will not be deleting anything)> I will not been shifting
					ArrayList<Double> linkedList = new ArrayList<Double>();
					linkedList.add(mark);
					aphaOrderedtreeMap.put(name, linkedList);
				}
				
			}
			
			//doing the alphabetical order task
			alphaOrderTask(aphaOrderedtreeMap);
			//doing the merit order task
			meritOrder(aphaOrderedtreeMap);
			//summary of the entire list of students
			System.out.println("\n");
			generalSumary(aphaOrderedtreeMap);
			sc.close();
			
		} catch (Exception e) {
			System.out.println("File not found");
		}
	}
	
	public static void generalSumary(TreeMap<String, ArrayList<Double>> aphaOrderedtreeMap)
	{
		Double averageMark = 0.0;
		ArrayList<Double> individualAverages = new ArrayList<Double>();
		Double standarDeviation = 0.0;
		
		//calculating the averageMark of all the students
		Set set = aphaOrderedtreeMap.entrySet();
		Iterator it = set.iterator();
		while(it.hasNext())
		{
			Map.Entry entry = (Map.Entry)it.next();
			double avg = getAverage((ArrayList<Double>)entry.getValue());
			individualAverages.add(avg);
			averageMark += avg;
		}
		averageMark = averageMark/aphaOrderedtreeMap.entrySet().size();
		standarDeviation = getStandardDeviation(averageMark, individualAverages);
		
		System.out.println("Number of students: " + aphaOrderedtreeMap.entrySet().size());
		System.out.print("Average student mark: ");
		System.out.printf("%.1f\n", averageMark);
		System.out.print("Standard Deviation: ");
		System.out.printf("%.1f\n", standarDeviation);
	}
	
	public static void meritOrder(TreeMap<String, ArrayList<Double>> aphaOrderedtreeMap)
	{
		//average -> [names of students with that average]
		TreeMap<Double, ArrayList<String>> rankSortedTreeMap = new TreeMap<Double, ArrayList<String>>();
		Set set = aphaOrderedtreeMap.entrySet();
		Iterator it = set.iterator();
		
		//sorting based on the average marks (in an increasing fashion)
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry)it.next();
			Double avg = getAverage((ArrayList<Double>)entry.getValue());
			if(rankSortedTreeMap.containsKey(avg))
			{
				rankSortedTreeMap.get(avg).add((String)entry.getKey());
			}
			else
			{
				ArrayList<String> arrayList = new ArrayList<String>();
				arrayList.add((String)entry.getKey());
				rankSortedTreeMap.put(avg, arrayList);
			}
		}
		
		//Using a LinkedList in an Stack fashion to reverse the order of the keys (to get decreasing order)
		LinkedList<Double> reversedKeys = new LinkedList<Double>();
		set = rankSortedTreeMap.entrySet();
		it = set.iterator();
		
		while(it.hasNext())
		{
			Map.Entry entry = (Map.Entry)it.next();
			reversedKeys.push((Double)entry.getKey());
		}
		
		//printing result
		System.out.println("\nMerit Order");
		int pos = 1;
		for(Double avg: reversedKeys)
		{
			ArrayList<String> namesWithThisAvg = rankSortedTreeMap.get(avg);
			for(String name : namesWithThisAvg)
			{
				System.out.println(pos + " " + name + " " + aphaOrderedtreeMap.get(name).size() + " " + avg);
			}
			pos++;
		}
	}
	
	public static void alphaOrderTask(TreeMap<String, ArrayList<Double>> aphaOrderedtreeMap)
	{
		Set set = aphaOrderedtreeMap.entrySet();
		Iterator it = set.iterator();
		
		System.out.println("Alpha Order");
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry)it.next();
			System.out.println(entry.getKey() + " " + ((ArrayList)entry.getValue()).size() + " " + getAverage((ArrayList<Double>)entry.getValue()));
		}
	}
	
	public static double getAverage(ArrayList<Double> arrayList)
	{
		double sum = 0;
		for(double mark : arrayList)
		{
			sum += mark;
		}
		
		return sum/arrayList.size();
	}
	
	public static double getStandardDeviation(double mean, ArrayList<Double> individualAvgs)
	{	
		double standarDeviation = 0.0;
	
		for(double individualAvg : individualAvgs)
		{	
			double diff = mean - individualAvg;
			standarDeviation =standarDeviation +  diff * diff;
		}
		
		standarDeviation = standarDeviation/(individualAvgs.size() - 1);
		standarDeviation = Math.sqrt(standarDeviation);
		return standarDeviation;
	}

}
