package org.sklse.processRegister.processGraph;

import change4epml.Edge;

import org.sklse.processRegister.db.dao.*;
import org.sklse.processRegister.db.dto.ConditionDTO;
import org.sklse.processRegister.db.dto.PMContainPEDTO;
import org.sklse.processRegister.db.dto.ProcessDTO;
import org.sklse.processRegister.db.dto.ProcessModelDTO;

import java.util.List;
import java.util.Set;

public class LoadProcessGraph {

    public ProcessGraph load(long pid) throws Exception {
        ProcessGraph pg = new ProcessGraph();
        ProcessModelDTO processModelDTO = ProcessModelDAO.getInstance().queryById(pid);
        assert processModelDTO != null;
        ProcessDTO mainProcess = ProcessDAO.instance.queryById(processModelDTO.getPid());
        pg.setPmdto(processModelDTO);
        long pmid = processModelDTO.getId();
        Set<Edge> edgeset = new PMContainEgDAO().QueryByPmid(pmid);
        List<PMContainPEDTO> pmpelist = new PMContainPEDAO().queryByPMId(pmid);
        //   Edge是否需要存储边，增加相应字段 ，坐标的相应的信息存储在哪里

        for (PMContainPEDTO p : pmpelist) {
            switch (p.getPetype()) {
                case EVENT:
                    EventNode e = new EventNode(new EventDAO().queryById(p.getPEId()));
                    pg.addNode(e);
                    break;

                case PROCESS:
                    ProcessNode pn = new ProcessNode(new ProcessDAO().queryById(p.getPEId()));
                    pg.addNode(pn);
                    break;

                case SPLIT:
                    SplitDependencyNode sd = new SplitDependencyNode(new SplitDependencyDAO().queryById(p.getPEId()));
                    pg.addNode(sd);
                    break;

                case SPLITOPTION:
                    SplitDependencyOptionNode sdo = new SplitDependencyOptionNode();
                    sdo.dto(new SplitDependencyOptionDAO().queryById(p.getPEId()));
                    pg.addNode(sdo);
                    break;

                case JOIN:
                    JoinDependencyNode jd = new JoinDependencyNode(new JoinDependencyDAO().queryById(p.getPEId()));
                    pg.addNode(jd);
                    break;

                case JOINOPTION:
                    JoinDependencyOptionNode jdo = new JoinDependencyOptionNode();
                    jdo.dto(new JoinDependencyOptionDAO().queryById(p.getPEId()));
                    pg.addNode(jdo);
                    break;
                case SEQUENCE:
                    SequenceDependencyNode sdn = new SequenceDependencyNode();
                    sdn.dto(new SequenceDependencyDAO().queryById(p.getPEId()));
                    pg.addNode(sdn);
                    break;
                case RESOURCE:
                    break;
            }
        }

        for (GraphNode gn : pg.nodeSet()) {
            //System.out.println("Node id: " + gn.getDTOId());
        }
        for (Edge e : edgeset) {
            //System.out.println("srcid : " + e.getSrcid()  + "\n tgtid : " + e.getTgtid());
            GraphNode src = pg.getNodeById(e.getSrcid());
            GraphNode tgt = pg.getNodeById(e.getTgtid());
            pg.addEdge(src, tgt);
        }
        for(ProcessNode pn : pg.getAllProcessNodes()){
        	long preconditionid = pn.dto().getPreconditionid();
        	long postconditionid = pn.dto().getPostconditionid();
        	if(preconditionid > -1L){
        		ConditionDTO queryById = ConditionDAO.getDAO().queryById(preconditionid);
        		if(queryById == null) System.out.println("QUERY　By ID  is null");
        		else 
				pg.getConset().add(queryById);
        	}
        	if(postconditionid > -1L){
        		ConditionDTO queryById = ConditionDAO.getDAO().queryById(postconditionid);
        		if(queryById == null) System.out.println("QUERY　By ID  is null");
        		else 
				pg.getConset().add(queryById);
        	}
        }
        pg.representing(mainProcess);
        return pg;
    }


}
