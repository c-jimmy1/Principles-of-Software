package hw4;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

class GraphWrapperTest {
    private GraphWrapper graphWrapper;

    @BeforeEach
    void setUp() {
        // Initialize a new GraphWrapper object before each test
        graphWrapper = new GraphWrapper();
    }

    @Test
    void testAddNode() {
        // Test adding a node to the graph
        graphWrapper.addNode("A");
        Iterator<String> nodesIterator = graphWrapper.listNodes();
        assertTrue(nodesIterator.hasNext());
        assertEquals("A", nodesIterator.next());
    }

    @Test
    void testAddEdge() {
        // Test adding an edge to the graph
        graphWrapper.addNode("A");
        graphWrapper.addNode("B");
        graphWrapper.addEdge("A", "B", "5");
        // Check if the edge is added correctly
        Iterator<String> childrenIterator = graphWrapper.listChildren("A");
        assertTrue(childrenIterator.hasNext());
        assertEquals("B(5)", childrenIterator.next());
    }

    @Test
    void testListNodes() {
        // Test listing nodes when the graph is empty
        Iterator<String> nodesIterator = graphWrapper.listNodes();
        assertFalse(nodesIterator.hasNext());

        // Test listing nodes when the graph has some nodes
        graphWrapper.addNode("A");
        graphWrapper.addNode("B");
        nodesIterator = graphWrapper.listNodes();
        assertTrue(nodesIterator.hasNext());
        assertEquals("A", nodesIterator.next());
        assertTrue(nodesIterator.hasNext());
        assertEquals("B", nodesIterator.next());
        assertFalse(nodesIterator.hasNext());
    }

    @Test
    void testListChildren() {
        // Test listing children when the graph is empty
        assertThrows(IllegalArgumentException.class, () -> graphWrapper.listChildren("A"));

        // Test listing children for a node with no children
        graphWrapper.addNode("A");
        Iterator<String> childrenIterator = graphWrapper.listChildren("A");
        assertFalse(childrenIterator.hasNext());

        // Test listing children for a node with children
        graphWrapper.addNode("B");
        graphWrapper.addNode("C");
        graphWrapper.addEdge("A", "A", "label1");
        graphWrapper.addEdge("A", "B", "label1");
        graphWrapper.addEdge("A", "B", "label2");
        graphWrapper.addEdge("A", "B", "label3");
        graphWrapper.addEdge("A", "C", "label1");
        
              
        childrenIterator = graphWrapper.listChildren("A");
        
        List<String> expectedChildren = Arrays.asList("A(label1)", "B(label1)", "B(label2)", "B(label3)", "C(label1)");
        List<String> actualChildren = new ArrayList<>();
        
        
        while(childrenIterator.hasNext()) {
            actualChildren.add(childrenIterator.next());
        }
        

        assertTrue(actualChildren.containsAll(expectedChildren) && expectedChildren.containsAll(actualChildren));
    }


}
