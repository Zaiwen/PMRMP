package nl.tue.tm.is.epc;

import org.utils.JsonUtil;

public class Arc{
	
	String id;
	Node source;
	Node target;
	
	private long srcid;
	private long tgtid;

	@Override
	public String toString() {
		return JsonUtil.toJson(this);
	}
	
	public Arc(){
	}
	public Arc(String id, Node source, Node target){
		this.id = id;
		this.source = source;
		this.target = target;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Node getSource() {
		return source;
	}
	public void setSource(Node source) {
		this.source = source;
	}
	public Node getTarget() {
		return target;
	}
	public void setTarget(Node target) {
		this.target = target;
	}

	
	public boolean equals(Object arg0) {
		if (arg0 instanceof Arc){
			return id.equals(((Arc)arg0).getId());
		}else{
			return false;
		}
	}
	
	public int hashCode() {
		return id.hashCode();
	}
	public long getSrcid() {
		return srcid;
	}
	public void setSrcid(long srcid) {
		this.srcid = srcid;
	}
	public long getTgtid() {
		return tgtid;
	}
	public void setTgtid(long tgtid) {
		if(tgtid == 0) System.out.println("0 is here");
		this.tgtid = tgtid;
	}
	
}
