package org.sklse.owlseditor.json;

import java.util.List;

public class JAtomicProcess extends JProcess {

    private String portType = null;

    private String operation = null;

    private String inputMessageName = null;

    private List<JResult> results = null;

    private String outputMessageName = null;

    private String[] inputMessageMap = null;

    private String[] outputMessageMap = null;

    private  JPreConditionList conditionList;

    public JPreConditionList getConditionList() {
        return conditionList;
    }

    public void setConditionList(JPreConditionList conditionList) {
        this.conditionList = conditionList;
    }

    public List<JResult> getResults() {
        return results;
    }

    public void setResults(List<JResult> results) {
        this.results = results;
    }


    public String getPortType() {
        return portType;
    }

    public void setPortType(String portType) {
        this.portType = portType;
    }

    public String getInputMessageName() {
        return inputMessageName;
    }

    public void setInputMessageName(String inputMessageName) {
        this.inputMessageName = inputMessageName;
    }

    public String getOutputMessageName() {
        return outputMessageName;
    }

    public void setOutputMessageName(String outputMessageName) {
        this.outputMessageName = outputMessageName;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String[] getInputMessageMap() {
        return inputMessageMap;
    }

    public void setInputMessageMap(String[] inputMessageMap) {
        this.inputMessageMap = inputMessageMap;
    }

    public String[] getOutputMessageMap() {
        return outputMessageMap;
    }

    public void setOutputMessageMap(String[] outputMessageMap) {
        this.outputMessageMap = outputMessageMap;
    }


}
