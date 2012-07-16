package jxmutil.utility;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/*
 * Extends the default SaxParser Event Handler to parse an input XML file
 * 
 */
public class CustomSaxParseHandler extends DefaultHandler{

	private DefaultMutableTreeNode base;
	private JTree xmlJTree;
	
	public CustomSaxParseHandler(DefaultMutableTreeNode b, JTree tree) {
		this.base = b;
		this.xmlJTree = tree;
	}
	
	
	public void startElement(String uri, String localName, String tagName, Attributes attr) throws SAXException {

		DefaultMutableTreeNode current = new DefaultMutableTreeNode(tagName);

		base.add(current);
		base = current;

		for (int i = 0; i < attr.getLength(); i++) {
			DefaultMutableTreeNode currentAtt = new DefaultMutableTreeNode(attr.getLocalName(i) + " = "+ attr.getValue(i));
			base.add(currentAtt);
		}
	}

	public void skippedEntity(String name) throws SAXException {
		//System.out.println("Skipped Entity: '" + name + "'");
	}

	public void startDocument() throws SAXException {
		
		 super.startDocument();
		 base = new DefaultMutableTreeNode("XML Viewer"); // The root name
		((DefaultTreeModel) xmlJTree.getModel()).setRoot(base);
	}

	public void characters(char[] ch, int start, int length) throws SAXException {

		String s = new String(ch, start, length).trim();
		
		if (!s.equals("")) {
			
			DefaultMutableTreeNode current = new DefaultMutableTreeNode(s);
			base.add(current);

		}
	}

	public void endElement(String namespaceURI, String localName, String qName) throws SAXException {

		base = (DefaultMutableTreeNode) base.getParent();
	}

	
	public void endDocument() throws SAXException {
		// Refresh JTree
		((DefaultTreeModel) xmlJTree.getModel()).reload();
		expandAll(xmlJTree);
	}
	
	public void expandAll(JTree tree) {
		int row = 0;
		while (row < tree.getRowCount()) {
			tree.expandRow(row);
			row++;
		}
	}

}
