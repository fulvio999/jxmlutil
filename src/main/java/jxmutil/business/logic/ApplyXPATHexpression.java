package jxmutil.business.logic;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 * 
 * 
 *
 */
public class ApplyXPATHexpression 
{
	
	public ApplyXPATHexpression(){
		
	}
	
	public String execute(String XMLfileSource, String destinationFile, String xpathExpression, boolean showNodeNameChecked, boolean showNodeValueChecked, boolean shownodeTextContentChecked)
	{

		boolean showNodeName = false;
		boolean showNodeValue = false;
		boolean shownodeTextContent = false;
		
		if(showNodeNameChecked){
			//System.out.println("node name checked");
			showNodeName = true;
		}	
		if(showNodeValueChecked){
			//System.out.println("node value checked");
			showNodeValue = true;
		}	
		if(shownodeTextContentChecked){
			//System.out.println("node text content checked");
			shownodeTextContent = true;
		}	
		
		//System.out.println("File input: "+fileXMLsorgente);
		//System.out.println("xpath exp:  "+xpathExpression);
		File fileSorg = new File(XMLfileSource); 

			try {
				
				DocumentBuilderFactory domFactory =  DocumentBuilderFactory.newInstance();
			    domFactory.setNamespaceAware(true);
			    
			    DocumentBuilder builder = domFactory.newDocumentBuilder();
			    
			    Document doc = builder.parse(fileSorg);			    
			    XPath xpath = XPathFactory.newInstance().newXPath();			    
			   
			    XPathExpression expr =  xpath.compile(xpathExpression);
			    
			    /*
			     * Set the expected node type returned by the query: a set of node, if we use some xpath function (eg count(...) the returned type would be
			     * XPathConstants.NUMBER)
			     *			    
			     * Method .getNodeValue():
			     * like getTextContent() but don't convert into a String,is necessary put the XPATH 'text()' function at the end of the expression
		         *
		 		 * Method .getNodeName():
			     * return the tag name (eg <pippo> return pippo)
				 *
    	         * Method .getTextContent():
    	         * return the content of the tag (eg <pippo>33</pippo> return 33. Inside an xpath expression is not necessary put the 'text()' function for String convertion
			     * 
			     */
			    Object result = expr.evaluate(doc, XPathConstants.NODESET);
			    NodeList nodes = (NodeList) result;
			    
			    StringBuffer sb = new StringBuffer();
			    
			    for (int i = 0; i < nodes.getLength(); i++)
			    {
			    	//-- Depends on the checkbox selected, decide which value retrieve from the node
			    	if(showNodeName)
			    		sb.append(nodes.item(i).getNodeName()+"\n");
			    	
			    	if(showNodeValue)
			    		sb.append(nodes.item(i).getNodeValue()+"\n");
			    	
			    	if(shownodeTextContent)
			    		sb.append(nodes.item(i).getTextContent()+"\n");
			    }
			    
			    //If the user has chosen also write to an out file
			    if(!destinationFile.equalsIgnoreCase("")){
			    	//System.out.println("Writing to out file");
			    	
			    	File f = new File(destinationFile);
			    	
			    	if (f.exists())
						f.delete();
			    		
			    	if(f.createNewFile())
			    	{
			    		FileWriter fw = new FileWriter(destinationFile);
						BufferedWriter bw  = new BufferedWriter(fw);
						PrintWriter pw = new PrintWriter(bw);
						
						pw.print(sb.toString());						
						pw.close();
						bw.close();
						fw.close();
						
			    	}
		  	    }
			    
			    
			    return sb.toString();

			} catch (Exception e) {
				
				return "Check your Input: "+e.toString();
			}	



	}	  
}
