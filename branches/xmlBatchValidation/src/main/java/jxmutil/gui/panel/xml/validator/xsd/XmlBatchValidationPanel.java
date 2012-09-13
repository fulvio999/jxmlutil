package jxmutil.gui.panel.xml.validator.xsd;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;


/**
 * Create a subpanel of the xsd validation panel where the user can perform a batch xml validation:
 * validate more xml file against the same xsd file
 *
 */
public class XmlBatchValidationPanel extends JPanel implements ActionListener{
	
	/* The parent jpanel that contains this sub-panel */
	private XmlValidatorPanel parentPanel;
	
	// Components used to locate the folder containing the XML files to be validated
	private JLabel sourceXMLfileLabel;
	private JTextField sourceXMLfolderTextField; //the XML file to be validated
	private String folderSourceXMLfile; //the absolute path to the XML chosen file    
	private JButton browseButton;
	
	private JLabel titleLabel;
	
	private JButton confirmBatchValidationButton;

	/**
	 * constructor
	 */
	public XmlBatchValidationPanel(XmlValidatorPanel xmlValidatorPanel) {
		
		super();
		
		this.parentPanel=xmlValidatorPanel;
		this.setLayout(new MigLayout("wrap 3")); //we want 3 column
		
		titleLabel = new JLabel("<html><b>Batch Validation: validate more xml files with the same xsd<b/></html>");
		
		sourceXMLfileLabel = new JLabel("Input Folder:");
		
		confirmBatchValidationButton = new JButton("Batch validation");
		confirmBatchValidationButton.addActionListener(this);
		
		//------ Add the component to the panel -----
		this.add(titleLabel,"wrap 3");
		this.add(confirmBatchValidationButton);
		
	}

	/**
	 * Manage the actions on the buttons
	 */
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() instanceof JButton)  
	    {	
			/* forward to the parent panel the action */
			if (e.getActionCommand().equals("Batch validation")) 
		    {
				this.parentPanel.actionPerformed(e);
		    }
			
	    }
	}
}
