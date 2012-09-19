package jxmutil.gui.panel.xslt;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import jxmutil.business.logic.ApplyXsltTransformation;
import jxmutil.gui.common.InputFileEditorGui;
import jxmutil.utility.CustomFileFilter;
import net.miginfocom.swing.MigLayout;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;


/**
 * Create the panel with the XSLT functionality
 * 
 */
public class XsltPanel extends JPanel implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	/* Source XML file */
	private JLabel sourceXMLlabel;
    private JTextField sourceXMLfileTextField; //the XML file to be processed by xpath
    private String pathSourceXMLfile; //the absolute path to the xml source file
    
    /* Destination XSLT output file data */ 
    private JLabel destinationXSLTlabel;
    private JTextField destinationXSLTresultTextField; 
    
    /* XSL Transformation file */
    private JLabel xslTransformationLabel;
    private JTextField xslTransformationTextField;
    private String pathXslFile; //the absolute path to the xsl file
    
    /* The text area which show the xml input file */
    private JTextArea xmlInputTextArea;
    
    /* The text area which show the XSLT result output */
    private JTextArea xsltOutputTextArea;
    
    // The text area with xml input file. A special scroll panel with syntax highlight (http://fifesoft.com/rsyntaxtextarea)
    private RTextScrollPane inputXmlFileScrollPanel;  
    
    // The text area with xsl output
    private RTextScrollPane outPutXslFileScrollPanel;
    
    private JButton browseButton; //the textField with the name of the input xml file
    private JButton changeButton; //the textField used to select the folder where put the XSLT output
    private JButton startProcessingButton;
    private JButton findXslFileButton; //the button to choose an xsl file   
    private JButton reloadXmlButton;
    private JButton editChosenXslbutton;
    private JButton editInputXmlButton;
    
    // A label used to show messages
    private JLabel messageLabel;    
    private JButton closeButton;
    
    // The frame that will be close with the close button
    private JFrame mainFrame;
    
    //A special text Area (http://fifesoft.com/rsyntaxtextarea) with syntax-highlight functionality
    private RSyntaxTextArea xmlInputRsyntaxtextarea;
    private RSyntaxTextArea xslOutputRsyntaxtextarea;
	
    /**
     * Constructor: create the GUI
     * 
     */
	public XsltPanel(JFrame mainframe) 
	{
		this.setBorder(BorderFactory.createTitledBorder(" Apply an xsl sheet to an input xml file and (optionally) write the result to a chosen file "));
        this.setLayout(new MigLayout("wrap 4")); // we want 4 columns
        this.mainFrame = mainframe;
        
        sourceXMLlabel = new JLabel("* Source XML file:");
        sourceXMLfileTextField = new JTextField();
        sourceXMLfileTextField.setToolTipText("The XML file to process with XSLT");       
        
        destinationXSLTlabel = new JLabel("Processing output file:");
        destinationXSLTresultTextField = new JTextField();
        destinationXSLTresultTextField.setToolTipText("The filename where write output:");
             
        xslTransformationLabel = new JLabel("* XSL File path:");
        xslTransformationTextField = new JTextField();
        xslTransformationTextField.setToolTipText("The xsl transformation file");
         
        //Choose a file output
        changeButton = new JButton("Choose");
        changeButton.addActionListener(this);
        
        //Find xml button
        browseButton = new JButton("Find");
        browseButton.addActionListener(this);
        
        findXslFileButton = new JButton("Find Xsl");
        findXslFileButton.addActionListener(this);
        
        //Start the xslt processing
        startProcessingButton = new JButton("Start Processing");
        startProcessingButton.addActionListener(this);
        
        reloadXmlButton = new JButton("Refresh XML");
        reloadXmlButton.addActionListener(this);
        
        editChosenXslbutton = new JButton("Edit Input XSL");
        editChosenXslbutton.addActionListener(this);
        
        editInputXmlButton = new JButton("Edit Input XML");
        editInputXmlButton.addActionListener(this);

        //------------ Text area 1: XML source
        xmlInputRsyntaxtextarea = new RSyntaxTextArea();
        xmlInputRsyntaxtextarea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_XML);
        xmlInputRsyntaxtextarea.setEditable(false);
        
        inputXmlFileScrollPanel = new RTextScrollPane(xmlInputRsyntaxtextarea);
        inputXmlFileScrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        inputXmlFileScrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        inputXmlFileScrollPanel.setPreferredSize(new Dimension(250, 250));
        inputXmlFileScrollPanel.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("XML input File"), BorderFactory.createEmptyBorder(5, 5, 5, 5)), inputXmlFileScrollPanel.getBorder()));
        
        //------------ Text area 2: XSLT output
        xslOutputRsyntaxtextarea = new RSyntaxTextArea();
        xslOutputRsyntaxtextarea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_XML);
        xslOutputRsyntaxtextarea.setEditable(false);
        
        outPutXslFileScrollPanel = new RTextScrollPane(xslOutputRsyntaxtextarea);
        outPutXslFileScrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        outPutXslFileScrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        outPutXslFileScrollPanel.setPreferredSize(new Dimension(250, 250));
        outPutXslFileScrollPanel.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("XSL output"), BorderFactory.createEmptyBorder(5, 5, 5, 5)), outPutXslFileScrollPanel.getBorder()));
        
        closeButton = new JButton("Close");
        closeButton.setPreferredSize(new Dimension(100,33));
        closeButton.addActionListener(this);
        
        messageLabel = new JLabel();
        messageLabel.setFont(new Font("Serif", Font.BOLD, 15));
        messageLabel.setForeground(Color.RED);
        
        //------ Add the components to the JPanel
        this.add(sourceXMLlabel);
        this.add(sourceXMLfileTextField,"width 700:350:700,growx");
        this.add(browseButton,"width 105");
        this.add(editInputXmlButton,"width 105");
        
        
        this.add(destinationXSLTlabel);
        this.add(destinationXSLTresultTextField,"width 700:350:700,growx");
        this.add(changeButton,"width 105");
        this.add(new JLabel(""));
        
        
        this.add(xslTransformationLabel);
        this.add(xslTransformationTextField,"width 800:350:700,growx");
        this.add(findXslFileButton,"width 105");
        this.add(editChosenXslbutton);
        
        
        //this.add(new JLabel(""),"span 2");
        this.add(messageLabel,"span 4, align center");
        
        
        GridLayout textAreaLayout = new GridLayout(1,2);         
        final JPanel xmlTextAreaPanel = new JPanel();
        xmlTextAreaPanel.setLayout(textAreaLayout);      
        xmlTextAreaPanel.add(inputXmlFileScrollPanel);                
        xmlTextAreaPanel.add(outPutXslFileScrollPanel);          
        this.add(xmlTextAreaPanel,"span 4,width 1000, height 650,growx,growy");
        
       
        this.add(reloadXmlButton);      
        this.add(startProcessingButton);  
        this.add(new JLabel("")); // placeholder
        this.add(closeButton); 	
	}


	/* 
	 * Manage the action of the JButton, JRadioButton objects etc.
	 */
	public void actionPerformed(ActionEvent e) 
	{
		  final JFileChooser sorceXmFileChooser;
	      final JFileChooser destinationbFolderFileChooser;
	      final JFileChooser xslFileChooser;	      
	      
	      if (e.getSource() instanceof JButton)  
	      {	    	  
	          // the user want choose a source XML file
	          if (e.getActionCommand().equals("Find")) 
	          {
	        	  sorceXmFileChooser = new JFileChooser();
	        	  sorceXmFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	        	  sorceXmFileChooser.setDialogTitle("Choose xml file source");
	        	  sorceXmFileChooser.setFileFilter(new CustomFileFilter());
	              
	              int value = sorceXmFileChooser.showOpenDialog(this);
	             
	              // Return value if approved (ie yes, ok) is chosen.
	              if (value==JFileChooser.APPROVE_OPTION)
	              {
	            	  File  f = sorceXmFileChooser.getSelectedFile();
	            	 
	            	  pathSourceXMLfile = f.getAbsolutePath();	                 
	            	  sourceXMLfileTextField.setText(pathSourceXMLfile); 
	            	  
	            	  FileReader fr = null;
	            	  BufferedReader br = null;
	            	  
	            	  try{
		                  File fileSorg = new File(pathSourceXMLfile);
			        	  fr = new FileReader(fileSorg);
			        	  br = new BufferedReader(fr);
			        	  
			        	  StringBuilder sb = new StringBuilder();
			        	  String startLine = null;
			        		  
			        	  while ((startLine = br.readLine()) !=null){
			        		 sb.append(startLine+"\n");
			        	  }
			        		  
			        	  fr.close();
			        	  br.close();
			        		  
			        	  this.xmlInputRsyntaxtextarea.setText(sb.toString());	
			        	  
			        	  // clean the output text area from previous results (if any)
			        	  this.xslOutputRsyntaxtextarea.setText("");
			        	  
			        	  // clean old result message
			        	  messageLabel.setForeground(Color.GREEN);
				      	  messageLabel.setText("");
			        		  
		                }catch (Exception ex) {     	
		                	this.xmlInputRsyntaxtextarea.setText(ex.toString());
						} 	          
	              }
	           
	          }
	          // True if user choose an output file where write XSL output
	          if (e.getActionCommand().equals("Choose"))
	          {	              
	       	      destinationbFolderFileChooser =  new JFileChooser();
	              destinationbFolderFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	              destinationbFolderFileChooser.setDialogTitle("specify a file name for XPATH output");
	              destinationbFolderFileChooser.setAcceptAllFileFilterUsed(false); //disable the default fileChooserFilter
	              
	              int value = destinationbFolderFileChooser.showOpenDialog(this);
	            
	              // Return value if approve (yes, ok) is chosen.
	              if (value==JFileChooser.APPROVE_OPTION)
	              {
	                   File  f = destinationbFolderFileChooser.getSelectedFile();	                  
	                  
	                   pathSourceXMLfile = f.getAbsolutePath();	              
	                   destinationXSLTresultTextField.setText(pathSourceXMLfile); 	              
	              }
	        	   
	          }
	          // True if user choose an XSL file
	          if (e.getActionCommand().equals("Find Xsl"))
	          {
	        	  xslFileChooser =  new JFileChooser();
	        	  xslFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	        	  xslFileChooser.setDialogTitle("Choose an XSL file");
	        	  xslFileChooser.setFileFilter(new CustomFileFilter());
	        	  
	              int value = xslFileChooser.showOpenDialog(this);
	            
	              // Return value if approve (yes, ok) is chosen.
	              if (value==JFileChooser.APPROVE_OPTION)
	              {
	                   File  f = xslFileChooser.getSelectedFile();	                  
	                   
	                   pathXslFile = f.getAbsolutePath(); 
	                   xslTransformationTextField.setText(pathXslFile); 	              
	              }	        	   
	          }
	          
	          // Refresh the XML showed in the text area after his editing with an external editor -----
	          if(e.getActionCommand().equals("Refresh XML"))
	          {
	        	 String fileToReload = sourceXMLfileTextField.getText(); 
	        	
            	  try{
	                   File fileSorg = new File(fileToReload);
		        	   FileReader fr = new FileReader(fileSorg);
		        	   BufferedReader br = new BufferedReader(fr);
		        	  
		        	   StringBuilder sb = new StringBuilder();
		        	   String startLine = null;
		        		  
		        	   while ((startLine = br.readLine()) !=null){
		        		 sb.append(startLine+"\n");
		        	   }
		        		  
		        	   this.xmlInputRsyntaxtextarea.setText(sb.toString());		        	   
		        	  
			           messageLabel.setForeground(Color.GREEN);
				       messageLabel.setText("Input Reloaded successfully");
		        		  
	                }catch (Exception ex) {
	                   this.xmlInputTextArea.setText(ex.toString());
					}
	          } 	          
	          
	          // Edit the chosen input XML file
	          if(e.getActionCommand().equals("Edit Input XML"))
	          {
	        	  InputFileEditorGui editor = new InputFileEditorGui(this.sourceXMLfileTextField.getText());
	          }
	          
	          // Edit the chosen XSL file
	          if(e.getActionCommand().equals("Edit Input XSL"))
	          {
	  	         InputFileEditorGui editor = new InputFileEditorGui(this.xslTransformationTextField.getText());
	          }
	          
	          // Start XSLT processing
	          if (e.getActionCommand().equals("Start Processing")) 
	          {
	        		  String XMLinputFileName = sourceXMLfileTextField.getText(); //The full path name of the xml input file to transform
	        		  String destinationFile = destinationXSLTresultTextField.getText();
	        		  String xslFileToLoad = xslTransformationTextField.getText(); //The full path name of the xsl file
	        	  
	        		  try {
	        			 
	        			  ApplyXsltTransformation xsltTransformer = new ApplyXsltTransformation();
	        			  String xslResult = xsltTransformer.execute(XMLinputFileName, xslFileToLoad);
	        			  
	        			  this.xslOutputRsyntaxtextarea.setText(xslResult);
	        			  
	        			  messageLabel.setForeground(Color.GREEN);
			      		  messageLabel.setText("Operation executed successfully");
	        			  
	        			  // Write the XSL result to the output file (if the user has specified one)	        			 	        			  
	      			      if(!destinationFile.equalsIgnoreCase(""))
	      			      {	      			    	
	      			    	File f = new File(destinationFile);
	      			    	
	      			    	if (f.exists())
	      						f.delete();
	      			    		
	      			    	if(f.createNewFile())
	      			    	{
	      			    		FileWriter fw = new FileWriter(destinationFile);
	      						BufferedWriter bw  = new BufferedWriter(fw);
	      						PrintWriter pw = new PrintWriter(bw);
	      						pw.print(xslResult);
	      						
	      						pw.close();
	      						bw.close();
	      						fw.close();	      						
	      			    	}
	      		  	    }	        			  
	      			      
	        		  }catch (Exception ex) {
	        			  this.xsltOutputTextArea.setText(ex.toString());
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

	
	public JTextField getSourceXMLfileTextField() {
		return sourceXMLfileTextField;
	}


	public void setSourceXMLfileTextField(JTextField sourceXMLfileTextField) {
		this.sourceXMLfileTextField = sourceXMLfileTextField;
	}


	public String getPathXslFile() {
		return pathXslFile;
	}


	public void setPathXslFile(String pathXslFile) {
		this.pathXslFile = pathXslFile;
	}


}
