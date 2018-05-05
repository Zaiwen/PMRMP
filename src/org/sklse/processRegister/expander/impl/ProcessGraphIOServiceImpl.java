package org.sklse.processRegister.expander.impl;

import change4epml.ConvertInterface;
import change4epml.EpmlMapping;
import change4epml.ProcessGraph2Epml;
import change4epml.ProcessGraphTemp;
import nl.tue.tm.is.epc.EPC;

import org.sklse.processRegister.expander.ProcessGraphIOService;
import org.sklse.processRegister.processGraph.LoadProcessGraph;
import org.sklse.processRegister.processGraph.ProcessGraph;
import org.sklse.processRegister.processGraph.ProcessGraphRegister;

import java.io.File;
import java.util.List;

/**
 * Created by 袁胜磊 on Intellij IDEA
 *
 * @date 2015/4/26
 * @email 745861642@qq.com
 * @description
 */
public class ProcessGraphIOServiceImpl implements ProcessGraphIOService {

    private LoadProcessGraph loadProcessGraph = new LoadProcessGraph();
    private ProcessGraphRegister processGraphRegister = new ProcessGraphRegister();

    @Override
    public long ProcessGraphrRgister(ProcessGraph processGraph) throws Exception {
        return processGraphRegister.register(processGraph);
    }

    @Override
    public ProcessGraph ProcessGraphLoad(long id) throws Exception {
        return loadProcessGraph.load(id);
    }

    @Override
    public List<Long> converts(File file) throws Exception {
        return ConvertInterface.converts(file);
    }

    @Override
    public ProcessGraph EpcToProcessGraph(EPC epc) throws Exception {
        ProcessGraphTemp mapping = EpmlMapping.mapping(epc);
        return mapping.convert2processGraph();
    }

    @Override
    public EPC ProcessGraphToEpc(ProcessGraph processGraph) throws Exception {
        return ProcessGraph2Epml.Convert(processGraph);
    }

	@Override
	public String Epc2Epml(EPC epc) {
		return epc.Epc2Epml(epc);
	}

	
}
