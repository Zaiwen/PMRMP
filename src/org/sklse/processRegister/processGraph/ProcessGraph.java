package org.sklse.processRegister.processGraph;

import ontology.IdWorker;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.sklse.processRegister.db.dto.ConditionDTO;
import org.sklse.processRegister.db.dto.ProcessDTO;
import org.sklse.processRegister.db.dto.ProcessModelDTO;
import org.sklse.processRegister.util.DocumentTypeRecognizer.DocumentType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProcessGraph {
    public ProcessGraph() {
        g = new SimpleDirectedGraph<GraphNode, DefaultEdge>(DefaultEdge.class);
        process = new ProcessDTO();
        pmdto = new ProcessModelDTO();
        conset = new ArrayList<ConditionDTO>();
    }

    public DocumentType lang() {
        return srcLang;
    }

    public void lang(DocumentType _lang) {
        this.srcLang = _lang;
    }

    public ProcessDTO representing() {
        return process;
    }

    public void representing(ProcessDTO process) {
        this.process = process;
    }

    public boolean addNode(GraphNode node) {
        return g.addVertex(node);
    }

    public Set<GraphNode> getPre(GraphNode gn){
    	Set<GraphNode> pres = new HashSet<GraphNode>();
    	for(DefaultEdge de : this.incomingEdgesOf(gn)){
    		GraphNode src = this.getEdgeSource(de);
    		pres.add(src);
    	}
    	return pres;
    }

    public Set<GraphNode> getPost(GraphNode gn){
    	Set<GraphNode> posts = new HashSet<GraphNode>();
    	for(DefaultEdge de : this.outgoingEdgesOf(gn)){
    		GraphNode tgt = this.getEdgeTarget(de);
    		posts.add(tgt);
    	}
    	return posts;
    }

    public GraphNode getNodeById(long id ){
    	//System.out.println("SearchID : " + id );
    	for(GraphNode gn : this.nodeSet()){
    		//System.out.println("Gn id : " + gn.getDTOId());
    		if(gn.getDTOId() == id){
    			//System.out.println("I find the node");
    			return gn;
    		}

    	}
    	return null;
    }

    public void  removeEdgeByTwoNode(GraphNode src,GraphNode tgt){
    	for (DefaultEdge de : this.outgoingEdgesOf(src)){
    		GraphNode t = this.getEdgeTarget(de);
    		if(t.equals(tgt)) {
    			this.removeEdge(de);
    			return ;
    		}
    	}

    }

    public DefaultEdge addEdge(GraphNode v1, GraphNode v2) {
        if (v1 == null) {
        	try {
				throw new Exception(" Add Edge V 1 is empty");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            System.out.println("V1 is empty");
        } else if (v2 == null) {
        	try {
				throw new Exception(" Add Edge V 2 is empty");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            System.out.println("V2  is empty");
        } else {
            //System.out.println("I get Node ");
            return g.addEdge(v1, v2);
        }
        return null;
    }

    public Set<DefaultEdge> incomingEdgesOf(GraphNode node) {
        return g.incomingEdgesOf(node);
    }

    public Set<DefaultEdge> outgoingEdgesOf(GraphNode node) {
        return g.outgoingEdgesOf(node);
    }

    public DefaultEdge getEdgeByNode(GraphNode src,GraphNode tgt){
    	for(DefaultEdge de : this.outgoingEdgesOf(src)){
    		GraphNode t = this.getEdgeTarget(de);
    		if(t.equals(tgt)) return de;
    	}
    	return null;
    }
    public GraphNode getEdgeSource(DefaultEdge e) {
        return g.getEdgeSource(e);
    }

    public GraphNode getEdgeTarget(DefaultEdge e) {
        return g.getEdgeTarget(e);
    }

    public Set<GraphNode> nodeSet() {
        return g.vertexSet();
    }

    public Set<EventNode> getAllEventNodes() {
        Set<GraphNode> s = g.vertexSet();
        Set<EventNode> es = new HashSet<EventNode>();
        for (GraphNode n : s) {
            if (n instanceof EventNode) {
                es.add((EventNode) n);
            }
        }
        return es;
    }

    public Set<JoinDependencyNode> getAllJoinDependencyNodes(){
    	Set<JoinDependencyNode> joinset = new HashSet<JoinDependencyNode>();
    	for(GraphNode gn : this.nodeSet()){
    		if(gn instanceof JoinDependencyNode){
    			joinset.add((JoinDependencyNode) gn);
    		}
    	}
    	return joinset;
    }

    public Set<ResourceNode> getAllResourceNodes() {
        Set<GraphNode> s = g.vertexSet();
        Set<ResourceNode> ns = new HashSet<ResourceNode>();
        for (GraphNode n : s) {
            if (n instanceof ResourceNode) {
                ns.add((ResourceNode) n);
            }
        }
        return ns;
    }

    /*
     *      { ProcessElementNode } = { ProcessNode, SplitDependencyNode, 
     *              JoinDependencyNode, LoopStartNode }
     * 
     */
    public Set<GraphNode> getAllProcessElementNodes() {
        Set<GraphNode> s = g.vertexSet(),
                ns = new HashSet<GraphNode>();
        for (GraphNode n : s) {
            if (n instanceof ProcessNode || n instanceof SplitDependencyNode ||
                    n instanceof JoinDependencyNode || n instanceof LoopStartNode ||
                    n instanceof SequenceDependencyNode) {
                ns.add(n);
            }
        }
        return ns;
    }

    public Set<ProcessNode> getAllProcessNodes() {
        Set<ProcessNode> result = new HashSet<ProcessNode>();
        for (GraphNode graphNode : g.vertexSet()) {
            if (graphNode instanceof ProcessNode) {
                result.add((ProcessNode) graphNode);
            }
        }
        return result;
    }

    /**
     * 删除某个node
     *
     * @param graphNode
     */
    public void removeGraphNode(GraphNode graphNode) {
        g.removeVertex(graphNode);
    }

    public List<GraphNode> getSortedProcessElementNodes() {
        List<GraphNode> lst = new ArrayList<GraphNode>();
        List<ProcessNode> plst = new ArrayList<ProcessNode>();
        List<SplitDependencyNode> slst = new ArrayList<SplitDependencyNode>();
        List<JoinDependencyNode> jlst = new ArrayList<JoinDependencyNode>();
        List<LoopStartNode> llst = new ArrayList<LoopStartNode>();
        List<SequenceDependencyNode> sdlst = new ArrayList<SequenceDependencyNode>();
        Set<GraphNode> set = getAllProcessElementNodes();
        for (GraphNode node : set) {
            if (node instanceof ProcessNode) plst.add((ProcessNode) node);
            else if (node instanceof SplitDependencyNode) slst.add((SplitDependencyNode) node);
            else if (node instanceof JoinDependencyNode) jlst.add((JoinDependencyNode) node);
            else if (node instanceof LoopStartNode) llst.add((LoopStartNode) node);
            else if (node instanceof SequenceDependencyNode) sdlst.add((SequenceDependencyNode) node);
        }
        for (int i = 0; i < plst.size(); i++) {
            int min = i;
            for (int j = i + 1; j < plst.size(); j++) {
                if (plst.get(j).dto().getId() < plst.get(min).dto().getId()) {
                    min = j;
                }
            }
            if (min != i) {
                ProcessNode tmp = plst.get(i);
                plst.set(i, plst.get(min));
                plst.set(min, tmp);
            }
        }
        for (int i = 0; i < slst.size(); i++) {
            int min = i;
            for (int j = i + 1; j < slst.size(); j++) {
                if (slst.get(j).dto().getId() < slst.get(min).dto().getId()) {
                    min = j;
                }
            }
            if (min != i) {
                SplitDependencyNode tmp = slst.get(i);
                slst.set(i, slst.get(min));
                slst.set(min, tmp);
            }
        }
        for (int i = 0; i < jlst.size(); i++) {
            int min = i;
            for (int j = i + 1; j < jlst.size(); j++) {
                if (jlst.get(j).dto().getId() < jlst.get(min).dto().getId()) {
                    min = j;
                }
            }
            if (min != i) {
                JoinDependencyNode tmp = jlst.get(i);
                jlst.set(i, jlst.get(min));
                jlst.set(min, tmp);
            }
        }
        for (int i = 0; i < llst.size(); i++) {
            int min = i;
            for (int j = i + 1; j < llst.size(); j++) {
                if (llst.get(j).dto().getId() < llst.get(min).dto().getId()) {
                    min = j;
                }
            }
            if (min != i) {
                LoopStartNode tmp = llst.get(i);
                llst.set(i, llst.get(min));
                llst.set(min, tmp);
            }
        }
        for (int i = 0; i < sdlst.size(); i++) {
            int min = i;
            for (int j = i + 1; j < sdlst.size(); j++) {
                if (sdlst.get(j).dto().getId() < sdlst.get(min).dto().getId()) {
                    min = j;
                }
            }
            if (min != i) {
                SequenceDependencyNode tmp = sdlst.get(i);
                sdlst.set(i, sdlst.get(min));
                sdlst.set(min, tmp);
            }
        }
        lst.addAll(plst);
        lst.addAll(slst);
        lst.addAll(jlst);
        lst.addAll(llst);
        lst.addAll(sdlst);
        return lst;
    }


    public Set<DefaultEdge> edgeSet() {
        return g.edgeSet();
    }

    public void removeEdge(DefaultEdge edge) {
        g.removeEdge(edge);
    }

    /*
    public Set<DefaultEdge> getSequenceEdges() {
        Set<DefaultEdge> all = g.edgeSet(),
                         seq = new HashSet<DefaultEdge>();
        for( DefaultEdge e : all ) {
            if ( g.getEdgeSource(e) instanceof ProcessNode &&
                 g.getEdgeTarget(e) instanceof ProcessNode ) {
                seq.add(e);
            }
        }
        return seq;
    }
    */
    public SplitDependencyNode dependencyForOption(SplitDependencyOptionNode node) {
        Set<DefaultEdge> in = g.incomingEdgesOf(node);
        DefaultEdge e = in.iterator().next();
        GraphNode d = g.getEdgeSource(e);
        return (SplitDependencyNode) d;
    }

    public JoinDependencyNode dependencyForOption(JoinDependencyOptionNode node) {
        Set<DefaultEdge> out = g.outgoingEdgesOf(node);
        DefaultEdge e = out.iterator().next();
        GraphNode d = g.getEdgeTarget(e);
        return (JoinDependencyNode) d;
    }

    public boolean containsEdge(GraphNode src, GraphNode tgt) {
        return g.containsEdge(src, tgt);
    }

    public boolean containsNode(GraphNode node) {
        return g.containsVertex(node);
    }

    public void id(long id) {
        pmid = id;
    }

    public long id() {
        return pmid;
    }

    public String debug() {
        String s = "";
        s += ("lang : " + this.lang() + "\n");
        s += ("representing : " + this.representing().getName() + "\n");
        Set<GraphNode> nodes = this.nodeSet();
        for (GraphNode n : nodes) {
            s += (n + "\n");
        }
        Set<DefaultEdge> edges = this.edgeSet();
        for (DefaultEdge e : edges) {
            s += (e + "\n");
        }
        for(ConditionDTO cd : conset){
        	s += "Condition ID : " + (cd.getId()) + "\n";
        }

        return s;
    }


    private DirectedGraph<GraphNode, DefaultEdge> g;
    private DocumentType srcLang;
    private ProcessDTO process;
    private List<ConditionDTO> conset;
    private long pmid;
    private ProcessModelDTO pmdto;
    


    public static void main(String args[]) {
        ProcessGraph pg = new ProcessGraph();
       
    }

	public void addEdge(DefaultEdge de) {
		// TODO Auto-generated method stub
		GraphNode src = this.getEdgeSource(de);
		GraphNode tgt = this.getEdgeTarget(de);
		this.addEdge(src, tgt);
	}



	public ConditionDTO getConById(long id) throws Exception{
		if(this.conset == null) throw new Exception("Conset is Empty");
		for(ConditionDTO cd : this.conset){
			//System.out.println("getConByID - > ConditionID : " + cd.getId());
			if(cd.getId() == id){
				return cd;
			}
		}
		return null;
	}

	public List<ConditionDTO> getConset() {
		return conset;
	}

	public void setConset(List<ConditionDTO> conset) {
		this.conset = conset;
	}

	public ProcessModelDTO getPmdto() {
		return pmdto;
	}

	public void setPmdto(ProcessModelDTO pmdto) {
		this.pmdto = pmdto;
	}

	public void assingID() throws Exception {
		
		AssingConditionId();
		for(GraphNode gn : this.nodeSet()){
			long newid = IdWorker.instance.nextId();
			gn.setDTOId(newid);
		}
		
		for(GraphNode gn : this.nodeSet()){
			if(gn instanceof SequenceDependencyNode){
				System.out.println("Sequence");
				long following = this.getPost(gn).iterator().next().getDTOId();
				((SequenceDependencyNode) gn).setFollowing(following);
				long preceding = this.getPost(gn).iterator().next().getDTOId();
				((SequenceDependencyNode) gn).setPreceding(preceding);
			}
			if(gn instanceof JoinDependencyOptionNode){
				System.out.println("JoinDependencyOption");
				//if(((JoinDependencyOptionNode) gn).getDTOId() == 1) System.out.println("Assign ID  find 1");
				long joinid = this.getPost(gn).iterator().next().getDTOId();
				((JoinDependencyOptionNode) gn).dto().setJoinid(joinid);
				
				if(this.getPre(gn).isEmpty()){
					
				}else{
					long preceding = this.getPre(gn).iterator().next().getDTOId();
					((JoinDependencyOptionNode) gn).dto().setPreceding(preceding);
				}
				
			}
			if(gn instanceof SplitDependencyOptionNode){
				System.out.println("SplitDependencyOption");
				long following = this.getPre(gn).iterator().next().getDTOId();
				((SplitDependencyOptionNode) gn).dto().setFollowing(following);
				if(this.getPost(gn).isEmpty()){
					
				}else{
					long splitid = this.getPost(gn).iterator().next().getDTOId();
					((SplitDependencyOptionNode) gn).dto().setSplitid(splitid);
				}
				
			}
			
			if(gn instanceof JoinDependencyNode){
				System.out.println("JoinDependency");
				System.out.println(gn.getDTOId());
				if(this.getPost(gn).isEmpty()){
					System.out.println("Join Following Is Empty");
				}else {
					long following = this.getPost(gn).iterator().next().getDTOId();
					((JoinDependencyNode) gn).dto().setFollowing(following);
				}
				
			}
			if(gn instanceof SplitDependencyNode){
				System.out.println("SplitDependency");
				if(this.getPre(gn).isEmpty()){
					System.out.println("Split Preceding is Empty");
				}else {
					long preceding = this.getPre(gn).iterator().next().getDTOId();
					((SplitDependencyNode) gn).dto().setPreceding(preceding);
				}
				
			}
		}
		
	}	
		
		private void AssingConditionId() throws Exception{
			this.conset = new ArrayList<ConditionDTO>();
			for(ProcessNode pd : this.getAllProcessNodes()){
				String pre = pd.dto().getPreconditions();
				String post = pd.dto().getPostconditions();
				if(!pre.equalsIgnoreCase("")){
					Set<ConditionDTO> set = pd.dto().convertString2Object(pre, 1001);
					for(ConditionDTO cd : set){
						this.addCon(cd);
					}
				}
				if(!post.equalsIgnoreCase("")){
					Set<ConditionDTO> set = pd.dto().convertString2Object(post, 1002);
					for(ConditionDTO cd : set){
						this.addCon(cd);
					}
				}
			}
			
			
		}

		


	private void addCon(ConditionDTO cd) {
		if(cd != null) this.conset.add(cd);
		
	}
}
