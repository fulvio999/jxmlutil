package jxmutil.utility;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * FileFiter to select only folders
 *
 */
public class FolderFileFilter extends FileFilter{

	
	public FolderFileFilter() {
		
	}

	
	public boolean accept(File f) {
		return f.isDirectory();	
	}

	
	public String getDescription() {
		return "Folders only";
	}

}
