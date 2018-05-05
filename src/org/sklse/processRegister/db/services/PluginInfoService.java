package org.sklse.processRegister.db.services;

import org.sklse.processRegister.db.dto.PluginInfoDTO;
import org.sklse.processRegister.db.dto.PluginResourceDTO;
import org.sklse.processRegister.db.dto.ProcessDTO;
import org.sklse.processRegister.db.services.impl.PluginInfoServiceImpl;

import java.util.List;

/**
 * Created by 袁胜磊 on Intellij IDEA
 *
 * @date 2015/4/25
 * @email 745861642@qq.com
 * @description
 */
public interface PluginInfoService {
    PluginInfoService instance = new PluginInfoServiceImpl();

    /**
     * 保存插件信息 包括 pluginInfoDTO 和包含的ProcessDTO PluginResourceDTO 信息
     * 使用此接口前需要将需要 pluginInfoDTO的ProcessDTO字段赋值
     * 如果没有pluginResourceDTOs 则pluginResourceDTOs 为null
     *
     * @param pluginInfoDTO
     * @param pluginResourceDTOs
     * @return
     * @throws Exception
     */
    long savePluginInfo(PluginInfoDTO pluginInfoDTO, List<PluginResourceDTO> pluginResourceDTOs) throws Exception;

    /**
     * 保存插件信息 包括 pluginInfoDTO 和包含的ProcessDTO PluginResourceDTO 信息
     * 如果没有pluginResourceDTOs 则pluginResourceDTOs 为null
     *
     * @param pluginInfoDTO
     * @param pluginResourceDTOs
     * @return
     * @throws Exception
     */
    long savePluginInfo(PluginInfoDTO pluginInfoDTO, ProcessDTO processDTO, List<PluginResourceDTO> pluginResourceDTOs) throws Exception;

    /**
     * 删除pluginInfoDTO 信息 包括 ProcessDTO PluginResourceDTO
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deletePluginInfo(long id) throws Exception;

    /**
     * 删除pluginInfoDTO 信息 包括 ProcessDTO PluginResourceDTO
     *
     * @param pluginInfoDTO
     * @return
     * @throws Exception
     */
    boolean deletePluginInfo(PluginInfoDTO pluginInfoDTO) throws Exception;

    /**
     * 根据id 查询PluginInfoDTO 信息 并绑定对应的process
     * @param id
     * @return
     * @throws Exception
     */
    PluginInfoDTO getPluginInfoDTO(long id) throws Exception;

}
