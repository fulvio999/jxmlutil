package jxmutil.gui.common;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

/**
 * Create the window where the user can edit a file (xml, json, xsl) and save it after the editing
 *
 */
public class InputFileEditorGui extends JDialog implements ActionListener{
	
	private static final long serialVersionUID = 1L;

	//A special text Area (http://fifesoft.com/rsyntaxtextarea) with syntax-highlight functionality for input File text area
    private RSyntaxTextArea xslOutputRsyntaxtextarea;   
    private RTextScrollPane outPutXslFileScrollPanel; 
    
    private JButton saveButton;
    private String xslFileName; //the file to display
    private JLabel messageLabel;    
    private JButton closeButton;

	/**
	 * Constructor
	 */
	public InputFileEditorGui(String fileName) {
		
		super.rootPane.setBorder(BorderFactory.createTitledBorder("Options"));
		
		this.xslFileName = fileName;
        this.setLayout(new GridBagLayout());
		
		xslOutputRsyntaxtextarea = new RSyntaxTextArea();
        xslOutputRsyntaxtextarea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_XML);
        xslOutputRsyntaxtextarea.setEditable(true);
        
        // Load the file to display to edit it
        try {        		
        	 FileReader fr = new FileReader(xslFileName);
        	 BufferedReader br = new BufferedReader(fr);
	  
        	 StringBuilder sb = new StringBuilder();
        	 String startLine = null;
		  
        	 while ((startLine = br.readLine()) !=null){
        		sb.append(startLine+"\n");
        	 }
       		 this.xslOutputRsyntaxtextarea.setText(sb.toString());
       		
        }catch (Exception e) {
        	this.xslOutputRsyntaxtextarea.setText(e.toString());
        } 
        
		//-- Add the text area to a scrollpanel
        outPutXslFileScrollPanel = new RTextScrollPane(xslOutputRsyntaxtextarea);
        outPutXslFileScrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        outPutXslFileScrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        outPutXslFileScrollPanel.setPreferredSize(new Dimension(250, 250));
        outPutXslFileScrollPanel.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Edit File (Editable)"), BorderFactory.createEmptyBorder(5, 5, 5, 5)), outPutXslFileScrollPanel.getBorder()));
        
        saveButton = new JButton("Save File");
        saveButton.addActionListener(this);
        
        // The label that show the save operation result
        messageLabel = new JLabel("");
        messageLabel.setFont(new Font("Serif", Font.BOLD, 15));        
        messageLabel.setVisible(false);
        
        closeButton = new JButton("Close");
        closeButton.addActionListener(this);
        
        //------- Add the components to the JPanel
        GridBagConstraints c = new GridBagConstraints();
        
        c.fill = GridBagConstraints.CENTER;
        c.gridx = 0;  //The col number (horizontal axis) 
        c.gridy = 0;  //The row number (vertical axis)
        c.weightx = 1;
        c.weighty = 0.5;
        c.gridwidth = 2; 
        this.add(messageLabel, c);
        
        //--- 1st row
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;  
        c.gridy = 1; 
        c.weightx = 1;
        c.weighty = 10;
        c.gridwidth = 2; 
        this.add(outPutXslFileScrollPanel, c);
        
        //--- 2nd row
        // Add a sub-panel containing the save and close buttons
        GridLayout commandPanelLayout = new GridLayout(1,2);  
        
        final JPanel commandPanel = new JPanel();
        commandPanel.setLayout(commandPanelLayout);
      
        commandPanel.add(saveButton,c);        
        commandPanel.add(closeButton,c);
        
        c.fill = GridBagConstraints.CENTER;
        c.gridx = 0; //The col number (horizontal axis) 
        c.gridy = 2;
        c.weightx = 0;
        c.weighty = 0;
        c.gridwidth = 2; 
        this.add(commandPanel, c); 
        
        //set the dialog as a modal window
        this.setModalityType(ModalityType.APPLICATION_MODAL);

        this.setTitle("File Editor");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(1000,600);  			
        this.setVisible(true);
	}

    /**
     * Manage the action on the buttons
     */
	public void actionPerformed(ActionEvent e) {
		 
	      if (e.getSource() instanceof JButton)  
	      {        
	          if (e.getActionCommand().equals("Save File")) 
	          {	        	  
	            	File xslFile = new File(getXslFileName());
	            	xslFile.delete();	            	
	        	  
	            	try{	            		  
		            	 xslFile.createNewFile(); 
		            		  
		            	 FileWriter fw = new FileWriter(xslFile);
		      			 BufferedWriter bw  = new BufferedWriter(fw);
		      			 PrintWriter pw = new PrintWriter(bw);
		      				
		      			 //-- Write the new content
		      			 String textToWrite = this.xslOutputRsyntaxtextarea.getText();
		      			 pw.print(textToWrite);
	
		      			 messageLabel.setText("File saved Successfully !");
		      			 messageLabel.setForeground(Color.GREEN);
		      			 messageLabel.setVisible(true);
		      				
		      			 pw.close();
		      			 bw.close();
		      			 fw.close();
			        		  
		               }catch (Exception ex) {
		                    messageLabel.setText("Error saving file: "+ex.toString());
		                	messageLabel.setForeground(Color.RED);
			      			messageLabel.setVisible(true);  			
			      			
					  }   	           
	        }	
	          
	        //-- True if the user ha pressed the Close button
	        if (e.getActionCommand().equals("Close")){	        	  
	        	this.setVisible(false);
	    		this.dispose();
      	   }
	}

}


	public String getXslFileName() {
		return xslFileName;
	}


	public void setXslFileName(String xslFileName) {
		this.xslFileName = xslFileName;
	}
	
}	
	
