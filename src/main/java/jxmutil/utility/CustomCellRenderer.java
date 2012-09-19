
package jxmutil.utility;

import java.awt.Component;

import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;

/**
 * A custom cell render for the JTree that show the chosen xml file.
 * It show a border around the leaf value
 *
 */
public class CustomCellRenderer implements TreeCellRenderer {
	
	 private JTextField leafRenderer = new JTextField();

	 /* Cell renderer for the no-leaf nodes */
	 private DefaultTreeCellRenderer nonLeafRenderer = new DefaultTreeCellRenderer();
	 
	/**
	 * Constructor
	 */
	public CustomCellRenderer() {
		
	}

	public Component getTreeCellRendererComponent(JTree tree, Object value,	boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		
		 Component returnValue;
		 
		 if (leaf) // only for leaf return the custom renderer
		 {
		    String stringValue = tree.convertValueToText(value, selected, expanded, leaf, row, false);
		    leafRenderer.setText(stringValue);		     
		    leafRenderer.setEnabled(tree.isEnabled());
		     
		    returnValue = leafRenderer;
		 } else {
		    returnValue = nonLeafRenderer.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
		 }
		 return returnValue;
	}

}
