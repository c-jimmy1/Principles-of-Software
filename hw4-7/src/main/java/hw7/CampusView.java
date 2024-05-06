package hw7;
import java.util.*;

public class CampusView {
	
	/**
	 * @param: buildings A map of building id to building name
	 * @modifies: None
	 * @effects: Prints the list of buildings in the map
	 * @returns: None
	 */
	public void printBuildings(Map<String, String> buildings) {
		for (String id : buildings.keySet()) {
			if (buildings.get(id) != "") {
				System.out.println(buildings.get(id) + "," + id);
			}
		}
	}
	
	/**
	 * @param: None
	 * @modifies: None
	 * @effects: Prints the list of commands
	 * @returns: None
	 */
	public void printCommands() {
        System.out.println("b lists all buildings");
        System.out.println("r prints directions for the shortest route between any two buildings");
        System.out.println("m prints a menu of all commandsm");
        System.out.println("q to quit");
	}
	
	
	/**
	 * @param: Building1Name     The name of the first building
	 * @param: Building1ID       The id of the first building
	 * @param: Building2Name     The name of the second building
	 * @param: Building2ID       The id of the second building
	 * @param: containsBuilding1 Whether the first building is in the map
	 * @param: containsBuilding2 Whether the second building is in the map
	 * @modifies: None
	 * @effects: Prints the edge cases for the input buildings
	 * @returns: Whether the edge cases are valid
	 */
	public boolean printEdgeCases(String Building1Name, String Building1ID, String Building2Name, String Building2ID, 
			boolean containsBuilding1, boolean containsBuilding2) {
		if (Building1Name.equals("") || Building2Name.equals("")) {
			if (Building1Name.equals("") && Building2Name.equals("") && !Building1ID.equals(Building2ID)) {
				System.out.print("Unknown building: [" + Building1ID + "]\nUnknown building: [" + Building2ID + "]");
			}
			else {
	            System.out.print("Unknown building: [" + Building1ID + "]");  
			}
			return false;
		}

		if (!containsBuilding1 && !containsBuilding2 && !Building1Name.equals(Building2Name)) {
			System.out.print("Unknown building: [" + Building1Name + "]\nUnknown building: [" + Building2Name + "]");
			return false;
		} else if (!containsBuilding1) {
			System.out.print("Unknown building: [" + Building1Name + "]");
			return false;
		} else if (!containsBuilding2) {
			System.out.print("Unknown building: [" + Building2Name + "]");
			return false;
		}
		
		if (Building1ID.equals(Building2ID)) {
                System.out.print("Path from " + Building1Name + " to " + Building2Name + ":\nTotal distance: 0.000 pixel units.");
	        return false;
	    }
		return true;
	}
		
	/**
	 * @param: paths         A list of maps, where each map contains a node and the
	 *                       weight (Double) of the edge to the next node
	 * @param: Building1Name The name of the first building
	 * @param: Building2Name The name of the second building
	 * @param: totalWeight   The total weight of the path
	 * @param: directions    A list of directions to get to the next building
	 * @modifies: None
	 * @effects: Prints the path from the first building to the second building
	 * @returns: None
	 */
	public void printPath(List<Map<Map<String,List<String>>, Double>> paths, String Building1Name, 
			String Building2Name, double totalWeight, List<String> directions) {
		System.out.println("Path from " + Building1Name + " to " + Building2Name + ":");
		int i = 0;
	    for (Map<Map<String, List<String>>, Double> path : paths) {
	    	if (i != 0) {
				String id = path.keySet().iterator().next().keySet().iterator().next();
		    	String building = path.keySet().iterator().next().values().iterator().next().get(0);
	        	if (building == "") {
					System.out.println("\tWalk " + directions.get(i-1) +  " to " + "(Intersection " + id + ")");
				}
				else {
	                System.out.println("\tWalk " + directions.get(i-1) +  " to (" + building + ")");
	            }
	    	}
	    	i++;
	    }
	    System.out.println("Total distance: " + String.format("%.3f", totalWeight) + " pixel units.");
	}
	
}
