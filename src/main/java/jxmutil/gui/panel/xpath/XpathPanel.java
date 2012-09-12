package jxmutil.gui.panel.xpath;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import jxmutil.business.logic.ApplyXPATHexpression;
import jxmutil.gui.common.InputFileEditorGui;

import jxmutil.utility.CustomFileFilter;
import net.miginfocom.swing.MigLayout;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;


/**
 * Create the panel with the XPATH functions
 *
 */
public class XpathPanel extends JPanel implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	/* Source XML file */
	private JLabel sourceXMLlabel;
    private JTextField sourceXMLfileTextField; //the XML file to be processed by xpath
    private String pathSourceXMLfile; //the absolute path to the source file
    
    /* Output file data */ 
    private JLabel outPutFilelabel;
    private JTextField destinationXPATHresultTextField; 
    
    private JLabel xpathExpressionLabel;
    
    /* The text area which show the xml input file */
    private JTextArea xmlInputTextArea;
    
    /* The text area which show the XPATH result output */
    private JTextArea xmlOutputTextArea;
    
    //A special text Area (http://fifesoft.com/rsyntaxtextarea) with syntax-highlight functionality
    private RSyntaxTextArea inputRsyntaxtextarea;
    private RSyntaxTextArea outputRsyntaxtextarea;
    
    /* The three RadioButton with the option about which value xpath expression must retrieve from the xml processed */
    private JRadioButton showNodeValueRadioButton; 
    private JRadioButton showNodeNameRadioButton;
    private JRadioButton shownodeTextContentRadioButton;
    
    /* The text area with xml input file and xpath result  */
    private RTextScrollPane inputXmlFileScrollPanel;  //A special scroll panel with syntax highlight (http://fifesoft.com/rsyntaxtextarea) 
   
    /* The text area with xpath output  */
    private RTextScrollPane outPutXpathFileScrollPanel;
    
    private JButton browseButton; //the textField with the name of the input xml file
    private JButton changeButton; //the textField used to select the folder where put the  XPATH output
    private JButton confirmButton;
    private JButton reloadXmlButton;
    private JButton editChosenXmlbutton;
    
    private JTextField xpathExpressionTextField;
    
    private ButtonGroup buttonGroup;
    
    /* Flag used to monitor the state of the three checkbox  */
    private boolean showNodeValueChecked = false;
    private boolean showNodeNameChecked = false;
    private boolean shownodeTextContentChecked = false;
    
    /* A label used to show an error message when the user don't check any checkbox  */
    private JLabel messageLabel;    
    private JButton closeButton;
     
    /* The frame that will be closed with the close button  */
    private JFrame mainFrame;
 	
    /**
     * Create the GUI
     * 
     */
	public XpathPanel(JFrame mainFrame) 
	{
		this.mainFrame = mainFrame;
		this.setBorder(BorderFactory.createTitledBorder(" Apply an xpath expression to an input xml file and (optionally) write the result to a chosen file "));
        this.setLayout(new MigLayout("wrap 3")); // we want 3 columns
		
        xpathExpressionLabel = new JLabel("* XPATH Expression:");
		xpathExpressionTextField = new JTextField(50);
		xpathExpressionTextField.setActionCommand("XPATH Expression");
		xpathExpressionTextField.setToolTipText("The XPATH expression to use");  
		xpathExpressionTextField.addActionListener(this);
        
        sourceXMLlabel = new JLabel("* Source XML file:");
        sourceXMLfileTextField = new JTextField();
        sourceXMLfileTextField.setToolTipText("The XML file to process with XPATH");       
        
        outPutFilelabel = new JLabel("Processing output file:");
        destinationXPATHresultTextField = new JTextField();
        destinationXPATHresultTextField.setToolTipText("The filename where write the output of the XPATH processing");
         
        changeButton = new JButton("Choose");
        changeButton.addActionListener(this);
        
        browseButton = new JButton("Find");
        browseButton.addActionListener(this);
        
        confirmButton = new JButton("Start Processing");
        confirmButton.addActionListener(this);
        
        reloadXmlButton = new JButton("Refresh XML");
        reloadXmlButton.addActionListener(this);
        
        editChosenXmlbutton = new JButton("Edit input XML");
        editChosenXmlbutton.addActionListener(this);
        
        xmlOutputTextArea = new JTextArea("");
        xmlOutputTextArea.setFont(new Font("Serif", Font.ITALIC, 12));
        xmlOutputTextArea.setLineWrap(true);
        xmlOutputTextArea.setWrapStyleWord(true);
        xmlOutputTextArea.setEditable(false);
        
        //-------------- Text area 1: XML source
        inputRsyntaxtextarea = new RSyntaxTextArea();
        inputRsyntaxtextarea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_XML);
        inputRsyntaxtextarea.setEditable(false);
        inputXmlFileScrollPanel = new RTextScrollPane(inputRsyntaxtextarea);
        inputXmlFileScrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        inputXmlFileScrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        inputXmlFileScrollPanel.setPreferredSize(new Dimension(250, 250));
        inputXmlFileScrollPanel.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("XML input File"), BorderFactory.createEmptyBorder(5, 5, 5, 5)), inputXmlFileScrollPanel.getBorder()));

        //--------------- Text area 2: XPATH Result
        outputRsyntaxtextarea = new RSyntaxTextArea();
        outputRsyntaxtextarea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE);
        outputRsyntaxtextarea.setEditable(false);
        outPutXpathFileScrollPanel = new RTextScrollPane(outputRsyntaxtextarea);
        outPutXpathFileScrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        outPutXpathFileScrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        outPutXpathFileScrollPanel.setPreferredSize(new Dimension(250, 250));
        outPutXpathFileScrollPanel.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("XPATH output"), BorderFactory.createEmptyBorder(5, 5, 5, 5)), outPutXpathFileScrollPanel.getBorder()));
        
        
        //The button with the options about which node value xpath must retrieve
        showNodeNameRadioButton = new JRadioButton("Show Node Name");
        showNodeNameRadioButton.setActionCommand("Show Node Name");
        showNodeNameRadioButton.addActionListener(this);
        
        //NOTE: node value is the same as Show Node Text Content, but node value require that the user insert text() function in the xpath expression
        //EG: /node1/node2/text()
        showNodeValueRadioButton = new JRadioButton("Show Node Value");
        showNodeValueRadioButton.setActionCommand("Show Node Value");
        showNodeValueRadioButton.setToolTipText("To work require use of text() function in the Xpath expression");
        showNodeValueRadioButton.addActionListener(this);
        
        shownodeTextContentRadioButton = new JRadioButton("Show Node Text Content");       
        shownodeTextContentRadioButton.setActionCommand("Show Node Text Content");
        shownodeTextContentRadioButton.addActionListener(this);
        
        // Group the radio button so that only one can be selected
        buttonGroup = new ButtonGroup();
        buttonGroup.add(showNodeNameRadioButton);
        buttonGroup.add(shownodeTextContentRadioButton);
        buttonGroup.add(showNodeValueRadioButton);

        messageLabel = new JLabel();
        messageLabel.setFont(new Font("Serif", Font.BOLD, 15));
        messageLabel.setForeground(Color.RED);
        
        closeButton = new JButton("Close");        
        closeButton.addActionListener(this);        
        
        //------------- Add the components to the JPanel ---------------
        this.add(sourceXMLlabel);
        this.add(sourceXMLfileTextField,"width 800:350:900,growx");
        this.add(browseButton,"width 105");
        
        
        this.add(outPutFilelabel);
        this.add(destinationXPATHresultTextField,"width 800:350:900,growx");
        this.add(changeButton,"width 105"); 
        
        
        this.add(xpathExpressionLabel);
        this.add(xpathExpressionTextField,"width 800:350:900,growx");
        this.add(editChosenXmlbutton,"width 105");
        
        
        GridLayout checkBoxAreaLayout = new GridLayout(1,3);        
        final JPanel checkBoxAreaPanel = new JPanel();
        checkBoxAreaPanel.setLayout(checkBoxAreaLayout);
        checkBoxAreaPanel.add(showNodeValueRadioButton);
        checkBoxAreaPanel.add(showNodeNameRadioButton);
        checkBoxAreaPanel.add(shownodeTextContentRadioButton);
        this.add(checkBoxAreaPanel,"span 3,width 1000,align center");
        
        
        this.add(messageLabel,"span 3, align center");
        
        
        GridLayout textAreaLayout = new GridLayout(1,2);         
        final JPanel xmlTextAreaPanel = new JPanel();
        xmlTextAreaPanel.setLayout(textAreaLayout);            
        xmlTextAreaPanel.add(inputXmlFileScrollPanel);            
        xmlTextAreaPanel.add(outPutXpathFileScrollPanel);
        this.add(xmlTextAreaPanel,"span 3,width 1000, height 650,growx,growy");
        
        
        this.add(reloadXmlButton);        
        this.add(confirmButton);  
        this.add(closeButton,"width 100");  
	}


	/* 
	 * Manage the action of the JButton, JRadioButton objects" etc.
	 */
	public void actionPerformed(ActionEvent e) 
	{
		  final JFileChooser sorceXmlFileChooser;
	      final JFileChooser destinationbFolderFileChooser;  	  
	      
	      if (e.getSource() instanceof JRadioButton)  
		  {		      
		      // Manage the action coming from the checkbox
		      if(e.getActionCommand().equals("Show Node Name")){		    	  
		       	 
		       	  this.setShowNodeNameChecked(true);
		       	  this.setShowNodeValueChecked(false);		       	 
		          this.setShownodeTextContentChecked(false);
		      }
		         
		      if(e.getActionCommand().equals("Show Node Value")){		    	  
		       	 
		       	  this.setShowNodeNameChecked(false);
		       	  this.setShowNodeValueChecked(true);		       	 
		          this.setShownodeTextContentChecked(false);
		      }
		          
		      if(e.getActionCommand().equals("Show Node Text Content")){		    	  
		      	  
		      	  this.setShowNodeNameChecked(false);
		       	  this.setShowNodeValueChecked(false);		       	 
		          this.setShownodeTextContentChecked(true);
		      }		      
		  }
	      
	      //---- Check if user has pressed some button 
	      if (e.getSource() instanceof JButton)  
	      {	    	  
	          // True if the user want choose a source XML file
	          if (e.getActionCommand().equals("Find")) 
	          {
	        	  sorceXmlFileChooser = new JFileChooser();
	        	  sorceXmlFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	        	  sorceXmlFileChooser.setDialogTitle("Choose xml file source");
	        	  sorceXmlFileChooser.setFileFilter(new CustomFileFilter());
	              
	              int value = sorceXmlFileChooser.showOpenDialog(this);
	             
	              // Return value if approved (ie yes, ok) is chosen.
	              if (value == JFileChooser.APPROVE_OPTION)
	              {
	            	  File inputFile = sorceXmlFileChooser.getSelectedFile();
	            	  
	            	  pathSourceXMLfile = inputFile.getAbsolutePath(); 	                 
	            	  sourceXMLfileTextField.setText(pathSourceXMLfile); 
	            	  
	            	  try{
		                    File fileSorg = new File(pathSourceXMLfile);
			        		FileReader fr = new FileReader(fileSorg);
			        		BufferedReader br = new BufferedReader(fr);
			        	  
			        		StringBuilder sb = new StringBuilder();
			        		String startLine = null;
			        		  
			        		while ((startLine = br.readLine()) !=null){
			        		   sb.append(startLine+"\n");
			        		}
			        		  
			        		this.inputRsyntaxtextarea.setText(sb.toString()); 			        		  
			        		  
			        		// clean the output text area from previous results (if any)			        		  
			        		this.outputRsyntaxtextarea.setText("");
			        		// clean old result message
			        		messageLabel.setForeground(Color.GREEN);
				      		messageLabel.setText("");
			        		  
		                }catch (Exception ex) {
		                	this.xmlInputTextArea.setText(ex.toString());
						}	          
	              }
	           
	          }
	          // True if user want choose a output file where write XPATH output
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
	                  
	                   //get the absolute path of the chosen file
	                   pathSourceXMLfile = f.getAbsolutePath(); 
	              
	                   //set the file path in the textField
	                   destinationXPATHresultTextField.setText(pathSourceXMLfile); 	              
	              }	        	   
	          }
	          
	          // Reload XML the same file showed in the text area 
	          if(e.getActionCommand().equals("Refresh XML"))
	          {
	        	 String fileToReload = sourceXMLfileTextField.getText(); 
	        	 FileReader fr = null;
	        	 BufferedReader br = null;
	        	 
            	  try{
	                   File inputFile = new File(fileToReload);
		        	   fr = new FileReader(inputFile);
		        	   br = new BufferedReader(fr);
		        	  
		        	   StringBuilder sb = new StringBuilder();
		        	   String startLine = null;
		        		  
		        	   while ((startLine = br.readLine()) !=null){
		        		  sb.append(startLine+"\n");
		        	   }
		        		  
		        	   this.inputRsyntaxtextarea.setText(sb.toString());
		        		  
		        	   fr.close();
		        	   br.close();
		        	   
		        	   messageLabel.setForeground(Color.GREEN);
				       messageLabel.setText("Input Reloaded successfully");
		        		  
	                }catch (Exception ex) {
	                	this.xmlInputTextArea.setText(ex.toString()); 
					}
	          }   
	          
	          //------ Edit Chosen XSL file with an internal editor
	          if(e.getActionCommand().equals("Edit input XML"))
	          {
		         InputFileEditorGui editor = new InputFileEditorGui(this.sourceXMLfileTextField.getText());
	          }
	          
	          //------ Start XPATH processing
	          if (e.getActionCommand().equals("Start Processing")) 
	          {
	        	  // True if at least one checkbox is marked
	        	  if(isShowNodeNameChecked() || isShowNodeValueChecked() || isShownodeTextContentChecked())
	        	  {
	        		  // reset the message
	        		  this.messageLabel.setText("");
	        		  
	        		  String XMLsource = sourceXMLfileTextField.getText();
	        		  String destinationFile = destinationXPATHresultTextField.getText();
	        		  String xpathExpression = xpathExpressionTextField.getText();
	        	  
	        		  try {	        			  
	        			  // perform some basic validation
        				  if(!XMLsource.endsWith("xml")){
        					  messageLabel.setForeground(Color.RED);
      		      			  messageLabel.setText("Input must be an xml file !");
      		      			  
      		      			  throw new Exception("Input must be an xml file !");
        				  }	        			  
	        			  
	        			  ApplyXPATHexpression applyXPATHexpression = new ApplyXPATHexpression();
	             
	        			  String xpathResult = applyXPATHexpression.execute(XMLsource, destinationFile, xpathExpression, isShowNodeNameChecked(), isShowNodeValueChecked(), isShownodeTextContentChecked());
	        			  this.outputRsyntaxtextarea.setText(xpathResult);
	        			  
	        			  messageLabel.setForeground(Color.GREEN);
			      		  messageLabel.setText("Operation executed successfully");	 
	        	  
	        		  }catch (Exception ex) {
	        			  this.outputRsyntaxtextarea.setText(ex.getMessage());
	        		  }
	           
	        	  } else
	        		  this.messageLabel.setText("Mark a CheckBox !");	        	  
	          }
	          
	          // The user ha pressed the Close button
	          if (e.getActionCommand().trim().equals("Close")){
	        	  
        		  if (mainFrame.isDisplayable()) {                     
        			  mainFrame.dispose();
                  }
        	  }
	          
	      }	     
	}

	
	public boolean isShowNodeValueChecked() {
		return showNodeValueChecked;
	}


	public void setShowNodeValueChecked(boolean showNodeValueChecked) {
		this.showNodeValueChecked = showNodeValueChecked;
	}


	public boolean isShowNodeNameChecked() {
		return showNodeNameChecked;
	}


	public void setShowNodeNameChecked(boolean showNodeNameChecked) {
		this.showNodeNameChecked = showNodeNameChecked;
	}


	public boolean isShownodeTextContentChecked() {
		return shownodeTextContentChecked;
	}


	public void setShownodeTextContentChecked(boolean shownodeTextContentChecked) {
		this.shownodeTextContentChecked = shownodeTextContentChecked;
	}


	public JTextField getSourceXMLfileTextField() {
		return sourceXMLfileTextField;
	}


	public void setSourceXMLfileTextField(JTextField sourceXMLfileTextField) {
		this.sourceXMLfileTextField = sourceXMLfileTextField;
	}

}
