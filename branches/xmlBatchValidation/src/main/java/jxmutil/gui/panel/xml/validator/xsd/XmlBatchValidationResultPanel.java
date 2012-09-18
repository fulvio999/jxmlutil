
package jxmutil.gui.panel.xml.validator.xsd;

import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;

import net.miginfocom.swing.MigLayout;

/**
 * Show the xml batch validation results 
 *
 */
public class XmlBatchValidationResultPanel extends JPanel {
	
	/* The list validation results */
	private JTable validationResultTable;
	
	/* The scrollPanel which contains the list with the validation results */
	private JScrollPane tableListScrollPanel;

	/**
	 * constructor
	 */
	public XmlBatchValidationResultPanel() {
		
		super();
		
		this.setLayout(new MigLayout("wrap 1")); //we want 1 column
		
		validationResultTable = new JTable();				
		validationResultTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		validationResultTable.setModel(new BatchValidationResultTableModel());
		
		adjustTableWidth();
		
		tableListScrollPanel = new JScrollPane(validationResultTable,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		//----- Add the components to the panel
		this.add(tableListScrollPanel,"span 1,width 1050,height 300,align center,growx");	
	}
	
	
	/**
	 * Utility method that set the table width
	 * @param table
	 */
	private void adjustTableWidth(){
		
		//the width of the checkbox column
		TableColumn firstCol = this.validationResultTable.getColumnModel().getColumn(0);		
		firstCol.setPreferredWidth(150);
				
		TableColumn secondCol = this.validationResultTable.getColumnModel().getColumn(1);
		secondCol.setPreferredWidth(100);
				
		TableColumn thirdCol = this.validationResultTable.getColumnModel().getColumn(2);
		thirdCol.setPreferredWidth(800);
	}

	
	public JTable getValidationResultTable() {
		return validationResultTable;
	}

	public void setValidationResultTable(JTable validationResultTable) {
		this.validationResultTable = validationResultTable;
	}

	public JScrollPane getTableListScrollPanel() {
		return tableListScrollPanel;
	}

	public void setTableListScrollPanel(JScrollPane tableListScrollPanel) {
		this.tableListScrollPanel = tableListScrollPanel;
	}

}
