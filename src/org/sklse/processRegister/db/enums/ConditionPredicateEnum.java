package org.sklse.processRegister.db.enums;

/**
 * Created by Ô¬Ê¤ÀÚ on Intellij IDEA
 *
 * @date 2015/4/13
 * @email 745861642@qq.com
 * @description
 */
public enum ConditionPredicateEnum {
    AND(1001), OR(1002),XOR(1003);

    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    ConditionPredicateEnum(int value) {
        this.value = value;
    }

    public static ConditionPredicateEnum fromInt(int value) {
        for (ConditionPredicateEnum conditionPredicateEnum : values()) {
            if (conditionPredicateEnum.getValue() == value) {
                return conditionPredicateEnum;
            }
        }
        return null;
    }
}
