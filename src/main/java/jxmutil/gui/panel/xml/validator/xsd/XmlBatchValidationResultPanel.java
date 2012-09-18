
package jxmutil.gui.panel.xml.validator.xsd;

import java.util.ArrayList;
import java.util.logging.Level;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;

import org.jdesktop.swingx.JXErrorPane;
import org.jdesktop.swingx.error.ErrorInfo;

import net.miginfocom.swing.MigLayout;

/**
 * Show the xml batch validation results 
 *
 */
public class XmlBatchValidationResultPanel extends JPanel implements ListSelectionListener{
	
	private static final long serialVersionUID = 1L;

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
		/* Attach a listener on the row to show the validation error */		
		validationResultTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		validationResultTable.setColumnSelectionAllowed(true);  //so that a user can select a single cell
		validationResultTable.setModel(new BatchValidationResultTableModel());
		
		
		validationResultTable.getSelectionModel().addListSelectionListener(this); 
		
		adjustTableWidth(); //adjust the column width
		
		tableListScrollPanel = new JScrollPane(validationResultTable,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		//----- Add the components to the panel
		this.add(tableListScrollPanel,"span 1,width 1050,height 300,align center,growx");	
	}
	
	/**
	 * Manage the selection event on the table cell with the Error message
	 */
	public void valueChanged(ListSelectionEvent e) {
		
		   int selRows;
		   int selCol;
		   String errorMsg; //the value contained in the selected cell

		   if (!e.getValueIsAdjusting()) {  
			   
		      selRows = validationResultTable.getSelectedRow();
		      selCol = validationResultTable.getSelectedColumn();
		 	  BatchValidationResultTableModel tm = (BatchValidationResultTableModel) validationResultTable.getModel();
		 	  
		 	  if(selCol == 2) //only if the user select the error column show the 
		 	  {
		 		 errorMsg = (String) tm.getValueAt(selRows,2); //only for the error column we want the value
		 		 System.out.println("Selection : " + errorMsg );
		 		 ErrorInfo info = new ErrorInfo("Operation Result", "Validation Error", errorMsg, "category", null, Level.ALL, null); 
		         JXErrorPane.showDialog(this,info);	
		 	  }
		   } 		
	}
	
	
	/**
	 * Utility method that set the table column width
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
