package hw4;

import java.util.Map;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * <b>Graph</b> represents a mutable directed multigraph with labeled edges.
 * <p>
 *
 * Examples of Graphs are: 
 */

public class GraphWrapper {
	private Graph<String, String> graph;

	/**
     * @effects Constructs a new Graph with no Nodes or edges.
     */
	public GraphWrapper() {
		this.graph = new Graph<>();
	}
	
	/**
	 * 
     * @param adjacencyMatrix, a map of Nodes to their corresponding edges and labels in the graph
     * @requires adjacencyMatrix != null
     * @effects Constructs a new Graph with the given adjacencyMatrix
     */
	public GraphWrapper(Map<String, Map<String, List<String>>> adjacencyMatrix) {
		if (adjacencyMatrix == null) {
			throw new IllegalArgumentException("adjacencyMatrix cannot be null");
		}
		this.graph = new Graph<>(adjacencyMatrix);
	}
	

	/**
     * 
     * @param nodeData, a string representing the node to be added
     * @requires nodeData != "" && nodeData is not already in the graph
     * @modifies adjacencyMatrix
     * @effects adjacencyMatrix.size = adjacencyMatrix.size + 1
     */
    public void addNode(String nodeData) {
		graph.addNode(nodeData);
    }
    
    /**
	 * 
	 * @param parentNode, the parent node of the edge (the node the edge is coming from)
	 * @param childNode, the child node of the edge (the node the edge is pointing to)
	 * @param edgeLabel, the label of the edge being added
	 * @requires parentNode != "" && childNode != "" && parentNode is already in the graph
	 * @modifies adjacencyMatrix
	 * @effects adds a new child node to the parent node with the given label 
     *          if the child node doesn't exist,
	 *          otherwise updates the label of the edge to the child node
	 */
	public void addEdge(String parentNode, String childNode, String edgeLabel) {
		graph.addEdge(parentNode, childNode, edgeLabel);
	}
	
	/**
	 * Returns an iterator that represents all the nodes this Graph in lexicographical order.
	 *
	 * @requires adjecencyMatrix != null
	 * @return Iterator<String> that represents all the nodes in the graph in lexicographical order
	 */
	public Iterator<String> listNodes() {
		// Retrieve the unsorted list of nodes from the Graph class
        Iterator<String> unsortedNodesIterator = graph.listNodes();
        
        // Collect the nodes into a List
        List<String> nodesList = new ArrayList<>();
        while (unsortedNodesIterator.hasNext()) {
            nodesList.add(unsortedNodesIterator.next());
        }
        
        // Sort the list of nodes
        Collections.sort(nodesList);
        
        // Return an iterator over the sorted list of nodes
        return nodesList.iterator();
	}
	
	/**
	 * Returns an iterator that represents all the children of the given node in
	 * lexicographical order. First by the node name and secondarily by the edge label. If there is a
	 * reflexive edge, parentNode(edgeLabel) should appear in the list of children. 
	 *
	 * @param parentNode, the node to get the children of
	 * @requires parentNode != "" && parentNode is in the graph
	 * @return Iterator<String> that represents all the children of the given node
	 */
	public Iterator<String> listChildren(String parentNode) {
		// Retrieve the unsorted list of children from the Graph class
		Map<String, List<String>> childrenMap = graph.listChildren(parentNode);
		List<String> childrenList = new ArrayList<>();
		
		// Collect the children into a List
		for (String child : childrenMap.keySet()) {
			for (String edgeLabel : childrenMap.get(child)) {
				childrenList.add(child + "(" + edgeLabel + ")");
			}
		}
		
		Collections.sort(childrenList);
		

		// Return an iterator over the sorted list of children
		return childrenList.iterator();
	}

}
