
package jxmutil.gui.menu;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JPanel;
import javax.swing.JTextArea;


/**
 * A sub-panel that compose the AboutMenuPopUp.
 * Contains informations about the License of this product 
 *
 */
public class LicensePanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private JTextArea textArea;

	/**
	 * Constructor
	 * Load the GLP License from a file and display it
	 */
	public LicensePanel() {		

		textArea = new JTextArea();  
		
		try {

			InputStream is = this.getClass().getClassLoader().getResourceAsStream("XpathCheckerLicense.txt"); 	
	
	        BufferedReader br = new BufferedReader(new InputStreamReader(is));
	        
	        StringBuffer sb = new StringBuffer();
	        String eachLine = br.readLine();
	        
	        while(eachLine != null) {
	            sb.append(eachLine);
	            sb.append("\n");
	            
	            eachLine = br.readLine();
	        }
	        
	        textArea.setText(sb.toString());  
	        this.add(textArea);		
			
		}catch (Exception e) {
			e.printStackTrace();
		}					
		
		
	}

}
