package org.sklse.owlseditor.json;

/**
 * Created by yuan on 2015/3/22.
 */
public class JBinding {
    private String ID = null;

    private String fromProcess = null;

    private String toParam = null;

    private String theVar = null;

    public String getID() {
        return ID;
    }

    public void setID(String iD) {
        ID = iD;
    }

    public String getFromProcess() {
        return fromProcess;
    }

    public void setFromProcess(String fromProcess) {
        this.fromProcess = fromProcess;
    }

    public String getToParam() {
        return toParam;
    }

    public void setToParam(String toParam) {
        this.toParam = toParam;
    }

    public String getTheVar() {
        return theVar;
    }

    public void setTheVar(String theVar) {
        this.theVar = theVar;
    }
}
