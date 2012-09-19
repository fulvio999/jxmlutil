package jxmutil.business.logic;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;



/**
 * Core class that execute an XSLT transformation at the input xml file using the APACHE XALAN library.
 * 
 */
public class ApplyXsltTransformation {
	
	/**
	 * Constructor
	 * @throws IOException 
	 */
	public ApplyXsltTransformation() throws IOException {

	}
	

	/**
	 * Apply an XSLT Transformation at the input String representing a SOAP message, so that the response can be include in the
	 * 
	 * @param xmlToTransform The XML file, as a String, to transform
	 * @param xslFileToLoad The XSL file to load for the transformation
	 * @throws IOException 
	 *
	 */
	public String execute(String xmlToTransform, String xslFileToLoad) throws IOException
	{
		
		/*
		 * NOTE: In the main-class (ie StartApp.java) is set the system property to specify the right XSLT processor (ie xalan)
		 * instead of the jdk default implementation 
		 *  
		 * */	
	    
	    // The buffer where write the transformed message to return
		ByteArrayOutputStream out = new ByteArrayOutputStream();
			    
		try {
			
			TransformerFactory tFactory = TransformerFactory.newInstance();
			
			// Load the xsl
			Transformer transformer = tFactory.newTransformer(new StreamSource(new File(xslFileToLoad)));			
			
			//Apply the associated Templates object to "inputStreamProxyResponse" and write the output to "outputStreamClientResponse"
			transformer.transform(new StreamSource(new File(xmlToTransform)), new StreamResult(out));
						
		    //System.out.println("Transformed MSG: "+out.toString());			
			
		}catch (Exception e) {
			
			return "Error, check your input: "+e.toString();
		}
		return out.toString();
		
	}


}
