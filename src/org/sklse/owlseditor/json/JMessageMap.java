package org.sklse.owlseditor.json;

/**
 * Created by yuan on 2015/3/22.
 */
public class JMessageMap {
    private String ID = null;

    private String groundingParameter = null;

    private String owlsParameter = null;

    public String getID() {
        return ID;
    }

    public void setID(String iD) {
        ID = iD;
    }

    public String getGroundingParameter() {
        return groundingParameter;
    }

    public void setGroundingParameter(String groundingParameter) {
        this.groundingParameter = groundingParameter;
    }

    public String getOwlsParameter() {
        return owlsParameter;
    }

    public void setOwlsParameter(String owlsParameter) {
        this.owlsParameter = owlsParameter;
    }
}
