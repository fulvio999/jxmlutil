
package jxmutil.gui.menu;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * A sub-panel that compose the AboutMenuPopUp.
 * Contains some general information about Application
 *
 */
public class GeneralinfoPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private JLabel generalInfoLabel;

	/**
	 * Constructor
	 */
	public GeneralinfoPanel() {
		
		this.setBorder(BorderFactory.createTitledBorder(""));
		
		generalInfoLabel = new JLabel("<html> <br/><b> XMutiL</b> <br/><br/> A simple GUI for XML based activity (xslt, xpath, xml browsing, xml validtion) <br/> See The Help panel of the application for usage  <br/><br/> <b>Author: fulvio999@gmail.com</b> </html>");
		
		this.add(generalInfoLabel);		
	}

}
