package hw4;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

class GraphTest {

    private Graph<String, String> graph;

    @BeforeEach
    void setUp() {
        // Initialize the graph before each test
        graph = new Graph<>();
    }

    @Test
    void testAddNode() {
        graph.addNode("A");
        assertTrue(graph.containsNode("A"));
        
        assertThrows(IllegalArgumentException.class, () -> graph.addNode(null));
        
        assertThrows(IllegalArgumentException.class, () -> graph.addNode("A"));
    }

    @Test
    void testAddEdge() {
    	assertThrows(IllegalArgumentException.class, () -> graph.addEdge("", "B", "label"));
        assertThrows(IllegalArgumentException.class, () -> graph.addEdge("A", "", "label"));
        assertThrows(IllegalArgumentException.class, () -> graph.addEdge("A", "B", ""));
        
        graph.addNode("A");
        graph.addNode("B");
        // Generate a random label for the edge
        String edgeLabel = String.valueOf(new Random().nextInt(1000));
        graph.addEdge("A", "B", edgeLabel);
    }

    @Test
    void testRemoveNode() {
    	assertThrows(IllegalArgumentException.class, () -> graph.removeNode(""));
        assertThrows(IllegalArgumentException.class, () -> graph.removeNode("A"));
        
        graph.addNode("A");
        graph.removeNode("A");
        assertFalse(graph.containsNode("A"));
    }

    @Test
    void testRemoveEdge() {
    	assertThrows(IllegalArgumentException.class, () -> graph.removeEdge("", "B", "label"));
        assertThrows(IllegalArgumentException.class, () -> graph.removeEdge("A", "", "label"));
        assertThrows(IllegalArgumentException.class, () -> graph.removeEdge("A", "B", ""));
        assertThrows(IllegalArgumentException.class, () -> graph.removeEdge("A", "B", "label"));
        
        graph.addNode("A");
        graph.addNode("B");
        // Generate a random label for the edge
        String edgeLabel = String.valueOf(new Random().nextInt(1000));
        graph.addEdge("A", "B", edgeLabel);
        graph.removeEdge("A", "B", edgeLabel);
    }

    @Test
    void testListNodes() {
        graph.addNode("A");
        graph.addNode("B");
        Iterator<String> nodes = graph.listNodes();
        assertTrue(nodes.hasNext());
        assertEquals("A", nodes.next());
        assertEquals("B", nodes.next());
        assertFalse(nodes.hasNext());
    }

    @Test
    void testListChildren() {
        // Test listing children when the graph is empty
        assertThrows(IllegalArgumentException.class, () -> graph.listChildren("A"));

        // Test listing children for a node with no children
        graph.addNode("A");
        Map<String, List<String>> edgesMap = graph.listChildren("A");
        assertTrue(edgesMap.isEmpty());

        // Test listing children for a node with children
        graph.addNode("B");
        graph.addNode("C");
        graph.addEdge("A", "A", "label1");
        graph.addEdge("A", "B", "label1");
        graph.addEdge("A", "B", "label2");
        graph.addEdge("A", "B", "label3");
        graph.addEdge("A", "C", "label1");
        
              
        List<String> expectedChildren = Arrays.asList("A(label1)", "B(label1)", "B(label2)", "B(label3)", "C(label1)");
		for (String node : edgesMap.keySet()) {
			List<String> edges = edgesMap.get(node);
			for (String edge : edges) {
				assertTrue(expectedChildren.contains(node + "(" + edge + ")"));
			}
		}
       }

    @Test
    void testContainsNode() {
    	assertThrows(IllegalArgumentException.class, () -> graph.containsNode(null));
        graph.addNode("A");
        assertTrue(graph.containsNode("A"));
        assertFalse(graph.containsNode("B"));
    }

    @Test
    void testGetEdgeLabel() {

        graph.addNode("A");
        graph.addNode("B");
        // Generate a random label for the edge
        String edgeLabel = String.valueOf(new Random().nextInt(1000));
        graph.addEdge("A", "B", edgeLabel);
    }
}
