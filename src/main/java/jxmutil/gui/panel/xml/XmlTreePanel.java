package jxmutil.gui.panel.xml;


import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import jxmutil.gui.common.InputFileEditorGui;
import jxmutil.utility.CustomCellRenderer;
import jxmutil.utility.CustomFileFilter;
import jxmutil.utility.CustomSaxParseHandler;
import net.miginfocom.swing.MigLayout;

/**
 * Create the panel that allow to see an input xml file as a tree 
 *
 */
public class XmlTreePanel extends JPanel implements ActionListener{
		
	private static final long serialVersionUID = 1L;
	
	private JLabel sourceXMLlabel;
    private JTextField sourceXMLfileTextField; //the XML file to show
    private String pathSourceXMLfile; //the absolute path to the source file
    
    private JButton browseButton;
    
    // A label used to show messages
    private JLabel messageLabel; 
    
    private JPanel xmlTextAreaPanel;
	private JScrollPane scrollPanel;
	
	private JTree xmlJTree;	
    private JButton closeButton;
    private JButton expandAllTreeButton;
    private JButton compactAllTreeButton;
    
    private JButton reloadXmlbutton; 
    
    /* save the opened xml file after an editing  */
    private JButton editXmlbutton;
    
    // The frame that will be close with the close button
    private JFrame mainFrame;        
	
	/**
	 * Create the GUI
	 */
	public XmlTreePanel(JFrame mainFrame) {
		
		this.setBorder(BorderFactory.createTitledBorder(" Edit xml content "));				
		this.setLayout(new MigLayout("wrap 4")); //say that we want 4 columns
		this.mainFrame = mainFrame;
		    
		sourceXMLfileTextField = new JTextField();
		sourceXMLfileTextField.setToolTipText("The XML file to process with XPATH");
		sourceXMLlabel = new JLabel("* Source XML file:");
		 	
		browseButton = new JButton("Find");
	    browseButton.addActionListener(this);
	        
	    messageLabel = new JLabel();
	    messageLabel.setFont(new Font("Serif", Font.BOLD, 15));
	    messageLabel.setForeground(Color.RED);
	        
	    //-------- the text area with the xml
	    xmlTextAreaPanel = new JPanel();
	    xmlTextAreaPanel.setLayout(new GridLayout(1,1));
	        
	    scrollPanel = new JScrollPane(xmlTextAreaPanel);
	    //----------
	        
	    closeButton = new JButton("Close");	       
	    closeButton.addActionListener(this);
	        
	    expandAllTreeButton = new JButton("Expand All");
	    expandAllTreeButton.addActionListener(this);
	        
	    compactAllTreeButton = new JButton("Compact All");
	    compactAllTreeButton.addActionListener(this);
	        
	    editXmlbutton = new JButton("Edit XML");
	    editXmlbutton.addActionListener(this);
	        
	    reloadXmlbutton = new JButton("Refresh XML");
	    reloadXmlbutton.addActionListener(this);
		    
		//----------- Add the components to the JPanel
	    this.add(sourceXMLlabel);
	    this.add(sourceXMLfileTextField,"width 800,growx"); //min:preferred:max
	    this.add(browseButton,"width 100");
	    this.add(editXmlbutton,"width 150");
	        
	    this.add(messageLabel,"span 4,align center");
	        
	    this.add(scrollPanel,"span 4,height 650,growx,growy");  
	        
	    this.add(expandAllTreeButton,"width 100");
	    this.add(compactAllTreeButton,"width 100");
	    this.add(reloadXmlbutton,"width 100");
	    this.add(closeButton,"width 150");	        
	}

	
	/**
	 * Manage the events on the buttons
	 */
	public void actionPerformed(ActionEvent e) {
		
		  final JFileChooser sorceXmFileChooser;		
		 
	      if (e.getSource() instanceof JButton)  
	      {	    	  
	          // The user want choose a source XML file
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
	            	  
	            	  try {
						this.loadOrRefreshXml(f,true);
					  } catch (Exception e1) {	
						  messageLabel.setText(e1.getMessage());						
					  }        
	              }	           
	          }
	          
	          // Edit the chosen input XML file
	          if(e.getActionCommand().equals("Edit XML"))
	          {
	        	 InputFileEditorGui editor = new InputFileEditorGui(this.sourceXMLfileTextField.getText());
	          }
	          
	          // Refresh the XML showed in the text area after his editing with internal (or external) editor -----
	          if(e.getActionCommand().equals("Refresh XML"))
	          {	 	 
	        	 File  f = new File(this.sourceXMLfileTextField.getText()); 
	        	 try {
					this.loadOrRefreshXml(f,false);
				} catch (Exception e1) {
					messageLabel.setText(e1.getMessage());
				}	               
	          } 
	          
	          // Close the application
	          if (e.getActionCommand().equals("Close"))
	          {	        	  
	      		if (mainFrame.isDisplayable()) {                     
	      		    mainFrame.dispose();
	            }
      	      }
	         
	         // Expand all the tree nodes
	         if (e.getActionCommand().equals("Expand All"))
	         {	        	  
	        	int row = xmlJTree.getRowCount() - 1;
	        	while (row >= 0) {
	        	  xmlJTree.expandRow(row);	        	     
	        	  row--;
	        	}	
      	     }  

	         // Compact all the tree nodes
			 if (e.getActionCommand().equals("Compact All"))
			 {				
				int row = xmlJTree.getRowCount() - 1;	        	
	        	while (row >= 2) {
	        	  xmlJTree.collapseRow(row);	        	      
	        	  row--;
	        	}				
			 }	          
	    } 	
	}
	
    /**
     * Utility method to load in the textarea a chosen xml file o refresh it after an editing operation
     * 
     * @throws SAXException 
     * @throws Exception 
     * 
     */
	private void loadOrRefreshXml(File file, boolean isLoad) throws Exception{
		
	    // Remove the old content displayed
   		this.getXmlTextAreaPanel().removeAll();
   		  
       	// The root node of the JTree. Is a special node that has no parent but can have childs 
       	DefaultMutableTreeNode base = new DefaultMutableTreeNode("XML Viewer");	      		 
	    DefaultTreeModel treeModel = new DefaultTreeModel(base);
	        	 
		xmlJTree = new JTree(treeModel);
		xmlJTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		xmlJTree.setAutoscrolls(true);
		xmlJTree.setCellRenderer(new CustomCellRenderer());
	        	 
		// Create the xml parser
	    SAXParserFactory fact = SAXParserFactory.newInstance();
		SAXParser parser = fact.newSAXParser();	     				
				 
		// Set to the parser the handler (ie a class that extends the default event handler: DefaultHandler)
		File f = new File(this.sourceXMLfileTextField.getText());
		parser.parse(file,new CustomSaxParseHandler(base,xmlJTree));
		
		// By default compact all the tree before show it
		int row = xmlJTree.getRowCount() - 1;
	    // Note: use '2' as fix value to compact only the child and not the root
	    while (row >= 2) {
	        xmlJTree.collapseRow(row);	        	      
	        row--;
	    }  				 
			      			  
		this.getXmlTextAreaPanel().add(xmlJTree);
			     
		// Refresh (and redraw) the panel and show the tree view of the chosen XML file
		this.revalidate();
	        	
		if(!isLoad) //only for refresh xml show a message
		{
		   messageLabel.setForeground(Color.GREEN);
		   messageLabel.setText("Input Reloaded successfully");
		}  
	}
	
	
	public JPanel getXmlTextAreaPanel() {
		return xmlTextAreaPanel;
	}

	public void setXmlTextAreaPanel(JPanel xmlTextAreaPanel) {
		this.xmlTextAreaPanel = xmlTextAreaPanel;
	}

	public JTextField getSourceXMLfileTextField() {
		return sourceXMLfileTextField;
	}

	public void setSourceXMLfileTextField(JTextField sourceXMLfileTextField) {
		this.sourceXMLfileTextField = sourceXMLfileTextField;
	}

	public JScrollPane getScrollPanel() {
		return scrollPanel;
	}

	public void setScrollPanel(JScrollPane scrollPanel) {
		this.scrollPanel = scrollPanel;
	}




}
