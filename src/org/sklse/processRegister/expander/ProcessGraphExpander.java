package org.sklse.processRegister.expander;

import org.sklse.processRegister.db.dto.ConditionDTO;
import org.sklse.processRegister.db.dto.PluginInfoDTO;
import org.sklse.processRegister.expander.impl.ProcessGraphExpanderImpl;
import org.sklse.processRegister.processGraph.ProcessGraph;
import org.sklse.processRegister.processGraph.ProcessNode;

import java.util.List;

/**
 * Created by 袁胜磊 on Intellij IDEA
 *
 * @date 2015/3/26
 * @email 745861642@qq.com
 * @description
 */
public interface ProcessGraphExpander {

    ProcessGraphExpander instance = new ProcessGraphExpanderImpl();

    /**
     * 根据条件查询查询processNode
     *
     * @param graph
     * @param pluginInfoDTO
     * @return
     */
    List<ProcessNode> getExtensibilityPoint(ProcessGraph graph, PluginInfoDTO pluginInfoDTO) throws Exception;

    /**
     * 根据条件查询 插入点
     *
     * @param graph
     * @param preCondition
     * @param postCondition
     * @return
     * @throws Exception
     */
    List<ProcessNode> conditionQuery(ProcessGraph graph, ConditionDTO preCondition, ConditionDTO postCondition) throws Exception;


    /**
     * 根据插件数据表插入数据 建议采用此方法
     *
     * @param processGraph
     * @param ProcessNodes
     * @param pluginInfoDTO
     * @throws Exception
     */
    void expandProcessGraph(ProcessGraph processGraph, List<ProcessNode> ProcessNodes, PluginInfoDTO pluginInfoDTO) throws Exception;
}
