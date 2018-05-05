package change4epml;

import nl.tue.tm.is.epc.*;
import ontology.IdWorker;
import org.jgrapht.graph.DefaultEdge;
import org.sklse.processRegister.db.dto.ConditionDTO;
import org.sklse.processRegister.db.services.ConditionService;
import org.sklse.processRegister.processGraph.*;

import java.util.ArrayList;
import java.util.List;

public class ProcessGraph2Epml {

    public static EPC Convert(ProcessGraph pg) throws NumberFormatException, Exception {

        EPC epc = new EPC();
        epc.setId(String.valueOf(pg.getPmdto().getEpcid()));
        epc.setName(pg.getPmdto().getName());

        //Delete EmptyOptionNode (Empty)
        DeleteOptionNode(epc, pg);

        //Map ProcessNode
        MapProcessNode(epc, pg);

        //Map Join/SplitDependencyNode
        MapJoinSplit(epc, pg);

        //MapSequenceNode
        MapSequenceNode(epc, pg);

        //MapEventNode
        MapEventNode(epc, pg);

        //Convert Pre/PostCondition into Event
        // TODO upgrade the algorithoms
        ConvertConditionIntoEvent(epc, pg);

        //Map Edge into Arc
        MapEdge(epc, pg);

        //assing original ID
        AssigningID(epc, pg);

        return epc;
    }

    private static void AssigningID(EPC epc, ProcessGraph pg) {
        for (GraphNode gn : pg.nodeSet()) {
            long orid = gn.getOriginalId();
            if (orid != 0) {
                Node n = epc.getNodeById(gn.getDTOId());
                n.setId(String.valueOf(orid));
            }
        }
    }

    private static void MapEdge(EPC epc, ProcessGraph pg) {
        // TODO Auto-generated method stub

        for (DefaultEdge de : pg.edgeSet()) {
            GraphNode src = pg.getEdgeSource(de);
            GraphNode tgt = pg.getEdgeTarget(de);
            Arc a = new Arc();
            try {
                a.setId(String.valueOf(IdWorker.getNextId()));
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (src.getDTOId() == 0) System.out.println("SrcDTOid is 0 ");
            if (src.getDTOId() == 0) System.out.println("TgtDTOid is 0");
            a.setSrcid(src.getDTOId());
            a.setTgtid(tgt.getDTOId());
            epc.addArcByid(a);
        }

    }

    private static void ConvertConditionIntoEvent(EPC epc, ProcessGraph pg) throws NumberFormatException, Exception {
        // TODO Auto-generated method stub
        ProcessGraph newpg = new ProcessGraph();
        for (ProcessNode gn : pg.getAllProcessNodes()) {
            GraphNode pre = null;
            GraphNode post = null;
            if (!pg.getPre(gn).isEmpty()) {
                pre = pg.getPre(gn).iterator().next();
            }
            if (!pg.getPost(gn).isEmpty()) {
                post = pg.getPost(gn).iterator().next();
            }

            //processNode 前面是Join 则需要仔细探查 Process - Join - Event模式
            if (pre instanceof JoinDependencyNode) {
                /*for(GraphNode gn2 : pg.getPre(pre)){
                    if(gn2 instanceof ProcessNode){
						String condition = ((ProcessNode) gn2).dto().getPostconditions();
						if(gn.dto().getPreconditions().equals(condition)){
							Event e = new Event();
							e.setName(condition.replace("#", ""));
							try {
								e.setId(String.valueOf(IdWorker.instance.getNextId()));
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							epc.addEvent(e);
							NullNode nn = new NullNode();
							nn.setDTOId(Long.parseLong(e.getId()));
							pg.addNode(nn);
							pg.addEdge(pre,nn);
							pg.addEdge(nn, gn);
							pg.removeEdgeByTwoNode(pre, gn);
							break;
						}
					}
				}
				4/25修改 算法优化
				*/
                ConditionDTO cddto = pg.getConById(gn.dto().getPreconditionid());

                for (ConditionDTO cd : getPostconditonFromPrenode(pg, pre)) {
                    if (cd == null) {
                        continue;
                    } else if (ConditionService.instance.isSubof(cddto, cd)) {
                        Event e = new Event();
                        e.setName(cddto.getName());
                        e.setId(String.valueOf(IdWorker.instance.getNextId()));
                        //System.out.println(e.getId());
                        epc.addEvent(e);
                        NullNode nn = new NullNode();
                        //System.out.println();
                        nn.setId(Long.parseLong(e.getId()));
                        pg.addNode(nn);
                        //if(nn.getDTOId() == 0 ) throw new Exception("0000000000000000");
                        pg.addEdge(pre, nn);
                        pg.addEdge(nn, gn);
                        pg.removeEdgeByTwoNode(pre, gn);
                        break;
                    }
                }

            }

            if (post instanceof SplitDependencyNode) {
                ConditionDTO cddto = pg.getConById(gn.dto().getPostconditionid());
                for (ConditionDTO cd : getPreconditionFromPostnode(pg, post)) {
                    if (cd == null) continue;
                    if (ConditionService.instance.isSubof(cddto, cd)) {
                        Event e = new Event();
                        e.setName(cddto.getName());
                        e.setId(String.valueOf(IdWorker.instance.getNextId()));
                        epc.addEvent(e);
                        NullNode nn = new NullNode();
                        nn.setDTOId(Long.parseLong(e.getId()));
                        pg.addNode(nn);
                        pg.addEdge(pre, nn);
                        pg.addEdge(nn, gn);
                        pg.removeEdgeByTwoNode(pre, gn);
                        System.out.println("Error Scheme : " + "Function - Event - Split ");
                        break;
                    }
                }
            }
        }

        for (GraphNode gn : pg.nodeSet()) {
            newpg.addNode(gn);
        }

        for (DefaultEdge de : pg.edgeSet()) {
            newpg.addEdge(de);

        }
        newpg.setConset(pg.getConset());

        pg = newpg;

        for (JoinDependencyNode join : pg.getAllJoinDependencyNodes()) {

            GraphNode post = pg.getPost(join).iterator().next();
            //出现Join - Split模式需探查是否有Event
            if (post instanceof SplitDependencyNode) {
                boolean isfind = false;
                ConditionService cs = ConditionService.instance;
                if (getPostconditonFromPrenode(pg, join) == null) continue;
                List<ConditionDTO> postcons = getPostconditonFromPrenode(pg, join);
                List<ConditionDTO> precons = getPreconditionFromPostnode(pg, post);
                for (ConditionDTO postcd : postcons) {
                    if (isfind) break;
                    for (ConditionDTO precd : precons) {
                        if (isfind) break;
                        for (ConditionDTO singlepost : cs.getSingleCondition(postcd))
                            if (cs.isSubof(singlepost, precd)) {
                                Event e = new Event();
                                e.setName(singlepost.getName());
                                e.setId(String.valueOf(IdWorker.instance.nextId()));
                                epc.addEvent(e);
                                NullNode nn = new NullNode();
                                nn.setDTOId(Long.parseLong(e.getId()));
                                pg.addNode(nn);
                                pg.addEdge(join, nn);
                                pg.addEdge(nn, post);
                                pg.removeEdgeByTwoNode(join, post);
                                isfind = true;
                                break;
                            }
                    }
                }
            }
        }

    }


    private static void MapEventNode(EPC epc, ProcessGraph pg) {
        // TODO Auto-generated method stub
        for (GraphNode gn : pg.getAllEventNodes()) {
            Event e = new Event();
            e.setId(String.valueOf(gn.getDTOId()));
            e.setName(((EventNode) gn).dto().getName());
            epc.addEvent(e);
        }
    }

    private static void MapSequenceNode(EPC epc, ProcessGraph pg) {
        for (GraphNode gn : pg.nodeSet()) {
            if (gn instanceof SequenceDependencyNode) {
                GraphNode gntemp = pg.getEdgeSource(pg.incomingEdgesOf(gn).iterator().next());
                String eventInfo = ((ProcessNode) gntemp).dto().getPostconditions();
                String name = ((SequenceDependencyNode) gn).dto().getName();
                if (name != null && name.trim().length() > 0) {
                    eventInfo = name + " " + eventInfo;
                }
                eventInfo = eventInfo.trim();
                Event e = new Event();
                e.setId(String.valueOf(gn.getDTOId()));
                e.setName(eventInfo.replace("#", ""));
                epc.addEvent(e);

            }
        }

    }

    private static void MapJoinSplit(EPC epc, ProcessGraph pg) {
        for (GraphNode gn : pg.nodeSet()) {
            if (gn instanceof JoinDependencyNode) {
                Connector c = new Connector();
                c.setId(String.valueOf(gn.getDTOId()));
                c.setName(((JoinDependencyNode) gn).get_dto().getType().name());
                epc.addConnector(c);
            } else if (gn instanceof SplitDependencyNode) {
                Connector c = new Connector();
                c.setId(String.valueOf(gn.getDTOId()));
                c.setName(((SplitDependencyNode) gn).dto().getType().name());
                epc.addConnector(c);
            }
        }

    }

    private static void MapProcessNode(EPC epc, ProcessGraph pg) {
        for (ProcessNode pn : pg.getAllProcessNodes()) {
            Function f = new Function();
            f.setId(String.valueOf(pn.getDTOId()));
            f.setName(pn.dto().getName());
            epc.addFunction(f);
        }

    }

    private static void DeleteOptionNode(EPC epc, ProcessGraph pg) {
        //删除空的OptionNode
        ProcessGraph newpg = new ProcessGraph();
        // TODO Auto-generated method stub
        for (GraphNode gn : pg.nodeSet()) {
            if (gn instanceof JoinDependencyOptionNode) {
                if (!((JoinDependencyOptionNode) gn).dto().getCondition().equalsIgnoreCase("")) {
                    Event e = new Event();
                    e.setId(String.valueOf(gn.getDTOId()));
                    e.setName(((JoinDependencyOptionNode) gn).dto().getCondition());
                    epc.addEvent(e);
                } else {
                    DefaultEdge de = pg.incomingEdgesOf(gn).iterator().next();
                    GraphNode pre = pg.getEdgeSource(de);
                    DefaultEdge de2 = pg.outgoingEdgesOf(gn).iterator().next();
                    GraphNode post = pg.getEdgeTarget(de2);
                    pg.removeEdge(de2);
                    pg.removeEdge(de);
                    pg.addEdge(pre, post);
                    continue;
                }

            }

            if (gn instanceof SplitDependencyOptionNode) {
                if (!((SplitDependencyOptionNode) gn).dto().getCondition().equalsIgnoreCase("")) {
                    Event e = new Event();
                    e.setId(String.valueOf(gn.getDTOId()));
                    e.setName(((SplitDependencyOptionNode) gn).dto().getCondition());
                    epc.addEvent(e);
                } else {
                    DefaultEdge de = pg.incomingEdgesOf(gn).iterator().next();
                    GraphNode pre = pg.getEdgeSource(de);
                    DefaultEdge de2 = pg.outgoingEdgesOf(gn).iterator().next();
                    GraphNode post = pg.getEdgeTarget(de2);
                    pg.removeEdge(de2);
                    pg.removeEdge(de);
                    pg.addEdge(pre, post);
                    continue;
                }
            }
            newpg.addNode(gn);
        }

        for (DefaultEdge de : pg.edgeSet()) {
            GraphNode pre = pg.getEdgeSource(de);
            GraphNode post = pg.getEdgeTarget(de);
            newpg.addEdge(pre, post);
        }

        newpg.setConset(pg.getConset());
        pg = newpg;
    }

    private static List<ConditionDTO> getPostconditonFromPrenode(ProcessGraph pg, GraphNode gn) throws Exception {
        if (pg.getConset() == null) throw new Exception("Conset Null when came into GetPostconditionFromPrenode");

        List<ConditionDTO> conlist = new ArrayList<ConditionDTO>();
        for (GraphNode pre : pg.getPre(gn)) {
            if (pre instanceof ProcessNode) {
                long cdid = ((ProcessNode) pre).dto().getPostconditionid();
                if (cdid == 0) System.out.println("this pre has no post");
                else {
                    //System.out.println(" ProcessGraph2Epml ConditionId : " + cdid);
                    conlist.add(pg.getConById(cdid));
                }
            } else if (pre instanceof JoinDependencyNode || pre instanceof SplitDependencyNode) {
                conlist.addAll(getPostconditonFromPrenode(pg, pre));
            }
        }
        return conlist;
    }

    private static List<ConditionDTO> getPreconditionFromPostnode(ProcessGraph pg, GraphNode gn) throws Exception {
        List<ConditionDTO> conlist = new ArrayList<ConditionDTO>();
        for (GraphNode post : pg.getPost(gn)) {
            if (post instanceof ProcessNode) {
                long cdid = ((ProcessNode) post).dto().getPreconditionid();
                conlist.add(pg.getConById(cdid));
            } else if (post instanceof JoinDependencyNode || post instanceof SplitDependencyNode) {
                conlist.addAll(getPreconditionFromPostnode(pg, post));
            }
        }
        return conlist;
    }

}
