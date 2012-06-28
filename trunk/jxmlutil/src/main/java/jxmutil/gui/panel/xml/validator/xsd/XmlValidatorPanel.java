
package jxmutil.gui.panel.xml.validator.xsd;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.logging.Level;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import jxmutil.business.logic.XMLvalidator;
import jxmutil.gui.common.InputFileEditorGui;
import jxmutil.utility.CustomFileFilter;
import net.miginfocom.swing.MigLayout;

import org.jdesktop.swingx.JXErrorPane;
import org.jdesktop.swingx.error.ErrorInfo;


/**
 * Create a panel where the user can validate an XML file against an XSD file.
 * 
 * The validation is performed with Apache XERCES (http://xerces.apache.org)
 *
 */
public class XmlValidatorPanel extends JPanel implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	
	// Components used to locate an XML file to be validated
	private JLabel sourceXMLlabel;
    private JTextField sourceXMLfileTextField; //the XML file to be validated
    private String pathSourceXMLfile; //the absolute path to the XML chosen file    
    private JButton browseXmlButton;    
    
    // Components used to locate an XSD file used as validator
 	private JLabel sourceXSDlabel;
    private JTextField sourceXSDfileTextField; //the XSD file used for validation
    private String pathSourceXSDfile; //the absolute path to the source file    
    private JButton browseXSDButton;
    
    private JButton editXsdButton;
    private JButton editXmlButton;
    
    private JButton confirmButton;    
    private JButton closeButton;
    
    // The frame that will be close with the close button
    private JFrame mainFrame;        

	/**
	 * Constructor: create the panel to be shown
	 */
	public XmlValidatorPanel(JFrame mainFrame) {
		
		this.setBorder(BorderFactory.createTitledBorder(" Validate the input xml file using the provided xsd "));
		this.setLayout(new MigLayout("wrap 4")); // we want 4 columns   				
		this.mainFrame = mainFrame;
		
		// XML
		sourceXMLlabel = new JLabel("* XML to validate:");
		sourceXMLfileTextField = new JTextField();
		browseXmlButton = new JButton("Find");
		browseXmlButton.addActionListener(this);
		
		// XSD
		sourceXSDlabel = new JLabel("* XSD to use:");
		sourceXSDfileTextField = new JTextField();
		browseXSDButton = new JButton("Choose");
		browseXSDButton.addActionListener(this);
		
		editXmlButton = new JButton("Edit Input XML");
		editXmlButton.addActionListener(this);
		
		editXsdButton = new JButton("Edit XSD");
		editXsdButton.addActionListener(this);
		
		confirmButton = new JButton("Validate");
		confirmButton.setFont(new Font("Serif", Font.BOLD, 12));
        confirmButton.addActionListener(this);
        
        closeButton = new JButton("Close");
        closeButton.setPreferredSize(new Dimension(100,33));
        closeButton.addActionListener(this);
		
		//---------- Add the components to the JPanel
        this.add(sourceXMLlabel);
        this.add(sourceXMLfileTextField,"width 700:350:700,growx");   //min:preferred:max
        this.add(browseXmlButton,"width 100"); 
        this.add(editXmlButton,"width 100");
        
        
        this.add(sourceXSDlabel);
        this.add(sourceXSDfileTextField,"width 700:350:700,growx");
        this.add(browseXSDButton,"width 100");
        this.add(editXsdButton,"width 105");
        
        
        this.add(new JLabel(""),"span 3");
        this.add(confirmButton,"width 105"); //validate
        
       
        this.add(new JLabel(""),"span 4 1,height 500"); // placeholder 
        
        
        this.add(new JLabel(""),"span 3");
        this.add(closeButton,"width 105");      
	}

	/**
	 * Manage Action on the buttons placed on the JPanel
	 */
	public void actionPerformed(ActionEvent e) {
		
		  final JFileChooser xmlFileChooser;
	      final JFileChooser xsdFileChooser;	
		 
	      if (e.getSource() instanceof JButton)  
	      {	    	  
	          // True if the user want choose an XML file to be validated
	          if (e.getActionCommand().equals("Find")) 
	          {
	        	  xmlFileChooser = new JFileChooser();
	        	  xmlFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	        	  xmlFileChooser.setDialogTitle("Choose xml file");
	        	  xmlFileChooser.setFileFilter(new CustomFileFilter());
	              
	              int value = xmlFileChooser.showOpenDialog(this);
	             
	              // Return value if approved (ie yes, ok) is chosen.
	              if (value==JFileChooser.APPROVE_OPTION)
	              {
	            	  File  f = xmlFileChooser.getSelectedFile();
	            	  
	            	  //get the absolute path of the chosen xml file
	            	  pathSourceXMLfile = f.getAbsolutePath(); 
	                  //set the file path in the textField
	            	  sourceXMLfileTextField.setText(pathSourceXMLfile);            	  
	              }
	           
	          }
	          
	          // True if the user want choose an XSD file
	          if (e.getActionCommand().equals("Choose")) 
	          {
	        	  xsdFileChooser = new JFileChooser();
	        	  xsdFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	        	  xsdFileChooser.setDialogTitle("Choose xsd file");
	        	  xsdFileChooser.setFileFilter(new CustomFileFilter());
	              
	              int value = xsdFileChooser.showOpenDialog(this);
	             
	              // Return value if approved (ie yes, ok) is chosen.
	              if (value==JFileChooser.APPROVE_OPTION)
	              {
	            	  File  f = xsdFileChooser.getSelectedFile();
	            	  pathSourceXSDfile = f.getAbsolutePath(); 
	            	  
	            	  sourceXSDfileTextField.setText(pathSourceXSDfile);
	              }	        	  
	          }	          
	          
	          // Edit the xml to validate
	          if (e.getActionCommand().equals("Edit Input XML")) 
	          {
	        	  InputFileEditorGui editor = new InputFileEditorGui(this.sourceXMLfileTextField.getText());
	          }
	          
	          // Edit the xsd
	          if (e.getActionCommand().equals("Edit XSD")) 
	          {
	        	  InputFileEditorGui editor = new InputFileEditorGui(this.sourceXSDfileTextField.getText());
	          }
	          
	          // True if the user want perform the validation
	          if (e.getActionCommand().equals("Validate")) 
	          {	
	        	  try{		        	  
		        	  // Check if the user has swapped the files
		        	  if ((pathSourceXMLfile.endsWith("xml") && pathSourceXSDfile.endsWith("xsd")) || (pathSourceXMLfile.endsWith("XML") || pathSourceXMLfile.endsWith("XSD")))
		        	  {	        	  
				          XMLvalidator xMLvalidator = new XMLvalidator();
				          boolean validationResult = xMLvalidator.isValid(pathSourceXMLfile, pathSourceXSDfile);
				        	  
				          if(validationResult){
				        	 ErrorInfo info = new ErrorInfo("Operation Result", "Successfull Validation", "The xml file is valid !", "category", null, Level.ALL, null); 
					         JXErrorPane.showDialog(this,info);		              
				          }
		        	  
		        	 }else{
		        		ErrorInfo info = new ErrorInfo("Operation Result", "Error during the validation", "Check the files extensions", "category", null, Level.FINE, null); 
			            JXErrorPane.showDialog(this, info); 
		        	 }
	        	  
	        	}catch (Exception ex) {
	        		 ErrorInfo info = new ErrorInfo("Operation Result", "Error during the validation", null, "category", ex, Level.FINE, null); 
		             JXErrorPane.showDialog(this, info); 
				}
	 			  
	          }
	          
	          //-- True if the user ha pressed the Close button
	          if (e.getActionCommand().equals("Close")){
	        	  
        		  if (mainFrame.isDisplayable()) {                     
        			  mainFrame.dispose();
                  }
        	  }
	          
	      }   
		
	}

	public JLabel getSourceXMLlabel() {
		return sourceXMLlabel;
	}


	public void setSourceXMLlabel(JLabel sourceXMLlabel) {
		this.sourceXMLlabel = sourceXMLlabel;
	}


	public JTextField getSourceXMLfileTextField() {
		return sourceXMLfileTextField;
	}


	public void setSourceXMLfileTextField(JTextField sourceXMLfileTextField) {
		this.sourceXMLfileTextField = sourceXMLfileTextField;
	}


	public String getPathSourceXMLfile() {
		return pathSourceXMLfile;
	}


	public void setPathSourceXMLfile(String pathSourceXMLfile) {
		this.pathSourceXMLfile = pathSourceXMLfile;
	}


	public JButton getBrowseXmlButton() {
		return browseXmlButton;
	}


	public void setBrowseXmlButton(JButton browseXmlButton) {
		this.browseXmlButton = browseXmlButton;
	}


	public JLabel getSourceXSDlabel() {
		return sourceXSDlabel;
	}


	public void setSourceXSDlabel(JLabel sourceXSDlabel) {
		this.sourceXSDlabel = sourceXSDlabel;
	}


	public JTextField getSourceXSDfileTextField() {
		return sourceXSDfileTextField;
	}


	public void setSourceXSDfileTextField(JTextField sourceXSDfileTextField) {
		this.sourceXSDfileTextField = sourceXSDfileTextField;
	}


	public String getPathSourceXSDfile() {
		return pathSourceXSDfile;
	}


	public void setPathSourceXSDfile(String pathSourceXSDfile) {
		this.pathSourceXSDfile = pathSourceXSDfile;
	}


	public JButton getBrowseXSDButton() {
		return browseXSDButton;
	}


	public void setBrowseXSDButton(JButton browseXSDButton) {
		this.browseXSDButton = browseXSDButton;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
