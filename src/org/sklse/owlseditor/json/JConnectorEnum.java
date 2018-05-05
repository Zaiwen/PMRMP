package org.sklse.owlseditor.json;

/**
 * Created by yuan on 2015/3/23.
 */
public enum JConnectorEnum {

    AND(1), OR(2), XOR(3);

    JConnectorEnum(int type) {
        this.type = type;
    }

    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public static JConnectorEnum fromValue(int value) {
        for (JConnectorEnum JConnectorEnum : values()) {
            if (JConnectorEnum.getType() == value) {
                return JConnectorEnum;
            }
        }
        return AND;
    }
}
