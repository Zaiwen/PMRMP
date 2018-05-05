package org.sklse.owlseditor.xml;

public class XWsdl_Grounding {
	private String Wsdl_Location=null;
	private String Operation=null;
	private String InputMessageName=null;
	private String OutputMessageName=null;
	private String portType=null;
	public String getPortType() {
		return portType;
	}
	public void setPortType(String portType) {
		this.portType = portType;
	}
	public String getInputMessageName() {
		return InputMessageName;
	}
	public void setInputMessageName(String inputMessageName) {
		InputMessageName = inputMessageName;
	}
	public String getOutputMessageName() {
		return OutputMessageName;
	}
	public void setOutputMessageName(String outputMessageName) {
		OutputMessageName = outputMessageName;
	}
	public String getWsdl_Location() {
		return Wsdl_Location;
	}
	public void setWsdl_Location(String wsdlLocation) {
		Wsdl_Location = wsdlLocation;
	}
	public String getOperation() {
		return Operation;
	}
	public void setOperation(String operation) {
		Operation = operation;
	}

}
