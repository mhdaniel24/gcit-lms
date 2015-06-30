import java.util.*;

public class SetDemo{
	public static void main(String [] args){
		
		  // Create a set called mySet
		 Set mySet = new HashSet();
		 String fruit1 = "pear", fruit2="banana", fruit3="tangerine",
		 fruit4="strawberry", fruit5="blackberry";
		 mySet.add(fruit1);
		 mySet.add(fruit2);
		 mySet.add(fruit3);
		 mySet.add(fruit4);
		 mySet.add(fruit5);
		 
		 System.out.println("mySet now contains:");
		 System.out.println(mySet);
		 
		 System.out.println("The cardinality of mySet is " + mySet.size());
		 
		 
		 mySet.remove(fruit5);
		 mySet.remove(fruit4);
		 System.out.println(mySet);
		 
		 mySet.clear();
		 if (mySet.isEmpty()) {
			System.out.println("The set is empty.");
		}
		 
		List<String> myList = new ArrayList<String>();
		myList.add(fruit1);
		myList.add(fruit2);
		myList.add(fruit3);
		myList.add(fruit4);
		myList.add(fruit5);
		
		ListIterator<String> it = myList.listIterator();
		System.out.println("\nElements of myList in foward order:");
		while (it.hasNext()) {
			System.out.println(it.next());			
		}
		
		System.out.println("\nElements of myList in reverse order:");
		while (it.hasPrevious()) {
			System.out.println(it.previous());
		}
		
		myList.add(3, fruit2);
		System.out.println("\nmyList now contains(after adding a second banana):");
		System.out.println(myList);
		
		
	}
}