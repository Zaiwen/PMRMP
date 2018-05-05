package org.sklse.processRegister.expander;

import nl.tue.tm.is.epc.EPC;
import org.sklse.processRegister.expander.impl.ProcessGraphIOServiceImpl;
import org.sklse.processRegister.processGraph.ProcessGraph;

import java.io.File;
import java.util.List;

/**
 * Created by 袁胜磊 on Intellij IDEA
 *
 * @date 2015/4/26
 * @email 745861642@qq.com
 * @description
 */
public interface ProcessGraphIOService {
    ProcessGraphIOService intance = new ProcessGraphIOServiceImpl();

    /**
     * 保存 ProcessGraph 到数据库
     *
     * @param processGraph
     * @return
     * @throws Exception
     */
    long ProcessGraphrRgister(ProcessGraph processGraph) throws Exception;

    /**
     * 从数据库读取 ProcessGraph  对应ProcessGraph  processmodel id
     *
     * @param id
     * @return
     * @throws Exception
     */
    ProcessGraph ProcessGraphLoad(long id) throws Exception;

    /**
     * 读取epc文件并保存为图 返回id
     *
     * @param file
     * @return
     * @throws Exception
     */
    List<Long> converts(File file) throws Exception;


    /**
     * epc 转化为图
     *
     * @param epc
     * @return
     * @throws Exception
     */
    ProcessGraph EpcToProcessGraph(EPC epc) throws Exception;

    /**
     * 图转换为epc  
     *
     * @param processGraph
     * @return
     * @throws Exception
     */
    EPC ProcessGraphToEpc(ProcessGraph processGraph) throws Exception;
    
    /**
     * EPC对象转化为EPML格式字符串 -->xml
     */
    String Epc2Epml(EPC epc);
}
