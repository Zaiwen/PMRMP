package org.sklse.processRegister.expander.impl;

import junit.framework.TestCase;
import org.sklse.processRegister.expander.ProcessGraphIOService;
import org.sklse.processRegister.processGraph.ProcessGraph;

/**
 * Created by 袁胜磊 on Intellij IDEA
 *
 * @date 2015/5/4
 * @email 745861642@qq.com
 * @description
 */
public class ProcessGraphIOServiceImplTest extends TestCase {
    ProcessGraphIOService processGraphIOService = ProcessGraphIOService.intance;

    public void testProcessGraphLoad() throws Exception {
        ProcessGraph processGraph = processGraphIOService.ProcessGraphLoad(531528947318800384L);
        assert processGraph != null;
    }
}