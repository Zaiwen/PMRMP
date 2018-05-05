package org.sklse.processRegister.db.dto;

import org.utils.JsonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shenlei Yuan Intellij IDEA
 * 
 * Revised by Zaiwen FENG
 *
 * @date 2015/4/13
 * @email 745861642@qq.com; zwfeng@whu.edu.cn
 * @description
 */
public class ConditionDTO {

    private long id;
    /**
     * Condition 类型 {@link SingleTypeEnum}
     */
    private int singleType = -1;
    /**
     * condition  {@link PositionTypeEnum}
     */
    private int positionType = -1;

    /**
     *连接符 {@link org.sklse.processRegister.db.enums.ConditionPredicateEnum }
     * type��single 无意义;
     * type:connect 连接符
     */
    private int predicate = -1;

    /**
     * Actually 'name' means the text label of the precondtion/post-condition
     */
    private String name = "";
    /**
     * type:single 无意义;
     * type:connect 包含condition id
     */
    private String conditionIds = "";
    /**
     * 对应conditionIds
     */
    private List<Long> conditionIdList;
    /**
     * 扩展字段
     */
    private String param = "";

    /**
     * 对应 conditionIds
     */
    private List<ConditionDTO> conditionDTOList;

    private List<ConditionDTO> singleConditionList;

    private List<ConditionDTO> connectConditionList;


    

    public List<ConditionDTO> getSingleConditionList() {
        if (singleConditionList == null) {
            singleConditionList = new ArrayList<>();
            List<ConditionDTO> conditionDTOList = getConditionDTOList();
            for (ConditionDTO conditionDTO : conditionDTOList) {
                if (conditionDTO.getSingleType() == SingleTypeEnum.single.getValue()) {
                    singleConditionList.add(conditionDTO);
                }
            }
//            Collections.sort(singleConditionList, new Comparator<ConditionDTO>() {
//                @Override
//                public int compare(ConditionDTO o1, ConditionDTO o2) {
//                    return o1.getName().compareTo(o2.getName());
//                }
//            });
//            JsonUtil.toJson(conditionIdList);
        }
        return singleConditionList;
    }

    public List<ConditionDTO> getConnectConditionList() {
        if (connectConditionList == null) {
            connectConditionList = new ArrayList<>();
            for (ConditionDTO conditionDTO : getConditionDTOList()) {
                if (conditionDTO.getSingleType() == SingleTypeEnum.single.getValue()) {
                    connectConditionList.add(conditionDTO);
                }
            }
        }
        return connectConditionList;
    }

    public void setConditionIdList(List<Long> conditionIdList) {
        this.conditionIdList = conditionIdList;
    }

    public List<ConditionDTO> getConditionDTOList() {
        if (conditionDTOList == null) {
            conditionDTOList = new ArrayList<>();
        }
        return conditionDTOList;
    }

    public void setConditionDTOList(List<ConditionDTO> conditionDTOList) {
        this.conditionDTOList = conditionDTOList;
    }


    public int getPositionType() {
        return positionType;
    }

    public void setPositionType(int positionType) {
        this.positionType = positionType;
    }

    public int getSingleType() {
        return singleType;
    }

    public void setSingleType(int singleType) {
        this.singleType = singleType;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConditionIds() {
        return conditionIds;
    }

    public void setConditionIds(String conditionIds) {
        this.conditionIds = conditionIds;
    }

    public List<Long> getConditionIdList() {
        if (conditionIdList == null && singleType == SingleTypeEnum.connect.getValue() && conditionIds != null && conditionIds.length() > 0) {
            conditionIdList = JsonUtil.toList(conditionIds, Long.class);
        }
        return conditionIdList;
    }



    public int getPredicate() {
        return predicate;
    }

    public void setPredicate(int predicate) {
        this.predicate = predicate;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public enum SingleTypeEnum {
        single(1001), connect(1002);
        int value;

        SingleTypeEnum(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public static SingleTypeEnum fromint(int value) {
            for (SingleTypeEnum typeEnum : values()) {
                if (typeEnum.getValue() == value) {
                    return typeEnum;

                }
            }
            return null;
        }
    }


    public enum PositionTypeEnum {
        pre(1001), post(1002);
        int value;

        PositionTypeEnum(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public static PositionTypeEnum fromint(int value) {
            for (PositionTypeEnum typeEnum : values()) {
                if (typeEnum.getValue() == value) {
                    return typeEnum;

                }
            }
            return null;
        }
    }

    public static void main(String[] args) {
        List<Long> list = new ArrayList<>();
        list.add(1L);
        list.add(2L);
        System.out.println(JsonUtil.toJson(list));

        String str = "[1,2]";
        List<Long> list1 = JsonUtil.toList(str, Long.class);
        System.out.println(list1);
    }
}
