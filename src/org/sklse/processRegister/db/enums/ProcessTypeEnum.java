package org.sklse.processRegister.db.enums;

/**
 * Created by Ô¬Ê¤ÀÚ on Intellij IDEA
 *
 * @date 2015/4/6
 * @email 745861642@qq.com
 * @description
 */
public enum ProcessTypeEnum {
    owls(1001), epc(1002);

    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    ProcessTypeEnum(int value) {
        this.value = value;
    }

    public static ProcessTypeEnum fromInt(int value) {
        for (ProcessTypeEnum processTypeEnum : values()) {
            if (processTypeEnum.getValue() == value) {
                return processTypeEnum;
            }
        }
        return null;
    }
}
