package hw7;

import java.util.*;

public class CampusPaths {
	
	public static void main(String[] args) {
		CampusModel model = new CampusModel();
		CampusView view = new CampusView();
		
		model.createNewGraph("data/RPI_map_data_Nodes.csv", "data/RPI_map_data_Edges.csv");
		Scanner scanner = new Scanner(System.in);
		scanner.useDelimiter("\\n");
        String input;
		while (true) {
			
			input = scanner.next().trim();
			
			if (input.equals("b")) {
				view.printBuildings(model.ListBuildings());
			} else if (input.equals("r")) {
				// Checking if the input is a number or a building name to get the missing information
				System.out.print("First building id/name, followed by Enter: ");
                String input1 = scanner.next().strip();
                String Building1Name = "", Building1ID = "";
                if (input1.matches("\\d+")) {
                	Building1ID = input1;
                	Building1Name = model.getBuildingName(Building1ID);
                }
                else {
                    Building1Name = input1;
                    Building1ID = model.getID(Building1Name);
                }
                
                System.out.print("Second building id/name, followed by Enter: ");
                String input2 = scanner.next().strip();
                String Building2Name = "", Building2ID = "";
                if (input2.matches("\\d+")) {
                	Building2ID = input2;
                	Building2Name = model.getBuildingName(Building2ID);
                }
                else {
                    Building2Name = input2;
                    Building2ID = model.getID(Building2Name);
                }
                
                // Checking if the input is a valid building
                boolean containsBuilding1 = model.containsNode(Building1ID);
                boolean containsBuilding2 = model.containsNode(Building2ID);
                boolean NoEdgeCases = view.printEdgeCases(Building1Name, Building1ID, Building2Name, Building2ID, containsBuilding1, containsBuilding2);
                
				if (!NoEdgeCases) {
					continue;
				}
				List<Map<Map<String, List<String>>, Double>> path = model.findPath(Building1ID, Building2ID);
				if (path == null) {
		            System.out.print("There is no path from " + Building1Name + " to " + Building2Name + ".");
		        }
				else {
					double totalWeight = model.getTotalWeight(path);
					List<String> directions = model.calcDirection(path);
					view.printPath(path, Building1Name, Building2Name, totalWeight, directions);
				}
			} else if (input.equals("m")) {
				view.printCommands();
			} else if (input.equals("q")) {
                break;
            } else {
                System.out.println("Unknown option");
			}
		}
		scanner.close();
	}
}