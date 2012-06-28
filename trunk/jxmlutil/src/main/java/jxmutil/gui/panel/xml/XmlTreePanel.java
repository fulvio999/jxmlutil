package jxmutil.gui.panel.xml;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import net.miginfocom.swing.MigLayout;

import jxmutil.utility.CustomFileFilter;
import jxmutil.utility.CustomSaxParseHandler;

/**
 * Create the panel that allow to see an input xml file as a tree 
 *
 */
public class XmlTreePanel extends JPanel implements ActionListener{
		
	private static final long serialVersionUID = 1L;
	
	private JLabel sourceXMLlabel;
    private JTextField sourceXMLfileTextField; //the XML file to be processed by xpath
    private String pathSourceXMLfile; //the absolute path to the source file
    
    private JButton browseButton;
    
    private JPanel xmlTextAreaPanel;
	private JScrollPane scrollPanel;
	
	private JTree xmlJTree;	
    private JButton closeButton;
    private JButton expandAllTreeButton;
    private JButton compactAllTreeButton;
    
    // The frame that will be close with the close button
    private JFrame mainFrame;        
	
	/**
	 * Create the gui showed in the XML Tree tab
	 */
	public XmlTreePanel(JFrame mainFrame) {
		
			this.setBorder(BorderFactory.createTitledBorder(" Show an input xml file as a tree "));				
			this.setLayout(new MigLayout("wrap 3")); //say that we want 3 columns
			this.mainFrame = mainFrame;
		    
		 	sourceXMLfileTextField = new JTextField();
		 	sourceXMLfileTextField.setToolTipText("The XML file to process with XPATH");
		 	sourceXMLlabel = new JLabel("* Source XML file:");
		 	
		 	browseButton = new JButton("Find");
	        browseButton.addActionListener(this);
	        
	        xmlTextAreaPanel = new JPanel();
	        xmlTextAreaPanel.setLayout(new GridLayout(1,1));
	        
	        scrollPanel = new JScrollPane(xmlTextAreaPanel);
	        
	        closeButton = new JButton("Close");
	        closeButton.setPreferredSize(new Dimension(100,33));
	        closeButton.addActionListener(this);
	        
	        expandAllTreeButton = new JButton("Expand All");
	        expandAllTreeButton.addActionListener(this);
	        
	        compactAllTreeButton = new JButton("Compact All");
	        compactAllTreeButton.addActionListener(this);
		    
		 	//----------- Add the components to the JPanel
	        this.add(sourceXMLlabel);
	        this.add(sourceXMLfileTextField,"width 850:350:900,growx"); //min:preferred:max
	        this.add(browseButton,"width 100");
	        
	        this.add(scrollPanel,"span 3,width 1000, height 650,growx,growy");  
	        
	        this.add(expandAllTreeButton);
	        this.add(compactAllTreeButton);
	        this.add(closeButton);

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
	            	  // the absolute path of the choosed file
	            	  pathSourceXMLfile = f.getAbsolutePath(); 	                  
	            	  sourceXMLfileTextField.setText(pathSourceXMLfile); 
	            	  
	            	  try{
	            		    // Remove the old content displayed
	            		    this.getXmlTextAreaPanel().removeAll();
	            		  
		            		// The root node of the JTree. Is a special node that has no parent but can have childs 
		            		DefaultMutableTreeNode base = new DefaultMutableTreeNode("XML Viewer");		            		 
		     	        	DefaultTreeModel treeModel = new DefaultTreeModel(base);
		     	        	 
		     				xmlJTree = new JTree(treeModel);
		     	        	 
		     				// Create the xml parser
		     	        	SAXParserFactory fact = SAXParserFactory.newInstance();
		     				SAXParser parser = fact.newSAXParser();	     				
		     				 
		     				// Set to the parser the handler (ie a class that extends the default event handler: DefaultHandler)
		     				parser.parse(f,new CustomSaxParseHandler(base,xmlJTree));
		     		
		     				// By default compact all the tree before show it
		     				int row = xmlJTree.getRowCount() - 1;
		  	        	    //Note: use '2' as fix value to compact only the child and not the root
		  	        	    while (row >= 2) {
		  	        	       xmlJTree.collapseRow(row);	        	      
		  	        	       row--;
		  	        	    }  				 
		     			      			  
		     			    this.getXmlTextAreaPanel().add(xmlJTree);
		     			     
		     			    //To refresh (and redraw) the panel and show the tree view of the chosen XML file
		     			    this.revalidate();		            		  
				        		  
			             }catch (Exception ex) {
			                this.getXmlTextAreaPanel().add(new JLabel("Error: "+ex.toString()));
						 } 	          
	              }	           
	          }
	          
	          // Close the application
	          if (e.getActionCommand().equals("Close")){
	        	  
	      		if (mainFrame.isDisplayable()) {                     
	      		    mainFrame.dispose();
	            }
      	     }
	         
	         // Expand all the tree node 
	         if (e.getActionCommand().equals("Expand All")){
	        	  
	        	  int row = xmlJTree.getRowCount() - 1;
	        	  while (row >= 0) {
	        		xmlJTree.expandRow(row);	        	     
	        	    row--;
	        	  }	
      	     }

	         // Compact all the tree node
			 if (e.getActionCommand().equals("Compact All")){
				
				int row = xmlJTree.getRowCount() - 1;	        	
	        	while (row >= 2) {
	        	  xmlJTree.collapseRow(row);	        	      
	        	  row--;
	        	}				
			}
	          
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
