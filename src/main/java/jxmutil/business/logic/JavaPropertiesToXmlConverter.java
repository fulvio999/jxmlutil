package jxmutil.business.logic;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;
import net.sf.json.xml.XMLSerializer;


/**
 * Create a the xml version of a java.util.Properties
 * 
 *
 */
public class JavaPropertiesToXmlConverter
{
	
	/**
	 * Constructor
	 */
	public JavaPropertiesToXmlConverter(){
		
	}
	
	/**
	 * Convert a Java.util.Properties to XML
	 * 
	 * @param inputFilePath The file to convert
	 * @param outFile The file where write the result
	 * @return the xml representing the properties in input
	 * @throws IOException
	 */
    public String convert(String inputFilePath, String outFile) throws IOException
    {	
    	FileReader fr = new FileReader(inputFilePath);
    	
    	Properties props = new Properties();
    	props.load(fr);
  
    	// save the properties file as XML content to the chosen output file (if any)
    	if(!outFile.equalsIgnoreCase(""))
	    {	      		
    		OutputStream os = new FileOutputStream(outFile);
    		props.storeToXML(os, "","UTF-8"); //replace empty String with a String comment to insert as <comment>Support Email</comment>
    		
    		os.close();
  	    }
    	
    	// convert the xml to String to show it in the text-area
    	ByteArrayOutputStream buf = new ByteArrayOutputStream();     	
    	props.storeToXML(buf, "","UTF-8");
    	
    	return new String(buf.toByteArray());
 
    }
}