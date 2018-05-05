package org.sklse.owlseditor.json;

public class JPerform {

	private String ID = null;

	private String process = null;

	private String[] inputBinding = null;
	
	private boolean qureyResult=false;
	
	private boolean extended=false;

	public boolean isQureyResult() {
		return qureyResult;
	}

	public void setQureyResult(boolean qureyResult) {
		this.qureyResult = qureyResult;
	}

	public boolean isExtended() {
		return extended;
	}

	public void setExtended(boolean extended) {
		this.extended = extended;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		this.process = process;
	}

	public String[] getInputBinding() {
		return inputBinding;
	}

	public void setInputBinding(String[] inputBindings) {
		this.inputBinding = inputBindings;
	}
}
