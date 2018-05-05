package org.sklse.owlseditor.json;

/**
 * Created by yuan on 2015/3/23.
 */
public class JConnectorContainer {
    /**
     * 连接符类型 参看：org.sklse.owlseditor.json.JConnectorEnum
     */
    private int connector;
    private JConnectorEnum jconnector = null;

    public int getConnector() {
        return connector;
    }

    public void setConnector(int connector) {
        this.connector = connector;
    }

    public JConnectorEnum getJconnector() {
        if (jconnector == null) {
            jconnector = JConnectorEnum.fromValue(getConnector());
        }
        return jconnector;
    }

    public void setJconnector(JConnectorEnum jconnector) {
        this.jconnector = jconnector;
    }

}
