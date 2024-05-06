package hw6;

import java.util.*;
import hw4.Graph;

public class DijkstraAlgorithm<N> {
	
	/**
	 * @param: startNode The starting node of the path
	 * @param: endNode   The ending node of the path
	 * @param: graph     The graph to search for the path
	 * @requires: startNode != null, endNode != null, graph != null
	 * @modifies: None
	 * @effects: Finds the shortest path from the startNode to the endNode in the
	 *           graph
	 * @returns: A list of maps, where each map contains a node and the weight of
	 *           the edge to the next node
	 */
	public List<Map<N, Double>> Dijkstra(N startNode, N endNode, Graph<N, Double> graph) {
        PriorityQueue<List<Map<N, Double>>> active = new PriorityQueue<>(Comparator.comparingDouble(this::calcTotalWeight));
        Set<N> finished = new HashSet<>();
        
        List<Map<N, Double>> startPath = new ArrayList<>();
        Map<N, Double> startEdge = new HashMap<>();
        startEdge.put(startNode, 0.0);
        startPath.add(startEdge);
        active.add(startPath);
        
		while (!active.isEmpty()) {
			List<Map<N, Double>> path = active.poll();
			N lastNode = path.get(path.size() - 1).keySet().iterator().next();
			if (lastNode.equals(endNode)) {
				return path;
			}
			if (!finished.contains(lastNode)) {
				finished.add(lastNode);
				Map<N, List<Double>> neighbors = graph.listChildren(lastNode);
				Map<N, List<Double>> sortedNeighbors = new TreeMap<>(Comparator.comparing(Object::toString));
				sortedNeighbors.putAll(neighbors);


				for (N neighbor : sortedNeighbors.keySet()) {
					if (!finished.contains(neighbor)) {
						double weight = graph.getLastEdgeWeight(lastNode, neighbor);
						List<Map<N, Double>> newPath = new ArrayList<>(path);
						Map<N, Double> newEdge = new HashMap<>();
						newEdge.put(neighbor, weight);
						newPath.add(newEdge);
						active.add(newPath);
					}
				}
			}
		}
		return null;
        
	}
	
	/**
     * @param: path The path to calculate the weight of
     * @requires: path != null
     * @modifies: None
     * @effects: Calculates the total weight of the path
     * @returns: The total weight of the path
     */
	public double calcTotalWeight(List<Map<N, Double>> path) {
        double totalWeight = 0.0;
        for (Map<N, Double> edge : path) {
            for (N node : edge.keySet()) {
                totalWeight += edge.get(node);
            }
        }
        return totalWeight;
    }
}