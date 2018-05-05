package org.sklse.processRegister.expander.impl;

import java.sql.Date;
import java.util.*;
import java.io.*;

import ontology.IdWorker;

import org.sklse.processRegister.db.dto.*;
import org.sklse.processRegister.db.enums.*;
import org.sklse.processRegister.processGraph.*;

public class ProcessGraphExpanderImplTest {
    static ProcessGraphExpanderImpl processGraphExpander = new ProcessGraphExpanderImpl();
    static ProcessGraph processGraph = getDebugProcessGraph();
    static PluginInfoDTO pluginInfoDTO =getDebugpluginProcessDTO();
    static int insertPattern = 1002;
    static int totalnum = 0;
    
    public static void  main(String[] args) throws Exception {
       
        StringBuffer sb = new StringBuffer();
        double total = 0;
        for(int i = 0; i < 1000; i++){
        	processGraph = getDebugProcessGraph();
        	List<ProcessNode> processNodes = new ArrayList<ProcessNode>();
        	processNodes.add((ProcessNode)processGraph.getNodeById(3001));
        	System.out.println("---------------------");
            double timestart = System.currentTimeMillis();
            System.out.println(timestart);
            PluginInfoDTO pluginInfoDTO = getDebugpluginProcessDTO();
            if (processNodes.size() > 0) {
                processGraphExpander.expandProcessGraph(processGraph, processNodes, pluginInfoDTO);
            }
            System.out.println(processGraph.getAllProcessNodes().size());
            double timeend = System.currentTimeMillis();
            System.out.println(timeend);
            double takentime = timeend - timestart;
            total = total + takentime;
            sb.append(" " + pluginInfoDTO.getControllFlowExtensionPattern() + " " + takentime + "\r\n");
        }
       System.out.println(total/1000);
//        saveTimeResult2File(sb.toString());
    }

    public static void saveTimeResult2File(String content){

        try {
            File file = new File("d:/data.txt");

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();

            System.out.println("Done");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static  ProcessGraph getDebugProcessGraph() {
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
        processNode.dto().setPreconditionid(1);
        pg.addNode(processNode);
        pg.addEdge(eventNode1, processNode);
        pg.addEdge(processNode, eventNode2);
        
        GraphNode gn = eventNode2;
        for(int i = 0; i < totalnum; i++){
        	ProcessNode tempProcessNode = new ProcessNode();
            tempProcessNode.dto().setId(IdWorker.getNextId());
            tempProcessNode.dto().setName("Service Entry Sheet");
            tempProcessNode.dto().setPreconditionid(1);
            pg.addNode(tempProcessNode);
            pg.addEdge(gn,tempProcessNode);
            
            EventNode tempEvent = new EventNode();
            tempEvent.dto().setId(IdWorker.getNextId());
            tempEvent.dto().setName("Service entry sheet is transmitted");
            pg.addNode(tempEvent);
            pg.addEdge(tempProcessNode, tempEvent);
            gn = tempEvent;
        }

        System.out.println(pg.getAllProcessNodes().size());
        return pg;
    }


    public static PluginInfoDTO getDebugpluginProcessDTO()  {
        PluginInfoDTO processDTO = new PluginInfoDTO();
//        Random rd = new Random();
//        int controlcode = rd.nextInt(4);
//        controlcode += 1001;
        processDTO.setControllFlowExtensionPattern(insertPattern);
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
        processDTO.setPreconditionid(1);
        processDTO.setPostconditionid(2);
        return null;
    }

    public static ConditionDTO getSingleConditionDTO(long id) {
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
