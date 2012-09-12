
package jxmutil.gui.panel.javaproperties;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.ItemSelectable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import jxmutil.business.logic.JavaPropertiesToXmlConverter;
import jxmutil.business.logic.XmlToJavaPropertiesConverter;
import jxmutil.gui.common.InputFileEditorGui;
import net.miginfocom.swing.MigLayout;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

/**
 * Panel that offer the functionality to convert a java.util.Properties file to an XML file and vice versa
 *
 */
public class JavaPropertiesConverterPanel extends JPanel implements ActionListener, ItemListener{
	
	private static final long serialVersionUID = 1L;
	
	/* Source file */
	private JLabel sourceFilelabel;
    private JTextField sourceFileTextField; //the input file to be processed
    private String pathSourcefile; //the absolute path to the input file
    
    /* Destination output file data */ 
    private JLabel destinationFilelabel;
    private JTextField destinationFileTextField; 
    
    /* The text area that show the input file */
    private JTextArea fileInputTextArea;
    
    // The text area with input file with syntax highlight (http://fifesoft.com/rsyntaxtextarea) 
    private RTextScrollPane inputFileScrollPanel;
    
    //The text area with processing output
    private RTextScrollPane outPutScrollPanel;
    
    private JButton browseButton; //the textField with the name of the input file
    private JButton changeButton; //the textField used to select the folder where put the output
    private JButton startconversionButton;
    private JButton editInputFilebutton;
    
    private JButton closeButton;
    
    // The frame that will be close with the close button
    private JFrame mainFrame;
    
    // Refresh the input text area content 
    private JButton reloadInputButton;
    
    // A label used to show an error message when the user don't check any checkbox
    private JLabel messageLabel;
    
    // A special text Area (http://fifesoft.com/rsyntaxtextarea) with syntax-highlight functionality for input File text area
    private RSyntaxTextArea fileInputRsyntaxtextarea;    
    private RSyntaxTextArea outputRsyntaxtextarea;
    
    // The conversion to execute java-properties <--> xml
    private JComboBox convertTypeCombo;
    private JLabel conversionTypeLabel;
    
    private static String[] convertTypeOption = {"---" , "PROPERTIES to XML", "XML to PROPERTIES"};
	
    /**
     * Constructor: create the GUI 
     */
	public JavaPropertiesConverterPanel(JFrame mainframe) 
	{
		this.setBorder(BorderFactory.createTitledBorder(" Convert a Java.util.Properties file to xml and vice versa "));
		this.setLayout(new MigLayout("wrap 3")); // we want 3 columns               
        this.mainFrame = mainframe;
        
        sourceFilelabel = new JLabel("* Input file:");
        sourceFileTextField = new JTextField();
        sourceFileTextField.setToolTipText("The file to be converted");       
        
        destinationFilelabel = new JLabel("Processing output file:");
        destinationFileTextField = new JTextField();
        destinationFileTextField.setToolTipText("The filename where write the conversion output");
        
        conversionTypeLabel = new JLabel("* Conversion Type:");
         
        //Choose a file output
        changeButton = new JButton("Choose");
        changeButton.addActionListener(this);
        
        //Find
        browseButton = new JButton("Find");
        browseButton.addActionListener(this);
        
        //Start the processing
        startconversionButton = new JButton("Start Processing");
        startconversionButton.addActionListener(this);
        
        reloadInputButton = new JButton("Reload Input");
        reloadInputButton.addActionListener(this);

        //------- textArea 1:  Source File with syntax-highlight
        fileInputRsyntaxtextarea = new RSyntaxTextArea();        
        fileInputRsyntaxtextarea.setEditable(false);
        
        inputFileScrollPanel = new RTextScrollPane(fileInputRsyntaxtextarea);
        inputFileScrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        inputFileScrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        inputFileScrollPanel.setPreferredSize(new Dimension(250, 250));
        inputFileScrollPanel.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Input File"), BorderFactory.createEmptyBorder(5, 5, 5, 5)), inputFileScrollPanel.getBorder()));
        
        //-------- textArea 2: conversion output
        outputRsyntaxtextarea = new RSyntaxTextArea();        
        outputRsyntaxtextarea.setEditable(true);
        
        outPutScrollPanel = new RTextScrollPane(outputRsyntaxtextarea);
        outPutScrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        outPutScrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        outPutScrollPanel.setPreferredSize(new Dimension(250, 250));
        outPutScrollPanel.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Conversion Output"), BorderFactory.createEmptyBorder(5, 5, 5, 5)), outPutScrollPanel.getBorder()));

        // Label used to display some error message
        messageLabel = new JLabel();
        messageLabel.setFont(new Font("Serif", Font.BOLD, 15));  
        
        convertTypeCombo = new JComboBox(convertTypeOption);  
        convertTypeCombo.addItemListener(this);
        
        closeButton = new JButton("Close");
        closeButton.setPreferredSize(new Dimension(100,33));
        closeButton.addActionListener(this);
        
        editInputFilebutton = new JButton("Edit input File");
        editInputFilebutton.addActionListener(this);
        
        //------------- Add the components to the JPanel
        this.add(sourceFilelabel);
        this.add(sourceFileTextField,"width 800:350:900,growx");  //min:preferred:max
        this.add(browseButton,"width 100");
        
        
        this.add(destinationFilelabel);
        this.add(destinationFileTextField,"width 800:350:900,growx");
        this.add(changeButton,"width 100"); 
        
        this.add(conversionTypeLabel);
        this.add(convertTypeCombo);
        this.add(editInputFilebutton);         
        
        this.add(messageLabel,"span 3, align center"); 
        
        
        // Add the two text area: source file and conversion output
        GridLayout experimentLayout = new GridLayout(1,2);         
        final JPanel xmlTextAreaPanel = new JPanel();
        xmlTextAreaPanel.setLayout(experimentLayout);      
        xmlTextAreaPanel.add(inputFileScrollPanel);        
        xmlTextAreaPanel.add(outPutScrollPanel);        
        this.add(xmlTextAreaPanel,"span 3,width 1000, height 650,growx,growy");  //3 col
        
        
        this.add(reloadInputButton);
        this.add(startconversionButton);
        this.add(closeButton);    
   	
	}


	/* 
	 * Manage the action on the JButton
	 */
	public void actionPerformed(ActionEvent e) 
	{
		  final JFileChooser sorceFileChooser;
	      final JFileChooser destinationbFolderFileChooser;		      
	      
	      if (e.getSource() instanceof JButton)  
	      {	    	  
	          // True if the user want choose a source XML file
	          if (e.getActionCommand().equals("Find")) 
	          {
	        	  sorceFileChooser = new JFileChooser();
	        	  sorceFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	        	  sorceFileChooser.setDialogTitle("Choose Input file");	        	 
	              
	              int value = sorceFileChooser.showOpenDialog(this);
	             
	              // Return value if approved (ie yes, ok) is chosen.
	              if (value==JFileChooser.APPROVE_OPTION)
	              {
	            	  File inputFile = sorceFileChooser.getSelectedFile();
	            	  
	            	  pathSourcefile = inputFile.getAbsolutePath(); 	                  
	            	  sourceFileTextField.setText(pathSourcefile); 
	            	  
	            	  try{
		                   File fileSource = new File(pathSourcefile);
			        	   FileReader fr = new FileReader(fileSource);
			        	   BufferedReader br = new BufferedReader(fr);
			        	  
			        	   StringBuilder sb = new StringBuilder();
			        	   String startLine = null;
			        		  
			        	   while ((startLine = br.readLine()) != null){
			        		  sb.append(startLine+"\n");
			        	   }
			        		  
			        	   this.fileInputRsyntaxtextarea.setText(sb.toString());
			        		  
			        	   // clean the output text area from previous results (if any)
			        	   this.outputRsyntaxtextarea.setText("");
			        	   //hide some previous message
			        	   messageLabel.setForeground(Color.GREEN);
			        	   messageLabel.setText("");
			        		  
		                }catch (Exception ex) {
		                   this.fileInputRsyntaxtextarea.setText(ex.toString());
						} 	          
	              }
	           
	          }
	          // the user want choose an output file where write output
	          if (e.getActionCommand().equals("Choose"))
	          {	              
	       	      destinationbFolderFileChooser =  new JFileChooser();
	              destinationbFolderFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	              destinationbFolderFileChooser.setDialogTitle("Choose a file name for the processing result");
	              destinationbFolderFileChooser.setAcceptAllFileFilterUsed(false); //disable default fileChooserFilter
	              
	              int value = destinationbFolderFileChooser.showOpenDialog(this);
	            
	              // Return value if approve (yes, ok) is chosen.
	              if (value==JFileChooser.APPROVE_OPTION)
	              {
	                   File  f = destinationbFolderFileChooser.getSelectedFile();	                  
	                  
	                   pathSourcefile = f.getAbsolutePath();      
	                   destinationFileTextField.setText(pathSourcefile);	              
	              }	        	   
	          }
	          
	          // Reload input file showed in the text area
	          if(e.getActionCommand().equals("Reload Input"))
	          {
	        	 String fileToReload = sourceFileTextField.getText(); 
	        	
	        	 FileReader fr = null;
           	     BufferedReader br = null;
	        	 
            	 try{
	                  File fileSorg = new File(fileToReload);
		        	  fr = new FileReader(fileSorg);
		        	  br = new BufferedReader(fr);
		        	  
		        	  StringBuilder sb = new StringBuilder();
		        	  String startLine = null;
		        		  
		        	  while ((startLine = br.readLine()) !=null){
		        		  sb.append(startLine+"\n");
		        	  }
		        		  
		        	  this.fileInputRsyntaxtextarea.setText(sb.toString());
		        		
		        	  messageLabel.setForeground(Color.GREEN);
					  messageLabel.setText("Input Reloaded successfully");
		        		  
	                }catch (Exception ex) {	                	
	                   this.fileInputTextArea.setText(ex.toString());
					}
	          } 	          
	          
	          // Start CONVERSION processing
	          if (e.getActionCommand().equals("Start Processing")) 
	          {
	        	      //The full path name of the input file to transform
	        		  String inputFileName = sourceFileTextField.getText();
	        		  // Optional the file where write result (properties or xml)
	        		  String destinationFile = destinationFileTextField.getText();		  
	        		 
	        		  String conversionType = (String) this.convertTypeCombo.getSelectedItem();
	        		  String conversionResult = null;
	        		  
	        		  try {	        			
	        			  if(conversionType.equalsIgnoreCase("PROPERTIES to XML"))
	        			  {   
	        				  // perform some basic validation
	        				  if(!inputFileName.endsWith("properties")){
	        					  messageLabel.setForeground(Color.RED);
	      		      			  messageLabel.setText("For the chosen conversion type, input must be a properties file !");
	      		      			  
	      		      			  throw new Exception("Input must be a properties file !");
	        				  }
	        				  
	        				  JavaPropertiesToXmlConverter javaPropertiesToXmlConverter = new JavaPropertiesToXmlConverter();
	        				  conversionResult = javaPropertiesToXmlConverter.convert(inputFileName,destinationFile);	        				 
	        				  
	        			  }else if(conversionType.equalsIgnoreCase("XML to PROPERTIES"))
	        			  {	        				  
	        				  // perform some basic validation
	        				  if(!inputFileName.endsWith("xml")){
	        					  messageLabel.setForeground(Color.RED);
	      		      			  messageLabel.setText("For the chosen conversion type, input must be a xml file !");
	      		      			  
	      		      			  throw new Exception("For the chosen conversion type, input must be a xml file !");
	        				  }
	        				  
	        				  XmlToJavaPropertiesConverter xmlToJavaPropertiesConverter = new XmlToJavaPropertiesConverter();
	        				  conversionResult = xmlToJavaPropertiesConverter.convert(inputFileName,destinationFile);	
	        				  
	        			  }else {
	        				  messageLabel.setForeground(Color.RED);
      		      			  messageLabel.setText("Wrong conversion type !");
      		      			  
      		      			  throw new Exception("Wrong conversion type !");
	        			  }
	        			  // show the result
	        			  this.outputRsyntaxtextarea.setText(conversionResult);     			  
	      			      
	      			      messageLabel.setForeground(Color.GREEN);
		      			  messageLabel.setText("Operation executed successfully");	  
		      				        		  	      			      
	        		  }catch (Exception ex) {
	        			  this.outputRsyntaxtextarea.setText(ex.getMessage());
	        		  }	        	  
	          	}
	          
	          // Edit Chosen input file
	          if(e.getActionCommand().equals("Edit input File"))
	          {
	        	  InputFileEditorGui editor = new InputFileEditorGui(this.sourceFileTextField.getText());
	          }
	          
	          // pressed the Close button
	          if (e.getActionCommand().equals("Close")){
	        	  
        		  if (mainFrame.isDisplayable()) {                     
        			  mainFrame.dispose();
                  }
        	  }
	      }  
	
	     
	}

    /**
     * Called when the user set or change the conversion type to perform in the combo box.
     * It set the right highlight type in the to text area according with the chosen conversion type
     */
	public void itemStateChanged(ItemEvent e) {
		
		// clean the output text area from previous results
		outputRsyntaxtextarea.setText("");
		
		if(e.getStateChange() == ItemEvent.SELECTED)
		{		
			ItemSelectable is = (ItemSelectable)e.getSource();
	        Object selected[] = is.getSelectedObjects();
	        String conversionType = (String)selected[0];	
	      
	        if(conversionType.equalsIgnoreCase("PROPERTIES to XML")){        	
	        	
	        	fileInputRsyntaxtextarea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_PROPERTIES_FILE);
	        	outputRsyntaxtextarea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_XML);
	        	
	        } else if(conversionType.equalsIgnoreCase("XML to PROPERTIES"))
	        {     
	        	fileInputRsyntaxtextarea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_XML);
	        	outputRsyntaxtextarea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_PROPERTIES_FILE);		
	        }        
		}
	}


}
