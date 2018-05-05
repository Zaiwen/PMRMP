package org.sklse.processRegister.db.enums;

/**
 * Created by Ԭʤ�� on Intellij IDEA
 *
 * @date 2015/4/4
 * @email 745861642@qq.com
 * @description
 */
public enum SearchExtensibilityPointTypeEnum {
    interfaceQuery(1001),conditionQuery(1002),terminologyQuery(1003);

    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    SearchExtensibilityPointTypeEnum(int value) {
        this.value = value;
    }

    public static SearchExtensibilityPointTypeEnum fromInt(int value) {
        for (SearchExtensibilityPointTypeEnum processFieldEnum : values()) {
            if (processFieldEnum.getValue() == value) {
                return processFieldEnum;
            }
        }
        return null;
    }

}
