package org.sklse.processRegister.db.enums;

/**
 * Created by Ô¬Ê¤ÀÚ on Intellij IDEA
 *
 * @date 2015/3/31
 * @email 745861642@qq.com
 * @description
 */
public enum ResourceTypeEnum {
    pre(1001), post(1002);

    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    ResourceTypeEnum(int value) {
        this.value = value;
    }

    public static ResourceTypeEnum fromInt(int value) {
        for (ResourceTypeEnum resourceTypeEnum : values()) {
            if (resourceTypeEnum.getValue() == value) {
                return resourceTypeEnum;
            }
        }
        return null;
    }
}
