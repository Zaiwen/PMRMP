package org.sklse.processRegister.db.services.impl;

import ontology.IdWorker;
import org.sklse.processRegister.db.dao.PluginProcessDAO;
import org.sklse.processRegister.db.dao.PluginResourceDAO;
import org.sklse.processRegister.db.dao.ProcessDAO;
import org.sklse.processRegister.db.dto.PluginInfoDTO;
import org.sklse.processRegister.db.dto.PluginResourceDTO;
import org.sklse.processRegister.db.dto.ProcessDTO;
import org.sklse.processRegister.db.services.PluginInfoService;

import java.util.List;

/**
 * Created by Ԭʤ�� on Intellij IDEA
 *
 * @date 2015/4/25
 * @email 745861642@qq.com
 * @description
 */
public class PluginInfoServiceImpl implements PluginInfoService {

    private PluginProcessDAO pluginProcessDAO = PluginProcessDAO.getInstance();
    private ProcessDAO processDAO = ProcessDAO.getInstance();
    private PluginResourceDAO resourceDAO = PluginResourceDAO.getInstance();

    @Override
    public long savePluginInfo(PluginInfoDTO pluginInfoDTO, List<PluginResourceDTO> pluginResourceDTOs) throws Exception {
        ProcessDTO processDTO = pluginInfoDTO.getProcessDTO();
        return savePluginInfo(pluginInfoDTO, processDTO, pluginResourceDTOs);
    }

    @Override
    public long savePluginInfo(PluginInfoDTO pluginInfoDTO, ProcessDTO processDTO, List<PluginResourceDTO> pluginResourceDTOs) throws Exception {
        assert pluginInfoDTO != null;
        assert processDTO != null;
        processDTO.setId(IdWorker.getNextId());
        long l = processDAO.create(processDTO);
        pluginInfoDTO.setProcessId(l);
        if (pluginResourceDTOs != null && pluginResourceDTOs.size() > 0) {
            for (PluginResourceDTO resourceDTO : pluginResourceDTOs) {
                resourceDTO.setId(IdWorker.getNextId());
                resourceDTO.setProcessId(l);
                resourceDAO.insert(resourceDTO);
            }
        }
        return pluginProcessDAO.insert(pluginInfoDTO);
    }

    @Override
    public boolean deletePluginInfo(long id) throws Exception {
        PluginInfoDTO pluginInfoDTO = pluginProcessDAO.get(id);
        if (pluginInfoDTO == null) {
            return false;
        }
        resourceDAO.deleteByProcessId(id);
        long processId = pluginInfoDTO.getProcessId();
        resourceDAO.deleteByProcessId(processId);
        processDAO.delete(processId);
        return true;
    }

    @Override
    public boolean deletePluginInfo(PluginInfoDTO pluginInfoDTO) throws Exception {
        assert pluginInfoDTO != null;
        resourceDAO.deleteByProcessId(pluginInfoDTO.getId());
        long processId = pluginInfoDTO.getProcessId();
        resourceDAO.deleteByProcessId(processId);
        processDAO.delete(processId);
        return true;
    }

    @Override
    public PluginInfoDTO getPluginInfoDTO(long id) throws Exception {
        PluginInfoDTO pluginInfoDTO = pluginProcessDAO.get(id);
        assert pluginInfoDTO != null;
        long processId = pluginInfoDTO.getProcessId();
        ProcessDTO processDTO = processDAO.queryById(processId);
        assert processDTO != null;
        pluginInfoDTO.setProcessDTO(processDTO);
        return pluginInfoDTO;
    }
}
