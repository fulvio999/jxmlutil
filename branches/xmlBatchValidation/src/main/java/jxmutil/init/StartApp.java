package jxmutil.init;

import jxmutil.gui.JxmlutilGui;

/**
 * 
 * Entry point for the application
 *
 */
public class StartApp {

	/**
	 * Entry point of the application
	 */
	public static void main(String[] args) 
	{		
		
		/* ************ Necessary to use Apache XLAN XSLT Transaction Factory and NOT the which one provided by the JDK ************
	   
		 ( See: http://xml.apache.org/xalan-j/usagepatterns.html#plug 
		        http://www.caucho.com/resin-3.0/xml/jaxp.xtp )
		 The javax.xml.transform.TransformerFactory system property setting determines the actual class to instantiate
		 
		 JAXP is a standard interface which supports pluggable XML and XSL implementations
		 JAXP 1.1 defines a specific lookup procedure to locate an appropriate XSLT processor
		 		
		 The steps that JAXP performs when attempting to locate a factory:
		   1 Use the value of the javax.xml.transform.TransformerFactory system property if it exists.
		   2 If JRE/lib/jaxp.properties exists, then look for a javax.xml.transform.TransformerFactory=ImplementationClass entry in that file.
		   3 Use a JAR file service provider to look for a file called META-INF/services/javax.xml.transform.TransformerFactory in any JAR file on the CLASSPATH.
		   4 Use the default TransformerFactory instance.

		   For example: to set the SAX parser to use must be set the system property "javax.xml.parsers.SAXParserFactory"
		   or to specify the XSLT processor must be set the property "javax.xml.transform.TransformerFactory" providing the right implementation
		*/
		
		// set xslt processor to Apache XALAN
		System.setProperty("javax.xml.transform.TransformerFactory", "org.apache.xalan.processor.TransformerFactoryImpl");	
		
		// set the XPATH processor to Apache XALAN
		System.setProperty("javax.xml.xpath.XPathFactory", "org.apache.xpath.jaxp.XPathFactoryImpl");
		
		//********************************* Set Apache XERCES as xml validator and parser *****************************************
		
		// set the DOM xml parser to Apache XERCES
		System.setProperty("javax.xml.parsers.DocumentBuilderFactory", "org.apache.xerces.jaxp.DocumentBuilderFactoryImpl");
		
		// set the SAX xml parser to Apache XERCES
		System.setProperty("javax.xml.parsers.SAXParserFactory", "org.apache.xerces.jaxp.SAXParserFactoryImpl");

		// set xerces as XML validator
		System.setProperty("javax.xml.validation.SchemaFactory:http://www.w3.org/2001/XMLSchema","org.apache.xerces.jaxp.validation.XMLSchemaFactory");

		
		//***********************************************************************************************************************************
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			
            public void run() {
            	JxmlutilGui jxmlutilGui = new JxmlutilGui();
            }
        });
	}

	

}
