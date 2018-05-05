package org.sklse.processRegister.db.services;

import org.sklse.owlseditor.sparqldl.ConditionQuery;
import org.sklse.processRegister.db.dao.ConditionDAO;
import org.sklse.processRegister.db.dto.ConditionDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 袁胜磊 on Intellij IDEA
 * 
 * 
 * @date 2015/4/15
 * @email 745861642@qq.com
 * @description
 */
public class ConditionService {

    public static ConditionService instance = new ConditionService();
    ConditionDAO conditionDAO = ConditionDAO.getDAO();

    /**
     * 根据condition最外层表达式id 构建整个Condition 表达式
     *
     * @param id
     * @return
     * @throws Exception
     */
    public ConditionDTO getRecursiveConditionById(long id) throws Exception {
        return conditionDAO.getRecursiveConditionById(id);
    }
    
    
    
    public boolean isSubof(ConditionDTO cd1 , ConditionDTO cd2) throws Exception{
    	String name1 = cd1.getName();
    	for(ConditionDTO cd : getSingleCondition(cd2)){
    		String name2 = cd.getName();
    		if(name1.equalsIgnoreCase(name2)) return  true;
    	}
    	return false;
    }

    /**
     * 获取一个conditionDTO中所有的single类型的condition 包括本身
     * @param conditionDTO
     * @return
     * @throws Exception
     */
    public List<ConditionDTO> getSingleCondition(ConditionDTO conditionDTO) throws Exception {
        assert conditionDTO != null;
        List<ConditionDTO> conditionDTOs = new ArrayList<>();
        ConditionDTO.SingleTypeEnum singleType = ConditionDTO.SingleTypeEnum.fromint(conditionDTO.getSingleType());
        if(singleType == null){
        	throw new Exception("single type is null");
        }
        assert singleType != null;
        switch (singleType) {
            case single:
                conditionDTOs.add(conditionDTO);
                break;
            case connect:
                for (ConditionDTO dto : conditionDTO.getConditionDTOList()) {
                    conditionDTOs.addAll(getSingleCondition(dto));
                }
                break;
        }
        return conditionDTOs;
    }

    /**
     * 比较第一个ConditionDTO 是否跟第二个ConditionDTO 或者第二个condition包含的ConditionDTO相似
     *
     * @param test1
     * @param test2
     * @return
     */
    public boolean SimilarToSubCondition(ConditionDTO test1, ConditionDTO test2) throws Exception {
        assert test1 != null && test2 != null;
        if (test1.getSingleType() == ConditionDTO.SingleTypeEnum.single.getValue()) {
            if (test2.getSingleType() == ConditionDTO.SingleTypeEnum.single.getValue()) {
                return ConditionQuery.findSimilarity(test1.getName(), test2.getName());
            } else {
                for (ConditionDTO conditionDTO : test2.getConditionDTOList()) {
                    if (SimilarToSubCondition(test1, conditionDTO)) {
                        return true;
                    }
                }
                return false;
            }
        } else {
            int predicate = test1.getPredicate();
            ConditionDTO.SingleTypeEnum singleType2 = ConditionDTO.SingleTypeEnum.fromint(test2.getSingleType());
            assert singleType2 != null;
            switch (singleType2) {
                case single:
                    return false;
                case connect:
                    //fixme 此处算法有误
                    if (predicate == test2.getPredicate()) {
                        return subConditionCompare(test1, test2);
                    }
                    break;
            }
        }
        return false;
    }

    private boolean subConditionCompare(ConditionDTO test1, ConditionDTO test2) throws Exception {
        List<ConditionDTO> test1condition = test1.getConditionDTOList();
        List<ConditionDTO> test2condition = test2.getConditionDTOList();
        if (test1condition.size() > test2condition.size()) {
            for (ConditionDTO conditionDTO : test2condition) {
                if (SimilarToSubCondition(test1, conditionDTO)) {
                    return true;
                }
            }
            return false;
        } else {
            boolean find = true;
            loop:
            for (ConditionDTO conditionDTO : test1condition) {
                int tmp = 0;
                for (int i = 0; i < test2condition.size(); i++) {
                    ConditionDTO conditionDTO1 = test2condition.get(i);
                    if (conditionDTO1.getSingleType() == conditionDTO.getSingleType() && ConditionQuery.findSimilarity(conditionDTO1.getName(), conditionDTO.getName())) {
                        tmp++;
                    }
                    if (tmp > 0) {
                        continue loop;
                    }
                }
                if (tmp == 0) {
                    find = false;
                    break;
                }
            }
            if (!find) {
                for (ConditionDTO conditionDTO : test2condition) {
                    if (SimilarToSubCondition(test1, conditionDTO)) {
                        return true;
                    }
                }
                return false;
            } else {
                return true;
            }
        }
    }
    

    /**
     * 查找匹配 算法有待改进
     *
     * @param simarlarTable
     * @return
     */
    private boolean findAnswer(List<List<Integer>> simarlarTable) {
        boolean result = true;
        for (List<Integer> integers : simarlarTable) {
            result = result && integers.size() > 0;
        }
        return result;
    }
}
