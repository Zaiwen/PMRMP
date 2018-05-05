package org.sklse.processRegister.processGraph;

import change4epml.Edge;
import change4epml.EpmlMapping;
import ontology.IdWorker;

import org.jgrapht.graph.DefaultEdge;
import org.sklse.processRegister.db.dao.*;
import org.sklse.processRegister.db.dto.ConditionDTO;
import org.sklse.processRegister.db.dto.PMContainPEDTO;
import org.sklse.processRegister.db.dto.PMContainPEDTO.ElementType;
import org.sklse.processRegister.db.dto.ProcessModelDTO;

public class ProcessGraphRegister {
    public ProcessGraphRegister() {
    }

    public long register(ProcessGraph pg) throws Exception {

    	//重新分配ID
    	
    	pg.assingID();
    	
        // register the main process
        long main_pid = IdWorker.instance.nextId();
        pg.representing().setId(main_pid);
        new ProcessDAO().create(pg.representing());//DAO 的及时关闭

        // register the process model
        ProcessModelDTO pmdto = pg.getPmdto();
        long model_id = IdWorker.instance.nextId();
        pmdto.setId(model_id);
        pmdto.setLanguage(pg.lang());
        pmdto.setPid(main_pid);
        pmdto.setUri(pg.representing().getUri());
        new ProcessModelDAO().create(pmdto);

        //register all Nodes and Model - Node Relation
        PMContainPEDTO medto = new PMContainPEDTO();
        medto.setPMId(model_id);
        for (GraphNode node : pg.nodeSet()) {
            if (node instanceof JoinDependencyNode) {
                new JoinDependencyDAO().create(((JoinDependencyNode) node).dto());
                medto.setPetype(ElementType.JOIN);
            } else if (node instanceof JoinDependencyOptionNode) {
                new JoinDependencyOptionDAO().create(((JoinDependencyOptionNode) node).dto());
                medto.setPetype(ElementType.JOINOPTION);
            } else if (node instanceof EventNode) {
                new EventDAO().create(((EventNode) node).dto());
                medto.setPetype(ElementType.EVENT);
            } else if (node instanceof ResourceNode) {
                //
            } else if (node instanceof ProcessNode) {
                new ProcessDAO().create(((ProcessNode) node).dto());
                medto.setPetype(ElementType.PROCESS);
            } else if (node instanceof SplitDependencyNode) {
                new SplitDependencyDAO().create(((SplitDependencyNode) node).dto());
                medto.setPetype(ElementType.SPLIT);
            } else if (node instanceof SplitDependencyOptionNode) {
                new SplitDependencyOptionDAO().create(((SplitDependencyOptionNode) node).dto());
                medto.setPetype(ElementType.SPLITOPTION);
            } else if (node instanceof SequenceDependencyNode) {
                new SequenceDependencyDAO().create(((SequenceDependencyNode) node).dto());
                medto.setPetype(ElementType.SEQUENCE);
            }
            medto.setPEId(node.getDTOId());
            new PMContainPEDAO().create(medto);
        }

        //register all Edges and Model - Edge Relation
        Edge e = new Edge();
        e.setId(model_id);
        //if(pg.edgeSet().isEmpty()) System.out.println("Kong KOng");
        for (DefaultEdge de : pg.edgeSet()) {
            long srcid = pg.getEdgeSource(de).getDTOId();
            long tgtid = pg.getEdgeTarget(de).getDTOId();
            e.setSrcid(srcid);
            e.setTgtid(tgtid);
            //System.out.println("i come here");
            new PMContainEgDAO().insert(e);
        }

        //register all conditions , the same 
        for (ConditionDTO cd : pg.getConset()) {
            System.out.println(cd.getName());
            new ConditionDAO().create(cd);
        }
        return main_pid;
    }


}
