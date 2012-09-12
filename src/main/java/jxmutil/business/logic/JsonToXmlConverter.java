package jxmutil.business.logic;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;
import net.sf.json.xml.XMLSerializer;


/**
 * Convert a JSON object to a XML
 * The conversion is performed using the library available at:
 * http://json-lib.sourceforge.net/index.html
 *
 */
public class JsonToXmlConverter {

	/**
	 * Constructor
	 */
	public JsonToXmlConverter() {
		
	}
	
	/**
	 * Convert a JSON to XML
	 * 
	 * @param jsonFilePath The path at the file containing the json to convert in xml 	 
	 * @throws FileNotFoundException 
	 */
	public String convert(String jsonFilePath) throws Exception {			
		
		// Read the json form the input file
		FileReader fr = new FileReader(jsonFilePath);
		BufferedReader br = new BufferedReader(fr);

		StringBuilder sb = new StringBuilder();
		String startLine = null;
  
		while ((startLine = br.readLine()) != null){
			sb.append(startLine+"\n");
		}		
        
        XMLSerializer serializer = new XMLSerializer();
        JSON json = JSONSerializer.toJSON( sb.toString() ); 
        
        String xml = serializer.write(json); 
        
        return xml;		
	}

}
