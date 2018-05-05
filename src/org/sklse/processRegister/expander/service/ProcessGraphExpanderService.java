package org.sklse.processRegister.expander.service;

import ontology.IdWorker;
import org.sklse.processRegister.db.dao.PluginResourceDAO;
import org.sklse.processRegister.db.dto.JoinDependencyDTO;
import org.sklse.processRegister.db.dto.PluginResourceDTO;
import org.sklse.processRegister.db.dto.SplitDependencyDTO;
import org.sklse.processRegister.db.enums.DataFlowExtensionPartternEnum;
import org.sklse.processRegister.db.enums.ResourceTypeEnum;
import org.sklse.processRegister.processGraph.*;

import java.util.List;

/**
 * Created by 袁胜磊 on Intellij IDEA
 *
 * @date 2015/3/27
 * @email 745861642@qq.com
 * @description
 */
public class ProcessGraphExpanderService {
    public static ProcessGraphExpanderService instance = new ProcessGraphExpanderService();

    private ProcessGraphExpanderService() {
    }

    /**
     * 保存splitOption
     *
     * @param followingId
     * @param sqid
     */
    public SplitDependencyOptionNode saveSplitOption(long followingId, long sqid) {
        SplitDependencyOptionNode processSdo = new SplitDependencyOptionNode();
        long jqid = 0;
        try {
            jqid = IdWorker.getNextId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        processSdo.setDTOId(jqid);
        processSdo.dto().setFollowing(followingId);
        processSdo.dto().setCondition("");//todo 添加合适condtion
        processSdo.dto().setSplitid(sqid);
        return processSdo;
    }

    /**
     * 保存joinOption
     *
     * @param joinId
     */
    public JoinDependencyOptionNode saveJoinOption(long precedingId, long joinId) {
        JoinDependencyOptionNode processJdo = new JoinDependencyOptionNode();
        long jqid = 0;
        try {
            jqid = IdWorker.getNextId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        processJdo.setDTOId(jqid);
        processJdo.dto().setPreceding(precedingId);
        processJdo.dto().setCondition("");
        processJdo.dto().setJoinid(joinId);
        return processJdo;
    }


    public SplitDependencyNode getSplitDependencyNode() {
        long sqid = 0;
        try {
            sqid = IdWorker.getNextId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        SplitDependencyNode split = new SplitDependencyNode();
        split.dto().setType(SplitDependencyDTO.SplitType.AND);
        split.dto().setSync(true);
        split.dto().setId(sqid);
        return split;
    }


    public JoinDependencyNode getJoinDependencyNode() {
        long joinId = 0;
        try {
            joinId = IdWorker.getNextId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        JoinDependencyNode join = new JoinDependencyNode();
        join.dto().setSync(true);
        join.dto().setType(JoinDependencyDTO.JoinType.AND);
        join.dto().setId(joinId);
        return join;
    }


    public SequenceDependencyNode saveSequenceDependencyNode(long preceding, long following) {
        long sqid = 0;
        try {
            sqid = IdWorker.getNextId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        SequenceDependencyNode sequence = new SequenceDependencyNode();
        sequence.dto().setName("Any Condition");
        sequence.dto().setId(sqid);
        sequence.dto().setPreceding(preceding);
        sequence.dto().setFollowing(following);
        return sequence;
    }


    public boolean isDataFlow(GraphNode node) {
        return (node instanceof ResourceNode);
    }


    public List<PluginResourceDTO> getPluginResourceByProcessid(long processid, ResourceTypeEnum resourceType) throws Exception {
        PluginResourceDAO instance = PluginResourceDAO.getInstance();
        if (instance != null) {
            return instance.getByProcessIdAndResourceType(processid, resourceType);
        }
        return null;
    }

    public void commonExpender(ProcessGraph processGraph, ProcessNode insertProcess, DataFlowExtensionPartternEnum partternEnum) throws Exception {
        List<PluginResourceDTO> preResourceDTOs = getPluginResourceByProcessid(insertProcess.getDTOId(), ResourceTypeEnum.pre);
        List<PluginResourceDTO> postResourceDTOs = getPluginResourceByProcessid(insertProcess.getDTOId(), ResourceTypeEnum.post);
        switch (partternEnum) {
            case biexternal:
                for (PluginResourceDTO preResourceDTO : preResourceDTOs) {
                    ResourceNode resourceNode = new ResourceNode(preResourceDTO);
                    processGraph.addNode(resourceNode);
                    processGraph.addEdge(resourceNode, insertProcess);
                }
                for (PluginResourceDTO postResourceDTO : postResourceDTOs) {
                    ResourceNode resourceNode = new ResourceNode(postResourceDTO);
                    processGraph.addNode(resourceNode);
                    processGraph.addEdge(insertProcess, resourceNode);
                }
                break;
            case preexternal:
                for (PluginResourceDTO preResourceDTO : preResourceDTOs) {
                    ResourceNode resourceNode = new ResourceNode(preResourceDTO);
                    processGraph.addNode(resourceNode);
                    processGraph.addEdge(resourceNode, insertProcess);
                }
                break;
            case postexternal:
                for (PluginResourceDTO postResourceDTO : postResourceDTOs) {
                    ResourceNode resourceNode = new ResourceNode(postResourceDTO);
                    processGraph.addNode(resourceNode);
                    processGraph.addEdge(insertProcess, resourceNode);
                }
                break;
        }
    }
}
