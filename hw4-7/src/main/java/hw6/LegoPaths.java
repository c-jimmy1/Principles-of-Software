 package hw6;

import java.io.IOException;
import java.util.*;

import hw4.Graph;
import hw5.ProfessorParser;

public class LegoPaths {
	
	private Graph<String, Double> graph;
	
	/**
	 * @param: filename 	The path to a "CSV" file that contains the "part","set"
	 *                  	pairs
	 * @requires: filename != null
	 * @modifies: graph
	 * @effects: Creates a new graph with the given file, where each node is a lego,
	 *           and each edge is pointing to another lego, with the weights representing the 
	 *           number of sets they are in together. For example, if lego1 and lego2 are in 3 sets, 
	 *           the edge between them will have a weight of 1/3. The weight is represented as a double
	 *           A path from a part to itself will have a weight of 0.0
	 * @throws: IOException if file cannot be read or file not a CSV file following
	 *                      the proper format.
	 * @returns: None
	 */
	public void createNewGraph(String filename) {
		try {
			this.graph = new Graph<>();
			Map<String, Set<String>> partsSets = new HashMap<>();
			Set<String> parts = new HashSet<>();
			
			ProfessorParser.readData(filename, partsSets, parts);
			
			for (String part : parts) {
	            graph.addNode(part);
	            for (String set : partsSets.keySet()) {
	                Set<String> otherParts = partsSets.get(set);
	                if (otherParts.contains(part)) {
	                    for (String otherPart : otherParts) {
	                        if (!part.equals(otherPart)) {
	                            // Check if an edge already exists between the two parts
	                            if (graph.containsEdge(part, otherPart)) {
	                                // If yes, update the weight of the existing edge
	                                double currentWeight = graph.getLastEdgeWeight(part, otherPart);
	                                double newWeight = 1.0 + currentWeight;
	                                graph.addEdge(part, otherPart, newWeight);
	                            } else {
	                                // If no, add a new edge with weight 1.0
	                                graph.addEdge(part, otherPart, 1.0);
	                            }
	                        } else {
	                            // If part equals otherPart, set weight to 0.0
	                            graph.addEdge(part, otherPart, 0.0);
	                        }
	                    }
	                }
	            }
	            Map<String, List<Double>> neighbors = graph.listChildren(part);
	            Map<String, List<Double>> sortedNeighbors = new TreeMap<>(neighbors);
	            
				for (String neighbor : sortedNeighbors.keySet()) {
					double currentWeight = graph.getLastEdgeWeight(part, neighbor);
					double newWeight = 0.0;
					if (currentWeight != 0.0) {
						newWeight = 1.0 / currentWeight;
					}
					graph.clearEdges(part, neighbor);
					graph.addEdge(part, neighbor, newWeight);
				}
	        }
			
			
		}
		catch (IOException e) {
            e.printStackTrace(); // or handle the exception as appropriate
        }
	}
	
	/**
	 * @param: node1     The node where the path will start
	 * @param: node2     The second node, where the path will end
	 * @requires: node != "" && node2 != "" && node1 is in the graph && node2 is in the graph
	 * @modifies: None
	 * @effects: None
	 * @returns: String of the path from node1 to node2, if it exists. Using Dijkstra's algorithm
	 * 			Format as: path from Prof1 to ProfN:\nProfN−1 to ProfN via CourseN−1
	 * 			Otherwise if there is no path, returns String as: path from Prof1 to ProfN:\nno path found."
	 *          If a professor is not in the graph, returns "unknown professor Prof1\n" or "unknown professor Prof2\n" or both
	 */
	public String findPath(String part1, String part2) {
		DijkstraAlgorithm<String> dijkstraAlgorithm = new DijkstraAlgorithm<>();
	    if (!graph.containsNode(part1) && !graph.containsNode(part2) && !part1.equals(part2)) {
	        return "unknown part " + part1 + "\nunknown part " + part2 + "\n";
	    } else if (!graph.containsNode(part1)) {
	        return "unknown part " + part1 + "\n";
	    } else if (!graph.containsNode(part2)) {
	        return "unknown part " + part2 + "\n";
	    }
	    if (part1 == null || part2 == null) {
	        throw new IllegalArgumentException("Node cannot be empty");
	    }
	    if (part1.equals(part2)) {
	        return "path from " + part1 + " to " + part2 + ":\ntotal cost: 0.000\n";
	    }

	    List<Map<String, Double>> path = dijkstraAlgorithm.Dijkstra(part1, part2, graph);

		if (path == null) {
			return "path from " + part1 + " to " + part2 + ":\nno path found\n";
		}
		double totalWeight = calcTotalWeight(path);
      
	    StringBuilder sb = new StringBuilder();
	    sb.append("path from ").append(part1).append(" to ").append(part2).append(":\n");
	    for (int i = 0; i < path.size() - 1; i++) {          
	    	String part = path.get(i).keySet().iterator().next();
	        String nextPart = path.get(i + 1).keySet().iterator().next();
	        sb.append(part).append(" to ").append(nextPart).append(" with ");
	        double weight = path.get(i + 1).get(nextPart);
	        sb.append("weight ").append(String.format("%.3f", weight)).append("\n");
	    }
	    sb.append("total cost: ").append(String.format("%.3f", totalWeight)).append("\n");
	    return sb.toString();
	}
	
	/**
	 * @param: path The path that will be calculated
	 * @requires: path != null
	 * @modifies: None
	 * @effects: None
	 * @returns: The total weight of the path
	 */
	private static double calcTotalWeight(List<Map<String, Double>> path) {
		double totalWeight = 0.0;
		for (Map<String, Double> edge : path) {
			for (String part : edge.keySet()) {
				totalWeight += edge.get(part);
			}
		}
		return totalWeight;
	}
 
	
	/**
	 * @param: None
	 * @requires: None
	 * @modifies: None
	 * @effects: Prints the graph
	 * @returns: None
	 */
	public void PrintGraph() {
		graph.printGraph();
	}
}