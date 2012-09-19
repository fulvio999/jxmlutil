
package jxmutil.gui.panel.xml.validator.xsd;

/**
 * Bean that represent the validation result about an xml input file
 *
 */
public class ValidationResultBean {
	
    private String xmlfileName;	
	private String validationResult;
	private String errorDescription;

	/**
	 * Constructor
	 */
	public ValidationResultBean() {
		
	}
	

	public String getXmlfileName() {
		return xmlfileName;
	}

	public void setXmlfileName(String xmlfileName) {
		this.xmlfileName = xmlfileName;
	}

	public String getValidationResult() {
		return validationResult;
	}

	public void setValidationResult(String validationResult) {
		this.validationResult = validationResult;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

}
