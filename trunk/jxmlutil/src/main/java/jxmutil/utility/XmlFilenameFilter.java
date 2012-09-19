
package jxmutil.utility;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Custom filename filder used to list onlt the xml files in a directory
 * Used
 *
 */
public class XmlFilenameFilter implements FilenameFilter{	

	public boolean accept(File dir, String name) {
		return name.endsWith("xml");		
	}

}
