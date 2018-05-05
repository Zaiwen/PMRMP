package org.sklse.processRegister.expander.impl;

import junit.framework.TestCase;
import ontology.IdWorker;
import org.sklse.processRegister.db.dto.ConditionDTO;
import org.sklse.processRegister.db.dto.PluginInfoDTO;
import org.sklse.processRegister.db.dto.ProcessDTO;
import org.sklse.processRegister.db.enums.*;
import org.sklse.processRegister.db.services.PluginInfoService;
import org.sklse.processRegister.expander.ProcessGraphExpander;
import org.sklse.processRegister.expander.ProcessGraphIOService;
import org.sklse.processRegister.expander.service.ProcessGraphExpanderService;
import org.sklse.processRegister.processGraph.EventNode;
import org.sklse.processRegister.processGraph.ProcessGraph;
import org.sklse.processRegister.processGraph.ProcessNode;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Ԭʤ�� on Intellij IDEA
 *
 * @date 2015/4/4
 * @email 745861642@qq.com
 * @decription
 */
public class ProcessGraphExpanderImplTest extends TestCase {

    ProcessGraphExpanderImpl processGraphExpander = new ProcessGraphExpanderImpl();
    ProcessGraphExpanderService service = ProcessGraphExpanderService.instance;
    ProcessGraph processGraph = getDebugProcessGraph();
    PluginInfoDTO pluginInfoDTO =getDebugpluginProcessDTO();

    ProcessGraphIOService processGraphIOService=ProcessGraphIOService.intance;
    public void testGetExtensibilityPoint() throws Exception {
        List<ProcessNode> processNodes = processGraphExpander.conditionQuery(processGraph, getSingleConditionDTO(1), null);
        assert processNodes.size() > 0;
    }

    public  void test() throws Exception {
        ProcessGraph processGraph1 = processGraphIOService.ProcessGraphLoad(531528947318800384L);
        PluginInfoDTO pluginInfoDTO1 = PluginInfoService.instance.getPluginInfoDTO(533097097130819584L);
        List<ProcessNode> processNodes= processGraphExpander.getExtensibilityPoint(processGraph1, pluginInfoDTO1);
        assert  processNodes.size()>0;
        processGraphExpander.expandProcessGraph(processGraph1,processNodes,pluginInfoDTO1);
        assert processGraph1.getAllProcessNodes().size()==4;
    }

    public void testExpandProcessGraph() throws Exception {
        List<ProcessNode> processNodes = processGraphExpander.conditionQuery(processGraph, getSingleConditionDTO(1),null);
        PluginInfoDTO pluginInfoDTO = getDebugpluginProcessDTO();
        if (processNodes.size() > 0) {
            processGraphExpander.expandProcessGraph(processGraph, processNodes, pluginInfoDTO);
        }
        assert processGraph.getAllProcessNodes().size() == 1;
    }


    public ProcessGraph getDebugProcessGraph() {
        ProcessDTO mainProcess = new ProcessDTO();
        mainProcess.setId(1001);
        mainProcess.setName("1Be_25fz");
        mainProcess.setUri("http://www.w3.org/1999/XSL/Format");
        ProcessGraph pg = new ProcessGraph();
        pg.representing(mainProcess);
        pg.id(1000);


        EventNode eventNode1 = new EventNode();
        eventNode1.dto().setId(2001);
        eventNode1.dto().setName("Service is accepted");
        pg.addNode(eventNode1);

        EventNode eventNode2 = new EventNode();
        eventNode2.dto().setId(2002);
        eventNode2.dto().setName("Service entry sheet is transmitted");
        pg.addNode(eventNode2);


        ProcessNode processNode = new ProcessNode();
        processNode.dto().setId(3001);
        processNode.dto().setName("Service Entry Sheet");
        processNode.dto().setParam("param");
//        processNode.dto().setPreconditonid(8);
        pg.addNode(processNode);
        pg.addEdge(eventNode1, processNode);
        pg.addEdge(processNode, eventNode2);
        return pg;
    }


    public PluginInfoDTO getDebugpluginProcessDTO()  {
        PluginInfoDTO processDTO = new PluginInfoDTO();
        processDTO.setControllFlowExtensionPattern(ControllFlowExtensionPatternEnum.insertAfter.getValue());
        processDTO.setId(IdWorker.getNextId());
        processDTO.setDataFlowExtensionParttern(DataFlowExtensionPartternEnum.postfix.getValue());
        processDTO.setField(ProcessFieldEnum.hotel.getValue());
        processDTO.setPluginParam("");
        processDTO.setProcessId(1);
        processDTO.setQueryStr1("1");
        processDTO.setQueryStr1("2");
        processDTO.setProcessType(ProcessTypeEnum.epc.getValue());
        processDTO.setSearchExtensibilityPointType(SearchExtensibilityPointTypeEnum.conditionQuery.getValue());

//        processDTO.setAuthor("ysl");
        processDTO.setCreateTime(new Date(new java.util.Date().getTime()));
        return processDTO;
    }

    public ProcessDTO getProcessDTO() {
        ProcessDTO processDTO = new ProcessDTO();
        processDTO.setId(1);
        processDTO.setName("process");
        processDTO.setParam("");
//        processDTO.setPreconditonid(1);
        processDTO.setPostconditionid(2);
        return null;
    }

    public ConditionDTO getSingleConditionDTO(long id) {
        ConditionDTO conditionDTO = new ConditionDTO();
        conditionDTO.setParam("");
        conditionDTO.setId(id);
        conditionDTO.setName("condition");
        conditionDTO.setPositionType(ConditionDTO.PositionTypeEnum.pre.getValue());
        conditionDTO.setSingleType(ConditionDTO.SingleTypeEnum.single.getValue());
        return conditionDTO;
    }


    public ConditionDTO getConnectConditionDTO(long id) {
        ConditionDTO conditionDTO = new ConditionDTO();
        conditionDTO.setParam("");
        conditionDTO.setId(id);
        conditionDTO.setName("condition" + id);
        conditionDTO.setPositionType(ConditionDTO.PositionTypeEnum.pre.getValue());
        conditionDTO.setSingleType(ConditionDTO.SingleTypeEnum.connect.getValue());
        conditionDTO.setPredicate(ConditionPredicateEnum.AND.getValue());

        List<ConditionDTO> conditionDTOs = new ArrayList<>();
        ConditionDTO conditionDTO1 = new ConditionDTO();
        conditionDTO1.setParam("");
        conditionDTO1.setId(12);
        conditionDTO1.setName("condition");
        conditionDTO1.setPositionType(ConditionDTO.PositionTypeEnum.pre.getValue());
        conditionDTO1.setSingleType(ConditionDTO.SingleTypeEnum.single.getValue());

        ConditionDTO conditionDTO2 = new ConditionDTO();
        conditionDTO2.setParam("");
        conditionDTO2.setId(12);
        conditionDTO2.setName("condition");
        conditionDTO2.setPositionType(ConditionDTO.PositionTypeEnum.pre.getValue());
        conditionDTO2.setSingleType(ConditionDTO.SingleTypeEnum.single.getValue());
        conditionDTOs.add(conditionDTO1);
        conditionDTOs.add(conditionDTO2);
        conditionDTO.setConditionDTOList(conditionDTOs);
        return conditionDTO;
    }

}