package jxmutil.utility;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * FileFiter for xml, xsd, xsl files
 *
 */
public class CustomFileFilter extends FileFilter{

	
	public CustomFileFilter() {
		
	}

	
	public boolean accept(File f) {
		return f.getName().toLowerCase().endsWith(".xml") || f.isDirectory() || f.getName().toLowerCase().endsWith(".xsl") || f.getName().toLowerCase().endsWith(".xsd");
		
	}

	
	public String getDescription() {
		return "XML XSD or XSL files only";
	}

}
