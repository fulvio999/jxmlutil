package jxmutil.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import jxmutil.gui.menu.AboutMenuPopUp;
import jxmutil.gui.panel.help.HelpPanel;
import jxmutil.gui.panel.javaproperties.JavaPropertiesConverterPanel;
import jxmutil.gui.panel.json.JsonXmlConverterPanel;
import jxmutil.gui.panel.xml.XmlTreePanel;
import jxmutil.gui.panel.xml.validator.xsd.XmlValidatorPanel;
import jxmutil.gui.panel.xpath.XpathPanel;
import jxmutil.gui.panel.xslt.XsltPanel;



/**
 * Main class that create and show the application 
 *
 */
public class JxmlutilGui {
	
	private JFrame mainFrame;

	/**
	 * Main class for the application
	 * Create the full gui and show it
	 */
	public JxmlutilGui() {
		
	try {
			UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName());
	 
	} catch (Exception e) {
		
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		
		} catch (Exception e1) {			
			//e1.printStackTrace();
		}
	}
    
    mainFrame = new JFrame("JXMutiL v1.2");    
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainFrame.setPreferredSize(new Dimension(1100,700));   
    
    
    //------------ Creates a menubar for the JFrame ------------------------
    JMenuBar menuBar = new JMenuBar();    
 
    mainFrame.setJMenuBar(menuBar);
    
    // Define and add a drop down menu to the menubar   
    JMenu aboutMenu = new JMenu("About");
   
    menuBar.add(aboutMenu);
     
    JMenuItem about = new JMenuItem("Info");
    aboutMenu.add(about);
    
    // Show a pop-up window with author and license informations
    about.addActionListener(new ActionListener() {

        public void actionPerformed(ActionEvent event) {
        	AboutMenuPopUp aboutMenuPopUp = new AboutMenuPopUp();
        }
    });    
    
    //-------------- Create the panels  --------------------
    JTabbedPane tabbedPane = new JTabbedPane(); 
    
    XpathPanel xpathPanel = new XpathPanel(mainFrame);
    tabbedPane.addTab("XPATH Processing", xpathPanel);
    
    XsltPanel xsltGui = new XsltPanel(mainFrame);
    tabbedPane.addTab("XSLT Processing", xsltGui);
    
    XmlTreePanel xmlTreeGui = new XmlTreePanel(mainFrame);
    tabbedPane.addTab("XML Tree Viewer", xmlTreeGui);
    
    XmlValidatorPanel xmlValidatorPanel = new XmlValidatorPanel(mainFrame);
    tabbedPane.addTab("XML Validator", xmlValidatorPanel);
    
    JsonXmlConverterPanel jsonJavaConverterPanel = new JsonXmlConverterPanel(mainFrame);
    tabbedPane.addTab("JSON Processing", jsonJavaConverterPanel);
    
    JavaPropertiesConverterPanel javaPropertiesConverterPanel = new JavaPropertiesConverterPanel(mainFrame);
    tabbedPane.addTab("JAVA Properties Processing", javaPropertiesConverterPanel);
    
    HelpPanel helpPanel = new HelpPanel(mainFrame);
    tabbedPane.addTab("<html><b>HELP<b/></html>", helpPanel);
    
    //-------------------------------------
    
    mainFrame.add(tabbedPane);   
    mainFrame.pack();
    mainFrame.setVisible(true);
	}

}
