package org.sklse.processRegister.db.enums;

/**
 * Created by Ԭʤ�� on Intellij IDEA
 *
 * @date 2015/4/4
 * @email 745861642@qq.com
 * @description
 */
public enum ProcessFieldEnum {
    traffic(1001), logistics(1002), hotel(1003), dailylife(1004);

    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    ProcessFieldEnum(int value) {
        this.value = value;
    }

    public static ProcessFieldEnum fromInt(int value) {
        for (ProcessFieldEnum processFieldEnum : values()) {
            if (processFieldEnum.getValue() == value) {
                return processFieldEnum;
            }
        }
        return null;
    }
}
