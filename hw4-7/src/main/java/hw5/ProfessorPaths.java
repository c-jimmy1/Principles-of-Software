package hw5;

import java.io.IOException;
import java.util.*;

import hw4.Graph;

public class ProfessorPaths {
	// AF and RI would normally be here but this is not an ADT so they are not needed
	
	private Graph<String, String> graph;

	
	/**
	 * @param: filename     The path to a "CSV" file that contains the
	 *                      "professor","course" pairs
	 * @requires: filename != null
	 * @modifies: graph
	 * @effects: Creates a new graph with the given file, where each node is a professor,
	 * and each edge is pointing to another professor if they have taught the same courses.
	 * @throws: IOException if file cannot be read or file not a CSV file following
	 *                      the proper format.
	 * @returns: None
	 */
	public void createNewGraph(String filename) {
        try {
        	this.graph = new Graph<>();
        	Map<String, Set<String>> profsTeaching = new HashMap<>();
            Set<String> profs = new HashSet<>();
            
            ProfessorParser.readData(filename, profsTeaching, profs);
            // Loops through set all unique professors
			for (String professor : profs) {
				graph.addNode(professor);
				// Loop through the courses the professor is teaching
				for (String course : profsTeaching.keySet()) {
					
					Set<String> otherProfessors = profsTeaching.get(course);
					// If the professor is in the list of professors teaching the course
					if (otherProfessors.contains(professor)) {
						// Add an edge to the graph for each other professor also teaching the course
						for (String otherProfessor : otherProfessors) {
							// Skips adding an edge to the same professor
							if (!professor.equals(otherProfessor)) {
								graph.addEdge(professor, otherProfessor, course);
							}
						}
					}
				}
			}			
            
        } catch (IOException e) {
            e.printStackTrace(); // or handle the exception as appropriate
        }
	}
	
	
	/**
	 * @param: node1     The node where the path will start
	 * @param: node2     The second node, where the path will end
	 * @requires: node != "" && node2 != "" && node1 is in the graph && node2 is in the graph
	 * @modifies: None
	 * @effects: None
	 * @returns: String of the path from node1 to node2, if it exists. 
	 * 			Format as: path from Prof1 to ProfN:\nProfN−1 to ProfN via CourseN−1
	 * 			Otherwise if there is no path, returns String as: path from Prof1 to ProfN:\nno path found."
	 *          If a professor is not in the graph, returns "unknown professor Prof1\n" or "unknown professor Prof2\n" or both
	 */
	public String findPath(String node1, String node2) {
		if (!graph.containsNode(node1) && !graph.containsNode(node2) && !node1.equals(node2)) {
			return "unknown professor " + node1 + "\nunknown professor " + node2 + "\n";
		}
		else if (!graph.containsNode(node1)) {
			return "unknown professor " + node1 + "\n";
		}
		else if (!graph.containsNode(node2)) {
			return "unknown professor " + node2 + "\n";
		}
		if (node1.equals("") || node2.equals("")) {
			throw new IllegalArgumentException("Node cannot be empty");
		}
		if (node1.equals(node2)) {
			return "path from " + node1 + " to " + node2 + ":\n";
		}
		
		Queue<String> queue = new LinkedList<>();
        Map<String, List<String>> paths = new HashMap<>();
        queue.add(node1);
        paths.put(node1, new ArrayList<>(Collections.singletonList(node1)));
        
		while (!queue.isEmpty()) {
			String currentNode = queue.poll();
			if (currentNode.equals(node2)) {

				// Found the path, construct the path string format and return
				List<String> path = paths.get(currentNode);
				String path_str = "path from " + node1 + " to " + node2 + ":\n";
				for (int i = 0; i < path.size() - 1; i++) {
					String prof1 = path.get(i).split("\\(")[0].trim();
					String prof2 = path.get(i + 1).split("\\(")[0].trim();
					String edgeLabel = path.get(i + 1).replaceAll(".*\\((.*)\\)", "$1");
					path_str += prof1 + " to " + prof2 + " via " + edgeLabel + "\n"; 
				}
				return path_str;
			}
			
			// get neighbors of the current node
			Map<String, List<String>> neighbors = graph.listChildren(currentNode);
			
			List<String> childrenList = new ArrayList<>();
			
			// Collect the children into a List
			for (String child : neighbors.keySet()) {
				for (String edgeLabel : neighbors.get(child)) {
					childrenList.add(child + "(" + edgeLabel + ")");
				}
			}
			
			Collections.sort(childrenList);
			
            for (String child : childrenList) {
				String nextProf = child.split("\\(")[0].trim();
				String edgeLabel = child.replaceAll(".*\\((.*)\\)", "$1");
				if (!paths.containsKey(nextProf)) {
					List<String> newPath = new ArrayList<>(paths.get(currentNode));
					newPath.add(nextProf + "(" + edgeLabel + ")");
					paths.put(nextProf, newPath);
					queue.add(nextProf);
				}
            }
		}
        return "path from " + node1 + " to " + node2 + ":\nno path found\n";
        
	}
	
	/**
	 * Returns a boolean indicating whether the given node is in the graph
	 * 
	 * @param parentNode, the parent node of the edge (the node the edge is coming from)
	 * @requires nodeData != ""
	 * @return boolean, true if the node is in the graph, false otherwise
	 */
	public boolean containsNode(String parentNode) {
		return graph.containsNode(parentNode);
	}
	
	/**
	 * @param: None
	 * @requires: graph != null
	 * @modifies: None
	 * @effects: Prints the graph to the console
	 * @returns: None
	 */
	public void PrintGraph() {
		graph.printGraph();
	}
	
	
	
}