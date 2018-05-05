package org.sklse.processRegister.db.services;

import junit.framework.TestCase;
import org.sklse.processRegister.db.dto.ConditionDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ô¬Ê¤ÀÚ on Intellij IDEA
 *
 * @date 2015/4/17
 * @email 745861642@qq.com
 * @description
 */
public class ConditionServiceTest extends TestCase {
    ConditionService conditionService = ConditionService.instance;

    public void testGetConditionById() throws Exception {

    }


    public void testSimilarToSubCondition() throws Exception {
        ConditionDTO test1 = new ConditionDTO();
        test1.setId(1);
        test1.setParam("");
        test1.setName("test");
        test1.setSingleType(ConditionDTO.SingleTypeEnum.single.getValue());
        test1.setPositionType(ConditionDTO.PositionTypeEnum.pre.getValue());
        ConditionDTO test2 = new ConditionDTO();
        test2.setId(2);
        test2.setParam("");
        test2.setName("test");
        test2.setSingleType(ConditionDTO.SingleTypeEnum.single.getValue());
        test2.setPositionType(ConditionDTO.PositionTypeEnum.pre.getValue());
        assert conditionService.SimilarToSubCondition(test1, test2);
    }

    public void testSimilarToSubCondition1() throws Exception {
        ConditionDTO test1 = new ConditionDTO();
        test1.setId(1);
        test1.setParam("");
        test1.setName("test");
        test1.setSingleType(ConditionDTO.SingleTypeEnum.single.getValue());
        test1.setPositionType(ConditionDTO.PositionTypeEnum.pre.getValue());
        ConditionDTO test2 = new ConditionDTO();
        test2.setId(2);
        test2.setParam("");
        test2.setSingleType(ConditionDTO.SingleTypeEnum.connect.getValue());
        test2.setPositionType(ConditionDTO.PositionTypeEnum.pre.getValue());

        List<ConditionDTO> conditionDTOs = new ArrayList<>();

        ConditionDTO test3 = new ConditionDTO();
        test3.setId(3);
        test3.setParam("");
        test3.setName("test");
        test3.setSingleType(ConditionDTO.SingleTypeEnum.single.getValue());
        test3.setPositionType(ConditionDTO.PositionTypeEnum.pre.getValue());
        ConditionDTO test4 = new ConditionDTO();
        test4.setId(3);
        test4.setParam("");
        test4.setName("test");
        test4.setSingleType(ConditionDTO.SingleTypeEnum.single.getValue());
        test4.setPositionType(ConditionDTO.PositionTypeEnum.pre.getValue());
        conditionDTOs.add(test3);
        conditionDTOs.add(test4);
        test2.setConditionDTOList(conditionDTOs);
        assert conditionService.SimilarToSubCondition(test1, test2);

    }

    public void testSimilarToSubCondition2() throws Exception {
        ConditionDTO test1 = new ConditionDTO();
        test1.setId(1);
        test1.setParam("");
        test1.setName("test");
        test1.setSingleType(ConditionDTO.SingleTypeEnum.connect.getValue());
        test1.setPositionType(ConditionDTO.PositionTypeEnum.pre.getValue());

        List<ConditionDTO> conditionDTOList = new ArrayList<>();
        ConditionDTO test5 = new ConditionDTO();
        test5.setId(3);
        test5.setParam("");
        test5.setName("test");
        test5.setSingleType(ConditionDTO.SingleTypeEnum.single.getValue());
        test5.setPositionType(ConditionDTO.PositionTypeEnum.pre.getValue());
        ConditionDTO test6 = new ConditionDTO();
        test6.setId(3);
        test6.setParam("");
        test6.setName("test");
        test6.setSingleType(ConditionDTO.SingleTypeEnum.single.getValue());
        test6.setPositionType(ConditionDTO.PositionTypeEnum.pre.getValue());
        conditionDTOList.add(test5);
        conditionDTOList.add(test6);
        test1.setConditionDTOList(conditionDTOList);



        ConditionDTO test2 = new ConditionDTO();
        test2.setId(2);
        test2.setParam("");
        test2.setSingleType(ConditionDTO.SingleTypeEnum.connect.getValue());
        test2.setPositionType(ConditionDTO.PositionTypeEnum.pre.getValue());

        List<ConditionDTO> conditionDTOs = new ArrayList<>();

        ConditionDTO test3 = new ConditionDTO();
        test3.setId(3);
        test3.setParam("");
        test3.setName("test");
        test3.setSingleType(ConditionDTO.SingleTypeEnum.single.getValue());
        test3.setPositionType(ConditionDTO.PositionTypeEnum.pre.getValue());
        ConditionDTO test4 = new ConditionDTO();
        test4.setId(3);
        test4.setParam("");
        test4.setName("test");
        test4.setSingleType(ConditionDTO.SingleTypeEnum.single.getValue());
        test4.setPositionType(ConditionDTO.PositionTypeEnum.pre.getValue());
        conditionDTOs.add(test3);
        conditionDTOs.add(test4);
        test2.setConditionDTOList(conditionDTOs);
        assert conditionService.SimilarToSubCondition(test1, test2);

    }

    public void testGetConditionById1() throws Exception {

    }


}