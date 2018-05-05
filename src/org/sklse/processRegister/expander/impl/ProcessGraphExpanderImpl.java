package org.sklse.processRegister.expander.impl;

import ontology.IdWorker;

import org.jgrapht.graph.DefaultEdge;
import org.sklse.owlseditor.sparqldl.VariableQuery;
import org.sklse.processRegister.db.ProcessGraphFactory;
//import org.sklse.processRegister.db.dao.ProcessDAO;
import org.sklse.processRegister.db.dto.APGProcessDTO;
import org.sklse.processRegister.db.dto.ConditionDTO;
import org.sklse.processRegister.db.dto.PluginInfoDTO;
import org.sklse.processRegister.db.dto.ProcessDTO;
import org.sklse.processRegister.db.enums.ControllFlowExtensionPatternEnum;
import org.sklse.processRegister.db.enums.DataFlowExtensionPartternEnum;
import org.sklse.processRegister.db.enums.SearchExtensibilityPointTypeEnum;
import org.sklse.processRegister.db.services.ConditionService;
import org.sklse.processRegister.expander.ProcessGraphExpander;
import org.sklse.processRegister.expander.service.ProcessGraphExpanderService;
import org.sklse.processRegister.processGraph.*;

import com.hp.hpl.jena.ontology.OntModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by 袁胜磊 on Intellij IDEA
 * Revised by 冯在文  from 7 Nov. 2015
 *
 * @date 2015/3/26
 * @email 745861642@qq.com
 * @description
 */
public class ProcessGraphExpanderImpl implements ProcessGraphExpander {

    private static ProcessGraphExpanderService expanderService = ProcessGraphExpanderService.instance;

    private static ConditionService conditionService = ConditionService.instance;

    public ProcessGraphExpanderImpl() {
    }


    /*This function is based on Annotated Process Graph and just used to evaluate the prototype*/
    public List<AnnotatedProcessNode> getExtensibilityPoint(AnnotatedProcessGraph apg, PluginInfoDTO pluginInfoDTO, OntModel ontModel) throws Exception {
        assert pluginInfoDTO != null;
        SearchExtensibilityPointTypeEnum extensibilityPointType = SearchExtensibilityPointTypeEnum.fromInt(pluginInfoDTO.getSearchExtensibilityPointType());
        assert extensibilityPointType != null;
        switch (extensibilityPointType) {
            case interfaceQuery:
                
                /*Get the input list of plug-in*/
                List<String> inputList = pluginInfoDTO.getInputList();
            	
            	/*Get the output list of plug-in*/
                List<String> outputList = pluginInfoDTO.getOutputList();
                
                ////******Need to get inputList or outputList from database table directly BY queryStr***TOD*********////
            	
                return interfaceQuery(apg, inputList, outputList, ontModel);
                
            case conditionQuery:
            	//todo conditionQuery
            	return null;
            
            case terminologyQuery:
                //todo terminologyQuery
                return null;
        }
        return null;

    }
    
    
    @Override
    public List<ProcessNode> getExtensibilityPoint(ProcessGraph graph, PluginInfoDTO pluginInfoDTO) throws Exception {
        assert pluginInfoDTO != null;
        SearchExtensibilityPointTypeEnum extensibilityPointType = SearchExtensibilityPointTypeEnum.fromInt(pluginInfoDTO.getSearchExtensibilityPointType());
        assert extensibilityPointType != null;
        switch (extensibilityPointType) {
            case interfaceQuery:
            	String queryStr1_interfaceQuery = pluginInfoDTO.getQueryStr1();//插件查找的条件1, 只针对基于的接口的EP查询，还未映射到数据库中的表，TOD!
                String queryStr2_interfaceQuery = pluginInfoDTO.getQueryStr2();//插件查找的条件2，只针对基于的接口的EP查询，还未映射到数据库中的表，TOD!
                
                /*Get the query input list of plug-in*/
                List<String> inputList = pluginInfoDTO.getInputList();
            	
            	/*Get the query output list of plug-in*/
                List<String> outputList = pluginInfoDTO.getOutputList();
                
                ////******Need to get inputList or outputList from database table directly BY queryStr***TOD*********////
            	
                return null;
                
            case conditionQuery:
                String queryStr1 = pluginInfoDTO.getQueryStr1();//插件查找的条件1, 实际上是_condition表里面condition的id号码
                String queryStr2 = pluginInfoDTO.getQueryStr2();//插件查找的条件2，实际上是_condition表里面condition的id号码
                
                ConditionDTO preconditionDTO = pluginInfoDTO.getPreCondition();
                ConditionDTO postconditionDTO = pluginInfoDTO.getPostCondition();
                
                if (preconditionDTO == null && queryStr1 != null && queryStr1.length() > 0) {
                    preconditionDTO = conditionService.getRecursiveConditionById(Long.parseLong(queryStr1));//得到复合condition对象
                }
                if (preconditionDTO == null && queryStr2 != null && queryStr2.length() > 0) {
                    postconditionDTO = conditionService.getRecursiveConditionById(Long.parseLong(pluginInfoDTO.getQueryStr2()));//得到复合condition对象
                }
                
                return conditionQuery(graph, preconditionDTO, postconditionDTO);
            
            case terminologyQuery:
                //todo terminologyQuery
                return null;
        }
        return null;

    }
    
    /*Here, *'inputList' indicates that the 'eps_designator_type' is 'input_name'****/
    /*'outputList' means that the 'eps_designator_type' is supposed to be 'output_name'*
     * 'ontModel' is the model of the domain-specific ontology***/
    public List<AnnotatedProcessNode> interfaceQuery(AnnotatedProcessGraph apg, List<String> inputList, List<String> outputList, OntModel ontModel){
    	
        List<AnnotatedProcessNode> result = new ArrayList<AnnotatedProcessNode>();
        
        int inputList_PE_num = inputList.size(); // 'inputList_PE_num' is the length of the query input list array of PE
        int outputList_PE_num = outputList.size(); // 'outputList_PE_num' is the length of the query output list array of PE
        
        /*If there is not any 'eps_designator_value' at all, return null directly*/
        if (inputList_PE_num == 0 && outputList_PE_num == 0) {
            return result;
        }
        
        /*Otherwise, traverse all of the process nodes in the process graph in order to find EP*/
        for (AnnotatedProcessNode annotatedprocessNode : apg.getAllAnnotatedProcessNodes()) {
            boolean eps_designator_type_input_name_pass = false;
            boolean eps_designator_type_output_name_pass = false;
        	boolean words_similar = true;
            
            //**In case 'eps_designator_type' is 'input_name'**// 
        	
            if (inputList_PE_num != 0 && outputList_PE_num == 0) {
                	
                /*The first step is to obtain the input list of process node */
                List<String> input_list_process_node = annotatedprocessNode.dto().getInputList();
                
                //*Convert 'input_list_process_node' to an array*//
                String[] input_list_pn = new String[input_list_process_node.size()];
                input_list_process_node.toArray(input_list_pn);
                	
                /*Secondly, we should compare the input of process node with 'eps_designator_value' of PE
                	 * Now, the 'eps_designator_type' is 'input_name'
                	 **/
                /*set a flag array list named 'flag'*/
                List<Boolean> flag = new ArrayList<Boolean>();                
                
                /**Loop the 'inputList' of PE****/
                Iterator<String> it = inputList.iterator();
                
                
                while(it.hasNext()){
                	
                	String input_PE = it.next();
                	
                	/*Compare the inputs of process node with query input of PE semantically*/
                	for (int i = 0; i < input_list_pn.length; i++){
                		
                		words_similar = VariableQuery.semanticSimilarity4Words(input_list_pn[i], input_PE, ontModel);
                		
                		if(words_similar){
                			
                			flag.add(new Boolean(true));
                		}else{
                			continue;
                		}
                		
                	}                	
                }
                
                /*Lastly, check if 'input_list' of process node comprises the query input list of PE*/
                if(flag.size() == inputList_PE_num){
                	eps_designator_type_input_name_pass = true;
                }
                
                eps_designator_type_output_name_pass = true;
            }
            
            //**In case 'eps_designator_type' is 'output_name'**// 
            
            if (outputList_PE_num != 0 && inputList_PE_num == 0) {
                	
                /*The first step is to obtain the output list of annotated process node */
                List<String> output_list_process_node = annotatedprocessNode.dto().getOutputList();
                
                //*Convert 'output_list_process_node' to an array*//
                String[] output_list_pn = new String[output_list_process_node.size()];
                output_list_process_node.toArray(output_list_pn);
                	
                /*Secondly, we should compare the output of process node with 'eps_designator_value' of PE
                	 * Now, the 'eps_designator_type' is 'output_name'
                	 **/
                /*set a flag array list named 'flag'*/
                List<Boolean> flag = new ArrayList<Boolean>();
                                
                /**Loop the 'outputList' of PE****/
                Iterator<String> it = outputList.iterator();
                
                
                while(it.hasNext()){
                	
                	String output_PE = it.next();
                	
                	/*Compare the outputs of process node with query output of PE semantically*/
                	for (int i = 0; i < output_list_pn.length; i++){
                		
                		words_similar = VariableQuery.semanticSimilarity4Words(output_list_pn[i], output_PE, ontModel);
                		
                		if(words_similar){
                			
                			flag.add(new Boolean(true));
                		}else{
                			continue;
                		}
                		
                	}                	
                }
                
                /*Lastly, check if 'input_list' of process node subsumes the query input list of PE*/
                if(flag.size() == outputList_PE_num){
                	eps_designator_type_output_name_pass = true;
                }
                
                eps_designator_type_input_name_pass = true;
                
            }
            
            //*In case 'eps_designator_type' are both 'input_name' and 'output_name'*//
            if (inputList_PE_num != 0 && outputList_PE_num != 0){
            	
                /*The first step is to obtain the input list of process node */
                List<String> input_list_process_node = annotatedprocessNode.dto().getInputList();
                
                //*Convert 'input_list_process_node' to an array*//
                String[] input_list_pn = new String[input_list_process_node.size()];
                input_list_process_node.toArray(input_list_pn);
                	
                /*Secondly, we should compare the input of process node with 'eps_designator_value' of PE
                	 * Now, the 'eps_designator_type' is 'input_name'
                	 **/
                /*set a flag array list named 'flag'*/
                List<Boolean> flag = new ArrayList<Boolean>();                
                
                /**Loop the 'inputList' of PE****/
                Iterator<String> it = inputList.iterator();
                
                
                while(it.hasNext()){
                	
                	String input_PE = it.next();
                	
                	/*Compare the inputs of process node with query input of PE semantically*/
                	for (int i = 0; i < input_list_pn.length; i++){
                		
                		words_similar = VariableQuery.semanticSimilarity4Words(input_list_pn[i], input_PE, ontModel);
                		
                		if(words_similar){
                			
                			flag.add(new Boolean(true));
                		}else{
                			continue;
                		}
                		
                	}                	
                }
                
                /*Lastly, check if 'input_list' of process node comprises the query input list of PE*/
                if(flag.size() == inputList_PE_num){
                	eps_designator_type_input_name_pass = true;
                }
                
                /*The first step is to obtain the output list of process node */
                List<String> output_list_process_node = annotatedprocessNode.dto().getOutputList();
                
                //*Convert 'output_list_process_node' to an array*//
                String[] output_list_pn = new String[output_list_process_node.size()];
                output_list_process_node.toArray(output_list_pn);
                	
                /*Secondly, we should compare the output of process node with 'eps_designator_value' of PE
                	 * Now, the 'eps_designator_type' is 'output_name'
                	 **/
                /*set a flag array list named 'flag'*/
                List<Boolean> flag4output = new ArrayList<Boolean>();
                                
                /**Loop the 'outputList' of PE****/
                Iterator<String> its = outputList.iterator();
                
                
                while(its.hasNext()){
                	
                	String output_PE = its.next();
                	
                	/*Compare the outputs of process node with query output of PE semantically*/
                	for (int i = 0; i < output_list_pn.length; i++){
                		
                		words_similar = VariableQuery.semanticSimilarity4Words(output_list_pn[i], output_PE, ontModel);
                		
                		if(words_similar){
                			
                			flag4output.add(new Boolean(true));
                		}else{
                			continue;
                		}
                		
                	}                	
                }
                
                /*Lastly, check if 'output_list' of process node subsumes the query output list of PE*/
                if(flag4output.size() == outputList_PE_num){
                	eps_designator_type_output_name_pass = true;
                }
            	
            	
            }
            
            if (eps_designator_type_output_name_pass && eps_designator_type_input_name_pass) {
                result.add(annotatedprocessNode);
            }
            
        }
        
        return result;
    	
    	
    }
    

    @Override
    /*Here, 'preCondition' refers to the 'eps_designator_type' of PE
     * Similarly, 'postCondtion' refers to another 'eps_designator_type' of PE */
    public List<ProcessNode> conditionQuery(ProcessGraph graph, ConditionDTO preCondition, ConditionDTO postCondition) throws Exception {
        List<ProcessNode> result = new ArrayList<>();
        if (preCondition == null && postCondition == null) {
            return result;
        }
        
        for (ProcessNode processNode : graph.getAllProcessNodes()) {
            boolean contains = true;
            
            if (preCondition != null) {
            	
            	long preconditionid = processNode.dto().getPreconditionid();
                
                /* consider common cases */
                if (preconditionid > 100000L) {
                    ConditionDTO conditionById = conditionService.getRecursiveConditionById(preconditionid);
                    if (conditionById != null) {
                        contains = conditionService.SimilarToSubCondition(preCondition, conditionById);
                    } else {
                        contains = false;
                    }
                } 
                
                else {
                    contains = false;
                }
            }
            
            if (contains && postCondition != null) {
                long postconditionid = processNode.dto().getPostconditionid();
                if (postconditionid > 1L) {
                    ConditionDTO conditionById = conditionService.getRecursiveConditionById(postconditionid);
                    if (conditionById != null) {
                        contains = conditionService.SimilarToSubCondition(postCondition, conditionById);
                    } else {
                        contains = false;
                    }
                } else {
                    contains = false;
                }
            }
            
            if (contains) {
                result.add(processNode);
            }
            
        }
        
        return result;
    }


    /**
     * 在processnode之前插入process 并完成流程重构和数据流的重构
     * 可选数据流扩展方式(DataFlowExtensionPartternEnum)：
     * biexternal(1001),
     * preexternal(1002),
     * postexternal(1003),
     * prefix(1003),
     *
     * @param processGraph //The original process graph
     * @param ProcessNodes //The extension point
     * @param pluginInfoDTO //Plug-in
     * @return
     */
    private void insertBefore(ProcessGraph processGraph, List<ProcessNode> ProcessNodes, PluginInfoDTO pluginInfoDTO, DataFlowExtensionPartternEnum partternEnum) throws Exception {
//        assert partternEnum != null;
//        if (DataFlowExtensionPartternEnum.postfix.getValue() == partternEnum.getValue() || DataFlowExtensionPartternEnum.subsititution.getValue() == partternEnum.getValue() || DataFlowExtensionPartternEnum.synchronization.getValue() == partternEnum.getValue()) {
//            throw new Exception("insertbefore does not support this DataFlowExtensionParttern：" + partternEnum);
//        }
        boolean special = DataFlowExtensionPartternEnum.prefix.getValue() == partternEnum.getValue();
        for (ProcessNode processNode : ProcessNodes) {
            ProcessDTO processDTO = pluginInfoDTO.getProcessDTO();
//            if (processDTO == null) {
//                long processId = pluginInfoDTO.getProcessId();
//                processDTO = ProcessDAO.instance.queryById(processId);
//            }
//            ProcessNode insertProcess = ProcessGraphFactory.createProcessNode(processDTO);
            ProcessNode insertProcess = ProcessGraphFactory.createProcessNode();
            SequenceDependencyNode sequence = expanderService.saveSequenceDependencyNode(insertProcess.getDTOId(),processNode.getDTOId());

            processGraph.addNode(sequence);
            processGraph.addNode(insertProcess);

            Set<DefaultEdge> edges = processGraph.incomingEdgesOf(processNode);
            Set<DefaultEdge> useEdge = new HashSet<>();
            useEdge.addAll(edges);
            List<DefaultEdge> deleteEdge = new ArrayList<>();
            if (edges.size() > 0) {
                for (DefaultEdge edge : useEdge) {
                    GraphNode source = processGraph.getEdgeSource(edge);
                    if (source != null) {
                        if (!expanderService.isDataFlow(source)) {
                            if (source instanceof IFollowingNode) {
                                IFollowingNode optionNode = (IFollowingNode) source;
                                optionNode.setFollowing(insertProcess.getDTOId());
                            }
                            processGraph.addEdge(source, insertProcess);
                            processGraph.addEdge(insertProcess, sequence);
                            processGraph.addEdge(sequence, processNode);
                            deleteEdge.add(edge);
                        } else if (special) {
                            //Resource
                            processGraph.addEdge(source, insertProcess);
                            processGraph.addEdge(insertProcess, sequence);
                            processGraph.addEdge(sequence, processNode);
                            deleteEdge.add(edge);
                        }
                    }
                }
            }
            for (DefaultEdge edge : deleteEdge) {
                processGraph.removeEdge(edge);
            }
            if (!special) {
                expanderService.commonExpender(processGraph, insertProcess, partternEnum);
            }
        }
    }


    /**
     * 在processnode之后插入process 并完成流程重构和数据流的重构
     * 可选数据流扩展方式(DataFlowExtensionPartternEnum)：
     * biexternal(1001),
     * preexternal(1002),
     * postexternal(1003),
     * postfix(1004)
     *
     * @param processGraph
     * @param ProcessNodes
     * @param pluginInfoDTO
     * @return
     */
    private void insertAfter(ProcessGraph processGraph, List<ProcessNode> ProcessNodes, PluginInfoDTO pluginInfoDTO, DataFlowExtensionPartternEnum partternEnum) throws Exception {
//        assert partternEnum != null;
//        if (DataFlowExtensionPartternEnum.prefix.getValue() == partternEnum.getValue() || DataFlowExtensionPartternEnum.subsititution.getValue() == partternEnum.getValue() || DataFlowExtensionPartternEnum.synchronization.getValue() == partternEnum.getValue()) {
//            throw new Exception("insertAfter does not support this DataFlowExtensionParttern：" + partternEnum);
//        }
        boolean special = DataFlowExtensionPartternEnum.postfix.getValue() == partternEnum.getValue();
        for (ProcessNode processNode : ProcessNodes) {
            ProcessDTO processDTO = pluginInfoDTO.getProcessDTO();
//            if (processDTO == null) {
//                long processId = pluginInfoDTO.getProcessId();
//                processDTO = ProcessDAO.instance.queryById(processId);
//            }
//            ProcessNode insertProcess = ProcessGraphFactory.createProcessNode(processDTO);
            ProcessNode insertProcess = ProcessGraphFactory.createProcessNode();
            SequenceDependencyNode sequence = expanderService.saveSequenceDependencyNode(processNode.getDTOId(), insertProcess.getDTOId());

            processGraph.addNode(sequence);
            processGraph.addNode(insertProcess);
            List<DefaultEdge> deleteEdge = new ArrayList<>();
            Set<DefaultEdge> edges = processGraph.outgoingEdgesOf(processNode);
            Set<DefaultEdge> useEdge = new HashSet<>();
            useEdge.addAll(edges);
            for (DefaultEdge edge : useEdge) {
                GraphNode target = processGraph.getEdgeTarget(edge);
                if (target != null) {
                    if (!expanderService.isDataFlow(target)) {
                        if (target instanceof IPrecedingNode) {
                            IPrecedingNode optionNode = (IPrecedingNode) target;
                            optionNode.setPreceding(insertProcess.getDTOId());
                        }
                        processGraph.addEdge(processNode, sequence);
                        processGraph.addEdge(sequence, insertProcess);
                        processGraph.addEdge(insertProcess, target);
                        deleteEdge.add(edge);
                    } else if (special) {
                        processGraph.addEdge(processNode, sequence);
                        processGraph.addEdge(sequence, insertProcess);
                        processGraph.addEdge(insertProcess, target);
                        deleteEdge.add(edge);
                    }
                }
            }
            for (DefaultEdge edge : deleteEdge) {
                processGraph.removeEdge(edge);
            }


            if (!special) {
                expanderService.commonExpender(processGraph, insertProcess, partternEnum);
            }
        }
    }
    
    /**
     * 在processnode之后插入process 并完成流程重构
     * However, this function is just for evaluation. Data-flow restructuring is not considered in this function. 
     * Particularly, this function uses the APG structure rather than PMR Graph. 
     *
     * @param processGraph
     * @param ProcessNodes
     * @param pluginInfoDTO
     * @return
     */
    private void insertAfter4Evaluation(AnnotatedProcessGraph annotatedprocessGraph, List<AnnotatedProcessNode> annotatedProcessNodes, PluginInfoDTO pluginInfoDTO) throws Exception {

        for (AnnotatedProcessNode annotatedprocessNode : annotatedProcessNodes) {
            
        	
        	long process_id = pluginInfoDTO.getProcessId();

            /*create a new process node, which is essentially an newly inserted annotated process node*/
        	AnnotatedProcessNode insertProcess = ProcessGraphFactory.createAnnotatedProcessNode();
        	
        	/*Need to assign an 'id' to the annotated process node 'insertProcess'*/
        	insertProcess.setDTOId(process_id);
            
            /*we ought to assign the preceding node and successive node for every sequence dependency node*/
            SequenceDependencyNode sequence = expanderService.saveSequenceDependencyNode(annotatedprocessNode.getDTOId(), insertProcess.getDTOId());

            /*add the sequence node and plug-in activity into the original APG*/
            annotatedprocessGraph.addNode(sequence);
            annotatedprocessGraph.addNode(insertProcess);
            
            List<DefaultEdge> deleteEdge = new ArrayList<>();
            Set<DefaultEdge> edges = annotatedprocessGraph.outgoingEdgesOf(annotatedprocessNode);
            Set<DefaultEdge> useEdge = new HashSet<>();
            useEdge.addAll(edges);
            for (DefaultEdge edge : useEdge) {
            	
            	/*'target' node is the successive node of 'processNode'*/
                GraphNode target = annotatedprocessGraph.getEdgeTarget(edge);
                
                /*in case the 'target' node exists...*/
                if (target != null) {
                	
                	/*in case the 'target' node is not a resource node*/
                    if (!expanderService.isDataFlow(target)) {
                    	
                    	/*re-assign the preceding node of 'target' node*/
                        if (target instanceof IPrecedingNode) {
                        	
                        	/*we have to assign the preceding node and successive node for each sequence dependency node*/
                            IPrecedingNode optionNode = (IPrecedingNode) target;
                            optionNode.setPreceding(insertProcess.getDTOId());
                        }
                        
                        /*link the edges between the added nodes*/
                        annotatedprocessGraph.addEdge(annotatedprocessNode, sequence);
                        annotatedprocessGraph.addEdge(sequence, insertProcess);
                        annotatedprocessGraph.addEdge(insertProcess, target);
                        
                        /*remove the redundant edge*/
                        deleteEdge.add(edge);
                    } 
                }
            }
            for (DefaultEdge edge : deleteEdge) {
                annotatedprocessGraph.removeEdge(edge);
            }

        }
    }
    

    /**
     * 在processnode并行process 并完成流程重构和数据流的重构
     * 可选数据流扩展方式(DataFlowExtensionPartternEnum)：
     * biexternal(1001),
     * preexternal(1002),
     * postexternal(1003),
     * synchronization(1006)
     *
     * @param processGraph
     * @param ProcessNodes
     * @param pluginInfoDTO
     * @return
     */
    private void insertParallelTo(ProcessGraph processGraph, List<ProcessNode> ProcessNodes, PluginInfoDTO pluginInfoDTO, DataFlowExtensionPartternEnum partternEnum) throws Exception {
//        assert partternEnum != null;
//        if (DataFlowExtensionPartternEnum.postfix.getValue() == partternEnum.getValue() || DataFlowExtensionPartternEnum.subsititution.getValue() == partternEnum.getValue() || DataFlowExtensionPartternEnum.prefix.getValue() == partternEnum.getValue()) {
//            throw new Exception("insertParallelTo does not support this DataFlowExtensionParttern：" + partternEnum);
//        }
        boolean special = DataFlowExtensionPartternEnum.synchronization.getValue() == partternEnum.getValue();
        for (ProcessNode processNode : ProcessNodes) {
            ProcessDTO processDTO = pluginInfoDTO.getProcessDTO();
//            if (processDTO == null) {
//                long processId = pluginInfoDTO.getProcessId();
//                processDTO = ProcessDAO.instance.queryById(processId);
//            }
//            ProcessNode insertProcess = ProcessGraphFactory.createProcessNode(processDTO);
            ProcessNode insertProcess = ProcessGraphFactory.createProcessNode();
            SplitDependencyNode split = expanderService.getSplitDependencyNode();
            JoinDependencyNode join = expanderService.getJoinDependencyNode();
            SplitDependencyOptionNode processSdo = expanderService.saveSplitOption(processNode.dto().getId(), split.dto().getId());
            SplitDependencyOptionNode insertSdo = expanderService.saveSplitOption(insertProcess.dto().getId(), split.dto().getId());
            JoinDependencyOptionNode processJdo = expanderService.saveJoinOption(processNode.dto().getId(), join.dto().getId());
            JoinDependencyOptionNode insertJdo = expanderService.saveJoinOption(insertProcess.dto().getId(), join.dto().getId());

            processGraph.addNode(insertProcess);
            processGraph.addNode(split);
            processGraph.addNode(join);
            processGraph.addNode(processSdo);
            processGraph.addNode(insertSdo);
            processGraph.addNode(insertJdo);
            processGraph.addNode(processJdo);

            Set<DefaultEdge> outEdge = processGraph.outgoingEdgesOf(processNode);
            Set<DefaultEdge> useOutEdge = new HashSet<>();
            useOutEdge.addAll(outEdge);
            Set<DefaultEdge> inEdge = processGraph.incomingEdgesOf(processNode);
            Set<DefaultEdge> useInEdge = new HashSet<>();
            useInEdge.addAll(inEdge);
            List<DefaultEdge> deleteEdge = new ArrayList<>();
            for (DefaultEdge edge : useOutEdge) {
                GraphNode edgeTarget = processGraph.getEdgeTarget(edge);
                if (edgeTarget != null) {
                    if (!expanderService.isDataFlow(edgeTarget)) {
                        if (edgeTarget instanceof IPrecedingNode) {
                            IPrecedingNode optionNode = (IPrecedingNode) edgeTarget;
                            optionNode.setPreceding(join.getDTOId());
                        }
                        processGraph.addEdge(join, edgeTarget);
                        join.dto().setFollowing(edgeTarget.getDTOId());
                        deleteEdge.add(edge);
                    } else if (special) {//resource
                        processGraph.addEdge(insertProcess, edgeTarget);
                    }
                }
            }
            for (DefaultEdge edge : useInEdge) {
                GraphNode edgeSource = processGraph.getEdgeSource(edge);
                if (edgeSource != null) {
                    if (!expanderService.isDataFlow(edgeSource)) {
                        if (edgeSource instanceof IFollowingNode) {
                            IFollowingNode optionNode = (IFollowingNode) edgeSource;
                            optionNode.setFollowing(insertProcess.getDTOId());
                        }
                        processGraph.addEdge(edgeSource, split);
                        split.dto().setPreceding(edgeSource.getDTOId());
                        deleteEdge.add(edge);
                    } else if (special) {
                        processGraph.addEdge(edgeSource, insertProcess);
                    }

                }
            }
            for (DefaultEdge edge : deleteEdge) {
                processGraph.removeEdge(edge);
            }

            processGraph.addEdge(split,processSdo);
            processGraph.addEdge(split,insertSdo);
            processGraph.addEdge(processJdo,join);
            processGraph.addEdge(insertJdo,join);
            processGraph.addEdge(processSdo,processNode);
            processGraph.addEdge(insertSdo, insertProcess);
            processGraph.addEdge(processNode, processJdo);
            processGraph.addEdge(insertProcess, insertJdo);

            if (!special) {
                expanderService.commonExpender(processGraph, insertProcess, partternEnum);
            }
        }
    }


    /**
     * 把processnode替换为插件的process 并完成流程重构和数据流的重构
     * 可选数据流扩展方式(DataFlowExtensionPartternEnum)：
     * biexternal(1001),
     * preexternal(1002),
     * postexternal(1003),
     * subsititution(1005)
     *
     * @param processGraph
     * @param ProcessNodes
     * @param pluginInfoDTO
     * @return
     */
    private void insertReplace(ProcessGraph processGraph, List<ProcessNode> ProcessNodes, PluginInfoDTO pluginInfoDTO, DataFlowExtensionPartternEnum partternEnum) throws Exception {
//        assert partternEnum != null;
//        if (DataFlowExtensionPartternEnum.prefix.equals(partternEnum) || DataFlowExtensionPartternEnum.postfix.equals(partternEnum) || DataFlowExtensionPartternEnum.synchronization.equals(partternEnum)) {
//            throw new Exception("insertReplace does not support this DataFlowExtensionParttern：" + partternEnum);
//        }
        boolean special = DataFlowExtensionPartternEnum.subsititution.equals(partternEnum);
        for (ProcessNode processNode : ProcessNodes) {
            ProcessDTO processDTO = pluginInfoDTO.getProcessDTO();
//            if (processDTO == null) {
//                long processId = pluginInfoDTO.getProcessId();
//                processDTO = ProcessDAO.instance.queryById(processId);
//            }
//            ProcessNode insertProcess = ProcessGraphFactory.createProcessNode(processDTO);
            ProcessNode insertProcess = ProcessGraphFactory.createProcessNode();

            processGraph.addNode(insertProcess);

            Set<DefaultEdge> outEdge = processGraph.outgoingEdgesOf(processNode);
            Set<DefaultEdge> useOutEdge = new HashSet<>();
            useOutEdge.addAll(outEdge);
            Set<DefaultEdge> inEdge = processGraph.incomingEdgesOf(processNode);
            Set<DefaultEdge> useInEdge = new HashSet<>();
            useInEdge.addAll(inEdge);
            List<DefaultEdge> deleteEdge = new ArrayList<>();//通過outgoingEdgesOf incomingEdgesOf 获得的edge 为不可修改的边 所以采用新的集合保存 最后删除
            for (DefaultEdge edge : useOutEdge) {
                GraphNode edgeTarget = processGraph.getEdgeTarget(edge);
                if (edgeTarget != null) {
                    if (!expanderService.isDataFlow(edgeTarget)) {
                        if (edgeTarget instanceof IPrecedingNode) {
                            IPrecedingNode optionNode = (IPrecedingNode) edgeTarget;
                            optionNode.setPreceding(insertProcess.getDTOId());
                        }
                        processGraph.addEdge(insertProcess, edgeTarget);
                        deleteEdge.add(edge);
                    } else if (special) {
                        processGraph.addEdge(insertProcess, edgeTarget);
                        deleteEdge.add(edge);
                    }
                }
            }
            for (DefaultEdge edge : useInEdge) {
                GraphNode edgeSource = processGraph.getEdgeSource(edge);
                if (edgeSource != null) {
                    if (!expanderService.isDataFlow(edgeSource)) {
                        if (edgeSource instanceof IFollowingNode) {
                            IFollowingNode optionNode = (IFollowingNode) edgeSource;
                            optionNode.setFollowing(insertProcess.getDTOId());
                        }
                        processGraph.addEdge(edgeSource, insertProcess);
                        deleteEdge.add(edge);
                    } else if (special) {
                        processGraph.addEdge(edgeSource, insertProcess);
                        deleteEdge.add(edge);
                    }
                }
            }
            for (DefaultEdge edge : deleteEdge) {
                processGraph.removeEdge(edge);
            }
            processGraph.removeGraphNode(processNode);
            if (!special) {
                expanderService.commonExpender(processGraph, insertProcess, partternEnum);
            }
        }

    }

    @Override
    public void expandProcessGraph(ProcessGraph processGraph, List<ProcessNode> ProcessNodes, PluginInfoDTO pluginInfoDTO) throws Exception {
        assert pluginInfoDTO != null;
        ControllFlowExtensionPatternEnum flowExtensionPatternEnum = ControllFlowExtensionPatternEnum.fromInt(pluginInfoDTO.getControllFlowExtensionPattern());
        DataFlowExtensionPartternEnum dataFlow = DataFlowExtensionPartternEnum.fromInt(pluginInfoDTO.getDataFlowExtensionParttern());
        assert flowExtensionPatternEnum != null;
        assert dataFlow != null;
        switch (flowExtensionPatternEnum) {
            case insertBefore:
                insertBefore(processGraph, ProcessNodes, pluginInfoDTO, dataFlow);
                break;
            case insertAfter:
                insertAfter(processGraph, ProcessNodes, pluginInfoDTO, dataFlow);
                break;
            case insertParallelTo:
                insertParallelTo(processGraph, ProcessNodes, pluginInfoDTO, dataFlow);
                break;
            case insertReplace:
                insertReplace(processGraph, ProcessNodes, pluginInfoDTO, dataFlow);
                break;
        }
    }
    
    /*To extend the APG, the function is just used to evaluate the prototype*/
    public void expandAnnotatedProcessGraph(AnnotatedProcessGraph annotatedprocessGraph, List<AnnotatedProcessNode> annotatedProcessNodes, PluginInfoDTO pluginInfoDTO) throws Exception {
        assert pluginInfoDTO != null;
        ControllFlowExtensionPatternEnum flowExtensionPatternEnum = ControllFlowExtensionPatternEnum.fromInt(pluginInfoDTO.getControllFlowExtensionPattern());
        //DataFlowExtensionPartternEnum dataFlow = DataFlowExtensionPartternEnum.fromInt(pluginInfoDTO.getDataFlowExtensionParttern());
        assert flowExtensionPatternEnum != null;
        //assert dataFlow != null;
        switch (flowExtensionPatternEnum) {
            case insertBefore:
                /*TOD*/
                break;
            case insertAfter:
                insertAfter4Evaluation(annotatedprocessGraph, annotatedProcessNodes, pluginInfoDTO);
                break;
            case insertParallelTo:
                /*TOD*/
                break;
            case insertReplace:
                /*TOD*/
                break;
        }
    }


}
