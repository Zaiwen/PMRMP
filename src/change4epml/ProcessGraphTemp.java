package change4epml;

import ontology.IdWorker;

import org.sklse.processRegister.db.dto.*;
import org.sklse.processRegister.processGraph.*;
import org.sklse.processRegister.util.DocumentTypeRecognizer.DocumentType;
import org.utils.JsonUtil;

import java.util.*;

public class ProcessGraphTemp {
	
	private HashMap<String,GraphNode> nodes;
	private HashMap<String,Edge> edges;
	private List<ConditionDTO> cons;
	 long newAddNode = 0;
	 long newAddEdge = 0;
	
	
	
	public ProcessGraphTemp(){
		nodes = new HashMap<String,GraphNode>();
		edges = new HashMap<String,Edge>();
		cons = new ArrayList<ConditionDTO>();
	
	}
	
	public ConditionDTO getCon(long id){
		for(ConditionDTO cd : cons){
			if (cd.getId() == id) return cd;
		}
		return null;
	}
	
	public ProcessGraph convert2processGraph(){
		ProcessGraph pg = new ProcessGraph();
		for(GraphNode gn : this.nodes.values()){
			pg.addNode(gn);
		}
		System.out.println(pg.nodeSet().size());
		for(Edge e : this.edges.values()){
			GraphNode src =  pg.getNodeById(e.getSrcid());
			//if(src == null) System.out.println("Src is empty");
			GraphNode tgt = pg.getNodeById(e.getTgtid());
		
			pg.addEdge(src, tgt);
		}
		
		if(pg.edgeSet().isEmpty()){
			//System.out.println("here is kong");
		}
		pg.setConset(this.cons);
		
		ProcessDTO p = new ProcessDTO();
		try {
			p.setId(IdWorker.instance.nextId());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		pg.lang(DocumentType.EPC2_0);
		pg.representing(p);
		
		return pg;
	}
	
	public HashMap<String,GraphNode> getNodes() {
		return nodes;
	}
	public void setNodes(HashMap<String,GraphNode> nodes) {
		this.nodes = nodes;
	}
	public HashMap<String,Edge> getEdges() {
		return edges;
	}
	public void setEdges(HashMap<String,Edge> edges) {
		this.edges = edges;
	}

	public void addNode(GraphNode node){
		String id = String.valueOf(node.getDTOId());
		if(this.getNode(id) != null) {
			//System.out.println("Same ID in Adding " + id);
			return ;
		}
		nodes.put(id, node);
		//System.out.println("NodeID " + nodes.get(id).getDTOId());
		//System.out.println("NodeNum " + nodes.size());
		/*------------------------------
		if(nodes.get("15") == null) {
			System.out.println("15 NOOOO");
		}else{
			System.out.println("15 Yesss");
		}
		if(id.equalsIgnoreCase("15")){
			nodes.put("15",new EventNode());
			System.out.println("I Put 15 into Nodes");
			if(nodes.get("15") == null) {
				System.out.println("15 NOOOO");
			}else{
				System.out.println("15 Yesss");
			}
		}
		-----------------------------------------*/
		System.out.println("----------------");
		
	}
	
	public void deleteNode(GraphNode gn){
		nodes.remove(gn);
		//System.out.println("Delete Test -- Nodes Num : " + nodes.size());
	}
	
	public void addEdge(Edge edge){
		String id = String.valueOf(edge.getId());
		edges.put(id, edge);
	}
	
	public void addCon(ConditionDTO c){
		if(cons.contains(c)) return ;
		else cons.add(c);
	}
	
	public void removeEdgeByTwoNode(long srcid,long tgtid){
		HashMap<String,Edge> edgeNew = new HashMap<String,Edge>();
		for(Edge e : edges.values()){
			if(e.getSrcid() == srcid && e.getTgtid() == tgtid) continue;
			edgeNew.put(String.valueOf(e.getId()), e);	
		}
		edges = edgeNew;
	}
	
	public void removeEdgeByTarget(long tgtid){
		HashMap<String,Edge> edgeNew = new HashMap<String,Edge>();
		for(Edge e : edges.values()){
			if(e.getTgtid() == tgtid) continue;
			edgeNew.put(String.valueOf(e.getId()), e);
		}
		edges = edgeNew;
	}
	
	public void removeEdgeBySource(long srcid){
		HashMap<String,Edge> edgeNew = new HashMap<String,Edge>();
		for(Edge e : edges.values()){
			if(e.getSrcid() == srcid) continue;
			edgeNew.put(String.valueOf(e.getId()), e);
		}
		edges = edgeNew;
	}
	
	public GraphNode getNode(String id){
		return nodes.get(id);
	}
	
	public Set<GraphNode> getPreNode(long id){
		//System.out.println("Current Node id : " + id);
		Set<GraphNode> sg = new HashSet<GraphNode>();
		for(Edge e : this.getEdgesByTgt(id)){
			GraphNode gn = this.getNode(String.valueOf(e.getSrcid()));
			if(gn != null) sg.add(gn);
			//System.out.println("preNode id : " + gn.getDTOId());
			
		}
		//System.out.println("----------------");
		//if (sg.size() == 0 ) return null;
		return sg;
	}
	
	public Set<GraphNode> getPostNode(long id){
		Set<GraphNode> sg = new HashSet<GraphNode>();
		for(Edge e : this.getEdgesBySrc(id)){
			GraphNode g = this.getNode(String.valueOf(e.getTgtid()));
			if(g != null)
			sg.add(g);
		}
		//if(sg.size() == 0) return null;
		return sg;
	}
	
	public Set<Edge> getEdgesBySrc(long src){
		Set<Edge> es = new HashSet<Edge>();
		for(Edge e : edges.values()){
			if(e.getSrcid() == src)
				es.add(e);
		}
		return es;
	}
	
	public Set<Edge> getEdgesByTgt(long tgt){
		Set<Edge> es = new HashSet<Edge>();
		for(Edge e : edges.values()){
			if(e.getTgtid() == tgt)
				es.add(e);
		}
		return es;
	}
	
	public int  assignId (GraphNode node){
	//...
		return 0;
	}
	

	public Set<ProcessDTO> getALLProcessDTO(){
		Set<ProcessDTO> pdset = new HashSet<ProcessDTO>();
		for(GraphNode gn : nodes.values()){
			if(gn instanceof ProcessNode){
				pdset.add(((ProcessNode) gn).dto());
			}
		}
		return pdset;
	}
	
	public void Condition2Object() throws Exception{
		for(ProcessDTO pd : this.getALLProcessDTO()){
			String pre = pd.getPreconditions();
			String post = pd.getPostconditions();
			if(!pre.equalsIgnoreCase("")){
				Set<ConditionDTO> set = pd.convertString2Object(pre, 1001);
				for(ConditionDTO cd : set){
					this.addCon(cd);
				}
			}
			if(!post.equalsIgnoreCase("")){
				Set<ConditionDTO> set = pd.convertString2Object(post, 1002);
				for(ConditionDTO cd : set){
					this.addCon(cd);
				}
			}
		}
		
		
	}

	public String toString(){
		String str = "";
		int count = 0;
	
		for(GraphNode gn: nodes.values()){
			count ++;
			str += "count: " + count + "\n";
			if(gn instanceof ProcessNode){
				str += "ProcessNode : " + ((ProcessNode) gn).dto().getName() + "\n" + 
						"ID : " + ((ProcessNode) gn).dto().getId() + "\n" + 
						"Preconditon : " + ((ProcessNode) gn).dto().getPreconditions() + "\n" +
						"Postcondition : " + ((ProcessNode) gn).dto().getPostconditions() + "\n";
			}
			if(gn instanceof EventNode){
				str += "EventNode : " + ((EventNode) gn).dto().getName() + "\n" +
						"ID : " + ((EventNode) gn).dto().getId() + "\n";
			}
			if(gn instanceof JoinDependencyNode){
				str += "JoinDependencyNode : " + ((JoinDependencyNode) gn).dto().getType() + "\n" +
						"ID : " + ((JoinDependencyNode) gn).dto().getId() + "\n";
			}
			if(gn instanceof SplitDependencyNode){
				str += "SplitDependencyNode : " + ((SplitDependencyNode) gn).dto().getType() + "\n" + 
						"ID : " + ((SplitDependencyNode) gn).dto().getId() + "\n" ;
			}
			if(gn instanceof ResourceNode){
				str += "ResourceNode : " + ((ResourceNode) gn).dto().getName() + "\n" +
						"ID : " + ((ResourceNode) gn).dto().getId() + "\n";
			}
			if(gn instanceof SequenceDependencyNode){
				str += "SequenceDependencyNode : " + ((SequenceDependencyNode) gn).dto().getName() + "\n" +
						"ID : " + ((SequenceDependencyNode) gn).dto().getId() + "\n";
			}
			if(gn instanceof JoinDependencyOptionNode){
				str += "JoinDependencyOptionNode : " + ((JoinDependencyOptionNode) gn).dto().getCondition()  + "\n" + 
						"ID : " + ((JoinDependencyOptionNode) gn).dto().getId() + "\n";
			}
			if(gn instanceof SplitDependencyOptionNode){
				str += "SplitDependencyOptionNode : " + ((SplitDependencyOptionNode) gn).dto().getCondition() + "\n" +
						"ID : " + ((SplitDependencyOptionNode) gn).dto().getId()+ "\n";
			}
			
			str += "---------------------\n";
			str += "---------------------\n";
			
		}
		
		
		str += "ElementsNode count : " + nodes.entrySet().size() + "\n";
		str += "Real count : " + count + "\n";
		
		for(Edge e : edges.values()){
				
			str += e.getSrcid() + "  ----->>  " + e.getTgtid() + "\n";
		}
		
		if(cons.isEmpty()) str += "\nNo ConditionDTO\n";
		
		for(ConditionDTO cd : cons){
			str += "Condition : " + "\n" + "ID : " + cd.getId() + "\n" + 
					"SingleType : " + cd.getSingleType() + "\n" +
					"PredicateType : " + cd.getPredicate() + "\n" + 
					"PositionType : " + cd.getPositionType() + "\n" + 
					"Name : " + cd.getName() + "\n";
			if(cd.getSingleType() == 1002){
				str += "ConditionExpression ID :" + cd.getConditionIdList().toString() + "\n";
			}
					
			str += "---------------------\n";
			str += "---------------------\n";
		}
		
		return str;
		
		
	}

	public void ParamObject2String() {
		for(GraphNode node : this.nodes.values()){
        	if (node instanceof JoinDependencyNode) {
               JoinDependencyDTO jdd = ((JoinDependencyNode) node).get_dto();
			   jdd.setParam(JsonUtil.toJson(jdd.getParamObject()));
            } else if (node instanceof JoinDependencyOptionNode) {
               JoinDependencyOptionDTO    dto = ((JoinDependencyOptionNode) node).dto();
			   dto.setParam(JsonUtil.toJson(dto.getParamObject()));
            } else if (node instanceof EventNode) {
              EventDTO   dto = ((EventNode) node).dto();
			  dto.setParam(JsonUtil.toJson(dto.getParamObject()));
            } else if (node instanceof ResourceNode) {
                //
            } else if (node instanceof ProcessNode) {
               ProcessDTO    dto = ((ProcessNode) node).dto();
			   dto.setParam(JsonUtil.toJson(dto.getParamObject()));
            } else if (node instanceof SplitDependencyNode) {
               SplitDependencyDTO    dto = ((SplitDependencyNode) node).dto();
			   dto.setParam(JsonUtil.toJson(dto.getParamObject()));
            } else if (node instanceof SplitDependencyOptionNode) {
                SplitDependencyOptionDTO dto = ((SplitDependencyOptionNode) node).dto();
				dto.setParam(JsonUtil.toJson(dto.getParamObject()));
            } else if (node instanceof SequenceDependencyNode) {
                SequenceDependencyDTO dto = ((SequenceDependencyNode) node).dto();
				dto.setParam(JsonUtil.toJson(dto.getParamObject()));
            }
        	
        }
		
	}

	public long getNewAddNode() {
		return newAddNode;
	}

	public void setNewAddNode(long newAddNode) {
		this.newAddNode = newAddNode;
	}

}
