package org.sklse.processRegister.expander;

import junit.framework.TestCase;
import org.sklse.processRegister.db.dto.ConditionDTO;
import org.sklse.processRegister.db.dto.PluginInfoDTO;
import org.sklse.processRegister.db.enums.ControllFlowExtensionPatternEnum;
import org.sklse.processRegister.db.enums.DataFlowExtensionPartternEnum;
import org.sklse.processRegister.db.enums.ProcessTypeEnum;
import org.sklse.processRegister.db.enums.SearchExtensibilityPointTypeEnum;
import org.sklse.processRegister.db.services.ConditionService;
import org.sklse.processRegister.processGraph.ProcessGraph;
import org.sklse.processRegister.processGraph.ProcessNode;

import java.io.File;
import java.util.List;
import java.util.Set;

/**
 * Created by 袁胜磊 on Intellij IDEA
 *
 * @date 2015/4/26
 * @email 745861642@qq.com
 * @description
 */
public class ProcessGraphIOServiceTest extends TestCase {

    ProcessGraphIOService intance = ProcessGraphIOService.intance;

    public void testProcessGraphrRgister() throws Exception {
        ProcessGraph processGraph = intance.ProcessGraphLoad(527971820939657217L);
        assert processGraph != null;
    }


    public void testProcessGraphLoad() throws Exception {

        ProcessGraph processGraph = intance.ProcessGraphLoad(531528947318800384L);
        Set<ProcessNode> allProcessNodes = processGraph.getAllProcessNodes();
        assert allProcessNodes.size() > 0;
//        intance.ProcessGraphrRgister(processGraph);
    }

    public void testConverts() throws Exception {
        List<Long> converts = intance.converts(new File("E:\\workspace\\Remco'sSearcher\\models\\modelpairs\\1Be_1y63.epml"));
        assert converts.size() > 0;
    }

    /**
     * 测试根据第一个processGraph创建的PluginInfoDTO 是否能复用到第二个processGraph
     *
     * @throws Exception
     */
    public void testCast() throws Exception {
        ProcessGraph processGraph1 = intance.ProcessGraphLoad(529359335886045184L);//334
        ProcessGraph processGraph2 = intance.ProcessGraphLoad(529359350738075648l);//202
        PluginInfoDTO testPluginInfoDTO = getTestPluginInfoDTO(processGraph1);
        assert testPluginInfoDTO != null;
        List<ProcessNode> extensibilityPoint = ProcessGraphExpander.instance.getExtensibilityPoint(processGraph2, testPluginInfoDTO);
        assert extensibilityPoint.size() > 0;
        ProcessGraphExpander.instance.expandProcessGraph(processGraph2, extensibilityPoint, testPluginInfoDTO);
    }


    public PluginInfoDTO getTestPluginInfoDTO(ProcessGraph processGraph) throws Exception {
        Set<ProcessNode> allProcessNodes = processGraph.getAllProcessNodes();
        assert allProcessNodes != null && allProcessNodes.size() > 0;
        for (ProcessNode processNode : allProcessNodes) {
            long postconditionid = processNode.dto().getPostconditionid();
            if (postconditionid > 0L) {
                PluginInfoDTO pluginInfoDTO = new PluginInfoDTO();
                ConditionDTO conditionDTO = ConditionService.instance.getRecursiveConditionById(postconditionid);
                List<ConditionDTO> singleCondition = ConditionService.instance.getSingleCondition(conditionDTO);
                assert singleCondition.size() > 0;
                ConditionDTO conditionDTO1 = singleCondition.get(0);
                pluginInfoDTO.setQueryStr2(Long.toString(conditionDTO1.getId()));
                pluginInfoDTO.setControllFlowExtensionPattern(ControllFlowExtensionPatternEnum.insertBefore.getValue());
                pluginInfoDTO.setDataFlowExtensionParttern(DataFlowExtensionPartternEnum.prefix.getValue());
                pluginInfoDTO.setSearchExtensibilityPointType(SearchExtensibilityPointTypeEnum.conditionQuery.getValue());
                pluginInfoDTO.setProcessId(processNode.getDTOId());
                pluginInfoDTO.setProcessDTO(processNode.dto());
                pluginInfoDTO.setProcessType(ProcessTypeEnum.epc.getValue());
                return pluginInfoDTO;
            }
        }
        return null;
    }

}