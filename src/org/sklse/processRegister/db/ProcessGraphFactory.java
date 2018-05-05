package org.sklse.processRegister.db;

import ontology.IdWorker;

import org.sklse.processRegister.db.dto.ConditionDTO;
import org.sklse.processRegister.db.dto.ProcessDTO;
import org.sklse.processRegister.processGraph.AnnotatedProcessNode;
import org.sklse.processRegister.processGraph.ProcessNode;

/**
 * Created by Chenlei Yuan
 * Revised by Zaiwen FENG on 12 NOV 2015
 *
 * @date 2015/4/1
 * @email 745861642@qq.com
 * @decription
 */
public class ProcessGraphFactory {
    public static ProcessNode createProcessNode() {
        return new ProcessNode();
    }
    
    /*Create a new annotated process node, just for evaluating prototype*/
    public static AnnotatedProcessNode createAnnotatedProcessNode(){
    	
    	return new AnnotatedProcessNode();
    }

    public static ProcessNode createProcessNode(ProcessDTO processDTO) {
        ProcessDTO clone = processDTO.clone();
        clone.setId(IdWorker.getNextId());
        return new ProcessNode(clone);
    }

    public static ConditionDTO createSingleCondition(String name, ConditionDTO.PositionTypeEnum positionType) throws  Exception{
        ConditionDTO conditionDTO = new ConditionDTO();
        assert name != null;
        assert positionType != null;
        conditionDTO.setId(IdWorker.getNextId());
        conditionDTO.setSingleType(ConditionDTO.SingleTypeEnum.single.getValue());
        conditionDTO.setName(name);
        conditionDTO.setPositionType(positionType.getValue());
        return conditionDTO;
    }
}
