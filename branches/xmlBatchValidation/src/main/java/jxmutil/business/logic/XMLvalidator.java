
package jxmutil.business.logic;

import java.io.File;
import java.io.IOException;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

/**
 * Core class that execute the validation of a provided XML file against a provided XSD
 * The validation is performed using Apache XERCES library
 *
 */
public class XMLvalidator {

	/**
	 * Constructor
	 */
	public XMLvalidator() {
		
	}
	
	
	/**
	 * Perform the xml validation against an XSD file
	 * 
	 * @param xmlFilePath The path at the xml file to be validated
	 * @param xsdFilePath The path at the xsd file to used for validation
	 * @return true if the xml file is valid
	 * @throws SAXException If the xml is not valid
	 * @throws IOException If the xml is not valid
	 */
	public boolean isValid(String xmlFilePath, String xsdFilePath) throws Exception
	{			
			//debug
		    //System.out.println("- xml file: "+xmlFilePath);
		    //System.out.println("- xsd file: "+xsdFilePath);
		
			/* NOTE: the setting of xerces as default xml validator is done in the java class "StartApp" */    
		
			File schemaLocation = new File(xsdFilePath);
			
			// Parse the document you want to check.
			Source source = new StreamSource(xmlFilePath);		
			
			SchemaFactory sf = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
			
			Schema schema = sf.newSchema(schemaLocation);
			Validator validator = schema.newValidator();
			
			// NOTE: some validators features can be customized using the dedicated set method of the Validator object
			
			validator.validate(source);			
				
			return true;
 	}

}
