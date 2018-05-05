package org.sklse.processRegister.db.enums;

/**
 * Created by Ԭʤ�� on Intellij IDEA
 *
 * @date 2015/3/30
 * @email 745861642@qq.com
 * @description
 */
public enum ControllFlowExtensionPatternEnum {
    /**
     *
     */
    insertBefore(1001),
    /**
     *
     */
    insertAfter(1002),
    /**
     *
     */
    insertParallelTo(1003),
    /**
     *
     */
    insertReplace(1004);
    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    ControllFlowExtensionPatternEnum(int value) {
        this.value = value;
    }

    public static ControllFlowExtensionPatternEnum fromInt(int value) {
        for (ControllFlowExtensionPatternEnum ControllFlowExtensionPatternEnum : values()) {
            if (ControllFlowExtensionPatternEnum.getValue() == value) {
                return ControllFlowExtensionPatternEnum;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        ControllFlowExtensionPatternEnum controllFlowExtension = ControllFlowExtensionPatternEnum.fromInt(1002);
        assert controllFlowExtension != null;
        System.out.println(controllFlowExtension.name());
    }
}
