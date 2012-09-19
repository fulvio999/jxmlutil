
package jxmutil.gui.panel.xml.validator.xsd;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import jxmutil.business.logic.XMLvalidator;
import jxmutil.gui.common.BusyLabelPanel;
import jxmutil.gui.common.InputFileEditorGui;
import jxmutil.utility.CustomFileFilter;
import jxmutil.utility.FolderFileFilter;
import jxmutil.utility.XmlFilenameFilter;
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
	
	private static String VALID_XML_MSG = "---"; //the value to show in the error message cell when the xml is valid
	
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
    
    // The xml validation type: single or batch
    private JLabel validationTypeLabel;
    private JRadioButton singleValidationRadioButton;
	private JRadioButton batchValidationRadioButton;
	private ButtonGroup validationTypeButtonGroup;
	
	/* A sub-panel with an animation to indicates that a processing is in action */
    private BusyLabelPanel busyLabelPanel;
    
    /* A sub-panel with the batch validation functionality (showed only on demand) */
    private XmlBatchValidationPanel xmlBatchValidationPanel;
    
    /* A sub-panel with the batch validation result */
    private XmlBatchValidationResultPanel xmlBatchValidationResultPanel;
    
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
		
		busyLabelPanel = new BusyLabelPanel();
		busyLabelPanel.getJxBusyLabel().setBusy(false);
		
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
        
        // Validation Type
        validationTypeLabel = new JLabel("Validation Type:");
        
        singleValidationRadioButton = new JRadioButton("Single file");
        singleValidationRadioButton.doClick(); //by default this button is clicked: ie the sinle file validation mode is the default
    	batchValidationRadioButton = new JRadioButton("Batch mode");
    	singleValidationRadioButton.addActionListener(this);
    	batchValidationRadioButton.addActionListener(this);
    	    	
    	validationTypeButtonGroup = new ButtonGroup();
    	validationTypeButtonGroup.add(singleValidationRadioButton);
    	validationTypeButtonGroup.add(batchValidationRadioButton);
    	
    	// the panel with the batch validation function
    	xmlBatchValidationPanel = new XmlBatchValidationPanel(this);
    	xmlBatchValidationPanel.setVisible(false);
    	
    	// the panel with the batch validation RESULTS
    	xmlBatchValidationResultPanel = new XmlBatchValidationResultPanel();
    	xmlBatchValidationResultPanel.setVisible(false);
		
		//---------- Add the components to the JPanel ------------
        this.add(sourceXSDlabel);
        this.add(sourceXSDfileTextField,"width 700:350:700,growx");
        this.add(browseXSDButton,"width 100");
        this.add(editXsdButton,"width 105");
        
        this.add(sourceXMLlabel);
        this.add(sourceXMLfileTextField,"width 700:350:700,growx");   //min:preferred:max
        this.add(browseXmlButton,"width 100"); 
        this.add(editXmlButton,"width 100");
        
        this.add(new JLabel(""),"span 3");
        this.add(confirmButton,"width 105"); //validate
 
        this.add(validationTypeLabel);
        this.add(singleValidationRadioButton,"split 2");
		this.add(batchValidationRadioButton,"wrap");         
       
        this.add(xmlBatchValidationPanel,"span 4,growx"); 
        this.add(xmlBatchValidationResultPanel,"span 4,growx");  
        
        this.add(new JLabel(""),"span 3"); //place-holder
        this.add(closeButton,"width 100");      
	}

	/**
	 * Manage Action on the buttons placed on the JPanel
	 */
	public void actionPerformed(ActionEvent e) {
		
		  final JFileChooser xmlFileChooser;
	      final JFileChooser xsdFileChooser;
	      
	      /* to chose a folder containing xml files to validate */
	      final JFileChooser xmlFolderChooser;
	      
	      /* Manage the action coming from the checkbox: hide or show the partial export panel */
		  if (e.getSource() instanceof JRadioButton)  
		  {	
			  /* Depending on the validation type chosen enable/disable the right component */
			  
			  if(e.getActionCommand().equals("Single file")){				 
	             this.xmlBatchValidationPanel.setVisible(false); 
	             xmlBatchValidationResultPanel.setVisible(false);
	             
	             this.sourceXMLfileTextField.setEditable(true);
				 this.sourceXMLfileTextField.setEnabled(true);
				 this.confirmButton.setEnabled(true);
				 this.browseXmlButton.setEnabled(true);
				 this.editXmlButton.setEnabled(true);
			  }
			  
			  //show the panel with batch validation functionality and disable the single validation widget 
			  if(e.getActionCommand().equals("Batch mode")){  				 
				  this.xmlBatchValidationPanel.setVisible(true);
				  
				  this.sourceXMLfileTextField.setEditable(false);
				  this.sourceXMLfileTextField.setEnabled(false);
				  this.confirmButton.setEnabled(false);
				  this.browseXmlButton.setEnabled(false);
				  this.editXmlButton.setEnabled(false);
			  } 		      	      
		  }
		  
		  /* Manage the actions on the Buttons */
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
	          
	          // ----- The user want perform the SINGLE FILE VALIDATION
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
	          
	          /* ---------- Action for the sub-panel with batch validation functionality ---------- */	          
	          
	          /* True if the user want execute a batch validation (action coming from the sub-panel XmlBatchValidationPanel) */
	          if (e.getActionCommand().equalsIgnoreCase("Batch Validation")){
	        	  
	        	  if ((pathSourceXSDfile.endsWith("xsd")) || pathSourceXSDfile.endsWith("XSD"))
	        	  {	        	  
	        	    // execute the batch validation
	        	    String inputXmlfolder = this.xmlBatchValidationPanel.getSourceXMLfolderTextField().getText();
	        	  
	        	    if(!inputXmlfolder.equalsIgnoreCase("") && inputXmlfolder != null && pathSourceXSDfile != null){
	        		  
	        		   ArrayList<ValidationResultBean> validationResultList = new ArrayList<ValidationResultBean>();
	        		  
	        		   File xmlInputDir = new File(inputXmlfolder);
	        		   XMLvalidator xMLvalidator = new XMLvalidator();
	        		    
	        		   // using a custom filter list only the xml files
	        		   String[] xmlFileFound = xmlInputDir.list(new XmlFilenameFilter());					 
						 
					   for (int i=0; i<xmlFileFound.length; i++)
					   {						  
						  ValidationResultBean resultBean = new ValidationResultBean();
						  
						  // Get filename of file or directory
						  String filename = xmlFileFound[i];
						  // System.out.println("File to validate: "+filename);						   
						  resultBean.setXmlfileName(filename); 
						  
						  try {
							   boolean validationResult = xMLvalidator.isValid(xmlInputDir+File.separator+filename, pathSourceXSDfile);
							   
							   if(validationResult)
							   {								 
								 resultBean.setValidationResult("yes");
								 resultBean.setErrorDescription(VALID_XML_MSG);
							   }
							      
						   }catch (Exception exc) {	// validation exception						
							 resultBean.setValidationResult("no");
							 resultBean.setErrorDescription(exc.toString()); 
						   }
						   
						   validationResultList.add(resultBean);						   
						  
						   BatchValidationResultTableModel tableModel = (BatchValidationResultTableModel)  xmlBatchValidationResultPanel.getValidationResultTable().getModel();		
						   //initialize the table model with an empty list of data
						   tableModel.setValidationResultBeanList(validationResultList);
						   tableModel.fireTableDataChanged();
					  }					  
					  this.xmlBatchValidationResultPanel.setVisible(true);

	        	  }else{        		    
		        	 ErrorInfo info = new ErrorInfo("Operation Result", "Input Error", "Wrong input folder or XSD file", "category", null, Level.ALL, null); 
			         JXErrorPane.showDialog(this,info);
	        	  }	
	        	  
	        	}else{ // wrong input files extensions
	        		ErrorInfo info = new ErrorInfo("Operation Result", "Error during the validation", "Check the files extensions", "category", null, Level.FINE, null); 
		            JXErrorPane.showDialog(this, info); 
	        	}
	         }		  
	          
	          /* The user choose a folder containing xml files to validate */
	          if (e.getActionCommand().equals("Browse"))
	          {	        	  
	        	  xmlFolderChooser = new JFileChooser();
	        	  xmlFolderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	        	  xmlFolderChooser.setDialogTitle("Choose a folder with xml files");
	        	  xmlFolderChooser.setFileFilter(new FolderFileFilter());
	              
	              int value = xmlFolderChooser.showOpenDialog(this);
	             
	              // Return value if approved (ie yes, ok) is chosen.
	              if (value==JFileChooser.APPROVE_OPTION)
	              {
	            	 File f = xmlFolderChooser.getSelectedFile();  	
	            	 this.xmlBatchValidationPanel.getSourceXMLfolderTextField().setText(f.getAbsolutePath());	            	 
	              }	        
	          }
	          
	          //-- True if the user ha pressed the Close button
	          if (e.getActionCommand().equals("Close"))
	          {	        	  
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
