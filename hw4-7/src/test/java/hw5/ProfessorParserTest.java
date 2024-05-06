package hw5;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.*;

public class ProfessorParserTest {

    @Test
    public void testReadData() {
        // Prepare test data
        Map<String, Set<String>> profsTeaching = new HashMap<>();
        Set<String> profs = new HashSet<>();
        String filename = "data/courses.csv";
        
        try {
            ProfessorParser.readData(filename, profsTeaching, profs);

            // Verify the results
            assertEquals(2254, profs.size());
            assertEquals(2374, profsTeaching.size());

            // Test specific professor-course mappings
            assertTrue(profsTeaching.containsKey("ECSE-2010"));
            assertTrue(profsTeaching.get("ECSE-2010").contains("A Bruce Carlson"));

            assertTrue(profsTeaching.containsKey("COMM-1510"));
            assertTrue(profsTeaching.get("COMM-1510").contains("A. Michael DelPrete"));

            assertTrue(profsTeaching.containsKey("STSO-2400"));
            assertTrue(profsTeaching.get("STSO-2400").contains("Aalok Khandekar"));

            assertTrue(profsTeaching.containsKey("ENGR-2020"));
            assertTrue(profsTeaching.get("ENGR-2020").contains("Abby J. Kinchy"));

        } catch (IOException e) {
            fail("IOException thrown unexpectedly: " + e.getMessage());
        }
    }
}