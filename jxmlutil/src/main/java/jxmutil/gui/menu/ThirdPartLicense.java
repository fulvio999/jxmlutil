
package jxmutil.gui.menu;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.net.URI;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import org.jdesktop.swingx.JXHyperlink;

/**
 * Create a panel with the informations about the third part library used
 *
 */
public class ThirdPartLicense extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
    private static String rsyntaxtextareaUrl = "http://fifesoft.com/rsyntaxtextarea/";    
    
    private static String xalanUrl = "http://xml.apache.org/xalan-j/#license";
    
    private static String xercesUrl = "http://www.apache.org/licenses/LICENSE-2.0";
    
    private static String swingXurl = "http://swingx.java.net/";
    
    private static String jsonLiburl = "http://json-lib.sourceforge.net/index.html";
    
    private static String migLayouturl = "http://www.miglayout.com/";
    
    private static String toolTipMessage = "Open with the browser";

	/**
	 * Constructor
	 */
	public ThirdPartLicense() {		
		
		 this.setBorder(BorderFactory.createTitledBorder("Third Part Licenses"));		       
		 this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS)); 
		 
		 //------------ License for rsyntaxtextarea ------------
		 String smsHelpMsg = "<html><b><br/>For rsyntaxtextarea see:</b> <br/> </html>";
		 
		 this.add(new JLabel(smsHelpMsg));		
		 this.add(createLink(rsyntaxtextareaUrl, toolTipMessage));		 
		 this.add(new JLabel("<html><br/></html>"));
		
		 this.add(new JSeparator(SwingConstants.HORIZONTAL));		 
		 
		 //----------- License for Apache XALAN  ---------------
		 String xalanLicenseMsg = "<html><b><br/>For the Apache Xalan see: </b> <br/>  </html>";
		 
		 this.add(new JLabel(xalanLicenseMsg));			
		 this.add(createLink(xalanUrl, toolTipMessage));
		 this.add(new JLabel("<html><br/></html>"));
		 
		 this.add(new JSeparator(SwingConstants.HORIZONTAL));
		 
		 //----------- License for Apache XERCES -------------- 
		 String xercesLicenseMsg = "<html><b><br/>For the Apache Xerces see: </b> <br/>  </html>";
		 
		 this.add(new JLabel(xercesLicenseMsg));			 	 
		 this.add(createLink(xercesUrl, toolTipMessage));
		 this.add(new JLabel("<html><br/></html>"));
		 
		 this.add(new JSeparator(SwingConstants.HORIZONTAL));		 
		 
		 //----------- License for swingX library -------------
		 String swingxMsg = "<html><b><br/>For the Swingx library see: </b> <br/> </html>";
		 
		 this.add(new JLabel(swingxMsg));		 
		 this.add(createLink(swingXurl, toolTipMessage));
		 this.add(new JLabel("<html><br/></html>"));
		 
		 this.add(new JSeparator(SwingConstants.HORIZONTAL));

		 
		 //--------- License for json-lib -------------
         String jsonLibMsg = "<html><b><br/>For the Json-lib library see: </b> <br/> </html>";
		 
		 this.add(new JLabel(jsonLibMsg));		 
		 this.add(createLink(jsonLiburl, toolTipMessage));
		 this.add(new JLabel("<html><br/></html>"));
		 
		 this.add(new JSeparator(SwingConstants.HORIZONTAL));
		 
		 //--------- License for MigLayout  ---------------
		 
         String migLayoutMsg = "<html><b><br/>For the Miglayout library see: </b> <br/> </html>";
		 
		 this.add(new JLabel(migLayoutMsg));		 
		 this.add(createLink(migLayouturl, toolTipMessage));
		 this.add(new JLabel("<html><br/></html>"));
		 
		 this.add(new JSeparator(SwingConstants.HORIZONTAL));
				
		 // dummy placeholder for layout adjausting
		 this.add(new JLabel("<html><br/><br/><br/></html>"));
		 this.add(new JLabel("<html><br/><br/><br/></html>"));
		
	}
	
	/**
	 * utility method to create html link with a tooltip message associated
	 * @param url
	 * @param toolTip
	 * @return
	 */
	private JXHyperlink createLink(String url, String toolTip){
		
		SampleAction action = new SampleAction(url);		
		//JXHyperlink is special Component offered by Swingx library
		JXHyperlink link = new JXHyperlink(action);
		link.setToolTipText(toolTip);
		
		return link;
	}
	
	
	/**
	 * Action that handle the click event on the html link produced with Swingx library. That link are placed in the Help panel 
	 */
	private class SampleAction extends AbstractAction {
		
		private static final long serialVersionUID = 1L;

		/* Constructor */
		public SampleAction(String url) {			
			super.putValue(Action.NAME, url); //the url to open
			super.putValue(Action.SHORT_DESCRIPTION, ""); //the tooltip showed over the link
		}

		public void actionPerformed(ActionEvent e) {
			
			 //System.out.println("Clicked the link: "+super.getValue(Action.NAME));
			
			 if (Desktop.isDesktopSupported()) {
			      try {
			    	//open the default browser (if the running JRE support this features)
			        Desktop.getDesktop().browse(new URI((String)super.getValue(Action.NAME)));	
			        
			      } catch (Exception ex) {
			    	  
			    	 //---- If the "Desktop" functionality is not supported:using a workaround ---- 
			    	  
			    	 String osName = System.getProperty("os.name");
		             try {
		                     if (osName.startsWith("Windows"))
		                         Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + super.getValue(Action.NAME));
		                     
		                    else {
		                         String[] browsers = { "firefox", "opera", "konqueror","epiphany", "mozilla", "netscape" };
		                         String browser = null;
		                             for (int count = 0; count < browsers.length && browser == null; count++)
		                                  if (Runtime.getRuntime().exec(new String[] { "which", browsers[count] }).waitFor() == 0)
		                                      browser = browsers[count];
		                             Runtime.getRuntime().exec(new String[] { browser, (String) super.getValue(Action.NAME) });
		                        }
		                } catch (Exception exc) {
		                        JOptionPane.showMessageDialog(null, "Error in opening browser"+ ":\n" + exc.getLocalizedMessage());
		                }			    	  
			    	  
			      }
			 } else { 
				 JOptionPane.showMessageDialog(null, "Error in opening browser"+ ":\n" + (String) super.getValue(Action.NAME)+ " Open manually");			       	
			 }			
		}
	}

}
