package org.sklse.processRegister.db.enums;

/**
 * Created by Ԭʤ�� on Intellij IDEA
 *
 * @date 2015/3/31
 * @email 745861642@qq.com
 * @description
 */
public enum DataFlowExtensionPartternEnum {
    biexternal(1001), preexternal(1002), postexternal(1003), prefix(1004), postfix(1005), subsititution(1006), synchronization(1007);
    private int value;

    DataFlowExtensionPartternEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static DataFlowExtensionPartternEnum fromInt(int value) {
        for (DataFlowExtensionPartternEnum DataFlowExtensionPartternEnum : values()) {
            if (DataFlowExtensionPartternEnum.getValue() == value) {
                return DataFlowExtensionPartternEnum;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "DataFlowExtensionPartternEnum{" +
                "value=" + value +
                '}';
    }
}
