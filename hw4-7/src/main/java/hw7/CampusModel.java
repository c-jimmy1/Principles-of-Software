package hw7;

import java.util.*;

import hw4.Graph;
import hw6.DijkstraAlgorithm;

public class CampusModel {
private Graph<Map<String, List<String>>, Double> graph;
	
	/**
	 * @param: nodeFile     The path to a "CSV" file that contains the
	 *                     "building","id","x","y" data
	 * @param: edgeFile     The path to a "CSV" file that contains the "id1", "id2" data
	 * @requires: filename != null
	 * @modifies: graph
	 * @effects: Creates a new graph with the given node and edge files, where each node is a Map where the key is the building id,
	 *             and the value is a list of strings containing the building name, x and y coordinates. 
	 *             Each edge is pointing to another node with the weights representing the distance between them.
	 * @throws: IO Exception if file cannot be read or file not a CSV file following the proper format
	 * @returns: None
	 */
	public void createNewGraph(String nodeFile, String edgeFile) {
		graph = new Graph<>();
		Map<String, List<String>> NodeData = new HashMap<String, List<String>>();
		List<List<String>> EdgeData = new ArrayList<List<String>>();
		try {
			// read data from files
			MapParser.readNodeData(nodeFile, NodeData);
			MapParser.readEdgeData(edgeFile, EdgeData);
			
			for (String id : NodeData.keySet()) {
				Map<String, List<String>> node = new HashMap<String, List<String>>();
				node.put(id, NodeData.get(id));
				graph.addNode(node);
			}
			
			for (List<String> edge : EdgeData) {
				// get the two nodes, with their data
				String id1 = edge.get(0);
                String id2 = edge.get(1);
                List<String> id1Data = NodeData.get(id1);
                List<String> id2Data = NodeData.get(id2);
                
                // calculate distance between the two nodes
				double distance = calcDistance(Double.parseDouble(id1Data.get(1)), Double.parseDouble(id1Data.get(2)),
						Double.parseDouble(id2Data.get(1)), Double.parseDouble(id2Data.get(2)));
				
				
				// create a new edge
				Map<String, List<String>> node1 = new HashMap<String, List<String>>();
				Map<String, List<String>> node2 = new HashMap<String, List<String>>();
				node1.put(id1, id1Data);
				node2.put(id2, id2Data);
				graph.addEdge(node1, node2, distance);
				graph.addEdge(node2, node1, distance);
			}			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @requires: None
	 * @modifies: None
	 * @effects: Calculates the distance between two points
	 * @throws: None
	 * @returns: The distance between the two points
	 */
	public double calcDistance(double x1, double y1, double x2, double y2) {
	    return Math.abs(Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2)));
	}
	
	/**
	 * @param: id1 The id of the starting node
	 * @param: id2 The id of the ending node
	 * @requires: id1 != null, id2 != null
	 * @modifies: None
	 * @effects: Finds the shortest path between two nodes
	 * @throws: None
	 * @returns: A list of maps, where each map contains a node and the weight (Double) of
	 *           the edge to the next node
	 */
	public List<Map<Map<String, List<String>>, Double>> findPath(String id1, String id2) {
		DijkstraAlgorithm<Map<String, List<String>>> dijkstra = new DijkstraAlgorithm<>();
		Map<String, List<String>> startNode = getIDInfo(id1);
		Map<String, List<String>> endNode = getIDInfo(id2);
		
	    List<Map<Map<String, List<String>>, Double>> paths = dijkstra.Dijkstra(startNode, endNode, graph);
		
		return paths;
	}
	
	/**
	 * @param: id The id of the node to get information for
	 * @requires: id != null
	 * @modifies: None
	 * @effects: Gets the information for a given node
	 * @throws: None
	 * @returns: A map containing the node information
	 */
	public Map<String, List<String>> getIDInfo(String id) {
		Iterator<Map<String, List<String>>> nodeItr = graph.listNodes();
		while (nodeItr.hasNext()) {
			Map<String, List<String>> node = nodeItr.next();
			if (node.containsKey(id)) {
				return node;
			}
		}
		return null;
	}
	
	/**
	 * @param: id The id of the node to get the building name for
	 * @requires: id != null
	 * @modifies: None
	 * @effects: Gets the building name for a given node
	 * @throws: None
	 * @returns: The building name
	 */
	public String getBuildingName(String id) {
		Map<String, List<String>> node = getIDInfo(id);
		return node.get(id).get(0);
	}
	
	/**
	 * @param: building The name of the building to get the id for
	 * @requires: building != null
	 * @modifies: None
	 * @effects: Gets the id for a given building
	 * @throws: None
	 * @returns: The id
	 */
	public String getID(String building) {
		Iterator<Map<String, List<String>>> nodeItr = graph.listNodes();
		while (nodeItr.hasNext()) {
			Map<String, List<String>> node = nodeItr.next();
			for (String id : node.keySet()) {
				if (node.get(id).get(0).equals(building)) {
					return id;
				}
			}
		}
		return null;
	}

	/**
	 * @param: paths The list of paths to calculate the total weight for
	 * @requires: paths != null
	 * @modifies: None
	 * @effects: Calculates the total weight of a list of paths
	 * @throws: None
	 * @returns: The total weight
	 */
	public double getTotalWeight(List<Map<Map<String, List<String>>, Double>> path) {
		DijkstraAlgorithm<Map<String, List<String>>> dijkstra = new DijkstraAlgorithm<>();
		double totalWeight = dijkstra.calcTotalWeight(path);
		return totalWeight;
	}
	
	/**
	 * @param: path The list of paths to calculate the directions for
	 * @requires: path != null
	 * @modifies: None
	 * @effects: Calculates the direction of a list of paths, using angles and direction sectors
	 * @throws: None
	 * @returns: A list of directions
	 */
	public List<String> calcDirection(List<Map<Map<String, List<String>>, Double>> path) {
		List<String> directions = new ArrayList<>();
		for (int i = 0; i < path.size() - 1; i++) {
			Map<String, List<String>> node1 = path.get(i).keySet().iterator().next();
			Map<String, List<String>> node2 = path.get(i + 1).keySet().iterator().next();
			List<String> node1Data = node1.values().iterator().next();
			List<String> node2Data = node2.values().iterator().next();

			double x1 = Double.parseDouble(node1Data.get(1));
			double y1 = Double.parseDouble(node1Data.get(2));
			double x2 = Double.parseDouble(node2Data.get(1));
			double y2 = Double.parseDouble(node2Data.get(2));

			double angle = Math.toDegrees(Math.atan2(y2-y1, x2-x1));
			if (angle < 0) {
	            angle += 360; // Convert negative angle to positive
	        }
			angle = (angle + 90) % 360;
			String[] possibledirections = {"North", "NorthEast", "East", "SouthEast", "South", "SouthWest", "West", "NorthWest"};
	        int sector = (int) Math.floor((angle + 22.5) / 45) % 8;
			
	        directions.add(possibledirections[sector]);
			
		}
		return directions;
		
	}
	
	/**
	 * @requires: None
	 * @modifies: None
	 * @effects: Lists all the buildings in the graph
	 * @throws: None
	 * @returns: A map where the key is the building id, and the value is the
	 *           building name
	 */
	public Map<String, String> ListBuildings() {
		Map<String, String> buildings = new HashMap<>();
	    Iterator<Map<String, List<String>>> nodeItr = graph.listNodes();

	    while (nodeItr.hasNext()) {
	        Map<String, List<String>> node = nodeItr.next();
	        node.forEach((id, name) -> buildings.put(id, name.get(0)));
	    }

	    // Sort the entries by value and collect them into a LinkedHashMap
	    return buildings.entrySet().stream()
	            .sorted(Map.Entry.comparingByValue())
	            .collect(LinkedHashMap::new, (map, entry) -> 
	            map.put(entry.getKey(), entry.getValue()), LinkedHashMap::putAll);
	}
	
	/**
	 * @param: id The id of the node to check if it exists
	 * @requires: id != null
	 * @modifies: None
	 * @effects: Checks if a node exists in the graph
	 * @throws: None
	 * @returns: True if the node exists, false otherwise
	 */
	public boolean containsNode(String id) {
		Iterator<Map<String, List<String>>> nodeItr = graph.listNodes();
		while (nodeItr.hasNext()) {
			Map<String, List<String>> node = nodeItr.next();
			if (node.containsKey(id)) {
				return true;
			}
		}
		return false;
	}
}
