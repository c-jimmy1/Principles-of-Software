package hw4;

import java.util.Map;
import java.util.Iterator;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;


/**
 * <b>Graph</b> represents a mutable directed multigraph with labeled edges.
 * <p>
 *
 * Examples of Graphs are: social networks, computer networks, airline flights between cities, etc.
 */

public class Graph <N, E> {
	// Abstraction Function:
    // Represents a graph where nodes are represented by strings and edges are represented as labeled strings.
	// Each node in the graph is mapped to a list of strings, where each string represents an edge with the format "childNode(edgeLabel)".
	// If there is a reflexive edge, parentNode(edgeLabel) should appear in the list of children.
	// If there are no children, the list will be empty.
	// If there are no nodes, the size of the graph will be 0.
    //
	// Representation Invariant for every Graph adjacencyList:
    // Each key in the adjacencyList (representing a node) must be a non-null, non-empty generic value.
	// Each value in the adjacencyList (representing edges) must be a map of non-null, non-empty generic values
	// Each key in the map of edges (representing child node connecting to parent) must be a non-null, non-empty generic value.
	// Each value in the map of edges (representing edge labels) must be a list of non-null, non-empty generic values.

	// In other words:
	// The adjacencyList must not contain null keys or values.
	// Each node must have a valid list of edges associated with it.
	// No two nodes can have the same name.
	// Each edge representation in the list must follow the specified format: "childNode(edgeLabel)".
	
	private Map<N, Map<N, List<E>>> adjacencyList;
	
	private void checkRep() {
	    // Check if adjacencyList is null
	    if (adjacencyList == null) {
	        throw new IllegalStateException("Adjacency list cannot be null.");
	    }

	    // Check representation invariant for every Graph adjacencyList
	    for (Map.Entry<N, Map<N, List<E>>> entry : adjacencyList.entrySet()) {
	        N node = entry.getKey();
	        Map<N, List<E>> edges = entry.getValue();

	        // Each key in the adjacencyList must be a non-null, non-empty value
	        if (node == null) {
	            throw new IllegalStateException("Representation Invariant violated: Node key cannot be null.");
	        }

	        // Each value in the adjacencyList must be a non-null, non-empty map
	        if (edges == null) {
	            throw new IllegalStateException("Representation Invariant violated: Map of edges cannot be null.");
	        }

	        // Each key in the map of edges must be a non-null, non-empty value
	        for (N childNode : edges.keySet()) {
	            if (childNode == null) {
	                throw new IllegalStateException("Representation Invariant violated: Child node key cannot be null.");
	            }

	            // Each value in the map of edges must be a non-null, non-empty list
	            List<E> edgeLabels = edges.get(childNode);
	            if (edgeLabels == null) {
	                throw new IllegalStateException("Representation Invariant violated: List of edge labels cannot be null.");
	            }

	            // Each edge representation in the list must follow the specified format: "childNode(edgeLabel)"
	            for (E edgeLabel : edgeLabels) {
	                if (edgeLabel == null) {
	                    throw new IllegalStateException("Representation Invariant violated: Edge label cannot be null.");
	                }
	            }
	        }
	    }
	}

	/**
     * @effects Constructs a new Graph with no Nodes or edges.
     */
	public Graph() {
		this.adjacencyList = new HashMap<>();
		// checkRep();
	}
	
	/**
	 * 
     * @param adjacencyList, a map of Nodes to their corresponding edges and labels in the graph
     * @requires adjacencyList != null
     * @effects Constructs a new Graph with the given adjacencyList
     */
	public Graph(Map<N, Map<N, List<E>>> adjacencyList) {
		if (adjacencyList == null) {
			throw new IllegalArgumentException("adjacencyList cannot be null");
		}
		this.adjacencyList = adjacencyList;
		// checkRep();
	}
	

    /**
     * 
     * @param nodeData, a string representing the node to be added
     * @requires nodeData != "" && nodeData is not already in the graph
     * @modifies adjacencyList
     * @effects adjacencyList.size = adjacencyList.size + 1
     */
    public void addNode(N nodeData) {
		if (nodeData == null) {
			throw new IllegalArgumentException("nodeData cannot be null or empty");
		}
		if (adjacencyList.containsKey(nodeData)) {
			throw new IllegalArgumentException("nodeData already exists in the graph");
		}
		adjacencyList.put(nodeData, new HashMap<>());
		// checkRep();
    }
    
	/**
	 * 
	 * @param parentNode, the parent node of the edge (the node the edge is coming from)
	 * @param childNode, the child node of the edge (the node the edge is pointing to)
	 * @param edgeLabel, the label of the edge being added
	 * @requires parentNode != "" && childNode != ""
	 *           && parentNode is already in the graph
	 * @modifies adjacencyList
	 * @effects adds a new child node to the parent node with the given label if the child node doesn't exist,
	 *         otherwise updates the label of the edge to the child node
  
	 */
	public void addEdge(N parentNode, N childNode, E edgeLabel) {
		if (parentNode == null) {
			throw new IllegalArgumentException("parentNode cannot be null or empty");
		}
		if (childNode == null) {
			throw new IllegalArgumentException("childNode cannot be null or empty");
		}
		if (!adjacencyList.containsKey(parentNode)) {
			throw new IllegalArgumentException("parentNode does not exist in the graph");
		}
		
		Map<N, List<E>> edgesFromParent = adjacencyList.get(parentNode);
 		if (!edgesFromParent.containsKey(childNode)) {
            edgesFromParent.put(childNode, new ArrayList<>());
 		}
 		edgesFromParent.get(childNode).add(edgeLabel);
		
        // checkRep();
	}
	
	
	/**
	 * 
	 * @param nodeData, a string representing the node to be removed
	 * @requires nodeData != "" && nodeData is in the graph
	 * @modifies adjacencyList
	 * @effects removes the node from the graph
	 */
	public void removeNode(N nodeData) {
		if (nodeData == null) {
			throw new IllegalArgumentException("nodeData cannot be null or empty");
		}
		if (!adjacencyList.containsKey(nodeData)) {
			throw new IllegalArgumentException("nodeData does not exist in the graph");
		}
		adjacencyList.remove(nodeData);
		// checkRep();
	}
	
	/**
	 * 
	 * @param parentNode, the parent node of the edge (the node the edge is coming from)
	 * @param childNode,  the child node of the edge (the node the edge is pointing to)
	 * @param edgeLabel,  the label of the edge being removed
	 * @requires parentNode != "" && childNode != "" && edgeLabel != "" &&
	 *           parentNode is in the graph
	 * @modifies adjacencyList
	 * @effects removes the edge from the parent node with the child node and label
	 */
	public void removeEdge(N parentNode, N childNode, E edgeLabel) {
		if (parentNode == null) {
			throw new IllegalArgumentException("parentNode cannot be null or empty");
		}
		if (childNode == null) {
			throw new IllegalArgumentException("childNode cannot be null or empty");
		}
		if (edgeLabel == null) {
			throw new IllegalArgumentException("edgeLabel cannot be null or empty");
		}
		if (!adjacencyList.containsKey(parentNode)) {
			throw new IllegalArgumentException("parentNode does not exist in the graph");
		}
		
		Map<N, List<E>> edgesFromParent = adjacencyList.get(parentNode);
		List<E> edgeList = edgesFromParent.get(childNode);
		edgeList.remove(edgeLabel);
        // checkRep();
	}
	
	/**
	 * 
	 * @param parentNode, the parent node of the edge (the node the edge is coming
	 *                    from)
	 * @param childNode,  the child node of the edge (the node the edge is pointing
	 *                    to)
	 * @requires parentNode != "" && childNode != "" && parentNode is in the graph
	 * @modifies adjacencyList
	 * @effects removes all edges from the parent node to the child node
	 */
	public void clearEdges(N parentNode, N childNode) {
		if (parentNode == null) {
			throw new IllegalArgumentException("parentNode cannot be null or empty");
		}
		if (childNode == null) {
			throw new IllegalArgumentException("childNode cannot be null or empty");
		}
		if (!adjacencyList.containsKey(parentNode)) {
			throw new IllegalArgumentException("parentNode does not exist in the graph");
		}
		if (!adjacencyList.get(parentNode).containsKey(childNode)) {
			throw new IllegalArgumentException("childNode does not exist in the graph");
		}
		adjacencyList.get(parentNode).get(childNode).clear();
		// checkRep();
	}
	
	/**
	 * Returns an iterator that represents all the nodes this Graph
	 *
	 * @requires adjecencyList != null
	 * @return Iterator<String> that represents all the nodes in the graph
	 */
	public Iterator<N> listNodes() {
		if (adjacencyList == null) {
			throw new IllegalArgumentException("adjacencyList cannot be null");
		}
		// checkRep();
		
		return adjacencyList.keySet().iterator();
	}
	
	/**
	 * Returns a map of all the children of the given node
	 *
	 * @param parentNode, the node to get the children of
	 * @requires parentNode != "" && parentNode is in the graph
	 * @return Map<N, List<E>> that represents all the children of the given node
	 */
	public Map<N, List<E>> listChildren(N parentNode) {
		if (parentNode == null) {
			throw new IllegalArgumentException("parentNode cannot be null or empty");
		}
		if (!adjacencyList.containsKey(parentNode)) {
			throw new IllegalArgumentException("parentNode does not exist in the graph");
		}
		Map<N, List<E>> edgesFromParent = adjacencyList.get(parentNode);
	    
	    
	    return edgesFromParent;
    }
	
	/**
	 * Returns a boolean indicating whether the given node is in the graph
	 * 
	 * @param parentNode, the parent node of the edge (the node the edge is coming from)
	 * @requires nodeData != ""
	 * @return boolean, true if the node is in the graph, false otherwise
	 */
	public boolean containsNode(N nodeData) {
		if (nodeData == null) {
			throw new IllegalArgumentException("nodeData cannot be null or empty");
		}
		// checkRep();
		return adjacencyList.containsKey(nodeData);
	}
	
	/**
	 * Returns a boolean indicating whether the given edge is in the graph
	 * 
	 * @param node1, the parent node of the edge (the node the edge is coming from)
	 * @param node2, the child node of the edge (the node the edge is pointing to)
	 * @requires node1 != "" && node2 != ""
	 * @return boolean, true if the edge is in the graph, false otherwise
	 */
	public boolean containsEdge(N node1, N node2) {
		if (node1 == null) {
			throw new IllegalArgumentException("node1 cannot be null or empty");
		}
		if (node2 == null) {
			throw new IllegalArgumentException("node2 cannot be null or empty");
		}
		if (!adjacencyList.containsKey(node1)) {
			throw new IllegalArgumentException("node1 does not exist in the graph");
		}
		// checkRep();
		return adjacencyList.get(node1).containsKey(node2);
	}
	
	
	/**
	 * Returns the first edge weight of the given edge
	 * 
	 * @param parentNode, the parent node of the edge (the node the edge is coming
	 *                    from)
	 * @param childNode,  the child node of the edge (the node the edge is pointing
	 *                    to)
	 * @requires parentNode != "" && childNode != "" && parentNode is in the graph
	 * @return E, the first edge weight of the given edge
	 */
	public E getFirstEdgeWeight(N parentNode, N childNode) {
		if (parentNode == null) {
			throw new IllegalArgumentException("parentNode cannot be null or empty");
		}
		if (childNode == null) {
			throw new IllegalArgumentException("childNode cannot be null or empty");
		}
		if (!adjacencyList.containsKey(parentNode)) {
			throw new IllegalArgumentException("parentNode does not exist in the graph");
		}
		if (!adjacencyList.get(parentNode).containsKey(childNode)) {
			throw new IllegalArgumentException("childNode does not exist in the graph");
		}
		// checkRep();
		return adjacencyList.get(parentNode).get(childNode).get(0);
	}
	
	/**
	 * Returns the last edge weight of the given edge
	 * 
	 * @param parentNode, the parent node of the edge (the node the edge is coming
	 *                    from)
	 * @param childNode,  the child node of the edge (the node the edge is pointing
	 *                    to)
	 * @requires parentNode != "" && childNode != "" && parentNode is in the graph
	 * @return E, the last edge weight of the given edge
	 */
	public E getLastEdgeWeight(N parentNode, N childNode) {
		if (parentNode == null) {
			throw new IllegalArgumentException("parentNode cannot be null or empty");
		}
		if (childNode == null) {
			throw new IllegalArgumentException("childNode cannot be null or empty");
		}
		if (!adjacencyList.containsKey(parentNode)) {
			throw new IllegalArgumentException("parentNode does not exist in the graph");
		}
		if (!adjacencyList.get(parentNode).containsKey(childNode)) {
			throw new IllegalArgumentException("childNode does not exist in the graph");
		}
		// checkRep();
		int last_index = adjacencyList.get(parentNode).get(childNode).size() - 1;
		return adjacencyList.get(parentNode).get(childNode).get(last_index);
	}
	
	/**
	 * Prints the graph in the following format: Node: parentNode Edges:
	 * childNode1(edgeLabel1), childNode2(edgeLabel2), ... Node: parentNode2 Edges:
	 * childNode3(edgeLabel3), childNode4(edgeLabel4), ...
	 */
	public void printGraph() {
        if (adjacencyList == null) {
            throw new IllegalArgumentException("adjacencyList cannot be null");
        }
        System.out.println("Graph:");

        // Iterate over each node in the adjacency list
        for (Map.Entry<N, Map<N, List<E>>> entry : adjacencyList.entrySet()) {
            N node = entry.getKey();
            Map<N, List<E>> edges = entry.getValue();

            // Print node
            System.out.print(node + ": ");
            System.out.println();

            System.out.print("Edges:\n");
            // Print edges
            for (Map.Entry<N, List<E>> edgeEntry : edges.entrySet()) {
                N childNode = edgeEntry.getKey();
                List<E> edgeLabels = edgeEntry.getValue();

                for (E edgeLabel : edgeLabels) {
                    System.out.print("\t" + childNode + "(" + edgeLabel + ")\n");
                }
            }

            System.out.println(); // Move to the next line for the next node
        }
    }
}
