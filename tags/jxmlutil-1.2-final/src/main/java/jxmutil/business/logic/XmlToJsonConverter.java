package jxmutil.business.logic;

import java.io.BufferedReader;
import java.io.FileReader;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;
import net.sf.json.xml.XMLSerializer;


/**
 * Convert an XML to a JSON Object
 * The conversion is performed using the library available at:
 * http://json-lib.sourceforge.net/index.html
 *
 */
public class XmlToJsonConverter {

	/**
	 * Constructor
	 */
	public XmlToJsonConverter() {
		
	}

	/**
	 * OK
	 * Convert XML to JSON format
	 * @param xmlFilePath The path at the file containing the xml to convert in json 
	 * @param outFile The file where write result	 
	 */
	public String convert(String xmlFilePath) throws Exception
	{		
	    // Read the input xml file
		FileReader fr = new FileReader(xmlFilePath);
		BufferedReader br = new BufferedReader(fr);

		StringBuilder sb = new StringBuilder();
		String startLine = null;
  
		while ((startLine = br.readLine()) !=null){
			sb.append(startLine+"\n");
		}
	    
	    XMLSerializer xmlSerializer = new XMLSerializer(); 
        JSON json = xmlSerializer.read(sb.toString());       

        // the '2' in argument indicates to print a formatted json     
        return json.toString(2);		
	}
}
