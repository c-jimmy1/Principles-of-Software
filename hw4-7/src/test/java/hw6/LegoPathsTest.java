package hw6;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import static org.junit.Assert.assertEquals;

public class LegoPathsTest {
	private LegoPaths legoPaths;
	
	@BeforeEach
	public void setUp() {
        legoPaths = new LegoPaths();
    }
	
	@Test
	public void testCreateNewGraph() throws IOException {
		String filename = "data/lego1960.csv";
		legoPaths.createNewGraph(filename);
	}
	
	@Test
	public void testfindPath_custom1() {
		String filename = "data/hw6_lego_parts.csv";
		legoPaths.createNewGraph(filename);
		String path = legoPaths.findPath("Brick 1x1", "Plate 2x4");
		assertEquals("path from Brick 1x1 to Plate 2x4:\n"
				+ "Brick 1x1 to Plate 2x4 with weight 0.500\n"
				+ "total cost: 0.500\n", path);
		
		String path2 = legoPaths.findPath("Brick 1x1", "Brick 1x1");
		assertEquals("path from Brick 1x1 to Brick 1x1:\n"
				+ "total cost: 0.000\n", path2);
	}
	
	@Test
	public void testfindPathLego1500() {
		String filename = "data/hw6_lego_1500pts.csv";
		legoPaths.createNewGraph(filename);
		String path = legoPaths.findPath("Fence - Translucent - Plastic - Combat", "Plant - Brown - Fabric - Entry");
		assertEquals("path from Fence - Translucent - Plastic - Combat to Plant - Brown - Fabric - Entry:\n"
				+ "Fence - Translucent - Plastic - Combat to Axle - Orange - Plastic - Mechanical with weight 1.000\n"
				+ "Axle - Orange - Plastic - Mechanical to Axle - Bronze - Fabric - Decorative with weight 1.000\n"
				+ "Axle - Bronze - Fabric - Decorative to Axle - Silver - Fabric - Transportation with weight 1.000\n"
				+ "Axle - Silver - Fabric - Transportation to Plant - Brown - Fabric - Entry with weight 1.000\n"
				+ "total cost: 4.000\n", path);
		
		String path2 = legoPaths.findPath("Fence - Translucent - Plastic - Combat", "Plant - Dirt - Fabric - Entry");
		assertEquals("unknown part Plant - Dirt - Fabric - Entry\n", path2);
		
		String path3 = legoPaths.findPath("Door - Beige - Plastic - Functional", "Axle - Maroon - Fabric - Mechanical");
		assertEquals("path from Door - Beige - Plastic - Functional to Axle - Maroon - Fabric - Mechanical:\n"
				+ "no path found\n", path3);
		
	}
	
	@Test
	public void testfindPathLego500() {
        String filename = "data/hw6_lego_small.csv";
        legoPaths.createNewGraph(filename);
        String path = legoPaths.findPath("Plate - Blue - Plastic - Basic", "Beam - Tan - Plastic - Building");
        assertEquals("path from Plate - Blue - Plastic - Basic to Beam - Tan - Plastic - Building:\n"
        		+ "Plate - Blue - Plastic - Basic to Tile - Yellow - Plastic - Decorative with weight 0.111\n"
        		+ "Tile - Yellow - Plastic - Decorative to Beam - Tan - Plastic - Building with weight 0.100\n"
        		+ "total cost: 0.211\n", path);
        
        String path2 = legoPaths.findPath("Window - Transparent - Plastic - Accessory", "Vehicle - Blue - Plastic - Transportation");
		assertEquals(
				"path from Window - Transparent - Plastic - Accessory to Vehicle - Blue - Plastic - Transportation:\n"
				+ "Window - Transparent - Plastic - Accessory to Vehicle - Blue - Plastic - Transportation with weight 0.111\n"
				+ "total cost: 0.111\n", path2);
		
		String path3 = legoPaths.findPath("Plate - Blue - Fabric - Trail", "Vehicle - Blue - Plastic - Transportation");
		assertEquals("unknown part Plate - Blue - Fabric - Trail\n",  path3);
		
		String path4 = legoPaths.findPath("Vehicle - Blue - Plastic - Housing", "Plate - Blue - Fabric - Trail");
		assertEquals("unknown part Vehicle - Blue - Plastic - Housing\n"
				+ "unknown part Plate - Blue - Fabric - Trail\n",  path4);
	}

}