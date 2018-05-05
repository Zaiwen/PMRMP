package org.sklse.processRegister.db.services;

import junit.framework.TestCase;
import org.sklse.processRegister.db.dto.PluginInfoDTO;
import org.sklse.processRegister.db.dto.ProcessDTO;
import org.sklse.processRegister.db.enums.ControllFlowExtensionPatternEnum;
import org.sklse.processRegister.db.enums.ProcessFieldEnum;
import org.sklse.processRegister.db.enums.ProcessTypeEnum;
import org.sklse.processRegister.db.enums.SearchExtensibilityPointTypeEnum;

import java.sql.Date;

/**
 * Created by Ԭʤ�� on Intellij IDEA
 *
 * @date 2015/4/25
 * @email 745861642@qq.com
 * @description
 */
public class PluginInfoServiceTest extends TestCase {

    PluginInfoService pluginInfoService = PluginInfoService.instance;

    public void testSavePluginInfo() throws Exception {
        long l = pluginInfoService.savePluginInfo(getTestPluginInfo(), getTestProcesDTO(), null);
        assert l > 1l;
    }

    public void testSavePluginInfo1() throws Exception {

    }

    public void testDeletePluginInfo() throws Exception {

    }

    public void testDeletePluginInfo1() throws Exception {

    }

    public PluginInfoDTO getTestPluginInfo() {
        PluginInfoDTO pluginInfoDTO = new PluginInfoDTO();
        pluginInfoDTO.setName("testpluginInfoDTO");
        pluginInfoDTO.setSearchExtensibilityPointType(SearchExtensibilityPointTypeEnum.conditionQuery.getValue());
        pluginInfoDTO.setProcessType(ProcessTypeEnum.epc.getValue());
//        pluginInfoDTO.setAuthor("ysl");
        pluginInfoDTO.setAnnotation("testpluginInfoDTO");
        pluginInfoDTO.setControllFlowExtensionPattern(ControllFlowExtensionPatternEnum.insertBefore.getValue());
        Date createTime = new Date(System.currentTimeMillis());
        pluginInfoDTO.setCreateTime(createTime);
        pluginInfoDTO.setField(ProcessFieldEnum.hotel.getValue());
        pluginInfoDTO.setPluginParam("");
        pluginInfoDTO.setQueryStr1("test1");
        pluginInfoDTO.setQueryStr2("test2");
        pluginInfoDTO.setUrl("");
        return pluginInfoDTO;
    }

    public ProcessDTO getTestProcesDTO() {
        ProcessDTO processDTO = new ProcessDTO();
        processDTO.setName("processDTO");
        processDTO.setParam("");
        processDTO.setAnnotation("processDTO");
        processDTO.setUri("");
//        processDTO.setPreconditonid(1L);
//        processDTO.setPreconditonid(2L);
        return processDTO;
    }
}