package org.sklse.processRegister.expander;

import org.sklse.processRegister.expander.impl.OriginalProcessServiceImpl;

import java.util.List;

/**
 * Created by 袁胜磊 on Intellij IDEA
 *
 * @date 2015/5/4
 * @email 745861642@qq.com
 * @description
 */
public interface OriginalProcessService {

    OriginalProcessService instance = new OriginalProcessServiceImpl();

    long getGraphIdByUserAndName(String user, String name) throws Exception;

    List<Long> getPeidByPmid(long graph) throws Exception;

    long getMaxVersion(String user,String name ) throws  Exception;

    void insertOriginalProcess(String user,String processName,String content,long version, long lastVersion) throws  Exception;
}
