package hw5;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach; // Changed from BeforeAll to BeforeEach
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import static org.junit.Assert.assertEquals;

public class ProfessorPathsTest {
    private ProfessorPaths professorPaths;
    
    @BeforeEach // Changed from BeforeAll to BeforeEach
    public void setUp() {
        // Set up the test environment
        professorPaths = new ProfessorPaths();
    }
    
    @Test
    public void testCreateNewGraph() throws IOException {       
        String filename = "data/courses.csv";
        professorPaths.createNewGraph(filename);
        // professorPaths.PrintGraph();
        assertTrue(professorPaths.containsNode("Mohammed J. Zaki"));
        assertTrue(professorPaths.containsNode("Wilfredo Colon"));
        assertTrue(professorPaths.containsNode("David Eric Goldschmidt"));
        assertTrue(professorPaths.containsNode("Michael Joseph Conroy"));
        assertFalse(professorPaths.containsNode("Jimmy Chen"));
    }
    
    @Test
    public void testfindPath() {
        String filename = "data/courses.csv";
        professorPaths.createNewGraph(filename);
        // professorPaths.PrintGraph();
        String result = professorPaths.findPath("Mohammed J. Zaki", "Wilfredo Colon");
        assertEquals("path from Mohammed J. Zaki to Wilfredo Colon:\nMohammed J. Zaki to David Eric Goldschmidt via "
        		+ "CSCI-2300\nDavid Eric Goldschmidt to Michael Joseph Conroy via CSCI-4430\nMichael Joseph Conroy to Alan R Cutler via CHEM-1200\n"
        		+ "Alan R Cutler to Wilfredo Colon via CHEM-1100\n", result);
        //System.out.println(result);
        
        String result2 = professorPaths.findPath("David Eric Goldschmidt", "Hugh Johnson");
        assertEquals("path from David Eric Goldschmidt to Hugh Johnson:\nno path found\n", result2);
        //System.out.println(result2); 
        
        String result3 = professorPaths.findPath("Donald Knuth", "Malik Magdon-Ismail");
        assertEquals("unknown professor Donald Knuth\n", result3);
        //System.out.println(result3);
        
        String result4 = professorPaths.findPath("Donald Knuth", "Brian Kernighan");
        assertEquals("unknown professor Donald Knuth\nunknown professor Brian Kernighan\n", result4);
        //System.out.println(result4);
        
        String result5 = professorPaths.findPath("Barbara Cutler", "Barbara Cutler");
        assertEquals("path from Barbara Cutler to Barbara Cutler:\n", result5);
        
        String result6 = professorPaths.findPath("Donald Knuth", "Donald Knuth");
        assertEquals("unknown professor Donald Knuth\n", result6);        
    }
    
    @Test
    public void test_Courses10000() {
    	String filename = "data/courses_10000.csv";
    	
    	professorPaths.createNewGraph(filename);
    	
    	assertTrue(professorPaths.containsNode("Ronald Smith"));
    	assertTrue(professorPaths.containsNode("Alexandra Johnson"));
    	assertTrue(professorPaths.containsNode("Richard Vargas"));
    	
    	String result = professorPaths.findPath("Ronald Smith", "Alexandra Johnson");

    	assertEquals(
				"path from Ronald Smith to Alexandra Johnson:\n"
				+ "Ronald Smith to Adam Johnson via MT-1700\n"
				+ "Adam Johnson to Alexandra Johnson via MED-300\n", result);
		
		String result2 = professorPaths.findPath("Travis Allen", "Joseph Phillips");
		assertEquals("path from Travis Allen to Joseph Phillips:\n"
				+ "Travis Allen to Aaron Hansen via CS-3100\n"
				+ "Aaron Hansen to Joseph Phillips via MT-1700\n", result2);
	}


    @Test
    public void test_lesscourses_lessnames() {
    	String filename = "data/lesscourses_lessnames_150.csv";
    	
    	professorPaths.createNewGraph(filename);
    	
    	assertTrue(professorPaths.containsNode("Frances Lee"));
    	assertTrue(professorPaths.containsNode("Harry Johnson"));
    	assertTrue(professorPaths.containsNode("John Torres"));
    	
    	String result = professorPaths.findPath("Harry Johnson", "John Torres");
    	// System.out.println(result);
    	assertEquals("path from Harry Johnson to John Torres:\n"
    			+ "Harry Johnson to Billy Becker via ENG-3500\n"
    			+ "Billy Becker to John Torres via CS-5200\n", result);
    	
    	String result2 = professorPaths.findPath("Natasha Mays", "Emma White");
		assertEquals("path from Natasha Mays to Emma White:\n"
				+ "no path found\n", result2);
    	}
    
    
    
    @Test
    public void test_lesscourses_morenames() {
    	// This test case will always all more names than courses
    	String filename = "data/lesscourses_morenames_150.csv";
    	
    	professorPaths.createNewGraph(filename);
    	
    	assertTrue(professorPaths.containsNode("Andrea Graham"));
    	assertTrue(professorPaths.containsNode("Samantha Tucker"));
    	
    	String result = professorPaths.findPath("Samantha Tucker", "Andrea Graham");
    	// System.out.println(result);
		assertEquals("path from Samantha Tucker to Andrea Graham:\n"
				+ "no path found\n", result);
		
		String result2 = professorPaths.findPath("John E. Mitchell", "Albert Lee Chun");
		// System.out.println(result2);
		assertEquals("unknown professor John E. Mitchell\n"
				+ "unknown professor Albert Lee Chun\n", result2);
    	
		String result3 = professorPaths.findPath("Jordan Roberts PhD", "Rebecca Morris");
		assertEquals("path from Jordan Roberts PhD to Rebecca Morris:\n"
				+ "Jordan Roberts PhD to Rebecca Morris via ENG-5700\n", result3);
    }
  
    
    @Test
    public void test_morecourses_lessnames() {
    	String filename = "data/morecourses_lessnames_150.csv";
    	
    	professorPaths.createNewGraph(filename);
    	
    	assertTrue(professorPaths.containsNode("Kathryn Murray"));
    	assertTrue(professorPaths.containsNode("Michael Ortiz"));
    	
    	String result = professorPaths.findPath("Michael Ortiz", "Kathryn Murray");
    	// System.out.println(result);
		assertEquals("path from Michael Ortiz to Kathryn Murray:\n"
				+ "Michael Ortiz to Jonathan Barrera III via BTECH-3200\n"
				+ "Jonathan Barrera III to Kathryn Murray via CS-1200\n", result);
		
		String result2 = professorPaths.findPath("Nicholas Blair", "Michael Ramirez");
		assertEquals("path from Nicholas Blair to Michael Ramirez:\n"
				+ "Nicholas Blair to Heidi Riley via MUS-4300\n"
				+ "Heidi Riley to Michael Ramirez via MED-3500\n", result2);
		
		String result3 = professorPaths.findPath("Nicholas Blake", "Samantha Tucker");
		assertEquals("unknown professor Nicholas Blake\n"
				+ "unknown professor Samantha Tucker\n", result3);
    }
}
