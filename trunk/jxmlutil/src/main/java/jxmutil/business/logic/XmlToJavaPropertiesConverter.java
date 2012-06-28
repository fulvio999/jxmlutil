package jxmutil.business.logic;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Properties;


/**
 * Read an xml file and convert it in a java.util.properties 
 *
 */
public class XmlToJavaPropertiesConverter {
	
	/**
	 * Constructor
	 */
	public XmlToJavaPropertiesConverter(){
		
	}

	/**
	 * 
	 * @param inputFilePath The xml file to convert
	 * @param outFile A .properties file where write the conversion result
	 * @return
	 * @throws IOException
	 */
	public String convert(String inputFilePath, String outFile) throws IOException
    {	
    	Properties props = new Properties();

    	InputStream is = new FileInputStream(inputFilePath);
    	//load the xml file into properties format
    	props.loadFromXML(is);     	
    	
    	ByteArrayOutputStream buf = new ByteArrayOutputStream();     
    	props.store(buf, "");  
    	
	    // Save to output file if the use has chosen it
    	if(!outFile.equalsIgnoreCase(""))
	    {	      			    	
	    	 File f = new File(outFile);
	    	
	    	 if (f.exists())
				f.delete();
	    		
	    	 if(f.createNewFile())
	    	 {
	    		FileWriter fw = new FileWriter(outFile);
				BufferedWriter bw  = new BufferedWriter(fw);
				PrintWriter pw = new PrintWriter(bw);
			    pw.print(new String(buf.toByteArray()));
				
				pw.close();
				bw.close();
				fw.close();	      						
	    	 }
  	     }
     	
    	// The String to show in the text area
    	return new String(buf.toByteArray());  
    }
	
}
