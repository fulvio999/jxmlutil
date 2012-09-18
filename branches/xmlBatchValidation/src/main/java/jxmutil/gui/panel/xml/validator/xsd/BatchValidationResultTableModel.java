
package jxmutil.gui.panel.xml.validator.xsd;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

/**
 * Custom table model for the table with the batch validation result 
 *
 */
public class BatchValidationResultTableModel extends AbstractTableModel{	
	
	private static final long serialVersionUID = 1L;

	private String[] columnNames = {"File Name","Valid","Reason"};
	
	/* The list of beans to display in the table: each table row represents a bean (ie each cell is a bean field value) */
	private ArrayList<ValidationResultBean> validationResultBeanList = new ArrayList<ValidationResultBean>();

	/**
	 * constructor
	 */
	public BatchValidationResultTableModel() {
		
	}

	
	public int getRowCount() {		
		return validationResultBeanList.size();
	}

	public int getColumnCount() {		
		return columnNames.length;
	}

	/**
	 * Return the value to insert in a cell, depending on the column index
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		ValidationResultBean tb = validationResultBeanList.get(rowIndex);
		
		switch (columnIndex) {
	      case 0:
	        return tb.getXmlfileName();
	      case 1:
	        return tb.getValidationResult();
	      case 2:
	        return tb.getErrorDescription();	      
	      default:
	        return null;
	   }
	}
	
	public String getColumnName(int col) {
		return columnNames[col];
	}
	

	public String[] getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(String[] columnNames) {
		this.columnNames = columnNames;
	}

	public ArrayList<ValidationResultBean> getValidationResultBeanList() {
		return validationResultBeanList;
	}

	public void setValidationResultBeanList(
			ArrayList<ValidationResultBean> validationResultBeanList) {
		this.validationResultBeanList = validationResultBeanList;
	}

}
