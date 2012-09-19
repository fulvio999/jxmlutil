package jxmutil.gui.panel.xml.validator.xsd;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;


/**
 * A sub-panel of the xsd validation panel which the functionality to perform a batch xml validation (ie validate more xml file against the same xsd file)
 * instead of the default single file one.
 * It is showed only when the user choose the checkbox for the batch validation. Otherwise is hidden by default
 */
public class XmlBatchValidationPanel extends JPanel implements ActionListener{	
	
	private static final long serialVersionUID = 1L;

	/* The parent jpanel that contains this sub-panel used to forward the handled Actions */
	private XmlValidatorPanel parentPanel;
	
	// Components used to locate the folder containing the XML files to be validated
	private JLabel sourceXMLfileLabel;
	private JTextField sourceXMLfolderTextField; //the XML file to be validated	
	private JButton browseButton;
	
	/* The title of the panel */
	private JLabel titleLabel;
	
	private JButton confirmBatchValidationButton;

	/**
	 * constructor
	 */
	public XmlBatchValidationPanel(XmlValidatorPanel xmlValidatorPanel) {
		
		super();
		
		this.parentPanel = xmlValidatorPanel;
		this.setLayout(new MigLayout("wrap 4")); //we want 4 column
		
		titleLabel = new JLabel("<html><b>Batch Validation: validate more xml files with the same xsd<b/></html>");
		
		sourceXMLfileLabel = new JLabel("* Input Folder:");
		sourceXMLfolderTextField = new JTextField();
		browseButton = new JButton("Browse");
		browseButton.addActionListener(this);
		
		confirmBatchValidationButton = new JButton("Validate");
		confirmBatchValidationButton.setActionCommand("Batch Validation");
		confirmBatchValidationButton.addActionListener(this);
		
		//------ Add the component to the panel ---------
		this.add(titleLabel,"span 4,align center");
		
		this.add(sourceXMLfileLabel,"align left");
		this.add(sourceXMLfolderTextField,"width 700:350:700,span 2,gapleft 20");
		this.add(browseButton,"width 100");
		
		this.add(new JLabel(""),"span 3"); //place-holder
		this.add(confirmBatchValidationButton,"width 100,gapleft 100");		
	}

	/**
	 * Manage the actions on the buttons
	 */
	public void actionPerformed(ActionEvent e) {		
		
		if (e.getSource() instanceof JButton)  
	    {	
			/* forward to the parent panel the action */
			if (e.getActionCommand().equalsIgnoreCase("Batch Validation")) 
		    {
			   this.parentPanel.actionPerformed(e);
		    }
			
			if (e.getActionCommand().equals("Browse")) 
		    {
			   this.parentPanel.actionPerformed(e);
		    }
			
	    }
	}

	public JTextField getSourceXMLfolderTextField() {
		return sourceXMLfolderTextField;
	}

	public void setSourceXMLfolderTextField(JTextField sourceXMLfolderTextField) {
		this.sourceXMLfolderTextField = sourceXMLfolderTextField;
	}
}
