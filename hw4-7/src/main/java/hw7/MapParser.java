package hw7;

import java.util.*;
import java.io.*;

public class MapParser {
	/**
	 * @param: filename 	The path to a "CSV" file that contains the "building",
	 *                  		"id","x","y"
	 * @param: Data    		The Map that stores parsed <id, [building, x, y]>
	 * @modifies: Data		Adds parsed data to Data
	 * @throws: IOException if file cannot be read or file not a CSV file following
	 *                      	the proper format.
	 * @returns: None
	 */
	public static void readNodeData(String filename, Map<String, List<String>> Data) 
			throws IOException {
		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
			String line = null;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts == null) {
                    throw new IOException("File " + filename + " not a CSV file.");
                }
                List<String> node = new ArrayList<String>();
				for (int i = 0; i < parts.length; i++) {
					if (i != 1) {
						node.add(parts[i]);
					}
				}
                Data.put(parts[1], node);
            }
		}
		
	}
	
	/**
	 * @param: filename 	 	The path to a "CSV" file that contains the "id1", "id2" pairs
	 * @param: Data       		The List that stores parsed <id1, id2> pairs;
	 * @modifies: Data
	 * @throws: IOException 	if file cannot be read or file not a CSV file following the proper format.
	 * @returns: None
	 */
	public static void readEdgeData(String filename, List<List<String>> Data) 
	        throws IOException { 
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line = null;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(",");
				if (parts == null) {
                    throw new IOException("File " + filename + " not a CSV file.");
                }
				List<String> node = new ArrayList<String>();
				for (String part : parts) {
					node.add(part);
				}
				Data.add(node);
			}
        }
    }
		
//	public static void main(String[] arg) {
//		String file = arg[0];
//		String file2 = arg[1];
//		try {
//			Map<String, List<String>> NodeData = new HashMap<String, List<String>>();
//			readNodeData(file, NodeData);
//			for (String key : NodeData.keySet()) {
//				System.out.println(key + " : " + NodeData.get(key));
//			}
//			
//			List<List<String>> EdgeData = new ArrayList<List<String>>();
//			readEdgeData(file2, EdgeData);
//			for (List<String> edge : EdgeData) {
//				System.out.println(edge);
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
}

