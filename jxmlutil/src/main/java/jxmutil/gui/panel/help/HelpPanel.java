
package jxmutil.gui.panel.help;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

/**
 * Create the Help panel with some usage informations
 *
 */
public class HelpPanel extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	 // The frame that will be close with the close button
    private JFrame mainFrame;

	/**
	 * Constructor
	 */
	public HelpPanel(JFrame mainframe) {
		
		 this.setBorder(BorderFactory.createTitledBorder("Help"));		       
		 this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS)); 		 
		 this.mainFrame = mainframe;
		 
		 //------------- Premise section ----------
		 String premiseString = "<html> For the XML parsing and validation is used <b>Apache XERCES</b> <br/>" +
		 		"As XSLT and XPATH processor is used <b>Apache XALAN</b> <br/>" +
		 		"The xml to json conversion (and vice versa) is performed with the library <b>json-lib</b> <br/><br/> </html>";
		 
		 this.add(new JLabel(premiseString));
		 this.add(new JSeparator(SwingConstants.HORIZONTAL));	
		 
		 //------------- XPATH Panel --------------
		 String smsHelpMsg = "<html><b> XPATH Processing:</b>  <br/> </html>";
		 
		 this.add(new JLabel(smsHelpMsg));		 
		 this.add(new JLabel("<html>" +
					 		"- Select an input xml file <br/> - Optionally chose a file where write the output produced by the processing <br/> " +
					 		"- Insert a valid XPATH expression (eg: /nodeone/nodetwo/nodethree ) <br/> " +
					 		"- Select a checkbox according with the wanted result type: <br/> " +
					 		"<ul> " +
					 		"<li>Show Node Value: show the value of the node that match the xpath expression</li>" +
					 		"<li>Show Node Name: show the name of the node that match the xpath expression</li> " +
					 		"<li>Show Node Text Content: show the content of the node that match the xpath expression</li>  " +
					 		"</ul> " +
					 		" (see Java documentation for details) <br/>" +
					 		"- Press \"Start Procesing\" button " +
					 		"- Press \"Refresh XML\" button to reload the input xml file after an external modification <br/>" +
					 		"</html>"));
		
		 this.add(new JSeparator(SwingConstants.HORIZONTAL));		 
		 
		 //------------- XSLT Panel ---------------
		 String xalanLicenseMsg = "<html> <b> XSLT Processing: </b> <br/>  </html>";
		 
		 this.add(new JLabel(xalanLicenseMsg));			
		 this.add(new JLabel("<html>Similar at the XPATH section, except for the fact that you must chose an xsl file to apply at the input xml <br/>" +
		 					 "The chosen xsl file can be edited presing the \"Edit View xsl file \" button  <br/>" +
		 					 " </html>"));
		 
		 this.add(new JSeparator(SwingConstants.HORIZONTAL));
		 
		 //------------- XML Tree Panel -------------
		 String xmlTreeMsg = "<html> <b> XML Tree Viewer: </b> <br/> </html>";
		 
		 this.add(new JLabel(xmlTreeMsg));		 
		 this.add(new JLabel("<html>Choose an xml to browse it as a tree and edit his node values <br/> </html>"));
		 
		 this.add(new JSeparator(SwingConstants.HORIZONTAL));		 
		 
		 //------------ XML Validator ---------------		 
         String xmlValidatorMsg = "<html><b>XML Validator: </b>  </html>";
		 
		 this.add(new JLabel(xmlValidatorMsg));		 
		 this.add(new JLabel("<html> -- Single file validation: choose an xml file and an xsd to validated against it. <br/> -- Batch validation: select an xsd and a folder with the xml to check </html>"));		 
		
		 this.add(new JSeparator(SwingConstants.HORIZONTAL));		 
		 
		 //------------ JSON - XML converter ------------
         String jsonXmlconverterMsg = "<html><b>JSON to XML (and vice versa) converter: </b> <br/>  </html>";
		 
		 this.add(new JLabel(jsonXmlconverterMsg));		 
		 this.add(new JLabel("<html>Choose an input file (xml or json) and (optionally) a path where to save the result and the conversion type to perform. <br/> </html>"));		 

		 this.add(new JSeparator(SwingConstants.HORIZONTAL));	
		 
		 
		 // dummy placeholder for layout adjausting
		 this.add(new JLabel("<html><br/></html>"));
//		 
//		 GridBagConstraints c = new GridBagConstraints();	       
//	     
//	        c.fill = GridBagConstraints.WEST;
//	        c.gridx = 0;  //The col number (horizontal axis) 
//	        c.gridy = 0;  //The row number (vertical axis)
//	      
//		 
//		 GridLayout experimentLayout = new GridLayout(1,1);  
//	        
//	     final JPanel xmlTextAreaPanel = new JPanel();
//	     xmlTextAreaPanel.setLayout(experimentLayout);
//	            
//	     xmlTextAreaPanel.add(new JLabel("ww"),c);            
//	     xmlTextAreaPanel.add(closeButton,c);
//		 
//		 this.add(xmlTextAreaPanel);
//		 this.add(new JLabel("<html><br/></html>"));	 
		
	}

	
	public void actionPerformed(ActionEvent e) {
		
		 //-- Check if user has pressed some button 
	     if (e.getSource() instanceof JButton)  
	     {	
			 //-- True if the user ha pressed the Close button
		     if (e.getActionCommand().trim().equals("Close")){
		      	  
		    	 if (mainFrame.isDisplayable()) {                     
		  			  mainFrame.dispose();
		         }
		  	  }
	      }
		
	}

}
